package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import view.Test;
import vo.BasketVO;
import vo.BungaeVO;
import vo.DanggeunVO;
import vo.JoonggoVO;
import vo.TotalDTO;

public class TotalDAO {

	public Connection connection; // 연결 객체
	public PreparedStatement preparedStatement; // 쿼리(SQL문) 객체
	public ResultSet resultSet; // 결과 테이블 객체
	Statement stmt = null;
	
	public List<JoonggoVO> joonggoList = new ArrayList<JoonggoVO>();
	
//  검색 결과 저장하기_중고나라
//	중고나라 정보를 담을 joonggoList에 전체 정보 추가
  public void joonggoResult(JoonggoVO joonggoVO) {
     Test.joonggoList.add(joonggoVO);
  }
  
//	검색 결과 저장하기_당근
//	당근마켓 정보를 담을 danggeunList에 전체 정보 추가
	public void danggeunResult(DanggeunVO danggeunVO) {
		Test.danggeunList.add(danggeunVO);
	}

//	검색 결과 저장하기_번개
//	번개장터 정보를 담을 bungaeList에 전체 정보 추가
	public void bungaeResult(BungaeVO bungaeVO) {
		Test.bungaeList.add(bungaeVO);
	}
  
//	검색 결과 저장하기_통합
//	3개 사이트의 정보를 담을 totalList에 전체 정보 추가	
	public void totalResult(TotalDTO totalDTO) {
		Test.totalList.add(totalDTO);
}

	       
//	       --------------------------------------------------------------------------------

// 출력된 전체 상품 정보 중 내가 고른 상품의 정보를 출력해주는 메소드
    public TotalDTO choiceItem(int productNum)   {
    	TotalDTO item = null;
    	
//    	TotalDTO 타입의 item 변수에 totalList에 있는 제품의 정보를 저장한다.
		item = Test.totalList.get(productNum);
		System.out.println(item);
    	
    	return item;
    }

	
	
	
//	       쿼리 시작 전 테이블 초기화 메소드
//	       public void refresh() {
//	   		String dropQuery = "DROP TABLE TBL_TOTAL";
//			String dropSequence= "DROP SEQUENCE TOTALRESULT_SEQ";
//
//			String createQuery = "CREATE TABLE TBL_TOTAL "
//					+ "(TOTAL_PRODUCTNUMBER NUMBER CONSTRAINT PK_TOTAL PRIMARY KEY, "
//					+ "	TOTAL_COMPANYNAME VARCHAR2(1000), "
//					+ "	TOTAL_NAME VARCHAR2(1000), "
//					+ "	TOTAL_PRICE NUMBER, "
//					+ "	TOTAL_ADDRESS VARCHAR2(1000), "
//					+ "	TOTAL_URL VARCHAR2(1000) "
//					+ ")";
//			
//			String createSequence = "CREATE SEQUENCE TOTALRESULT_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 9999 NOCYCLE NOCACHE NOORDER";
//			
//			try {
//				connection = DBConnector.getConnection();
//				preparedStatement = connection.prepareStatement(dropQuery);
//				preparedStatement.executeUpdate(); // 여기까지 맞음
//				
//				stmt = connection.createStatement();
//				boolean b = stmt.execute(createQuery); //테이블 만들기
//				preparedStatement = connection.prepareStatement(dropSequence);
//				preparedStatement.executeUpdate();
//				preparedStatement = connection.prepareStatement(createSequence);
//				preparedStatement.executeUpdate();
//			} catch (SQLException e) {
//				System.out.println(e);
//				System.out.println("totalResult에서 쿼리문 오류");
//			} finally {
//				try {
//					if (preparedStatement != null) {
//						preparedStatement.close();
//					}
//					if (connection != null) {
//						connection.close();
//					}
//					if(stmt !=null) {
//						stmt.close();
//					}
//				} catch (SQLException e) {
//					throw new RuntimeException(e.getMessage());
//				}
//			}
//	      }	
	       
	       
	       

//		가격 String 형태를 Integer로 변경해주기 위해 콤마를 지워준다.
		public String deleteComma(String data) {
			String result = "";
			for (int i = 0; i < data.length(); i++) {
//				문자열을 하나씩 가져와 콤마 또는 '원'이 있다면 result에 담지 않고 continue를 진행한다.
				if(data.charAt(i) == ',' || data.charAt(i) == '원'){
					continue;
				}
//				continue가 되지 않았을 경우 result에 추가한다.
				result = result + data.charAt(i);
			}
			return result;
		}
		
		
//		카테고리(대분류)를 선택하면 그에 맞는 소분류 항목들을 출력하는 메소드
		public String getCategory(int categoryNum) {
//			TBL_CATEGORY 테이블에서 번호에 부합하는 세부 카테고리 항목 출력
			String query="SELECT CATEGORY_SEMICATEGORY FROM TBL_CATEGORY "
					+ "WHERE CATEGORY_NUMBER=?";
			String categories=null;
			
			try {
				connection=DBConnector.getConnection();
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1, categoryNum);
				resultSet=preparedStatement.executeQuery();
				
//				결과값이 있다면 해당 값을 가져온다. 
				if(resultSet.next()) {
					categories=resultSet.getString(1);
				}
			} catch (SQLException e) {
				System.out.println("getCategory() 오류");
				e.printStackTrace();
			} finally {
				try {
					if(resultSet!=null) {resultSet.close();}
					if(preparedStatement!=null) {preparedStatement.close();}
					if(connection!=null) {connection.close();}
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			return categories;
		}
		
		
//		조회수 순 검색
	      public void arrangeView() {
	    	  // 조회수만 views에 배열로 담아서 정렬
	    	  ArrayList<Integer> views = new ArrayList<Integer>();
//	    	  totalList에 담긴 전체 정보를 조회수를 기준으로 내림차순 정렬해 views ArrayList에 저장한다.
	    	  Test.totalList.stream().map(v-> v.getView()).sorted(Collections.reverseOrder()).forEach(views::add);
//	    	  중복된 조회수는 제거하기 위해, HashSet을 이용한다.
//	    	  만약 중복 조회수를 제거하지 않는다면, 같은 항목이 중복된 조회수만큼 출력이 되기 때문에 조회수 비교 시 한 개씩만 ArrayList에 넣어야 한다.
	    	  HashSet<Integer> duplicateViews = new HashSet<>(views);
	    	  
//	    	  views에 중복을 제거한 HashSet 리스트를 담아준다.
	    	  views = new ArrayList<>(duplicateViews);
//	    	  views에 저장된 조회수를 내림차순으로 바꾸고, integer 타입 ArrayList로 형변환하여 views에 다시 담아준다.
//	    	  views에는 조회수가 높은 순서대로 ArrayList에 담기게 된다.
	    	  views = (ArrayList<Integer>) views.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
	       
	       // 조회수가 같은 totalList의 i 번째를 순서대로 저장
	       for (Integer item : views) {  
	          for (TotalDTO data : Test.totalList) { 
	             if(data.getView() == item) {
	                Test.setTotalList.add(data);
	             }
	          }
	       }       
	     }
	      
	      
//	     장바구니 중복선택 막는 메소드
//	     중복되면 false임
	     public boolean checkName(String name, String userId) {
	        Test test = new Test();
//	        showBasket메소드를 통해 내 아이디의 바스켓 정보를 basketList에 저장
	        showBasket(userId);
	        for (BasketVO basket : Test.basketList) {
//	        	내가 장바구니에 담으려는 항목의 제목과, 이미 장바구니에 있는 상품의 제목이 같다면, false를 리턴한다.
	         if(basket.getItemName().equals(name)) {return false;}
	      }
//	        장바구니에 같은 제목이 없다면 true를 리턴한다.
	        return true;
	     }
	        
//   		선택한 상품을 장바구니에 저장하는 메소드
	          public void insertBasket(int productNum, String userId)   {
//	        	선택한 상품을 장바구니에 저장하기 때문에, 선택한 상품은 반드시 한개이며, 그렇다면 list의 0번째 항목만 가져올 수 있기 때문에 get(0)이 된다.
//	        	내가 선택한 상품의 전체 정보를 TotalDTO 타입인 item에 담는다.
	            TotalDTO item = Test.setTotalList.get(0);
	            
//	            TBL_BASKET 테이블에 선택한 상품의 정보를 저장한다.
	            String insertQuery = "INSERT INTO TBL_BASKET "
	                   + "(ITEM_NUMBER, USERS_ID, ITEM_NAME, ITEM_PRICE, ITEM_ADDRESS, ITEM_URL, ITEM_VIEW) "
	                   + "VALUES(BASKET_SEQ.NEXTVAL,?, ?, ?, ?, ?, ?)";       
	            int i = 0;
	             
	                try {
	               connection = DBConnector.getConnection();
            
	               preparedStatement = connection.prepareStatement(insertQuery);
	               
	               preparedStatement.setString(++i, userId);
	               preparedStatement.setString(++i, item.getName());
	               preparedStatement.setInt(++i, item.getPrice());
	               preparedStatement.setString(++i, item.getAddress());
	               preparedStatement.setString(++i, item.getUrl());
	               preparedStatement.setInt(++i, item.getView());
	               
	               preparedStatement.executeUpdate();
	               System.out.println("장바구니 담기 성공");
	               
	            } catch (SQLException e) {
	               e.printStackTrace();
	               System.out.println("insertBasket에서 쿼리문 오류");
	            } finally {
	               try {
	                  if (preparedStatement != null) {
	                     preparedStatement.close();
	                  }
	                  if (connection != null) {
	                     connection.close();
	                  }
	               } catch (SQLException e) {
	                  throw new RuntimeException(e.getMessage());
	               }
	            }
	          }
	          
//	          장바구니 출력 메소드	          
	          public void showBasket(String userId) {
	             int i =0;
//	             장바구니 리스트 초기화
	             Test.basketList = new ArrayList<BasketVO>();
//	             내가 찾는 아이디의 TBL_BASKET 정보를 basketList에 저장
	             String query = "SELECT USERS_ID, ITEM_NAME, ITEM_PRICE, ITEM_ADDRESS, ITEM_URL, ITEM_VIEW "
	                            + "FROM TBL_BASKET WHERE USERS_ID = ?";
	             try {
	               connection = DBConnector.getConnection();
	               preparedStatement = connection.prepareStatement(query);
	               preparedStatement.setString(1, userId);
	               resultSet = preparedStatement.executeQuery();

	               while(resultSet.next()) {
	                  BasketVO basketVO = new BasketVO();
	                  basketVO.setUserId(resultSet.getString(1));
	                  basketVO.setItemName(resultSet.getString(2));
	                  basketVO.setItemPrice(Integer.valueOf(resultSet.getString(3)));
	                  basketVO.setItemAddress(resultSet.getString(4));
	                  basketVO.setItemUrl(resultSet.getString(5));
	                  basketVO.setItemView(resultSet.getInt(6));
	                  Test.basketList.add(basketVO);
	               }
	               
	         } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("showBasket에서 쿼리문 오류");
	         }  finally {
	            try {
	               if (preparedStatement != null) {
	                  preparedStatement.close();
	               }
	               if (connection != null) {
	                  connection.close();
	               }
	            } catch (SQLException e) {
	               throw new RuntimeException(e.getMessage());
	            }
	         }
	            
	          }
	          
	          
	          
	          
//	          쿼리 시작 전 테이블 초기화 메소드
	          public void refresh() {
	            String dropQuery = "DROP TABLE TBL_BASKET";
	            String dropSequence= "DROP SEQUENCE BASKET_SEQ";

	            String createQuery = "   CREATE TABLE TBL_BASKET("
	               + " ITEM_NUMBER NUMBER CONSTRAINT PK_BASKET PRIMARY KEY,"
	               + " USERS_ID VARCHAR2(1000),"
	               + " ITEM_NAME VARCHAR2(1000),"
	               + " ITEM_PRICE NUMBER,"
	               + " ITEM_ADDRESS VARCHAR2(1000),"
	               + " ITEM_URL VARCHAR2(1000),"
	               + " ITEM_VIEW VARCHAR2(1000), "
	               + " CONSTRAINT FK_BASKET FOREIGN KEY(USERS_ID) REFERENCES TBL_USERS(USER_ID) )";
	            
	         
	            String createSequence = "CREATE SEQUENCE BASKET_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 9999 NOCYCLE NOCACHE NOORDER";
	         
	            try {
		            connection = DBConnector.getConnection();
		            preparedStatement = connection.prepareStatement(dropQuery);
		            preparedStatement.executeUpdate(); // 여기까지 맞음
		            
		            stmt = connection.createStatement();
		            boolean b = stmt.execute(createQuery); //테이블 만들기
		            preparedStatement = connection.prepareStatement(dropSequence);
		            preparedStatement.executeUpdate();
		            preparedStatement = connection.prepareStatement(createSequence);
		            preparedStatement.executeUpdate();
		         } catch (SQLException e) {
		            System.out.println(e);
		            System.out.println("totalResult에서 쿼리문 오류");
		         } finally {
		            try {
		               if (preparedStatement != null) {
		                  preparedStatement.close();
		               }
		               if (connection != null) {
		                  connection.close();
		               }
		               if(stmt !=null) {
		                  stmt.close();
		               }
		            } catch (SQLException e) {
		               throw new RuntimeException(e.getMessage());
		            }
		         }
	 } 
}
