/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.service;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.model.dao.MenuDAO2;
import jsoftware.com.jblue.model.dto.MenuDTO;
import jsoftware.com.jblue.model.exp.SystemException;
import jsoftware.com.jblue.model.view.ProcessModel;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jblue.sys.app.AppConfig;
import jsoftware.com.jblue.sys.app.AppFiles;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.util.JFuncLogs;

/**
 *
 * @author juanp
 */
public class MenuService extends AbstractService {

    private final MenuDAO2 menu_dao;
    private final LoginService login_Service;
    private final SystemSessionService sss_service;

    public MenuService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        this.menu_dao = new MenuDAO2(dev_flag, process_name);
        this.login_Service = new LoginService(dev_flag, process_name);
        this.sss_service = new SystemSessionService(dev_flag, process_name);
    }

    public Optional<JMenuBar> builMenu(JDBConnection connection, SystemSession ss, JPanel ly, ActionListener listener) {
        //sss_service.valid(connection, ss);
        Optional<JMenuBar> res = Optional.empty();
        System.out.println(process_name);
        System.out.println("INICIO METODO MENU");
        try {
            JMenuBar menu_bar = new JMenuBar();
            List<MenuDTO> root_menus = menu_dao.selectByIsRootMenu(connection);
            System.out.println("Menu Bar");
            for (MenuDTO i : root_menus) {
                JMenu root_menu = new JMenu(i.getName());
                root_menu.setActionCommand(i.getAction_command());
                root_menu.setName(i.getName());
                root_menu.setText(i.getName());
                // Construimos recursivamente los items o submenús internos
                addNode(connection, root_menu, ly, i.getId(), listener);
                menu_bar.add(root_menu);
            }
            res = Optional.of(menu_bar);
            returnMessageError(SERVICE_EXECUTE_OK, "TODO OK");
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println("ERROR METODO MENU - " + process_name + ":" + ex.getMessage());
            JFuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, getClass(), ex, process_name, "builMenu", ex.getMessage());
            returnMessageError("ARBOL DE PROCESOS NO GENERADO");
        }
        System.out.println("FIN METODO MENU");
        return res;
    }

    private void addNode(JDBConnection connection, JMenu menuPadreVisual, JPanel root_panel, int parent_id, ActionListener listener) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<MenuDTO> childrens = menu_dao.selectByParentId(connection, parent_id);
        for (MenuDTO i : childrens) {
            if (i.isIs_menu()) { // Si es un menú desplegable interno
                JMenu sub_menu = new JMenu(i.getName());
                sub_menu.setActionCommand(i.getAction_command());
                addNode(connection, sub_menu, root_panel, i.getId(), listener);
                menuPadreVisual.add(sub_menu);
            } else { // Si es una opción final de menú (JMenuItem)
                JMenuItem item = new JMenuItem(i.getName());
                item.setActionCommand(i.getAction_command());
                if (listener != null) {
                    item.addActionListener(listener);
                }
                menuPadreVisual.add(item);
                Optional<JPanel> cls = createClass(i.getProcess_file());
                if (cls.isPresent()) {
                    JPanel new_view = cls.get();
                    new_view.setName(i.getName());
                    root_panel.add(new_view, new_view.getName());
                    if (new_view instanceof ProcessModel p) {
                        p.setProcessId(i.getId());
                        p.setProcessName(p.getProcessName());
                    }
                }
            }
        }
    }

    private Optional<JPanel> createClass(String process_name) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (process_name == null) {
            return Optional.empty();
        }
        Class<?> cls = Class.forName(process_name);
        Object instance = cls.getDeclaredConstructor().newInstance();
        if (instance instanceof JPanel p) {
            return Optional.of(p);
        }
        return Optional.empty();
    }

    public boolean logout(JDBConnection connection, SystemSession ss) throws SystemException, SQLException {
        sss_service.valid(connection, ss);
        return login_Service.logout(connection, ss);
    }
}
