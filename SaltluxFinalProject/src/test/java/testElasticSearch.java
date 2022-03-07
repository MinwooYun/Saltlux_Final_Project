import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;


public class testElasticSearch {

	private String hostname = "final-project.es.us-central1.gcp.cloud.es.io"; // localhost

	private Integer port = 9243;

	private String user = "elastic";
	
	private String password = "i8Fyr6KQKVDzBEjrZKKZZcii";
	
	private String driver = "org.mariadb.jdbc.Driver";
	
	private String url = "jdbc:mariadb://211.109.9.175:9082/luxian";
	
	private String username = "root";
	
	private String pwd = "sw_saltlux";

	/**
	 * SSL 무시하고 통신하는 Elastic Search Client 객체 {@link RestHighLevelClient}를 생성해서 반환한다.
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws KeyManagementException
	 */
	public RestHighLevelClient restHighLevelClientSSLIgnore() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, password));

		final SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true);
		final SSLContext sslContext = sslBuilder.build();
		
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(hostname, port, "https"))
						.setHttpClientConfigCallback(new HttpClientConfigCallback() {
							@Override
							public HttpAsyncClientBuilder customizeHttpClient(
									HttpAsyncClientBuilder httpClientBuilder) {
								return httpClientBuilder.setSSLContext(sslContext)
										.setDefaultCredentialsProvider(credentialsProvider)
										.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
							}
						}).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
							@Override
							public RequestConfig.Builder customizeRequestConfig(
									RequestConfig.Builder requestConfigBuilder) {
								return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(120000);
							}
						}));
		
		System.out.println("elasticsearch client created");
		return client;

	}

	/**
	 * 
	 * {@link RestHighLevelClient#get} 호출 테스트하고 결과를 프링팅한다. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void get() throws Exception {

		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		GetRequest getRequest = new GetRequest("news", "3");
		
		GetResponse getResponse = restHighLevelClientSSLIgnore.get(getRequest, RequestOptions.DEFAULT);
		
		System.out.println(getResponse);
		
	}
	
	/**
	 * 
	 * {@link RestHighLevelClient#search} 호출 테스트하고 결과를 프링팅한다. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void search() throws Exception {
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news");
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
	}
	
	/**
	 * 
	 * {@link RestHighLevelClient#bulk} bulk 작업 
	 * 
	 * @throws Exception
	 * 
	 * 임시 테스트 방법 (elastic search의 _id가 겹칠 수 있으므로 아래 방법 중 하나를 택해서 하기)
	 * 1. 현재 index를 지우고 새로 만든 후 테스트하기
	 * 	  : ElasticSearch의 Dev Tools 에서,
	 * 
	 * 		  ->DELETE news
			  ->PUT news
				{
				    "settings": {},
				    "mappings": {
				        "properties": {
				            "news_no": {"type": "integer"},
				            "title" : {"type": "text"},
				            "contents" : {"type": "text"},
				            "image_url" : {"type": "text"},
				            "thumbnail_url" : {"type": "text"},
				            "press" : {"type": "text"},
				            "category" : {"type": "text"},
				            "news_date" : {"type": "text"},
				            "nouns" : {"type": "text"}
				        }
				    }
				}
				
	  * 2. 현재 index에 들어있는 가장 마지막 id를 확인한 후, 아래의 idx 변수 값을 그 다음 id로 설정한 후 테스트하기
	  *
	  *
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void bulk() throws Exception {
		
		BulkProcessor.Listener listener = new BulkProcessor.Listener() {
			
			int count = 0;

			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				count = count + request.numberOfActions(); 
				System.out.println("Uploaded " + count + " requests");

			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				if (response.hasFailures()) { 
					for (BulkItemResponse bulkItemResponse : response) { 
						if (bulkItemResponse.isFailed()) { 
							BulkItemResponse.Failure failure = bulkItemResponse.getFailure(); 
							System.out.println("Error " + failure.toString()); 
						} 
					} 
				}
				
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("Errors " + failure.toString());
			}
		};
		
		BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> {
			try {
				restHighLevelClientSSLIgnore().bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
				
				e.printStackTrace();
			}
        }, listener)
			.setBulkActions(10) // 요청 개수가 10개이면 flush
			.setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)) // 요청한 파일의 총 용량이 7MB면 flush
			.setFlushInterval(TimeValue.timeValueSeconds(10)) // 요청 후 10초가 되면 flush
			.setBackoffPolicy(
					BackoffPolicy.constantBackoff(TimeValue.timeValueMillis(5000), 1)
					) // retry 정책 요청 후 5초 이상 응답이 없을 경우 1회 재시도
			.build();
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, pwd);
			System.out.println("연결 성공");
			
			String sql = "select * from news where news_no between 12 and 31"; // 임시로 몇 개만 테스트하기 위해 설정한 것
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int idx = 1;	
			
			while(rs.next()) {
				bulkProcessor.add(new IndexRequest("news").id(Integer.toString(idx)).source(
						"news_no", rs.getInt("news_no"),
						"title", rs.getString("title"),
						"contents", rs.getString("contents"),
						"image_url", rs.getString("image_url"),
						"thumbnail_url", rs.getString("thumbnail_url"),
						"press", rs.getString("press"),
						"category", rs.getString("category"),
						"news_date", rs.getDate("news_date").toString(),
						"nouns", rs.getString("nouns")
				));
				
				idx++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
			pstmt.close();
			rs.close();
			System.out.println("Driver 연결 종료");
		}
	}
}
