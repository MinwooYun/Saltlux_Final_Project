import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.HashMap;

import org.elasticsearch.common.inject.internal.ToStringBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kosa.saltlux.vo.NewsVO;


public class esBulk {

	public static void main(String[] args) throws JsonProcessingException  {
		// DB 접속 객체선언
        Connection conn = null;
 
        try {
            // Maria db 드라이버 로드
            Class.forName("org.mariadb.jdbc.Driver");
            // 데이터베이스 접속
            conn = DriverManager.getConnection("jdbc:mariadb://211.109.9.175:9082/Luxian", "root", "sw_saltlux");
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        if (conn != null) {
            System.out.println("접속성공");
        }
        
        
		HashMap<String, String> hashMap = new HashMap<String, String>() {

			private static final long serialVersionUID = 1L;
			{
				put("classes", "1조");
				put("name", "Pre Luxsian");
			}
			
		};
		
		NewsVO newsVO = new NewsVO();
		newsVO.setTitle("tile");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String json = new Gson().toJson(hashMap);
		
		System.out.println(json);
		System.out.println(objectMapper.writeValueAsString(hashMap));
		
		System.out.println(new Gson().toJson(newsVO));
		System.out.println(objectMapper.writeValueAsString(newsVO));
		
		JsonNewsData forObject = new RestTemplate().getForObject("http://localhost:8080/newsTest", JsonNewsData.class);
		
		System.out.println();

	}
	
	
	public static class JsonNewsData {
		NewsData jsonData;
		public NewsData getJsonData() {
			return jsonData;
		}
		public void setJsonData(NewsData jsonData) {
			this.jsonData = jsonData;
		}
	}
	
	public static class NewsData {
		private String classes;
		private String name;
		private String newsNo;
		private String contents;
		private String imageURL;
		private String thumbnailURL;
		private String press;
		private String category;
		private Date newsDate;
		private String nouns;
		
		public String getClasses() {
			return classes;
		}
		public void setClasses(String classes) {
			this.classes = classes;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNewsNo() {
			return newsNo;
		}
		public void setNewsNo(String newsNo) {
			this.newsNo = newsNo;
		}
		public String getContents() {
			return contents;
		}
		public void setContents(String contents) {
			this.contents = contents;
		}
		public String getImageURL() {
			return imageURL;
		}
		public void setImageURL(String imageURL) {
			this.imageURL = imageURL;
		}
		public String getThumbnailURL() {
			return thumbnailURL;
		}
		public void setThumbnailURL(String thumbnailURL) {
			this.thumbnailURL = thumbnailURL;
		}
		public String getPress() {
			return press;
		}
		public void setPress(String press) {
			this.press = press;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public Date getNewsDate() {
			return newsDate;
		}
		public void setNewsDate(Date newsDate) {
			this.newsDate = newsDate;
		}
		public String getNouns() {
			return nouns;
		}
		public void setNouns(String nouns) {
			this.nouns = nouns;
		}
		
	}

}
