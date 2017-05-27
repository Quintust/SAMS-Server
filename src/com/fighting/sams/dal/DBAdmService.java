package com.fighting.sams.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fighting.sams.model.Admin;
import com.fighting.sams.model.Student;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;

public class DBAdmService extends DatabaseHelper implements DatabaseOperation {

	@Override
	public STATE insert(Object obj) {
		Admin adm = (Admin) obj;
		String insert = "INSERT INTO "+TABLE_NAME.tadm+" VALUES('"
				+adm.getID()+"','"+adm.getPWD()+"','"+adm.getName() +"','"+adm.getGender()+"','"
				+adm.getYoB()+"','"+adm.getRemark()+"')";
		int m = executeUpdate(insert);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public STATE delete(Object obj) {
		Admin adm = (Admin) obj;
		String delete = "DELETE FROM "+TABLE_NAME.tadm + " WHERE ID='"+adm.getID()+"'";
		int m = executeUpdate(delete);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public STATE update(Object obj) {
		Admin adm = (Admin) obj;
		String update = "UPDATE "+TABLE_NAME.tadm + " SET PWD='"+adm.getPWD()+"', Name='"+adm.getName()+"', Gender='"+adm.getGender()
					+"', YoB='"+adm.getYoB()+"', Remark='"+adm.getRemark()+"' where ID='"+adm.getID()+"'";
		int m = executeUpdate(update);
		if(m>0){
			return STATE.SUCCESS;
		}
		return STATE.FAILURE;
	}
	
	@Override
	public Object query(Object obj) {
		Admin adm = (Admin) obj;
		String query = "SELECT * FROM "+TABLE_NAME.tadm+" WHERE ID='"+adm.getID()+"'";
		ResultSet m = query(query);
		try {
			while(m.next()){
				adm.setID(m.getString("ID"));
				adm.setName(m.getString("Name"));
				adm.setGender(m.getString("Gender"));
				adm.setYoB(m.getString("YoB"));
				adm.setRemark(m.getString("Remark"));
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
		return adm;
	}
	
	public STATE checkLogin(String ID, String PWD) {
		STATE state = STATE.FAILURE;
		String query = "SELECT * FROM "+TABLE_NAME.tadm+" WHERE ID='"+ID+"' and PWD='"+PWD+"'";
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
