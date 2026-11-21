package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;
import jsoftware.com.jblue.model.dto.EmployeeUserDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 * DAO para la gestión de usuarios, credenciales y datos de contacto de
 * empleados.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 */
public class EmployeeUserDAO extends AbstractDAO {

    private static final String BASE_COLUMNS
            = "id, employee_id, office_id, user, password, description, "
            + "email, phone_number, employee_type, status, last_employee_update, "
            + "last_update_password, date_update, date_register, date_end";

    public EmployeeUserDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un nuevo usuario de empleado y enriquece el DTO con el ID
     * autogenerado.
     */
    public boolean insert(JDBConnection connection, EmployeeUserDTO dto) {
        boolean success = false;
        String sql = "INSERT INTO emp_user ("
                + "employee_id, office_id, user, password, description, "
                + "email, phone_number, employee_type, status, last_employee_update, "
                + "last_update_password, date_register, date_end"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getEmployee_id());
            pstmt.setInt(2, dto.getOffice_id());
            pstmt.setString(3, dto.getUser());
            pstmt.setString(4, dto.getPassword());
            pstmt.setString(5, dto.getDescription());
            pstmt.setString(6, dto.getEmail());
            pstmt.setString(7, dto.getPhone_number());
            pstmt.setInt(8, dto.getEmployee_type());
            pstmt.setInt(9, dto.getStatus());
            pstmt.setInt(10, dto.getLast_employee_update());

            setTimestampOrNull(pstmt, 11, dto.getLast_update_password());

            LocalDateTime registerDate = dto.getDate_register() != null ? dto.getDate_register() : LocalDateTime.now();
            pstmt.setTimestamp(12, Timestamp.valueOf(registerDate));
            setTimestampOrNull(pstmt, 13, dto.getDate_end());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }
        } catch (SQLException e) {
            System.getLogger(EmployeeUserDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al insertar EmployeeUserDTO", e);
            success = false;
        }
        return success;
    }

    /**
     * Actualiza las credenciales (password), el empleado que realizó el cambio
     * y la marca de tiempo last_update_password por id.
     */
    public boolean updateCredential(JDBConnection connection, EmployeeUserDTO dto) {
        boolean success = false;
        String sql = "UPDATE emp_user SET "
                + "password = ?, "
                + "last_employee_update = ?, "
                + "last_update_password = ? "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setString(1, dto.getPassword());
            pstmt.setInt(2, dto.getLast_employee_update());

            LocalDateTime passUpdate = dto.getLast_update_password() != null
                    ? dto.getLast_update_password()
                    : LocalDateTime.now();
            pstmt.setTimestamp(3, Timestamp.valueOf(passUpdate));
            pstmt.setInt(4, dto.getId());

            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            System.getLogger(EmployeeUserDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al actualizar credenciales de EmployeeUserDTO", e);
            success = false;
        }
        return success;
    }

    /**
     * Busca y retorna el registro de usuario de empleado filtrado por nombre de
     * usuario y oficina.
     */
    public Optional<EmployeeUserDTO> get(JDBConnection connection, int officeId, String user) {
        String sql = "SELECT * FROM emp_user "
                + "WHERE user = ? AND office_id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setInt(2, officeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.getLogger(EmployeeUserDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al consultar EmployeeUserDTO por usuario y oficina", e);
        }
        return Optional.empty();
    }

    /**
     * Actualiza la información de contacto (email, phone_number y description)
     * y el último empleado modificador buscando por id.
     */
    public boolean update(JDBConnection connection, EmployeeUserDTO dto) {
        boolean success = false;
        String sql = "UPDATE emp_user SET "
                + "email = ?, "
                + "phone_number = ?, "
                + "description = ?, "
                + "last_employee_update = ? "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setString(1, dto.getEmail());
            pstmt.setString(2, dto.getPhone_number());
            pstmt.setString(3, dto.getDescription());
            pstmt.setInt(4, dto.getLast_employee_update());
            pstmt.setInt(5, dto.getId());

            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            System.getLogger(EmployeeUserDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al actualizar información de contacto de EmployeeUserDTO", e);
            success = false;
        }
        return success;
    }

    private EmployeeUserDTO mapRow(ResultSet rs) throws SQLException {
        EmployeeUserDTO dto = new EmployeeUserDTO();
        dto.setId(rs.getInt("id"));
        dto.setEmployee_id(rs.getInt("employee_id"));
        dto.setOffice_id(rs.getInt("office_id"));
        dto.setUser(rs.getString("user"));
        dto.setPassword(rs.getString("password"));
        dto.setDescription(rs.getString("description"));
        dto.setEmail(rs.getString("email"));
        dto.setPhone_number(rs.getString("phone_number"));
        dto.setEmployee_type(rs.getInt("employee_type"));
        dto.setStatus(rs.getInt("status"));
        dto.setLast_employee_update(rs.getInt("last_employee_update"));
        dto.setLast_update_password(toLocalDateTime(rs.getTimestamp("last_update_password")));
        dto.setDate_update(toLocalDateTime(rs.getTimestamp("date_update")));
        dto.setDate_register(toLocalDateTime(rs.getTimestamp("date_register")));
        dto.setDate_end(toLocalDateTime(rs.getTimestamp("date_end")));
        return dto;
    }

    private void setTimestampOrNull(PreparedStatement pstmt, int index, LocalDateTime value) throws SQLException {
        if (value != null) {
            pstmt.setTimestamp(index, Timestamp.valueOf(value));
        } else {
            pstmt.setNull(index, Types.TIMESTAMP);
        }
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
