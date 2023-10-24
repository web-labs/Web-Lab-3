package database;

public class DatabaseScripts {

    public final static String GET_POINTS_DATA = "SELECT * FROM point_model";

    public final static String COUNT_ROWS = "SELECT COUNT(*) FROM point_model";

    public final static String GET_TEST_POINT_DATA = "SELECT * FROM point_model WHERE xvalue = ? AND yvalue = ? AND rvalue = ?";

    public final static String CLEAR_RESULTS = "DELETE FROM point_model";

    public static final String ADD_POINT_DATA = "INSERT INTO point_model (xvalue, yvalue, rvalue, result) VALUES (?, ?, ?, ?)";
}
