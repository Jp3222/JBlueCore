/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.service;

import java.io.Serializable;
import java.sql.SQLException;
import jsoftware.com.jblue.model.dao.TransactionHistoryDAO;
import jsoftware.com.jblue.model.dto.TransactionHistoryDTO;
import jsoftware.com.jblue.model.exp.ServiceException;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class TransactionHistoryService implements Serializable {

    private TransactionHistoryDAO dao;

    public TransactionHistoryService(boolean dev_flag, String process_name) {
        this.dao = new TransactionHistoryDAO();
    }

    public boolean insert(JDBConnection connection, SystemSession ss, TransactionHistoryDTO dto) throws SQLException, ServiceException {
        if (connection == null || dto == null) {
            return false;
        }
        boolean ok = false;
        try {
            connection.setAutoCommit(false);

            ok = dao.insert(connection, dto);
            if (!ok) {
                throw new ServiceException(1, "TRANSACCION NO REGISTRADA");
            }

            connection.commit();
            return true;
        } catch (SQLException | ServiceException e) {
            connection.rollBack();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean update(JDBConnection connection, SystemSession ss, TransactionHistoryDTO dto) throws SQLException, ServiceException {
        if (connection == null || dto == null) {
            return false;
        }
        boolean res = dao.updateStatusActive(connection, dto);
        if (!res) {
            throw new ServiceException(1, "TRANSACCION NO REGISTRADA");
        }
        return true;
    }
}
