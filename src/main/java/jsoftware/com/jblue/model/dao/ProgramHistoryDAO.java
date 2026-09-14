/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import jsoftware.com.jblue.model.dto.ProgramHistoryDTO;
import jsoftware.com.jblue.model.exp.imp.CorruptInsertionException;
import jsoftware.com.jblue.model.exp.imp.KeyNotGenerateException;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 *
 * @author juanp
 */
public class ProgramHistoryDAO extends AbstractDAO {

    public ProgramHistoryDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    public boolean insert(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 1);
    }

    public boolean update(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 2);
    }

    public boolean select(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 4);
    }

    public boolean exports(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 5);
    }

    public boolean imports(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 6);
    }

    public boolean login(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 7);
    }

    public boolean logout(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 8);
    }

    public boolean delete(JDBConnection connection, ProgramHistoryDTO dto) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        return insert(connection, dto, 9);
    }

    private boolean insert(JDBConnection connection, ProgramHistoryDTO dto, int type_mov) throws SQLException, CorruptInsertionException, KeyNotGenerateException {
        boolean res = false;
        String query = """
                       INSERT INTO hys_program_history 
                       (transaction_id, type_mov, affected_table, enty_id, description, db_user, committee_id, office_id, employee_id) 
                       VALUES(?,?,?,?,?,USER(),?,?,?)
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            if (res) {
                ps.setInt(1, dto.getTransaction_id());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setInt(2, dto.getType_mov());
            ps.setInt(3, dto.getAffected_table());
            ps.setInt(4, dto.getEnty_id());
            ps.setString(5, dto.getDescription());
            ps.setInt(6, dto.getCommittee_id());
            ps.setInt(7, dto.getOffice_id());
            ps.setInt(8, dto.getEmployee_id());
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new CorruptInsertionException();
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                res = rs.next();
                if (!res) {
                    throw new KeyNotGenerateException();
                }
                dto.setId(rs.getInt(1));
                res = true;
            }
        }
        return res;
    }

}
