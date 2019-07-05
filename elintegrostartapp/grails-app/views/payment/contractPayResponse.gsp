<%@ page import="grails.converters.JSON" %>
<script>
    var data = ${result as JSON};
    window.close();
    opener.contractPayResponse(data);
    // jQuery(document).ready(function () {
    //     updatePayResponse(data)
    // });
    //
    // function updatePayResponse(data) {
    //
    // }
</script>

