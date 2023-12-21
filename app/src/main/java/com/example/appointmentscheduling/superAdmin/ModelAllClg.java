package com.example.appointmentscheduling.superAdmin;

public class ModelAllClg {
    public String clgId;
    public String clgName;
    public String clgAddr;

    public ModelAllClg(String clgId, String clgName, String clgAddr){
        this.clgId = clgId;
        this.clgName = clgName;
        this.clgAddr = clgAddr;
    }

    public String getClgId() { return clgId; }

    public void setClgId(String clgId) {
        this.clgId = clgId;
    }

    public String getClgName() {
        return clgName;
    }

    public void setClgName(String clgName) {
        this.clgName = clgName;
    }

    public String getClgAddr() {
        return clgAddr;
    }

    public void setClgAddr(String clgAddr) {
        this.clgAddr = clgAddr;
    }
}
