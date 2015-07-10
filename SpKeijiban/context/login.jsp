<%@page language="java" pageEncoding="Windows-31J" contentType="text/html; charset=Windows-31J" %>
<!-- jstl用 -->
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J"/>
	<meta http-equiv="Content-Style-type" content="text/css"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript"/>
	<script langage="JavaScript" type="text/javascript" src="Check.js"></script>
	<link rel="stylesheet" type="text/css" href="common.css" charset="Shift_JIS"/>
	<title>ログイン画面</title>
</head>
<body>
	<div class="tytle"
		align="center">
		<em class="tytle">
			ログイン
		</em>
	</div>
	<div align="center">
	<c:if test="${errorMessage != null}">
		<div><c:out value="${errorMessage}" escapeXml="false"/></div>
	</c:if>
	</div>
	<form name="loginForm" action="/SpKeijiban/login" method="post">
		<div class="table"
			align="center">
			<table border=1 class="small">
				<tr>
					<th class="gray">ログインID</th>
					<td><input type="text" name="userId" value="" maxlength=20 style="width:160px;"></td></tr>
				<tr>
					<th class="gray">パスワード</th>
					<td><input type="text" name="userPassWord" value="" maxlength=20 
						style="width:160px;"></td></tr>
			</table>
		</div>
		<div class="button"
			align="center">
			<input type="submit" name="LoginButton" value="ログイン" 
				onclick="return chkLogin(userId.value,userPassWord.value);"/>
			<input type="submit" name="EntryButton" value="アカウント登録" 
				onclick="return chkLogin(userId.value,userPassWord.value);"/>
		</div>
	</form>
</body>
</html>
