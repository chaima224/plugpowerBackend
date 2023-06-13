package com.example.ElectricStations.services;

import com.example.ElectricStations.entities.Borne;
import com.example.ElectricStations.entities.Stations;
import com.example.ElectricStations.enums.Connecteur;
import com.example.ElectricStations.enums.Mode;
import com.example.ElectricStations.repositories.BorneRepository;
import com.example.ElectricStations.repositories.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BorneService {
    @Autowired
    private final StationsRepository stationsRepository;
    private final BorneRepository borneRepository;




    public BorneService(StationsRepository stationsRepository, BorneRepository borneRepository) {
        this.stationsRepository = stationsRepository;
        this.borneRepository = borneRepository;

    }

    public boolean save(Borne borne) {
        Borne savedBorne = borneRepository.save(borne);
        return savedBorne != null;
    }

    public Borne findById(String id) {
        return borneRepository.findById(id)
                .orElse(null);
    }

    public List<Borne> findAll() {
        return borneRepository.findAll();
    }

    public boolean updateBorne(String id, Borne newBorne) {
        // Vérifier si la borne avec cet ID existe déjà
        Optional<Borne> existingBorne = borneRepository.findById(id);
        if (existingBorne.isPresent()) {
            // Mettre à jour les propriétés de la borne existante avec les nouvelles valeurs
            Borne updatedBorne = existingBorne.get();
            updatedBorne.setName(newBorne.getName());
            updatedBorne.setPuissance(newBorne.getPuissance());
            updatedBorne.setTempsCharge(newBorne.getTempsCharge());
            updatedBorne.setMode(newBorne.getMode());
            updatedBorne.setConnecteur(newBorne.getConnecteur());
            updatedBorne.setImage(newBorne.getImage());
            updatedBorne.setStatus("approuved");


            // Enregistrer les modifications dans la base de données
            borneRepository.save(updatedBorne);

            return true; // Modification réussie
        } else {
            return false; // Borne non trouvée
        }
    }

    public void delete(String id) {
        borneRepository.deleteById(id);
    }

//    public Boolean reserverBorne(String id) {
//        Borne borne = borneRepository.findById(id).orElseThrow();
//        if (borne.getDisponibilite() == Disponibilite.OCCUPEE) {
//            System.out.println("La borne est déjà occupée.");
//            return false;
//        } else {
//            borne.setDisponibilite(Disponibilite.OCCUPEE);
//            borneRepository.save(borne);
//            System.out.println("La borne a été réservée avec succès.");
//            return true;
//        }
//    }
//    public synchronized boolean reserverBorne(String id) {
//        Borne borne = borneRepository.findById(id).orElseThrow();
//
//        if (borne.getDisponibilite() == Disponibilite.OCCUPEE) {
//            System.out.println("La borne est déjà occupée.");
//            return false;
//        } else {
//            borne.setDisponibilite(Disponibilite.OCCUPEE);
//            borneRepository.save(borne);
//            System.out.println("La borne a été réservée avec succès.");
//            return true;
//        }
//    }
//
//    public synchronized boolean libererBorne(String id) {
//        Borne borne = borneRepository.findById(id).orElseThrow();
//
//        if (borne.getDisponibilite() == Disponibilite.DISPONIBLE) {
//            System.out.println("La borne est déjà libre.");
//            return false;
//        } else {
//            borne.setDisponibilite(Disponibilite.DISPONIBLE);
//            borneRepository.save(borne);
//            System.out.println("La borne a été libérée avec succès.");
//            return true;
//        }
//    }

    public List<Borne> rechercheBorne(

            Optional<Float> puissance,
            Optional<String> mode,
            Optional<String> connecteur) {
        List<Borne> resultats = new ArrayList<>();


        if (puissance.isPresent()) {
            List<Borne> resultatsPuissance = borneRepository.findByPuissance(puissance.get());
            resultats.addAll(resultatsPuissance);
        }

        if (mode.isPresent()) {
            Mode modeValue = Mode.valueOf(mode.get());
            List<Borne> resultatsMode = borneRepository.findByMode(modeValue);
            resultats.addAll(resultatsMode);
        }

        if (connecteur.isPresent()) {
            Connecteur connecteurValue = Connecteur.valueOf(connecteur.get());
            List<Borne> resultatsConnecteur = borneRepository.findByConnecteur(connecteurValue);
            resultats.addAll(resultatsConnecteur);
        }

        return resultats;
    }




//    public List<String> findStations(Disponibilite disponibilite, float puissance, Mode mode, Connecteur connecteur, float autonomieRestante) {
//        List<Borne> bornes = borneRepository.findByDisponibiliteAndPuissanceAndModeAndConnecteur(disponibilite, puissance, mode, connecteur);
//        List<String> result = new ArrayList<>();
//
//        if (bornes.isEmpty()) {
//            result.add("Aucune station ne correspond aux critères de recherche donnés");
//            return result;
//        }
//
//        for (Borne borne : bornes) {
//            List<Stations> stations = stationsRepository.findByBornes(borne.getId());
//            for (Stations station : stations) {
//                Double latitudeVoiture = 0.0;
//                Double longitudeVoiture = 0.0;
//                double distance = distance(station.getLatitude(), station.getLongitude(), latitudeVoiture, longitudeVoiture);
//                if (distance < autonomieRestante) {
//                    String stationInfo =
//                            "Station : " + station.getId() + "\n" +
//                                    "Puissance de la borne : " + borne.getPuissance() + " kW\n" +
//                                    "Temps de charge : " + borne.getTempsCharge() + " heure(s)\n" +
//                                    "Mode de charge : " + borne.getMode() + "\n" +
//                                    "Connecteur de la borne : " + borne.getConnecteur() + "\n" +
//                                    "Distance à la station : " + distance + " km\n\n";
//                    result.add(stationInfo);
//                }
//            }
//        }
//
//        return result;
//    }
//
//    public static double distance(double lat1, double lon1, double lat2, double lon2) {
//        final double R = 6371e3; // Earth radius in meters
//        double phi1 = Math.toRadians(lat1);
//        double phi2 = Math.toRadians(lat2);
//        double deltaPhi = Math.toRadians(lat2 - lat1);
//        double deltaLambda = Math.toRadians(lon2 - lon1);
//
//        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
//                Math.cos(phi1) * Math.cos(phi2) *
//                        Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return R * c;
//    }
//
//    public String find(Disponibilite disponibilite, float puissance, Mode mode, Connecteur connecteur, float autonomieRestante) {
//        List<Borne> resultats = borneRepository.findByDisponibiliteAndPuissanceAndModeAndConnecteur(disponibilite, puissance, mode, connecteur);
//        if (resultats.isEmpty()) {
//            return "Aucune station de recharge ne correspond à votre recherche.";
//        } else {
//            StringBuilder message = new StringBuilder("Voici les stations de recharge qui correspondent à votre recherche :\n");
//            for (Borne borne : resultats) {
//                List<Stations> stations = stationsRepository.findByBornes(borne.getId());
//                for (Stations station : stations) {
//                    Double latitudeVoiture = 0.0;
//                    Double longitudeVoiture = 0.0;
//                    double distance = distance(station.getLatitude(), station.getLongitude(), latitudeVoiture, longitudeVoiture);
//                    if (distance < autonomieRestante) {
//                        message.append("Nom de la station : ").append(station.getId()).append("\n")
//                                .append("Puissance de la borne : ").append(borne.getPuissance()).append(" kW\n")
//                                .append("Temps de charge : ").append(borne.getTempsCharge()).append(" heure(s)\n")
//                                .append("Mode de charge : ").append(borne.getMode()).append("\n")
//                                .append("Connecteur de la borne : ").append(borne.getConnecteur()).append("\n")
//                                .append("Disponibilité : ").append(borne.getDisponibilite()).append("\n\n");
//
//                    }
//                }
//            }
//            return message.toString();
//        }
//    }




//borne Approved
public List<Borne> findAllApprovedBornes() {
    List<Borne> approvedBornes = new ArrayList<>();
    List<Borne> allBornes = borneRepository.findAll();

    // Filtrer les bornes approuvées
    for (Borne borne : allBornes) {
        if (borne.getStatus().equals("approuved")) {
            approvedBornes.add(borne);
        }
    }

    return approvedBornes;
}

}



