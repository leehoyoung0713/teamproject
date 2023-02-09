package com.meommi.app.postFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.meommi.app.Execute;
import com.meommi.app.Result;
import com.meommi.app.follow.dao.FollowDAO;
import com.meommi.app.postFile.dao.PostFileDAO;
import com.meommi.app.postFile.vo.PostFileVO;

//Execute 인터페이스를 구현하는 PostFileListFollowingController클래스 선언
public class PostFileListFollowingController implements Execute {

	//	execute메서드 재정의
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		
//		데이터베이스의 data에 접근하기 위한 List<PostFileVO>타입의 객체 및 dao객체들 생성
		FollowDAO followingDAO=new FollowDAO();
		PostFileDAO postFileDAO=new PostFileDAO();
		List<PostFileVO> followingfiles=new ArrayList<>();
		
//		초기값 설정
		int followerNumber=3;
		
//		Servlet으로 들어온 요청을 텍스트 형태로 응답하기 위해 출력스트림을 얻어야하므로 getWriter()메서드 사용[바이트를 문자 형태를 가지는 객체로 바꿔준다]
		PrintWriter out =resp.getWriter();
		
//		JSONArray타입의 객체 생성
		JSONArray jsonArray=new JSONArray();
		
//		유저 팔로잉 사람들의 목록을 조회해서 List<PostFileVO>타입의 객체에 저장
		followingDAO.selectFollowing(followerNumber).forEach(followingNumber->{
			
			followingfiles.addAll(postFileDAO.selectFollowerPostfileDefault(followingNumber.getFollowingNumber()));
			/* postDAO.selectFollowerDefault(followerNumber.getFollowerNumber()); */
		});

//		followingfiles객체에서 저장된 데이터를 JSONArray타입으로 변환하기 위한 작업
		followingfiles.forEach(followingfile->{
			JSONObject jsonObject=new JSONObject(followingfile); jsonArray.put(jsonObject);
		});

//		스트림에 jsonArray 기록
		out.print(jsonArray.toString());
		
//		스트림의 내용을 flush메서드를 통해 요청한 client에게 전달해주고 사용된 자원을 반환
		out.close();
		
//		페이지 이동이 없으므로 null반환
		return null;
	}

}
