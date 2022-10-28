package crawling;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import dao.TotalDAO;
import vo.DanggeunVO;
import vo.TotalDTO;

public class DanggeunCrawling {
	private WebDriver webDriver;
	public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	
	public void crawling(String search) {
		//	운영체제에 드라이버 설정
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		
		DanggeunCrawling danggeunCrawling = new DanggeunCrawling();
		WebDriver driver = danggeunCrawling.webDriver;
		
		String url = "https://www.daangn.com/search/";
		String searchUrl = null, name = null, cost = null, site = null, href = null;;
		int integerCost = 0, view = 0, j=0, banNameCount = 0;
		TotalDAO costTotalDAO = new TotalDAO();
		
		ArrayList<String> hrefs = new ArrayList<String>();
		
//		거를 제목 키워드
		String[] buys = {"구매", "삽니다", "구해요", "구함", "사요", "구입", "교환", "급구", "양도"};
		
		
//		WebDriver객체에 크롬 드라이버 대입
		driver = new ChromeDriver();
		
//		원하는 경로를 브라우저에 전달
		searchUrl = url + search;
		driver.get(searchUrl);
		try {Thread.sleep(1000);} catch (InterruptedException e) {;}
		
//		url만 담기
	    for (WebElement webElement : driver.findElements(By.cssSelector("a.flea-market-article-link"))) {
	        href = webElement.getAttribute("href");
	        hrefs.add(href);
	     }

//     	더보기 클릭
//	    당근마켓은 첫 화면에 6개의 정보가 나오고, 더보기를 누를 때마다 12개의 정보가 추가된다. 
//	    href값을 먼저 담아놓으면 세부 정보 페이지로 갔다가 다시 더보기를 누를 필요가 없기 때문에 크롤링 하려는 개수만큼 더보기를 미리 눌러, href 정보를 다 담아온다.
	    for (int k = 0; k < 2; k++) {
	    	try {
				driver.findElement(By.className("more-btn")).click();
			} catch (Exception e1) {
				continue;
			}			
	    	try {Thread.sleep(1000);} catch (InterruptedException e) {;}
	    }
		
		
		
	    try {
			for (int i = 0; i < 3; i++) {
//				미리 담았던 주소로 하나씩 옮기기
				driver.get(hrefs.get(i));
				try {Thread.sleep(1000);} catch (InterruptedException e) {;}
				
//	            예약중 또는 판매완료 상품 제외 
	            try {
	               if(driver.findElement(By.className("css-1vwq7gg")) != null) {
	                  continue;
	               }
	            } catch (NoSuchElementException e) {;}

				
//	    		이름
//	            세부 정보 페이지에서 크롤링을 진행하므로, findElements가 아닌 findElement로 정보를 찾는다.
			  	try {
					name = driver.findElement(By.cssSelector("section#article-description h1#article-title")).getText();
					for (int k = 0; k < buys.length; k++) {
//						제목에 판매가 아닌, 구매를 원하는 사람들의 글을 크롤링 하지 않고 다음으로 넘긴다.
						if(name.contains(buys[k])) {
							banNameCount++;
							continue;								
						}
					}
			  	} catch (Exception e1) {
					continue;
				}
			  	
//			  	크롤링이 종료된 후, banNameCount를 0으로 초기화하고 다음으로 넘긴다.
				if(banNameCount>0) {
					banNameCount=0;
					continue;
				}

//    			가격
			  	cost = driver.findElement(By.cssSelector("section#article-description p#article-price")).getText();
		       try {
		    	   integerCost = Integer.valueOf(costTotalDAO.deleteComma(cost));
		       } catch(NumberFormatException e){
		    	   integerCost = -1;
		       }
    
//		     	동네
			  	site = driver.findElement(By.cssSelector("section#article-profile div#region-name")).getText();
    
//				url 저장
    			href = hrefs.get(i);

//			   조회수
//    			관심 3 ∙ 채팅 2 ∙ 조회 342 형태로 text를 반환되므로, ∙ 기준으로 배열에 담고, 조회수가 있는 2번째 배열의 값을 가져와 " 조회 "를 공백으로 바꿔 int 타입으로 변환한다
		       view = Integer.valueOf(driver.findElement(By.id("article-counts")).getText().split("∙")[2].replaceAll(" 조회 ", ""));
		       
//			   객체 선언		
				TotalDAO totalDAO = new TotalDAO();
				DanggeunVO danggeunVO = new DanggeunVO();
				TotalDTO totalDTO = new TotalDTO();
					
//				VO값 세팅
				danggeunVO.setNumber(++j);
				danggeunVO.setName(name);
				danggeunVO.setPrice(integerCost);
				danggeunVO.setAddress(site);
				danggeunVO.setUrl(href);
				danggeunVO.setView(view);
				
//				DB전달
				totalDAO.danggeunResult(danggeunVO);
					
//				DTO값 세팅
				totalDTO.setCompanyName("당근마켓");
				totalDTO.setName(name);
				totalDTO.setPrice(integerCost);
				totalDTO.setAddress(site);
				totalDTO.setUrl(href);
				totalDTO.setView(view);
				
//				totalList 전달
				totalDAO.totalResult(totalDTO);
			  }

//			검색결과가 20개 미만일 경우
	 	} catch (IndexOutOfBoundsException e) {
	 		driver.quit();
	 	}
	 		driver.quit();	
	 	}
	 }