package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {

	private static ClientDao instance = null;

	private ClientDao() {
	}

	public static ClientDao getInstance() {
		if (instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	public long create(Client client) throws DaoException{
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, client.getNom().toUpperCase(Locale.ROOT));
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.execute(); //Maybe we should use executeUpdate() instead ?
			ResultSet resultset =preparedStatement.getGeneratedKeys();
			resultset.next();
            return resultset.getInt(1);
		} catch (SQLException e) {
			throw new DaoException("error creating client\n"+e.getMessage());
		}
	}
	public long delete(Client client) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setLong(1, client.getId());
			preparedStatement.execute();
			return client.getId();
		} catch (SQLException e) {
			throw new RuntimeException("error deleting client\n"+e.getMessage());
		}
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet resultset=preparedStatement.executeQuery();
			resultset.next();
			String nom=resultset.getString("nom");
			String prenom=resultset.getString("prenom");
			String email=preparedStatement.getResultSet().getString("email");
			LocalDate Date= preparedStatement.getResultSet().getDate("naissance").toLocalDate();
            return new Client(id,nom,prenom,email,Date);
		} catch (SQLException e) {
			throw new RuntimeException("error finding the client by id\n"+e.getMessage());
		}
	}

	public List<Client> findAll() throws DaoException {
        try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet resultset=preparedStatement.executeQuery();
			ArrayList<Client> ListeDesClients = new ArrayList<Client>();
			while (resultset.next()){
				int id=resultset.getInt("id");
				String nom=resultset.getString("nom");
				String prenom=resultset.getString("prenom");
				String email=resultset.getString("email");
				LocalDate Date= resultset.getDate("naissance").toLocalDate();
				ListeDesClients.add(new Client(id,nom,prenom,email,Date));
			}
			return ListeDesClients;
		} catch (SQLException e) {
            throw new RuntimeException("error finding clients\n"+e.getMessage());
        }
	}

}
