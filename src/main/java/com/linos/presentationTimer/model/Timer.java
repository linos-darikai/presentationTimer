package com.linos.presentationTimer.model;

import java.util.UUID;

public class Timer {

    //Time in minutes
    private final String id;
    private int presentationDuration;
    private int questionDuration;


    public Timer(int presentationDuration, int questionDuration){
        this.id = UUID.randomUUID().toString();
        this.presentationDuration = presentationDuration;
        this.questionDuration= questionDuration;
    }

    public int getPresentationDuration(){
        return presentationDuration;
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
   
}
