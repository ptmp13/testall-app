package testallpackage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;

import java.sql.Types;

import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;
import javassist.ClassPool;

/**
 *
 * @author frank
 */

@WebServlet(urlPatterns = "/stuck")
public class StuckServlet extends HttpServlet {
    static ClassPool classPool = ClassPool.getDefault();
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
    @EJB
    private LongRunningEJB lr;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int numberOfThreads = Integer.parseInt(request.getParameter("numberOfThreads"));
        int timeBusy = Integer.parseInt(request.getParameter("timeBusy"));
        String select = request.getParameter("select");
        
        
        PrintWriter out = response.getWriter();
        try {
        
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet stuck</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet to create stuck threads called... </h1>");


            for (int i = 0; i < numberOfThreads; i++) {
               
                        
                if ("calc".equals(select)) {
                    out.println("asynchronously calling EJB method calc("+timeBusy+" sec) in iteration " + i + " at " + new Date() + "</br>");
                    lr.threadCalc(timeBusy);
                } else if ("sleep".equals(select)) {
                    out.println("asynchronously calling EJB method sleep("+timeBusy+" sec) in iteration " + i + " at " + new Date() + "</br>");
                lr.threadSleep(timeBusy);
                } else throw new IllegalArgumentException("no vaild select for thread blocking");
            }

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

	protected void testDS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context ctx = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String dsName = request.getParameter("nameDataSource");
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dsName);
			
			con = ds.getConnection();
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("select 'testdatasource' as test,'ok' as stat from dual");
			
			PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.print("<html><body><h2>Test Details</h2>");
            out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
            //out.print("<th>Employee ID</th>");
            //out.print("<th>Employee Name</th>");
            
            while(rs.next())
            {
                out.print("<tr>");
                out.print("<td>" + rs.getString("test") + "</td>");
                out.print("<td>" + rs.getString("stat") + "</td>");
                out.print("</tr>");
            }
            out.print("</table></body><br/>");
            
            //lets print some DB information
            out.print("<h3>Database Details</h3>");
            out.print("Database Product: "+con.getMetaData().getDatabaseProductName()+"<br/>");
            out.print("Database Driver: "+con.getMetaData().getDriverName());
            out.print("</html>");
            
		}catch(NamingException e){
			System.out.println("Exception not found DS");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stmt.close();
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}
			
		}
	}
        
	protected void insertTestTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context ctx = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String dsName = request.getParameter("nameDataSource");
		String sql = "INSERT INTO testins (id,text) VALUES (?,?)";
		//String sql = "CALL DSN8.DSN8ED2(?,?,?,?,?)";
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dsName);
			
			con = ds.getConnection();
	                pstmt = con.prepareStatement(sql);
                        //pcall = con.prepareCall(sql);
	                pstmt.setInt(1, 1);
		        pstmt.setString(2, "lalal");
			pstmt.executeUpdate();	
			PrintWriter out = response.getWriter();
	                response.setContentType("text/html");
	                out.print("<html><body><h2>Test Details</h2>");
	                out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
            //out.print("<th>Employee ID</th>");
            //out.print("<th>Employee Name</th>");
            
            
            //lets print some DB information
            out.print("<h3>Database Details</h3>");
            out.print("Database Product: "+con.getMetaData().getDatabaseProductName()+"<br/>");
            out.print("Database Driver: "+con.getMetaData().getDriverName());
            out.print("OK");
            out.print("</html>");
            
		}catch(NamingException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}
			
		}
	}

    protected void executeProcWithReturn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Context ctx = null;
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            //PreparedStatement pstmt = null;
            CallableStatement cstmt = null;
            String dsName = request.getParameter("nameDataSource");
            //String sql = "INSERT INTO testins (id,text) VALUES (?,?)";
            String sql = "{ CALL ? := sm_mdm.GET_CD_ID_BY_MDM_ID(1782400299) }";
            try{
                    ctx = new InitialContext();
                    DataSource ds = (DataSource) ctx.lookup(dsName);
                    
                    con = ds.getConnection();
                    //pstmt = con.prepareStatement(sql);
                    cstmt = con.prepareCall(sql);
                    cstmt.registerOutParameter(1, Types.DECIMAL);
                    //pstmt.setInt(1, 1);
                    //pstmt.setString(2, "lalal");
                    //pstmt.executeUpdate();  
                    //cstmt.registerOutParameter(2, Types.DECIMAL);

                    //cstmt.setInt(1, 1);
                    cstmt.execute();            
                
                    int gavno = cstmt.getInt(1);

                    cstmt.close();
                    //System.out.println("Retorno: " + str);
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html");
                    out.print("<html><body><h2>Test Details</h2>");
                    out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
        //out.print("<th>Employee ID</th>");
        //out.print("<th>Employee Name</th>");
        
        
        //lets print some DB information
        out.print("<h3>Database Details</h3>");
        out.print("Database Product: "+con.getMetaData().getDatabaseProductName()+"<br/>");
        out.print("Database Driver: "+con.getMetaData().getDriverName()+"<br/>");
        out.print("Result: " + gavno);
        out.print("</html>");
        
            }catch(NamingException e){
                    e.printStackTrace();
            } catch (SQLException e) {
                    e.printStackTrace();
            }finally{
                    try {
                            //cstmt.close();
                            con.close();
                            ctx.close();
                    } catch (SQLException e) {
                            System.out.println("Exception in closing DB resources");
                    } catch (NamingException e) {
                            System.out.println("Exception in closing Context");
                    }
                    
            }
    }

    protected void executeProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Context ctx = null;
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            //PreparedStatement pstmt = null;
            CallableStatement cstmt = null;
            String dsName = request.getParameter("nameDataSource");
            //String sql = "INSERT INTO testins (id,text) VALUES (?,?)";
            String sql = "CALL testproc1(?,?)";
            try{
                    ctx = new InitialContext();
                    DataSource ds = (DataSource) ctx.lookup(dsName);
                    
                    con = ds.getConnection();
                    //pstmt = con.prepareStatement(sql);
                    cstmt = con.prepareCall(sql);
                    cstmt.registerOutParameter(2, Types.DECIMAL);
                    //pstmt.setInt(1, 1);
                    //pstmt.setString(2, "lalal");
                    //pstmt.executeUpdate();  
                    //cstmt.registerOutParameter(2, Types.DECIMAL);

                    cstmt.setInt(1, 1);
                    cstmt.execute();            
                
                    int gavno = cstmt.getInt(2);

                    cstmt.close();
                    //System.out.println("Retorno: " + str);
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html");
                    out.print("<html><body><h2>Test Details</h2>");
                    out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
        //out.print("<th>Employee ID</th>");
        //out.print("<th>Employee Name</th>");
        
        
        //lets print some DB information
        out.print("<h3>Database Details</h3>");
        out.print("Database Product: "+con.getMetaData().getDatabaseProductName()+"<br/>");
        out.print("Database Driver: "+con.getMetaData().getDriverName());
        out.print(gavno);
        out.print("</html>");
        
            }catch(NamingException e){
                    e.printStackTrace();
            } catch (SQLException e) {
                    e.printStackTrace();
            }finally{
                    try {
                            //cstmt.close();
                            con.close();
                            ctx.close();
                    } catch (SQLException e) {
                            System.out.println("Exception in closing DB resources");
                    } catch (NamingException e) {
                            System.out.println("Exception in closing Context");
                    }
                    
            }
    }

    protected void HeapOOM()
    {
        StringBuilder s = new StringBuilder();
    
        while (true) {
            s.append("dummy");
        }
    }
    
    protected void MetaspaceOOM(HttpServletRequest request, HttpServletResponse response) throws Exception {    
        StringBuilder s = new StringBuilder();
        System.out.println("Start while!....");
        int i=0;
        while (true) {
            i++;
            Class clas = classPool.makeClass(
                         i + " outofmemory.OutOfMemoryErrorMetaspace ").toClass();
            //Print name of class loaded
            System.out.println(clas.getName());
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
	    System.out.println("doGet go");
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
	if (request.getParameter("go") != null) {
	    System.out.println("doPost first IF");
            processRequest(request, response);
        } 
	if (request.getParameter("TestDS") != null) {
	    System.out.println("doPost second IF");
            testDS(request, response);
        }
	if (request.getParameter("insertTestTable") != null) {
	    System.out.println("doPost insertTestTable IF");
            insertTestTable(request, response);
        }
        if (request.getParameter("executeProc") != null) {
            System.out.println("doPost executeProc IF");
            executeProc(request, response);
        }        
        if (request.getParameter("executeProcWithReturn") != null) {
            System.out.println("doPost executeProcWithReturn IF");
            executeProcWithReturn(request, response);
        }        
        if (request.getParameter("MetaspaceOOM") != null) {
            System.out.println("doPost MetaspaceOOM IF");
            try {
                MetaspaceOOM(request, response);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
	/* else if (request.getParameter("button3") != null) {
            myClass.method3();
        } else {
            // ???
            //         }*/
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
