package test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/ServletUser")

public class ServletUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	 public	void init() {
		 
	 }	
	
	public ServletUser() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse) throws ServletException, IOException {
		
		UserBean lUserBeanUser = new UserBean();
		UserManager mUserManager= new UserManager();
		String mStrAction="";
		mStrAction = pRequest.getParameter("action");
		
		// Ricerca iniziale
//		if (mStrAction.equals("")){
//			pRequest.setAttribute("users", mDbManager.getAllUsers());
//			
//			RequestDispatcher lDispatcher = pRequest.getRequestDispatcher("/WEB-INF/index.jsp");
//            lDispatcher.forward(pRequest,pResponse);
//		}
		
		// Ricerca	
		if (mStrAction.equalsIgnoreCase("search")) {
			mUserManager = new UserManager();
			lUserBeanUser.setName(pRequest.getParameter("nome").trim());
			lUserBeanUser.setSurname(pRequest.getParameter("cognome").trim());  
			lUserBeanUser.setDate(pRequest.getParameter("data").trim());
			 
			pRequest.setAttribute("pUsers", mUserManager.getUsers(lUserBeanUser));	  
			RequestDispatcher lRequestDispatcher = pRequest.getRequestDispatcher("/WEB-INF/index.jsp");
			lRequestDispatcher.forward(pRequest, pResponse);
			
		// Link  pag addUser	
		} else if(mStrAction.equalsIgnoreCase("addUser")){
			mUserManager = new UserManager();
			ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/addUser.jsp");
            dispatcher.forward(pRequest,pResponse);
            
        // Link  pag modUser	
	    } else if(mStrAction.equalsIgnoreCase("modUser")){
	    	mUserManager = new UserManager();
	    	ServletContext context = getServletContext();
	    	RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/modUser.jsp");
	    	dispatcher.forward(pRequest,pResponse);
	    }	


	}
	
	@Override
	protected void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse) throws ServletException, IOException {
	
		UserBean mUserBean = new UserBean();
		UserManager mUserManager= new UserManager();
		String mStrAction;
		int mIntId=0;
		mStrAction = pRequest.getParameter("action");
		
		// Elimina 
		if (mStrAction.equalsIgnoreCase("delete")) {
			mUserManager = new UserManager();
			mIntId = Integer.parseInt(pRequest.getParameter("id"));
			pRequest.setAttribute("pResp", "Id utente eliminato: ");
			pRequest.setAttribute("pUsr", mUserManager.deleteUser(mIntId));
			pRequest.setAttribute("pUsers", mUserManager.getAllUsers());
			
			RequestDispatcher lDispatcher = pRequest.getRequestDispatcher("/WEB-INF/index.jsp");
            lDispatcher.forward(pRequest,pResponse);
			
		// Aggiungi	
		} else if(mStrAction.equalsIgnoreCase("add")){   
				mUserManager=new UserManager();
				mUserBean.setName(pRequest.getParameter("nome"));
				mUserBean.setSurname(pRequest.getParameter("cognome"));
				mUserBean.setDate(pRequest.getParameter("data"));
				
				pRequest.setAttribute("pUsr", mUserManager.addUser(mUserBean));
				pRequest.setAttribute("pResp", "Id utente aggiunto: ");
				pRequest.setAttribute("pUsers", mUserManager.getAllUsers());
				 
				RequestDispatcher lRd = pRequest.getRequestDispatcher("/WEB-INF/index.jsp");
				lRd.forward(pRequest, pResponse);
				
		// Modifica
		}else if (mStrAction.equalsIgnoreCase("update")) {
			mUserManager = new UserManager();
			mUserBean.setId(Integer.parseInt(pRequest.getParameter("id")));
			mUserBean.setName(pRequest.getParameter("name"));
			mUserBean.setSurname(pRequest.getParameter("surname"));
			mUserBean.setDate(pRequest.getParameter("date"));
			
			pRequest.setAttribute("pUsr", mUserBean);
			mUserBean= mUserManager.updateUser(mUserBean);
			pRequest.setAttribute("pResp", "ID utente modificato: ");
			pRequest.setAttribute("pUsers", mUserManager.getAllUsers());
			pRequest.setAttribute("newData", "Valori precedenti: Nome= "
					+mUserBean.getName()+";  Cognome= "+mUserBean.getSurname()+";  Data di nascita= "+mUserBean.getDate());
			
			RequestDispatcher lRd = pRequest.getRequestDispatcher("/WEB-INF/index.jsp");
			lRd.forward(pRequest, pResponse);
		}
		
	}
	
}
