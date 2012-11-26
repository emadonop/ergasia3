/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.util.Map.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 *
 * @author User
 */
@WebServlet(name = "stats", urlPatterns = {"/stats"}, loadOnStartup=3)
public class Stats extends HttpServlet implements LoginListener, RegisterListener, PhotoListener {
    HashSet<String> UsersLog;
    HashSet<String> UsersReg;
    HashMap<String, Integer> UsersPhotos;
    
    public void Login(String user)
    {
        UsersLog.add(user);
    }
    
    public void Register(String user)
    {
        UsersReg.add(user);
    }
    
    public void Photos(String user)
    {
        Integer number = (Integer) UsersPhotos.get(user);
        
        if ( number == null ) {
            UsersPhotos.put(user, new Integer(1));
        } else {
            number++;
            UsersPhotos.put(user, number);
            System.out.println(user + " : " + UsersPhotos.get(user));
        }
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
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Stats</title>");            
            out.println("</head>");
            out.println("<body id=\"body\">");
            
            out.println("<script>\n"
                + "setInterval(\"getStats()\" ,1000);\n"
                + "function getStats(){\n"
                + "    var req = new XMLHttpRequest();\n"
                + "    req.open('GET', 'http://'+location.host+'/ergasia3/stats' , false);\n"
                + "    req.send();\n"
                + "    \n"
                + "    if (req.status == 200) { \n"
                + "        \n"
                + "        var x = document.getElementById('body');\n"
                + "       if ( x.innerHTML!=req.responseText )\n"
                + "        x.innerHTML=req.responseText;\n"
                + "        \n"
                
                + "    }\n"
                + "}\n"
                + ""
                + "</script>\n");
            
            Integer photos = 0;
            
            Iterator iter = UsersPhotos.entrySet().iterator();
            
            while ( iter.hasNext() ) {
                Map.Entry<String, Integer> e = (Map.Entry) iter.next();
                
                if ( e.getValue() != null )
                    photos += (Integer)e.getValue();
                
            }
            
            out.println("Users Online: " + UsersLog.size() + "<br>");
            out.println("Users Registered: " + UsersReg.size()  + "<br>");
            out.println("Photos Uploaded:" + photos  + "<br>");
            
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
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
    
     public void init(ServletConfig config) throws ServletException {
        super.init(config);
        UsersLog = new HashSet();
        UsersReg = new HashSet();
        UsersPhotos = new HashMap();
        
        loginflickr.SetListener(this);
        project3.SetListener(this);
        upload_photos.SetListener(this);
     }
}
