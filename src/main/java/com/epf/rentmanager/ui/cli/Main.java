package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    //ClientService clientService = ClientService.getInstance();
    ApplicationContext context = new
            AnnotationConfigApplicationContext(AppConfiguration.class);
    ClientService clientService = context.getBean(ClientService.class);
    VehicleService vehicleService = context.getBean(VehicleService.class);
    ReservationService reservationService = context.getBean(ReservationService.class);

    public static void main(String[] args) throws ServiceException, DaoException {
        Main cli = new Main();
        cli.start();
    }

    public void start() throws ServiceException, DaoException {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            String choice = IOUtils.readString("Votre choix : ", true);
            switch (choice) {
                case "1":
                    createClient();
                    break;
                case "2":
                    createVehicle();
                    break;
                case "3":
                    createReservation();
                    break;
                case "4":
                    listClients();
                    break;
                case "5":
                    listVehicles();
                    break;
                case "6":
                    listReservations();
                    break;
                case "7":
                    listReservationsByClient();
                    break;
                case "8":
                    listReservationsByVehicle();
                    break;
                    case "9":
                    deleteClient();
                    break;
                case "10":
                    deleteVehicle();
                    break;
                case "11":
                    deleteReservation();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }


    private void displayMenu() {
        IOUtils.print(
                "Menu :\n" +
                        "1. Créer un Client\n" +
                        "2. Créer un Véhicule\n"+
                        "3. Créer une Réservation\n" +
                        "4. Lister tous les Clients\n" +
                        "5. Lister tous les Véhicules\n" +
                        "6. Lister toutes les Réservations\n" +
                        "7. Lister toutes les Réservations à un client donnée\n" +
                        "8. Lister toutes les Réservations à un véhicule donnée\n" +
                        "9. Supprimer un Client\n" +
                        "10.Supprimer un Véhicule\n" +
                        "11.Supprimer une Réservation\n" +
                        "0. Quitter"
        );

    }

    private void createClient() throws ServiceException, DaoException {
        String prenom = IOUtils.readString("Nouveau Client :\nPrenom :", true);
        String nom = IOUtils.readString("Nom :", true);
        String email = IOUtils.readString("Email : ", true);
        LocalDate localdate = IOUtils.readDate("DateDeNaissance ( format (dd/MM/yyyy) ) : ", true);
        clientService.create(new Client(prenom, nom, email, localdate));
    }

    private void createVehicle() throws ServiceException, DaoException {
        String constructeur = IOUtils.readString("Nouveau Véhicule :\nConstructeur :", true);
        String modele = IOUtils.readString("Modele : ", true);
        int nb_places = IOUtils.readInt("Nombre de places : ");
        vehicleService.create(new Vehicle(constructeur, modele, nb_places));
    }

    private void createReservation() throws ServiceException, DaoException {
        LocalDate debut = IOUtils.readDate("Début de la réservation ( format (dd/MM/yyyy) ) : ", true);
        LocalDate fin = IOUtils.readDate("Fin de la réservation ( format (dd/MM/yyyy) ) : ", true);
        int idClient = IOUtils.readInt("ID du client : ");
        int idVehicle = IOUtils.readInt("ID du véhicule : ");
        reservationService.create(new Reservation(idClient, idVehicle, debut, fin));

    }

    private void listClients() throws ServiceException, DaoException {
        clientService.findAll().forEach(client -> IOUtils.print(client.toString()));
    }

    private void listVehicles() throws ServiceException, DaoException {
        vehicleService.findAll().forEach(vehicle -> IOUtils.print(vehicle.toString()));
    }

    private void listReservations() throws ServiceException, DaoException {
        reservationService.findAll().forEach(reservation -> IOUtils.print(reservation.toString()));
    }

    private void listReservationsByClient() throws ServiceException, DaoException {
        long idClient = IOUtils.readInt("ID du client : ");
        reservationService.findByClientId(idClient).forEach(reservation -> IOUtils.print(reservation.toString()));
    }

    private void listReservationsByVehicle() throws ServiceException, DaoException {
        long idVehicle = IOUtils.readInt("ID du véhicule : ");
        reservationService.findByVehicleId(idVehicle).forEach(reservation -> IOUtils.print(reservation.toString()));
    }

    private void deleteClient() throws ServiceException, DaoException {
        long idASupprimer = IOUtils.readInt("ID du client à supprimer : ");
        IOUtils.print(clientService.findById(idASupprimer).toString());
        boolean confirmation = IOUtils.readString("Voulez-vous vraiment supprimer ce client ? (O/N)", true).equalsIgnoreCase("O");
        if (confirmation) {
            IOUtils.print("Suppression du client " + idASupprimer);
            clientService.delete(clientService.findById(idASupprimer));
        }
    }

    private void deleteVehicle() throws ServiceException, DaoException {
        long idASupprimer = IOUtils.readInt("ID du véhicule à supprimer : ");
        IOUtils.print(vehicleService.findById(idASupprimer).toString());
        boolean confirmation = IOUtils.readString("Voulez-vous vraiment supprimer ce véhicule ? (O/N)", true).equalsIgnoreCase("O");
        if (confirmation) {
            IOUtils.print("Suppression du véhicule " + idASupprimer);
            vehicleService.delete(vehicleService.findById(idASupprimer));
        }
    }

    private void deleteReservation() throws ServiceException, DaoException {
        long idASupprimer = IOUtils.readInt("ID de la réservation à supprimer : ");
        IOUtils.print(reservationService.findById(idASupprimer).toString());
        boolean confirmation = IOUtils.readString("Voulez-vous vraiment supprimer cette réservation ? (O/N)", true).equalsIgnoreCase("O");
        if (confirmation) {
            IOUtils.print("Suppression de la réservation " + idASupprimer);
            reservationService.delete(reservationService.findById(idASupprimer));
        }
    }


}

