package com.team2052.frckrawler.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ROBOT_EVENT.
 */
public class RobotEvent implements java.io.Serializable {

    private Long id;
    private long robot_id;
    private long event_id;
    private String data;

    public RobotEvent() {
    }

    public RobotEvent(Long id) {
        this.id = id;
    }

    public RobotEvent(Long id, long robot_id, long event_id, String data) {
        this.id = id;
        this.robot_id = robot_id;
        this.event_id = event_id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getRobot_id() {
        return robot_id;
    }

    public void setRobot_id(long robot_id) {
        this.robot_id = robot_id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
