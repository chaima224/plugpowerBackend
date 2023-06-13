package com.example.ElectricStations.entities;

import com.example.ElectricStations.enums.Dispo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Borne {
    @Id
    private String id;
    private String name;
    private float puissance;
    private float tempsCharge;
    private String mode;
    private String connecteur;
    private Image image;
    private String description;

    private String status = "pending";


    public void setStation(Stations station) {
    }

}
