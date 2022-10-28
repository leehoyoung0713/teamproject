package crawling;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import dao.TotalDAO;
import vo.BungaeVO;
import vo.TotalDTO;

public class BungaeCrawling {
	private WebDriver webDriver;
	public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	
	public void crawling(String search) {
		//	운영체제에 드라이버 설정
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		
		BungaeCrawling bungaeCrawling = new BungaeCrawling();
		WebDriver driver = bungaeCrawling.webDriver;
		
		String url = "https://m.bunjang.co.kr/search/products?q=";
		String searchUrl = null, lastChild = null, name = null, site = null, href = null, cost = null;
		int j = 0, integerCost = 0, view = 0, banNameCount = 0;
		TotalDAO costTotalDAO = new TotalDAO();
		
//		가져올 항목들을 담을 ArrayList 만들기
		ArrayList<String> hrefs = new ArrayList<String>();
//		거를 제목 키워드
		String[] buys = {"구매", "삽니다", "구해요", "구함", "사요", "구입", "교환", "급구","양도"};
		
//		WebDriver객체에 크롬 드라이버 대입
		driver = new ChromeDriver();
		
//		원하는 경로를 브라우저에 전달
		searchUrl = url + search;
		driver.get(searchUrl);
		try {Thread.sleep(1000);} catch (InterruptedException e) {;}
		
//		url만 담기
//		각 상품의 URL주소로 들어가 상세 정보를 크롤링해야하므로, URL을 미리 담아준다.
	    for (WebElement webElement : driver.findElements(By.cssSelector("a.sc-chbbiW"))) {
	        href = webElement.getAttribute("href");
	        hrefs.add(href);
	     }

		try {
			for (int i = 0; i < 3; i++) {
//				미리 담았던 주소로 하나씩 옮기기
				driver.get(hrefs.get(i));
//				새로운 주소로 들어가기 때문에 1초 쉬기
				try {Thread.sleep(1000);} catch (InterruptedException e) {;}
				
//	            예약중 또는 판매완료 상품 제외 
	            try {
//	            	예약중 또는 판매완료 상품의 클래스이름을 찾아보고, 그 값이 null이 아니라면, 아래 코드는 지나치고 다음 값으로 넘어간다.
	               if(driver.findElement(By.className("sc-gtfDJT cCAfKj")) != null) {
	                  continue;
	               }
	            } catch (NoSuchElementException e) {;}

			
//				이름
//	            세부 정보 페이지에서 크롤링을 진행하므로, findElements가 아닌 findElement로 정보를 찾는다.
				try {
					name = driver.findElement(By.className("sc-sdtwF")).getText();
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
				
				if(banNameCount>0) {
					banNameCount=0;
					continue;
				}
				
//				가격
//				가격정보도 getText로 찾기 때문에, cost는 String 타입으로 받게된다.
				cost = driver.findElement(By.className("sc-cHSUfg")).getText();
	            try {
//	            	우리는 int형의 가격을 찾아야 하므로 Integer.valueOf를 사용해 integer형태인 integerCost로 담아준다.
//	            	int형으로 바꾸기 전에, 숫자 내에 콤마가 있으면 Integer로 변환이 되지 않기 때문에 deleteComma 메소드를 사용해 콤마를 지워준다.
	                integerCost = Integer.valueOf(costTotalDAO.deleteComma(cost));
	             } catch(NumberFormatException e){
//	            	 만약 cost 정보가 숫자가 아닌 "가격협의" 등으로 기재되어 있던 것은 integerCost 는 -1로 기재한다.
	                integerCost = -1;
	             }
				
//				동네
	            site =  driver.findElement(By.className("sc-fEUNkw")).getText();

//				url
				href = hrefs.get(i);
		   
//				조회수
	    		view = Integer.valueOf(driver.findElement(By.cssSelector(".kdvJwU>div:nth-of-type(2)")).getText());
	    		
//				객체 선언
				TotalDAO totalDAO = new TotalDAO();
				BungaeVO bungaeVO = new BungaeVO();
				TotalDTO totalDTO = new TotalDTO();
						
//				VO 값 세팅
				bungaeVO.setNumber(++j);
				bungaeVO.setName(name);
				bungaeVO.setPrice(integerCost);
				bungaeVO.setAddress(site);
				bungaeVO.setUrl(href);
				bungaeVO.setView(view);
				
//				DB에 전달
				totalDAO.bungaeResult(bungaeVO);
						
//				DTO값 세팅
				totalDTO.setCompanyName("번개장터");
				totalDTO.setName(name);
				totalDTO.setPrice(integerCost);
				totalDTO.setAddress(site);
				totalDTO.setUrl(href);
				totalDTO.setView(view);
				
//				통합리스트에 전달
				totalDAO.totalResult(totalDTO);
			}
		} catch (Exception e) {
			driver.quit();
		}
		driver.quit();
	}
}

