package crawling;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import dao.TotalDAO;
import vo.JoonggoVO;
import vo.TotalDTO;

public class JoonggoCrawling {
	private WebDriver webDriver;
	public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";

	public void crawling(String search) {
		// 운영체제에 드라이버 설정
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		JoonggoCrawling joonggoCrawling = new JoonggoCrawling();
		WebDriver driver = joonggoCrawling.webDriver;

		String url = "https://web.joongna.com/search?keyword=";
		String searchUrl = null, lastChild = null, name = null, cost = null, site = null, href = null,
				firstChild = null;
		int j = 0, view = 0, integerCost = 0, banNameCount = 0;
		TotalDAO costTotalDAO = new TotalDAO();
		
//		가져올 항목들을 담을 ArrayList 만들기
		ArrayList<String> hrefs = new ArrayList<String>();
		ArrayList<String>	names = new ArrayList<String>();
		ArrayList<Integer>costs = new ArrayList<Integer>();
		ArrayList<String>sites = new ArrayList<String>();
		
//		거를 제목 키워드
		String[] buys = {"구매", "삽니다", "구해요", "구함", "사요", "구입", "교환", "급구","양도"};
		
		
//      WebDriver객체에 크롬 드라이버 대입
		driver = new ChromeDriver();

//      원하는 경로를 브라우저에 전달
		searchUrl = url + search;
		driver.get(searchUrl);
		try {Thread.sleep(1000);} catch (InterruptedException e) {;}
		
//		url만 담기
		for (WebElement webElement : driver.findElements(By.cssSelector("a.css-8rmnao"))) {
			href = webElement.getAttribute("href");
			hrefs.add(href);
		}
		
//		제목 담기
		for (WebElement webElement : driver.findElements(By.cssSelector("div.titleTxt span.css-5uwdmz"))) {
			names.add(webElement.getText());
			}
		
//		가격
		for (WebElement webElement : driver.findElements(By.cssSelector("div.css-17j97b9 div.priceTxt"))) {
		       try {
		    	   integerCost = Integer.valueOf(costTotalDAO .deleteComma(webElement.getText()));
		       } catch(NumberFormatException e){
		    	   integerCost = -1;
		       }
		    	costs.add(integerCost);
		    }
		
		
//		동네
		for (WebElement webElement : driver.findElements(By.cssSelector("div.registInfo span:first-child"))) {
			lastChild =  driver.findElements(By.cssSelector("div.registInfo span:last-child")).get(j).getText();
			
			if(webElement.getText().equals(lastChild)) {
				sites.add("지역 정보 없음");
			} else {
				sites.add(webElement.getText());
			}
			j++;
		}
		
	    
		try {
			for (int i = 0; i < 3; i++) {
//				미리 담았던 주소로 하나씩 옮기기
				driver.get(hrefs.get(i));
				try {Thread.sleep(1000);} catch (InterruptedException e) {;}
				
				
//      		이름
				name = names.get(i);
				for (int k = 0; k < buys.length; k++) {
					if(name.contains(buys[k])) {
						banNameCount++;
						continue;	
					}
				}
				
				if(banNameCount>0) {
					banNameCount=0;
					continue;
				}

//				가격
				integerCost = costs.get(i);

//      		동네
				site = sites.get(i);
				
//				url
				href =  hrefs.get(i);

//				조회수
				try {
					view = Integer.valueOf(driver.findElement(By.tagName("p")).getText().split(" ·")[0].replaceAll("조회 ", ""));
				} catch (NumberFormatException e) {
					continue;
				}
						
//				객체 선언		
				TotalDAO totalDAO = new TotalDAO();
				JoonggoVO joonggoVO = new JoonggoVO();
				TotalDTO totalDTO = new TotalDTO();
				
				joonggoVO.setNumber(++j);
				joonggoVO.setName(name);
				joonggoVO.setPrice(integerCost);
				joonggoVO.setAddress(site);
				joonggoVO.setUrl(href);
				joonggoVO.setView(view);
//				검색 결과 저장
				totalDAO.joonggoResult(joonggoVO);

				totalDTO.setCompanyName("중고나라");
				totalDTO.setName(name);
				totalDTO.setPrice(integerCost);
				totalDTO.setAddress(site);
				totalDTO.setUrl(href);
				totalDTO.setView(view);
				totalDAO.totalResult(totalDTO);
			}
		} catch (IndexOutOfBoundsException e) {
			driver.quit();
		} 
		driver.quit();	
	}
}