package com.zihui.cwoa.routine.pojo;

public class rw_trip {
    private Integer tripId;

    private Integer tripUserId;

    private String tripContent;

    private String tripTime;

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getTripUserId() {
        return tripUserId;
    }

    public void setTripUserId(Integer tripUserId) {
        this.tripUserId = tripUserId;
    }

    public String getTripContent() {
        return tripContent;
    }

    public void setTripContent(String tripContent) {
        this.tripContent = tripContent == null ? null : tripContent.trim();
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime == null ? null : tripTime.trim();
    }

    @Override
    public String toString() {
        return "rw_trip{" +
                "tripId=" + tripId +
                ", tripUserId=" + tripUserId +
                ", tripContent='" + tripContent + '\'' +
                ", tripTime='" + tripTime + '\'' +
                '}';
    }
}