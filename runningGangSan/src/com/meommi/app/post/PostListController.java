package com.meommi.app.post;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.post.dao.PostDAO;

//Execute 인터페이스를 구현하는 PostListController클래스 선언
public class PostListController implements Execute {

//	execute메서드 재정의
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		
//		데이터베이스의 data에 접근하기 위한 dao객체 생성
		PostDAO postDAO=new PostDAO();
		
//		Servlet으로 들어온 요청을 텍스트 형태로 응답하기 위해 출력스트림을 얻어야하므로 getWriter()메서드 사용[바이트를 문자 형태를 가지는 객체로 바꿔준다]
		PrintWriter out=resp.getWriter();
		
//		JSONArray타입의 객체 생성
		JSONArray jsonArray=new JSONArray();
		
//		데이터베이스에서 조회해온 결과값들을 JSONObject형태의 객체로 변환하고 JSONArray타입의 객체에 누적
		postDAO.selectPostDefault().forEach(posts->{
			JSONObject jsonObject =new JSONObject(posts);  jsonArray.put(jsonObject);
		});
		
//		스트림에 jsonArray 기록
		out.print(jsonArray.toString());
		
//		스트림의 내용을 flush메서드를 통해 요청한 client에게 전달해주고 사용된 자원을 반환
		out.close();
		System.out.println(req.getSession().getAttribute("userId"));
		System.out.println(req.getSession().getAttribute("userNumber"));
		
//		페이지 이동이 없으므로 null반환
		return null;
	}

}
