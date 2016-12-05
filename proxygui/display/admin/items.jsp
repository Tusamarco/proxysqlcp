<%@ page import="java.util.*" %>
<%@ page import="net.tc.isma.auth.security.*" %>
<%@ page import="net.tc.isma.persister.*" %>
<%@ page import="net.tc.isma.utils.*" %>
<%@ page import="net.tc.isma.lang.*" %>
<%@ page import="net.tc.isma.data.objects.*" %>
<%@ page import="net.tc.isma.actions.*" %>
<%@ page import="net.tc.isma.actions.generic.*" %>
<%@ page import="net.tc.isma.request.generic.*" %>
<%!static final String[] REQUEST_PARAMETERS = {"*"};%>
<%try{%>
<%
// IMPORTANT: REQUIRES ismainclude.jsp BE INCLUDED IN CALLING JSP! //
HttpSession sessioncheck = request.getSession(true);
		requestImpl requestWrapper = (requestImpl)request.getAttribute("requestImplementation");
//
		results resultsData = (results)requestWrapper.getResult();
		HttpQuery qs = requestWrapper.getHttpQuery(REQUEST_PARAMETERS);
//
		String lang = requestWrapper.getLanguage();
		Locale locale = requestWrapper.getLocale();
		ResourceBundle rsc = IsmaPersister.getResourceBundle(locale);
		List resultList = (List) resultsData.get(List.class);
//
		LanguageSelector ls = new LanguageSelector(lang);
//		//String currentLang = ls.getCurrentLanguage();
		String HOME = IsmaPersister.getMAINROOT();
//
		UserBase user = requestWrapper.getUserBean();

//
//**************************************************************

Map items = null;
Map areas = null;
Map elements = null;
items = (Map)resultsData.get("items.all.records");
areas = (Map)resultsData.get("areas.all.records");
elements = (Map)resultsData.get("elements.all.records");


%>


<!doctype html public "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Expires" content="Tue, 01 Jun 1999 19:58:02 GMT">
<meta name="Pragma" content="no-cache">
<title>FAOSTAT - ITEMS</title>
</head>

<body>



<table bgcolor="#ffffcc">
<%
	if(items == null || items.size() <= 0)
	{
		out.print("No records found");
	}
	else
	{

		Iterator it = null;
		it = items.keySet().iterator();
		while(it.hasNext())
		{
			Object itemId = it.next();
			item tempItem = (item)items.get(itemId);
			itemBean newItem = null;
			newItem	= (itemBean)tempItem.getAsBean();

	%><tr>
		<td>
			Objectid = <%=itemId.toString()%>
		</td>
		<td>
			<%=newItem.getId()%>
		</td>
		<td>
			<%=newItem.getDisseminateind()%>
		</td>
		<td>
			<%=newItem.getGrpind()%>
		</td>
		<td>
			<%=newItem.getName(ls)%>
		</td>
		<td>
			<%=newItem.getNameLong(ls)%>
		</td>

	 </tr><%
		}
%>
<tr><td bgcolor="black" colspan="5">&nbsp;</td> </tr>
<%
		it = areas.keySet().iterator();
		while(it.hasNext())
		{
			Object itemId = it.next();
			area tempArea = (area)areas.get(itemId);
			areaBean newArea = null;
			newArea	= (areaBean)tempArea.getAsBean();

	%><tr>
		<td>
			Objectid = <%=itemId.toString()%>
		</td>
		<td>
			<%=newArea.getId()%>
		</td>
		<td>
			<%=newArea.getISO3()%>
		</td>
		<td>
			<%=newArea.getGrpInd()%>
		</td>
		<td>
			<%=newArea.getName(ls)%>
		</td>
		<td>
			<%=newArea.getNameLong(ls)%>
		</td>

	 </tr><%
		}
%>
	<tr><td bgcolor="black" colspan="5">&nbsp;</td> </tr>
<%
		it = elements.keySet().iterator();
		while(it.hasNext())
		{
			Object itemId = it.next();
			element tempelement = (element)elements.get(itemId);
			elementBean newelement = null;
			newelement	= (elementBean)tempelement.getAsBean();


	%><tr>
		<td>
			Objectid = <%=itemId.toString()%>
		</td>
		<td>
			<%=newelement.getId()%>
		</td>
		<td>
			<%=newelement.getItemType()%>
		</td>
		<td>
			<%=newelement.getDisplayele()%>
		</td>
		<td>
			<%=newelement.getName(ls)%>
		</td>
		<td>
			<%=newelement.getNameLong(ls)%>
		</td>

	 </tr><%
		}


	}

%>

</table>

</body>
</html>
<%}catch(Throwable th){th.printStackTrace();}%>
