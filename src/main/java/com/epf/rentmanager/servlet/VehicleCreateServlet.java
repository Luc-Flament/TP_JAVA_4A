package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService =VehicleService.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nb_places = Integer.parseInt(request.getParameter("seats"));
        Vehicle vehicle = new Vehicle(constructeur, modele, nb_places);
        try {
            vehicleService.create(vehicle);
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException("error" +e);
        }

    }

}
