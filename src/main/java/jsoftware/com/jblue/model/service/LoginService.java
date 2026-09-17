/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.service;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.model.cryp.BCryptCrypto;
import jsoftware.com.jblue.model.cryp.DeterministicCrypto;
import jsoftware.com.jblue.model.dao.AdministrationHistoryDAO;
import jsoftware.com.jblue.model.dao.EmployeeUserDAO;
import jsoftware.com.jblue.model.dao.SessionDAO;
import jsoftware.com.jblue.model.dto.AdministrationHistoryDTO;
import jsoftware.com.jblue.model.dto.EmployeeUserDTO;
import jsoftware.com.jblue.model.dto.ProgramHistoryDTO;
import jsoftware.com.jblue.model.dto.SessionDTO;
import jsoftware.com.jblue.model.exp.DataAccesObjectException;
import jsoftware.com.jblue.model.exp.ServiceException;
import jsoftware.com.jblue.model.exp.service.LoginFailedException;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jblue.sys.app.AppFiles;
import jsoftware.com.jblue.utils.Func;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.util.FuncLogs;

/**
 * Servicio encargado de la validación criptográfica de accesos, inicialización
 * y cierre de sesiones de usuario en JBlue.
 *
 * * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-06-12
 * @version 2.0
 */
public class LoginService extends AbstractService {

    private static final long serialVersionUID = 1L;

    private SystemSession system_session;
    private final EmployeeUserDAO employee_dao;
    private final AdministrationHistoryDAO administration_history_dao;
    private final SessionDAO session_dao;
    private final HistoryService hys;
    // Constante del sistema para el cifrado determinista del usuario (Pimienta Global)
    private static final String SYSTEM_PEPPER = "JBl_u3#Pozo$2026_MasterKey";

    public LoginService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        system_session = SystemSession.getInstancia();
        employee_dao = new EmployeeUserDAO(dev_flag, process_name);
        administration_history_dao = new AdministrationHistoryDAO(dev_flag, process_name);
        session_dao = new SessionDAO(dev_flag, process_name);
        hys = new HistoryService(dev_flag, process_name);
    }

    public boolean login(JDBConnection connection, SystemSession ss, String user, String password) {
        if (connection == null || ss == null || user == null || password == null) {
            return returnMessageError("PARÁMETROS DE ENTRADA INVÁLIDOS");
        }

        boolean res = false;
        SessionDTO dto = new SessionDTO();

        try {
            // [0] Iniciar control transaccional
            connection.setAutoCommit(false);

            // [1] ENCRIPTAR EL USUARIO DE FORMA DETERMINISTA PARA LA BÚSQUEDA INDEXADA
            String secureUser = DeterministicCrypto.encryp(user, SYSTEM_PEPPER);
            Optional<EmployeeUserDTO> option = employee_dao.get(connection, secureUser);

            if (option.isEmpty()) {
                log("INTENTO DE INICIO DE SESION FALLIDO (USUARIO INEXISTENTE): USUARIO=%s".formatted(user));
                rollback(connection);
                return returnMessageError("USUARIO O CONTRASEÑA INCORRECTOS");
            }

            EmployeeUserDTO employee = option.get();

            // [2] VALIDACIÓN DE LA CONTRASEÑA USANDO BCRYPT
            String dbPasswordHash = employee.getPassword();
            boolean isPasswordCorrect = BCryptCrypto.equalsEncryp(password, dbPasswordHash, null);

            if (!isPasswordCorrect) {
                log("INTENTO DE INICIO DE SESION FALLIDO (CONTRASEÑA ERRÓNEA): USUARIO=%s".formatted(user));
                rollback(connection);
                return returnMessageError("USUARIO O CONTRASEÑA INCORRECTOS");
            }

            // Limpiar contraseña de memoria por seguridad
            employee.put("password", null);

            // ASIGNAR EMPLEADO A LA SESIÓN LOCAL Y AL DTO
            ss.setCurrentEmployee(employee);
            dto.put("employee_id", employee.getId());

            // Control de concurrencia de sesiones activas
            res = session_dao.haveActiveSession(connection, employee.getId());
            if (res) {
                ProgramHistoryDTO hys_close_open_session = ss.getProgramHistoryDTO(132, Integer.parseInt(employee.getId()));
                hys_close_open_session.setDescription("CERRAR SESION ABIERTA AUTOMÁTICAMENTE POR NUEVA AUTENTICACIÓN");

                res = hys.logout(connection, ss, hys_close_open_session);
                if (!res) {
                    throw new LoginFailedException(2, "FALLO AL CERRAR SESION ANTERIOR");
                }

                dto.put("history_end_id", String.valueOf(hys_close_open_session.getId()));
                res = session_dao.updateStatus(connection, dto);
                if (!res) {
                    throw new LoginFailedException(3, "FALLO AL ACTUALIZAR ESTADO DE SESION ANTERIOR");
                }
            }
            // [5] CARGA DE INFORMACIÓN DE SESIÓN Y CONTEXTO
            String db_user = hys.currentUser(connection);
            AdministrationHistoryDTO currentAdministration = administration_history_dao.getCurrentAdministration(connection);

            ss.setCurrentAdministration(currentAdministration);
            ss.setCurrentDbUser(db_user);
            ss.setCurrentSession(dto);

            // Validaciones defensivas de consistencia sobre el parámetro 'ss' (evita acoplamiento con Singleton)
            if (!Func.isNotNull(ss.getCurrentEmployee())) {
                throw new LoginFailedException(4, "EL USUARIO DE SESION NO SE GUARDO CORRECTAMENTE");
            }
            if (!Func.isNotNull(ss.getCurrentDbUser())) {
                throw new LoginFailedException(6, "ERROR DE SERVIDOR EN DB USER");
            }
            if (!Func.isNotNull(ss.getCurrent_instance())) {
                throw new LoginFailedException(7, "CREDENCIALES DE ADMINISTRACION NO VALIDAS");
            }

            ss.put("user-session", secureUser);

            // [3] REGISTRO EN BITACORA DE HISTORIAL
            ProgramHistoryDTO hys_session = ss.getProgramHistoryDTO(132, Integer.parseInt(employee.getId()));
            hys_session.setDescription("INICIO DE SESIÓN");

            res = hys.login(connection, ss, hys_session);
            if (!res) {
                throw new LoginFailedException(1, "FALLO AL REGISTRAR HISTORIAL DE SESION");
            }
            dto.put("history_start_id", String.valueOf(hys_session.getId()));

            // [4] REGISTRO DE SESION EN INFRAESTRUCTURA (Validación estricta del insert)
            res = session_dao.insert(connection, dto) > 0;
            if (!res) {
                throw new LoginFailedException(5, "FALLO AL CREAR LA SESION EN INFRAESTRUCTURA");
            }

            log("INICIO DE SESION EXITOSO: USUARIO=%s".formatted(secureUser));
            // Confirmar la transacción completa
            commit(connection);
            returnMessageError(SERVICE_EXECUTE_OK, "INICIO DE SESIÓN EXITOSO");
            return true;
        } catch (SQLException | DataAccesObjectException ex) {
            rollback(connection);
            log(ex, "login");
            res = returnMessageError("INICIO DE SESION FALLIDO");
        } catch (ServiceException ex) {
            rollback(connection);
            res = returnMessageError(ex.getUserMessage());
        } catch (UnknownHostException ex) {
            rollback(connection);
            res = returnMessageError(ex.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
        return res;
    }

    public boolean logout(JDBConnection connection, SystemSession ss) {
        if (connection == null || ss == null) {
            return returnMessageError("PARÁMETROS DE ENTRADA INVÁLIDOS");
        }

        SessionDTO session_dto = ss.getCurrentSession();
        Object userSessionObj = ss.get("user-session");

        if (session_dto == null || userSessionObj == null) {
            SystemSession.setNull();
            return returnMessageError("NO EXISTE UNA SESIÓN ACTIVA PARA CERRAR");
        }

        String user = userSessionObj.toString();
        boolean res = false;

        try {
            // [1] Iniciar transacción atómica
            connection.setAutoCommit(false);
            ProgramHistoryDTO hys_logout_session = ss.getProgramHistoryDTO(132, Integer.parseInt(ss.getCurrentEmployee().getId()));
            hys_logout_session.setDescription("FIN DE SESION");
            // [2] Registrar cierre en el historial de bitácora
            res = hys.logout(connection, ss, hys_logout_session);
            if (!res) {
                throw new LoginFailedException(1, "ERROR AL REGISTRAR EL FIN DE LA SESIÓN EN BITÁCORA");
            }

            // [3] Actualizar el estado de la sesión en base de datos
            session_dto.put("history_end_id", String.valueOf(hys_logout_session.getId()));
            res = session_dao.updateStatus(connection, session_dto);
            if (!res) {
                throw new LoginFailedException(2, "ERROR AL ACTUALIZAR EL ESTADO DE LA SESIÓN EN INFRAESTRUCTURA");
            }

            // [4] Confirmar cambios atómicos en la base de datos
            commit(connection);

            // [5] Destruir la sesión en RAM únicamente tras el commit exitoso
            SystemSession.setNull();
            log("FIN DE SESION EXITOSO: USUARIO=%s".formatted(user));

            returnMessageError(SERVICE_EXECUTE_OK, "FIN DE SESIÓN EXITOSO");
            return true;
        } catch (SQLException | DataAccesObjectException ex) {
            rollback(connection);
            log(ex, "logout");
            return returnMessageError("FIN DE SESIÓN FALLIDO EN BASE DE DATOS");
        } catch (ServiceException ex) {
            rollback(connection);
            log(ex, "logout");
            return returnMessageError(ex.getUserMessage());
        } catch (UnknownHostException ex) {
            rollback(connection);
            log(ex, "logout");
            return returnMessageError(ex.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void log(Exception e, String method_name) {
        FuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, getClass(), e, getProcess_name(), method_name, e.getMessage());
    }

    public void log(String message) {
        FuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, "MAIN", message);
    }
}
