package com.linos.presentationTimer.controllers;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linos.presentationTimer.model.Timer;
import com.linos.presentationTimer.services.TimerService;

@RestController
public class TimerController {

    private TimerService timerService;

    public TimerController(TimerService timerService){
        this.timerService = timerService;
    }

    @GetMapping("/timer/{id}")
    public Timer getTimer(@PathVariable String id){
        return timerService.getTimer(id);
    }

    @GetMapping("/timers")
    public Collection<Timer> getAllTimers(){
        return timerService.getAllTimers();
    }
    @PostMapping("/createTimer")
    public void createTimer(int presDuration, int quesDuration){
        timerService.createTimer(presDuration, quesDuration);
    }



    
}
