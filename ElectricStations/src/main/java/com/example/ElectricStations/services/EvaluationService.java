package com.example.ElectricStations.services;

import com.example.ElectricStations.entities.Evaluation;
import com.example.ElectricStations.entities.Stations;
import com.example.ElectricStations.entities.User;
import com.example.ElectricStations.repositories.EvaluationRepository;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }



    public Evaluation addCommentToStation(Stations station, String comment) {
        JsonParser parser = new JsonParser();
        //Creating JSONObject from String using parser
        JsonObject JSONObject = parser.parse(comment).getAsJsonObject();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        LocalDateTime timestamp = LocalDateTime.now();

        Evaluation evaluation = new Evaluation();
        evaluation.setUser(user);
        evaluation.setStation(station);
        evaluation.setComment(JSONObject.get("comment").getAsString());
        evaluation.setTimestamp(timestamp);

        return evaluationRepository.save(evaluation);
    }
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    // Vous pouvez ajouter d'autres méthodes selon vos besoins, par exemple :

    public List<Evaluation> getEvaluationsByStation(String stationId) {
        return evaluationRepository.findByStationId(stationId);
    }
}
