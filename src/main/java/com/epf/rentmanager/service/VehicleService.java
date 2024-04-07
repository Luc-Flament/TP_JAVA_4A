package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService(VehicleDao vehicleDao) {this.vehicleDao = vehicleDao;}
	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		if (vehicle.getConstructeur().isEmpty()){throw new ServiceException("error : the brand that makes the car must be referenced");}
		if (vehicle.getModele().isEmpty()){throw new ServiceException("error : the type of car must be referenced");}
		if (vehicle.getNb_places()<=1){throw new ServiceException("A car has at least 2 seat please add how many seats are in the car");}
		if (vehicle.getNb_places()>=10){throw new ServiceException("A car has at most 9 seats please add how many seats are in the car");}
		return vehicleDao.create(vehicle);
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		return vehicleDao.findById(id);
		
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return vehicleDao.findAll();
	}

public long delete(Vehicle vehicle) throws ServiceException, DaoException {
		return vehicleDao.delete(vehicle);
	}
	
}
