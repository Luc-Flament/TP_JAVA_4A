package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.ClientDao;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException, DaoException {
		if (client.getNom().isEmpty() || client.getPrenom().isEmpty()){throw new ServiceException("error : the first or last name is empty");}
		return clientDao.create(client);
	}

	public Client findById(long id) throws ServiceException, DaoException {
		return clientDao.findById(id);
	}

	public long delete(Client client) throws ServiceException, DaoException {
		return clientDao.delete(client);
	}


	public List<Client> findAll() throws ServiceException, DaoException {
		return clientDao.findAll();
	}
	
}
