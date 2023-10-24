package TestsDB;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import database.DatabaseScripts;
import model.AreaCheckerBean;
import model.ResultsControllerBean;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import database.DatabaseConnector;
import org.testcontainers.shaded.com.google.common.collect.HashMultiset;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.sql.*;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@Testcontainers
public class TestResults {

    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.0")
            .withDatabaseName("PointStrorage")
            .withUsername("postgres")
            .withPassword("228337");

    static class TestDatabaseConnector extends DatabaseConnector {
        private final String url;
        private final String user;
        private final String password;

        TestDatabaseConnector(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

        @Override
        public Connection connect() throws ClassNotFoundException, SQLException {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        }
    }

    @BeforeAll
    public static void beforeAll(){
        postgres.start();
    }

    @AfterAll
    public static void afterAll(){
        postgres.stop();
    }


    @Test
    public void addPointDataTest() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/PointStrorage";


        String username = postgres.getUsername();
        String password = postgres.getPassword();

        TestDatabaseConnector databaseConnector = new TestDatabaseConnector(jdbcUrl, username, password);
        ResultsControllerBean resultsControllerBean = new ResultsControllerBean();
        resultsControllerBean.setDatabaseConnector(databaseConnector);


        resultsControllerBean.addResult(3.0, 2.0, 3.75, "dbTest");

        try (Connection connection = databaseConnector.connect()){
            PreparedStatement statement = connection.prepareStatement(DatabaseScripts.GET_TEST_POINT_DATA);
            statement.setDouble(1, 3.0);
            statement.setDouble(2, 2.0);
            statement.setDouble(3, 3.75);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                double x = resultSet.getDouble("xvalue");
                double y = resultSet.getDouble("yvalue");
                double r = resultSet.getDouble("rvalue");
                boolean result = resultSet.getBoolean("result");

                Assertions.assertEquals(3.0, x, 0.0);
                Assertions.assertEquals(2.0, y, 0.0);
                Assertions.assertEquals(3.75, r, 0.0);
                Assertions.assertTrue(result);
            } else {
                throw new AssertionError("No data found");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void clearDataTest() throws SQLException{
        String jdbcUrl = "jdbc:postgresql://localhost:5432/PointStrorage";

        boolean isEmpty = false;
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        TestDatabaseConnector databaseConnector = new TestDatabaseConnector(jdbcUrl, username, password);
        ResultsControllerBean resultsControllerBean = new ResultsControllerBean();
        resultsControllerBean.setDatabaseConnector(databaseConnector);


        try (Connection connection = databaseConnector.connect()) {
            Statement statement = connection.createStatement();
            statement.execute(DatabaseScripts.CLEAR_RESULTS);
            ResultSet resultSet = statement.executeQuery(DatabaseScripts.COUNT_ROWS);

           if (resultSet.next()){
               isEmpty = true;
           }

           Assertions.assertTrue(isEmpty, "The table should be empty");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void getAllResultsTest() throws SQLException{
        String jdbcUrl = "jdbc:postgresql://localhost:5432/PointStrorage";

        int count = 0;
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        TestDatabaseConnector databaseConnector = new TestDatabaseConnector(jdbcUrl, username, password);
        ResultsControllerBean resultsControllerBean = new ResultsControllerBean();
        resultsControllerBean.setDatabaseConnector(databaseConnector);

        resultsControllerBean.init();
        LinkedList<AreaCheckerBean> originalResults = resultsControllerBean.getResults();

        try (Connection connection = databaseConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(DatabaseScripts.COUNT_ROWS);
            if (resultSet.next()){
                count = resultSet.getInt(1);
            }

            Assertions.assertEquals(originalResults.size(), count);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
