<html>
<head>
    <meta http-equiv="refresh" content="0; url=/" />

    <script>
        var reloadPage = ${reloadPage?true:false};
        console.log(reloadPage);
        if(reloadPage){
            window.opener.location.reload();
            close();
        }
    </script>
</head>
<body><g:message code="redirecting" default="Redirecting..."/></body>
</html>