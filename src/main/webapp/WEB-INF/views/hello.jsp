<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en-GB" class="no-js ie6"> <![endif]-->
<!--[if IE 7   ]>      <html lang="en-GB" class="no-js ie7"> <![endif]-->
<!--[if IE 8   ]>      <html lang="en-GB" class="no-js ie8"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-GB" class="no-js"> <!--<![endif]-->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Hello Java</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
    </head>
    <body>
        <!-- The prefix "nc" stands for "nano container"-->
        <div >
		Hello World! Service URI is ${serviceURI}. Port: ${port}
        </div>
        <div >
		Session testing... Ports: ${ports}
        </div>
        
    </body>
</html>