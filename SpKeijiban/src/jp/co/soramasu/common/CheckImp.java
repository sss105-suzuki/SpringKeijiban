package jp.co.soramasu.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import jp.co.soramasu.Interface.Check;
import jp.co.soramasu.Interface.DB_Action;

@Component
public class CheckImp implements Check{
	//書き換え前
//	private final DB_ActionImp db_act = new DB_ActionImp();
	//書き換え後
	private ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	private final DB_Action db_act = context.getBean(DB_Action.class);

	public String checkPassword (String pass) {
		String errorMessage = "";
		
		if (pass == "" || pass == null) {
			errorMessage += "パスワードを入力してください\n";
		} else {
			if (!(Pattern.matches("^[a-zA-Z0-9]+$", pass))) {
				errorMessage += "パスワードには半角英数字の文字列を入力してください\n";
			}
			if (!(Pattern.matches("^.{1,20}$", pass))){
				errorMessage += "パスワードの字数制限は20字です\n";
			}
		}
		
		return errorMessage;
	}
	
	public String checkID (String id) {
		String errorMessage = "";
		
		if ("".equals(id)) {
			errorMessage += "IDが入力されていません\n";
		}
		if (!(Pattern.matches("^\\d{5}$", id))) {
			errorMessage += "IDには5桁の数字を入力してください\n";
		}
		
		return errorMessage;
	}
	
	public String checkLogin (String pass, String id) {
		String errorMessage = "";
		
		if (!(db_act.searchUser(id, pass))) {
			errorMessage += "IDまたはパスワードが違います\n";
		}

		return errorMessage;
	}
	
	public String checkMessageNumber (String number) {
		String errorMessage = "";
		
		if (!(Pattern.matches("^\\d+$", number))) {
			if (Pattern.matches("\\-\\d+", number)) {
				errorMessage += "番号には正の整数を入力してください\n";
			} else {
				errorMessage += "番号には整数を入力してください\n";
			}
		} else {
			try{
				ResultSet rs = db_act.getSingleMessage(Integer.parseInt(number));
				if (Integer.parseInt(number) == 0) {
					errorMessage += "番号を選択してください\n";
				} else	if (!(rs.next())) {
					errorMessage += "その番号の書き込みは存在しません\n";
				}
			} catch (SQLException e) {
				errorMessage += "データベース接続エラー\n";
			}
		}

		return errorMessage;
	}
	
	public String checkMessageEdit (String pass,String number) {
		String errorMessage = "";

		int selectnumber = -1;
		try {
			selectnumber = Integer.parseInt(number);
			ResultSet rs = null;
			rs = db_act.getSingleMessage(selectnumber);
			rs.next();
			String realPass = rs.getString("edit_pass");
			if (!(realPass.equals(pass))) {
				errorMessage += "番号またはパスワードを確認してください\n";
			}
		} catch (NumberFormatException e) {
		} catch (SQLException e) {
			if (selectnumber != 0){
				errorMessage += "データベース接続エラー\n";
			}
		}
		
		return errorMessage;
	}
	
	public String checkNewInput (String name,String title,String pass,String message) {
		String errorMessage = "";
		
		if (!Pattern.matches("^.{1,}$", name)) {
			errorMessage += "名前を入力してください\n";
		}
		if (!Pattern.matches("^.{1,}$", title)) {
			errorMessage += "タイトルを入力してください\n";
		}
		errorMessage += checkPassword(pass);
		if (!Pattern.matches("^.{1,}$", message)) {
			errorMessage += "本文を入力してください\n";
		}
		if (!(message.length()<500)) {
			errorMessage += "書き込み字数は500字までです\n";
		}
		
		return errorMessage;
	}
	
	public String checkEditInput (String message) {
		String errorMessage = "";

		if (!(message.length()>0)) {
			errorMessage += "本文を入力してください\n";
		}

		if (!(message.length()<500)) {
			errorMessage += "書き込み字数は500字までです\n";
		}
		
		return errorMessage;
	}
	
	public String checkMessageParagraph (String message) {
		//スクリプト上書き対策
		String exCheck = message.replaceAll("&","&amp;");
		exCheck = exCheck.replaceAll("<","&lt;");
		exCheck = exCheck.replaceAll(">","&gt;");
		exCheck = exCheck.replaceAll("\"","&#034;");
		//テーブル変形対策
		String[] target = new String[79];
		target[0] = "\\-{149}";
		for (int i=1;i<79;i++) {
			target[i] = "[^ー\n]{" + (78-i) + "}\\ー{" + i + "}";
		}
		
		for (int i=0; i<79;i++) {
			Pattern bigHifn = Pattern.compile(target[i]);
			Matcher macher = bigHifn.matcher(exCheck);
			while (macher.find()) {
				StringBuilder sb = new StringBuilder();
				sb.append(exCheck);
				sb.insert(macher.end(), "\n");
				exCheck = new String(sb);
			}
		}
		//改行対応
		exCheck = exCheck.replaceAll("\n","<br>");
		
		return exCheck;
	}
}
