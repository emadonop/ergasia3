 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.servlet.annotation.WebServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;


@WebServlet(name = "project3", urlPatterns = {"/project3"})
public class project3 extends HttpServlet {
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Statement stmt;
        ResultSet rs;
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/myflickr?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            if (con != null) {
                System.out.println("connected to mysql");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        }
  
        try {
            stmt = con.createStatement();

            System.out.println("SELECT * FROM flickrusers WHERE name='" + username + "'");
            rs = stmt.executeQuery("SELECT * FROM flickrusers WHERE name='" + username + "'");

             while(rs.next()){
               
                if ( rs.getObject(1).toString().equals(username)) {
                    
                    out.println("<h1>To username pou epileksate uparxei hdh</h1>");
                    out.println("<a href=\"project3.html\">parakalw dokimaste kapoio allo.</a>");
                    
                    stmt.close();
                    rs.close();
                    return;
                    
                }
               
             }
             stmt.close();
             rs.close();
             
             stmt = con.createStatement();
             
             if ( !stmt.execute("INSERT INTO flickrusers VALUES('" + username+"', '" + password + "')") ) {
                 out.println("<h1>Your registration is completed  " + username + "</h1>");
                 out.println("<a href=\"index.jsp\">go to the login menu</a>");
             } else {
                 out.println("<h1>To username pou epileksate uparxei hdh</h1>");
                 out.println("<a href=\"project3.html\">Register</a>");
             }
        } catch (SQLException e) {
                throw new ServletException("Servlet Could not display records.", e);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}