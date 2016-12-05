<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%//@ page import="org.fao.waicent.db.*"%>
<%//@ page import="org.fao.waicent.fpmis.*"%>
<HTML>
<HEAD>
 <head>
    <!-- META http-equiv="Refresh" content="3; URL=info.jsp" /-->
    <TITLE></TITLE>
</HEAD>
<BODY>
<%
	//dbConnectionManager conPool = (dbConnectionManager)application.getAttribute("CONPOOL");

	if ( conPool == null )
		throw new ServletException("Please, login before using this tool");

	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	//conPool.dump(pw);
	%>
		<!-- p><a href="https://extranet.fao.org:8881/fpmis/">non IIS FPMIS link</a></p-->
		<!-- DBConnectionManager dump -->
		<!-- p><xmp><%//=sw.toString()%></xmp></p-->
	<%

	Properties properties = System.getProperties();
	Enumeration e = properties.propertyNames();
	while ( e.hasMoreElements() ) {
		String key = (String)e.nextElement();
		String val = (String)properties.getProperty(key);
		%>
		<p>
		 <%=key%>: <%=val%>
		</p>
		<%
	}

%>
</BODY>
</HTML>
