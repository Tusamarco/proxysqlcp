<%@ page contentType="text/html"%><%@
page import="java.util.*" %><%@
page import="java.util.*" %><%@
page import="net.tc.isma.auth.security.*" %><%@
page import="net.tc.isma.persister.*" %><%@
page import="net.tc.isma.persister.IsmaPersister" %><%@
page import="net.tc.isma.utils.*" %><%@
page import="net.tc.isma.lang.*" %><%@
page import="net.tc.isma.data.hibernate.*" %><%@
page import="net.tc.isma.data.objects.*" %><%@
page import="net.tc.isma.comparators.*" %><%@
page import="net.tc.isma.data.referencepicker.*" %><%@
page import="net.tc.isma.actions.*" %><%@
page import="net.tc.isma.actions.generic.*" %><%@
page import="net.tc.isma.request.generic.*" %><%@
page import="org.fao.ncp.objects.*" %><%@
page import="org.fao.ncp.common.reporter.*" %><%@
page import="net.tc.isma.lang.LanguageSelector;" %><%!
static final String[] REQUEST_PARAMETERS = {"*"};%><%try{
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
  String viewStyle = null;
  if(qs != null && qs.get("style") != null)
    viewStyle = qs.get("style");

  Reports report = null;
  if(resultsData.get(Reports.class) != null)
  {
    report = (Reports)resultsData.get(Reports.class);
    reportLanguage = report.getLanguage()!=null?report.getLanguage():lang;
  }
  Long reportId = null;


  if(resultsData.get("reportid.form.report") != null)
    reportId = (Long)resultsData.get("reportid.form.report");



  if(report != null && qs.get("thid") == null &&  qs.get("toid") == null)
    {
      String s = null;
      xmlObjectGenerator xg = new xmlObjectGenerator();
       s = xg.getReportXmlTransformedAsString(report,ls);

      out.println(s);
    }
  else if(report != null && qs.get("thid") != null && !qs.get("thid").equals("") &&  qs.get("toid") == null)
  {
      xmlObjectGenerator xg = new xmlObjectGenerator();
      Themes th = (Themes)report.getInternalObject(new Long(qs.get("thid")));
//      String s = xg.getXmlThemesAsString(th, report.getLanguage());
      String s = xg.getThemesXmlTransformedAsString(th, new LanguageSelector(report.getLanguage()));

      out.println(s);

  }
  else if(report != null && qs.get("thid") == null &&  qs.get("toid") != null && !qs.get("toid").equals("") )
  {
      xmlObjectGenerator xg = new xmlObjectGenerator();
      Tobjects to = (Tobjects)report.getInternalObject(new Long(qs.get("toid")));
      if(to != null){
//        String s = xg.getXmlTObjectAsString(to, report.getLanguage());
        String s = xg.getTObjectsXmlTransformedAsString(to, new LanguageSelector(report.getLanguage()));
        out.println(s);
      }

  }



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
