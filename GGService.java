package com.example.service;


import com.example.entity.GG;
import com.example.repository.GGRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GGService {

    @Autowired
    private GGRepository ggRepository;

    private final String weatherAPIUrl = "https://gg-backend-assignment.azurewebsites.net/api/Weather";
    private final String distanceAPIUrl = "https://gg-backend-assignment.azurewebsites.net/api/Distance";
    private final String weatherAPIKey = "KfQnTWHJbg1giyB_Q9Ih3Xu3L9QOBDTuU5zwqVikZepCAzFut3rqsg==";
    private final String distanceAPIKey = "IAKvV2EvJa6Z6dEIUqqd7yGAu7IZ8gaH-a0QO6btjRc1AzFu8Y3IcQ==";

    public void createEvent(GG event) {
        ggRepository.save(event);
    }

    public List<Map<String, Object>> findEvents(double latitude, double longitude, String date) {
        LocalDate searchDate = LocalDate.parse(date);
        LocalDate endDate = searchDate.plusDays(14);

        List<GG> events = ggRepository.findByDateBetweenAndLocationNear(searchDate, endDate);
        
     // Sort events by date in ascending order
        Collections.sort(events, Comparator.comparing(GG::getDate));
        
        int pageSize = 10;
        int totalEvents = events.size();
        int totalPages = (int) Math.ceil((double) totalEvents / pageSize);

        List<Map<String, Object>> resultPages = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {
            List<Map<String, Object>> pageEvents = new ArrayList<>();
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalEvents);

            for (int i = startIndex; i < endIndex; i++) {
                GG event = events.get(i);
                String weather = getExternalAPIResponse(weatherAPIUrl + "?code=" + weatherAPIKey + "&city=" + event.getCityName() + "&date=" + event.getDate());
                String distanceResponse = getExternalAPIResponse(distanceAPIUrl + "?code=" + distanceAPIKey +
                        "&latitude1=" + latitude + "&longitude1=" + longitude +
                        "&latitude2=" + event.getLatitude() + "&longitude2=" + event.getLongitude());
                double distance = parseDistanceFromResponse(distanceResponse);
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("event_name", event.getEventName());
                eventMap.put("city_name", event.getCityName());
                eventMap.put("date", event.getDate());
                eventMap.put("weather", weather);
                eventMap.put("distance_km", distance);
                pageEvents.add(eventMap);
            }

            Map<String, Object> pageMap = new HashMap<>();
            pageMap.put("events", pageEvents);
            pageMap.put("page", page);
            pageMap.put("pageSize", pageSize);
            pageMap.put("totalEvents", totalEvents);
            pageMap.put("totalPages", totalPages);
          

            resultPages.add(pageMap);
        }

        return resultPages;
    }

    private double parseDistanceFromResponse(String response) {
        String numericPart = response.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numericPart);
    }

    private String getExternalAPIResponse(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
