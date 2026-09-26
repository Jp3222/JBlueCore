/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.service;

import java.sql.SQLException;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.model.dao.SessionDAO;
import jsoftware.com.jblue.model.exp.SystemException;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jblue.sys.app.AppConfig;
import jsoftware.com.jblue.utils.Func;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class SystemSessionService extends AbstractService {

    private static SystemSessionService instance;

    public static synchronized SystemSessionService getInstance(boolean dev_flag, String process_name) {
        if (instance == null) {
            instance = new SystemSessionService(dev_flag, process_name);
        }
        return instance;
    }

    private static final long serialVersionUID = 1L;
    private SessionDAO session_dao;

    public SystemSessionService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
    }

    public void valid(JDBConnection connection, SystemSession ss) throws SystemException, SQLException {
        boolean res = AppConfig.getParameterBoolean(connection, "SOLO_LECTURA");
        if (res) {
            throw new SystemException(1, "EL SISTEMA ESTA EN MODO LECTURA");
        }
        res = !ss.isOpen();
        if (res) {
            throw new SystemException(2, "SESION NO ABIERTA");
        }
        res = Func.isNull(ss.getCurrentSession());
        if (res) {
            throw new SystemException(3, "El objeto de session actual no ha sido registrado correctamente. No podrá realizar ningún registro administrativo.\n");
        }
        res = session_dao.isSessionClose(connection, ss.getCurrentSession());
        if (res) {
            throw new SystemException(4, "SESION CERRADA");
        }
        res = Func.isNull(ss.getCurrentEmployee());
        if (res) {
            throw new SystemException(5, "El usuario no se ha registrado correctamente. No podrá realizar algunos registros.\n");
        }
        res = AppConfig.getParameterBoolean(connection, "VALIDA_ADMINISTRACIO") && Func.isNull(ss.getCurrentAdministration());
        if (res) {
            throw new SystemException(6, "La administración actual no ha sido registrada. No podrá realizar ningún registro administrativo.\n");
        }
    }

}
