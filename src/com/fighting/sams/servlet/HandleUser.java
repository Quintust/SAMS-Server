package com.fighting.sams.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fighting.sams.dal.DBAdmService;
import com.fighting.sams.dal.DBCreService;
import com.fighting.sams.dal.DBStuService;
import com.fighting.sams.dal.DBTchService;
import com.fighting.sams.dal.DatabaseHelper;
import com.fighting.sams.model.Admin;
import com.fighting.sams.model.Credit;
import com.fighting.sams.model.Student;
import com.fighting.sams.model.Teacher;
import com.fighting.sams.model.User;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;
import com.fighting.sams.utils.http.HttpUtil;
import com.google.gson.Gson;

public class HandleUser extends HttpServlet {
	private DBStuService stu;
	private DBTchService tch;
	private DBAdmService adm;
	private DBCreService cre;
	@Override
	public void init(ServletConfig config) throws ServletException {
		stu = new DBStuService();
		tch = new DBTchService();
		adm = new DBAdmService();
		cre = new DBCreService();
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String[] type = request.getParameter("type").split("_");
		switch (type[0]) {
		case "reg":
			userRegister(request, response, type[1]);
			break;
		case "login":
			userLogin(request, response, type[1]);
			break;
		case "update":
			userUpdate(request, response, type[1]);
			break;
		default:
			break;
		}
	}
	
	private void userRegister(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException {
		Credit credit=null;
		switch (type) {
		case "stu":
			Student objStu = new Student(request.getParameter("ID"),
					request.getParameter("PWD"),request.getParameter("Name"),
					request.getParameter("Gender"),request.getParameter("YoB"),
					request.getParameter("ErlYear"),request.getParameter("Remark"));
			credit = new Credit();
			credit.setID(objStu.getID());
			credit.setCredit(0);
			credit.setRemark(objStu.getRemark());
			cre.insert(credit);
			response.getWriter().write((stu.insert(objStu)).toString());
			break;
		case "tch":
			Teacher objTch = new Teacher(request.getParameter("ID"),request.getParameter("PWD"),
					request.getParameter("Name"),request.getParameter("Gender"),request.getParameter("YoB"),
					request.getParameter("Title"),request.getParameter("Remark"));
			credit = new Credit();
			credit.setID(objTch.getID());
			credit.setCredit(0);
			credit.setRemark(objTch.getRemark());
			cre.insert(credit);
			response.getWriter().write((tch.insert(objTch)).toString());
			break;

		default:
			break;
		}
	}
	
	private void userLogin(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException {
		String ip = HttpUtil.getIpAddress(request);
		int port = request.getRemotePort();
		String user_ip = ip+":"+port;
		System.out.println("ip="+user_ip);
		String ID = request.getParameter("ID");
		String PWD = request.getParameter("PWD");
		Credit credit = new Credit();
		credit.setID(ID);
		cre.update(credit);
		switch (type) {
		case "stu":
			stu.executeUpdate("UPDATE "+TABLE_NAME.tstu+" SET Remark='"+user_ip+"' WHERE ID='"+ID+"'");
			response.getWriter().write( (stu.checkLogin(ID, PWD)).toString());
			break;
		case "tch":
			tch.executeUpdate("UPDATE "+TABLE_NAME.ttch+" SET Remark='"+user_ip+"' WHERE ID='"+ID+"'");
			response.getWriter().write( (tch.checkLogin(ID, PWD)).toString());
			break;
		case "adm":
			adm.executeUpdate("UPDATE "+TABLE_NAME.tadm+" SET Remark='"+user_ip+"' WHERE ID='"+ID+"'");
			response.getWriter().write((adm.checkLogin(ID, PWD)).toString());
			break;
		default:
			break;
		}
	}
	
	private STATE userUpdate(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException {
		STATE state = STATE.FAILURE;
		String ID = request.getParameter("ID");
		String PWD = request.getParameter("PWD");
		String Name = request.getParameter("Name");
		String YoB = request.getParameter("YoB");				
		String Gender = request.getParameter("Gender");		
		String Remark = request.getParameter("Remark");	
		switch (type) {
		case "stu":
			String ErlYear = request.getParameter("ErlYear");
			Student objStu = new Student(ID,PWD,Name,Gender,YoB,ErlYear,Remark);
			response.getWriter().write( (stu.update(objStu)).toString());
			break;
		case "tch":
			String Title = request.getParameter("Title");
			Teacher objTch = new Teacher(ID,PWD,Name,Gender,YoB,Title,Remark);
			response.getWriter().write((tch.update(objTch)).toString());
			break;
		case "adm":
			Admin objAdm = new Admin(ID,PWD,Name,Gender,YoB,Remark);
			response.getWriter().write((adm.update(objAdm)).toString());
			break;
		default:
			break;
		}
		return state;
	}
	
	private void userFind(HttpServletRequest request, HttpServletResponse response, String type) throws ServletException, IOException {
		String ID = request.getParameter("ID");
		switch (type) {
		case "stu":
			Student stuObj = new Student();
			stuObj.setID(ID);
			response.getWriter().write( ((Student)stu.query(stuObj)).toString());
			break;
		case "tch":
			Teacher tchObj = new Teacher();
			tchObj.setID(ID);
			response.getWriter().write( (tch.query(tchObj)).toString());
			break;
		case "adm":
			Admin admObj = new Admin();
			admObj.setID(ID);
			response.getWriter().write( (adm.query(admObj)).toString());
			break;
		default:
			break;
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void destroy() {
		super.destroy();
		stu.close();
		tch.close();
		adm.close();
		cre.close();
	}
	
}
