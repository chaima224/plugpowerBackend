package com.example.ElectricStations.Controller;

import com.example.ElectricStations.entities.Borne;
import com.example.ElectricStations.entities.StationRequest;
import com.example.ElectricStations.entities.Stations;
import com.example.ElectricStations.repositories.StationsRepository;
import com.example.ElectricStations.services.BorneService;
import com.example.ElectricStations.services.StationService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/electricStations")
public class StationController {

    private final StationService stationService;

    private final StationsRepository stationsRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    public StationController(StationService stationService,  StationsRepository stationsRepository) {
        this.stationService = stationService;

        this.stationsRepository = stationsRepository;
    }




    @PostMapping
    public ResponseEntity<String> save(@RequestBody Stations station) {
        return ResponseEntity.ok(stationService.save(station));
    }

    @GetMapping
    public ResponseEntity<List<Stations>> findAll() {
        return ResponseEntity.ok(stationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stations> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(stationService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStation(@PathVariable String id, @RequestBody Stations newStation) {
        return stationService.updateStation(id, newStation);
    }


    @DeleteMapping("/{station-id}")
    public ResponseEntity<Void> delete(@PathVariable("station-id") String id) {
        stationService.delete(id);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/stations/{stationId}/availability")
    public boolean isStationAvailable(@PathVariable String stationId, @RequestParam(required = false) LocalDateTime dateTime) {
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
        return stationService.isStationAvailable(stationId, dateTime);
    }
   /* @GetMapping("/{stationId}/bornes/{borneId}")
    public ResponseEntity<Object> isBorneExistInStation(@PathVariable String stationId, @PathVariable String borneId) {
        boolean isBorneExist = stationService.isBorneExistInStation(stationId, borneId);
        if (isBorneExist) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
   @GetMapping("/{stationId}/bornes/{borneId}")
   public boolean isBorneExistInStation(@PathVariable String stationId, @PathVariable String borneId) {
       return stationService.isBorneExistInStation(stationId, borneId);
   }
//    @GetMapping("/stations/nearby/{longitude}/{latitude}/{distance}")
//    public List<Stations> getStationsNearby(@PathVariable double longitude, @PathVariable double latitude, @PathVariable double distance) {
//        return stationService.findStationsNearby(longitude, latitude, distance);
//    }
@GetMapping("/stations")
public List<Stations> getStations() {
    List<Stations> stations = stationService.getStations();

    // Iterate over stations and retrieve the names of the 'bornes'
    for (Stations station : stations) {
        List<Borne> bornes = station.getBornes();
        if (bornes != null) {
            List<String> nomBornes = bornes.stream()
                    .filter(borne -> borne != null) // Filter out null 'bornes'
                    .map(Borne::getName)
                    .collect(Collectors.toList());
            station.setNomBornes(nomBornes);
        }
    }

    return stations;
}

    @PostMapping("/addStation")
    public ResponseEntity<Stations> createStation(@RequestBody Stations station) {
        Stations createdStation = stationService.createStation(station);
        return new ResponseEntity<>(createdStation, HttpStatus.CREATED);
    }



@PostMapping("/add")
public Stations createStationAndBorne(@RequestBody StationRequest request) {
    Stations station = request.getStation();
    Borne borne = request.getBorne();

    return stationService.createStationAndBorne(station, borne);
}





    //get stationsApprouved
    @GetMapping("/stationsApprouved")
    public List<Stations> getApprovedStations() {
        return stationService.getApprovedStations();
    }

    @GetMapping("/rechercheStation")
    public ResponseEntity<List<Stations>> rechercheStations(
            @RequestParam(required = false) Float puissance,
            @RequestParam(required = false) String mode,
            @RequestParam(required = false) String connecteur) {
        List<Stations> stations = stationService.rechercheStation(puissance, mode, connecteur);
        if (stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(stations, HttpStatus.OK);
        }
    }
//    @GetMapping("/coordinates")
//    public ResponseEntity<?> getAllStationsCoordinates() {
//        try {
//            return new ResponseEntity<>(stationService.getAllStationsCoordinates(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve station coordinates.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//@GetMapping("/coordinates")
//public ResponseEntity<List<Map<String, Object>>> getAllStationsCoordinates() {
//    List<Map<String, Object>> stationCoordinates = stationService.getAllStationsCoordinates();
//    return ResponseEntity.ok(stationCoordinates);
//}
//@GetMapping("/coordinates")
//public List<Map<String, Object>> getApprovedStationsCoordinates() {
//    return stationService.getAllStationsCoordinates();
//}
//    @GetMapping("/mode/{mode}")
//    public List<Stations> getStationsByMode(@PathVariable String mode) {
//        return stationService.getStationsByMode(mode);
//    }
@GetMapping("/coordinate")
public List<Map<String, Object>> getAllStationCoordinates() {
    return stationService.getAllStationCoordinates();
}
}
