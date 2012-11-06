/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


import java.io.File;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

@WebServlet(name = "profile_page", urlPatterns = {"/profile_page"})
public class profile_page extends HttpServlet {

    public static void generate_page(String username, Connection con, PrintWriter out, String path) {
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
        out.println("<link href=\"profilecss.css\" rel=\"stylesheet\" type=\"text/css\" />");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>User : " + username + "</p>");

        out.println(" <form action=\"upload_photos\" method=\"post\" \n"
                + "   enctype=\"multipart/form-data\" \n"
                + "   name=\"productForm\" id=\"productForm\">\n"
                + "<p>\n"
                + "mporeite proairetika na dwsete ena titlo gia th fwtografia pou 8a kanete upload:\n"
                + "<input type=\"text\" name=\"textline\" size=\"30\">\n"
                + "</p> \n"
                + "\n"
                + "          \n"
                + "<p>\n"
                + "Please specify a file, or a set of files:\n"
                + "<input type=\"file\" name=\"datafile\" size=\"40\">\n"
                + "    </p>\n"
                + "<div>\n"
                + "<input type=\"submit\" value=\"UPLOAD\">\n"
                + "</div>\n"
                + "</form>");

        try {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from user_and_photo_id where name='" + username + "'");
            System.out.println("select * from user_and_photo_id where name='" + username + "'");
            while (rs.next()) {

                out.println("<div class=\"manos\">");
                                   out.println("<form action=\"edit\"  method=\"get\">"
                            + "    <input type=\"submit\" name=\"Edit xml\">"
                            + "    <input type=\"hidden\" name=\"photo\" value=\"" + rs.getString("photo_id") + "\">"
                            + "</form>");
                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();

                    Document my_doc = db.parse(new File(path + "/project_images/" + rs.getString("photo_id") + ".xml"));
                    NodeList challenges = my_doc.getElementsByTagName("image");

                    Node _ch = challenges.item(0);
                    if (_ch.getNodeType() == Node.ELEMENT_NODE) {
                        Element ch = (Element) _ch;

                        String caption = ch.getElementsByTagName("caption").item(0).getAttributes().item(0).getNodeValue();
                        String transform = ch.getElementsByTagName("transform").item(0).getAttributes().item(0).getNodeValue();

                        if (transform.equals("scale_class")) {
                            out.println("<img class=\"scale_class\" src=\"project_images/" + rs.getString("photo_id") + "\">");
                        } 
                        
                        else if (transform.equals("rotate")) {
                            out.println("<img class=\"rotate\" src=\"project_images/" + rs.getString("photo_id") + "\">");
                        }
                        
                         else if (transform.equals("rotate180")) {
                            out.println("<img class=\"rotate180\" src=\"project_images/" + rs.getString("photo_id") + "\">");
                         }
                          else if (transform.equals("rotate270")) {
                            out.println("<img class=\"rotate270\" src=\"project_images/" + rs.getString("photo_id") + "\">");
                      
                          }else {
                            out.println("<img src=\"project_images/" + rs.getString("photo_id") + "\">");
                        }


                        out.println("<p><a href='#'>" + caption + "</a></p>");
                        out.println("</div>");
                        out.println("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
        }


        out.println("</body>");
        out.println("</html>");
    }

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
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("name");

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/myflickr?"
                    + "user=root&password=123456";
            con = DriverManager.getConnection(connectionUrl);

            if (con != null) {
                System.out.println("connected to mysql");
                generate_page(username, con, out, this.getServletContext().getRealPath("/"));
            } else {
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
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
