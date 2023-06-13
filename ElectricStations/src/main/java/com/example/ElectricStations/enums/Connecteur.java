package com.example.ElectricStations.enums;

public enum Connecteur {
    TYPE1("TYPE1"),
    TYPE2("TYPE2"),
    CHAdeMO("CHAdeMO"),
    Tesla("Tesla"),
    Tesla2("Tesla2"),
    P17("P17"),
    Marechal("Marechal"),
    IEC309DC("IEC309DC"),
    Type3A("Type3A"),
    TypeD("TypeD"),
    TeslaHPWC("TeslaHPWC"),
    ChinaPart2("ChinaPart2"),
    DomestiqueUK("DomestiqueUK"),
    ComboCCSUSA("ComboCCSUSA"),
    TeslaUSA("TeslaUSA"),
    CHT25("CHT25"),
    DomestiqueIT("DomestiqueIT"),
    CHT13("CHT13"),
    PriseAVCON("PriseAVCON"),
    DomestiqueUE("DomestiqueUE"),
    ComboCCSEU("ComboCCSEU");

    private String value;

    Connecteur(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
