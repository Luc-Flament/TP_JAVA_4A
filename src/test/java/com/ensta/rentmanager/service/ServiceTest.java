package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @InjectMocks
    private ClientService clientService;
    @InjectMocks
    private ReservationService reservationService;
    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private VehicleDao vehicleDao;
    @Mock
    private ClientDao clientDao;
    @Mock
    private ReservationDao reservationDao;

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void shouldAnswerWithFalse() {assertFalse( false );}
    @Test
    public void DoesNotAddClientIfNot18() throws DaoException, ServiceException {
        Client client = new Client("Family Name", "Name", "Email", LocalDate.now());
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }
    @Test
    public void DoesAddClientIf18() throws DaoException, ServiceException {
        Client client = new Client("Family Name", "Name", "Email", LocalDate.of(2000, 1, 1));
        when(clientService.create(client)).thenReturn((long) 0);
        assertEquals(0, clientService.create(client));
    }

    @Test
    public void vehicleIsCreated() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle("Brand", "Model", 2000);
        when(vehicleService.create(vehicle)).thenReturn((long) 0);
        assertEquals(0, vehicleService.create(vehicle));
    }
/*
    @Test
    public void DoesNotAddClientIfEmailAlreadyUsed() throws DaoException, ServiceException {
        Client client = new Client("Family Name", "Name", "Email", LocalDate.of(2000, 1, 1));
        when(clientService.create(client)).thenReturn((long) 0);
        assertEquals(0, clientService.create(client));
        assertThrows(ServiceException.class, () -> clientService.create(client));
        //Ce test ne fonctionne pas, il faudrait que le mock garde en mémoire les clients ajoutés.
    }
*/



}



