package jp.co.soramasu.Interface;

import java.sql.ResultSet;

public interface DB_Action {
	public boolean searchUser (String inputID, String inputPass);
	public String setUser (String inputID, String inputPass);
	public ResultSet getSingleMessage (int messageNumber);
	public ResultSet getAllMessage ();
	public void setSingleMessage (String name, String title, String message, String pass);
	public void editSingleMessage (int number, String message);
	public void deleteSingleMessage (int messageNumber);
	public ResultSet getSingleResponse (int distinationNumber, int responseNumber);
	public  ResultSet getSetResponse (int distinationNumber);
	public ResultSet getAllResponse ();
	public void setSingleResponse (int distinationnum, String message, String user_name, String edit_pass);
	public void editSingleResponse (int distinationnum, int responsenum, String message);
	public void deleteSingleResponse (int distinationnum, int responsenum);
}
