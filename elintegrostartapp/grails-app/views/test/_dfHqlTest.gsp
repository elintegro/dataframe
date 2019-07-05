

<g:if test="${status == 500 }">
	<div class="info" style="text-align: center; overflow-x:auto;">
		${flash.message}
	</div>
</g:if>
%{--<g:if test="${isnamedParameter}">--}%
	%{--<g:each in="${namedParameter}" var="param">--}%
		%{--<label for="${param}">${param}:&nbsp;&nbsp;&nbsp;</label>--}%
		%{--<input type="text" id="${param}" name="${param}"/> <br>--}%
	%{--</g:each>--}%
	%{--<input type="hidden" id="${params?.page}-${params?.dataframe}-isnamedParameter"  name="isnamedParameter" value="${isnamedParameter}"/>--}%
	%{--<br>--}%
%{--</g:if>--}%
<g:if test="${status == 200}">
	<div class="info" style="text-align: center; overflow-x:auto;">Success</div>
</g:if>
<g:if test="${status == 200}">
	<g:if test="${sql}">
		<label for="sql">Sql:&nbsp;&nbsp;&nbsp;</label>
		<textarea id="sql" readonly>${sql}</textarea> <br>
		<script type="text/javascript">
            $(document).ready(function () {
                $("#sql").jqxTextArea({ height: 90, width: 400, minLength: 1});
            });
		</script>
	</g:if>
	<div style="overflow-x:auto; margin-top: 20px">
		<table>
			<tr>
				<td colspan="2"><b>Data :</b></td>
			</tr>
			<tr>
				<td colspan="2">
					<table border="1px solid #000000;" cellspacing="0"
						   class="inner-table">
						<tbody>
						<g:if test="${data}">

							<g:each in="${data}" var="row">
								<tr>
									<g:each in="${row}" var="prop">
										<td >
											${prop.toString()}
										</td>
									</g:each>
								</tr>
							</g:each>
						</g:if>
						<g:else>
							<h>No record found.</h>
						</g:else>
						</tbody>
					</table>
				</td>
			</tr>

		</table>
	</div>


</g:if>


