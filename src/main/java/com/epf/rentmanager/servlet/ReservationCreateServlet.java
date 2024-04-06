package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epf.rentmanager.utils.IOUtils.print;


@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationService reservationService =ReservationService.getInstance();
    private ClientService clientService = ClientService.getInstance();
    private VehicleService vehicleService = VehicleService.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Vehicle> allVehicles = vehicleService.findAll();
            request.setAttribute("vehicles", allVehicles);
            List<Client> allClients = clientService.findAll();
            request.setAttribute("clients", allClients);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long client_id = Long.parseLong(request.getParameter("client"));
        long vehicle_id = Long.parseLong(request.getParameter("car"));
        String Stringdebut = request.getParameter("begin");
        String Stringfin = request.getParameter("end");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate debut= LocalDate.parse(Stringdebut, formatter);
        LocalDate fin= LocalDate.parse(Stringfin, formatter);
        Reservation reservation = new Reservation(client_id, vehicle_id, debut, fin);
        try {
            reservationService.create(reservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException("error" + e);
        }

    }
}
