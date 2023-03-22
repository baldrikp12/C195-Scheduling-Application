package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.DataCache;
import kbaldr2.model.ReportTypeMonth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDAO {

    private static final ObservableList<ReportTypeMonth> reportData = FXCollections.observableArrayList();
    protected static Connection connection;
    /**
     * Constructs a ReportDAO with the specified database connection.
     *
     * @param connection The database connection to use
     */
    public ReportDAO(Connection connection) {

        this.connection = connection;
    }

    /**
     * Retrieves all records of type T from the database.s
     *
     * @return An ObservableList containing all records of type T
     * @throws SQLException If there is an issue executing the query
     */
    public ObservableList<ReportTypeMonth> getAll() throws SQLException {

        String sql = "SELECT type, MONTH(start) AS month, COUNT(*) AS total FROM appointments GROUP BY type, month;";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            int Month = rs.getInt("month");
            String type = rs.getString("type");
            int total = rs.getInt("total");
            ReportTypeMonth report = new ReportTypeMonth(Month, type, total);
            reportData.add(report);
        }

        return reportData;
    }


}
