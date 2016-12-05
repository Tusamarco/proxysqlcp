<%@page import="java.util.*"%>
<%@page import="java.util.*"%>
<%@page import="net.tc.isma.auth.security.*"%>
<%@page import="net.tc.isma.persister.*"%>
<%@page import="net.tc.isma.persister.IsmaPersister"%>
<%@page import="net.tc.isma.utils.*"%>
<%@page import="net.tc.isma.lang.*"%>
<%@page import="net.tc.isma.data.hibernate.*"%>
<%@page import="net.tc.isma.data.objects.*"%>
<%@page import="net.tc.isma.comparators.*"%>
<%@page import="net.tc.isma.data.referencepicker.*"%>
<%@page import="net.tc.isma.actions.*"%>
<%@page import="net.tc.isma.actions.generic.*"%>
<%@page import="net.tc.isma.request.generic.*"%>
<%@page import="org.fao.ncp.objects.*"%>
<%@page import="net.tc.isma.lang.LanguageSelector"%>
<%@page import="org.fao.ncp.common.reporter.*"%>
<%@page contentType="text/html"%>
<%!
  static final String[] REQUEST_PARAMETERS = {
      "*"};
%>
<%
  try {
    // Generic
    HttpSession sessioncheck = request.getSession(true);
    requestImpl requestWrapper = (requestImpl) request.getAttribute("requestImplementation");
    results resultsData = null;
    HttpQuery qs = null;
    String lang = Text.defaultTo(request.getParameter("lang"), "en");
    String reportLanguage = lang;
    String version = Text.defaultTo(request.getParameter("version"), "ext");
    Locale locale = new Locale(lang);
    IniStyleResourceBundle rsc = (IniStyleResourceBundle) IsmaPersister.getResourceBundle(locale);
    List resultList = null;
    UserBase user = null;
    int cversion = 2;
    boolean reload = (request.getParameter("reload") != null) ? Boolean.getBoolean(request.getParameter("reload")) : false;
    if (requestWrapper != null && requestWrapper.getResult() != null) {
      reload = requestWrapper.getReload();
      resultsData = (results) requestWrapper.getResult();
      qs = requestWrapper.getHttpQuery(REQUEST_PARAMETERS);
      lang = requestWrapper.getLanguage();
      version = requestWrapper.getVersion();
      locale = requestWrapper.getLocale();
      rsc = (IniStyleResourceBundle) IsmaPersister.getResourceBundle(locale);
      resultList = (List) resultsData.get(List.class);
      user = requestWrapper.getUserBean();
    }
    LanguageSelector ls = new LanguageSelector(lang);
    String HOME = IsmaPersister.getMAINROOT();
    //**************************************************************
     Reports report = null;
    xmlObjectGenerator xo = new xmlObjectGenerator();
    if (resultsData.get(Reports.class) != null) {
      report = (Reports) resultsData.get(Reports.class);
      reportLanguage = report.getLanguage() != null ? report.getLanguage() : lang;
    }
    else {
%>
<html>
<head>
<link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css">
</head>
<body>
<center>    Invalid Report, please return to the
    <a href="/ncp/ncp?ac=ncpcontrol">home page</a>
    selection
</center>
</body>
<html>
<%
  }
      Long reportId = null;
  Long objId = null;
  String typeString = "";
  HybernateObject ho = null;
  int type = 1;
  if (resultsData.get("reportid.form.report") != null)
    reportId = (Long) resultsData.get("reportid.form.report");
  if (qs.get("type") != null) {
    try {
      type = Integer.parseInt(qs.get("type"));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  switch (type) {
  case 1:
    typeString = "text";
    break;
  case 2:
    typeString = "dataset";
    break;
  case 3:
    typeString = "picture";
    break;
  case 4:
    typeString = "generic";
    break;
  default:
    typeString = "text";
    break;
  }
  if (qs.get(typeString + "id") != null)
    objId = new Long(qs.get(typeString + "id"));
  if (objId != null && !objId.equals("") && report != null)
    ho = report.getInternalObject(objId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;CHARSET=UTF-8">
<link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css">
<title>Untitled</title>
</head>
<body class="bodyText">
<center>
    <a href="javascript:close();">close</a>
</center>
<P>
    <!--HEADER END -->
<%if (ho != null) {%>

<table align="center" width="550px" class="tbDatasetPreview" border="1">
<%
  if (ho != null) {
    out.println(xo.getObjectsXmlTransformedAsString(ho, ls,true));
  }
%>
</table>
<%}%>
</body>
</html>
<%
  } catch (Throwable th) {
    th.printStackTrace();
  }
%>
<%!
  private String getTextFormatted(HybernateObject ho) {
    String r = "";
    r = ho.toString();
    return r;
  }
  private String getPictureFormatted(HybernateObject ho) {
    String r = "";
    r = ho.toString();
    return r;
  }
  private String getDataFormatted(HybernateObject ho) {
    String r = "";
    r = ho.toString();
    return r;
  }
  private String getTaggedRowFromCSVLine(String s, String style) {
    String[] ars = s.split(",");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < ars.length; i++) {
      String sa = ars[i].replaceAll("null", "&nbsp;");
      if (sa.equals("") || sa.equals(" "))
        sa = "&nbsp;";
      sb.append("<td class='" + style + "'>" + sa + "</td>");
    }
    return sb.toString();
  }
%>


