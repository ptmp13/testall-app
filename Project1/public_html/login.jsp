<html>
    <head>
        <title>FormBased Authentication Demo in WebLogic Sample v2</title>
    </head>
    <body bgcolor="maroon" text="white">
        <center>
            <h2>Please Enter Your UserName &amp; Password (FormBased Auth Example)</h2>
            <form method="POST" action="j_security_check">
                <table border="5">
                    <tr>
                        <td>Username:</td>
                        <td>
                            <input type="text" name="j_username"/>
                        </td>
                    </tr>
                     
                    <tr>
                        <td>Password:</td>
                        <td>
                            <input type="password" name="j_password"/>
                        </td>
                    </tr>
                    <FORM NAME="data" METHOD="POST" Action="/testapp-all/testauth">
                    <tr>
                        <td colspan="2" align="right">
                            <input type="submit" name="auth" value="Login"/>
                        </td>
                    </tr>
                    </FORM>
                </table>
            </form>
        </center>
    </body>
</html>
