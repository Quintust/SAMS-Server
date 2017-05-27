package com.fighting.sams.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fighting.sams.model.Student;
import com.fighting.sams.model.Teacher;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;

public class DBTchService extends DatabaseHelper implements DatabaseOperation{
	
	@Override
	public STATE insert(Object obj) {
		Teacher tch = (Teacher) obj;
		String insert = "INSERT INTO "+TABLE_NAME.ttch+" VALUES('"
				+tch.getID()+"','"+tch.getPWD()+"','"+tch.getName() +"','"+tch.getGender()+"','"
				+tch.getYoB()+"','"+tch.getTitle()+"','"+tch.getRemark()+"')";
		int m = executeUpdate(insert);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public STATE delete(Object obj) {
		Teacher tch = (Teacher) obj;
		String delete = "DELETE FROM "+TABLE_NAME.ttch + " WHERE ID='"+tch.getID()+"'";
		int m = executeUpdate(delete);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public STATE update(Object obj) {
		Teacher tch = (Teacher) obj;
		String update = "UPDATE "+TABLE_NAME.ttch + " SET PWD='"+tch.getPWD()+"', Name='"+tch.getName()+"', Gender='"+tch.getGender()
					+"', YoB='"+tch.getYoB()+"', Title='"+tch.getTitle()+"', Remark='"+tch.getRemark()+"' where ID='"+tch.getID()+"'";
		int m = executeUpdate(update);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public Object query(Object obj) {
		Teacher tch = (Teacher) obj;
		String query = "SELECT * FROM "+TABLE_NAME.ttch+" WHERE ID='"+tch.getID()+"'";
		ResultSet m = this.query(query); 
		try {
			while(m.next()){
				tch.setID(m.getString("ID"));
				tch.setName(m.getString("Name"));
				tch.setGender(m.getString("Gender"));
				tch.setYoB(m.getString("YoB"));
				tch.setTitle(m.getString("Title"));
				tch.setRemark(m.getString("Remark"));
				return tch;
			}
		} catch (SQLException e) {
			System.out.println("操作失败"+e.toString());
			e.printStackTrace();
		}
		return STATE.FAILURE;
	}
	
	public STATE checkLogin(String ID, String PWD) {
		STATE state = STATE.FAILURE;
		String query = "SELECT * FROM "+TABLE_NAME.ttch+" WHERE ID='"+ID+"' and PWD='"+PWD+"'";
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
