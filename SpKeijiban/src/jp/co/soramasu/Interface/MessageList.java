package jp.co.soramasu.Interface;

import java.sql.ResultSet;

public interface MessageList {
	public void setAllMessage (ResultSet newone);
	public SingleMessage getSingleMessage (int MessageNum);
	public int size ();
}
