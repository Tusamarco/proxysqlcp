<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.xalan.xslt.EnvironmentCheck"%>
<%
    Hashtable tb = (new EnvironmentCheck()).getEnvironmentHash();

    long freeMemory = Runtime.getRuntime().freeMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    long occupiedMemory = totalMemory - freeMemory;
    StringWriter sw = new StringWriter();

    boolean environmentOK = (new EnvironmentCheck()).checkEnvironment (new PrintWriter(sw)); %> <html>  <head>
  <meta http-equiv="Refresh" content="300; URL=default.jsp" />
  <title><%=freeMemory%>/<%=totalMemory%>/<%=occupiedMemory%></title>
 </head>
 <body>
     <xmp>
   <%
     Iterator it = tb.keySet().iterator();
     while(it.hasNext())
     {
       String name = (String)it.next();
       out.println(name + " = " + tb.get(name).toString());

     }

  %>
  </xmp>
   <pre>aa
  <center>System informations</center>
  Free Memory  = <%=freeMemory%>
  Total Memory = <%=totalMemory%>
  Occupied Memory = <%=occupiedMemory%>
  </pre>
  Environmental check:<br>
     <xmp><%=sw.toString()%>  </xmp>

 </body>
</html>
