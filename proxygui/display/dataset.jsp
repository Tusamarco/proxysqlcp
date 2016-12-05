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
<%@ page import="net.tc.isma.lang.LanguageSelector;" %>

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
  LanguageSelector lsr = null;

  String[] dataStyle = new String[]{
                                    "table_title",
                                    "table_subtitle",
                                    "table_text",
                                    "table_text_1",
                                    "table_text_special",
                                    "table_text_special_1",
                                    "table_text_special_2"
                                  };

  if(resultsData.get(Reports.class) != null)
  {
    report = (Reports)resultsData.get(Reports.class);
    reportLanguage = report.getLanguage()!=null?report.getLanguage():lang;
    lsr = new LanguageSelector(reportLanguage);
  }
  else
  {
    %>
    <html><head><link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css"></head>
      <body><center> Invalid Report, please return to the <a href="/ncp/ncp?ac=ncpcontrol">home page</a> selection</center></body><html>
    <%
  }
  Long reportId = null;
  Long themeId = null;
  Long tobjectId = null;
  Long datasetId = null;

  String year = null;
  String areaId = null;


  year = Text.defaultTo(qs.get("year"),null);
  if(year == null && resultsData.get("year.form.report") != null)
    year = (String) resultsData.get("year.form.report");
  else if(year == null && resultsData.get("year.form.report") == null)
    year = "-- Template --";

    areaId = Text.defaultTo(qs.get("areas"),null);
    if(areaId == null && resultsData.get("areas.form.report") != null)
      areaId = (String) resultsData.get("areas.form.report");
    else if(areaId == null && resultsData.get("areas.form.report") == null)
      areaId = "0";



  if(resultsData.get("reportid.form.report") != null)
    reportId = (Long)resultsData.get("reportid.form.report");


  if(resultsData.get("themeid.form.report") != null)
    themeId = (Long)resultsData.get("themeid.form.report");

  if(resultsData.get("tobjectid.form.report") != null)
    tobjectId = (Long)resultsData.get("tobjectid.form.report");

  if(resultsData.get("datasetid.form.report") != null)
    datasetId = (Long) resultsData.get("datasetid.form.report");

  List expandl1 = null;
  expandl1 = (List)resultsData.get("expandl1.form.report");
  List expandl2 = null;
  expandl2 = (List)resultsData.get("expandl2.form.report");
  List expandl3 = null;
  expandl3 = (List)resultsData.get("expandl3.form.report");

  Datasets dataSet = null;

  if(datasetId != null && !datasetId.equals(""))
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

<script type="text/javascript" language="Javascript">
function runQuery(action,newwindows,parameters)
{
    var theForm = window.document.forms['ncpdataset'];
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
function SpawnBrowserFile(browserName, a, b)
{
    var url = browserName;
    var hWnd = window.open(url,"requestbrowse","width="+a+",height="+b+",resizable=yes,scrollbars=yes,location=no");
    if ((document.window != null) && (!hWnd.opener))
        hWnd.opener = document.window;

    if ( hWnd != null )
        hWnd.focus();

}



function submitForm(check)
{
  var theForm = window.document.forms['ncpdataset'];
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
function submitFormVariableAction(actionIn)
{
  var theForm = window.document.forms['ncpdataset'];
  var formOk =true;

  theForm.action = '/ncp/ncp?ac=' + actionIn;
//  alert(theForm.action);
  theForm.submit();

}

function dosubmit(action)
{
  var theForm = window.document.forms['ncpdataset'];

  if(action != "")
  {
    theForm.formaction.value=action;
    submitForm();
  }
}
function doFormCheck()
{


  return true;
}

</script>
</head>

<body class="bodyText">
    <form enctype="multipart/form-data"  method="post" id="ncpoutlinecountry" name="ncpdataset" action="/ncp/ncp?ac=showdataform" >
<!--HEADER START -->
	<table class="tbHead" width="100%">
		<tr class="trHead">
		    <td class="tdHead" width="100%"  colspan="7"><img src="/ncp/display/img2/logo.gif" alt=""  border="0"></td>
		</tr>

		<tr class="trHead">
		    <td class="bodyLineBlue" width="100%" class="bodyLineBlue" colspan="7"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
		<tr class="trHead">
		    <td class="tdHead" align="left">&nbsp; <a href="javascript:void(runQuery('ncpcontrol',false,''));">Home</a></td>
		    <td class="tdHead">&nbsp;</td>
		    <td class="tdHead">&nbsp;</td>
		    <td class="tdHead" align="center">User:&nbsp;<%=user.getName()%></td>
		    <td class="tdHead" align="center">Role:&nbsp;<%=HTML.format(user.getRoleNames()," - ", ";&nbsp;")%></td>
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
		    <td class="bodyLineBlue" width="100%" class="bodyLineBlue" colspan="7"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
	</table>

<!--HEADER END -->

      <input type="hidden" name="expandl1" value="<%=Text.getAsStringSeparated(expandl1,",")%>"/>
      <input type="hidden" name="expandl2" value="<%=Text.getAsStringSeparated(expandl2,",")%>"/>
      <input type="hidden" name="expandl3" value="<%=Text.getAsStringSeparated(expandl3,",")%>"/>
      <input type="hidden" name="reportid" value="<%=reportId!=null?reportId.longValue():0%>"/>
      <input type="hidden" name="themeid" value="<%=themeId!=null?themeId.longValue():0%>"/>
      <input type="hidden" name="tobjectid" value="<%=tobjectId!=null?tobjectId.longValue():0%>"/>
      <input type="hidden" name="datasetid" value="<%=datasetId!=null?datasetId.toString():""%>"/>
      <input type="hidden" name="areas" value="<%=areaId!=null?areaId:""%>"/>
      <input type="hidden" name="year" value="<%=year!=null?year:""%>"/>

      <input type="hidden" name="formaction" value=""/>

  <table align="center" width="550px" class="tbMainForm" >
  <tr class="trMainForm">
    <td>

    <table align="center" width="550px">
            <tr class="trMainForm">
                    <td class="tdMainFormHeader"><%=rsc.getDirectString("lang2.display.datasetName")%></td>
                    <td><input name="namedataset" class="formInput" size="50" value="<%=dataSet!=null?Text.defaultTo(dataSet.getName(lsr), ""):""%>" /></td>
            </tr>
            <tr class="trMainForm">
                    <td class="tdMainFormHeader"><%=rsc.getDirectString("lang2.display.style")%></td>
                    <%
                      String style ="";
                      if(dataSet != null && dataSet.getStyle() != null)
                        style = dataSet.getStyle();
                    %>
                    <td>
                      <%

                          String[] selectStyle = null;
                          selectStyle = dataStyle;

                      %>
                      <select name="styledataset" size="5" class="formInputList">
                        <option value="normal" <%=style.equals("")?" SELECTED ":""%>>Normal</option>
                        <%
                            if(selectStyle != null && selectStyle.length > 0)
                            {
                              for(int iSt = 0 ; iSt < selectStyle.length; iSt++)
                              {
                       %>
                        <option value="<%=selectStyle[iSt]%>" <%=style.equals(selectStyle[iSt])?" SELECTED ":""%>><%=selectStyle[iSt]%></option>
                       <%     }

                            }
                        %>
                      </select>

                    </td>
            </tr>
            <%
            String noteDataSet = "";
            String commentsDataSet = "";
            if(dataSet != null && dataSet.getNote(lsr) != null && !dataSet.getNote(lsr).equals(""))
              noteDataSet = dataSet.getNote(lsr);

            if(dataSet != null && dataSet.getNote(lsr) != null && !dataSet.getComment(lsr).equals(""))
              commentsDataSet = dataSet.getComment(lsr);


            %>
            <tr class="trMainForm">
                    <td colspan="2" class="tdMainFormHeader"><%=rsc.getDirectString("lang2.display.note")%></td>
            </tr>
            <tr class="trMainForm">
                    <td colspan="2"><textarea class="formInput" name="note" rows="5" cols="80"><%=noteDataSet%></textarea></td>
            </tr>
            <tr class="trMainForm">
                    <td colspan="2" class="tdMainFormHeader"><%=rsc.getDirectString("lang2.display.comments")%></td>
            </tr>
            <tr class="trMainForm">
                    <td colspan="2"><textarea class="formInput" name="comment" rows="5" cols="80"><%=commentsDataSet%></textarea></td>
            </tr>

    </table>
    <%if(dataSet == null || dataSet.getData() == null || dataSet.getData().size() == 0)
    {%>
    <table align="center" width="550px">
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
            <%=rsc.getDirectString("lang2.display.datasetToUpload")%>
          </td>
          <td>
            <input class="formInput" type="file" name="file1" size="20">
          </td>

        </tr>
      </table>
   <% }else{%>
       <table align="center" width="550px">
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
            <input type="button" class="formInputButton" name="deletebtnrecset"  id="deletebtnrecset" value="<%=rsc.getDirectString("lang2.display.deleterecset")%>" size="20" onclick="dosubmit('deleterecordset')">
          </td>
        </tr>
      </table>
   <% }%>
   <%if(dataSet != null && dataSet.getPicture() != null && dataSet.getPicture().size() > 0){%>
     <table align="center" width="550px">
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
            <input type="button" class="formInputButton" name="deletepicture"  id="deletepicture" value="<%=rsc.getDirectString("lang2.display.deletepictureset")%>" size="20" onclick="dosubmit('deletepictureset')">
          </td>
        </tr>
      <%
        Iterator itP = dataSet.getPicture().iterator();
        while(itP.hasNext())
        {
          Graphs g = (Graphs)itP.next();
          String name1 = g.getName();
          String url1 = g.getUrl();

          g = (Graphs)itP.next();
          String name2 = g.getName();
          String url2 = g.getUrl();

      %>
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
              <%=name1%><br/>
              <img src="<%=url1%>" class="thumbnail" alt="<%=url1%>" width="150">
          </td>
          <td class="tdMainFormHeader">
              <%=name2%><br/>
              <img src="<%=url2%>" class="thumbnail" alt="<%=url2%>" width="150">
          </td>

        </tr>
       <%
        }
       %>
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
            <input type="button" class="formInputButton" name="deletepicture"  id="deletepicture" value="<%=rsc.getDirectString("lang2.display.deletepictureset")%>" size="20" onclick="dosubmit('deletepictureset')">
          </td>
        </tr>

      </table>
    <%}
    else{}
    %>
    <table align="center" width="550px">
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
            <input type="reset" class="formInputButton" name="resetbtn"  id="resetbtn" value="<%=rsc.getDirectString("lang2.display.reset")%>" size="20" >
          </td>
          <td class="tdMainFormHeader">
            <input type="button" class="formInputButton" name="submitbtn"  id="submitbtn" value="<%=rsc.getDirectString("lang2.display.submit")%>" size="20" onclick="dosubmit('edit')">
          </td>
          <td class="tdMainFormHeader">
            <input type="button" class="formInputButton" name="deletebtn"  id="deletebtn" value="<%=rsc.getDirectString("lang2.display.delete")%>" size="20" onclick="dosubmit('delete')">
          </td>

        </tr>
      </table>

      </td>
    </tr>
  </table>

    </form>

</body>
</html>
<%
}
catch(Throwable th)
{th.printStackTrace();}
%>
