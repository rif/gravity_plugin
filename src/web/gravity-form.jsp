<%@ page
   import="org.jivesoftware.openfire.XMPPServer,
           com.zedmedia.gravity.plugin.GravityPlugin,
           org.jivesoftware.util.ParamUtils,
           java.util.HashMap,
           java.util.Map"
   errorPage="error.jsp"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<%
	boolean save = request.getParameter("save") != null;	
	boolean gravityEnabled = ParamUtils.getBooleanParameter(request, "gravityenabled", false);
	String gravitySubject = ParamUtils.getParameter(request, "gravitySubject");
	String gravityMessage = ParamUtils.getParameter(request, "gravityMessage");
    
	GravityPlugin plugin = (GravityPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("gravity");

	Map<String, String> errors = new HashMap<String, String>();	
	if (save) {
	  if (gravitySubject == null || gravitySubject.trim().length() < 1) {
	     errors.put("missingGravitySubject", "missingGravitySubject");
	  }
       
	  if (gravityMessage == null || gravityMessage.trim().length() < 1) {
	     errors.put("missingGravityMessage", "missingGravityMessage");
	  }
       
	  if (errors.size() == 0) {
	     plugin.setEnabled(gravityEnabled);
	     plugin.setSubject(gravitySubject);
	     plugin.setMessage(gravityMessage);
           
	     response.sendRedirect("gravity-form.jsp?settingsSaved=true");
	     return;
	  }		
	}
    
	gravityEnabled = plugin.isEnabled();
	gravitySubject = plugin.getSubject();
	gravityMessage = plugin.getMessage();
%>

<html>
	<head>
	  <title><fmt:message key="gravity.title" /></title>
	  <meta name="pageID" content="gravity-form"/>
	</head>
	<body>

<form action="gravity-form.jsp?save" method="post">

<div class="jive-contentBoxHeader"><fmt:message key="gravity.options" /></div>
<div class="jive-contentBox">
   
	<% if (ParamUtils.getBooleanParameter(request, "settingsSaved")) { %>
   
	<div class="jive-success">
	<table cellpadding="0" cellspacing="0" border="0">
	<tbody>
	  <tr>
	     <td class="jive-icon"><img src="images/success-16x16.gif" width="16" height="16" border="0"></td>
	     <td class="jive-icon-label"><fmt:message key="gravity.saved.success" /></td>
	  </tr>
	</tbody>
	</table>
	</div>
   
	<% } %>
   
	<table cellpadding="3" cellspacing="0" border="0" width="100%">
	<tbody>
	  <tr>
	     <td width="1%" align="center" nowrap><input type="checkbox" name="gravityenabled" <%=gravityEnabled ? "checked" : "" %>></td>
	     <td width="99%" align="left"><fmt:message key="gravity.enable" /></td>
	  </tr>
	</tbody>
	</table>
   
   <br><br>
	<p><fmt:message key="gravity.directions" /></p>
   
	<table cellpadding="3" cellspacing="0" border="0" width="100%">
	<tbody>
	  <tr>
	     <td width="5%" valign="top"><fmt:message key="gravity.subject" />:&nbsp;</td>
	     <td width="95%"><input type="text" name="gravitySubject" value="<%= gravitySubject %>"></td>
	     <% if (errors.containsKey("missingGravitySubject")) { %>
	        <span class="jive-error-text"><fmt:message key="gravity.subject.missing" /></span>
	     <% } %> 
	  </tr>
	  <tr>
	     <td width="5%" valign="top"><fmt:message key="gravity.message" />:&nbsp;</td>
	     <td width="95%"><textarea cols="45" rows="5" wrap="virtual" name="gravityMessage"><%= gravityMessage %></textarea></td>
	     <% if (errors.containsKey("missingGravityMessage")) { %>
	        <span class="jive-error-text"><fmt:message key="gravity.message.missing" /></span>
	     <% } %>            
	  </tr>
	</tbody>
	</table>
</div>
<input type="submit" value="<fmt:message key="gravity.button.save" />"/>
</form>

</body>
</html>
