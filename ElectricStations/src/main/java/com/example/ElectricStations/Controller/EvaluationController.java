package com.example.ElectricStations.Controller;

import com.example.ElectricStations.entities.Evaluation;
import com.example.ElectricStations.entities.Stations;
import com.example.ElectricStations.repositories.StationsRepository;
import com.example.ElectricStations.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    private final EvaluationService evaluationService;
    private final StationsRepository stationsRepository;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, StationsRepository stationsRepository) {
        this.evaluationService = evaluationService;
        this.stationsRepository = stationsRepository;
    }


    @PostMapping("/stations/{stationId}/comments")
    public ResponseEntity<Evaluation> addCommentToStation(@PathVariable("stationId") String stationId, @RequestBody String comment) {
        // Récupérer la station à partir de l'ID
        Stations station = stationsRepository.findById(stationId).orElse(null);

        if (station == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Appeler le service pour ajouter le commentaire
        Evaluation evaluation = evaluationService.addCommentToStation(station, comment);

        if (evaluation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(evaluation);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/comments")
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    // Vous pouvez ajouter d'autres méthodes de contrôleur selon vos besoins, par exemple :

//    @GetMapping("/station/{stationId}")
//    public ResponseEntity<List<Evaluation>> getEvaluationsByStation(@PathVariable String stationId) {
//        List<Evaluation> evaluations = evaluationService.getEvaluationsByStation(stationId);
//        return new ResponseEntity<>(evaluations, HttpStatus.OK);
//    }
@GetMapping("/station/{stationId}")
public ResponseEntity<List<Evaluation>> getApprovedEvaluationsByStation(@PathVariable("stationId") String stationId) {
    List<Evaluation> approvedEvaluations = evaluationService.getApprovedEvaluationsByStation(stationId);
    return ResponseEntity.ok(approvedEvaluations);
}


    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(evaluationService.findById(id));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateEvaluationStatus(@PathVariable("id") String id) {
        return evaluationService.updateEvaluation(id);
    }
    @DeleteMapping("/{evaluation-id}")
    public ResponseEntity<Void> delete(@PathVariable("evaluation-id") String id) {
        evaluationService.delete(id);
        return ResponseEntity.accepted().build();
    }


}