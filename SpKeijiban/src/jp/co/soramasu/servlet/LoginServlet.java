package jp.co.soramasu.servlet;

import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.soramasu.Interface.Check;
import jp.co.soramasu.Interface.DB_Action;
import jp.co.soramasu.Interface.MessageList;
import jp.co.soramasu.common.LoginObj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginServlet {
//	private ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	//-DB_ActionImplのインスタンス生成---------------------------------------------------------------------
	@Autowired
	private DB_Action login_tbl_act;
	
//	private DB_Action login_tbl_act = context.getBean(DB_Action.class);
	
//	private DB_Action login_tbl_act = new DB_ActionImpl();
	//-CheckImplのインスタンス生成-------------------------------------------------------------------------
	@Autowired
	private Check check_act;
	
//	private Check check_act = context.getBean(Check.class);
	
//	private Check check_act = new CheckImpl();
	//-MessageListImplのインスタンス生成-------------------------------------------------------------------
	@Autowired
	private MessageList requestList;
	
//	private MessageList requestList = context.getBean(MessageList.class);

//	private MessageList requestList = new MessageListImpl();
	//-----------------------------------------------------------------------------------
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login", "command", new LoginObj());
	}
	
	@RequestMapping(value = "/newboard", method = RequestMethod.POST)
	protected String newboard(@ModelAttribute("SpringWeb")LoginObj request,
			ModelMap model) {
		
		//入力された文字の取得
		String inputID = request.getUserid();
		String inputPass = request.getUserpassword();
		String errorMessage = check_act.checkPassword(inputPass) + check_act.checkID(inputID);//NullPointerException
		try {
			if ("".equals(request.getLoginbutton())) {
				errorMessage += check_act.checkLogin(inputPass, inputID);
				if("".equals(errorMessage)){
					//書き込み情報の取得
					
					ResultSet rs = login_tbl_act.getAllMessage ();
					if (rs.next()) {
						rs.previous();
						requestList.setAllMessage(rs);
					}
					model.addAttribute("MessageData", requestList);
					model.addAttribute("check", check_act);
					return "newboard";
				}
			} else if("".equals(request.getEntrybutton())) {
				if ("".equals(errorMessage)){
					errorMessage += login_tbl_act.setUser(inputID, inputPass);
					model.addAttribute("errorMessage", errorMessage.replaceAll("\n", "<br>"));
					return "login";
				}
				
			}
			model.addAttribute("errorMessage", errorMessage.replaceAll("\n", "<br>"));
			return "login";
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return "err";
		}
	}
}
