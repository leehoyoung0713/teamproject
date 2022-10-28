package view;

import java.util.ArrayList;
import java.util.Scanner;

import crawling.BungaeCrawling;
import crawling.DanggeunCrawling;
import crawling.JoonggoCrawling;
import crawling.Recommendation;
import dao.TotalDAO;
import dao.UserDAO;
import vo.BasketVO;
import vo.BungaeVO;
import vo.DanggeunVO;
import vo.JoonggoVO;
import vo.TotalDTO;
import vo.UserDataVO;
import vo.UserVO;

public class Test {

	public static ArrayList<JoonggoVO> joonggoList = new ArrayList<>();
	public static ArrayList<DanggeunVO> danggeunList = new ArrayList<>();
	public static ArrayList<BungaeVO> bungaeList = new ArrayList<>();
	public static ArrayList<TotalDTO> totalList = new ArrayList<>();
	public static ArrayList<TotalDTO> setTotalList = new ArrayList<>();
	public static ArrayList<TotalDTO> viewTotalList = new ArrayList<>();
	public static ArrayList<BasketVO> basketList = new ArrayList<>();

	public static void main(String[] args) {

		JoonggoCrawling joonggoCrawling = new JoonggoCrawling();
		DanggeunCrawling danggeunCrawling = new DanggeunCrawling();
		BungaeCrawling bungaeCrawling = new BungaeCrawling();
		TotalDAO totalDAO = new TotalDAO();
		UserDAO userDAO = new UserDAO();
		UserVO userVO = new UserVO();
		UserDataVO userDataVO = new UserDataVO();
		TotalDTO basketGood = new TotalDTO();

		Recommendation recommendation = new Recommendation();

		String search = null, searchSecond = null, cg = null, id = null, pw = null, category = null;
		int caseNumber = 0, caseNumberTwo = 0, caseNumberThree = 0, userNumberOne = 0, categoryCount = 0,
				productNum = 0, interestGoods=0;
		int searchNumber = 0, number = 0, count = 0, category_num = 0, countA = 0;
		Scanner sc = new Scanner(System.in);

		String[] categoryAr = { "공통의류", "여성의류", "남성의류", "신발", "가방", "시계", "쥬얼리", "뷰티", "화장품", "헤어", "육아", "유아", "유아의류",
				"유아잡화", "유아완구", "가구", "가정가구용품", "기타가구", "소형가전", "대형가전", "주방", "디지털기기", "PC", "게임", "카메라", "스포츠", "헬스용품",
				"계절스포츠", "자전거", "레저	", "낚시용품", "등산용품", "골프용품", "완구", "예술", "도서", "음반", "자동차", "생활용품", "반려용품", "여행" };

//		반복문 1번
		try {
			while (true) {
				System.out.println("=========================================================\n======================= 3조 자바 프로젝트 ======================\n=========================================================");
				System.out.println("1. 로그인\n2. 회원가입\n3. 비회원 검색\n4. 종료");
				caseNumber = sc.nextInt();
//			4 클릭 시 종료 
				if (caseNumber == 4) {
					System.out.println("이용해주셔서 감사합니다.");
					break;
				}
				switch (caseNumber) {
//			로그인 화면
				case 1:
					System.out.println("아이디를 입력하세요 : ");
					id = sc.next();
					System.out.println("비밀번호를 입력하세요 : ");
					pw = sc.next();
					
//				userNumberOne 변수에 로그인 성공한 회원의 번호를 저장
					userNumberOne = userDAO.login(id, pw);
//				회원번호는 0이 될 수 없으므로, 0이 아니라면 로그인 성공 출력 후 로그인 이후 화면 출력
					if (userNumberOne != 0) {
						System.out.println("로그인 성공!");
//					전체 정보를 담을 totalList 초기화
						totalList = new ArrayList<>();
						
//						반복문 2
//						로그인 후 화면
						try {
							while (true) {
								System.out.println("1. 검색\n2. 내 정보\n3. 내 카테고리 변경\n4. 장바구니 보기\n5. 뒤로가기");
								caseNumberTwo = sc.nextInt();
//								5 선택 시 반복문 1로 돌아감
								if (caseNumberTwo == 5) {
									break;
								}
								switch (caseNumberTwo) {
//								검색
								case 1:
									sc.nextLine();
									System.out.print("검색어: ");
//			    					검색어 입력
//									검색어에 띄어쓰기가 있을 수 있으므로 nextLine으로 진행
									search = sc.nextLine();
//			    					사이트 크롤링 - 중고나라, 당근마켓, 번개장터 순서로 크롤링 진행
//									전체 정보를 담을 totalList 초기화
									totalList = new ArrayList<>();
									joonggoCrawling.crawling(search);
									danggeunCrawling.crawling(search);
									bungaeCrawling.crawling(search);

//			    					유저 정보에 작성한 키워드 저장
									userDataVO.setId(id);
//									작성한 검색어 저장
									userDataVO.setKeyWord(search);
//									userDataVO에 아이디, 키워드값 저장
									userDAO.joinUserData(userDataVO);

//				   		    		연관검색어/추천 검색어 출력
									recommendation.recommendationCrawling(search);
									System.out.println("========================");

//			    			사이트 크롤링 정보 출력 
//							각 크롤링된 정보에 번호를 주기 위해 count 값 초기화
//							만약 이걸 하지 않는다면, 검색창 검색 후 다시 검색을 하게 되면 1부터 정보 출력이 되지 않고, 이전 검색한 검색량 +1이 된 숫자부터 나오게 된다.
									count=0;
									for (int i = 0; i < totalList.size(); i++) {
										System.out.println(++count + "\t" + totalList.get(i));
									}

//							반복문 3
//							검색 완료 후 화면
									try {
										while (true) {
											System.out.println("1. 카테고리 검색\n2. 조회순 검색\n3. 상품 고르기\n4. 뒤로가기");
											caseNumberThree = sc.nextInt();
//					   		    4번 누르면 뒤로가기가 되어 반복문 2가 나오게 된다.
											if (caseNumberThree == 4) {
												break;
											}
											switch (caseNumberThree) {
//					   		    카테고리 검색
											case 1:
												System.out.println("원하는 카테고리를 골라주세요.\n*나가기: 99");
//									어떤 카테고리가 있는지 화면에 출력
												for (int i = 0; i < categoryAr.length; i++) {
													System.out.println((i + 1) + ". " + categoryAr[i]);
												}
												System.out.print("카테고리 번호(카테고리 선택을 원하지 않을 경우 99를 입력해주세요): ");
//									고를 카테고리 번호 입력
												category_num = sc.nextInt();
//					   		    	99 입력 시 반복문 2로 이동
												if (category_num == 99) {
													break;
												}
//					   		    	1~41 입력 시 해당 번호에 부합하는 카테고리 선택
												category = totalDAO.getCategory(category_num);
//					   		    	DB에 저장된 카테고리 내 키워드를 ","를 기준으로 String배열에 담음
												String[] categories = category.split(",");
												
												

//					   		    	카테고리 부합 상품이 있는지 여부 판단을 위해 countA를 0으로 초기화해줌
												countA = 0;
												for (int i = 0; i < totalList.size(); i++) {
													for (int j = 0; j < categories.length; j++) {
//					   		    			전체 출력 정보의 제목과 카테고리 내 키워드가 부합하는 것이 있는지 확인 
														if (totalList.get(i).getName().contains(categories[j])) {
//					   		    				부합되는 것이 있다면 setTotalList에 정보 추가
															setTotalList.add(totalList.get(i));
//					   		    				부합되는 항목 출력
															System.out.println(++countA + "\t" + totalList.get(i));

//					   		    				카테고리 부합 상품이 있기 때문에 categoryCount 1 추가
															categoryCount++;
//												여기서 break를 잡아주지 않으면, 한 게시글의 제목에 동일한 키워드가 여러 개 들어가 있다면 들어간 키워드 만큼 출력을 반복해 동일한 게시글이 여러 개가 나오게 된다.
															break;
														}
													}
												}
//					   		    	categoryCount가 0이라면 부합 상품이 없다는 것을 뜻하기 때문에 부합 상품이 없음을 출력
												if (categoryCount == 0) {
													System.out.println("카테고리에 부합하는 상품이 없습니다.");
												}
												
//					   		    	categoryCount 초기화
												categoryCount = 0;
//									카테고리에 부합한 상품을 넣었던 setTotalList 초기화
												setTotalList = new ArrayList<>();			
												break;

//					   		    조회순 검색
											case 2:
//									조회순으로 검색하기 전 setTotalList 초기화
												setTotalList = new ArrayList<>();
//									내림차순 진행을 위한 메소드 실행
												totalDAO.arrangeView();
//									내림차순 진행 후 각 항목 차례대로 출력
												for (int j = 0; j < setTotalList.size(); j++) {
													System.out.println((j + 1) + ". " + setTotalList.get(j));
												}
												break;
//								상품 고르기
											case 3:
//									상품의 번호를 나타내기 위한 count 변수 초기화
												count = 0;
//									어떤 상품을 고를지 쉽게 파악하기 위해 전체 리스트 출력
												for (int i = 0; i < totalList.size(); i++) {
													System.out.println(++count + "\t" + totalList.get(i));
												}
												System.out.print("원하는 번호 출력: ");
												productNum = sc.nextInt();
												basketGood =  totalDAO.choiceItem(--productNum);
												
												System.out.println("해당 상품을 장바구니에 담으시겠습니까?\n1. 예\t2. 아니오");
												interestGoods = sc.nextInt();
												switch(interestGoods) {
													case 1 :
//											setTotalList 초기화
														setTotalList = new ArrayList<>();
//											setTotalList에 관심상품 추가
														setTotalList.add(basketGood);
//				                         	중복검사, 장바구니에 없으면 true임
										                 if(!totalDAO.checkName(basketGood.getName(), id)){
										                	 System.out.println("이미 장바구니에 담은 상품입니다."); 
										                	 setTotalList = new ArrayList<>();
										                	 break;}
//				                         	상품 추가
										                totalDAO.insertBasket(productNum, id);
										                System.out.println("해당 상품이 장바구니에 추가되었습니다.");
										                 
										                 count = 0;
										                 setTotalList = new ArrayList<TotalDTO>();
														break;
//										아니오 선택 시 반복문3으로 이동
													case 2 :
														break;
														
													default: 
														System.out.println("잘못 입력했습니다 다시 입력하세요!");
														break;
												}
												break;
											default:
												System.out.println("잘못 입력했습니다 다시 입력하세요!");
												break;
											}
										} // 반복문 3 끝
										break;
									} catch (Exception e) {
										System.out.println("잘못된 접근입니다.");
										break;
									}
//					   	내 정보
								case 2:
									System.out.println("· 아이디 : "+userDAO.selectUser(userNumberOne).getUserID()+"\n· 나의 관심 카테고리: " + userDAO.selectUser(userNumberOne).getCategory());
									break;
//			    		내 카테고리 변경	
								case 3:
									for (int i = 0; i < categoryAr.length; i++) {
										System.out.println((i + 1) + ". " + categoryAr[i]);
									}
									 category_num=sc.nextInt();
							         category= categoryAr[--category_num];
							         userVO=userDAO.selectUser(userNumberOne);
							         userVO.setCategory(totalDAO.getCategory(++category_num));
//	                         선택한 카테고리 번호의 정보를 userDAO에 저장
							         userDAO.update(userVO); 
							         System.out.println(category + " 으로 변경되었습니다.");
							         break;
//			    		장바구니 보기	
								case 4:
//							만약 장바구니에 항목이 있다면
									if(basketList.size()>0) {
//							 장바구니 내 정보 출력
							         System.out.println("나의 장바구니 \n");
							         totalDAO.showBasket(id);
							         for (int i = 0; i < basketList.size(); i++) {
							           System.out.println(i+1 + "\t" +basketList.get(i).getItemName() + "\t"+ basketList.get(i).getItemPrice() + "\t" + basketList.get(i).getItemUrl());
							        }
									}
//							basketList.size가 0이라면 상품 없다는 메세지 출력
									else {
										System.out.println("장바구니에 상품이 없습니다.");
									}
							         break;
								default:
									System.out.println("잘못 입력했습니다 다시 입력하세요!");
									break;
								}
							}
						} catch (Exception e) {
							System.out.println("잘못된 접근입니다.");
							break;
						}
						// 반복문 2 종료
						break;
					} else {
						System.out.println("아이디 또는 비밀번호가 틀렸습니다!");
						break;
					} 
//			    회원가입
				case 2:
//				반복문 4
					try {
						while (true) {
							System.out.println("아이디를 입력하세요 : ");
							id = sc.next();
//					중복 아이디 검사
							if (userDAO.checkId(id)) {
								System.out.println("중복된 아이디가 있습니다 다시 입력하세요.");
							} else {
								System.out.println("사용 가능한 아이디");
								userVO.setUserID(id);
								System.out.println("비밀번호를 입력하세요 : ");
								pw = sc.next();
								userVO.setUserPassword(pw);
								System.out.println("원하는 카테고리를 입력하세요 : ");
								for (int i = 0; i < categoryAr.length; i++) {
									System.out.println((i + 1) + ". " + categoryAr[i]);
								}
								category_num = sc.nextInt();
								category = totalDAO.getCategory(category_num);
//						카테고리 입력 시, 카테고리 대분류 이름이 아닌 그 안의 소분류 내용이 저장됨
								String[] categories = category.split(",");
//                  카운트 초기화
								countA = 0;
								System.out.println("회원가입 성공!");
//					userVO정보에 카테고리 선택 내역 저장
								userVO.setCategory(category);
//					TABLE_USERS에 저장
								userDAO.join(userVO);
							}
							break;
						} // 반복문 4 끝
						break;
					} catch (Exception e) {
						System.out.println("잘못된 접근입니다.");
						break;
					}
//			    비회원 보기
				case 3:
//					totalList 초기화
					totalList = new ArrayList<TotalDTO>();
					sc.nextLine();
					System.out.print("검색어: ");
//	    		검색어 입력
					search = sc.nextLine();
//	    		사이트 크롤링
					joonggoCrawling.crawling(search);
					danggeunCrawling.crawling(search);
					bungaeCrawling.crawling(search);

//		   		연관검색어/추천 검색어 출력
					recommendation.recommendationCrawling(search);
					System.out.println();

//	    		사이트 크롤링 정보 출력 
					count = 0;
					for (int i = 0; i < totalList.size(); i++) {
						System.out.println(++count + "\t" + totalList.get(i));
					}
					
//				반복문 5 - 상품 정보 출력 후 메뉴 화면
					try {
						while (true) {
							System.out.println("1. 카테고리 검색\n2. 조회순 검색\n3. 상품 고르기\n4. 뒤로가기");
							caseNumberThree = sc.nextInt();
//			   		3번 누르면 뒤로가기
							if (caseNumberThree == 4) {
								break;
							}
							switch (caseNumberThree) {
//			   		카테고리 검색
							case 1:
								System.out.println("원하는 카테고리를 골라주세요.\n*나가기: 99");
								for (int i = 0; i < categoryAr.length; i++) {
									System.out.println((i + 1) + ". " + categoryAr[i]);
								}
								System.out.print("카테고리 번호(카테고리 선택을 원하지 않을 경우 99를 입력해주세요): ");
								category_num = sc.nextInt();
//			   		    99 입력 시 뒤로가기 선택
								if (category_num == 99) {
									break;
								}
//			   		    1~41 입력 시 해당 번호에 부합하는 카테고리 선택
								category = totalDAO.getCategory(category_num);
//			   		    DB에 저장된 카테고리 내 키워드를 ","를 기준으로 String배열에 담음
								String[] categories = category.split(",");

//			   		    카테고리 부합 상품이 있는지 여부 판단을 위해 countA를 0으로 초기화해줌
								countA = 0;
								for (int i = 0; i < totalList.size(); i++) {
									for (int j = 0; j < categories.length; j++) {
//			   		    		전체 출력 정보의 제목과 카테고리 내 키워드가 부합하는 것이 있는지 확인 
										if (totalList.get(i).getName().contains(categories[j])) {
//			   		    			부합되는 것이 있다면 setTotalList에 정보 추가
											setTotalList.add(totalList.get(i));
//			   		    			부합되는 항목 출력
											System.out.println(++countA + "\t" + totalList.get(i));
//			   		    			카테고리 부합 상품이 있기 때문에 categoryCount 1 추가
											categoryCount++;
											break;
										}
									}
								}
//			   		    categoryCount가 0이라면 부합 상품이 없다는 것을 뜻하기 때문에 부합 상품이 없음을 출력
								if (categoryCount == 0) {
									System.out.println("카테고리에 부합하는 상품이 없습니다.");
								}
//			   		    categoryCount 초기화
								categoryCount = 0;
								setTotalList = new ArrayList<>();
								break;
								
//			     	조회순 정렬
							case 2:
								setTotalList = new ArrayList<>();	
								totalDAO.arrangeView();								
								for (int j = 0; j < setTotalList.size(); j++) {
									System.out.println((j + 1) + ". " + setTotalList.get(j));
								}
								break;
								
								

//					상품 고르기
							case 3:
								setTotalList = new ArrayList<TotalDTO>();
								count =0;
								for (int i = 0; i < totalList.size(); i++) {
									System.out.println(++count + "\t" + totalList.get(i));
								}
								System.out.print("원하는 번호 출력: ");
								productNum = sc.nextInt();
								totalDAO.choiceItem(--productNum);
								break;

							default:
								System.out.println("잘못 입력했습니다 다시 입력하세요!");
								break;
							}
						} 
						// 반복문 5 끝
					} catch (Exception e) {
						System.out.println("잘못된 접근입니다.");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("잘못된 접근입니다.");
		} 
		//반복문 1 끝
	}
}

