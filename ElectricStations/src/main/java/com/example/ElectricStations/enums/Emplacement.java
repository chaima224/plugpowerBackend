package com.example.ElectricStations.enums;

public enum Emplacement{
    Entreprise("Entreprise") ,
    Hôtel("Hôtel"),
    Particulier("Particulier"),
    Parking("Parking"),
    Gare("Gare"),
    Stationservice("Station-service"),
    Restaurant("Restaurant"),
    Voirie("Voirie"),
    Ecole("Ecolet"),
    Autoroute("Autoroute"),
    Autre("Autre");


    private String value;

    Emplacement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
