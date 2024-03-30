package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY);
			preparedStatement.setLong(1, reservation.getClient_id());
			preparedStatement.setLong(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.execute();
			return preparedStatement.getGeneratedKeys().getLong("id");
		} catch (SQLException e) {
			throw new DaoException("error creating reservation\n"+e.getMessage());
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setLong(1, reservation.getId());
			preparedStatement.execute();
			return reservation.getId();
		} catch (SQLException e) {
			throw new RuntimeException("error deleting reservation\n"+e.getMessage());
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ResultSet resultset=preparedStatement.executeQuery();
			ArrayList<Reservation> ListeDesReservations = new ArrayList<Reservation>();
			while (!resultset.next()){
				long id=preparedStatement.getGeneratedKeys().getLong("id");
				long vehicleId=preparedStatement.getGeneratedKeys().getLong("vehicle_id");
				LocalDate debut= preparedStatement.getGeneratedKeys().getDate("debut").toLocalDate();
				LocalDate fin= preparedStatement.getGeneratedKeys().getDate("fin").toLocalDate();
				ListeDesReservations.add(new Reservation(id,clientId,vehicleId,debut,fin));
			}
			return ListeDesReservations;
		} catch (SQLException e) {
			throw new RuntimeException("error finding reservations\n"+e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ResultSet resultset=preparedStatement.executeQuery();
			ArrayList<Reservation> ListeDesReservations = new ArrayList<Reservation>();
			while (!resultset.next()){
				long id=preparedStatement.getGeneratedKeys().getLong("id");
				long clientId=preparedStatement.getGeneratedKeys().getLong("client_id");
				LocalDate debut= preparedStatement.getGeneratedKeys().getDate("debut").toLocalDate();
				LocalDate fin= preparedStatement.getGeneratedKeys().getDate("fin").toLocalDate();
				ListeDesReservations.add(new Reservation(id,clientId,vehicleId,debut,fin));
			}
			return ListeDesReservations;
		} catch (SQLException e) {
			throw new RuntimeException("error finding reservations\n"+e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {

		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet resultset=preparedStatement.executeQuery();
			ArrayList<Reservation> ListeDesReservations = new ArrayList<Reservation>();
			while (resultset.next()){
				long id=preparedStatement.getGeneratedKeys().getLong("id");
				long clientId=preparedStatement.getGeneratedKeys().getLong("client_id");
				long vehicleId=preparedStatement.getGeneratedKeys().getLong("vehicle_id");
				LocalDate debut= preparedStatement.getGeneratedKeys().getDate("debut").toLocalDate();
				LocalDate fin= preparedStatement.getGeneratedKeys().getDate("fin").toLocalDate();
				ListeDesReservations.add(new Reservation(id,clientId,vehicleId,debut,fin));
			}
			return ListeDesReservations;
		} catch (SQLException e) {
			throw new RuntimeException("error finding reservations\n"+e.getMessage());
		}
	}
}
