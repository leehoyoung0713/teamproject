package com.meommi.app.post;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.post.dao.PostDAO;
import com.meommi.app.post.vo.PostDTO;

//Execute 인터페이스를 구현하는 PostRetouchController클래스 선언
public class PostRetouchController implements Execute {

	//	execute메서드 재정의
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		데이터베이스의 data에 접근하기 위한 dto 및 dao객체들 생성
		PostDAO postDAO=new PostDAO();
		PostDTO postDTO=new PostDTO();
		
//		게시글 수정 후 수정시간을 저장하기 위한 설정
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		String postUpdateTime=simpleDateFormat.format(now);
		
//		파라미터로 넘겨받은 postContent값을 문자열로 변환하여 저장
		String postContent=req.getParameter("postContent");
		
//		파라미터로 넘겨받은 postNumber값을 정수로 변환하여 저장
		int postNumber=Integer.valueOf(req.getParameter("postNumber"));

//		게시글 수정을 하기 위해 필요한 정보들을 인자로 전달
		postDTO.setPostContent(postContent);
		postDTO.setPostDateTime(postUpdateTime);
		postDTO.setPostNumber(postNumber);
		postDAO.updatePost(postDTO);
		
//		페이지 이동이 없으므로 null값 반환
		return null;
	}

}
