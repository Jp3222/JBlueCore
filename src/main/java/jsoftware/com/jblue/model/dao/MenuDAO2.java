package jsoftware.com.jblue.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import jsoftware.com.jblue.model.dto.MenuDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

public class MenuDAO2 extends AbstractDAO {

    public MenuDAO2(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un nuevo registro de menú en la base de datos.
     */
    public boolean insert(JDBConnection connection, MenuDTO dto) throws SQLException {
        String sql = "INSERT INTO app_cat_menu (parent_id, module_id, name, level, action_command, "
                + "is_menu, is_windows, is_root_menu, process_file, last_employee_update, status, "
                + "date_update, date_register, date_end) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, dto.getParent_id());
            pstmt.setInt(2, dto.getModule_id());
            pstmt.setString(3, dto.getName());
            pstmt.setInt(4, dto.getLevel());
            pstmt.setString(5, dto.getAction_command());
            pstmt.setBoolean(6, dto.isIs_menu());
            pstmt.setBoolean(7, dto.isIs_windows());
            pstmt.setBoolean(8, dto.isIs_root_menu());
            pstmt.setString(9, dto.getProcess_file());
            pstmt.setInt(10, dto.getLast_employee_update());
            pstmt.setInt(11, dto.getStatus());

            pstmt.setTimestamp(12, dto.getDate_update() != null ? Timestamp.valueOf(dto.getDate_update()) : null);
            pstmt.setTimestamp(13, dto.getDate_register() != null ? Timestamp.valueOf(dto.getDate_register()) : null);
            pstmt.setTimestamp(14, dto.getDate_end() != null ? Timestamp.valueOf(dto.getDate_end()) : null);

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza un registro de menú existente basándose en su ID.
     */
    public boolean update(JDBConnection connection, MenuDTO dto) throws SQLException {
        String sql = "UPDATE app_cat_menu SET parent_id = ?, module_id = ?, name = ?, level = ?, "
                + "action_command = ?, is_menu = ?, is_windows = ?, is_root_menu = ?, "
                + "process_file = ?, last_employee_update = ?, status = ?, date_update = ?, "
                + "date_register = ?, date_end = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, dto.getParent_id());
            pstmt.setInt(2, dto.getModule_id());
            pstmt.setString(3, dto.getName());
            pstmt.setInt(4, dto.getLevel());
            pstmt.setString(5, dto.getAction_command());
            pstmt.setBoolean(6, dto.isIs_menu());
            pstmt.setBoolean(7, dto.isIs_windows());
            pstmt.setBoolean(8, dto.isIs_root_menu());
            pstmt.setString(9, dto.getProcess_file());
            pstmt.setInt(10, dto.getLast_employee_update());
            pstmt.setInt(11, dto.getStatus());

            pstmt.setTimestamp(12, dto.getDate_update() != null ? Timestamp.valueOf(dto.getDate_update()) : null);
            pstmt.setTimestamp(13, dto.getDate_register() != null ? Timestamp.valueOf(dto.getDate_register()) : null);
            pstmt.setTimestamp(14, dto.getDate_end() != null ? Timestamp.valueOf(dto.getDate_end()) : null);
            pstmt.setInt(15, dto.getId());

            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<MenuDTO> selectByIsRootMenu(JDBConnection connection) throws SQLException {
        List<MenuDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM app_cat_menu WHERE is_menu = 1 AND is_root_menu = 1 AND is_windows = 0 AND status = 1";
        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MenuDTO dto = mapResultSetToDTO(rs);
                    System.out.println(dto.toString());
                    lista.add(dto);
                }
            }
        }
        return lista;
    }

    /**
     *
     * @param connection
     * @param parentId
     * @return
     * @throws SQLException
     */
    public List<MenuDTO> selectByParentId(JDBConnection connection, int parentId) throws SQLException {
        List<MenuDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM app_cat_menu WHERE parent_id = ? AND is_windows = 0 AND is_root_menu = 0 AND status = 1";

        try (PreparedStatement ps = connection.getNewPreparedStatement(sql)) {
            if (parentId == 0 || parentId == -1) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, parentId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MenuDTO dto = mapResultSetToDTO(rs);
                    System.out.println(dto.toString());
                    lista.add(dto);
                }
            }
        }
        return lista;
    }

    /**
     * Método auxiliar privado para mapear el ResultSet completo a tu MenuDTO,
     * respetando la estructura y nombres de tus atributos.
     */
    private MenuDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        MenuDTO dto = new MenuDTO();
        dto.setId(rs.getInt("id"));
        dto.setParent_id(rs.getInt("parent_id"));
        dto.setModule_id(rs.getInt("module_id"));
        dto.setName(rs.getString("name"));
        dto.setLevel(rs.getInt("level"));
        dto.setAction_command(rs.getString("action_command"));
        dto.setIs_menu(rs.getBoolean("is_menu"));
        dto.setIs_windows(rs.getBoolean("is_windows"));
        dto.setIs_root_menu(rs.getBoolean("is_root_menu"));
        dto.setProcess_file(rs.getString("process_file"));
        dto.setLast_employee_update(rs.getInt("last_employee_update"));
        dto.setStatus(rs.getInt("status"));
        Timestamp tsUpdate = rs.getTimestamp("date_update");
        if (tsUpdate != null) {
            dto.setDate_update(tsUpdate.toLocalDateTime());
        }
        Timestamp tsRegister = rs.getTimestamp("date_register");
        if (tsRegister != null) {
            dto.setDate_register(tsRegister.toLocalDateTime());
        }
        Timestamp tsEnd = rs.getTimestamp("date_end");
        if (tsEnd != null) {
            dto.setDate_end(tsEnd.toLocalDateTime());
        }

        return dto;
    }
}
