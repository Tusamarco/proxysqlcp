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
<%!static final String[] REQUEST_PARAMETERS = {"*"};%>
<%try{%>
<%
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
		if(version.equals("ext"))
			cversion = 2;
		if(version.equals("int"))
			cversion = 1;
		if(version.equals("cd"))
			cversion = 3;
		if(version.equals("unpublished"))
			cversion = 666;

//**************************************************************
Map areas = null;
areas = (Map)resultsData.get("areas.all.objects");
Map subsetM = null;
subsetM = (Map)resultsData.get("subsets.all.objects");

String reqServed = Text.defaultTo(resultsData.get("reqServed"),"X");
String reqServedSince = Text.defaultTo(resultsData.get("reqServedSince"),"today");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="Content-Type"  content="text/html;CHARSET=UTF-8">
	<link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css">
	<title>NCP - <%=reqServed%> - <%=reqServedSince%></title>
</head>

<body class="bodyText">
<%@include file="common/jsp/head.jsp"%>
<table width="100%" cellspacing="2" cellpadding="2" border="0">
<tr>
    <td>
		<table width="100%" cellspacing="2" cellpadding="2" border="0">
			<tr>
			    <td>
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr>
						    <td valign="top">
								<table cellspacing="0" cellpadding="1" border="0">
									<tr>
									    <td width="100%">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
												    <td class="bodyTitle"><%=rsc.getDirectString("caf_xlate.display.quicksection")%></td>
												</tr>
												<tr>
												    <td width="100%" class="bodyLineBlue"></td>
												</tr>
												<tr>
												    <td class="bodyH"><%=rsc.getDirectString("caf_xlate.display.datasets")%></td>
												</tr>
												<tr>
												    <td class="bodyText">
                                                     <%
                                                     if(subsetM != null && subsetM.size() > 0)
                                                     {
                                                           net.tc.isma.data.objects.subset sbs = null;
                                                           Iterator it = subsetM.keySet().iterator();
                                                           while( it.hasNext())
                                                           {
                                                                 Object idO = it.next();
                                                                 sbs = (subset)subsetM.get(idO);
                                                                 subsetBean sbsBean = (subsetBean)sbs.getAsBeanXml();

                                                                 String sbsId = Text.defaultTo((String)sbsBean.getId(),"");
                                                                 String name = sbsBean.getName(ls);
                                                                 out.println("<a class=\"body\" href=\"/faostat/fst?ac=getDataSet&set=" + sbsId + "&lang=" + lang + "\">" + name + "</a>&nbsp;");
                                                           }
                                                     }

													%>
													</td>
												</tr>
												<tr>
												    <td class="bodyH">&nbsp;</td>
												</tr>
												<tr>
												    <td class="bodyH"><%=rsc.getDirectString("caf_xlate.display.reports")%></td>
												</tr>
												<tr>
												    <td class="bodyText">
                                                        <ul>
                                                            <li><a class="body" href="/faostat/fst?ac=getReportsFbs&collection=9640&lang=<%=lang%>"><%=rsc.getDirectString("caf_xlate.display.fbs")%></a></li>
                                                        </ul>
													</td>

												<tr>
												    <td class="bodyH">&nbsp;</td>
												</tr>
												<tr>
												    <td class="bodyH"><%=rsc.getDirectString("caf_xlate.display.countries")%></td>
												</tr>
												<tr>
													<form name="countryform" action="/faostat/fst?ac=bycountry">
												    <td class="bodyText">
														<%=rsc.getDirectString("caf_xlate.display.selectcountry")%>:
														<select name="country" size="1">
															<option value="-1">-- none --</option>
															<%
																if(areas != null && areas.size() > 0)
																{
																	Collection c = areas.values();
																	List l = new ArrayList(c);
																	Collections.sort(l,new ObjectMultilingualComparator("namelong",ls));
																	Iterator it = l.iterator();
																	while(it.hasNext())
																	{
																		areaBean newArea = (areaBean)((area)it.next()).getAsBeanXml();
																		if(newArea != null)
																		{
																		%><option value="<%=newArea.getId()%>">
																			<%=Text.defaultTo(newArea.getNameLong(ls),newArea.getNameLong(new LanguageSelector("en")))%></option>
																		<%
																		}
																	}
																}
															%>
														</select>
														<a href="javascript()" class="body"><%=rsc.getDirectString("lang.res.search")%></a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr><td>&nbsp;<br></td></tr>
									<tr>
									    <td width="100%" class="bodyTitle"><%=rsc.getDirectString("caf_xlate.display.about")%></td>
									</tr>
									<tr>
									    <td width="100%" class="bodyLineBlue"></td>
									</tr>
									<tr>
									    <td width="100%" class="bodyText">
											<table>
												<tr>
													<td class="bodyText" colspan="3">
														<%=rsc.getDirectString("caf_xlate.display.headcomment")%>
													</td>
												</tr>
												<tr>
													<td class="bodyText">
														<ul type="circle" compact>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.production")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.trade")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.fbs")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.producerprices")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.fotrade")%></li>
														</ul>
													</td>
													<td class="bodyText">
														<ul type="circle" compact>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.landuseandirrigation")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.foproduct")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.fiproduct")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.population")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.codexalim")%></li>
														</ul>
													</td>
													<td class="bodyText">

														<ul type="circle" compact>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.fertilizerpesticide")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.agricmachinery")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.foodaidshipments")%></li>
															<li type="circle"><%=rsc.getDirectString("caf_xlate.display.exportbydest")%></li>
														</ul>
													</td>
												</tr>
											</table>
										</td>
									</tr>

								</table>
							</td>
						    <td width="250px" valign="top">
								<table cellspacing="2" cellpadding="2" border="0" width="100%">
									<tr>
									    <td class="bodyHeader"><%=rsc.getDirectString("caf_xlate.display.datareleases")%></td>
									</tr>
									<tr>
										<td class="bodyText">
											<%=rsc.getDirectString("lang.res.datareleasesData")%>
										</td>
									</tr>
									<tr>
									    <td class="bodyHeader"><%=rsc.getDirectString("caf_xlate.display.news")%></td>
									</tr>
									<tr>
										<td class="bodyText">
											<%
												  Vector whats_new = faostatOldCompatibilityTool.getWhatsNew(lang.toUpperCase(), version, reload);
										           byte[] tempByte;

												  if(whats_new!=null)
												  {
													  for(int ix = 0 ; ix < whats_new.size() ; ix++)
													  {
														if(ix == 2)
															break;

														Vector collection = (Vector)whats_new.elementAt(ix);
														String title = (String)collection.elementAt(0);
											            tempByte = title.getBytes();
											            title = new String(tempByte, "UTF-8");

													%><%=title%>:<%
														            for (int j=1; j<collection.size(); j++)
																	{
																	   String coll = (String)collection.elementAt(j);
																		tempByte = coll.getBytes();
																		coll = new String(tempByte, "UTF-8");

																	   %><li type="disc"><%=coll%></li><%
																	}
														%><br><%
													  }

												  }


											%>
											<br>

											<a href="/faostat/notes/jsp/whatsnew.jsp?language=<%=lang%>&version=<%=version%>" class="body"><b>...more</b></a>
										</td>
									</tr>

									<tr>
									    <td class="bodyHeader">products</td>
									</tr>
									<tr>
										<td class="bodyText">
											<a href="/faostat/subscription/faoscribe-e.jsp?formlogin=N" class="body">FAOSTAT On-line</a>
											<br>
											<a href="http://www.fao.org/catalog/book_review/giii/x9091-e.htm" class="body">FAOSTAT on CD-ROM</a>
										</td>
									</tr>
								</table>

							</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<%
if(qs.get("tree") != null && qs.get("tree").equals("true"))
{
  Map domain = null;
  domain = (Map)resultsData.get("domains.all.objects");
  Map collections = null;
  collections = (Map)resultsData.get("collections.all.objects");
  subset sbs = null;

  %>
  <table border="'1'">
          <tr>
                  <td>Subset</td><td colspan="3">Name</td>
          </tr>
  <%
                  Iterator it = subsetM.keySet().iterator();
                  while( it.hasNext())
                  {
                          Object idO = it.next();
                          sbs = (subset)subsetM.get(idO);
                          subsetBean sbsBean = (subsetBean)sbs.getAsBeanXml();

                          String sbsId = Text.defaultTo((String)sbsBean.getId(),"");
                          String name = sbsBean.getName(ls);
                          out.println("<tr bgcolor=\"green\"><td colspan='4'>" + sbsId + "</td><td colspan=3>" + name + "</td></tr>");
                          if(sbsBean.getDomains() != null && sbsBean.getDomains().size() > 0)
                          {
                                  out.println("<tr ><td></td><td></td><td colspan='2'>Domain</td><td>version</td><td>Name</td></tr>");

                                  Iterator itDom = sbsBean.getDomains().keySet().iterator();
                                  while(itDom.hasNext())
                                  {
                                          Object idDom = (Object)itDom.next();
                                          if(idDom instanceof SynchronizedMap)
                                          {
                                                  String idDomItem = (String)((SynchronizedMap)idDom).get("id");
                                                  int itemV = new Integer((String)((SynchronizedMap)idDom).get("version")).intValue();
                                                  if(itemV == 0 || itemV == cversion)
                                                  {
                                                      List l = net.tc.isma.data.referencepicker.domainPicker.getDomain(idDomItem, 0);
                                                          if(l != null && l.size() > 0)
                                                          {
                                                                  for(int ildom = 0 ; ildom < l.size() ; ildom++)
                                                                  {
                                                                          domainBean domBean = (domainBean)l.get(ildom);
                                                                          if(domBean == null)
                                                                          {
                                                                                  out.println("<tr bgcolor=\"grey\"><td></td><td></td><td colspan='2'><b>" + idDomItem + "</b></td><td>"+ itemV +"</td><td> n/a </td></tr>");
                                                                          }
                                                                          else
                                                                          {
                                                                                  String idDomben = domBean.getId().toString();
                                                                                  String nameDom = domBean.getName(ls);
                                                                                  out.println("<tr bgcolor=\"yellow\"><td>&nbsp;</td><td></td><td colspan='2'><i>" + idDomben + "</i></td><td>"+ itemV +"</td><td>"+ nameDom +"</td></tr>");

                                                                                  if(domBean.getCollections() != null)
                                                                                  {
                                                                                          out.println("<tr><td></td><td></td><td></td><td></td><td >Coll ID</td><td>version</td><td>Name</td></tr>");
                                                                                          Iterator itColl = domBean.getCollections().keySet().iterator();
                                                                                          while(itColl.hasNext())
                                                                                          {
                                                                                                  Object idCol = (Object)itColl.next();
                                                                                                  if(idCol instanceof SynchronizedMap)
                                                                                                  {
                                                                                                          String idColItem = (String)((SynchronizedMap)idCol).get("id");
                                                                                                          int itemColV = new Integer((String)((SynchronizedMap)idCol).get("version")).intValue();

                                                                                                          if(itemColV == 0 || itemColV == cversion)
                                                                                                          {
                                                                                                                  collectionBean colBean = net.tc.isma.data.referencepicker.collectionPicker.getCollection(idColItem);
                                                                                                                  if(colBean != null)
                                                                                                                  {
                                                                                                                          String idColben = colBean.getId().toString();
                                                                                                                          String nameCol = colBean.getName(ls);
                                                                                                                          if(colBean.getRedirect() != null)
                                                                                                                                  nameCol = "<a href='"+ colBean.getRedirect() +"' target='externalsite'>" + nameCol + "</a>";
                                                                                                                          out.println("<tr><td></td><td></td><td></td><td></td><td>" + idColben + "</td><td>"+ itemColV +"</td><td>"+ nameCol +"</td></tr>");
                                                                                                                  }
                                                                                                                  else
                                                                                                                  {
                                                                                                                          out.println("<tr><td></td><td></td><td></td><td></td><td>C " + idColItem + "</td><td>NA</td><td>NA</td></tr>");
                                                                                                                  }
                                                                                                          }
                                                                                                  }

                                                                                          }
                                                                                  }


                                                                          }
                                                                  }
                                                          }
                                                  }
                                          }

                                  }

                          }
                  }

  %>
  </table>
<%
}%>


</body>
</html>
<%}catch(Throwable th){th.printStackTrace();}%>
