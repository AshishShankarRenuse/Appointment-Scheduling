package com.example.appointmentscheduling.admin;

public class ModelMyFC {

    public String start_date;
    public String end_date;
    public String process;
    public String course;
    public String id;
    public String status;

    public ModelMyFC(String id,String course,String process,String start_date, String end_date,String status) {
        this.id = id;
        this.process = process;
        this.course = course;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {return status;}
}
