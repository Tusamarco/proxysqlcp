<%@ page import="java.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="net.tc.isma.auth.security.*" %>
<%@ page import="net.tc.isma.persister.*" %>
<%@ page import="net.tc.isma.persister.IsmaPersister" %>
<%@ page import="net.tc.isma.utils.*" %>
<%@ page import="net.tc.isma.workflows.*" %>
<%@ page import="net.tc.isma.lang.*" %>
<%@ page import="net.tc.isma.data.hibernate.*" %>
<%@ page import="net.tc.isma.data.objects.*" %>
<%@ page import="net.tc.isma.comparators.*" %>
<%@ page import="net.tc.isma.data.referencepicker.*" %>
<%@ page import="net.tc.isma.actions.*" %>
<%@ page import="net.tc.isma.actions.generic.*" %>
<%@ page import="net.tc.isma.request.generic.*" %>
<%@ page import="org.fao.ncp.objects.*" %>
<%!static final String[] REQUEST_PARAMETERS = {"*"};%>
<%!
  private UserBase user = null;
  private String moduleName = "REPORTER_NCP";

%>

<%try{
// Generic
      HttpSession sessioncheck = request.getSession(true);
      requestImpl requestWrapper = (requestImpl)request.getAttribute("requestImplementation");
      results resultsData = null;
      HttpQuery qs = null;
      String lang = Text.defaultTo(request.getParameter("lang"),"en");
      String version = Text.defaultTo(request.getParameter("version"),"ext");
      Locale locale = new Locale(lang);
      IniStyleResourceBundle rsc = (IniStyleResourceBundle)IsmaPersister.getResourceBundle(locale);
      List resultList = null;

      permissionBean permBean = null;
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
if(qs != null)
  qs.clear("password");
  qs.clear("user");

String reportLanguage = "en";

String defaultCountry = "862";
Map areas = null;
areas = (Map)resultsData.get("areas.all.objects");
Map subsetM = null;
subsetM = (Map)resultsData.get("subsets.all.objects");
areaBean area = null;

Map reports = null;
reports = (Map)resultsData.get("reports.all.objects");

Reports currentReport = null;

if(resultsData.get(Reports.class) != null)
 currentReport = (Reports)resultsData.get(Reports.class);

if(reports == null)
  reports = (Map)resultsData.get("reportsoutline.all.objects");

List expandl1 = null;
expandl1 = (List)resultsData.get("expandl1.form.report");
List expandl2 = null;
expandl2 = (List)resultsData.get("expandl2.form.report");
List expandl3 = null;
expandl3 = (List)resultsData.get("expandl3.form.report");

/**
 * Setting year from request or from application result
 */
String currentYear = Text.defaultTo(qs.get("year"),null);
if(currentYear == null && resultsData.get("year.form.report") != null)
  currentYear = (String) resultsData.get("year.form.report");
else if(currentYear == null && resultsData.get("year.form.report") == null)
  currentYear = "-- Template --";


Areas country = null;

/**
 * Setting country id from request or from application result
 */
String currentCountry = Text.defaultTo(qs.get("areas"),null);
if(currentCountry == null && resultsData.get("areas.form.report") != null)
  currentCountry = (String) resultsData.get("areas.form.report");
else if(currentCountry == null && resultsData.get("areas.form.report") == null)
  currentCountry = "0";



if( reports != null && reports.size() >0 && currentYear != null)
{
//  currentReport = (Reports)reports.get(currentYear);
}


if(currentReport != null)
  country = currentReport.getCountry();

String reqServed = Text.defaultTo(resultsData.get("reqServed"),"X");
String reqServedSince = Text.defaultTo(resultsData.get("reqServedSince"),"today");
if(areaPicker.getArea(defaultCountry) != null)
     area = areaPicker.getArea(defaultCountry);
if(areaPicker.getArea(defaultCountry) != null)
     area = areaPicker.getArea(defaultCountry);
else
{
    out.flush();
    return;
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="Content-Type"  content="text/html;CHARSET=UTF-8">
	<link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css">
	<title>NCP - <%//=reqServed%> - <%//=reqServedSince%></title>

<script type="text/javascript" language="Javascript">
function SpawnBrowserFile(browserName, a, b)
{
    var url = browserName;
    var hWnd = window.open(url,"requestbrowse","width="+a+",height="+b+",resizable=yes,scrollbars=yes,location=no");
    if ((document.window != null) && (!hWnd.opener))
        hWnd.opener = document.window;

    if ( hWnd != null )
        hWnd.focus();

}

function checkYear(newYear)
{
    var theForm = window.document.forms['ncpoutlinecountry'];
    var yearEl = theForm.year;

  for(var i=0 ; i < yearEl.options.length; i++)
  {
    if( yearEl.options[i].value == newYear )
      return true;
  }
  return false;
}
function cloneReport(action,newwindows,parameters)
{
    var theForm = window.document.forms['ncpoutlinecountry'];
    var newYear = "";

    newYear = prompt("Please insert the year(yyyy) of the new report.","");
    if(newYear == "" || newYear.length != 4 || checkYear(newYear)|| isNaN(newYear))
    {
      if(confirm("Invalid or already existing year. Do you want to retry the opertion?"))
      {
        cloneReport(action,newwindows,parameters);
      }
      else
     { return;}
    }
    else
    {
      if(confirm("A new report based on the current will be generate for the year = " + newYear+ ";\n\r Do you want continue?"))
      {
        runQuery(action,newwindows,parameters + "&cloneyear=" + newYear);
      }
      else
      {
        return;
      }
    }

}
function runQuery(action,newwindows,parameters)
{
    var theForm = window.document.forms['ncpoutlinecountry'];
    var faction = '/ncp/ncp?ac=' + action + parameters;
    if(newwindows)
    {
//        alert(faction + ' Before')
        SpawnBrowserFile(faction,470,250);
//        theForm.action = '/faostat/fst?ac=openrequestrequestor';
//        theForm.target = "newWindow";
//        alert(theForm.action + ' after');
//        if(valid_form(theForm))
//            theForm.submit();
    }
    else
    {
        theForm.action = faction;
        theForm.submit();
    }
}


function setExpand(level, id )
{
  var theForm = window.document.forms['ncpoutlinecountry'];
  var levelname = 'expandl' + level;
  var inputLevel = theForm.elements[levelname];
  var text2analize = '_'  + inputLevel.value;
  if(text2analize.indexOf(id) > 0)
  {
    inputLevel.value = inputLevel.value.replace(id + ',', '');
  }
  else
  {
    inputLevel.value = inputLevel.value + id + ',';
  }

  submitForm(false);
}

function submitForm(check)
{
  var theForm = window.document.forms['ncpoutlinecountry'];
  var formOk =true;
  if(check)
  {
    formOk = doFormCheck();
  }

  if(formOk)
  {
    theForm.submit();
  }

}
function changeCountry()
{
  var theForm = window.document.forms['ncpoutlinecountry'];
  if(confirm('Do you really want to change the selected country'))
  {
    if(theForm.areas.selectedIndex > 0)
    {
      theForm.expandl1.value = '';
      theForm.expandl2.value = '';
      theForm.expandl3.value = '';
      theForm.reportid.value = '';
      theForm.action = '/ncp/ncp?ac=ncpcontrol';
      theForm.year.options[0].selected = true;
      theForm.submit();
    }
  }
}
function changeYear()
{

  var theForm = window.document.forms['ncpoutlinecountry'];
  theForm.submit();
}
function doFormCheck()
{


  return true;
}
function deleteReport()
{
    var okForm = false;
    var theForm = window.document.forms['ncpoutlinecountry'];

    if(theForm.reportid.value != ""){
      okForm = true;
    }
    else{
      okForm = false;
    }
    if(okForm)
    {
      theForm.action = '/ncp/ncp?ac=deletereport';

      if(confirm('Do you really want delete the report?'))
      {
        if(confirm('Please note that this is a destructive NOT recoverable action! \n\tWould you like to proceed?'))
        {
          theForm.submit();
        }
        else
        {
          alert("Report NOT deleted 2");
        }
      }
      else
      {
        alert("Report NOT deleted");
      }
    }

}
function createNewReport()
{
    var okForm = false;
    var theForm = window.document.forms['ncpoutlinecountry'];

    if(theForm.areas.selectedIndex > 0 && theForm.areas.options[theForm.areas.selectedIndex].value !="862"){
      okForm = true;
    }
    else{
      okForm = false;
    }

    if(okForm && theForm.newyear.value != "" &&
      theForm.newyear.value.length == 4 &&
      !isNaN(theForm.newyear.value) &&
      theForm.newyear.value < 2015 ){
      okForm = true
    }
    else{
      okForm = false;
    }

    if(okForm && theForm.reportlanguage.selectedIndex > -1)
    {
      okForm = true;
    }
    else{
      okForm = false;
    }


    if(okForm)
    {
      if(confirm("Do you want proceed in creating a new Report for:\n\tCountry: " +
      theForm.areas.options[theForm.areas.selectedIndex].text + "\n\tYear: " + theForm.newyear.value +
      "\n\tLanguage:" + theForm.reportlanguage.options[theForm.reportlanguage.selectedIndex].text))
      {
        theForm.action = '/ncp/ncp?ac=createnewreport';
        //      alert(theForm.action);
        theForm.submit();
      }
    }
    else
    {
      alert("Invalid creation parameter please check and modify one of the following:\n\tCoutry"+
      theForm.areas.options[theForm.areas.selectedIndex].text + "\n\tYear: " + theForm.newyear.value +
      "\n\tLanguage:" + theForm.reportlanguage.options[theForm.reportlanguage.selectedIndex].text);
    }


}
</script>


</head>

<body class="bodyText">
          <form method="post" id="ncpoutlinecountry" name="ncpoutlinecountry" action="/ncp/ncp?ac=ncpcontrol" >
<!--HEADER START -->

	<table class="tbHead" width="100%">
		<tr class="trHead">
		    <td class="tdHead" width="100%"  colspan="7"><img src="/ncp/display/img2/logo.gif" alt=""  border="0"></td>
		</tr>

		<tr class="trHead">
		    <td class="tdHead" width="100%" class="bodyLineBlue" colspan="7"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
		<tr class="trHead">
		    <td class="tdHead" align="left">&nbsp; <a href="javascript:void(submitForm(false));">Home</a></td>
		    <td class="tdHead">&nbsp;</td>
		    <td class="tdHead">&nbsp;</td>
		    <td class="tdHead" align="center">User:&nbsp;<%=user.getName()%></td>
		    <td class="tdHead" align="center">Role:&nbsp;<%=HTML.format(user.getGroupsNames()," - ", ";&nbsp;")%></td>
		    <td class="tdHead" align="center"><%
                    if(user.isAnonymous())
                    {
                     %><a href="javascript:void(runQuery('usercontroller,ncpcontrol',false,''))" >Login</a><%
                    }
                    else
                    {
                    %><a href="javascript:void(runQuery('logout,ncpcontrol',false,''))" >Logout</a><%
                    }
                    %></td>
		</tr>
		<tr class="trHead">
		    <td class="tdHead" width="100%" class="bodyLineBlue" colspan="7"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
	</table>

<!--HEADER END -->
	<table class="tbMainSelection" width="100%" >


            <input type="hidden" name="expandl1" value="<%=Text.getAsStringSeparated(expandl1,",")%>">
            <input type="hidden" name="expandl2" value="<%=Text.getAsStringSeparated(expandl2,",")%>">
            <input type="hidden" name="expandl3" value="<%=Text.getAsStringSeparated(expandl3,",")%>">
            <input type="hidden" name="reportid" value="<%=currentReport!=null?currentReport.getId():""%>">
            <input type="hidden" name="formaction" value="">
            <tr class="trMainSelection">
              <td class="tdMainSelection" align="left">
                Country
                Choose a Country
                <select name="areas" onchange="changeCountry()">
                  <option value="862"> Choose a country </option>
                  <option value="862" <%=currentCountry.equals("862")?" SELECTED ":""%>> -- Template -- </option>
                  <option value="0">Template</option>
                  <%
                  if(area != null && area.getSubAreas() != null)
                  {
                    List cArea = areaPicker.getArea(area.getSubAreas().values());

                    Collections.sort(cArea, new MultilingualComparator("namelong",ls));


                    for(int iArea = 0 ; iArea < cArea.size() ; iArea++)
                    {
                      areaBean areaBeanC = (areaBean)cArea.get(iArea);
                      String selected = "";
                      if(areaBeanC.getId().equals(currentCountry))
                        selected = "SELECTED";
                      out.println("<option value='" + areaBeanC.getId() + "' " + selected + ">" + areaBeanC.getNameLong(ls) + "</option>");

                    }


                  }
                  %>
                  </select>
                 </td>
                </tr>
                <tr class="tdMainSelection">
                <td class="tdMainSelection" align="left"><%
                if(user.isAdministrator())
                {
                %>
                  Report
                  &nbsp;<%=rsc.getDirectString("lang2.display.language")%>:&nbsp;
                  <select name="reportlanguage">
                    <%
                      if(currentReport != null && currentReport.getLanguage() != null)
                          reportLanguage = currentReport.getLanguage();
                    %>
                      <option value="en" <%=reportLanguage.equals("en")?" SELECTED ":""%>><%=rsc.getDirectString("lang2.display.english")%></option>
                      <option value="fr" <%=reportLanguage.equals("fr")?" SELECTED ":""%>><%=rsc.getDirectString("lang2.display.french")%></option>
                      <option value="es" <%=reportLanguage.equals("es")?" SELECTED ":""%>><%=rsc.getDirectString("lang2.display.spanish")%></option>
                  </select>
                  &nbsp;year:<input type="text" name="newyear" size="4">
                  &nbsp;
                  <a href="javascript:void(createNewReport())">Create new<img src="/ncp/display/img2/new.gif" alt="add new Report" title="add new Report" width="16" height="16" border="0"></a>
                  &nbsp; &brvbar;&nbsp;
                  <a href="javascript:void(deleteReport())"><img src="/ncp/display/img2/deleteicon_enabled.gif" alt="delete whole Theme" border="0" align="middle" title="delete Current Report"></a>
                    <%
                }
                    %>

                    </td>
                  </tr>
		<tr class="trMainSelection">
		    <td width="80%" class="tdMainSelection" ><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>

		<!-- if country select START-->
		<tr class="trMainSelection">
                  <td align="left" class="tdMainSelection">
                    <table class="tbMainSelection1">
                      <tr class="trMainSelection1">
                        <%if( country != null){%>
                        <td class="tdMainSelection1">Country information</td>
                        <td class="tdMainSelection1">Name: <%= currentReport!=null?HTML.format(country.getName(ls), " - "):""%></td>
                        <td class="tdMainSelection1">ISO: <%= currentReport!=null?HTML.format(country.getIso3(), " - "):""%></td>
                        <td class="tdMainSelection1">code: <%= currentReport!=null?HTML.format(country.getId(), " - "):""%></td>
                        <td class="tdMainSelection1">Rep Lang:<%= currentReport!=null?HTML.format(currentReport.getLanguage(), " - "):""%></td>
                        <td class="tdMainSelection1">&nbsp;</td>
                        <%}%>
                      </tr>
                      <tr class="trMainSelection1">
                        <td class="tdMainSelection1" colspan="3">Available reports(years)
                          <select name="year" onchange="changeYear()">
                            <option value="new">-- new --</option>
                            <%
                              if(reports != null && reports.size() > 0 )
                              {
//                                if(country != null){
//                                  out.println("<option value='" + country.getId()+ "' >-- Template --</options>");
//                                }
                                Iterator itreports = reports.keySet().iterator();

                                while (itreports.hasNext())
                                {
                                  String vyear = (String)itreports.next();
                                  String selected = "";
                                  if(currentYear.equals(vyear))
                                    selected = " SELECTED ";
                                  out.println("<option value='" + vyear + "' "+ selected  +">"+ vyear +"</option>");
                                }
                              }
                            %>
                          </select>
                        </td>
                        <%if(currentReport != null){%>
                         <td class="tdMainSelection1" colspan="1">
                          Current Report:&nbsp;Preview&nbsp;
                          <a href="javascript:void(runQuery('showreport',true,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))" >
                          <img src="/ncp/display/img2/eyeglasses.gif" alt="Preview Report" border="0" align="middle" title="Preview Report"></a>
                          &nbsp;
                          <a href="javascript:void(runQuery('showreportxml',true,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))" >
                          <img src="/ncp/display/img2/xml.jpg" alt="Preview Report XML" border="0" align="middle" title="Preview Report XML"></a>
                          <a href="javascript:void(runQuery('viewfop',true,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))" >FO</a>

                          <a href="javascript:void(runQuery('createpdf',true,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))" >
                          <img src="/ncp/display/img2/pdf.jpg" alt="Preview Report PDF" border="0" align="middle" title="Preview Report PDF"></a>
                          </td>
                          <%
                          if(user.isAdministrator())
                          {
                          %>
                          <td class="tdMainSelection1" colspan="1">
                          &nbsp;Generate&nbsp;<img src="/ncp/display/img2/button_g_go.gif" alt="Generate Report" width="25" height="20" border="0" align="middle" title="Generate Report">
                          </td>
                          <td class="tdMainSelection1">
                            &nbsp;<a href="javascript:void(cloneReport('clonereport',false,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))">clone</a>
                          </td>
                          <%
                          }
                          else
                          {
                          %><td class="tdMainSelection1" colspan="2">&nbsp;</td>
                         <%}%>

                        <%}else{out.println("<td colspan='2'>&nbsp;</td>");}%>
                        </tr>

                      </table>
                   </td>
		</tr>
		<tr>
		    <td width="100%" class="bodyLineBlue" colspan="2"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
                <%
                if(currentReport != null && currentReport.getThemes() != null && currentReport.getThemes().size() > 0)
                {
                  Module mod = null;
                  workflow wf = null;
                  if(IsmaPersister.getModulesMap() != null)
                  {
                    Map modMap = IsmaPersister.getModulesMap();
                    mod = (Module)modMap.get("REPORTER_NCP");
                    if(mod != null && mod.getWorkflow() != null)
                    wf = (workflow)mod.getWorkflow().toArray()[0];
                  }

                  List lTheme = new ArrayList((Collection)currentReport.getThemes());
                  Collections.sort(lTheme, new HibernateOrderComparator());

                  HttpQuery qsto = requestWrapper.getHttpQuery(new String[]{"*"});;
                  qsto.clear(new String[]{"ac","formaction","textid","dataid","pictureid","genericid","level","objid","datasetid","password","user"});

                  qsto.set("areas", String.valueOf(currentReport.getCountry().getId()));
                  qsto.set("year", currentReport.getYear());
                  qsto.set("reportid", currentReport.getId().toString());
                  qsto.set("expandl1", Text.getAsStringSeparated(expandl1,","));
                  qsto.set("expandl2", Text.getAsStringSeparated(expandl2,","));
                  qsto.set("expandl3", Text.getAsStringSeparated(expandl3,","));
                  qsto.set("level", "0");

                  for(int ilt = 0 ; ilt < lTheme.size() ; ilt++)
                  {
                    boolean expandl1b = false;
                    qsto.clear("level");
                    qsto.set("level", "0");
                    Themes theme = (Themes)lTheme.get(ilt);

                    qsto.set("themeid", theme.getId().toString());

                    stepImpl step = null;
                    if(wf != null && theme.getStep() != null)
                      step = (stepImpl)wf.getStep(new Long(theme.getStep().getIdStep()));

                    permBean = getPermission(step)  ;

                    if(expandl1 != null && expandl1.contains(theme.getId()))
                      expandl1b = true;

              %>

		<tr>
		<!-- Outline Section [START]-->
                  <td>
                    <table class="tbBodyTextLev0" cellpadding="0" cellspacing="0" border="0">
                      <tr class="trBodyTextLev0" >
                        <td rowspan="2" ></td>
                        <td >
                          <table class="tbBodyTextLev0" cellpadding="0" cellspacing="0">
                            <tr class="trBodyTextLevel0">
                              <td class="tdBodyTextLevel0" >
                                <a href="javascript:void(setExpand('1',<%=theme.getId()%>))"><img src="/ncp/display/img2/<%=expandl1b?"minus-button.gif":"plus-button.gif"%>" alt="collapse" border="0" align="middle" title="collapse"></a>&nbsp;&nbsp;
                              </td>
                              <td class="chap"><%=theme.getName(ls)%></td>
                              <td class="tdBodyTextLevel0">&nbsp;&nbsp;&nbsp;</td>
                              <%if(permBean.canRead() || permBean.canEdit() || permBean.canAdd() || permBean.canDelete() || permBean.isAdmin()){%>
                                <td class="tdBodyTextLevel0"><%if(ilt != 0){%><a href="<%qsto.set("ac","setorder,ncpcontrol");qsto.set("ordert","-1");%><%=qsto%>"><img align="middle" src="/ncp/display/img2/m-arr-u.gif" alt="move up"  border="0"  title="move up"></a><%}%></td>
                                <td class="tdBodyTextLevel0"><%if(ilt != (lTheme.size()-1)){%><a href="<%qsto.set("ac","setorder,ncpcontrol");qsto.set("ordert","1");%><%=qsto%>"><img align="middle" src="/ncp/display/img2/m-arr-d.gif" alt="move down"  border="0" title="move down"></a><%}%></td>
                              <td class="tdBodyTextLevel0">&nbsp;<img src="/ncp/display/img2/bar2.gif"  alt="" border="0" align="top" ></td>
                              <td class="tdBodyTextLevel0">&nbsp;
                                <a href="javascript:void(runQuery('showreport',true,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>&thid=<%=theme.getId()%>'))" >
                                <img align="middle" src="/ncp/display/img2/eyeglasses.gif" title="preview" alt="preview"  border="0"></a></td>
                              <td class="tdBodyTextLevel0">&nbsp;<img align="middle" src="/ncp/display/img2/refresh.gif" alt="Refresh Theme"  border="0" title="Refresh Theme"></td>
                              <%}else{%><td></td><td></td><td></td><td></td><td></td><%}%>


                                <td class="tdBodyTextLevel0">&nbsp;<img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                                <td class="tdBodyTextLevel0"><img src="/ncp/display/img2/empty.gif" alt="" width="20px" height="1px" border="0">wf Status (<%=step.getName(ls)%>)</td>
                              <%if(permBean.canPromote() || permBean.canDemote() || permBean.isAdmin()){%>
                                <%if(wf.getPrevious(step) != null){
                                %>
                                <td class="tdBodyTextLevel0">&nbsp;<a href="<%qsto.set("ac","setstep,ncpcontrol");qsto.set("step","0");qsto.set("stepid",new Long(step.getIdStep()).toString());%><%=qsto%>"><img src="/ncp/display/img2/m-arr-l.gif" alt="move to prev step"  border="0" align="middle" title="move to prev step"></a></td>
                                <%}%>
                                <%if(wf.getNext(step) != null){
                                %>
                                <td class="tdBodyTextLevel0">&nbsp;<a href="<%qsto.set("ac","setstep,ncpcontrol");qsto.set("step","1");qsto.set("stepid",new Long(step.getIdStep()).toString());%><%=qsto%>"><img src="/ncp/display/img2/m-arr-r.gif" alt="move to next step"  border="0" align="middle" title="move to next step"></a></td>
                                <%}%>
                              <%}else{%><td></td><td></td><%}%>

                              <%if(permBean.canSecurity() || permBean.isAdmin()){%>
                                <td class="tdBodyTextLevel0">&nbsp;<img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                                <%if(theme.getStatus().intValue() == 1){%>
                                <td class="tdBodyTextLevel0"><a href="<%qsto.set("ac","setostatus,ncpcontrol");qsto.set("status","0");%><%=qsto%>" onclick="return(confirm('Lock object?'))"><img src="/ncp/display/img2/tbunlock.gif" border="0" align="middle" title="status Unlocked" alt="status Unlocked"></a></td>
                                <%}else{%>
                                <td class="tdBodyTextLevel0"><a href="<%qsto.set("ac","setostatus,ncpcontrol");qsto.set("status","1");%><%=qsto%>" onclick="return(confirm('Unlock object?'))"><img src="/ncp/display/img2/tblock.gif" border="0" align="middle" title="status Locked" alt="status Locked"></a></td>
                                <%}%>
                              <%}else{%><td></td><td></td><td></td><%}%>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <%
                  if(expandl1b)
                  {
                    /**
                     * First get the generic wrapping object
                     */
                    Set toSet = theme.getObjects();
                    if(toSet != null && toSet.size() > 0)
                    {

                      List toList = new ArrayList(toSet);
                      Collections.sort(toList, new HibernateOrderComparator());
                      qsto.clear("hoid");
                      for(int il1 = 0 ; il1 < toList.size() ; il1++)
                      {
                        Tobjects to = (Tobjects)toList.get(il1);

                        qsto.clear("level");
                        qsto.set("level", "1");
                        qsto.set("tobjectid", to.getId().toString());
                        boolean expandl2b = false;
                        if(expandl2 != null && expandl2.contains(to.getId()))
                          expandl2b = true;

                %>
                <tr>
                  <td colspan="2">
                    <table class="tbBodyTextLevel1" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td>
                          <table class="tbBodyTextLevel1" cellpadding="0" cellspacing="0" border="0">
                            <tr class="trBodyTextLevel1">
                              <td class="tdBodyTextLevel1"><img src="/ncp/display/img2/empty.gif" alt="" width="20px" height="1px" border="0">
                                <a href="javascript:void(setExpand('2',<%=to.getId()%>))"><img src="/ncp/display/img2/<%=expandl2b?"minus-button.gif":"plus-button.gif"%>" alt="collapse" width="9" height="9" border="0" align="middle" title="collapse"></a>&nbsp;&nbsp;</td>
                              <td class="tdBodyTextLevel1"><%=to.getName(ls)%></td>

                              <%if(permBean.canRead() || permBean.canEdit() || permBean.canAdd() || permBean.canDelete() || permBean.isAdmin()){%>
                                <td class="tdBodyTextLevel1">&nbsp;<img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                                <td class="tdBodyTextLevel1"><%if(il1 != 0){%><a href="<%qsto.set("ac","setorder,ncpcontrol");qsto.set("ordert","-1");%><%=qsto%>"><img align="middle" src="/ncp/display/img2/m-arr-u.gif" alt="move up"  border="0"  title="move up"></a><%}%></td>
                                <td class="tdBodyTextLevel1"><%if(il1 != (toList.size()-1)){%><a href="<%qsto.set("ac","setorder,ncpcontrol");qsto.set("ordert","1");%><%=qsto%>"><img align="middle" src="/ncp/display/img2/m-arr-d.gif" alt="move down"  border="0" title="move down"></a><%}%></td>
                                <td class="tdBodyTextLevel1">&nbsp;<img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                                <td class="tdBodyTextLevel1">&nbsp;
                                  <a href="javascript:void(runQuery('showreport',true,'&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>&toid=<%=to.getId()%>'))" >
                                    <img align="middle" src="/ncp/display/img2/eyeglasses.gif" title="preview" alt="preview"  border="0"></td>
                                  </a>
                                <!--td>&nbsp;<img align="middle" src="/ncp/display/img2/refresh.gif" alt="Refresh Item" border="0" title="Refresh Item"></td-->
                                <td class="tdBodyTextLevel1">&nbsp;<img src="/ncp/display/img2/bar2.gif"  alt=""  border="0" align="top" ></td>
                                <td class="tdBodyTextLevel1">&nbsp;<a href="<%qsto.set("hotype","1");qsto.set("ac","showtextform");%><%=qsto%>"><img src="/ncp/display/img2/text.gif" alt="add Text"  border="0" align="middle" title="add text"></a></td>
                                <td class="tdBodyTextLevel1">&nbsp;<a href="<%qsto.set("hotype","2");qsto.set("ac","showdataform");%><%=qsto%>"><img src="/ncp/display/img2/data.gif" alt="add data"  border="0" align="middle" title="add data"></a></td>
                                <td class="tdBodyTextLevel1">&nbsp;<a href="<%qsto.set("hotype","3");qsto.set("ac","showtextform");%><%=qsto%>"><img src="/ncp/display/img2/img.gif" alt="add picture"  border="0" align="middle" title="add picture"></a></td>
                                <td class="tdBodyTextLevel1">&nbsp;<a href="<%qsto.set("hotype","4");qsto.set("ac","showtextform");%><%=qsto%>"><img src="/ncp/display/img2/generic.gif" alt="add generic"  border="0" align="middle" title="add generic"></a></td>
                                <td class="tdBodyTextLevel1">&nbsp;<img src="/ncp/display/img2/bar2.gif" alt="" border="0" align="top" ></td>
                                <td class="tdBodyTextLevel1"><a href="<%qsto.set("ac","deleteto,ncpcontrol");%><%=qsto%>" onclick="return(confirm('Delete Section?'))"><img src="/ncp/display/img2/deleteicon_enabled.gif" alt="delete" border="0" align="middle" title="delete"></a></td>
                                <%if(to.getStatus().intValue() == 1){%>
                                <td class="tdBodyTextLevel1"><a href="<%qsto.set("ac","setostatus,ncpcontrol");qsto.set("status","0");%><%=qsto%>" onclick="return(confirm('Unlink object?'))"><img src="/ncp/display/img2/linked.gif" alt="currently linked" width="16" height="16" border="0" align="middle" title="currently linked"></a></td>
                                <%}else{%>
                                <td class="tdBodyTextLevel1"><a href="<%qsto.set("ac","setostatus,ncpcontrol");qsto.set("status","1");%><%=qsto%>" onclick="return(confirm('Link object?'))"><img src="/ncp/display/img2/unlink.gif" alt="currently unlinked" width="16" height="16" border="0" align="middle" title="currently unlinked"></a></td>
                                <%}%>
                              <%}else{%><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><%}%>

                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <%
                        if(expandl2b)
                        {
                          /**
                           * then the specific objects data text picture etc...
                           */
                           Set textS = to.getText();
                           Set dataS = to.getData();
                           Set pictureS = to.getPicture();
                           Set unboundS = to.getUnbound();
                           List allSubObj = new ArrayList();
                           if(textS != null)
                               allSubObj.addAll(textS);

                           if(dataS != null)
                               allSubObj.addAll(dataS);

                           if(pictureS != null)
                               allSubObj.addAll(pictureS);

                           if(unboundS != null)
                               allSubObj.addAll(unboundS);

                           if(allSubObj != null)
                               Collections.sort(allSubObj, new HibernateOrderComparator());

                           if(allSubObj != null && allSubObj.size() > 0)
                           {
                             for(int iSubObj = 0 ; iSubObj < allSubObj.size() ; iSubObj++)
                             {
                                HybernateObject ho = (HybernateObject)allSubObj.get(iSubObj);
                                qsto.clear(new String[]{"textid","dataid","pictureid","genericid","level","objid"});
                                String typeString = "- undefine -";
                                qsto.set("hoid", ho.getId().toString());

                                qsto.clear("level");
                                qsto.set("level", "2");
                                qsto.set("hotype","1");
                                if(ho.getType() != null)
                                {
                                  qsto.set("hotype",ho.getType().toString());
                                  switch (ho.getType().intValue())
                                  {
                                    case 1: typeString = "text";break;
                                    case 2: typeString = "dataset";qsto.set("datasetid", ho.getId().toString());break;
                                    case 3: typeString = "picture";break;
                                    case 4: typeString = "generic";break;
                                    default :typeString = "text";break;
                                  }
                                }
                %>
                <tr>
                  <td colspan="2">
                    <table border="0" class="tbBodyTextLevel2" cellpadding="0" cellspacing="2">
                      <tr class="trBodyTextLevel2">
                        <td class="tdBodyTextLevel2"><img src="/ncp/display/img2/empty.gif" alt="" width="30px" height="1px" border="0"><img src="/ncp/display/img2/<%=typeString%>.gif" alt="<%=typeString%>"  border="0" align="middle" title="<%=typeString%>"></td>
                        <td class="tdBodyTextLevel2"><%=ho.getName(ls)%></td>
                        <td class="tdBodyTextLevel2">&nbsp;(<%=typeString%>)</td>

                        <%if(permBean.canRead() || permBean.canEdit() || permBean.canAdd() || permBean.canDelete() || permBean.isAdmin()){%>
                          <td class="tdBodyTextLevel2"><img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                          <td class="tdBodyTextLevel2"><%if(iSubObj != 0){%><a href="<%qsto.set("ac","setorder,ncpcontrol");qsto.set("ordert","-1");%><%=qsto%>"><img align="middle" src="/ncp/display/img2/m-arr-u.gif" alt="move up"  border="0"  title="move up"></a><%}%></td>
                          <td class="tdBodyTextLevel2"><%if(iSubObj != (allSubObj.size()-1)){%><a href="<%qsto.set("ac","setorder,ncpcontrol");qsto.set("ordert","1");%><%=qsto%>"><img align="middle" src="/ncp/display/img2/m-arr-d.gif" alt="move down"  border="0" title="move down"></a><%}%></td>
                          <td class="tdBodyTextLevel2"><img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                          <td class="tdBodyTextLevel2"><a href="javascript:void(runQuery('show<%=typeString%>',true,'&type=<%=ho.getType()%>&<%=typeString%>id=<%=ho.getId()%>&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))" ><img align="middle" src="/ncp/display/img2/eyeglasses.gif" title="preview" alt="preview"  border="0"></a></td>
                          <!--td ><img align="middle" src="/ncp/display/img2/refresh.gif" alt="Refresh Item" border="0" title="Refresh Item"></td-->
                          <td class="tdBodyTextLevel2"><img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                           <%qsto.set("hotype","1");qsto.set("ac","showtextform");%>
                          <%if(ho.getType() != null && ho.getType().intValue() == 2){%>
                            <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","showdataform");qsto.set("formaction","show");%><%=qsto%>" ><img src="/ncp/display/img2/updateicon_enabled.gif" alt="Edit Item"  border="0" align="middle" title="Edit Item"></a></td>
                          <%if(((Datasets)ho).getData() != null && ((Datasets)ho).getData().size() > 0){ %>

                            <td class="tdBodyTextLevel2"><a href="javascript:void(runQuery('editdatasetkids',true,'&<%=typeString%>id=<%=ho.getId()%>&reportid=<%=currentReport.getId()%>&year=<%=currentReport.getYear()%>'))" ><img src="/ncp/display/img2/editdateset.gif" alt="Edit Dataset in Kids"  border="0" align="middle" title="Edit Dataset in Kids"></a></td>
                            <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","grabdataset,ncpcontrol");%><%=qsto%>" ><img src="/ncp/display/img2/impfkids.jpg" alt="Import data from kids editor"  border="0" align="middle" title="Import data from kids editor"></a></td>

                            <%if(((Datasets)ho).getXml() != null && !((Datasets)ho).getXml().equals("")){%>
                              <!-- for graphs -->

                              <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","graphfromkids,ncpcontrol");%><%=qsto%>" ><img src="/ncp/display/img2/impgrph.jpg" alt="get graph from kids"  border="0" align="middle" title="get graph from kids"></a></td>
                            <%}%>
                          <%}else{%><td></td><td></td><td></td><%}%>

                          <%}else{%>
                          <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","showtextform");qsto.set("formaction","show");%><%=qsto%>" ><img src="/ncp/display/img2/updateicon_enabled.gif" alt="Edit Item"  border="0" align="middle" title="Edit Item"></a></td>
                          <%}%>
                          <%if(ho.getType() != null && ho.getType().intValue() == 2){%>
                            <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","deletedataset");qsto.set("formaction","delete");%><%=qsto%>" onclick="return(confirm('Delete dataset?'))"><img src="/ncp/display/img2/deleteicon_enabled.gif" alt="delete" border="0" align="middle" title="delete"></a></td>
                          <%}else{%>
                            <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","deleteobject");qsto.set("formaction","delete");%><%=qsto%>" onclick="return(confirm('Delete object?'))"><img src="/ncp/display/img2/deleteicon_enabled.gif" alt="delete" border="0" align="middle" title="delete"></a></td>
                          <%}%>

                              <td class="tdBodyTextLevel2"><img src="/ncp/display/img2/bar2.gif" alt=""  border="0" align="top" ></td>
                          <%if(ho.getStatus() != null && ho.getStatus().intValue() == 1){%>
                          <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","setostatus,ncpcontrol");qsto.set("status","0");%><%=qsto%>" onclick="return(confirm('Unlink object?'))"><img src="/ncp/display/img2/linked.gif" alt="currently linked" width="16" height="16" border="0" align="middle" title="currently linked"></a></td>
                          <%}else if(ho.getStatus() != null && ho.getStatus().intValue() == 0){%>
                          <td class="tdBodyTextLevel2"><a href="<%qsto.set("ac","setostatus,ncpcontrol");qsto.set("status","1");%><%=qsto%>" onclick="return(confirm('Link object?'))"><img src="/ncp/display/img2/unlink.gif" alt="currently unlinked" width="16" height="16" border="0" align="middle" title="currently unlinked"></a></td>
                          <%}else{%><td></td><%}%>
                        <%}else{%><td colspan="16"></td><%}%>

                      </tr>
                    </table>
                  </td>
                </tr>

                <%
                             }
                 %>
                <tr class="trBodyTextLevel">
                    <td width="50px" class="bodyLineSenape"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
                    <td></td>
                </tr>
                 <%
                           }
                        }
                      }
                    }
                  }
                %>
                <tr class="trBodyTextLevel">
                    <td width="100%" class="bodyLineBlue" colspan="2"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
                </tr>

                <%
                    }
                %>


                <tr class="trBodyTextLevel">
                    <td width="100%" class="bodyLineBlue" colspan="2"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
                </tr>
                <%}
                  else
                  {
                  %>
                <tr class="trBodyTextLevel">
                    <td width="100%" class="bodyLineBlue" colspan="2"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
                </tr>
                <tr class="trBodyTextLevel">
                    <td width="100%" class="bodyText" colspan="2">No valid Country year selection modify your selection or create a new report</td>
                </tr>
                <tr class="trBodyTextLevel">
                    <td width="100%" class="bodyLineBlue" colspan="2"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
                </tr>

                  <%
                  }
                %>

                    </table>
                  </td>
		</tr>

		<!-- Outline Section [END]-->
		<!-- if country select END-->
		</form>
	</table>

</body>
<script type="text/javascript" language="Javascript">
  window.document.forms['ncpoutlinecountry'].action ='/ncp/ncp?ac=ncpcontrol';
</script>
</html>
<%
}catch(Throwable th)
{
  net.tc.isma.persister.IsmaPersister.getLogVisualizer().error(th);
  th.printStackTrace();
}%>
<%!
private permissionBean getPermission(Object step)
{
  return permissionBase.getSecurity(moduleName, user, (Permission)step);

}
%>
