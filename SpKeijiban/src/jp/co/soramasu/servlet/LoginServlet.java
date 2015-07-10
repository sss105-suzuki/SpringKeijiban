package jp.co.soramasu.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.soramasu.Interface.Check;
import jp.co.soramasu.Interface.DB_Action;
import jp.co.soramasu.Interface.MessageList;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoginServlet extends HttpServlet {
	private ConfigurableApplicationContext context = 
	          new ClassPathXmlApplicationContext("beans.xml");
	private final DB_Action login_tbl_act = context.getBean(DB_Action.class);
	private final Check check_act = context.getBean(Check.class);
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException{
		
		Enumeration<String> en = request.getParameterNames();
		//•¶š‰»‚¯–h~ˆ—
		request.setCharacterEncoding("Windows-31J");
		//“ü—Í‚³‚ê‚½•¶š‚Ìæ“¾
		String inputID = request.getParameter("userId");
		String inputPass = request.getParameter("userPassWord");
		String errorMessage = check_act.checkPassword(inputPass) + check_act.checkID(inputID);
		try {
			while (en.hasMoreElements()) {
				String action = en.nextElement();
				if ("LoginButton".equals(action)) {
					errorMessage += check_act.checkLogin(inputPass, inputID);
					if("".equals(errorMessage)){
						//‘‚«‚İî•ñ‚Ìæ“¾
						MessageList requestList = context.getBean(MessageList.class);
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
			// TODO ©“®¶¬‚³‚ê‚½ catch ƒuƒƒbƒN
			e.printStackTrace();
		}
	}
}
