/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jsoftware.com.jblue.model.dto.DeviceHistoryDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class DeviceHistoryDAO {

    /**
     * Inserta un registro de auditoría de dispositivo en la base de datos.
     * Utiliza la fecha y datos del dispositivo presentes en el DTO para el
     * registro, y enriquece el objeto inyectándole el ID autogenerado tras la
     * inserción.
     *
     * @param dto Objeto {@link DeviceHistoryDTO} con la información capturada
     * del dispositivo.
     * @param conn Conexión compartida dentro de la transacción.
     * @return {@code true} si la inserción fue exitosa; {@code false} en caso
     * contrario.
     * @throws SQLException Si ocurre un error de sintaxis o conexión a la base
     * de datos.
     */
    public boolean insert(JDBConnection connection, DeviceHistoryDTO dto) throws SQLException {
        if (dto == null || connection == null) {
            return false;
        }

        String sql = """
                 INSERT INTO device_history
                 (transaction_id, history_id, instance_id, host_name, ip, db_user, date_register)
                 VALUES (?, ?, ?, ?, ?, ?, ?)
                 """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignación de parámetros numéricos y textuales capturados por el DTO
            if (dto.getTransaction_id() == 0) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, dto.getTransaction_id());
            }
            ps.setInt(2, dto.getHistory_id());
            ps.setInt(3, dto.getInstance_id());
            ps.setString(4, dto.getHost_name());
            ps.setString(5, dto.getIp());
            ps.setString(6, dto.getDb_user());
            ps.setString(7, dto.getDate_register());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                // Recuperación e inyección del ID autogenerado por la BD
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        dto.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        }

        return false;
    }
}
