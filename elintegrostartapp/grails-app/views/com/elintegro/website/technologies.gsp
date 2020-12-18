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
        margin:auto;
        background-color: white;
    }
    </style>
</head>
<body>
<div class="technologiesContainer">
    <img class="item" src="assets/java.PNG" alt="java">
    <img class="item" src="assets/javascript.PNG" alt="javascript">
    <img class="item" src="assets/grailsphoto.PNG" alt="grails">
    <img class="item" src="assets/vuejs.PNG" alt="VueJS">
    <img class="item" src="assets/kafka.PNG" alt="Kafka">
    <img class="item" src="assets/oracle.PNG" alt="Oracle">
    <img class="item" src="assets/nodejs.PNG" alt="NodeJS">
    <img class="item" src="assets/kubernetes.PNG" alt="Kubernates">
    <img class="item" src="assets/mysql.PNG" alt="Mysql">
</div>

</body>
</html>