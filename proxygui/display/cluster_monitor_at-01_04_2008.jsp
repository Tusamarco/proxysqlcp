<%@ page import="java.util.*" %>
<%@ page import="net.tc.isma.auth.security.*" %>
<%@ page import="net.tc.isma.persister.*" %>
<%@ page import="net.tc.isma.persister.IsmaPersister" %>
<%@ page import="net.tc.isma.utils.*" %>
<%@ page import="net.tc.isma.lang.*" %>
<%@ page import="net.tc.isma.data.objects.*" %>
<%@ page import="net.tc.isma.actions.*" %>
<%@ page import="net.tc.isma.actions.generic.*" %>
<%@ page import="net.tc.isma.request.generic.*" %>
<%@ page import="com.mysql.cluster.cp.objects.*" %>
<%!static final String[] REQUEST_PARAMETERS = {"*"};%>
<%
// Generic
		HttpSession sessioncheck = request.getSession(true);
		requestImpl requestWrapper = (requestImpl)request.getAttribute("requestImplementation");
		results resultsData = null;
		HttpQuery qs = null;
		String lang = Text.defaultTo(request.getParameter("lang"),"en");
		String version = Text.defaultTo(request.getParameter("version"),"ext");
		Locale locale = new Locale(lang);
//		IniStyleResourceBundle rsc = (IniStyleResourceBundle)IsmaPersister.getResourceBundle(locale);
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
//			rsc = (IniStyleResourceBundle)IsmaPersister.getResourceBundle(locale);
			resultList = (List) resultsData.get(List.class);
			user = requestWrapper.getUserBean();
		}
		LanguageSelector ls = new LanguageSelector(lang);
		String HOME = IsmaPersister.getMAINROOT();

//**************************************************************

String reqServed = Text.defaultTo(resultsData.get("reqServed"),"X");
String reqServedSince = Text.defaultTo(resultsData.get("reqServedSince"),"today");

boolean inizialized = false;
if (resultsData.get("ndbMgm.init")!=null){
  inizialized = ((Boolean)resultsData.get("ndbMgm.init")).booleanValue();
}

boolean isMonitoring = false;
NdbMonitorHandler monitoring = null;
if (resultsData.get("NdbMonitorHandler")!=null){
  monitoring = ((NdbMonitorHandler)resultsData.get("NdbMonitorHandler"));
  isMonitoring = monitoring.isMonitoring();
}


Map locListeners = null;
if(resultsData.get("NDBMGMListeners")!=null){
  locListeners = (Map)resultsData.get("NDBMGMListeners");
}






%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>MySQL cluster control pannel reqserved=<%=reqServed%> - since = <%=reqServedSince%></title>
	<meta http-equiv="Content-Type"  content="text/html;CHARSET=UTF-8">
	<link rel="stylesheet" href="display/css/ndbj.css" type="text/css">
</head>
<body class="bodyText">

<h2 style="tbHead">MGM info</h2>
<form action="servlet" method="post" name="clustermgm" id="clustermgm">

<%
if(!inizialized){
  out.println(" press <a href='/ndbcp?ac=startcp'>/ndbcp?ac=startcp</a> to inizialize");
}
else if(inizialized && locListeners !=null && !isMonitoring){
%>System inizialized - monitoring not started - <a href="/ndbcp?ac=monitorhandler&do=start">START MONITOR</a><%
    }else if(inizialized && locListeners !=null && isMonitoring)
    {
      %>System inizialized - monitoring running - <a href="/ndbcp?ac=monitorhandler&do=stop">STOP MONITOR</a><%
    }
%>


<h3>Actions</h3>
Restart
<table border="1">
	<tr>
		<td><input type="button" name="rollrestart" value="rollrestart"></td>
		<td><input type="button" name="generalrestart" value="full Restart"></td>
		<td><input type="button" name="shutdown" value="shutdown"></td>
	</tr>
</table>
<p>
<h2>Client(s)</h2>

<table cellspacing="2" cellpadding="2" border="1">
<tr>
    <td>id</td>
    <td>ip</td>

    <td>status</td>
    <td>Type</td>
    <td>Start</td>
    <td>Stop</td>
</tr>
<tr>
    <td>10</td>
    <td>10.0.0.7</td>
    <td>attached</td>
    <td>MySQL 5.1.3</td>
    <td>St</td>
    <td>Sp</td>
</tr>
<tr>
    <td>10</td>
    <td>10.0.0.5</td>
    <td>attached</td>
    <td>MySQL 5.1.3</td>
    <td>St</td>
    <td>Sp</td>
</tr>
</table>
<h2>Node(s)</h2>

<table cellspacing="2" cellpadding="2" border="1">
<tr>
    <td>id</td>
    <td>ip</td>
    <td>status</td>
    <td>group</td>
    <td>datamemory</td>
	<td>indexmemory</td>
	<td>datadir</td>
    <td>Start</td>
    <td>Stop</td>
    <td>order</td>
    <td>inizialize</td>
	<td>rollrestart</td>
</tr>
<tr>
    <td>10</td>
    <td>10.0.0.5</td>
    <td>OK</td>
    <td>0</td>
    <td>83886080</td>
	<td>18874368</td>
	<td>/usr/local/mysql/cluster_data</td>
	<td><input type="button" name="stop1" id="stop1" value="stop"></td>
	<td><input type="button" name="start1" id="start1" value="start"></td>
    <td>
		<select name="order1" size="1">
		<option value="1" SELECTED>1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		</select>
	</td>
    <td><input type="checkbox" name="inizialize1" id="inizialize1" value="0"></td>
    <td><input type="checkbox" name="rollrestart1" id="rollrestart1" value="0"></td>
</tr>
<tr>
    <td>11</td>
    <td>10.0.0.6</td>
    <td>OK</td>
    <td>0</td>
    <td>83886080</td>
	<td>18874368</td>
	<td>/usr/local/mysql/cluster_data</td>
	<td><input type="button" name="stop1" id="stop1" value="stop"></td>
	<td><input type="button" name="start1" id="start1" value="start"></td>

    <td>
		<select name="order1" size="1">
		<option value="1" SELECTED>1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		</select>
	</td>

    <td><input type="checkbox" name="inizialize" id="inizialize11" value="0"></td>
    <td><input type="checkbox" name="rollrestart1" id="rollrestart1" value="0"></td>
</tr>

</table>



<p>

<table border="1">
	<tr>
		<td colspan="2"><b>Global</b></td>
	</tr>
	<tr>
		<td>
			last GCP
		</td>
		<td>
			60
		</td>
	</tr>
	<tr>
		<td>Table full error(s)</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Insufficent redo log cap.</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Dead locks Time Outs</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Disk Data Mem. Issue</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Disk Data Memory usage (%)</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Memory usage (%)</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Distribution changed errors</td>
		<td>0</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td colspan="2"><b>Node real time info</b></td>
	</tr>
	<tr>
		<td colspan="2">
			<table border="1">
				<tr>
					<td>id</td>
					<td>Last LCP</td>
					<td>data usage (%)</td>
					<td>HeartBeat Warning</td>
					<td>more from Johan</td>
				</tr>
				<tr>
					<td>1</td>
					<td>xxx</td>
					<td>75</td>
					<td>0</td>
					<td>more from Johan</td>
				</tr>
				<tr>
					<td>2</td>
					<td>xxx</td>
					<td>75</td>
					<td>0</td>
					<td>more from Johan</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<h2>Replication</h2>
<table border="1">
	<tr>
		<td>threads</td>
		<td>2</td>
	</tr>
	<tr>
		<td>seconds behind master</td>
		<td>0</td>
	</tr>
	<tr>
		<td>max epoch</td>
		<td>N</td>
	</tr>

</table>

</form>
<h2>Log</h2>
<iframe src="display/cluster_node_details.htm" width="769" height="400"></iframe>
<p/>
Available Listeners (read ONLY for Now to modify it please edit the file at <%=HOME%>/conf/references/listners_handlers.ini) :
        <table class="" border="0">
              <tr class="bodyHeader">
                  <th>name</th>
                  <th>clazz</th>
                  <th>packagename</th>
                  <th>category</th>
                  <th>type</th>
                  <th>active</th>
                  <th>order</th>
                  <th>value_ok</th>
                  <th>value_warning</th>
                  <th>value_error</th>
                  <th>action_warning</th>
                  <th>action_error</th>
                  <th>message_ok</th>
                  <th>message_warning</th>
                  <th>message_error</th>
              </tr>
          <%
              for(int ic = 0 ; ic < locListeners.size(); ic++){

                CategoryListner cl = (CategoryListner)locListeners.values().toArray()[ic];
                Map listnerHandlermap = cl.getListners();
                %>
                <tr>
                  <td colspan="15" class="tdBodyTextNameLevel1">Category - <%=cl.getName()%> - <%=cl.getFullreferencename()%></td>
                </tr>
                <%
                for(int ilh = 0; ilh < listnerHandlermap.size(); ilh++){
                  ListnerHandler lh = (ListnerHandler)listnerHandlermap.values().toArray()[ilh];
                  %>
                <tr class="bodyLightBlue">
                  <td class="tbDatasetPreview"><%=lh.getName()%></td>
                  <td class="tbDatasetPreview"><%=lh.getClazz()%></td>
                  <td class="tbDatasetPreview"><%=lh.getPackagename()%></td>
                  <td class="tbDatasetPreview"><%=lh.getCategory()%></td>
                  <td class="tbDatasetPreview"><%=lh.getType()%></td>
                  <td class="tbDatasetPreview"><%=lh.isActive()%></td>
                  <td class="tbDatasetPreview"><%=lh.getOrder()%></td>
                  <td class="tbDatasetPreview"><%=lh.getValue_ok()%></td>
                  <td class="tbDatasetPreview"><%=lh.getValue_warning()%></td>
                  <td class="tbDatasetPreview"><%=lh.getValue_error()%></td>
                  <td class="tbDatasetPreview"><%=lh.getAction_warning()%></td>
                  <td class="tbDatasetPreview"><%=lh.getAction_error()%></td>
                  <td class="tbDatasetPreview"><%=lh.getMessage_ok()%></td>
                  <td class="tbDatasetPreview"><%=lh.getMessage_warning()%></td>
                  <td class="tbDatasetPreview"><%=lh.getMessage_error()%></td>
                </tr>

                <%
                  }
                  //close listner iterator
                  %>
              <%}
              //close category iterator

                %>

        </table>

</form>

</body>
</html>

