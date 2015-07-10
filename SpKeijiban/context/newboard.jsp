<%@ page language="java" pageEncoding="Windows-31J" contentType="text/html; charset=Windows-31J" %>
<%@ page import="jp.co.soramasu.common.*" %>
<!-- jstl用 -->
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J"/>
	<meta http-equiv="Content-Style-type" content="text/css"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript"/>
	<script langage="JavaScript" type="text/javascript" src="Check.js"></script>
	<script langage="JavaScript" type="text/javascript" src="Inputer.js"></script>
	<script>
	</script>
	<link rel="stylesheet" type="text/css" href="common.css" charset="Shift_JIS"/>
	<link rel="stylesheet" type="text/css" href="tabMenu.css" charset="Shift_JIS"/>
	<title>新掲示板模索</title>
</head>
<body>
	<div align="center" 
		style="width:1325px; height:50px; vertical-align:middle; display: table-cell;">
		<em class="tytle">
			掲示板<br>
			(一覧表示)
		</em>
	</div>
	<div style="width:1325px; height:25px;">
		<form name="logoutForm" action="login.jsp" method="post">
			<input type="submit" value="ログアウト" style="float:left;">
		</form>
	</div>
	<div style="width:1325px; height:300px; overflow-y:scroll; border: black 1px solid;">
		<c:if test="${MessageData.size() >= 1}">
			<c:forEach begin="0" end="${MessageData.size() -1}" step="1" varStatus="num">
			<table border=1 style="width:1305px; table-layout:fixed;">
				<tr>
					<th style="width:60px; text-align:left; vertical-align:text-top; 
								display:table-cell; background-color:#cccccc;" rowspan=3>No.
						<c:out value="${MessageData.getSingleMessage(num.index).getNumber()}" escapeXml="false"/>
					</th>
					<th style="width:60px; text-align:center; background-color:#cccccc;">
						日時
					</th>
					<th style="width:100px; text-align:center;">
						<c:out value="${MessageData.getSingleMessage(num.index).getDate()}" escapeXml="false"/>
					</th>
					<th style="width:60px; text-align:center; background-color:#cccccc;">
						名前
					</th>
					<th>
						<c:out value="${MessageData.getSingleMessage(num.index).getName()}" escapeXml="true"/>
					</th>
				</tr>
				<tr>
					<th style="width:60px; text-align:center; background-color:#cccccc;">
						タイトル
					</th>
					<th style="text-align:left;" colspan=3 >
						<c:out value="${MessageData.getSingleMessage(num.index).getTitle()}" escapeXml="true"/>
					</th>
				</tr>
				<tr>
					<th style="text-align:left; word-break:break-all;" colspan=4 
						id="msg<c:out value="${MessageData.getSingleMessage(num.index).getNumber()}"/>">
						<c:out value="${check.checkMessageParagraph(
											MessageData.getSingleMessage(num.index).getMessage()
										)}" escapeXml="false"/>
					</th>
				<tr>
			</table>
			</c:forEach>
		</c:if>
	</div>
	<div style="width:1325px; height:225px;">
		<div class="tabbox">
			<p class="tabs">
				<a id="tab1" href="#box1" class="nonacttab" onclick="ChangeTab('tab1','box1'); return false;">
					投稿</a>
				<a id="tab2" href="#box2" class="nonacttab" onclick="ChangeTab('tab2','box2'); return false;">
					編集</a>
				<a id="tab3" href="#box3" class="nonacttab" onclick="ChangeTab('tab3','box3'); return false;">
					管理メッセージ</a>
			</p>
			<c:if test="${InputType == 'new'}">
				<c:set var="OldNewuserName" value="${oldData.getName()}"/>
				<c:set var="OldNewTitle" value="${oldData.getTitle()}"/>
				<c:set var="OldNewMessage" value="${oldData.getMessage()}"/>
			</c:if>
			<c:if test="${InputType == 'edit'}">
				<c:set var="OldEditMessage" value="${oldData.getMessage()}"/>
			</c:if>
			<div id="box1" class="box">
				<form name="InputForm" action="/SpKeijiban/input" method="post">
					<textarea name="inputType" hidden>new</textarea>
					<table border=1 style="table-layout:fixed;">
						<tr>
							<th class="gray"   style="width:100px;">返信No.</th>
							<td align="center" style="width:90px">
								<select name="newNumber" style="width:85px;">
									<option value="0">新規投稿</option>
									<c:forEach begin="0" end="${MessageData.size()-1}" 
										step="1" varStatus="newloop">
										<option value="${newloop.index + 1}" 
											<c:if test="${oldData.getNumber() == newloop.index + 1}"
												>selected</c:if> >
											${newloop.index + 1}</option>
									</c:forEach>
							</td>
							<th class="gray"   style="width:100px;">名前</th>
							<td align="center" style="width:200px">
								<input type="text" value="${OldNewuserName}" 
									name="userName" style="width:195px;">
							</td>
							<th class="gray"   style="width:100px;">タイトル</th>
							<td align="center" style="width:455px">
								<input type="text" value="${OldNewTitle}" name="title" style="width:450px;">
							</td>
							<th class="gray"   style="width:100px">パスワード</th>
							<td align="center" style="width:120px;">
								<input type="password" value="" name="password" maxlength=20 
									style="width:115px;">
							</td>
							<td align="center" style="width:50px;">
								<input type="submit" value="投稿" style="float:right;width:50px;">
							</td>
						</tr>
						<tr>
							<td colspan=9 align="center">
								<textarea name="msg" style="width:1310px; height:160px;" 
									placeholder="本文(500字以内)" maxlength=500>${OldNewMessage}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="box2" class="box">
				<form name="InputForm" action="/SpKeijiban/input" method="post">
					<table border=1 style="table-layout:fixed;">
						<tr>
							<th class="gray"   style="width:100px;">対象No.</th>
							<td align="center" style="width:120px">
								<select name="editNumber" style="width:115px;">
									<option id="nonsel" value="0"></option>
									<c:forEach begin="0" end="${MessageData.size()-1}" step="1" varStatus="loop">
										<option id="sel${loop.index + 1}" value="${loop.index + 1}" 
											<c:if test="${oldData.getNumber() == loop.index + 1}"
												>selected</c:if> 
											onclick="getSelectMessage(<c:out value="${loop.index+1}"/>);">
											${loop.index + 1}</option>
									</c:forEach>
								</select>
							</td>
							<th class="gray"   style="width:100px;">操作内容</th>
							<td align="left" style="width:725px">
								<input type=radio name="inputType" value="edit" 
									<c:if test="${InputType != 'delete'}">checked</c:if>>修正
								<input type=radio name="inputType" value="delete"
									<c:if test="${InputType == 'delete'}">checked</c:if>>削除
							</td>
							<th class="gray"   style="width:100px">パスワード</th>
							<td align="center" style="width:120px;">
								<input type="password" value="" name="password" maxlength=20 
									style="width:115px;">
							</td>
							<td align="center" style="width:50px;">
								<input type="submit" value="編集" style="float:right;width:50px;">
							</td>
						</tr>
						<tr>
							<td colspan=7 align="center">
								<textarea id="editmsg" name="msg" style="width:1310px; height:160px;" 
									placeholder="本文(500字以内)" maxlength=500>${OldEditMessage}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="box3" class="box">
				<textarea name="msg" readonly style="width:1315px; height:189px;" 
					maxlength=500><c:out value="${InputError}" escapeXml="false"/></textarea>
			</div>
		</div>
		<script type="text/javascript">ChangeTab('tab1','box1');</script>
		<c:if test="${InputError != null && InputError != ''}">
			<script type="text/javascript">ChangeTab('tab3','box3');</script>
		</c:if>
	</div>
</body>
</html>