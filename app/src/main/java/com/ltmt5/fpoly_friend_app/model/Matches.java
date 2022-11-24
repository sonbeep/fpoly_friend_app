package com.ltmt5.fpoly_friend_app.model;

public class Matches {
    private int matchId;
    private int user1;
    private int user2;
    private int time;
    private int chatId;

    public Matches(int matchId, int user1, int user2, int time, int chatId) {
        this.matchId = matchId;
        this.user1 = user1;
        this.user2 = user2;
        this.time = time;
        this.chatId = chatId;
    }

    public Matches() {
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
