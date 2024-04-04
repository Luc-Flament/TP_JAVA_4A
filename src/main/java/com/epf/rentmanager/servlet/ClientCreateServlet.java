package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import jdk.vm.ci.meta.Local;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private ClientService clientService = ClientService.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nom = request.getParameter("lastname");
        String prenom = request.getParameter("firstname");
        String Email = request.getParameter("Email");
        LocalDate clocktime= LocalDate.now();
        Client client = new Client(nom, prenom, Email, clocktime );
        try {
            clientService.create(client);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException("error" +e);
        }

    }

}
