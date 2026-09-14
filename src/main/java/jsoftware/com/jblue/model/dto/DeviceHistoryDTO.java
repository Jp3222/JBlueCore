/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dto;

import java.util.Objects;

/**
 *
 * @author juanp
 */
public class DeviceHistoryDTO {

    private int id;
    private int transaction_id;
    private int history_id;
    private int instance_id;
    private String host_name;
    private String ip;
    private String db_user;
    private String date_register;

    public DeviceHistoryDTO() {
        this.id = 0;
        this.transaction_id = 0;
        this.history_id = 0;
        this.instance_id = 0;
        this.host_name = null;
        this.ip = null;
        this.db_user = null;
        this.date_register = null;
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

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(int instance_id) {
        this.instance_id = instance_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDate_register() {
        return date_register;
    }

    public void setDate_register(String date_register) {
        this.date_register = date_register;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.transaction_id;
        hash = 89 * hash + this.history_id;
        hash = 89 * hash + this.instance_id;
        hash = 89 * hash + Objects.hashCode(this.host_name);
        hash = 89 * hash + Objects.hashCode(this.ip);
        hash = 89 * hash + Objects.hashCode(this.db_user);
        hash = 89 * hash + Objects.hashCode(this.date_register);
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
        final DeviceHistoryDTO other = (DeviceHistoryDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.transaction_id != other.transaction_id) {
            return false;
        }
        if (this.history_id != other.history_id) {
            return false;
        }
        if (this.instance_id != other.instance_id) {
            return false;
        }
        if (!Objects.equals(this.host_name, other.host_name)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
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
        sb.append("DeviceHistoryDTO{");
        sb.append("id=").append(id);
        sb.append(", transaction_id=").append(transaction_id);
        sb.append(", history_id=").append(history_id);
        sb.append(", instance_id=").append(instance_id);
        sb.append(", host_name=").append(host_name);
        sb.append(", ip=").append(ip);
        sb.append(", db_user=").append(db_user);
        sb.append(", date_register=").append(date_register);
        sb.append('}');
        return sb.toString();
    }

}
