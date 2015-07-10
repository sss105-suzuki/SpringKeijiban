package jp.co.soramasu.common;

import jp.co.soramasu.Interface.SingleMessage;

import org.springframework.stereotype.Component;

@Component
public class SingleMessageImp implements SingleMessage {
	private int number = 0;
	private String date = "";
	private String message = "";
	private String name = "";
	private String title = "";
	private String pass = "";

	public void setNumber (int newOne) {
		number = newOne;
	}
	
	public void setDate (String newOne) {
		date = newOne;
	}

	public void setMessage (String newOne) {
		message = newOne;
	}

	public void setName (String newOne) {
		name = newOne;
	}

	public void setTitle (String newOne) {
		title = newOne;
	}

	public void setPass (String newOne) {
		pass = newOne;
	}
	
	public int getNumber () {
		return number;
	}
	
	public String getDate () {
		return date;
	}
	
	public String getMessage () {
		return message;
	}
	
	public String getName () {
		return name;
	}
	
	public String getTitle () {
		return title;
	}
	
	public String getPass () {
		return pass;
	}
}
