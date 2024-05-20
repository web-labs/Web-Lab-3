package model;

import database.DatabaseConnector;
import database.DatabaseScripts;
import mbeans.MissPercentage;
import mbeans.MissPercentageMBean;
import mbeans.PointsCounter;
import mbeans.PointsCounterMBean;

import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.sql.*;
import java.util.LinkedList;
import java.util.Objects;


public class ResultsControllerBean implements Serializable {
    @Inject
    private XBean xBean;

    @Inject
    private YBean yBean;

    @Inject
    private RBean rBean;

    @Inject
    private DatabaseConnector databaseConnector;


    private LinkedList<AreaCheckerBean> results;
    @Inject
    private PointsCounterMBean pointsCounter;
    @Inject
    private MissPercentageMBean missPercentage;

    public ResultsControllerBean() {
        super();
        results = new LinkedList<>();
    }

    public LinkedList<AreaCheckerBean> getResults(){
        return results;
    }

    @PostConstruct
    public void init(){
        try (Connection connection = databaseConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(DatabaseScripts.GET_POINTS_DATA);

            while (resultSet.next()){
                AreaCheckerBean result = new AreaCheckerBean();
                result.setX(resultSet.getDouble("xValue"));
                result.setY(resultSet.getDouble("yValue"));
                result.setR(resultSet.getDouble("rValue"));
                result.setResult(resultSet.getBoolean("result"));
                results.add(result);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void setDatabaseConnector(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
    }

    public void setResults(LinkedList<AreaCheckerBean> results){
        this.results = results;
    }


    public void addResult(final double x, final double y, final double r, final String status){
        final AreaCheckerBean currentRes = new AreaCheckerBean();
        final boolean result = AreaChecker.isInArea(x, y, r);
        currentRes.setX(x);
        currentRes.setY(y);
        currentRes.setR(r);
        currentRes.setResult(result);
        System.out.println("curr res: " + result);
        System.out.println("all res: " + results);

        if (result) {
            pointsCounter.incrementTotalPoints();
        } else {
            pointsCounter.incrementTotalPoints();
            pointsCounter.incrementMissedPoints();
        }
//        missPercentage.getMissPercentage();

        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(DatabaseScripts.ADD_POINT_DATA)){

            statement.setDouble(1, x);
            statement.setDouble(2, y);
            statement.setDouble(3, r);
            statement.setBoolean(4, result);
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (!Objects.equals(status, "dbTest")){
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("drawPoint(" + x + ", " + y + ", " + r + ", " + result + ");");
        }
        results.addFirst(currentRes);

        System.out.println(results.toString());
    }

    public void clearResults(double rVal){
        results.clear();
        pointsCounter.resetCounts(results);
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("drawCoordsPlane(" + rVal + ");");

        try (Connection connection = databaseConnector.connect();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(DatabaseScripts.CLEAR_RESULTS);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateRValue(double newRValue){
        for (AreaCheckerBean point : results){
            point.setR(newRValue);
        }
    }

    public void generateRedrawScript(){
        for (AreaCheckerBean point : results){
            double x = point.getX();
            double y = point.getY();
            double r = point.getR();
            boolean result = AreaChecker.isInArea(x, y, r);
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("drawPoint(" + x + ", " + y + ", " + r + ", " + result + ");");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultsControllerBean that = (ResultsControllerBean) o;
        return Objects.equals(xBean, that.xBean) && Objects.equals(yBean, that.yBean) && Objects.equals(rBean, that.rBean) && Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xBean, yBean, rBean, results);
    }

    @Override
    public String toString() {
        return "ResultsControllerBean{" +
                "xBean=" + xBean +
                ", yBean=" + yBean +
                ", rBean=" + rBean +
                ", results=" + results +
                '}';
    }
}
