package com.kosa.saltlux.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.apache.lucene.queryparser.xml.builders.TermQueryBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.saltlux.config.ElasticSearchConfig;

/**
 * @author Juhui Park
 *
 */
@Service
public class ElasticsearchService {

	private String hostname = "saltlux-final.es.us-central1.gcp.cloud.es.io"; // localhost

	private Integer port = 9243;

	private String user = "elastic";
	
	private String password = "9yXP7QiM4JXmWL0WiXBJCnPW";
	
	private RestHighLevelClient restHighLevelClientSSLIgnore() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		
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
	
	// ????????????

	public List<String> autoCompletion(String term) throws Exception {
		
		/**
		 * @author Juhui Park
		 * 
		 * ???????????? 
		 * 
		 * @param term
		 * @return ???????????? terms List
		 * 
		 */
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("auto_completion");
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		
		suggestBuilder.addSuggestion("auto_completion_suggest", SuggestBuilders.completionSuggestion("search_terms.suggest").prefix(term));
		sourceBuilder.suggest(suggestBuilder);
		sourceBuilder.size(50);
		
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
		return searchResponse.getSuggest().filter(CompletionSuggestion.class).stream()
					.flatMap(s -> s.getOptions().stream())
					.sorted(Comparator.comparingDouble(Suggest.Suggestion.Entry.Option::getScore))
					.map(Suggest.Suggestion.Entry.Option::getText)
					.map(Text::toString)
					.distinct()
					.collect(Collectors.toList());
	}
	

	// ???????????????
	public List<Object> getSuggestionTerms(String term) throws Exception {
		
		/**
		 * @author Juhui Park
		 * 
		 * ?????? ?????????
		 * 
		 * @param term : ????????? ??????
		 * @return : List<Object> result, Object ?????? : 10
		 * 
		 * 
		 */
		
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		List<Object> result = new ArrayList<>();
		
		SearchRequest searchRequest = new SearchRequest("auto_completion");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("search_terms.edgengram", term);
		
		sourceBuilder.query(matchQueryBuilder);
		sourceBuilder.size(6);
		
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);

	
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			if (!result.containsAll(hit.getSourceAsMap().values())) {
				result.addAll(hit.getSourceAsMap().values());
			}
		}
		
		System.out.println(result);
		return result;
	}
	
	// news ?????? ??????
	public List<List<Object>> searchNews(String question, int pageNum) throws Exception {
		
		/**
		 * @author Juhui Park
		 * 
		 * news ?????? ??????
		 * 
		 * @param question : ????????? ??????
		 * @param pageNum : ????????? ??????
		 * 
		 * @return List<Object> result
		 * 
		 * 
		 */
		
		int startPageNum = ((pageNum-1) * 9);
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news3");

		List<List<Object>> result = new ArrayList<>();
		List<Object> countList = new ArrayList<>();
		List<Object> newsList = new ArrayList<>();
		
		// ?????? ?????? ?????? : nouns ?????? ?????? ?????? ??? ?????????
		String[] includes = null;
		String[] excludes = new String[]{"nouns"};
		
		
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(question, "title", "contents");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		
		highlightBuilder.field("contents");
		highlightBuilder.preTags("<strong style=\"color:red\">");
		highlightBuilder.postTags("</strong>");
		highlightBuilder.fragmentSize(30);
		
		sourceBuilder.fetchSource(fetchSourceContext);
		sourceBuilder.highlighter(highlightBuilder);
		
		sourceBuilder.query(multiMatchQueryBuilder);
		sourceBuilder.from(startPageNum);
		sourceBuilder.size(9);
		sourceBuilder.sort("news_date", SortOrder.DESC);
		sourceBuilder.sort("_score", SortOrder.DESC);

		
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
		// ?????? ?????? ?????????
		countList.add(searchResponse.getHits().getTotalHits().value);
		
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			
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
			}
			else {
				map.put("fragments", " ");
			}
			System.out.println(map);
			newsList.add(map);
			
		}
		
		result.add(countList);
		result.add(newsList);
		
		return result;

	}
	
	// ????????? : ??????????????? 
	public List<Object> searchNewsForSuggetionTerm(String question) throws Exception {
		
		/**
		 * @author Juhui Park
		 * 
		 * ??????????????? ?????????????????? ????????? ?????? ?????????
		 * 
		 * 
		 * @param question : ????????? ??????
		 * @param size : ?????? ?????? ???
		 * 
		 * @return List<Object> result
		 * 
		 * 
		 */
		
		List<Object> result = new ArrayList<>();
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news3");
		
		// ?????? ?????? ?????? : nouns ????????? ??????
		String[] includes = new String[]{"nouns"};
		String[] excludes = null;
		
		
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(question, "title", "contents");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.fetchSource(fetchSourceContext);
		sourceBuilder.query(multiMatchQueryBuilder);

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
		
		return result;
	}
	
}
