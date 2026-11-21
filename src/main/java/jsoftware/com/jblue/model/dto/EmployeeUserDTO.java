package jsoftware.com.jblue.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO correspondiente a las credenciales, accesos y estado de usuarios de
 * empleados (EmployeeUser).
 * <br>
 * Utiliza la estructura heredada dinámica de Map para transportar las cadenas
 * de texto de manera flexible entre los componentes de Swing y la capa DAO de
 * MySQL, apegándose al estándar actual de JBlue.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-03-21
 * @version 1.0
 */
public class EmployeeUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int employee_id;
    private int office_id;
    private String user;
    private String password;
    private String description;
    private String email;
    private String phone_number;
    private int employee_type;
    private int status;
    private int last_employee_update;
    private LocalDateTime last_update_password;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public EmployeeUserDTO() {
        this.id = -1;
        this.employee_id = -1;
        this.office_id = -1;
        this.user = null;
        this.password = null;
        this.description = null;
        this.email = null;
        this.phone_number = null;
        this.employee_type = -1;
        this.status = -1;
        this.last_employee_update = -1;
        this.last_update_password = null;
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

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(int employee_type) {
        this.employee_type = employee_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLast_employee_update() {
        return last_employee_update;
    }

    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    public LocalDateTime getLast_update_password() {
        return last_update_password;
    }

    public void setLast_update_password(LocalDateTime last_update_password) {
        this.last_update_password = last_update_password;
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
        hash = 83 * hash + this.id;
        hash = 83 * hash + this.employee_id;
        hash = 83 * hash + Objects.hashCode(this.user);
        hash = 83 * hash + Objects.hashCode(this.password);
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
        final EmployeeUserDTO other = (EmployeeUserDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.employee_id != other.employee_id) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EmployeeUserDTO{");
        sb.append("id=").append(id);
        sb.append(", employee_id=").append(employee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", user=").append(user);
        sb.append(", password=").append(password);
        sb.append(", description=").append(description);
        sb.append(", email=").append(email);
        sb.append(", phone_number=").append(phone_number);
        sb.append(", employee_type=").append(employee_type);
        sb.append(", status=").append(status);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", last_update_password=").append(last_update_password);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

}
