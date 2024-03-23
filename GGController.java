package com.example.controller;


import com.example.entity.GG;
import com.example.service.GGService;

//import com.example.service.GGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class GGController {

    @Autowired
    private GGService ggService;

    @PostMapping("/create")
    public void createEvent(@RequestBody GG event) {
        ggService.createEvent(event);
    }

    @GetMapping("/find")
    public List<Map<String, Object>> findEvents(@RequestParam double latitude, @RequestParam double longitude, @RequestParam String date) {
        return ggService.findEvents(latitude, longitude, date);
    }
}
