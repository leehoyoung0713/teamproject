package com.meommi.app.post.dao;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meommi.app.post.vo.PostDTO;
import com.meommi.app.post.vo.PostVO;
import com.meommi.mybatis.config.MyBatisConfig;

//데이터베이스의 data에 접근하기 위한 dao클래스 선언
public class PostDAO {
	
//	초기 설정을 하며 db와 연결후 connection객체를 반환받는다.
      SqlSessionFactory sqlSessionFactory = MyBatisConfig.getSqlsessionFactory();
      SqlSession sqlSession;
      
//		기본 생성자 선언하며, sqlSessionFactory의 openSession메서드를 통해 auto commit옵션을 true로 설정
      public PostDAO() {
         sqlSession = sqlSessionFactory.openSession(true);
      }
      
//		List<PostDTO>타입의 selectList 메서드를 통해 페이지당 가져올 게시글 별 댓글수를 조회하여 반환하는 메서드
      public List<PostDTO> selectPostDefault() {
         return sqlSession.selectList("Post.selectPostDefault");
      }
      
//		List<PostDTO>타입의 selectList 메서드를 통해 스크롤 이벤트시 1개의 게시글에 해당하는 댓글수를 조회하여 반환하는 메서드
      public List<PostDTO> selectPostRow(int Outputnum) {
         return sqlSession.selectList("Post.selectPostRow",Outputnum);
      }
      
//		댓글 삽입을 위한 메서드
      public void insertContent(PostVO postVO) {
         sqlSession.insert("Post.insertContent", postVO);
      }
      
//		최근 추가된 게시글 번호를 찾기 위한 메서드
      public int selectCurrentSequence() {
          return sqlSession.selectOne("Post.selectCurrentSequence");
       }
      
//		댓글 수정을 위한 메서드
      public void updatePost(PostDTO postDTO) {
    	  sqlSession.update("Post.updatePost",postDTO);
      }
      
//	   댓글 삭제를 위한 메서드
      public void deletePost(int postNumber) {
    	  sqlSession.delete("Post.deletePost",postNumber);
      }
      
//	   팔로우하는 사람들의 게시글 조회를 위한 메서드
      public List<PostDTO> selectFollowerDefault(int userNumber){
    	  return sqlSession.selectList("Post.selectFollowerDefault",userNumber);
      }
}