package com.example.ElectricStations.enums;

public enum Trajet {
    AvoidHighways("AvoidHighways") ,
    AvoidTolls("AvoidTolls");
    private String value;

    Trajet(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
