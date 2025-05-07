package com.grupo8.role.model;

public class EventGridObject {

    private String id;
    private String eventType;
    private String subject;
    private String eventTime;
    private Object data;
    
    public EventGridObject() {
    }

    public EventGridObject(String id, String eventType, String subject, String eventTime, Object data) {
        this.id = id;
        this.eventType = eventType;
        this.subject = subject;
        this.eventTime = eventTime;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
