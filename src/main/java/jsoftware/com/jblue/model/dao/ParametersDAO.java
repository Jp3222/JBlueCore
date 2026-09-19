/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import jsoftware.com.jblue.utils.Func;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 *
 * @author juanp
 */
public class ParametersDAO extends AbstractDAO {

    private static ParametersDAO instance = null;

    public static ParametersDAO getInstance() {
        return instance;
    }

    public synchronized static ParametersDAO getInstance(boolean flag_dev_log, String name_module) {
        if (Func.isNull(instance)) {
            instance = new ParametersDAO(flag_dev_log, name_module);
        }
        return instance;
    }

    private ParametersDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    public Object getValue(JDBConnection connection, String parameter) throws SQLException {
        Object res = null;
        String query = """
                       SELECT value, data_type FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    switch (rs.getString("data_type")) {
                        case "TEXT" ->
                            res = rs.getString("value");
                        case "BOOL" ->
                            res = rs.getBoolean("value");
                        case "INT" ->
                            res = rs.getInt("value");
                        case "DATE" ->
                            res = rs.getDate("value");
                        default ->
                            throw new AssertionError();
                    }
                }
            }
        }
        return res;
    }

    public Object getObject(JDBConnection connection, String parameter) throws SQLException {
        Object res = null;
        String query = """
                       SELECT value, data_type FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = rs.getObject(parameter);
                }
            }
        }
        return res;
    }

    public String getString(JDBConnection connection, String parameter) throws SQLException {
        String res = null;
        String query = """
                       SELECT value FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = rs.getString("value");
                }
            }
        }
        return res;
    }

    public boolean getBoolean(JDBConnection connection, String parameter) throws SQLException {
        boolean res = false;
        String query = """
                       SELECT value FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = rs.getBoolean("value");
                }
            }
        }
        return res;
    }

    public LocalDate getLocalDate(JDBConnection connection, String parameter) throws SQLException {
        LocalDate res = null;
        String query = """
                       SELECT value FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = LocalDate.parse(rs.getString("value"), DateTimeFormatter.ISO_DATE);
                }
            }
        }
        return res;
    }

    public LocalTime getLocalTime(JDBConnection connection, String parameter) throws SQLException {
        LocalTime res = null;
        String query = """
                       SELECT value FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = LocalTime.parse(rs.getString("value"), DateTimeFormatter.ISO_TIME);
                }
            }
        }
        return res;
    }

    public LocalDateTime getLocalDateTime(JDBConnection connection, String parameter) throws SQLException {
        LocalDateTime res = null;
        String query = """
                       SELECT value FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = LocalDateTime.parse(rs.getString("value"), DateTimeFormatter.ISO_DATE_TIME);
                }
            }
        }
        return res;
    }

    public int getInt(JDBConnection connection, String parameter) throws SQLException {
        int res = 0;
        String query = """
                       SELECT value FROM dev_parameters WHERE parameter = ? AND status = 1;
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = rs.getInt("value");
                }
            }
        }
        return res;
    }

}
