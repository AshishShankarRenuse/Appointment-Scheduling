package com.example.appointmentscheduling.admin;

public class ModelFCDay {
    public String appId;
    public String studName;
    public String course;
    public String process;
    public String date;
    public String slot;


    public ModelFCDay(String appId, String studName, String course, String process, String date, String slot) {
        this.appId = appId;
        this.studName = studName;
        this.course = course;
        this.process = process;
        this.date = date;
        this.slot = slot;
    }

    public String getAppId() {
        return appId;
    }

    public String getStudName() {
        return studName;
    }

    public String getCourse() {
        return course;
    }

    public String getProcess() {
        return process;
    }

    public String getDate() {
        return date;
    }

    public String getSlot() {
        return slot;
    }
}
