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

//  String[] textStyle = new String[]{"chap",
//                                    "footer",
//                                    "parag",
//                                    "NCP_text",
//                                    "ss_chap",
//                                    "ss_parag",
//                                    "NCP_title",
//                                    "NCP_title1",
//                                    "Table_title",
//                                    "Table_subtitle",
//                                    "Table_text",
//                                    "Table_text_1",
//                                    "Table_text_special",
//                                    "Table_text_special_1",
//                                    "Table_text_special_2",
//                                    "figure",
//                                    "tableau"
//                                    };

  String[] textStyle = new String[]{"level 1",
                                    "&nbsp;level 2",
                                    "&nbsp;&nbsp;level 3",
                                    "&nbsp;&nbsp;&nbsp;level 4"
                                    };

  String[] pictureStyle = new String[]{ "simple","boxed","boxedleft","boxedcenter","boxedrigth","figure"};

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
  if(resultsData.get(Reports.class) != null)
  {
    report = (Reports)resultsData.get(Reports.class);
    reportLanguage = report.getLanguage()!=null?report.getLanguage():lang;
  }
  else
  {
    %>
    <html>
      <head>
        <link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css">

      </head>
      <body><center> Invalid Report, please return to the <a href="/ncp/ncp?ac=ncpcontrol">home page</a> selection</center></body><html>
    <%
  }
  Long reportId = null;
  Long themeId = null;
  Long tobjectId = null;
  Long hoId = null;
  Long hoType = null;

  String year = null;
  String areaId = null;
  String style = "";
  String textBox ="";


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

  if(resultsData.get("hoid.form.report") != null)
    hoId = (Long) resultsData.get("hoid.form.report");

  if(resultsData.get("hotype.form.report") != null)
    hoType = (Long)resultsData.get("hotype.form.report");



  List expandl1 = null;
  expandl1 = (List)resultsData.get("expandl1.form.report");
  List expandl2 = null;
  expandl2 = (List)resultsData.get("expandl2.form.report");
  List expandl3 = null;
  expandl3 = (List)resultsData.get("expandl3.form.report");

  HybernateObject  ho = null;

  if(hoId != null && !hoId.equals(""))
    ho = report.getInternalObject(hoId);

  if(ho != null && ho.getType() != null)
  {
      hoType = ho.getType();
      LanguageSelector ls2 = new LanguageSelector(reportLanguage);
        switch (ho.getType().intValue())
      {
        case 1: style = ((Textobjects)ho).getStyle()!=null?((Textobjects)ho).getStyle():"";
                textBox = ((Textobjects)ho).getText(ls2)!=null?((Textobjects)ho).getText(ls2):"";
                break;

        case 2: style = ((Datasets)ho).getStyle()!=null?((Datasets)ho).getStyle():"";break;
        case 3: style = ((Graphs)ho).getStyle()!=null?((Graphs)ho).getStyle():"";
//                textBox = ((Graphs)ho).g.getText(ls2)!=null?((Textobjects)ho).getText(ls2):"";
                break;

        case 4: style = ((Textobjects)ho).getStyle()!=null?((Textobjects)ho).getStyle():"";
                textBox = ((Textobjects)ho).getText(ls2)!=null?((Textobjects)ho).getText(ls2):"";
                break;


      }


  }



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

  var textAreaT = theForm.textbox;
//  alert(textAreaT.value.length);
//  if(textAreaT.value.length > 4000)
//    textAreaT.value = textAreaT.value.substring(0,3999);
//  alert(textAreaT.value.length);

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

<script type="text/javascript" src="/ncp/display/common/js/tiny_mce/tiny_mce_src.js"></script>
<script type="text/javascript">
        tinyMCE.init({
        theme : "advanced",
        language : "en",
        mode : "specific_textareas",
        document_base_url : "",
        relative_urls : false,
        remove_script_host : false,
        save_callback : "TinyMCE_Save",
        invalid_elements : "script,applet,iframe",
        theme_advanced_toolbar_location : "top",
        theme_advanced_source_editor_height : "550",
        theme_advanced_source_editor_width : "750",
        directionality: "ltr",
        force_br_newlines : "true",
        force_p_newlines : "false",
        content_css : "display/css/ncp_edit.css",
        debug : false,
        cleanup : true,
        safari_warning : false,
        plugins : "advlink, advimage, ,preview,searchreplace,insertdatetime,emotions,advhr,flash,table,fullscreen",
        theme_advanced_buttons2_add : ",preview,search,replace,insertdate,inserttime,emotions",
        theme_advanced_buttons3_add : ",advhr,flash,tablecontrols,fullscreen",
        plugin_insertdate_dateFormat : "%Y-%m-%d",
        plugin_insertdate_timeFormat : "%H:%M:%S",
        plugin_preview_width : "750",
        plugin_preview_height : "550",
        extended_valid_elements : "a[name|href|target|title|onclick], img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name], ,hr[class|width|size|noshade]",
        fullscreen_settings : {
                theme_advanced_path_location : "top"
        }
});
function TinyMCE_Save(editor_id, content, node)
{
        base_url = tinyMCE.settings['document_base_url'];
        var vHTML = content;
        if (true == true){
                vHTML = tinyMCE.regexpReplace(vHTML, 'href\s*=\s*"?'+base_url+'', 'href="', 'gi');
                vHTML = tinyMCE.regexpReplace(vHTML, 'src\s*=\s*"?'+base_url+'', 'src="', 'gi');
                vHTML = tinyMCE.regexpReplace(vHTML, 'mce_real_src\s*=\s*"?', '', 'gi');
                vHTML = tinyMCE.regexpReplace(vHTML, 'mce_real_href\s*=\s*"?', '', 'gi');
        }
        return vHTML;
}


</script>

</head>

<body class="bodyText">
    <form enctype="multipart/form-data"  method="post"  id="" name="ncpdataset" action="/ncp/ncp?ac=showtextform" >
<!--HEADER START -->
	<table class="tbHead" width="100%">
		<tr class="trHead">
		    <td class="tdHead" width="100%"  colspan="7"><img src="/ncp/display/img2/logo.gif" alt=""  border="0"></td>
		</tr>

		<tr class="trHead">
		    <td class="tdHead" width="100%" colspan="7"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
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
		    <td class="tdHead" width="100%" colspan="7"><img src="/ncp/display/img2/empty.gif" alt="" width="1" height="1" border="0"></td>
		</tr>
	</table>


<!--HEADER END -->

      <input type="hidden" name="expandl1" value="<%=Text.getAsStringSeparated(expandl1,",")%>"/>
      <input type="hidden" name="expandl2" value="<%=Text.getAsStringSeparated(expandl2,",")%>"/>
      <input type="hidden" name="expandl3" value="<%=Text.getAsStringSeparated(expandl3,",")%>"/>
      <input type="hidden" name="reportid" value="<%=reportId!=null?reportId.longValue():0%>"/>
      <input type="hidden" name="themeid" value="<%=themeId!=null?themeId.longValue():0%>"/>
      <input type="hidden" name="tobjectid" value="<%=tobjectId!=null?tobjectId.longValue():0%>"/>
      <input type="hidden" name="hoid" value="<%=hoId!=null?hoId.toString():""%>"/>
      <input type="hidden" name="hotype" value="<%=hoType!=null?hoType.toString():""%>"/>
      <input type="hidden" name="areas" value="<%=areaId!=null?areaId:""%>"/>
      <input type="hidden" name="year" value="<%=year!=null?year:""%>"/>
      <input type="hidden" name="formaction" value=""/>

  <table align="center" width="550px" class="tbMainForm" >
  <tr>
    <td>
    <table align="center" width="550px" >
            <tr class="trMainForm">
                    <td class="tdMainFormHeader"><%=rsc.getDirectString("lang2.display.textName")%></td>
                    <td class="tbMainFormInput"><input class="formInput" name="name" size="50" value="<%=ho!=null?Text.defaultTo(ho.getName(new LanguageSelector(reportLanguage)), ""):""%>" /></td>
            </tr>
            <tr class="trMainForm">
                    <td valign="top" class="tdMainFormHeader"><%=rsc.getDirectString("lang2.display.style")%></td>
                    <td class="tbMainFormInputList">
                      <%

                          String[] selectStyle = null;
                          if(hoType != null && hoType.intValue() == 3 )
                          {
                            selectStyle = pictureStyle;
                            %>&nbsp;<%
                          }
                          else
                          {
                          selectStyle = textStyle;
                          %>
                          <select name="style" size="5" class="formInputList"-->
                            <option value="normal" <%=style.equals("")?" SELECTED ":""%>>Not in the index</option>
                            <%
                            if(selectStyle != null && selectStyle.length > 0)
                            {
                              for(int iSt = 0 ; iSt < selectStyle.length; iSt++)
                              {
                                %>
                                <option value="<%=selectStyle[iSt]%>" <%=style.equals(selectStyle[iSt])?" SELECTED ":""%>><%=selectStyle[iSt]%></option>
                                <%
                              }

                            }%>
                              </select>
                          <%}%>
                    </td>
            </tr>
            <%if(hoType != null && (hoType.intValue() == 1 || hoType.intValue() == 4 )){%>
            <tr class="trMainForm">
                    <td class="tdMainFormHeader" colspan="2"><%=rsc.getDirectString("lang2.display.text")%></td>
            </tr>
            <tr class="trMainForm">
                    <td class="tbMainFormInput" colspan="2">
                      <textarea id="introtext" name="textbox" cols="75" rows="20" style="width:100%;px; height:350px;" mce_editable="true"><%=textBox%></textarea>
                    </td>
            </tr>
            <%}else{%>
            <input name="textbox" type="hidden"><%}%>
    </table>


    <%if(hoType != null && hoType.intValue() == 3 && (ho == null || ((Graphs)ho).getUrlEn()== null) ){%>
    <table align="center" width="550px" >
      <tr class="trMainForm">
          <td class="tdMainFormHeader">
            <%=rsc.getDirectString("lang2.display.fileToUpload")%>
          </td>
          <td class="tdMainFormInput">
            <input class="formInput" type="file" name="file1" size="20">
          </td>

        </tr>
      </table>
   <% }else if(hoType != null && hoType.intValue() == 3 && (ho != null && ((Graphs)ho).getUrlEn()!= null) ){%>
       <table align="center" width="550px" >
      <tr class="trMainForm">

          <td class="tdMainForm">
            <img  border="0" src="<%=((Graphs)ho).getUrlEn()%>" alt=""/>
          </td>
      </tr>
      <tr class="trMainForm">
          <td class="tdMainFormInput">
            <input class="formInputButton" type="button" name="deletebtnrecset"  id="deletebtnrecset" value="<%=rsc.getDirectString("lang2.display.deletefile")%>" size="20" onclick="dosubmit('deletefile')">
          </td>
        </tr>
      </table>
   <% }%>



    <table align="center" width="550px">
      <tr class="trMainForm">
          <td class="tbMainFormInput">
            <input type="reset" class="formInputButton" name="resetbtn"  id="resetbtn" value="<%=rsc.getDirectString("lang2.display.reset")%>" size="20" >
          </td>
          <td class="tbMainFormInput">
            <input type="button" class="formInputButton" name="submitbtn"  id="submitbtn" value="<%=rsc.getDirectString("lang2.display.submit")%>" size="20" onclick="dosubmit('edit')">
          </td>
          <td class="tbMainFormInput">
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
