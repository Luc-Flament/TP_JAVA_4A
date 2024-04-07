package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String Email = request.getParameter("email");
        String dateNaissance = request.getParameter("naissance");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate= LocalDate.parse(dateNaissance, formatter);
        Client client = new Client(nom, prenom, Email, localDate );
        try {
            clientService.create(client);
            print(request.getContextPath());
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException("error" +e);
        }

    }

}
