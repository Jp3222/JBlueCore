/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class TransactionHistoryDTO {

    private int id;
    private int type_mov;
    private int module_id;
    private int affected_table;
    private String observation;
    private int status;
    private int committee_id;
    private int office_id;
    private int employee_id;
    private LocalDateTime date_register;

    public TransactionHistoryDTO() {
        this.id = 0;
        this.type_mov = 0;
        this.module_id = 0;
        this.affected_table = 0;
        this.observation = null;
        this.committee_id = 0;
        this.office_id = 0;
        this.employee_id = 0;
        this.date_register = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_mov() {
        return type_mov;
    }

    public void setType_mov(int type_mov) {
        this.type_mov = type_mov;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getAffected_table() {
        return affected_table;
    }

    public void setAffected_table(int affected_table) {
        this.affected_table = affected_table;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        hash = 79 * hash + this.type_mov;
        hash = 79 * hash + this.module_id;
        hash = 79 * hash + this.affected_table;
        hash = 79 * hash + Objects.hashCode(this.observation);
        hash = 79 * hash + this.committee_id;
        hash = 79 * hash + this.office_id;
        hash = 79 * hash + this.employee_id;
        hash = 79 * hash + Objects.hashCode(this.date_register);
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
        final TransactionHistoryDTO other = (TransactionHistoryDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.type_mov != other.type_mov) {
            return false;
        }
        if (this.module_id != other.module_id) {
            return false;
        }
        if (this.affected_table != other.affected_table) {
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
        if (!Objects.equals(this.observation, other.observation)) {
            return false;
        }
        return Objects.equals(this.date_register, other.date_register);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransactionHistoryDTO{");
        sb.append("id=").append(id);
        sb.append(", type_mov=").append(type_mov);
        sb.append(", module_id=").append(module_id);
        sb.append(", affected_table=").append(affected_table);
        sb.append(", observation=").append(observation);
        sb.append(", committee_id=").append(committee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", employee_id=").append(employee_id);
        sb.append(", date_register=").append(date_register);
        sb.append('}');
        return sb.toString();
    }

}
