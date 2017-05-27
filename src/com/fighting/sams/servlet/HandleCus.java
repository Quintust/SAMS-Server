package com.fighting.sams.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fighting.sams.dal.DBAdmService;
import com.fighting.sams.dal.DBCusService;
import com.fighting.sams.dal.DBStuService;
import com.fighting.sams.dal.DBTchService;
import com.fighting.sams.dal.DatabaseHelper;
import com.fighting.sams.model.Admin;
import com.fighting.sams.model.CheckInfo;
import com.fighting.sams.model.Course;
import com.fighting.sams.model.Student;
import com.fighting.sams.model.Teacher;
import com.fighting.sams.model.User;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;
import com.fighting.sams.utils.http.HttpUtil;
import com.google.gson.Gson;

public class HandleCus extends HttpServlet {
	private DBCusService cus;

	@Override
	public void init(ServletConfig config) throws ServletException {
		cus = new DBCusService();
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String type = request.getParameter("type");
		operatCus(request, response, type);
	}

	private void operatCus(HttpServletRequest request, HttpServletResponse response, String type)
			throws ServletException, IOException {
		int ID =Integer.parseInt(request.getParameter("ID"));
		Course objCus;
		String remark = HttpUtil.getIpAddress(request);
		String StuID="";
		int CusID;
		switch (type) {
		case "add":
			objCus = new Course(ID,request.getParameter("Name"),request.getParameter("tchID"),
					Integer.parseInt(request.getParameter("chkinFlg")),request.getParameter("remark"));
			response.getWriter().write( cus.insert(objCus).toString());
			break;
			
		case "query":
			objCus = new Course(ID,"","",0,"");
			response.getWriter().write( cus.query(objCus).toString());
			break;
			
		case "update":
			objCus = new Course(ID,request.getParameter("Name"),request.getParameter("tchID"),
					Integer.parseInt(request.getParameter("chkinFlg")),request.getParameter("remark"));
			response.getWriter().write( cus.update(objCus).toString());
			break;
			
		case "delete":
			objCus = new Course(ID,"","",0,"");
			response.getWriter().write( cus.delete(objCus).toString());
			break;
			
		case "chkinlog":
			StuID = request.getParameter("StuID");
			CusID = Integer.parseInt(request.getParameter("cusID"));
			response.getWriter().write( cus.queryChkin(StuID,CusID).toString());
			break;
			
		case "chkin":
			Calendar date = Calendar.getInstance();
			String mytime = date.get(Calendar.YEAR) + "年" + (date.get(Calendar.MONTH) + 1) + "月"
					+ date.get(Calendar.DAY_OF_MONTH) + "日" + date.get(Calendar.HOUR_OF_DAY) + ":"
					+ date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);
			CheckInfo objChkin = new CheckInfo(Integer.parseInt(request.getParameter("CusID")),request.getParameter("StuID"),
					mytime,Integer.parseInt(request.getParameter("Row")),Integer.parseInt(request.getParameter("Clm")),
					request.getParameter("chkinState"),remark);
			response.getWriter().write(cus.chkinCus(objChkin).toString());
			break;
			
		case "sel":
			CusID = Integer.parseInt(request.getParameter("CusID"));
			StuID = request.getParameter("StuID");
			response.getWriter().write( cus.selCus(CusID, StuID).toString());
			break;
			
		case "selected":
			StuID = request.getParameter("StuID");
			response.getWriter().write( cus.selectedCus(StuID).toString());
			break;
		default:
			break;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void destroy() {
		cus.close();
		super.destroy();
	}

}
