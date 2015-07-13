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
	//���������O
//	private final DB_ActionImp db_act = new DB_ActionImp();
	//����������
	private ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	private final DB_Action db_act = context.getBean(DB_Action.class);

	public String checkPassword (String pass) {
		String errorMessage = "";
		
		if (pass == "" || pass == null) {
			errorMessage += "�p�X���[�h����͂��Ă�������\n";
		} else {
			if (!(Pattern.matches("^[a-zA-Z0-9]+$", pass))) {
				errorMessage += "�p�X���[�h�ɂ͔��p�p�����̕��������͂��Ă�������\n";
			}
			if (!(Pattern.matches("^.{1,20}$", pass))){
				errorMessage += "�p�X���[�h�̎���������20���ł�\n";
			}
		}
		
		return errorMessage;
	}
	
	public String checkID (String id) {
		String errorMessage = "";
		
		if ("".equals(id)) {
			errorMessage += "ID�����͂���Ă��܂���\n";
		}
		if (!(Pattern.matches("^\\d{5}$", id))) {
			errorMessage += "ID�ɂ�5���̐�������͂��Ă�������\n";
		}
		
		return errorMessage;
	}
	
	public String checkLogin (String pass, String id) {
		String errorMessage = "";
		
		if (!(db_act.searchUser(id, pass))) {
			errorMessage += "ID�܂��̓p�X���[�h���Ⴂ�܂�\n";
		}

		return errorMessage;
	}
	
	public String checkMessageNumber (String number) {
		String errorMessage = "";
		
		if (!(Pattern.matches("^\\d+$", number))) {
			if (Pattern.matches("\\-\\d+", number)) {
				errorMessage += "�ԍ��ɂ͐��̐�������͂��Ă�������\n";
			} else {
				errorMessage += "�ԍ��ɂ͐�������͂��Ă�������\n";
			}
		} else {
			try{
				ResultSet rs = db_act.getSingleMessage(Integer.parseInt(number));
				if (Integer.parseInt(number) == 0) {
					errorMessage += "�ԍ���I�����Ă�������\n";
				} else	if (!(rs.next())) {
					errorMessage += "���̔ԍ��̏������݂͑��݂��܂���\n";
				}
			} catch (SQLException e) {
				errorMessage += "�f�[�^�x�[�X�ڑ��G���[\n";
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
				errorMessage += "�ԍ��܂��̓p�X���[�h���m�F���Ă�������\n";
			}
		} catch (NumberFormatException e) {
		} catch (SQLException e) {
			if (selectnumber != 0){
				errorMessage += "�f�[�^�x�[�X�ڑ��G���[\n";
			}
		}
		
		return errorMessage;
	}
	
	public String checkNewInput (String name,String title,String pass,String message) {
		String errorMessage = "";
		
		if (!Pattern.matches("^.{1,}$", name)) {
			errorMessage += "���O����͂��Ă�������\n";
		}
		if (!Pattern.matches("^.{1,}$", title)) {
			errorMessage += "�^�C�g������͂��Ă�������\n";
		}
		errorMessage += checkPassword(pass);
		if (!Pattern.matches("^.{1,}$", message)) {
			errorMessage += "�{������͂��Ă�������\n";
		}
		if (!(message.length()<500)) {
			errorMessage += "�������ݎ�����500���܂łł�\n";
		}
		
		return errorMessage;
	}
	
	public String checkEditInput (String message) {
		String errorMessage = "";

		if (!(message.length()>0)) {
			errorMessage += "�{������͂��Ă�������\n";
		}

		if (!(message.length()<500)) {
			errorMessage += "�������ݎ�����500���܂łł�\n";
		}
		
		return errorMessage;
	}
	
	public String checkMessageParagraph (String message) {
		//�X�N���v�g�㏑���΍�
		String exCheck = message.replaceAll("&","&amp;");
		exCheck = exCheck.replaceAll("<","&lt;");
		exCheck = exCheck.replaceAll(">","&gt;");
		exCheck = exCheck.replaceAll("\"","&#034;");
		//�e�[�u���ό`�΍�
		String[] target = new String[79];
		target[0] = "\\-{149}";
		for (int i=1;i<79;i++) {
			target[i] = "[^�[\n]{" + (78-i) + "}\\�[{" + i + "}";
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
		//���s�Ή�
		exCheck = exCheck.replaceAll("\n","<br>");
		
		return exCheck;
	}
}
