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
                java.text.SimpleDateFormat sdfDay = new java.text.SimpleDateFormat( "dd-MM-yyyy" );
                java.text.SimpleDateFormat sdfTime = new java.text.SimpleDateFormat( "HH:mm:ss" );

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

RangeHandler GCP = null;
if(resultsData.get("GlobalCheckpoint")!=null){
  GCP =  (RangeHandler)resultsData.get("GlobalCheckpoint");
}



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%if(inizialized && isMonitoring){%>
<META HTTP-EQUIV="refresh" Content="5; URL=/proxycp?ac=monitorhandler&do=refresh">
<%}%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"><head><title>MySQL cluster control pannel reqserved=9 - since = 11/March/2008</title>

	<meta http-equiv="Content-Type" content="text/html;CHARSET=UTF-8">
	<link href="display/css/ndbj.css" rel="stylesheet" type="text/css" /></head><body class="bodyText">

<h2 class="bodyTitle">Proxy Monitor Status</h2>
<form action="servlet" method="post" name="clustermgm" id="clustermgm">

<table>
	<tr class="bodyHeader">
		<td colspan='2'>Status</td>
		<td>Action</td>
	</tr>
<%
    if(!inizialized)
    {
%>
	<tr >
		<td>not inizialized</td>
		<td><img src="display/img2/icons/24x24/actions/button_cancel.png" width="24" height="24" alt="" /></td>
		<td><a href="http://localhost:8080/ndbcp?ac=startcp"><img border="0" src="display/img2/icons/inizialize.gif" alt="" /></a></td>
	</tr><%
    }
    else if(inizialized && !isMonitoring)
    {
        %>

	<tr >
		<td>inizialized - monitoring not started</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /></td>
		<td><a href="http://localhost:8080/ndbcp?ac=monitorhandler&amp;do=start"><img border='0'src="display/img2/icons/startmon.gif" width="124" height="29" alt="" /></a></td>
	</tr>
    <%
    }
    else if(inizialized && isMonitoring)
    {
    %>
	<tr >
		<td>monitoring running</td>
		<td><img src="display/img2/icons/watch_1.gif" width="22" height="22" alt="" /></td>
		<td><a href="http://localhost:8080/ndbcp?ac=monitorhandler&amp;do=stop"><img border='0'src="display/img2/icons/stopmon.gif" width="124" height="29" alt="" /></a></td>
	</tr>
    <%
    }
    else
    {
    %>
    <td colspan="3">problem in inizializing</td>
    <%
    }
    %>
</table>
<br />
<%
  if(inizialized)
  {
%>
<p class='bodyLineBlue'></p>

<h3 class="bodyTitle1">Monitor Status per category</h3>

		<table>
              <tr class="bodyHeader">
                <th></th>
                <th>Category name</th>
                <th>Monitor status</th>
                <!-- th>Action</th -->
              </tr>
<%
       for(int ic = 0 ; ic < locListeners.size(); ic++)
       {

                CategoryListner cl = (CategoryListner)locListeners.values().toArray()[ic];

%>
                <tr class="bodyLightBlue">
                  <td>+</td>
                  <td  ><%=cl.getName()%></td>
                  <td align="center"><%
                      if(cl.getStatus() == 0)
                      {out.print("<img src='display/img2/icons/24x24/actions/button_ok.png' width='16' height='16' alt='OK' />");}
                      else if(cl.getStatus() == 1)
                      {out.print("<img src='display/img2/warning.png' width='16' height='16' alt='' />");}
                      else if(cl.getStatus() == 2)
                      {out.print("<img src='display/img2/caution.png' width='16' height='16' alt='' />");}

                  %>
                  </td>
		  <!-- td align="center">
                    <img src="display/img2/icons/start.gif" width="124" height="29" alt="" />
                    <img src="display/img2/icons/stop.gif" width="124" height="29" alt="" />
                  </td -->
                </tr>

<%
       }
%>

        </table>


<p class='bodyLineBlue' />

<p/><table border="0">
	<tbody><tr class="bodyHeader">
		<td colspan="3"><b>Global monitoring Information</b></td>
	</tr>
	<tr  class="bodyLightBlue">
            <%
            if(GCP != null)
            {
              long elapsed = 0;
%>
		<td colspan="2">
                <%
                out.print(GCP.getEventStart().getEvent() + " "
                + sdfDay.format( GCP.getEventStart().getDate())
                + " " + sdfTime.format(GCP.getEventStart().getTime())
//                + GCP.getEventStart().
                );
                %>

		<br>
                <%
                if(GCP.getEventStop() != null)
                {
                  out.print(GCP.getEventStop().getEvent() + " "
                  + sdfDay.format(GCP.getEventStop().getDate())
                  + " " + sdfTime.format(GCP.getEventStop().getTime())
                  );

                  out.print("<br>");
                  elapsed = GCP.getEventStop().getSystemTime() - GCP.getEventStart().getSystemTime();



                  out.print("Execution time = " + elapsed + " milseconds" + " Status=" + GCP.getStatus());

                }
                %>
		</td>
		<td>
                  <%if(GCP.getStatus() == 0){%><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" />
                  <%}else if(GCP.getStatus() == 1 ){%><img src="display/img2/warning.png" width="16" height="16" alt="" />
                  <%}else{%><img src="display/img2/caution.png" width="34" height="34" alt="" /><%}%>
                </td>
	</tr>
        <%
          }
        %>

	<!-- tr  class="bodyLightBlue">
		<td>Table full error(s)</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>
	</tr -->

<!--
  	NDB_LE_TransporterError			:= 1,com.mysql.cluster.mgmj.listeners.handlers,TransporterErrorListener,1
  	NDB_LE_TransporterWarning		:= 1,com.mysql.cluster.mgmj.listeners.handlers,TransporterWarningListener,2
  	NDB_LE_MissedHeartbeat			:= 1,com.mysql.cluster.mgmj.listeners.handlers,MissedHeartbeatListener,3
  	NDB_LE_DeadDueToHeartbeat		:= 1,com.mysql.cluster.mgmj.listeners.handlers,DeadDueToHeartbeatListener,4
  	NDB_LE_WarningEvent			:= 1,com.mysql.cluster.mgmj.listeners.handlers,WarningEventListener,5
-->

	<tr class="bodyLightBlue">
		<td>Transporter Error</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>
	</tr>

	<tr class="bodyLightBlue">
		<td>Transporter Warning</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>
	</tr>

	<tr class="bodyLightBlue">
		<td>MissedHeartbeat</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>
	</tr>

	<tr class="bodyLightBlue">
		<td>Dead Due To Heartbeat</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>
	</tr>
	<tr class="bodyLightBlue">
		<td>Generic Warning Event</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>
	</tr>

	<!--tr class="bodyLightBlue">
		<td>Insufficent redo log cap.</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>			</tr>
	<tr class="bodyLightBlue">
		<td>MissedHeartbeat</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>
	</tr>
	<tr class="bodyLightBlue">
		<td>Disk Data Mem. Issue</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>
	</tr>
	<tr class="bodyLightBlue">
		<td>Disk Data Memory usage (%)</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>
	</tr>
	<tr class="bodyLightBlue">
		<td>Memory usage (%)</td>
		<td>0</td>
<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>
	</tr>
	<tr class="bodyLightBlue">
		<td>Distribution changed errors</td>
		<td>0</td>
		<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="24" height="24" alt="" /><img src="display/img2/warning.png" width="34" height="34" alt="" /><img src="display/img2/caution.png" width="34" height="34" alt="" /></td>
	</tr-->

	<tr><td colspan="2">&nbsp;</td></tr>
	<tr class="bodyHeader">
		<td colspan="2"><b>Node real time info</b></td>
	</tr>
	<tr>
		<td colspan="2">
			<table border="0">
				<tbody><tr class="bodyHeader">
					<td>id</td>
					<td>Last LCP</td>
					<td>data usage (%)</td>
					<td>HeartBeat Warning</td>
					<td>Status</td>
				</tr>
				<tr class="bodyLightBlue">
					<td>1</td>
					<td>xxx</td>
					<td>75</td>
					<td>0</td>
					<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>						</tr>
				<tr class="bodyLightBlue">
					<td>2</td>
					<td>xxx</td>
					<td>75</td>
					<td>0</td>
					<td><img src="display/img2/icons/24x24/actions/button_ok.png" width="16" height="16" alt="" /><img src="display/img2/warning.png" width="16" height="16" alt="" /><img src="display/img2/caution.png" width="16" height="16" alt="" /></td>						</tr>
			</tbody></table>
		</td>
	</tr>
</tbody></table>
<br />

<br />
<p class='bodyLineBlue' />


<%
{
    Map nodes = null;
   /**
    * Start of the node(s) block
    */
    if (monitoring.getNdbStatus() != null &&
        monitoring.getNdbStatus().getNodes() != null)
    {
      nodes = monitoring.getNdbStatus().getNodes() ;

    }

%>




<h2 class="bodyTitle">Node</h2>

<table border="0" cellpadding="2" cellspacing="2">
<tbody><tr class="bodyHeader">
    <td>id</td>
    <td>ip</td>
    <td>status</td>
    <td>group</td>
    <td>Data memory free</td>
    <td>Data %  free</td>
    <td>Index memory free</td>
    <td>Index % free</td>
    <td>Byte Snt</td>
    <td>Byte Rec</td>

    <td>Action</td>
    <td>order</td>
    <td>inizialize</td>
    <td>rollrestart</td>
</tr>
<%
    if(nodes != null)
    {
      ArrayList nodesClient = new ArrayList(nodes.values());

      for(int inodeD = 0 ; inodeD  < nodesClient.size(); inodeD++)
      {

        Node cNode = (Node)nodesClient.get(inodeD);
        if(cNode.getType() != null && cNode.getType().indexOf("_NDB") > 0)
        {

          %>
          <tr class="bodyLightBlue">
            <td><%=cNode.getId()%></td>
            <td><%=cNode.getAddress()%></td>
            <td>
            <%
            if(cNode.getStatus().equals("NDB_MGM_NODE_STATUS_STARTED"))
            {
              out.println("<img src='display/img2/icons/24x24/actions/button_ok.png' width='16' height='16' alt='' />");
            }
            else if (cNode.getStatus().equals("NDB_MGM_NODE_STATUS_STARTING"))
            {
              out.println("<img src=\"display/img2/warning.png\" width=\"16\" height=\"16\" alt=\"\" />");

            }
            else
            {
              out.println("<img src=\"display/img2/caution.png\" width=\"16\" height=\"16\" alt=\"\" />");

            }
            out.println("&nbsp <br>(" + cNode.getStatus().toString().toLowerCase().replaceAll("ndb_mgm_node","").replace('_',' ') +")");
            %>
            </td>
            <td><%=cNode.getNodeGroup()%></td>
            <%
            if(cNode.getMemoryUsage()!= null && cNode.getMemoryUsageIndex() != null)
            {
              out.println("<td>" + cNode.getMemoryUsage().getFreeMemoryMb() +"</td>");
              out.println("<td>" + cNode.getMemoryUsage().getFreeMemoryPrct() +"</td>");
              out.println("<td>" + cNode.getMemoryUsageIndex().getFreeMemoryMb() +"</td>");
              out.println("<td>" + cNode.getMemoryUsageIndex().getFreeMemoryPrct() +"</td>");

              out.println("<td>" + cNode.getByteSent() +"</td>");
              out.println("<td>" + cNode.getByteReceived() +"</td>");

            }
            else
            {
            %>
              <td>na</td>
              <td>na</td>
              <td>na</td>
              <td>na</td>
              <td>na</td>
              <td>na</td>


            <%}
            if(cNode.getStatus().equals("NDB_MGM_NODE_STATUS_STARTED"))
            {
            %>
             <td align="center"><b><a href="http://localhost:8080/ndbcp?ac=stopnode&nodeid=<%=cNode.getId()%>">[stop]</b></a>
             <!-- img src="display/img2/icons/stop.gif" width="60" height="21" alt="" /--></td>
            <%
             }
             else if(cNode.getStatus().equals("NDB_MGM_NODE_STATUS_STARTING"))
             {
             %><td align="center">&nbsp</td><%
             }
             else
             {
             %><td align="center"><a href="http://localhost:8080/ndbcp?ac=stopnode&nodeid=<%=cNode.getId()%>"><b>[start]</b></a><!--img src="display/img2/icons/start.gif" width="124" height="29" alt="" /--></td><%
             }
            %>
            <td>
              <select name="order1" size="1">
                <option value="1" selected="selected">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
              </select>
            </td>
            <td><input name="inizialize1" id="inizialize1" value="0" type="checkbox"></td>
              <td><input name="rollrestart1" id="rollrestart1" value="0" type="checkbox"></td>
          </tr>
          <%
          }
        }
      }
%>


</tbody></table>
<p>
<table border="0">
	<tr ><td class='bodyHeader' colspan='5'>Cluster actions</td></tr>
	<tr class="bodyLightBlue">
		<td ><img src="display/img2/icons/roollrestart.gif" width="124" height="29" alt="" /></td>
		<td><img src="display/img2/icons/fullrestart.gif" width="124" height="29" alt="" /></td>
		<td><img src="display/img2/icons/shutdowncluster.gif" width="124" height="29" alt="" /></td>
		<td class='bodyLineBlue' ><img src="display/img2/empty.gif" width="1" height="1" alt="" /></td>
		<td colspan='3'>
			<img src="display/img2/icons/backup.gif" width="124" height="29" alt="" />
		</td>
	</tr>
	<tr ><td class='bodyLineBlue' colspan='5'>&nbsp;</td></tr>
	<tr>
	</tr>

</table>
<br />
<p class='bodyLineBlue' />

<h2 class="bodyTitle">Client(s)</h2>

<table border="0" cellpadding="2" cellspacing="2">
<tbody><tr class='bodyHeader'>
    <td>id</td>
    <td>ip</td>

    <td>status</td>
    <td>API</td>
    <td>Type</td>
    <!-- td>Start</td>
    <td>Stop</td -->
</tr>
<%
    if(nodes != null)
    {

      ArrayList nodesClient = new ArrayList(nodes.values());

      for(int inode = 0 ; inode  < nodesClient.size(); inode++)
      {

        Node cNode = (Node)nodesClient.get(inode);
        if(cNode.getType() != null && cNode.getType().indexOf("_NDB") < 0)
        {
          %>
          <tr class="bodyLightBlue">
            <td><%=cNode.getId()%></td>
            <td><%=cNode.getAddress()%></td>
            <td><%=!cNode.getAddress().equals("0.0.0.0")?"Connected":"Not Connected"%></td>
            <td><%=cNode.getApiVersion()!= null?cNode.getApiVersion() + " " + cNode.getVersion():""%></td>
            <td><%=cNode.getType().replace('_', ' ')%></td>
            <!--td>St</td>
            <td>Sp</td -->
          </tr>
          <%
        }
      }
    }
%>
</tbody></table>
<br />




<%
}
/**
 * End of the Node(s) Block
 */
%>

<p class='bodyLineBlue' />

<h2 class="bodyTitle">MySQL Replication status</h2>
<table border="0">
	<tbody><tr class="bodyLightBlue">
		<td>threads</td>
		<td>2</td>
	</tr>
	<tr class="bodyLightBlue">
		<td>seconds behind master</td>
		<td>0</td>
	</tr>
	<tr class="bodyLightBlue">
		<td>max epoch</td>
		<td>N</td>
	</tr>

</tbody></table>


<br />
<p class='bodyLineBlue' />

<h2 class="bodyTitle">Monitoring Log details (60 minutes time frame)</h2>
<iframe src="../logs/mgm_listner.html" height="400" width="769"></iframe>
<p>
<br />


<%
  }
%>
</form>

</body></html>
