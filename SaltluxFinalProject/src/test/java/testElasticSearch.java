import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
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
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.hunspell.SuggestionTimeoutException;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.shingle.ShingleFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.builders.TermQueryBuilder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.suggest.document.FuzzyCompletionQuery;
import org.apache.taglibs.standard.lang.jstl.test.beans.Factory;
import org.elasticsearch.action.ActionType;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeRequest.CustomAnalyzerBuilder;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.DeprecationHandler;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentGenerator;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.ShingleTokenFilterFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.ReindexAction;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.ReindexRequestBuilder;
import org.elasticsearch.index.reindex.ScrollableHitSource.Hit;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.Suggest.Suggestion;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionSearchContext;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import joptsimple.internal.Strings;

import java.util.Properties;
import com.ibatis.common.resources.Resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

//@PropertySource("classpath:elastic.properties")
public class testElasticSearch {

//	@Value("${hostname}")
//	private String hostname;
	
	private String hostname = "saltlux-final.es.us-central1.gcp.cloud.es.io";

	private Integer port = 9243;

	private String user = "elastic";
	
	private String password = "9yXP7QiM4JXmWL0WiXBJCnPW";
	
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
//	@Test
	public void get() throws Exception {

		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		GetRequest getRequest = new GetRequest("news", "3");
		
		GetResponse getResponse = restHighLevelClientSSLIgnore.get(getRequest, RequestOptions.DEFAULT);
		
		System.out.println(getResponse.getSourceAsMap().get("contents"));
		
	}
//	
//	/**
//	 * 
//	 * {@link RestHighLevelClient#search} 호출 테스트하고 결과를 프링팅한다. 
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void search() throws Exception {
//		
//		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();
//
//		SearchRequest searchRequest = new SearchRequest("news");
//		
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		sourceBuilder.query(QueryBuilders.matchAllQuery());
//		searchRequest.source(sourceBuilder);
//
//		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
//		
//	}
//	
//	/**
//	 * 
//	 * {@link RestHighLevelClient#bulk} bulk 작업 
//	 * 
//	 * @throws Exception
//	 * 
//	 * 임시 테스트 방법 (elastic search의 _id가 겹칠 수 있으므로 아래 방법 중 하나를 택해서 하기)
//	 * 1. 현재 index를 지우고 새로 만든 후 테스트하기
//	 * 	  : ElasticSearch의 Dev Tools 에서,
//	 * 
//	 * 		  ->DELETE news
//			  ->PUT news
//				{
//				    "settings": {},
//				    "mappings": {
//				        "properties": {
//				            "news_no": {"type": "integer"},
//				            "title" : {"type": "text"},
//				            "contents" : {"type": "text"},
//				            "image_url" : {"type": "text"},
//				            "thumbnail_url" : {"type": "text"},
//				            "press" : {"type": "text"},
//				            "category" : {"type": "text"},
//				            "news_date" : {"type": "text"},
//				            "nouns" : {"type": "text"}
//				        }
//				    }
//				}
//				
//	  * 2. 현재 index에 들어있는 가장 마지막 id를 확인한 후, 아래의 idx 변수 값을 그 다음 id로 설정한 후 테스트하기
//	  *
//	  *
//	 */
//	@SuppressWarnings("deprecation")
//	@Test
	public void news_bulk() throws Exception {
		
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
			.setBulkActions(1) // 요청 개수가 10개이면 flush
//			.setBulkSize(new ByteSizeValue(7, ByteSizeUnit.MB)) // 요청한 파일의 총 용량이 7MB면 flush
//			.setFlushInterval(TimeValue.timeValueSeconds(10)) // 요청 후 10초가 되면 flush
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
			
//			String sql = "select * from news"; 
			String sql = "select * from news where news_no = 1465035"; // 임시로 몇 개만 테스트하기 위해 설정한 것
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int idx = 1465035;
			
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
	

//	@SuppressWarnings("unchecked")
//	@Test
//	public void getContents() throws Exception {
//		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();
//
//		SearchRequest searchRequest = new SearchRequest("news");
//		List<String> result = new ArrayList<>();
//		
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		
//		
//		String[] includes = new String[]{"contents"};
//		String[] excludes = null;
//		
//		
//		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
//		sourceBuilder.fetchSource(fetchSourceContext);
//		searchRequest.source(sourceBuilder);
//		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		
//		for (SearchHit hit : searchResponse.getHits().getHits()) {
//			result.add(hit.getSourceAsString());
//		}
//
//		result.forEach(System.out::println);
//	}
	
	
	static int idx = 1348505;
	final static int from = 8300;
	
	/**
	 * 
	 * 자동완성 기능을 위한 사전 작업 : news_keyword 인덱스 만들기
	 * 1. news 인덱스에 있는 nouns 필드 값 가져오기 
	 *
	 * @throws Exception
	 */
	public List<Object> getContents() throws Exception {
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news");
		List<Object> result = new ArrayList<>();
		int count = 0;
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		// 검색 조건 필터 : nouns 필드 외에 검색 안 되도록
		String[] includes = new String[]{"nouns"};
		String[] excludes = null;
		
		
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		sourceBuilder.fetchSource(fetchSourceContext);
		sourceBuilder.from(from);
		sourceBuilder.size(300); // 1465034
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
//		ObjectMapper mapper = new ObjectMapper();
		
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			result.addAll(hit.getSourceAsMap().values());
			count++;
		}
		System.out.println("nouns: " + count);
		
		return result;

	}
	
	/**
	 * 
	 * 자동완성 기능을 위한 사전 작업 : news_keyword 인덱스 만들기
	 * 2. news_keyword 인덱스의 _analyze 호출해서 token 받아오기 (명사만)
	 *
	 * @throws Exception
	 */
	

	public HashSet<String> /*void*/ getTokens() throws Exception {
		
		List<Object> textList = getContents();
		HashSet<String> tokenSet = new HashSet<>();
//		List<String> tokenList = new ArrayList<>();
		int count = 0;
		
		for(Object text : textList) {
			
//			AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder((ElasticsearchClient) restHighLevelClientSSLIgnore(), null);
//			TokenFilter shingleFilter = new ShingleFilter(null, 3);
//			ShingleFilterFactory shingleFilterFactory = new ShingleFilterFactory(text).
//			Tokenizer t = new StandardTokenizer();
//			ShingleFilter shingleFilter = new ShingleFilter(t);
//			shingleFilter.setMaxShingleSize(4);
//			shingleFilter.setMinShingleSize(2);
	
//			requestBuilder.addTokenFilter(shingleFilter);
			AnalyzeRequest request = AnalyzeRequest.withIndexAnalyzer("auto_completion", "shingle_analyzer", text.toString());
//			request.toXContent(XContentFactory.jsonBuilder().field("max_shingle_size", 3).field("min_shingle_size", 2), null);
			AnalyzeResponse response = restHighLevelClientSSLIgnore().indices().analyze(request, RequestOptions.DEFAULT);
			List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
			
			for(AnalyzeResponse.AnalyzeToken token : tokens) {

				if(token.getType().equals("shingle") || token.getType().equals("<HANGUL>")) {
					tokenSet.add(token.getTerm());
					count++;
//					System.out.println(token.getTerm());
				}
			}
		}
		
		System.out.println("tokens: " + count);
				
		return tokenSet;
//		return tokenList;
	}
	
	/**
	 * 
	 * 자동완성 기능을 위한 사전 작업 : news_keyword 인덱스 만들기
	 * 3. news_keyword 인덱스에 token들 넣어서 인덱싱하기
	 *
	 * @throws Exception
	 */
//	@Test 
	public void putTokens() throws Exception {
		
		HashSet<String> tokenSet = getTokens();
//		List<String> tokenList = getTokens();
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
	
		@SuppressWarnings("deprecation")
		BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> {
			try {
				restHighLevelClientSSLIgnore().bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
				
				e.printStackTrace();
			}
	    }, listener)
			.setBulkActions(100) // 요청 개수가 100개이면 flush
			.setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)) // 요청한 파일의 총 용량이 7MB면 flush
			.setFlushInterval(TimeValue.timeValueSeconds(10)) // 요청 후 10초가 되면 flush
			.setBackoffPolicy(
					BackoffPolicy.constantBackoff(TimeValue.timeValueMillis(5000), 1)
					) // retry 정책 요청 후 5초 이상 응답이 없을 경우 1회 재시도
			.build();
		
		
//		int idx = 4001;
		
		for (String token : tokenSet) {
			bulkProcessor.add(new IndexRequest("auto_completion").id(Integer.toString(idx)).source(
				"search_terms", token
			));

			idx++;
		}
		
		System.out.println("index 저장 완료");
		
	}
	
//	@Test
	public void autoCompletion() throws Exception {
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		String term = "코로나 백";
		SearchRequest searchRequest = new SearchRequest("auto_completion");
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		
		suggestBuilder.addSuggestion("auto_completion_suggest", SuggestBuilders.completionSuggestion("search_terms.suggest").prefix(term));
		sourceBuilder.suggest(suggestBuilder);
		sourceBuilder.size(10);
		
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
		System.out.println(searchResponse.getSuggest().filter(CompletionSuggestion.class).stream()
	      .flatMap(s -> s.getOptions().stream())
	      .sorted(Comparator.comparingDouble(Suggest.Suggestion.Entry.Option::getScore))
	      .map(Suggest.Suggestion.Entry.Option::getText)
	      .map(Text::toString)
	      .distinct()
	      .collect(Collectors.toList()));
	}
	
//	@Test
	public void getSuggestionTerms() throws Exception {
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("auto_completion");
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("search_terms.edgengram", "코로나 확진자 수");
		
		sourceBuilder.query(matchQueryBuilder);
//		sourceBuilder.aggregation(AggregationBuilders.topHits("duplication").size(1).fetchField("search_terms"));
		sourceBuilder.size(10);
		
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);

		List<Object> result = new ArrayList<>();
		
//		System.out.println(searchResponse.getAggregations().getAsMap());
	
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			if (!result.containsAll(hit.getSourceAsMap().values())) {
				result.addAll(hit.getSourceAsMap().values());
			}
		}
		
//		result.sort(new Comparator<Object>() {
//
//			@Override
//			public int compare(Object o1, Object o2) {
//				String s1 = o1.toString();
//				String s2 = o2.toString();
//						
//				return s1.compareTo(s2);
//			}
//			
//		});
		
		System.out.println(result);
	}
	

//	@Test
	public void searchNews() throws Exception {
//		List<String> li = new ArrayList<>();
//		li.add("서울");
//		li.add("현재");
//		li.add("날씨");
		
		//받아온 pageNum
		int pageNum = 1;
		
		int startPageNum = ((pageNum-1) * 9);
		
//		String terms = li.stream().map(w -> String.valueOf(w) + ' ').collect(Collectors.joining());
		String terms = "코로나";
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news");
//		List<String> result = new ArrayList<>();
		
		// 검색 조건 필터 : nouns 필드 외에 검색 안 되도록
		String[] includes = null;
		String[] excludes = new String[]{"nouns"};
		
		
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(terms, "title", "contents");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		
		highlightBuilder.field("contents");
		highlightBuilder.preTags("<strong>");
		highlightBuilder.postTags("<strong>");
		highlightBuilder.fragmentSize(30);
		
		sourceBuilder.fetchSource(fetchSourceContext);
		sourceBuilder.highlighter(highlightBuilder);
		
		sourceBuilder.query(multiMatchQueryBuilder);
		sourceBuilder.from(startPageNum);
		sourceBuilder.size(9);
		sourceBuilder.sort("news_date", SortOrder.DESC);

		
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);

		List<Object> result = new ArrayList<>();
		
		
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			
//			System.out.println(hit.getSourceAsMap().get("news_date").toString());
			Map<String, String> map = new HashMap<>();
			
			map.put("newsNo", hit.getSourceAsMap().get("news_no").toString());
			map.put("title", hit.getSourceAsMap().get("title").toString());
			map.put("contents", hit.getSourceAsMap().get("contents").toString());
			map.put("imageUrl", hit.getSourceAsMap().get("image_url").toString());
			map.put("thumbnailUrl", hit.getSourceAsMap().get("thumbnail_url").toString());
			map.put("press", hit.getSourceAsMap().get("press").toString());
			map.put("category", hit.getSourceAsMap().get("category").toString());
			map.put("newsDate", hit.getSourceAsMap().get("news_date").toString());
			if (hit.getHighlightFields().get("contents") != null) {
				map.put("fragments", hit.getHighlightFields().get("contents").getFragments()[0].toString());
				System.out.println("%%%%%" + hit.getHighlightFields().get("contents").getFragments()[0].toString());
			}
			else {
				map.put("fragments", " ");
			}
			
//			result.add(hit.getSourceAsString());
//			System.out.println(hit.getSourceAsMap());
//			System.out.println("================");
//			if(hit.getHighlightFields().get("contents") != null)
//				System.out.println(hit.getHighlightFields().get("contents").getFragments()[0]);
//			System.out.println("================");
//			System.out.println(hit.getHighlightFields().values());
			
//			for (HighlightField highlight : hit.getHighlightFields().values()) {
//				System.out.println("================");
//				System.out.println(highlight.getFragments());
//				System.out.println("================");
//			}
			
//			list.add(hit.getSourceAsMap());
//			if(hit.getHighlightFields().get("contents") != null) {
//				list.add(hit.getHighlightFields().get("contents").getFragments()[0]);
//			}
//			else {
//				list.add(" ");
//			}
//			list.add(hit.getSourceAsMap());
			result.add(map);
		}
//		System.out.println("********************************");
//		System.out.println(list);
//		System.out.println("********************************");
//		System.out.println(result);
//		return list;
		System.out.println(result);

	}

//	@Test
	public void count() throws Exception {
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();
		
		CountRequest countRequest = new CountRequest("news"); 
		CountResponse countResponse = restHighLevelClientSSLIgnore.count(countRequest, RequestOptions.DEFAULT);
		System.out.println(countResponse.getCount());
	}
	
	
//	@Test
	public void searchNewsForSuggetionTerm() throws Exception {

		String terms = "서울 현재 날씨는?";
		int size = 10;

		
		List<Object> result = new ArrayList<>();
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news");
		
		// 검색 조건 필터 : nouns 필드 외에 검색 안 되도록
		String[] includes = new String[]{"nouns"};
		String[] excludes = null;
		
		
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(terms, "title", "contents");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.fetchSource(fetchSourceContext);
		
		sourceBuilder.query(multiMatchQueryBuilder);
		sourceBuilder.size(size);

		
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);

		
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			
			JSONObject jsonObject=new JSONObject();
			
			jsonObject.put("nouns", hit.getSourceAsMap().values().toString().replace("[", "").replace("]", ""));
			jsonObject.put("scores", hit.getScore());
			
//			Map<String, String>
//			result.add(hit.getSourceAsMap());
//			result.add(hit.getScore());
//			System.out.println(hit.getSourceAsMap());
//			System.out.println(hit.getScore());
			result.add(jsonObject);
		}
		System.out.println(result);
		
//		sendJSON(terms, result);
	}
	
	
	public String sendJSON(String terms, List<Object> result) throws Exception {
		String inputLine = null;
		StringBuffer stringBuffer = new StringBuffer();
		
		
		URL url = new URL("http://localhost:5000/news?question=" + terms);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		System.out.println(conn.toString());
		
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST"); // GET
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept-Charset", "UTF-8");
			
		
		// 데이터 전송
		BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bWriter.write(result.toString());
		
		// 전송된 결과를 읽어옴
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while((inputLine=bReader.readLine()) != null) {
			stringBuffer.append(inputLine);
		}
		
        bWriter.close();
        bReader.close();
        conn.disconnect();
		
		return stringBuffer.toString();
	}
	
}