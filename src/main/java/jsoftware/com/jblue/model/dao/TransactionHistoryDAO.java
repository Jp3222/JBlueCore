/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jsoftware.com.jblue.model.dto.TransactionHistoryDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class TransactionHistoryDAO {

    /**
     * Inserta un registro de auditoría de transacción en la base de datos.
     * Todas las columnas son estrictamente obligatorias y se asignan
     * directamente desde el DTO. Tras una inserción exitosa, el ID autogenerado
     * se inyecta en el DTO.
     *
     * @param connection Conexión activa dentro del flujo transaccional.
     * @param dto Objeto {@link TransactionHistoryDTO} con la información
     * completa de la transacción.
     * @return {@code true} si la inserción fue exitosa; {@code false} en caso
     * contrario.
     * @throws SQLException Si ocurre un error de sintaxis o de conexión en la
     * base de datos.
     */
    public boolean insert(JDBConnection connection, TransactionHistoryDTO dto) throws SQLException {
        if (dto == null || connection == null) {
            return false;
        }

        String sql = """
                     INSERT INTO program_history
                     (type_mov, module_id, affected_table, observation, status, committee_id, office_id, employee_id, date_register)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                     """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Asignación directa de todos los parámetros (todos obligatorios)
            ps.setInt(1, dto.getType_mov());
            ps.setInt(2, dto.getModule_id());
            ps.setInt(3, dto.getAffected_table());
            ps.setString(4, dto.getObservation());
            ps.setInt(5, dto.getStatus());
            ps.setInt(6, dto.getCommittee_id());
            ps.setInt(7, dto.getOffice_id());
            ps.setInt(8, dto.getEmployee_id());
            // Conversión de LocalDateTime a java.sql.Timestamp (revisando asignación obligatoria)
            if (dto.getDate_register() != null) {
                ps.setTimestamp(9, java.sql.Timestamp.valueOf(dto.getDate_register()));
            } else {
                ps.setTimestamp(9, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                // Recuperación e inyección del ID autogenerado
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

    /**
     * Actualiza el estado de una transacción existente asignándole el status 1.
     * Utiliza el ID presente en el DTO para identificar el registro objetivo.
     *
     * @param connection Conexión activa dentro del flujo transaccional.
     * @param dto Objeto {@link TransactionHistoryDTO} con el ID de la
     * transacción a actualizar.
     * @return {@code true} si la actualización modificó al menos una fila;
     * {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error de sintaxis o conexión en la base
     * de datos.
     */
    public boolean updateStatusActive(JDBConnection connection, TransactionHistoryDTO dto) throws SQLException {
        if (dto == null || connection == null || dto.getId() <= 0) {
            return false;
        }
        String sql = """
                     UPDATE program_history
                     SET status = 1
                     WHERE id = ?
                     """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(sql)) {
            ps.setInt(1, dto.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                dto.setStatus(1); // Mantiene sincronizado el estado del DTO en memoria
                return true;
            }
        }
        return false;
    }
}
