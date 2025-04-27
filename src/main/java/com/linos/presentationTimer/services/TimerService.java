package com.linos.presentationTimer.services;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.linos.presentationTimer.model.Timer;

@Service
public class TimerService {

    ConcurrentHashMap<String, Timer> timerMap = new ConcurrentHashMap<>();

    public Timer getTimer(String id) {
        return timerMap.get(id);
    }

    public void createTimer(Timer nT){
        Timer newTimer = nT;
        timerMap.put(newTimer.getID(), newTimer);
    }

    public ConcurrentHashMap<String, Timer> getAllTimers() {
        return timerMap;
    }
    public void startTimer(String timerId) {
        Timer timer = timerMap.get(timerId);
        if (timer != null) {
            timer.setStatus(true);
            timerMap.put(timerId, timer);
        }
    }

   public void stopTimer(String timerId) {
        Timer timer = timerMap.get(timerId);
        if (timer != null) {
            timer.setStatus(false);
            timerMap.put(timerId, timer);
        }
    }


    
}
