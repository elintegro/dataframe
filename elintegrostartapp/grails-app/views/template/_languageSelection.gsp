<!-- This is a template gsp to present the languages as radio buttons or selectors -->

<%@ page import="com.elintegro.ref.Language" %>
<%@ page import="java.util.Locale" %>
<%
	List languages = application.getAttribute("LANG_4_SELECT");

    int width = 25;
    int width1 = 20;
    // temporary solution for the case in which attribute LANG_4_SELECT does not exist
    if(languages) {
        width = 5*languages.size()
        width1 = 100/languages.size();

    };
		org.springframework.web.servlet.LocaleResolver localeResolver = org.springframework.web.servlet.support.RequestContextUtils.getLocaleResolver(request);
	Locale newLocaleReal = localeResolver.resolveLocale(request);
	Locale newLocale = new Locale(newLocaleReal.getLanguage(),"","");
	
	System.out.println((request.getParameter("lang") != null && request.getParameter("lang").trim().equals("ru") )?" checked='checked' ":" ");
 %>
<g:if test="${bean == 'RADIO'}">     
	<div id="radioLangi18" align="left" style=" padding:4px; width:1310px;  height:30px; margin-left:10px">
	<g:each in="${languages}" status="i" var="lang">		
		 <div style="width: 60px; height:20px; float: left; display:inline;">
			<div><input type="radio" name="langsel" id="langsel_${lang.code}" value="${lang.code}" <%=(newLocale.equals(new Locale("${lang.code}","","")) )?" checked='checked' ":" "%> onChange="onLanguageChange('${lang.code}')"></div>
			<div>${lang.name}</div>
		</div>
	</g:each>
</div></g:if>
<g:if test="${bean == 'SELECT'}">
	<div id="radioLangi18">
		<div style="padding-left:10px;padding-top:5px;">
			<select onChange="onLanguageChange(this.options[this.selectedIndex].value)">
				<g:each in="${languages}" status="i" var="lang">
				<option value="${lang.code}" <%=(newLocale.equals(new Locale("${lang.code}","","")) )?" selected='selected' ":" "%>>${lang.name}</option>		
				</g:each>
			</select>
		</div>
	</div>
</g:if>