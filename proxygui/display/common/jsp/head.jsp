<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- start Head -->
<%
String uri = request.getRequestURI();
HttpQuery qsh = null;
%>
<table width="100%" cellspacing="2" cellpadding="2" border="0">
<tr>
    <td>
		<table width="100%" cellspacing="2" cellpadding="2" border="0">
			<tr>
			    <td width="100%">
					<table width="100%" border="0">
						<tr class="faoname">
						<form name="quicksearchform" action="http://www.fao.org/waicent/search/default.asp" method="post" target="_blank">
							<input type="hidden" name="Search" value="Search">
							<input type="hidden" name="lang" value="en">
							<td rowspan=2>
							<img src="/faostat/display/img2/fao.gif"  align="left">
							<img src="/faostat/display/img2/faostat.jpg" align="left">
							</td>
							<td ><%=rsc.getDirectString("caf_xlate.display.FAOTitle")%></td>
						</tr>
						<tr>
							<td class="faoslogan" align="right" valign="top"><%=rsc.getDirectString("caf_xlate.display.FAODeclaration")%><br>
							   <p><input name="querystring" size="16" maxlength="40" type="text">
							   <a href="javascript:document.quicksearchform.submit();" class="search"><%=rsc.getDirectString("lang.res.search")%></a></p>
							</form>
							<div class="bodyText">
								<%qsh = qs;qsh.clear("lang");qsh.set("lang","en");%>
								<a href="<%=qsh%>" class="head"><%=rsc.getDirectString("caf_xlate.display.English")%></a>
								<%qsh.clear("lang");qsh.set("lang","ar");%>
								<a href="<%=qsh%>" class="head"><%=rsc.getDirectString("caf_xlate.display.Arabic")%></a>
								<%qsh.clear("lang");qsh.set("lang","zh");%>
								<a href="<%=qsh%>" class="head"><%=rsc.getDirectString("caf_xlate.display.Chinese")%></a>
								<%qsh.clear("lang");qsh.set("lang","fr");%>
								<a href="<%=qsh%>" class="head"><%=rsc.getDirectString("caf_xlate.display.French")%></a>
								<%qsh.clear("lang");qsh.set("lang","es");%>
								<a href="<%=qsh%>" class="head"><%=rsc.getDirectString("caf_xlate.display.Spanish")%></a>
							</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100%" class="bodyLineBlue" colspan="7"></td>
						</tr>
						<tr class="menuDefault">
							<td><a href="/faostat/fst?ac=home&lang=<%=lang%>" class="head"><%=rsc.getDirectString("caf_xlate.display.home")%></a></td>
							<td><a href="/faostat/faoinfo/contact-e.htm" class="head"><%=rsc.getDirectString("caf_xlate.display.contacts")%></a></td>
							<td><a href="/faostat/faoinfo/usage_statistics-e.htm" class="head"><%=rsc.getDirectString("caf_xlate.display.usagestats")%></a></td>
							<td><a href="http://www.fao.org/waicent/faoinfo/economic/ess/form.htm" class="head"><%=rsc.getDirectString("caf_xlate.display.datacontrib")%></a></td>
							<%if(user != null && !user.isAnonymous()){%>
                            <td align="right" bgcolor="red"><a href="/faostat/fst?ac=checksubscriber&logout=true&lang=<%=lang%>" class="head"><%=rsc.getDirectString("caf_xlate.display.logout")%>&nbsp;<%=user.getName()%> </a></td>
                            <%}else{%>
                            <td align="right"><a href="/faostat/fst?ac=checksubscriber&lang=<%=lang%>" class="head"><%=rsc.getDirectString("caf_xlate.display.login")%></a></td>
                            <%}%>
                        </tr>
						<tr><td width="100%" class="bodyLineBlue" colspan="7"></td></tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<!-- end Head -->
