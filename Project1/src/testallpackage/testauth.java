package testallpackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class testauth extends HttpServlet {
    
    protected void auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try{
            HttpSession session=request.getSession(false);
            String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort();
                    String cPath = request.getContextPath();
                    String sPath = request.getServletPath();

                    String nodeId = System.getProperty("weblogic.Name");
                    String hostName = System.getProperty("java.rmi.server.hostname");
                    //String variableString = (String)session.getAttribute("SESSION_TEST_VARIABLE");
                    String username = request.getRemoteUser();

                    int count = 0;
//                    if(variableString != null) {
//                            count = Integer.parseInt(variableString);
//                            count++;
//                    }
            //session.setAttribute("SESSION_TEST_VARIABLE", String.valueOf(count));
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet auth</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> </h1>");
            out.println("<table columns=2>");
            //out.println("<tr><td><font color=blue>The sessionId is:              </font></td><td>"+session.getId()+"</td>");
            //out.println("<tr><td><font color=blue>The sessionObj is:              </font></td><td>"+session+"</td>");
            out.println("<tr><td><font color=blue>The nodeId is:         </font></td><td>"+nodeId+"</td>");
            out.println("<tr><td><font color=blue>The server hostName is:                </font></td><td>"+hostName+"</td>");
            out.println("<tr><td><font color=blue>User:               </font></td><td>"+username+"</td>");
            out.println("<tr><td><font color=blue># of requests placed on session:               </font></td><td>"+count+"</td>");
            out.println("</table>");
            out.println("<h3><a href=\"logout.jsp\"> Click Here to Logout</a></h3>");
            out.println("</body>");
            out.print("</html>");
        }
        finally {
                   out.close();
               }
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
    //@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("auth") != null) {
            System.out.println("doPost first IF");
            auth(request, response);
        } 
        /* else if (request.getParameter("button3") != null) {
            myClass.method3();
        } else {
            // ???
            //         }*/
    }    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            System.out.println("doGet go");
        auth(request, response);
    }
}
