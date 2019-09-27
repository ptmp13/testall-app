<%-- 
    Document   : index
    Created on : 04.04.2012, 10:59:14
    Author     : frank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>StuckThreadsForFree!</title>
    </head>
    <body>
        <h1>StuckThreadForFree</h1>
        <FORM NAME="data" METHOD="POST" Action="/testapp-all/stuck">

            Number of threads to run <INPUT TYPE="TEXT"   NAME="numberOfThreads" VALUE="3"><br/>
            Seconds to keep threads busy <INPUT TYPE="TEXT"   NAME="timeBusy" VALUE="700"><br/>
            <p/>

            <p><b>Choose a way make them stuck:</b><br>
                <input type="RADIO" value="calc" name="select" id="navRadio01" >
                <label for="navRadio01">Calculating sin()</label><br>
                <input type="RADIO" value="sleep"    name="select" id="navRadio02" checked="checked">
                <label for="navRadio02">Thread.sleep()</label><br>


            <p/>
            <INPUT TYPE="SUBMIT" NAME="go" VALUE="submit">
	    <p/>
	</FORM>
	<FORM NAME="data" METHOD="POST" Action="/testapp-all/stuck">
	    </p>
	    Test Datasource Name: <INPUT TYPE="TEXT" NAME="nameDataSource" VALUE="jdbc/oradmnDS"><br/>
	    </p>
            <INPUT TYPE="SUBMIT" NAME="TestDS" VALUE="testDS">
	    </p>
        </FORM>
        <FORM NAME="data" METHOD="POST" Action="/testapp-all/stuck">
            </p>
            Test Insert Datasource Name: <INPUT TYPE="TEXT" NAME="nameDataSource" VALUE="jdbc/oradmnDS"><br/>
            </p>
            <INPUT TYPE="SUBMIT" NAME="insertTestTable" VALUE="insert">
            </p>
        </FORM>        
        <FORM NAME="data" METHOD="POST" Action="/testapp-all/stuck">
            </p>
            executeProc Datasource Name: <INPUT TYPE="TEXT" NAME="nameDataSource" VALUE="jdbc/oradmnDS"><br/>
            </p>
            <INPUT TYPE="SUBMIT" NAME="executeProc" VALUE="insert">
            </p>
        </FORM>    
        <FORM NAME="data" METHOD="POST" Action="/testapp-all/stuck">
            </p>
            executeProcWithReturn Datasource Name: <INPUT TYPE="TEXT" NAME="nameDataSource" VALUE="jdbc/sed_repositoryDS"><br/>
            </p>
            <INPUT TYPE="SUBMIT" NAME="executeProcWithReturn" VALUE="insert">
            </p>
        </FORM>         
        <FORM NAME="data" METHOD="POST" Action="/testapp-all/stuck">
            </p>
            Simulate OOM Metaspace<br/>
            </p>
            <INPUT TYPE="SUBMIT" NAME="MetaspaceOOM" VALUE="submit">
            </p>
        </FORM>        
	</br>
	</br>
	<h3><font color=maroon><a href="testauth">Access Protected Resource</a></font></h3>

    </table>
</body>
</html>
