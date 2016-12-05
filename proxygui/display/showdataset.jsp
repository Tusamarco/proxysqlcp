<%@ page import="java.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="net.tc.isma.auth.security.*" %>
<%@ page import="net.tc.isma.persister.*" %>
<%@ page import="net.tc.isma.persister.IsmaPersister" %>
<%@ page import="net.tc.isma.utils.*" %>
<%@ page import="net.tc.isma.lang.*" %>
<%@ page import="net.tc.isma.data.hibernate.*" %>
<%@ page import="net.tc.isma.data.objects.*" %>
<%@ page import="net.tc.isma.comparators.*" %>
<%@ page import="net.tc.isma.data.referencepicker.*" %>
<%@ page import="net.tc.isma.actions.*" %>
<%@ page import="net.tc.isma.actions.generic.*" %>
<%@ page import="net.tc.isma.request.generic.*" %>
<%@ page import="org.fao.ncp.objects.*" %>
<%@ page import="net.tc.isma.lang.LanguageSelector" %>
<%@ page import="org.fao.ncp.common.reporter.xmlObjectGenerator" %>

<%!static final String[] REQUEST_PARAMETERS = {"*"};%>
<%try{
  // Generic
  HttpSession sessioncheck = request.getSession(true);
  requestImpl requestWrapper = (requestImpl)request.getAttribute("requestImplementation");
  results resultsData = null;
  HttpQuery qs = null;
  String lang = Text.defaultTo(request.getParameter("lang"),"en");
  String reportLanguage = lang;
  String version = Text.defaultTo(request.getParameter("version"),"ext");
  Locale locale = new Locale(lang);
  IniStyleResourceBundle rsc = (IniStyleResourceBundle)IsmaPersister.getResourceBundle(locale);
  List resultList = null;
  UserBase user = null;
  int cversion = 2;

  boolean reload = (request.getParameter("reload")!=null)?Boolean.getBoolean(request.getParameter("reload")):false;

  if(requestWrapper != null && requestWrapper.getResult() != null)
  {
    reload = requestWrapper.getReload();
    resultsData = (results)requestWrapper.getResult();
    qs = requestWrapper.getHttpQuery(REQUEST_PARAMETERS);
    lang = requestWrapper.getLanguage();
    version = requestWrapper.getVersion();
    locale = requestWrapper.getLocale();
    rsc = (IniStyleResourceBundle)IsmaPersister.getResourceBundle(locale);
    resultList = (List) resultsData.get(List.class);
    user = requestWrapper.getUserBean();
  }
  LanguageSelector ls = new LanguageSelector(lang);
  String HOME = IsmaPersister.getMAINROOT();

  //**************************************************************
  Reports report = null;
  xmlObjectGenerator xo = new xmlObjectGenerator();

  if(resultsData.get(Reports.class) != null)
  {
    report = (Reports)resultsData.get(Reports.class);
    reportLanguage = report.getLanguage()!=null?report.getLanguage():lang;
  }
  else
  {
    %>
    <html><head><link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css"></head>
      <body><center> Invalid Report, please return to the <a href="/ncp/ncp?ac=ncpcontrol">home page</a> selection</center></body><html>
    <%
  }
  Long reportId = null;
  Long datasetId = null;


  if(resultsData.get("reportid.form.report") != null)
    reportId = (Long)resultsData.get("reportid.form.report");

  if(qs.get("datasetid") != null)
    datasetId = new Long(qs.get("datasetid"));


  Datasets dataSet = null;

  if(datasetId != null && !datasetId.equals("") && report !=  null)
    dataSet = report.getDataSet(datasetId);


//  Map areas = null;
//  areas = (Map)resultsData.get("areas.all.objects");



%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="Content-Type"  content="text/html;CHARSET=UTF-8">
	<link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css">
	<title>Untitled</title>

</head>

<body class="bodyText">
<center>
<a href="javascript:close();">close</a>
</center>
<P>
<!--HEADER END -->
    <%if(dataSet != null)
    {

      if(dataSet.getData() != null ) //&& dataSet.getXml() == null
      {
        Iterator itDs = dataSet.getData().iterator();
        int i = 0 ;
        %>
      <table align="center" width="550px"  border="1" class="tbDatasetPreview">
        <%
        while(itDs.hasNext())
        {
          Data d = (Data) itDs.next();
          if(i == 0)
          {
            out.println("<tr class='thDatasetPreview'>" + this.getTaggedRowFromCSVLine(d.getMetaheaderString(),"tbDatasetPreviewHeader") + this.getTaggedRowFromCSVLine(d.getRowheader(),"tbDatasetPreviewHeader") + "</tr>");
            i++;
          }
          out.println("<tr class='trDatasetPreview'>" + this.getTaggedRowFromCSVLine(d.getMetavaluesString(),"tbDatasetPreviewData") + this.getTaggedRowFromCSVLine(d.getRowvalues(),"tbDatasetPreviewData") + "</tr>");
        }
        %></table><%
      }
//      else if(dataSet.getXml() != null && !dataSet.getXml().equals(""))
//      {
//        String toPrint = xo.getObjectsXmlTransformedAsString(dataSet,ls,true);
//
//        out.println(toPrint);
//      }
    }%>


</body>
</html>
<%
}
catch(Throwable th)
{th.printStackTrace();}
%>
<%!
private String getTaggedRowFromCSVLine(String s, String style)
{
  String[] ars = s.split(",");
  StringBuffer sb = new StringBuffer();

  for(int i = 0 ; i < ars.length ; i++)
  {
    String sa = ars[i].replaceAll("null","&nbsp;");
    if(sa.equals("") ||sa.equals(" "))
      sa = "&nbsp;";

    sb.append("<td class='" + style +"'>" + sa + "</td>");
  }
  return sb.toString();

}

%>
