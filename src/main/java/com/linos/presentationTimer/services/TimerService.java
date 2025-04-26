package com.linos.presentationTimer.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.linos.presentationTimer.model.Timer;

@Service
public class TimerService {

    List<Timer> timerList = new ArrayList<>();

    public Timer getTimer(String id) {
        Timer answer = null;
        for(Timer t : timerList){
            if(t.getID().equals(id)){
                answer = t;
            }
        }
        return answer;
    }

    public void createTimer(int presentationDuration, int questionDuration ){
        Timer newTimer = new Timer(presentationDuration, questionDuration);
        timerList.add(newTimer);
    }

    public Collection<Timer> getAllTimers() {
        return timerList;
    }


    
}
