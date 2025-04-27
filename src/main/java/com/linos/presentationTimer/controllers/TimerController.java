package com.linos.presentationTimer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.linos.presentationTimer.model.Timer;
import com.linos.presentationTimer.services.TimerService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TimerController {

    private  TimerService timerService;
    private  SimpMessagingTemplate messagingTemplate = null;
    private ConcurrentHashMap<String, Timer> timerStarts = new ConcurrentHashMap<>(); // Store timer start times

    @Autowired
    public TimerController(TimerService timerService, SimpMessagingTemplate messagingTemplate) {
        this.timerService = timerService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/timer/{id}")
    public Timer getTimer(@PathVariable String id) {
        return timerService.getTimer(id);
    }

    @GetMapping("/timers")
    public ConcurrentHashMap<String, Timer> getAllTimers() {
        return timerService.getAllTimers();
    }
   @PostMapping("/createTimer")
    public ResponseEntity<Map<String, String>> createTimer() {
        String timerId = UUID.randomUUID().toString();

        Timer newTimer = new Timer(900, 600);
        newTimer.setId(timerId);
        newTimer.setStatus(false);
        newTimer.setStartTime(0);
        newTimer.setEndTime(0);

        timerService.createTimer(newTimer);

        Map<String, String> response = new HashMap<>();
        response.put("id", timerId);  // Return { "id": "..." }

        return ResponseEntity.ok(response);
    }


    
    @MessageMapping("/start-timer/{timerId}")
    public void startTimer(@PathVariable String timerId) {
        Timer timer = timerService.getTimer(timerId);
        if (timer != null) {
            timer.setStatus(true); 
            timer.setStartTime(System.currentTimeMillis()); 
            sendTimerUpdates(timerId); // Send immediate updates after starting the timer
            System.out.println("Timer " + timerId + " started.");
        }
    }

    // Handle stop timer command from WebSocket
    @MessageMapping("/stop-timer/{timerId}")
    public void stopTimer(@PathVariable String timerId) {
        Timer timer = timerService.getTimer(timerId);
        if (timer != null) {
            timer.setStatus(false); // Mark the timer as inactive
            timer.setEndTime(System.currentTimeMillis()); // Store the end time
            System.out.println("Timer " + timerId + " stopped.");
        }
    }
    @Scheduled(fixedRate = 1000)
    public void sendUpdatesForActiveTimers() {
        System.out.println("⏰ Scheduled task running...");
        String formattedTime = null;
        for (Timer timer : timerService.getAllTimers().values()) {
            
            long elapsedTime = (System.currentTimeMillis() - timer.getStartTime()) / 1000; // Calculate elapsed time
            formattedTime = formatElapsedTime(elapsedTime);
            messagingTemplate.convertAndSend("/broadcast/timer/" + timer.getID(), formattedTime);
            System.out.println("📤 Sent update to /broadcast/timer/" + timer.getID());

        
        }
    }
    

    

    // Method to send updates to all connected clients
    public void sendTimerUpdates(String timerId) {
        Timer timer = timerService.getTimer(timerId);
        if (timer != null) {
            long elapsedTime = (System.currentTimeMillis() - timer.getStartTime()) / 1000; // Calculate elapsed time
            String formattedTime = formatElapsedTime(elapsedTime);
            messagingTemplate.convertAndSend("/broadcast/timer/" + timerId, formattedTime); // Send the time update to clients
            System.out.println(String.format("/broadcast/timer/%s/%s", timerId ,formattedTime));
        }
    }

    // Format the time to mm:ss
    private String formatElapsedTime(long elapsedTime) {
        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
