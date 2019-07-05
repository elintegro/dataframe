<%--
  Created by IntelliJ IDEA.
  User: Shai
  Date: 11/29/12
  Time: 12:34 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Enter Personal Info</title>
  ${dataframeJavascript}
</head>
<body>
        <h1>Enter Personal Info</h1>
<jqval:resources/>
<pre>
    flowExecutionKey: ${flowExecutionKey}
    flowExecutionUrl: ${flowExecutionUrl}
    eventId: ${eventId}
</pre>
<!-- TODO externalize flowExecutionUrl, formId, -->
<form action="${flowExecutionUrl}" method="post" id="${formId}">
    <input type="hidden" name="execution" value="${flowExecutionKey}" id="execution" />
    ${dataframeHtml}
    <input type="submit" name="_eventId_submit" value="next" id="_eventId_submit" />

</form>

</body>
</html>