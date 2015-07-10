/**
 * ログインの為に入力された値をチェックする。
 *
 * @param id	ID
 * @param pass	パスワード
 * @return チェック結果に問題がなければ、trueを返す
 */
function chkLogin(id, pass){
	var msg = "";
	if (id == "") {
		msg += "IDが入力されていません\n";
	}
	if (!(/^\d{5}$/.test(id))) {
		msg += "IDには5桁の数字を入力してください\n";
	}
	
	msg += chkPass(pass);
	
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		return true;
	}
}

/**
 * メッセージの編集・削除の為に入力された値をチェックする。
 *
 * @param number	番号
 * @param pass		パスワード
 * @return チェック結果に問題がなければ、trueを返す
 */
function chkSelectMessage(number, pass){
	var msg = "";
	if (number == "") {
		msg += "番号が入力されていません\n";
	}
	if (!(/^\d+$/.test(number))) {
		if (!(/^\-\d+$/.test(number))) {
			msg += "番号には正の整数を入力してください\n";
		} else {
			msg += "番号には整数を入力してください\n";
		}
	}
	
	msg += chkPass(pass);
	
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		return true;
	}
}

/**
 * 新規書き込み内容をチェックする。
 *
 * @param name		名前
 * @param title		タイトル
 * @param pass		パスワード
 * @param message	書き込み
 * @return チェック結果に問題がなければ、trueを返す
 */
function chkNewInput(name, title, pass, message) {
	var msg = "";
	if (name == "" || title == "" || pass == "" || message == "") {
		msg += "入力されていない項目があります\n";
	}
	if (message.length > 500) {
		msg += "書き込み字数は500字までです\n";
	}
	
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		return true;
	}
}

/**
 * 書き込み編集内容をチェックする。
 *
 * @param message	書き込み
 * @return チェック結果に問題がなければ、trueを返す
 */
function chkEditInput(message) {
	var msg = "";
	if (message == "") {
		msg += "メッセージを入力してください\n";
	}
	if (message.length > 500) {
		msg += "書き込み字数は500字までです\n";
	}
	
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		return true;
	}
}

/**
 * 入力されたパスワードをチェックする。
 *
 * @param pass	パスワード
 * @return チェック結果に応じた文字列を返す
 */
function chkPass(pass) {
	var msg = "";
	if (pass == "") {
		msg += "パスワードが入力されていません\n";
	}
	if (!(/^[a-zA-Z0-9]+$/.test(pass))) {
		msg += "パスワードには半角英数字の文字列を入力してください\n";
	}
	if (!(/^.{1,20}$/.test(pass))) {
		msg += "パスワードの字数制限は20字です\n";
	}
	return msg;
}

/**
 * チェック結果に問題があれば、メッセージを表示する。
 *
 * @return name		チェック結果に問題があれば、trueを返す
 */
function isError(msg) {
	if (msg != "") {
		alert(msg);
		return true;
	}
	return false;
}