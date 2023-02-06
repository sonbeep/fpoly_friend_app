package com.ltmt5.fpoly_friend_app.model;

import java.util.Date;
import java.util.List;

public class Ban {
    private String userId;
    private Date date;
    private List<String> evidence;

    public Ban() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<String> evidence) {
        this.evidence = evidence;
    }
}
