package com.meommi.app.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.post.dao.PostDAO;

//Execute 인터페이스를 구현하는 PostDeleteController클래스 선언
public class PostDeleteController implements Execute {

//	execute메서드 재정의
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		데이터베이스의 data에 접근하기 위한 dao객체 생성
		PostDAO postDAO=new PostDAO();
		
//		파라미터로 넘겨받은 postNumber값을 정수로 변환하여 저장하고, 게시글 삭제를 위해 변환 값을 deletePost메서드의 인자로 전달
		postDAO.deletePost(Integer.valueOf(req.getParameter("postNumber")));
		
//		경로 이동을 하지 않으므로 Result객체에 null값 반환
		return null;
	}

}
