package ir.alishi.vpn.model;


import com.google.gson.annotations.SerializedName;

public class ServerListModel {
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    private long id;
    @SerializedName("config")
    private String config;
    @SerializedName("ip")
    private String ip;
    @SerializedName("us")
    private String us;
    @SerializedName("pas")
    private String pas;
    @SerializedName("priority")
    private long priority;
    @SerializedName("flag_name")
    private String flag_name;
    @SerializedName("vis")
    private String vis;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag_name() {
        return flag_name;
    }

    public void setFlag_name(String flag_name) {
        this.flag_name = flag_name;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }
}
