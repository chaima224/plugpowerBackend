package com.example.ElectricStations.entities;
import com.example.ElectricStations.enums.Dispo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilite {
    @Id
    private String id;

    @DBRef
    private Stations station;

    @DBRef
    private Borne borne;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String etat;

    @Field("nomBorne")
    private String nomBorne;
    @Field("nomStation")
    private String nomStation;
    // Constructeurs, getters, setters, etc.
//    public Stations getStation() {
//        return station;
//    }
//    public Borne getBorne() {
//        return borne;
//    }
    public Stations getStation() {
        if (this.station != null) {
            return this.station;
        }
        return new Stations(); // Remplacez ceci par une valeur par défaut appropriée
    }

    public Borne getBorne() {
        if (this.borne != null) {
            return this.borne;
        }
        return new Borne(); // Remplacez ceci par une valeur par défaut appropriée
    }



}
