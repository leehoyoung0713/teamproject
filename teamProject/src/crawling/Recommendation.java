package crawling;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//네이버 쇼핑 추천 트렌드를 이용한 추천검색어, 연관검색어 출력
public class Recommendation {
   private WebDriver webDriver;
   public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";
   public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
   
   public void recommendationCrawling(String search) {
//      trends ArrayList에 추천검색어 저장
//	   relations ArrayList에 연관검색어 저장
      ArrayList<String> trends = new ArrayList<String>();
      ArrayList<String> relations = new ArrayList<String>();
      
      //   운영체제에 드라이버 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      
      Recommendation recommendation = new Recommendation();
      WebDriver driver = recommendation.webDriver;
      
//   매개변수로 받은 search를 url에 삽입하여, 바로 검색 결과를 출력, 크롤링 진행
      String url = "https://search.shopping.naver.com/search/all?query=" + search +"&cat_id=&frm=NVSHATC";

//   운영체제에 드라이버 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      
//    WebDriver객체에 크롬 드라이버 대입
      driver = new ChromeDriver();
      
//   원하는 경로를 브라우저에 전달
      driver.get(url);
      try {Thread.sleep(1000);} catch (InterruptedException e) {;}
      
//     추천 검색어 크롤링
//     cssSelector로 element들을 찾고, 찾은 엘리멘트가 있을 때마다 WebElement의 객체 webElement에 담아 trends ArrayList에 담는다.
       for (WebElement webElement : driver.findElements(By.cssSelector("a.recommendTrend_link__sPUPQ img"))) {
    	   trends.add(webElement.getAttribute("alt"));
        }
//       만약 추천검색어가 하나라도 있다면, if문의 출력문 출력
       if(trends.size() > 0 ) {
    	   System.out.print(" · 추천 검색어 : ");
    	   for (int i = 0; i < trends.size(); i++) {
    		   System.out.print("#" + trends.get(i) + " ");
    	   }
       }
       System.out.println();
       
//     만약 추천검색어가 없다면, 없다는 메세지 출력
       if(trends.size() == 0) {
          System.out.println("추천 검색어가 존재하지 않습니다.");
       }
       
//     연관 검색어 크롤링 진행
       for (WebElement webElement : driver.findElements(By.cssSelector("div.relatedTags_relation_srh__YG9s7 li a"))) {
           relations.add(webElement.getText());
        }
       
       System.out.print(" · 연관검색어 : ");
       for (int i = 0; i < relations.size(); i++) {
    	 if(i>6) {break;}
         System.out.print("#" + relations.get(i) + " ");
      }
       
       if(relations.size()==0) {
    	   System.out.println("연관 검색어가 존재하지 않습니다.");
       }
       System.out.println();
      
       driver.quit();
      
   }
}