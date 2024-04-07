package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

	private ClientDao clientDao;

	private ReservationDao reservationDao;
	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	
	public long create(Client client) throws ServiceException, DaoException {
		if (client.getNom().isEmpty() || client.getPrenom().isEmpty()){throw new ServiceException("error : the first or last name is empty");}
		if (!IsLegal(client)){throw new ServiceException("error : the client is not 18");}
		if (!FreeEmail(client)){throw new ServiceException("error : the email is already used");}
		return clientDao.create(client);
	}

	private boolean IsLegal(Client client) {
		return client.getNaissance().plusYears(18).isBefore(LocalDate.now());
	}

	public Client findById(long id) throws ServiceException, DaoException {
		return clientDao.findById(id);
	}

	private boolean FreeEmail(Client client){
		try {
			List<Client> list = clientDao.findAll();
			for (Client c : list) {
				if (c.getEmail().equals(client.getEmail())) {
					return false;
				}
			}
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
		return true;
	}


	public long delete(Client client) throws ServiceException, DaoException {
		return clientDao.delete(client);


	}


	public List<Client> findAll() throws ServiceException, DaoException {
		return clientDao.findAll();
	}
	
}
