package com.fighting.sams.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fighting.sams.model.Credit;
import com.fighting.sams.utils.STATE;
import com.fighting.sams.utils.TABLE_NAME;

public class DBCreService extends DatabaseHelper implements DatabaseOperation {

	@Override
	public STATE insert(Object obj) {
		Credit credit = (Credit) obj;
		String insert = "INSERT INTO "+TABLE_NAME.tcre+" VALUES('"+credit.getID()+"',"+credit.getCredit()+",'"+credit.getRemark()+"')";
		int m = executeUpdate(insert);
		if(m>0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	@Override
	public STATE delete(Object obj) {
		Credit credit = (Credit) obj;
		String delete = "DELETE FROM "+TABLE_NAME.tcre +" WHERE ID='"+credit.getID()+"'";
		int m = executeUpdate(delete);
		if(m>0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	@Override
	public STATE update(Object obj) {
		Credit credit = (Credit) obj;
		String update = "update "+TABLE_NAME.tcre+" set Credit=Credit+1 WHERE ID='"+credit.getID()+"'";
		int m = executeUpdate(update);
		if(m>0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	@Override
	public Object query(Object obj) {
		Credit credit = (Credit) obj;
		String query = "select * from "+TABLE_NAME.tcre+" where ID='"+credit.getID()+"'";
		ResultSet rs = query(query);
		try {
			while(rs.next()){
				credit.setID(rs.getString("ID"));
				credit.setCredit(rs.getInt("Credit"));
				credit.setRemark(rs.getString("Remark"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return credit;
	}

}
