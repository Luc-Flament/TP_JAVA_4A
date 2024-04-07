package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
@Service
public class ReservationService {

    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao){this.reservationDao = reservationDao;}
    public long create(Reservation reservation) throws ServiceException, DaoException {
    if(!VehicleIsAvailable(reservation)){ throw new ServiceException("error : the vehicle is not available");};
    if(!TempsMaxReservationParUtilisateur(reservation)){ throw new ServiceException("error : the reservation is too long");};
        return reservationDao.create(reservation);
    }

    private boolean VehicleIsAvailable(Reservation reservation) {
        try {
            List<Reservation> list = reservationDao.findResaByVehicleId(reservation.getVehicle_id());
            for (Reservation r : list) {
                if (r.getDebut().isBefore(reservation.getDebut()) && r.getFin().isAfter(reservation.getDebut())) {
                    return false;
                }
                if (r.getDebut().isBefore(reservation.getFin()) && r.getFin().isAfter(reservation.getFin())) {
                    return false;
                }
                if (r.getDebut().isAfter(reservation.getDebut()) && r.getFin().isBefore(reservation.getFin())) {
                    return false;
                }
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean TempsMaxReservationParUtilisateur(Reservation reservation) {
        if (reservation.getDebut().plusDays(7).isBefore(reservation.getFin())) {
            return false;
        }
        return true;
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
