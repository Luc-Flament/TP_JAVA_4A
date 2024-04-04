package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY,	PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());
			preparedStatement.execute(); //Maybe we should use executeUpdate() instead ?
			ResultSet resultset =preparedStatement.getGeneratedKeys();
			resultset.next();
			return resultset.getInt(1);
		} catch (SQLException e) {
			throw new DaoException("error creating vehicle\n"+e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicle.getId());
			preparedStatement.execute();
			return vehicle.getId();
		} catch (SQLException e) {
			throw new RuntimeException("error deleting vehicle\n"+e.getMessage());
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet resultset=preparedStatement.executeQuery();
			resultset.next();
			String constructeur=resultset.getString("constructeur");
			String modele=resultset.getString("modele");
			int nb_places=resultset.getInt("nb_places");
			return new Vehicle(id,constructeur,modele,nb_places);
		} catch (SQLException e) {
			throw new RuntimeException("error finding the vehicle by id\n"+e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet resultset=preparedStatement.executeQuery();
			ArrayList<Vehicle> ListeDesVehicles = new ArrayList<Vehicle>();
			while (resultset.next()){
				int id=resultset.getInt("id");
				String constructeur=resultset.getString("constructeur");
				String modele=resultset.getString("modele");
				int nb_places=resultset.getInt("nb_places");
				ListeDesVehicles.add(new Vehicle(id,constructeur,modele,nb_places));
			}
			return ListeDesVehicles;
		} catch (SQLException e) {
			throw new RuntimeException("error finding vehicles\n"+e.getMessage());
		}
		
	}

	public long count() throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT COUNT(*) FROM Vehicle;");
			ResultSet resultset=preparedStatement.executeQuery();
			resultset.next();
			return resultset.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException("error counting vehicles\n"+e.getMessage());
		}
	}
	

}
