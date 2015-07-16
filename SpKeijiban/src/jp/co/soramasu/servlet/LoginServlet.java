package jp.co.soramasu.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.soramasu.Interface.Check;
import jp.co.soramasu.Interface.DB_Action;
import jp.co.soramasu.Interface.MessageList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginServlet {
//	private ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	//-DB_ActionImpl�̃C���X�^���X����---------------------------------------------------------------------
	@Autowired
	private DB_Action login_tbl_act;
	
//	private DB_Action login_tbl_act = context.getBean(DB_Action.class);
	
//	private DB_Action login_tbl_act = new DB_ActionImpl();
	//-CheckImpl�̃C���X�^���X����-------------------------------------------------------------------------
	@Autowired
	private Check check_act;
	
//	private Check check_act = context.getBean(Check.class);
	
//	private Check check_act = new CheckImpl();
	//-MessageListImpl�̃C���X�^���X����-------------------------------------------------------------------
	@Autowired
	private MessageList requestList;
	
//	private MessageList requestList = context.getBean(MessageList.class);

//	private MessageList requestList = new MessageListImpl();
	//-----------------------------------------------------------------------------------
	
	@RequestMapping(method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException{
		
		Enumeration<String> en = request.getParameterNames();
		//���������h�~����
		request.setCharacterEncoding("Windows-31J");
		//���͂��ꂽ�����̎擾
		String inputID = request.getParameter("userId");
		String inputPass = request.getParameter("userPassWord");
		String errorMessage = check_act.checkPassword(inputPass) + check_act.checkID(inputID);//NullPointerException
		try {
			while (en.hasMoreElements()) {
				String action = en.nextElement();
				if ("LoginButton".equals(action)) {
					errorMessage += check_act.checkLogin(inputPass, inputID);
					if("".equals(errorMessage)){
						//�������ݏ��̎擾
						
						ResultSet rs = login_tbl_act.getAllMessage ();
						if (rs.next()) {
							rs.previous();
							requestList.setAllMessage(rs);
						}
						request.setAttribute("MessageData", requestList);
						request.setAttribute("check", check_act);
						this.getServletContext().getRequestDispatcher("/newboard.jsp")
							.forward(request, response);
						return;
					}
				} else if ("EntryButton".equals(action)) {
					if ("".equals(errorMessage)){
						errorMessage += login_tbl_act.setUser(inputID, inputPass);
						request.setAttribute("errorMessage", errorMessage.replaceAll("\n", "<br>"));
						this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
						return;
					}
				}
			}
			request.setAttribute("errorMessage", errorMessage.replaceAll("\n", "<br>"));
			this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
}
