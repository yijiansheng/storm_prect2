package com.storm_refer;

import java.io.Serializable;

public class RVideoEntity
        implements Serializable {
    private String videoId;
    private String userId;

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "RVideoEntity{videoId='" + this.videoId + '\'' + ", userId='" + this.userId + '\'' + '}';
    }

    public RVideoEntity(String videoId, String userId) {
        this.videoId = videoId;
        this.userId = userId;
    }

    public RVideoEntity() {
    }
}
