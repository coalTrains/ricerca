package test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
// import org.apache.commons.lang3.*;

import org.apache.commons.lang3.StringUtils;

public class UserManager {

	public UserManager() {
	}
		
	DbManager mDbManager = new DbManager();
	Connection connection;
	// Aggiungi utente al Db
	public  UserBean addUser(UserBean pUserBeanUser) {
		
		connection =mDbManager.openConnection();
		
		try {
			PreparedStatement pStatement = connection.prepareStatement("insert into users (name,surname,date) values (?, ?, ?)");
			pStatement.setString(1, pUserBeanUser.getName());
			pStatement.setString(2, pUserBeanUser.getSurname());
			String date= pUserBeanUser.getDate();
			java.sql.Date sqlDate=  java.sql.Date.valueOf(date);
			pStatement.setDate(3, sqlDate);
			pStatement.executeUpdate();
			
			// Restituisci utente aggiunto
			String sql = "select * from users where name = ? and surname = ? and date = ?";
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,pUserBeanUser.getName());
			pStatement.setString(2,pUserBeanUser.getSurname());
			pStatement.setDate(3, sqlDate);
			ResultSet rs = pStatement.executeQuery();
			
			while (rs.next()) {
				pUserBeanUser.setId(rs.getInt("id"));
			}
			rs.close();
			
			pStatement.close();
			mDbManager.closeConn();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		} 
			return pUserBeanUser;
	}
	
	// Eliminazione da Db
	public UserBean deleteUser(int pIntId) {		
		UserBean mUserBeanUser=new UserBean();
		mUserBeanUser.setId(pIntId);
		connection =mDbManager.openConnection();
		
		try {
			//Restituisci utente eliminato
			String sql = "select * from users where id = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setInt(1,pIntId);
			ResultSet rs = pstatement.executeQuery();
			
			while (rs.next()) {
				mUserBeanUser.setName(rs.getString("name"));
				mUserBeanUser.setSurname(rs.getString("surname"));
				String date= (String) rs.getDate("date").toString();
				mUserBeanUser.setDate(date);
			}
			rs.close();
			
			// Elimina
			pstatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
			pstatement.setInt(1, pIntId);
			pstatement.executeUpdate();
			
			pstatement.close();
			mDbManager.closeConn();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		} 
		return mUserBeanUser;
	}
	
	// Modifica utente Db
	public UserBean updateUser(UserBean pUserBeanUser) {
		UserBean oldUser = new UserBean();
		connection =mDbManager.openConnection();
		
		try {
			int ID= pUserBeanUser.getId();
			//Restituisci vecchio utente
			oldUser.setId(ID);
			PreparedStatement pStatement = connection.prepareStatement("select * from users where id=?");
			pStatement.setInt(1,ID);
			ResultSet rs = pStatement.executeQuery();
			
			while (rs.next()) {
				oldUser.setName(rs.getString("name"));
				oldUser.setSurname(rs.getString("surname"));
				String date= (String) rs.getDate("date").toString();
				oldUser.setDate(date);
			}
			rs.close();
			
			//Modifica
			pStatement = connection.prepareStatement("update users set name=?, surname=?, date=?" + "where id=?");
			pStatement.setString(1, pUserBeanUser.getName());
			pStatement.setString(2, pUserBeanUser.getSurname());
			String date= pUserBeanUser.getDate();
			java.sql.Date sqlDate=  java.sql.Date.valueOf(date);
			pStatement.setDate(3, sqlDate);
			pStatement.setInt(4, ID);
			pStatement.executeUpdate();
			
			pStatement.close();
			mDbManager.closeConn();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		} 
		return oldUser;
	}
	
	// Lista completa, da eseguire in startup o da eliminare
	public List<UserBean> getAllUsers() {
		List<UserBean> users = new ArrayList<UserBean>();
		UserBean user= new UserBean();		
		users=getUsers(user);
		
		return users;
	}
	
	//Ricerca utente
	public List<UserBean> getUsers(UserBean pUserBeanUser) {
		
		boolean lBlnUseAnd = false;
		
		List<UserBean> mListusers = new ArrayList<UserBean>();
		String lStrNome = pUserBeanUser.getName();
		String lStrCognome = pUserBeanUser.getSurname();
		String lStrData = pUserBeanUser.getDate();
		connection =mDbManager.openConnection();
		
		try {
			StringBuilder mBldr = new StringBuilder("select * from users");
			String sql = mBldr.toString();
			PreparedStatement pStatement= connection.prepareStatement(sql);
			ResultSet rs;
			int i=1; 
			Boolean lBlnName=false;

			if(StringUtils.isNotBlank(lStrNome)){
				lBlnUseAnd = true;
				mBldr.append(" where name like ?");
				sql = mBldr.toString();
				pStatement = connection.prepareStatement(sql);
				pStatement.setString(1,"%" + lStrNome + "%");
				lBlnName= true;
				i++;
			}
			if(StringUtils.isNotBlank(lStrCognome)){
				if (lBlnUseAnd) {
					mBldr.append(" and");
					i=2;
				} else {
					mBldr.append(" where");
					lBlnUseAnd = true;
				}
				mBldr.append(" surname like ?");
				sql = mBldr.toString();
				pStatement = connection.prepareStatement(sql);
				if(lBlnName){  
					pStatement.setString(1,"%" + lStrNome + "%");
				}
				pStatement.setString(i,"%" + lStrCognome + "%");
				i++;
			}
			if(StringUtils.isNotBlank(lStrData)){
				if (lBlnUseAnd) {
					mBldr.append(" and");
				} else {
					mBldr.append(" where");
					lBlnUseAnd = true;
				}
				mBldr.append(" date = ?");
				sql = mBldr.toString();
				pStatement = connection.prepareStatement(sql);
				if(i==3){
					pStatement.setString(1,"%" + lStrNome + "%");
					pStatement.setString(2,"%" + lStrCognome + "%");
				} else if(i==2){
					if(lBlnName){
						pStatement.setString(1,"%" + lStrNome + "%");
					} else pStatement.setString(1,"%" + lStrCognome + "%");
				}
				java.sql.Date sqlDate=  java.sql.Date.valueOf(lStrData);
				pStatement.setDate(i, sqlDate);
			}
			rs = pStatement.executeQuery();			
			
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				String date= (String) rs.getDate("date").toString();
				user.setDate(date);
				
				mListusers.add(user);
			} 	
			mDbManager.closeConn(rs,pStatement,connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		} 
			return mListusers;	
	}
	
}
