package com.example.microwave;

public class Program {
    private int minutes,seconds,heat;
    private String type;
    public Program(int minutes, int seconds, int heat, String type) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.heat = heat;
        this.type = type;
    }
    public Program(){}
    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
