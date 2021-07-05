package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*검색기능 실행해주는 컨트롤러*/
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userName = request.getParameter("userName");//요청에 담긴 파라미터값 userName 가져옴
		response.getWriter().write(getJSON(userName));
	}
	public String getJSON(String userName) {//index.jsp에서 요청한 결과를 불러올 때, JSON형태로 응답을 받는다.
		if(userName==null) userName="";
		StringBuffer result = new StringBuffer(""); 
		result.append("{\"result\":[");// 문자열 추가
		UserDAO userDAO = new UserDAO();
		// 검색할 userName을 매개변수로 받아 search메소드 실행 후 리턴값 저장
		ArrayList<User> userList = userDAO.search(userName);
		for(int i=0; i<userList.size(); i++) { // 검색에 결과에 해당되는 userList의 길이만큼 반복
			// 해당 user정보를 result에 추가
			result.append("[{\"value\":\""+userList.get(i).getUserName()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserAge()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserGender()+"\"},");
			result.append("{\"value\":\""+userList.get(i).getUserEmail()+"\"}],");
		}
		/* JSON 값 
		 {"result":[
					[{"value":"이시은"},
					 {"value":"25"},
					 {"value":"여"},
					 {"value":"user@naver.com"}
					],
					[{"value":"홍길동"},
					 {"value":"30"},
					 {"value":"남"},
					 {"value":"hong@naver.com"}
					],
			 	   ]
		 }
		 */
		result.append("]}");
		return result.toString(); //문자열 반환
	}
}
