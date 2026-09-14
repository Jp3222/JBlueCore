/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class ProgramHistoryDTO {

    private int id;
    private int transaction_id;
    private int type_mov;
    private int affected_table;
    private int enty_id;
    private String description;
    private String db_user;
    private int committee_id;
    private int office_id;
    private int employee_id;
    private LocalDate date_register;

    public ProgramHistoryDTO() {
        id = 0;
        transaction_id = 0;
        type_mov = 0;
        affected_table = 0;
        enty_id = 0;
        description = null;
        db_user = null;
        committee_id = 0;
        office_id = 0;
        employee_id = 0;
        date_register = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getType_mov() {
        return type_mov;
    }

    public void setType_mov(int type_mov) {
        this.type_mov = type_mov;
    }

    public int getAffected_table() {
        return affected_table;
    }

    public void setAffected_table(int affected_table) {
        this.affected_table = affected_table;
    }

    public int getEnty_id() {
        return enty_id;
    }

    public void setEnty_id(int enty_id) {
        this.enty_id = enty_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(int committee_id) {
        this.committee_id = committee_id;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public LocalDate getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDate date_register) {
        this.date_register = date_register;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + this.transaction_id;
        hash = 17 * hash + this.type_mov;
        hash = 17 * hash + this.affected_table;
        hash = 17 * hash + this.enty_id;
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.db_user);
        hash = 17 * hash + this.committee_id;
        hash = 17 * hash + this.office_id;
        hash = 17 * hash + this.employee_id;
        hash = 17 * hash + Objects.hashCode(this.date_register);
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
        final ProgramHistoryDTO other = (ProgramHistoryDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.transaction_id != other.transaction_id) {
            return false;
        }
        if (this.type_mov != other.type_mov) {
            return false;
        }
        if (this.affected_table != other.affected_table) {
            return false;
        }
        if (this.enty_id != other.enty_id) {
            return false;
        }
        if (this.committee_id != other.committee_id) {
            return false;
        }
        if (this.office_id != other.office_id) {
            return false;
        }
        if (this.employee_id != other.employee_id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.db_user, other.db_user)) {
            return false;
        }
        return Objects.equals(this.date_register, other.date_register);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProgramHistory{");
        sb.append("id=").append(id);
        sb.append(", transaction_id=").append(transaction_id);
        sb.append(", type_mov=").append(type_mov);
        sb.append(", affected_table=").append(affected_table);
        sb.append(", enty_id=").append(enty_id);
        sb.append(", description=").append(description);
        sb.append(", db_user=").append(db_user);
        sb.append(", committee_id=").append(committee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", employee_id=").append(employee_id);
        sb.append(", date_register=").append(date_register);
        sb.append('}');
        return sb.toString();
    }

}
