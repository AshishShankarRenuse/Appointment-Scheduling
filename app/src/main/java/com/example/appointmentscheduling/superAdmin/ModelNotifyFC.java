package com.example.appointmentscheduling.superAdmin;

public class ModelNotifyFC {
    public String clgId;
    public String course;
    public String process;
    public String startDate;
    public String endDate;

    public ModelNotifyFC(String clgId, String course, String process, String startDate, String endDate) {
        this.clgId = clgId;
        this.course = course;
        this.process = process;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getClgId() {
        return clgId;
    }

    public void setClgId(String clgId) {
        this.clgId = clgId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
