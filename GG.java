package com.example.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assignment") 
public class GG {

    @Id
    private String id;

    private String event_name;
    private String city_name;
    private LocalDate date;
    private String time;
    private double latitude;
    private double longitude;

    

    public GG() {
    }

    public GG(String id, String eventName, String cityName, LocalDate date, String time, double latitude, double longitude) {
        this.id = id;
        this.event_name = eventName;
        this.city_name = cityName;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return event_name;
    }

    public void setEventName(String eventName) {
        this.event_name = eventName;
    }

    public String getCityName() {
        return city_name;
    }

    public void setCityName(String cityName) {
        this.city_name = cityName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GG{" +
                "id='" + id + '\'' +
                ", eventName='" + event_name + '\'' +
                ", cityName='" + city_name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
