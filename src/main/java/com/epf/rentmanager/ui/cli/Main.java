package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;

public class Main {
    ClientService clientService = ClientService.getInstance();
    VehicleService vehicleService = VehicleService.getInstance();

    public static void main(String[] args) throws ServiceException, DaoException {
            Main cli= new Main();
            cli.start();
        }
        public void start() throws ServiceException, DaoException {
            boolean exit = false;
            while (!exit) {
                displayMenu();
                String choice = IOUtils.readString("Votre choix : ",true);
                switch (choice) {
                    case "1":
                        createClient();
                        break;
                    case "2":
                        listClients();
                        break;
                    case "3":
                        createVehicle();
                        break;
                    case "4":
                        listVehicles();
                        break;
                    case "5":
                        deleteClient();
                        break;
                    case "6":
                        deleteVehicle();
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
                    "Menu :\n"+
                            "1. Créer un Client\n"+
                            "2. Lister tous les Clients\n"+
                            "3. Créer un Véhicule\n"+
                            "4. Lister tous les Véhicules\n"+
                            "5. Supprimer un Client\n"+
                            "6. Supprimer un Véhicule\n"+
                            "0. Quitter"
            );

        }

        private void createClient() throws ServiceException, DaoException {
            String prenom = IOUtils.readString("Nouveau Client :\nPrenom :",true);
            String nom = IOUtils.readString("Nom :",true);
            String email = IOUtils.readString("Email : ",true);
            LocalDate localdate = IOUtils.readDate("DateDeNaissance ( format (dd/MM/yyyy) ) : ",true);
            clientService.create(new Client(prenom,nom,email,localdate));
        }
        private void createVehicle() throws ServiceException, DaoException {
            String constructeur = IOUtils.readString("Nouveau Véhicule :\nConstructeur :",true);
            String modele = IOUtils.readString("Modele : ",true);
            int nb_places = IOUtils.readInt("Nombre de places : ");
            vehicleService.create(new Vehicle(constructeur,modele,nb_places));
        }
        private void listClients() throws ServiceException, DaoException {
            clientService.findAll().forEach(client -> IOUtils.print(client.toString()));
        }
        private void listVehicles() throws ServiceException, DaoException {
            vehicleService.findAll().forEach(vehicle -> IOUtils.print(vehicle.toString()));
        }
        private void deleteClient() throws ServiceException, DaoException {
            long idASupprimer = (long) IOUtils.readInt("ID du client à supprimer : ");
            IOUtils.print(clientService.findById(idASupprimer).toString());
            boolean confirmation = IOUtils.readString("Voulez-vous vraiment supprimer ce client ? (O/N)",true).equalsIgnoreCase("O");
            if (confirmation){
                IOUtils.print("Suppression du client "+idASupprimer);
                clientService.delete(clientService.findById(idASupprimer));
            }
        }
        private void deleteVehicle() throws ServiceException, DaoException {
            long idASupprimer = (long) IOUtils.readInt("ID du véhicule à supprimer : ");
            IOUtils.print(vehicleService.findById(idASupprimer).toString());
            boolean confirmation = IOUtils.readString("Voulez-vous vraiment supprimer ce véhicule ? (O/N)",true).equalsIgnoreCase("O");
            if (confirmation){
                IOUtils.print("Suppression du véhicule "+idASupprimer);
                vehicleService.delete(vehicleService.findById(idASupprimer));
            }
        }
    }

