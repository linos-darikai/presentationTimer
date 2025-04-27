package com.linos.presentationTimer.model;

import java.util.UUID;

public class Timer {

    //Time in seconds 
    // change in front end to send seconds back
    private String id;
    private int presentationDuration;
    private int questionDuration;
    private boolean isActive= false;
    private int seconds;
    private long startTime;
    private long endTime;

    public Timer(int presentationDuration, int questionDuration){
        this.id = UUID.randomUUID().toString();
        this.presentationDuration = presentationDuration;
        this.questionDuration= questionDuration;
        this.seconds = (presentationDuration + questionDuration);
    }

    public int getPresentationDuration(){
        return presentationDuration;
    }
    public boolean getStatus(){
        return isActive;
    }

    public int getDurationSeconds(){
        return seconds;
    }
    public void setSeconds(int seconds){
        this.seconds = seconds;
    }
    public String getID(){
        return id;
    }
    public int getQuestionDuration(){
        return questionDuration;
    }
    public void setPresentationDuration(int newPresentationDuration){
        this.presentationDuration = newPresentationDuration;
    }
    public void setQuestionDuration(int newQuestionDuration){
        this.questionDuration = newQuestionDuration;
    }
    public void setStatus(boolean status){
        this.isActive = status;
    }

 

    public void setEndTime(long currentTimeMillis) {
        endTime = currentTimeMillis;
    }

    public void setStartTime(long currentTimeMillis) {
        startTime = currentTimeMillis;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setId(String timerId) {
        id = timerId;
    }

   
}
