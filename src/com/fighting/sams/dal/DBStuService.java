package com.fighting.sams.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fighting.sams.model.Credit;
import com.fighting.sams.model.Student;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;

public class DBStuService extends DatabaseHelper implements DatabaseOperation{
	
	@Override
	public STATE insert(Object obj) {
		Student stu = (Student) obj;
		String insert = "INSERT INTO "+TABLE_NAME.tstu+" VALUES('"
				+stu.getID()+"','"+stu.getPWD()+"','"+stu.getName() +"','"+stu.getGender()+"','"
				+stu.getYoB()+"','"+stu.getErlYear()+"','"+stu.getRemark()+"')";
		int m = executeUpdate(insert);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public STATE delete(Object obj) {
		Student stu = (Student) obj;
		String delete = "DELETE FROM "+TABLE_NAME.tstu + " WHERE ID='"+stu.getID()+"'";
		int m = executeUpdate(delete);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public STATE update(Object obj) {
		Student stu = (Student) obj;
		String update = "UPDATE "+TABLE_NAME.tstu + " SET PWD='"+stu.getPWD()+"', Name='"+stu.getName()+"', Gender='"+stu.getGender()
					+"', YoB='"+stu.getYoB()+"', ErlYear='"+stu.getErlYear()+"', Remark='"+stu.getRemark()+"' where ID='"+stu.getID()+"'";
		int m = executeUpdate(update);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public Object query(Object obj) {
		Student stu = (Student) obj;
		String query = "SELECT * FROM "+TABLE_NAME.tstu+" WHERE ID='"+stu.getID()+"'";
		ResultSet m = query(query);
		try {
			while(m.next()){
				stu.setID(m.getString("ID"));
				stu.setName(m.getString("Name"));
				stu.setGender(m.getString("Gender"));
				stu.setYoB(m.getString("YoB"));
				stu.setErlYear(m.getString("ErlYear"));
				stu.setRemark(m.getString("Remark"));
				break;
			}
		} catch (SQLException e) {
			System.out.println("数据库报错"+e.toString());
			e.printStackTrace();
		}finally{
			try {
				m.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stu;
	}
	
	public STATE checkLogin(String ID, String PWD) {
		STATE state = STATE.FAILURE;
		String query = "SELECT * FROM "+TABLE_NAME.tstu+" WHERE ID='"+ID+"' and PWD='"+PWD+"'";
		ResultSet rs = query(query);
		try {
			while(rs.next()){
				state = STATE.SUCCESS;
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return state;
	}

}
