package com.example.ElectricStations.entities;

import com.example.ElectricStations.enums.Connecteur;
import com.example.ElectricStations.enums.Emplacement;
import com.example.ElectricStations.enums.Trajet;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CrossOrigin

public class Stations {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;

    @NonNull
    @DBRef
    private List<Borne> bornes;
    @Field("nomBornes")
    private List<String> nomBornes;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime ouverture;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fermeture;
    @NonNull
    private String emplacement;
    @NonNull
    private String trajet;

    private String status = "pending";

    public Stations(List<Borne> bornes) {

        bornes = new ArrayList<>();
    }

    // Autres méthodes et getters/setters
    public void setNomBornes(List<String> nomBornes) {
        this.nomBornes = nomBornes;
    }



    public List<Borne> getBornes() {
        if (bornes == null) {
            bornes = new ArrayList<>(); // Initialisation de la liste bornes si elle est nulle
        }
        return bornes;
    }

    public void setBornes(List<Borne> bornes) {
        this.bornes = bornes;
    }


    //disponibiité

}

