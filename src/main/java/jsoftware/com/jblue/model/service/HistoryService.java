package jsoftware.com.jblue.model.service;

import java.net.UnknownHostException;
import java.sql.SQLException;
import jsoftware.com.jblue.model.dao.DeviceHistoryDAO;
import jsoftware.com.jblue.model.dao.ProgramHistoryDAO;
import jsoftware.com.jblue.model.dto.DeviceHistoryDTO;
import jsoftware.com.jblue.model.dto.ProgramHistoryDTO;
import jsoftware.com.jblue.model.exp.ServiceException;
import jsoftware.com.jblue.model.exp.imp.CorruptInsertionException;
import jsoftware.com.jblue.model.exp.imp.KeyNotGenerateException;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jutil.db.JDBConnection;

/**
 * Servicio encargado de orquestar el registro de bitácora general del sistema y
 * la auditoría del dispositivo cliente.
 *
 * @author juanp
 */
public class HistoryService {

    private final ProgramHistoryDAO history_dao;
    private final DeviceHistoryDAO device_dao;

    public HistoryService(boolean flag_dev_log, String name_module) {
        this.history_dao = new ProgramHistoryDAO(flag_dev_log, name_module);
        this.device_dao = new DeviceHistoryDAO();
    }

    /**
     * Inserta en una misma secuencia transaccional la bitácora del programa y
     * el historial de auditoría del dispositivo.
     *
     * @param connection Enlace a la conexión de base de datos activa.
     * @param ss Sesión actual del sistema para extraer contexto de
     * hardware/red.
     * @param dto Objeto DTO con los datos del historial de programa.
     * @return {@code true} si ambos registros se completaron correctamente.
     * @throws ServiceException Si alguna inserción falla o los datos resultan
     * corruptos.
     * @throws SQLException Si ocurre un fallo de persistencia en la BD.
     * @throws UnknownHostException Si falla la resolución de red del
     * dispositivo.
     */
    public boolean insert(JDBConnection connection, SystemSession ss, ProgramHistoryDTO dto)
            throws ServiceException, SQLException, UnknownHostException, CorruptInsertionException, KeyNotGenerateException {

        if (connection == null || ss == null || dto == null) {
            throw new ServiceException(0, "PARAMETROS DE HISTORIAL INVALIDOS");
        }

        // 1. Inserción en bitácora de programa
        boolean res = history_dao.insert(connection, dto);
        if (!res) {
            throw new ServiceException(1, "REGISTRO EN BITACORA CORRUPTO O NO REALIZADO");
        }

        // 2. Construcción e inserción en historial de dispositivo
        DeviceHistoryDTO dev = ss.getNewDeviceHistoryDTO();
        dev.setTransaction_id(dto.getTransaction_id());
        dev.setHistory_id(dto.getId());

        res = device_dao.insert(connection, dev);
        if (!res) {
            throw new ServiceException(2, "REGISTRO DEL DISPOSITIVO CORRUPTO O NO REALIZADO");
        }

        return true;
    }

    public boolean update(JDBConnection connection, SystemSession ss, ProgramHistoryDTO dto)
            throws ServiceException, SQLException, UnknownHostException, CorruptInsertionException, KeyNotGenerateException {

        if (connection == null || ss == null || dto == null) {
            throw new ServiceException(0, "PARAMETROS DE HISTORIAL INVALIDOS");
        }

        // 1. Inserción en bitácora de programa
        boolean res = history_dao.update(connection, dto);
        if (!res) {
            throw new ServiceException(1, "REGISTRO EN BITACORA CORRUPTO O NO REALIZADO");
        }

        // 2. Construcción e inserción en historial de dispositivo
        DeviceHistoryDTO dev = ss.getNewDeviceHistoryDTO();
        dev.setTransaction_id(dto.getTransaction_id());
        dev.setHistory_id(dto.getId());

        res = device_dao.insert(connection, dev);
        if (!res) {
            throw new ServiceException(2, "REGISTRO DEL DISPOSITIVO CORRUPTO O NO REALIZADO");
        }

        return true;
    }

    public boolean delete(JDBConnection connection, SystemSession ss, ProgramHistoryDTO dto)
            throws ServiceException, SQLException, UnknownHostException, CorruptInsertionException, KeyNotGenerateException {

        if (connection == null || ss == null || dto == null) {
            throw new ServiceException(0, "PARAMETROS DE HISTORIAL INVALIDOS");
        }

        // 1. Inserción en bitácora de programa
        boolean res = history_dao.delete(connection, dto);
        if (!res) {
            throw new ServiceException(1, "REGISTRO EN BITACORA CORRUPTO O NO REALIZADO");
        }

        // 2. Construcción e inserción en historial de dispositivo
        DeviceHistoryDTO dev = ss.getNewDeviceHistoryDTO();
        dev.setTransaction_id(dto.getTransaction_id());
        dev.setHistory_id(dto.getId());

        res = device_dao.insert(connection, dev);
        if (!res) {
            throw new ServiceException(2, "REGISTRO DEL DISPOSITIVO CORRUPTO O NO REALIZADO");
        }

        return true;
    }

    public boolean login(JDBConnection connection, SystemSession ss, ProgramHistoryDTO dto)
            throws ServiceException, SQLException, UnknownHostException, CorruptInsertionException, KeyNotGenerateException {

        if (connection == null || ss == null || dto == null) {
            throw new ServiceException(0, "PARAMETROS DE HISTORIAL INVALIDOS");
        }

        // 1. Inserción en bitácora de programa
        boolean res = history_dao.login(connection, dto);
        if (!res) {
            throw new ServiceException(1, "REGISTRO EN BITACORA CORRUPTO O NO REALIZADO");
        }

        // 2. Construcción e inserción en historial de dispositivo
        DeviceHistoryDTO dev = ss.getNewDeviceHistoryDTO();
        dev.setTransaction_id(dto.getTransaction_id());
        dev.setHistory_id(dto.getId());

        res = device_dao.insert(connection, dev);
        if (!res) {
            throw new ServiceException(2, "REGISTRO DEL DISPOSITIVO CORRUPTO O NO REALIZADO");
        }

        return true;
    }

    public boolean logout(JDBConnection connection, SystemSession ss, ProgramHistoryDTO dto)
            throws ServiceException, SQLException, UnknownHostException, CorruptInsertionException, KeyNotGenerateException {

        if (connection == null || ss == null || dto == null) {
            throw new ServiceException(0, "PARAMETROS DE HISTORIAL INVALIDOS");
        }

        // 1. Inserción en bitácora de programa
        boolean res = history_dao.logout(connection, dto);
        if (!res) {
            throw new ServiceException(1, "REGISTRO EN BITACORA CORRUPTO O NO REALIZADO");
        }

        // 2. Construcción e inserción en historial de dispositivo
        DeviceHistoryDTO dev = ss.getNewDeviceHistoryDTO();
        dev.setTransaction_id(dto.getTransaction_id());
        dev.setHistory_id(dto.getId());

        res = device_dao.insert(connection, dev);
        if (!res) {
            throw new ServiceException(2, "REGISTRO DEL DISPOSITIVO CORRUPTO O NO REALIZADO");
        }

        return true;
    }

    public String currentUser(JDBConnection connection) throws SQLException {
        return history_dao.getCurrentDBUser(connection);
    }
}
