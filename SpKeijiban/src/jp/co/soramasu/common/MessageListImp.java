package jp.co.soramasu.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.soramasu.Interface.MessageList;
import jp.co.soramasu.Interface.SingleMessage;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MessageListImp implements MessageList{
	//書き込みリスト
	private ArrayList<SingleMessage> list = new ArrayList<SingleMessage>(); 
	
	public void setAllMessage (ResultSet newone) {
		
		try {
			while (newone.next()) {
				ConfigurableApplicationContext context = 
				          new ClassPathXmlApplicationContext("beans.xml");
				
				SingleMessage setMessage = context.getBean(SingleMessage.class);
				setMessage.setNumber(newone.getInt("number"));
				setMessage.setDate(newone.getString("daynum"));
				setMessage.setMessage(newone.getString("message"));
				setMessage.setName(newone.getString("user_name"));
				setMessage.setTitle(newone.getString("title"));
				setMessage.setPass(newone.getString("edit_pass"));
				list.add(setMessage);
				
				context.close();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public SingleMessage getSingleMessage (int MessageNum) {
		return list.get(MessageNum);
	}
	
	public int size (){
		return list.size();
	}
}
