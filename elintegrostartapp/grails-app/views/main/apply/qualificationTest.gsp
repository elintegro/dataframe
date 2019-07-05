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
  <title>Are you qualified?</title>
</head>
<body>
        <h1> Are you qualified?</h1>


      <g:form>

          <input type="radio" name="levels" value="entry"> Entry<br>
          <input type="radio" name="levels" value="beginner"> Beginner<br>
          <input type="radio" name="levels" value="intermediate" checked> Intermediate <br>
          <input type="radio" name="levels" value="upperint" checked> Upper-Intermediate  <br>
          <input type="radio" name="levels" value="advanced"> Advanced    <br>
          <input type="radio" name="levels" value="expert"> Expert  <br>

          <div id="entry"></div>
          <div id="beginner"></div>
          <div id="intermediate"></div>
          <div id="upperint"></div>
          <div id="advanced"></div>
          <div id="expert"></div>

          <g:submitButton name="submit" value="next"/>
      </g:form>
</body>
</html>