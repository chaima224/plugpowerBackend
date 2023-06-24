package com.example.ElectricStations.services;

import com.example.ElectricStations.entities.Evaluation;
import com.example.ElectricStations.entities.Stations;
import com.example.ElectricStations.entities.User;
import com.example.ElectricStations.repositories.EvaluationRepository;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Evaluation findById(String id)
    { return evaluationRepository.findById(id)
            .orElse(null);
    }
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    // Vous pouvez ajouter d'autres méthodes selon vos besoins, par exemple :

//    public List<Evaluation> getEvaluationsByStation(String stationId) {
//        return evaluationRepository.findByStationId(stationId);
//    }
public List<Evaluation> getApprovedEvaluationsByStation(String stationId) {
    return evaluationRepository.findByStationIdAndStatus(stationId, "approuved");
}
    public ResponseEntity<?> updateEvaluation(String id) {
        // Vérifier si l'évaluation avec cet ID existe déjà
        Optional<Evaluation> existingEvaluation = evaluationRepository.findById(id);
        if (existingEvaluation.isPresent()) {
            // Mettre à jour les propriétés de l'évaluation existante avec les nouvelles valeurs
            Evaluation updatedEvaluation = existingEvaluation.get();
            updatedEvaluation.setStatus("approuved");

            // Enregistrer les modifications dans la base de données
            evaluationRepository.save(updatedEvaluation);

            // Retourner un objet JSON contenant un message de succès
            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("message", "L'évaluation a été modifiée avec succès.");
            return ResponseEntity.ok(response.toString());
        } else {
            // Retourner un objet JSON contenant un message d'erreur
            JSONObject response = new JSONObject();
            response.put("success", false);
            response.put("message", "L'évaluation avec l'ID " + id + " n'existe pas.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }
    }
    public void delete(String id)

    {
        evaluationRepository.deleteById(id);
    }
}
