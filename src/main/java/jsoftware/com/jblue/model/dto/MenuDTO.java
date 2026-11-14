/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dto;

import java.time.LocalDateTime;

/**
 *
 * @author juanp
 */
public class MenuDTO {

    private int id;
    private int parent_id;
    private int module_id;
    private String name;
    private int level;
    private String action_command;
    private boolean is_menu;
    private boolean is_windows;
    private boolean is_root_menu;
    private String process_file;
    private int last_employee_update;
    private int status;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public MenuDTO() {
        this.id = -1;
        this.parent_id = -1;
        this.module_id = -1;
        this.name = null;
        this.level = 0;
        this.action_command = null;
        this.is_menu = false;
        this.is_windows = false;
        this.is_root_menu = false;
        this.process_file = null;
        this.last_employee_update = -1;
        this.status = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAction_command() {
        return action_command;
    }

    public void setAction_command(String action_command) {
        this.action_command = action_command;
    }

    public boolean isIs_menu() {
        return is_menu;
    }

    public void setIs_menu(boolean is_menu) {
        this.is_menu = is_menu;
    }

    public boolean isIs_windows() {
        return is_windows;
    }

    public void setIs_windows(boolean is_windows) {
        this.is_windows = is_windows;
    }

    public boolean isIs_root_menu() {
        return is_root_menu;
    }

    public void setIs_root_menu(boolean is_root_menu) {
        this.is_root_menu = is_root_menu;
    }

    public String getProcess_file() {
        return process_file;
    }

    public void setProcess_file(String process_file) {
        this.process_file = process_file;
    }

    public int getLast_employee_update() {
        return last_employee_update;
    }

    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDate_update() {
        return date_update;
    }

    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
    }

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    public LocalDateTime getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuDTO other = (MenuDTO) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MenuDTO{");
        sb.append("id=").append(id);
        sb.append(", parent_id=").append(parent_id);
        sb.append(", module_id=").append(module_id);
        sb.append(", name=").append(name);
        sb.append(", level=").append(level);
        sb.append(", action_command=").append(action_command);
        sb.append(", is_menu=").append(is_menu);
        sb.append(", is_windows=").append(is_windows);
        sb.append(", is_root_menu=").append(is_root_menu);
        sb.append(", process_file=").append(process_file);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", status=").append(status);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

}
