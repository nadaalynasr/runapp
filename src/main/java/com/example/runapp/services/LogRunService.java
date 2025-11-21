package com.example.runapp.services;

import java.time.LocalTime;
import org.springframework.stereotype.Service;

@Service
public class LogRunService {
    
    public String getPlaceholderForTime(LocalTime now) {
        if (now.isAfter(LocalTime.of(5, 0)) && now.isBefore(LocalTime.of(11, 0))) {
            return "Morning Run";
        } else if (now.isBefore(LocalTime.of(17, 0))) {
            return "Afternoon Run";
        } else if (now.isBefore(LocalTime.of(22, 0))) {
            return "Evening Run";
        } else {
            return "Night Run";
        }
    }


}
