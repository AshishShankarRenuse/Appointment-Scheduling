package com.example.appointmentscheduling.superAdmin;

public class ModelAcceptCenters {
    public String clgId;
    public String clgName;
    public String clgAddr;
    public String contact;
    public String course;
    public String process;

    public ModelAcceptCenters(String clgId, String clgName, String clgAddr, String contact, String course, String process) {
        this.clgId = clgId;
        this.clgName = clgName;
        this.clgAddr = clgAddr;
        this.contact = contact;
        this.course = course;
        this.process = process;
    }

    public String getClgId() {
        return clgId;
    }

    public String getClgName() {
        return clgName;
    }

    public String getClgAddr() {
        return clgAddr;
    }

    public String getContact() {
        return contact;
    }

    public String getCourse() {
        return course;
    }

    public String getProcess() {
        return process;
    }
}
