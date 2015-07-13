package jp.co.soramasu.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.soramasu.Interface.Check;
import jp.co.soramasu.Interface.DB_Action;
import jp.co.soramasu.Interface.MessageList;
import jp.co.soramasu.Interface.SingleMessage;

import org.springframework.beans.factory.annotation.Autowired;

public class InputServlet extends HttpServlet {
	@Autowired
	private DB_Action message_tbl_act;
	@Autowired
	private Check check_act;
	@Autowired
	private SingleMessage oldData;
	@Autowired
	private MessageList requestList;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException{
		//���������h�~����
		request.setCharacterEncoding("Windows-31J");
		//�������ݓo�^
		String inputType = request.getParameter("inputType");
		//���ʏ���
		String errorMessage = "";
		if ("new".equals(inputType)) {//�V�K��������
			String newName = request.getParameter("userName");
			String newTitle = request.getParameter("title");
			String newPass = request.getParameter("password");
			String newMessage = request.getParameter("msg");
			
			String newNumber = request.getParameter("newNumber");
			
			if ("0".equals(newNumber)) {
				errorMessage += check_act.checkNewInput(newName, newTitle, newPass, newMessage);
				if ("".equals(errorMessage)) {
					message_tbl_act.setSingleMessage(newName, newTitle, newMessage, newPass);
				}
			} else {
				newTitle = "dummytitle";
				errorMessage += check_act.checkNewInput(newName, newTitle, newPass, newMessage);
				if ("".equals(errorMessage)) {
					//�ԐM���e����
				}
			}
			
			if (!("".equals(errorMessage))) {
				errorMessage = "���e�G���[�F\n" + errorMessage;
				oldData.setNumber(0);
				oldData.setDate("");
				oldData.setMessage(newMessage);
				oldData.setName(newName);
				oldData.setTitle(newTitle);
				oldData.setPass(newPass);
			}
		} else {
			String inputNumber = request.getParameter("editNumber");
			int editNumber = Integer.parseInt(request.getParameter("editNumber"));
			String inputPass = request.getParameter("password");
			errorMessage += check_act.checkMessageNumber(inputNumber) 
					+ check_act.checkPassword(inputPass) 
					+ check_act.checkMessageEdit(inputPass, inputNumber);
			String newMessage = "";
			if ("edit".equals(inputType)) {//�ҏW
				newMessage = request.getParameter("msg");
				errorMessage += check_act.checkEditInput(newMessage);
				if ("".equals(errorMessage)) {
					message_tbl_act.editSingleMessage(editNumber, newMessage);
				}
			} else if ("delete".equals(inputType)) {//�폜
				if ("".equals(errorMessage)) {
					message_tbl_act.deleteSingleMessage(editNumber);
				}
			}
			if (!("".equals(errorMessage))) {
				errorMessage = "�ҏW�G���[�F\n" + errorMessage;
				oldData.setNumber(editNumber);
				oldData.setDate("");
				oldData.setMessage(newMessage);
				oldData.setName("");
				oldData.setTitle("");
				oldData.setPass("");
			}
		}
		request.setAttribute("oldData", oldData);
		request.setAttribute("InputError", errorMessage);
		request.setAttribute("InputType", inputType);
		//�������ݏ��̎擾
		ResultSet rs = message_tbl_act.getAllMessage ();
		try {
			if (rs.next()) {
				rs.previous();
				requestList.setAllMessage(rs);
			}
			request.setAttribute("MessageData", requestList);
			request.setAttribute("check", check_act);
			this.getServletContext().getRequestDispatcher("/newboard.jsp").forward(request, response);
		} catch (SQLException | NullPointerException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
}
