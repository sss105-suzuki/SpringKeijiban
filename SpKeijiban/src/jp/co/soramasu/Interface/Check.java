package jp.co.soramasu.Interface;

public interface Check {
	public String checkPassword (String pass);
	public String checkID (String id);
	public String checkLogin (String pass, String id);
	public String checkMessageNumber (String number);
	public String checkMessageEdit (String pass,String number);
	public String checkNewInput (String name,String title,String pass,String message);
	public String checkEditInput (String message);
	public String checkMessageParagraph (String message);
}
