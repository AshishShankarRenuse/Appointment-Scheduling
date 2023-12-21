package com.example.appointmentscheduling.student;

public class ModelViewApp {
    public String process;
    public String college;
    public String date;
    public String slot;
    public String status;

    public ModelViewApp(String process, String college, String date, String slot,String status) {
        this.process = process;
        this.college = college;
        this.date = date;
        this.slot = slot;
        this.status = status;
    }

    public String getProcess() {
        return process;
    }

    public String getCollege() {
        return college;
    }

    public String getDate() {
        return date;
    }

    public String getSlot() {
        return slot;
    }

    public String getStatus() {
        return status;
    }
}
