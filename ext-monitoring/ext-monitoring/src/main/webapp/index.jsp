<%@page import="com.external.monitoring.Result"%>
<%@page import="com.external.monitoring.Monitoring"%>
<%@page import="com.external.monitoring.Response"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Service Monitoring</title>
<style>
body {
	background-color: #ffffff;
	padding: 20px 20px 20px 20px;
	font-size: 16px;
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif
}

.header {
	height: 50px;
	border: solid .5px #ffffff;
	font-weight: bold;
	font-family: verdana;
	color: #ffffff;
	background-color: #021B21;
	font-size: 24px;
	padding: 10 10 10 10;
}

.subHeader {
	border: solid .5px #ffffff;
	text-align: right;
	font-family: verdana;
	color: #ffffff;
	background-color: #021B21;
	font-size: 16px;
	padding: 10 10 10 10;
}

#outer {
	width: 80%;
	height: 80%;
	background-color: #F2EED8;
	border: solid 1px #eeeeee;
	vertical-align: middle;
	display: block;
}

#inner {
	background-color: #ffffff;
	border: solid .5px #ffffff;
}

#name {
	height: 30;
	font-size: 20px;
	background-color: #99D0F2;
	font-color: #000000;
	border: solid .5px #ffffff;
	font-color: #000000;
}

#description {
	font-size: 14px;
	font-style: italic;
	font-color: #000000;
	border: solid .5px #ffffff;
	font-color: #000000;
}

#result {
	font-size: 14px;
	background-color: #ffffff;
	font-color: #000000;
	border: solid .5px #ffffff;
}

#pass {
	width: 100px;
	font-size: 16px;
	text-align: center;
	background-color: #45A423;
	font-color: #000000;
	border: solid .5px #ffffff;
	font-color: #000000;
	font-size: 16px;
}

#fail {
	width: 100px;
	font-size: 16px;
	text-align: center;
	background-color: #FF0000;
	font-color: #000000;
	border: solid .5px #ffffff;
	font-color: #000000;
}

#message {
	width: 100px;
	font-size: 16px;
	text-align: center;
	background-color: #FF6666;
	font-color: #000000;
	border: solid .5px #ffffff;
	font-color: #000000;
	text-align: left;
}

HR{
	width: 98%;
	height:1px;
	background-color: #cccccc;
	border: solid 00px;
}
</style>
</head>
<%
	final Response resp = new Monitoring().monitor();
%>
<body>
	<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><div>
		<div id="outer">
			<table style="width: 100%; height: 100%; border: solid 1px black">
				<tr>
					<td class="header"><%=resp.header%></td>
				</tr>
				<tr>
					<td class="subHeader">Snap Taken at <%=resp.snapTime%></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<%
					for (Result result : resp.results) {
				%>
				<tr>
					<td>
						<div id="inner">
							<table style="width: 100%; height: 100%; border: solid 1px black">
								<%
									if (result.isSuccess()) {
								%>
								<tr>
									<td id="name">Plugin: <%=result.getName()%>
										&nbsp;&nbsp;&nbsp; <a id="description">[ <%=result.getDescription()%>
											]
									</a>
									</td>
									<td id="pass">SUCCESS</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2" id="result"><b>Message:</b><hr> <%=result.getResult()%></td>
								</tr>
								<%
									} else {
								%>
								<tr>
									<td id="name">Plugin: <%=result.getName()%>
										&nbsp;&nbsp;&nbsp; <a id="description">[ <%=result.getDescription()%>
											]
									</a>
									</td>
									<td id="fail">FAILURE</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2" id="result"><b></b><hr> <%=result.getException().getMessage()%></td>
								</tr>
								<tr>
									<td colspan="2" id="message"><b></b><hr> <%=result.getError()%></td>
								</tr>
								<%
									}
								%>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>