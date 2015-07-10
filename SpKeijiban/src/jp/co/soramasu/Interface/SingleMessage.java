package jp.co.soramasu.Interface;

public interface SingleMessage {
	public void setNumber(int newOne);
	public void setDate (String newOne);
	public void setMessage (String newOne);
	public void setName (String newOne);
	public void setTitle (String newOne);
	public void setPass (String newOne);
	public int getNumber ();
	public String getDate ();
	public String getMessage ();
	public String getName ();
	public String getTitle ();
	public String getPass ();
}
