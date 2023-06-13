package com.example.ElectricStations.enums;

public enum Mode {
    SLOW("SLOW") ,
    FAST("FAST");
    private String value;

    Mode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
