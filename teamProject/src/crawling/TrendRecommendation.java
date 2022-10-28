package crawling;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TrendRecommendation {//네이버 쇼핑 추천 트렌드
   private WebDriver webDriver;
   public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";
   public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
   
   public void trendRecommendationCrawling(String search) {
      
      ArrayList<String> trends = new ArrayList<String>();
      //   운영체제에 드라이버 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      
      TrendRecommendation trendRecomendation = new TrendRecommendation();
      WebDriver driver = trendRecomendation.webDriver;
      
      String url = "https://search.shopping.naver.com/search/all?query=" + search +"&cat_id=&frm=NVSHATC";

//      운영체제에 드라이버 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      
//      WebDriver객체에 크롬 드라이버 대입
      driver = new ChromeDriver();
      
      
//      원하는 경로를 브라우저에 전달
      driver.get(url);
      try {Thread.sleep(1000);} catch (InterruptedException e) {;}
      
       for (WebElement webElement : driver.findElements(By.className("recommendTrend_description__LsPRq"))) {
           trends.add(webElement.getText());
        }

       if(trends.size() > 0 ) {
    	   System.out.print(" · 추천 검색어 : ");
    	   for (int i = 0; i < trends.size(); i++) {
    		   System.out.print(trends.get(i) + " ");
    	   }
       }

       
       
       if(trends.size() == 0) {
          System.out.println("추천 트렌드가 존재하지 않습니다.");
       }
      
       driver.quit();
      
   }
}