<%@ page import="com.elintegro.crm.Person"%>
<!-- -->
<!DOCTYPE html>
<html>

<head>
<link href="/elintegro/js/jquery/jqtree/jqtree.css" type="text/css"
	rel="stylesheet">
<script type="text/javascript"
	src="/elintegro/js/jquery/jqtree/tree.jquery.js"></script>
<style type="text/css">
.error {
	border: 1px solid #f1a899;
	background: #fddfdf;
	color: #5f3f3f;
	margin-bottom: 15px;
	font-size: 16px;
}

textarea {
	height: 200px;
	width: 600px;
}

input[type=submit] {
	padding: 10px;
	letter-spacing: 2px;
	cursor: pinter;
	border: 1px solid #000000;
	margin-bottom: 15px;
	margin-top: 15px;
	text-transform: uppercase;
}

.info {
	border: 1px solid #fcefa1;
	background: #fbf9ee url("images/ui-bg_glass_55_fbf9ee_1x400.png") 50%
		50% repeat-x;
	color: #363636;
	margin-bottom: 15px;
	font-size: 16px;
	padding: 10px 0px 10px 0px;
}
</style>
</head>
<body>
	<g:if test="${status == 500 }">
		<div class="error
          " style="text-align: center;">
			${result}
		</div>
	</g:if>
	<g:if test="${status == 200}">
		<div class="info" style="text-align: center;">Success</div>
	</g:if>
	<g:form controller="main" action="getSqlfromHql">
		<table>
			<tr>
				<td>Hql :</td>
				<td><g:textArea name="hql" value="${hql}"></g:textArea></td>
			</tr>
			<g:if test="${status ==  200}">
				<tr>
					<td>Sql :</td>
					<td><g:textArea name="sql" class="sql" readonly="readonly"
							value="${result}"></g:textArea></td>
				</tr>
				<tr>
					<td>No of Records :</td>
					<td>
						${noRecords }
					</td>
				</tr>
				<tr>
					<td>Types returned:</td>
					<td colspan="1"><g:each in="${returnTypes}" var="type">
							<b> ${type.name}
							</b>,
				   </g:each></td>
				</tr>
				<tr>
					<td colspan="2"><b>Data :</b></td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="1px solid #000000;" cellspacing="0"
							class="inner-table">
							<tbody>
								<g:each in="${data}" var="row">
									<tr>
										<g:each in="${row}" var="prop">
											<td style="padding: 10px;">
												${prop.toString()}
											</td>
										</g:each>
									</tr>
								</g:each>
							</tbody>
						</table>
					</td>
				</tr>
			</g:if>
			<tr>

				<td colspan="2" style="text-align: center;"><input
					type="submit" value="getSqlfromHql" /></td>
			</tr>
		</table>
	</g:form>
</body>
</html>