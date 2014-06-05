<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="/antimoskal/pages/viewproduct">
       		<table>
        		<tr>
        		<td>
        		Enter BarCode:
        		</td>
        		<td>
        		<input type="text" name="barcode" />
           	 	</td>
           	 	</tr>
        		
        		<tr>
        		<td>
        		<input type="submit" name="okbutton" value="Search" />
        		</td>
           	 	</tr>
           	 </table>
        </form>

    </body> 
</html>
