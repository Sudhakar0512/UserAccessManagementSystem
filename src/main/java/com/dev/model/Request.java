package com.dev.model;

public class Request {
    private int id;
    private int userId;
    private int softwareId;
    private String accessType;
    private String reason;
    private String status;
    private String userName; // Field for user name
    private String softwareName; // Field for software name

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() { // Correct method for user name
        return userName;
    }

    public void setUserName(String userName) { // Setter for user name
        this.userName = userName;
    }

    public String getSoftwareName() { // Correct method for software name
        return softwareName;
    }

    public void setSoftwareName(String softwareName) { // Setter for software name
        this.softwareName = softwareName;
    }
}