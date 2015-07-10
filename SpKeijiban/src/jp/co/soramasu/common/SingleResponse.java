package jp.co.soramasu.common;

public class SingleResponse {
	private int distinationnum = 0;
	private int responsenum  = 0;
	private String daynum = "";
	private String message = "";
	private String user_name = "";
	private String edit_pass = "";
	
	public void setDisNum (int newNum) {
		distinationnum = newNum;
	}
	
	public void setResNum (int newNum) {
		responsenum = newNum;
	}
	
	public void setDayNum (String newString) {
		daynum = newString;
	}
	
	public void setMessage (String newString) {
		message = newString;
	}
	
	public void setUserName (String newString) {
		user_name = newString;
	}
	
	public void setEditPass (String newString) {
		edit_pass = newString;
	}
	
	public int getDisNum(){
		return distinationnum;
	}
	
	public int getResNum(){
		return responsenum;
	}
	
	public String getDayNum(){
		return daynum;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getUserName(){
		return user_name;
	}
	
	public String getEditPass(){
		return edit_pass;
	}

}
