package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;

import java.util.List;

public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {
        if (reservation.getVehicle_id() <= 0) {
            throw new ServiceException("error : VehicleID is empty");
        }
        if (reservation.getClient_id() <= 0) {
            throw new ServiceException("error : ClientID is empty");
        }
        //check if the client exists
        ClientDao clientDao = ClientDao.getInstance();
        Client client = clientDao.findById(reservation.getClient_id());
        if (client == null) {
            throw new ServiceException("error : Client does not exist");
        }
        //check if the vehicle exists
        VehicleService vehicleService = VehicleService.getInstance();
        Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
        if (vehicle == null) {
            throw new ServiceException("error : Vehicle does not exist");
        }
        return reservationDao.create(reservation);
    }
    public Reservation findById(long id) throws ServiceException, DaoException {
        return reservationDao.findById(id);
    }
    public List<Reservation> findByVehicleId(long id) throws ServiceException, DaoException {
        return reservationDao.findResaByVehicleId(id);
    }
    public List<Reservation> findByClientId(long id) throws ServiceException, DaoException {
        return reservationDao.findResaByClientId(id);
    }

    public long delete(Reservation reservation) throws ServiceException, DaoException {
        return reservationDao.delete(reservation);
    }


    public List<Reservation> findAll() throws ServiceException, DaoException {
        return reservationDao.findAll();
    }

}
