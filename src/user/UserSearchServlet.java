package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userName = request.getParameter("userName");//요청에 담긴 파라미터값 userName 가져옴
		response.getWriter().write(getJSON(userName));
	}
	public String getJSON(String userName) {//index.jsp에서 요청한 결과를 불러올 때, JSON형태로 응답을 받는다.
		if(userName==null) userName="";
		StringBuffer result = new StringBuffer("");
		result.append("{\result\":[");
		UserDAO userDAO = new UserDAO();
		ArrayList<User> userList = userDAO.search(userName);
		for(int i=0; i<userList.size(); i++) {
			result.append("[{\"value\":\""+userList.get(i).getUserName()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserName()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserName()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserName()+"\"}],");
		}
		result.append("}]");
		return result.toString();
	}
}
