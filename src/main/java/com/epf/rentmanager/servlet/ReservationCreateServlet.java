package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationService reservationService =ReservationService.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long client_id = Long.parseLong(request.getParameter("user"));
        long vehicle_id = Long.parseLong(request.getParameter("model"));
        LocalDate debut = LocalDate.parse(request.getParameter("start_date"));
        LocalDate fin = LocalDate.parse(request.getParameter("end_date"));
        Reservation reservation = new Reservation(client_id, vehicle_id, debut, fin);
        try {
            reservationService.create(reservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException("error" + e);
        }

    }
}
