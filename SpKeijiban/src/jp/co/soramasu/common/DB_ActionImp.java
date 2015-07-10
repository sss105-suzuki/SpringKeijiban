package jp.co.soramasu.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import jp.co.soramasu.Interface.DB_Action;

@Component
public class DB_ActionImp implements DB_Action{
	private final String DB_URL = "jdbc:postgresql://localhost/keijiban";
	private final String JDBC_DRIVER_NAME = "org.postgresql.Driver";
	private final String DB_USER = "postgres";
	private final String DB_PASSWORD = "postgres";
	private final String DB_ACCESS_ERROR_MESSAGE = "�f�[�^�x�[�X�ڑ��G���[";
	
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//�R���X�g���N�^
	public DB_ActionImp(){
		//�f�[�^�x�[�X�Ƃ̐ڑ�
		try {
			Class.forName(JDBC_DRIVER_NAME).newInstance();
			//DB�ւ̐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e1) {
			// TODO �����������ꂽ catch �u���b�N
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e1.printStackTrace();
		}		
	}
	
	//login_tbl����̃��[�U�[����
	public boolean searchUser (String inputID, String inputPass) {
		//�����pSQL��
		String selectSql = "select login_id, password, name from login_tbl where login_id = ? and password = ?";
		//ID&�p�X���[�h�̃`�F�b�N
		try {
			//��������
			ps = con.prepareStatement(selectSql);
			ps.setString(1, inputID);
			ps.setString(2, inputPass);
			rs = ps.executeQuery();
			if (!rs.next()){
				return false;
			}
			else {
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
	}

	//login_tbl�ւ̃��[�U�[�o�^
	public String setUser (String inputID, String inputPass) {
		String selectIdSql = "select login_id, password, name from login_tbl where login_id = ?";
		String setSql = "insert into login_tbl(login_id, password, name)"
				+ "values (?,?,'dummy')";
		try{
			ps = con.prepareStatement(selectIdSql);
			ps.setString(1, inputID);
			rs = ps.executeQuery();
			if (!(rs.next())){
				ps = con.prepareStatement(setSql);
				ps.setString(1, inputID);//SQLException
				ps.setString(2, inputPass);//SQLException
				ps.executeUpdate();
				con.commit();
				return "�A�J�E���g��o�^���܂���<br>";
			} else {
				return "����ID�͎g�p�ς݂ł�<br>";
			}
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
			return "�A�J�E���g�o�^�Ɏ��s���܂���<br>";
		}
	}
	
	//message_tbl����̒P�ꏑ�����ݏ�񔲏o
	public ResultSet getSingleMessage (int messageNumber) {
		String selectSql = "select * from message_tbl where number=?";
		try {
			ps = con.prepareStatement(selectSql);
			ps.setInt(1, messageNumber);
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
			return null;
		}
		
	}
	
	//message_tbl����̏������ݏ��S���o
	public ResultSet getAllMessage () {
		String selectSql = "select number,daynum,user_name,title,message,edit_pass " 
				+ "from message_tbl order by number asc";
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(selectSql);//���XSQLEx
			return rs;
		} catch (SQLException e1) {
			return null;
		}
	}
	
	//message_tbl�ւ̒P��V�K��������
	public void setSingleMessage (String name, String title, String message, String pass) {
		String setSql = "insert into message_tbl (number,daynum,user_name,title,message,edit_pass)"
				+ " values (nextval('message_tbl_number_seq'),'now',?,?,?,?)";
		try{
			ps = con.prepareStatement(setSql);
			ps.setString(1, name);
			ps.setString(2, title);
			ps.setString(3, message);
			ps.setString(4, pass);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
	
	//message_tbl�ւ̒P�ꏑ�����ݏ�������
	public void editSingleMessage (int number, String message) {
		String editSql = "update message_tbl set message=? where number=?";
		try{
			ps = con.prepareStatement(editSql);
			ps.setString(1, message);//SQLException
			ps.setInt(2, number);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
	
	//message_tbl����̒P�ꏑ�����ݏ��폜
	public void deleteSingleMessage (int messageNumber) {
		String deleteSql = "delete from message_tbl where number=?";
		try {
			//�폜
			ps = con.prepareStatement(deleteSql);
			ps.setInt(1, messageNumber);
			ps.executeUpdate();
			con.commit();
			//������
			ps = con.prepareStatement("select max(number) from message_tbl");
			ResultSet lastvalue = ps.executeQuery();
			lastvalue.next();
			int maxvalue = lastvalue.getInt(1);
			String updateSql = "update message_tbl set number=? where number=?";
			for (int i=messageNumber;i<maxvalue;i++) {
				ps = con.prepareStatement(updateSql);
				ps.setInt(1, i);
				ps.setInt(2, (i+1));//
				ps.executeUpdate();
				con.commit();
			}
			ps = con.prepareStatement("select setval('message_tbl_number_seq',?)");
			ps.setInt(1, maxvalue-1);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}

	//response_tbl����̒P�ꔲ�o
	public ResultSet getSingleResponse (int distinationNumber, int responseNumber) {
		String selectSql = "select daynum,distinationnum,responsenum,user_name,edit_pass,message" 
				+ " from response_tbl where distinationnum=? and responsenum=?";
		try {
			ps = con.prepareStatement(selectSql);
			ps.setInt(1, distinationNumber);
			ps.setInt(2, responseNumber);
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
			return null;
		}
	}
	
	//response_tbl����̈���w�蔲�o
	public  ResultSet getSetResponse (int distinationNumber) {
		String selectSql = "select daynum,distinationnum,responsenum,user_name,edit_pass,message" 
				+ " from response_tbl where distinationnum=? order by responsenum asc";
		try {
			ps = con.prepareStatement(selectSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, distinationNumber);
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e1) {
			return null;
		}
	}
	
	//response_tbl����̑S���o
	public ResultSet getAllResponse (){
		String selectSql = "select daynum,distinationnum,responsenum,user_name,edit_pass,message " 
				+ "from response_tbl order by distinationnum asc";
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(selectSql);//���XSQLEx
			return rs;
		} catch (SQLException e1) {
			return null;
		}
	}
	
	//response_tbl�ւ̒P��V�K��������
	public void setSingleResponse (int distinationnum, String message, String user_name, String edit_pass) {
		String setSql = "insert into response_tbl(daynum,distinationnum,responsenum,user_name,edit_pass,message)"
				+ " values ('now',?,getresnum(?),?,?,?);";
		try {
			ps = con.prepareStatement(setSql);
			ps.setInt(1, distinationnum);
			ps.setInt(2, distinationnum);
			ps.setString(3, user_name);
			ps.setString(4, edit_pass);
			ps.setString(5, message);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
	
	//message_tbl�ւ̒P�ꏑ�����ݏ�������
	public void editSingleResponse (int distinationnum, int responsenum, String message) {
		String editSql = "update response_tbl set message=? where distinationnum=? and responsenum=?";
		try{
			ps = con.prepareStatement(editSql);
			ps.setString(1, message);//SQLException
			ps.setInt(2, distinationnum);
			ps.setInt(3, responsenum);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
	
	//message_tbl����̒P�ꏑ�����ݏ��폜
	public void deleteSingleResponse (int distinationnum, int responsenum) {
		String deleteSql = "delete from response_tbl where distinationnum=? and responsenum=?";
		try {
			//�폜
			ps = con.prepareStatement(deleteSql);
			ps.setInt(1, distinationnum);
			ps.setInt(2, responsenum);
			ps.executeUpdate();
			con.commit();
			//������
			ps = con.prepareStatement("select max(responsenum) from response_tbl where distinationnum=?");
			ps.setInt(1, distinationnum);
			ResultSet lastvalue = ps.executeQuery();
			lastvalue.next();
			int maxvalue = lastvalue.getInt(1);
			String updateSql = "update response_tbl set responsenum=? where responsenum=? and distinationnum=?";
			for (int i=responsenum;i<maxvalue;i++) {
				ps = con.prepareStatement(updateSql);
				ps.setInt(1, i);
				ps.setInt(2, (i+1));//
				ps.setInt(3, distinationnum);
				ps.executeUpdate();
				con.commit();
			}
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
	
}
