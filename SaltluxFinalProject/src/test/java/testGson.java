import java.util.HashMap;

import org.elasticsearch.common.inject.internal.ToStringBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kosa.saltlux.vo.NewsVO;

public class testGson {

	public static void main(String[] args) throws JsonProcessingException {
		
		HashMap<String, String> hashMap = new HashMap<String, String>() {

			private static final long serialVersionUID = 1L;
			{
				put("classes", "1조");
				put("name", "Pre Luxsian");
			}
			
		};
		
		NewsVO newsVO = new NewsVO();
		newsVO.setTitle("title");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String json = new Gson().toJson(hashMap);
		
		System.out.println(json);
		System.out.println(objectMapper.writeValueAsString(hashMap));
		
		System.out.println(new Gson().toJson(newsVO));
		System.out.println(objectMapper.writeValueAsString(newsVO));
		
		JsonTestData forObject = new RestTemplate().getForObject("http://localhost:8080/jsonTest", JsonTestData.class);
		
		System.out.println();
		
	}
	
	public static class JsonTestData {
		
		TestData jsonData;

		public TestData getJsonData() {
			return jsonData;
		}

		public void setJsonData(TestData jsonData) {
			this.jsonData = jsonData;
		}
		
	}
	public static class TestData {
		
		private String classes;
		private String name;
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
		
	}
}
