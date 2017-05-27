package com.fighting.sams.dal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fighting.sams.model.CheckInfo;
import com.fighting.sams.model.Course;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;
import com.google.gson.Gson;

public class DBCusService extends DatabaseHelper implements DatabaseOperation {
	@Override
	public STATE insert(Object obj) {
		Course cus = (Course) obj;
		String insert = "INSERT INTO " + TABLE_NAME.tcus + " VALUES(" + cus.getID() + ",'"
				+ cus.getName() + "','" + cus.getTchID() + "'," + cus.getChkinFlg() + ",'" + cus.getRemark() + "')";
		int m = executeUpdate(insert);
		if (m > 0) {
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}

	@Override
	public STATE delete(Object obj) {
		Course cus = (Course) obj;
		String delete = "DELETE FROM " + TABLE_NAME.tcus + " WHERE ID='" + cus.getID() + "'";
		int m = executeUpdate(delete);
		if (m > 0) {
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}

	@Override
	public STATE update(Object obj) {
		Course cus = (Course) obj;
		String update = "UPDATE " + TABLE_NAME.tcus + " SET Name='" + cus.getName()
				+ "', TchID='" + cus.getTchID() + "', ChkinFlg='" + cus.getChkinFlg() + "', Remark='" + cus.getRemark()
				+ "' where ID='" + cus.getID() + "'";
		int m = executeUpdate(update);
		if (m > 0) {
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}

	@Override
	public Object query(Object obj) {
		Course cus = (Course) obj;
		String query = "SELECT * FROM " + TABLE_NAME.tcus + " WHERE ID='" + cus.getID() + "'";
		ResultSet m = query(query);
		try {
			while (m.next()) {
				cus.setID(m.getInt("ID"));
				cus.setName(m.getString("Name"));
				cus.setTchID(m.getString("TchID"));
				cus.setChkinFlg(m.getInt("ChkinFlg"));
				cus.setRemark(m.getString("Remark"));
				break;
			}
		} catch (SQLException e) {
			System.out.println("数据库报错" + e.toString());
			e.printStackTrace();
		} finally {
			try {
				m.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cus.toString();
	}
	
	public Object queryChkin(String stuID, int CusID) {
		List<CheckInfo> list = new ArrayList<CheckInfo>();
		CheckInfo chkinfo = null;
		String query = "SELECT * FROM " + TABLE_NAME.tchkin + " WHERE StuID='"+stuID+"' AND CusID="+CusID;
		ResultSet m = query(query);
		try {
			while (m.next()) {
				chkinfo = new CheckInfo();
				chkinfo.setCusID(m.getInt("CusID"));
				chkinfo.setStuID(m.getString("StuID"));
				chkinfo.setChkinTime(m.getString("ChkinTime"));
				chkinfo.setRow(m.getInt("Row"));
				chkinfo.setClm(m.getInt("Clm"));
				chkinfo.setChkinState(m.getString("ChkinState"));
				chkinfo.setRemark(m.getString("Remark"));
				list.add(chkinfo);
			}
		} catch (SQLException e) {
			System.out.println("数据库报错" + e.toString());
			e.printStackTrace();
		} finally {
			try {
				m.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	public STATE selCus(int CusID,String StuID) {
		String insert = "INSERT INTO " + TABLE_NAME.tcusstu + " VALUES(0,"+CusID+",'"+StuID+"','"+"nothing"+"')";
		int m = executeUpdate(insert);
		if(m>0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
	public Object selectedCus(String StuID) {
		List<Course> list = new ArrayList<Course>();
		Course cus = null;
		String query =  "SELECT * FROM " + TABLE_NAME.tcus + " WHERE ID IN (SELECT CusID FROM "+TABLE_NAME.tcusstu+" WHERE StuID='"+StuID+"')";
		ResultSet rs = query(query);
		try {
			while(rs.next()){
				cus = new Course();
				cus.setID(rs.getInt("ID"));
				cus.setName(rs.getString("Name"));
				cus.setTchID(rs.getString("TchID"));
				cus.setChkinFlg(rs.getInt("ChkinFlg"));
				cus.setRemark(rs.getString("Remark"));
				list.add(cus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	public STATE chkinCus(Object obj) {
		CheckInfo chkinfo = (CheckInfo) obj;
		String insert = "INSERT INTO " + TABLE_NAME.tchkin + " VALUES(0,"+chkinfo.getCusID()+",'"+chkinfo.getStuID()+"','"
					+chkinfo.getChkinTime()+"',"+chkinfo.getRow()+","+chkinfo.getClm()+",'"+chkinfo.getChkinState()
					+"','"+chkinfo.getRemark()+"')";
		int m = executeUpdate(insert);
		if(m>0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
}
