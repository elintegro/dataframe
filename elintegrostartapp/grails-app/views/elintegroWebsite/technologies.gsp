<%--
  Created by IntelliJ IDEA.
  User: Pangeni
  Date: 12/20/2019
  Time: 2:19 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <style>
    * {
        box-sizing: border-box;
    }
    html, body, div, img {
        margin: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
    }
    .technologiesContainer{
        display: flex;
        flex-wrap: wrap;
        padding: 20px;
        justify-content: center;
        align-items: center;
    }
    .item{
        margin: 5px;
    }
    </style>
</head>
<body>
<div class="technologiesContainer">
    <img class="item" src="${contextPath}/assets/java.PNG" alt="java">
    <img class="item" src="${contextPath}/assets/javascript.PNG" alt="javascript">
    <img class="item" src="${contextPath}/assets/grailsphoto.PNG" alt="grails">
    <img class="item" src="${contextPath}/assets/vuejs.PNG" alt="VueJS">
    <img class="item" src="${contextPath}/assets/kafka.PNG" alt="Kafka">
    <img class="item" src="${contextPath}/assets/oracle.PNG" alt="Oracle">
    <img class="item" src="${contextPath}/assets/nodejs.PNG" alt="NodeJS">
    <img class="item" src="${contextPath}/assets/kubernetes.PNG" alt="Kubernates">
    <img class="item" src="${contextPath}/assets/mysql.PNG" alt="Mysql">
</div>

</body>
</html>