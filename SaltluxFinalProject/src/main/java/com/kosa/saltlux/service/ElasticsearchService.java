package com.kosa.saltlux.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.saltlux.config.ElasticSearchConfig;

/**
 * @author Juhui Park
 *
 */
@Service
public class ElasticsearchService {

	private String hostname = "https://saltlux-final.es.us-central1.gcp.cloud.es.io"; // localhost

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

	public List<Object> autoCompletion(String word) throws Exception {

		SearchRequest searchRequest = new SearchRequest("test");
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder = new MatchPhrasePrefixQueryBuilder("keyword", word);
		FuzzyQueryBuilder fuzzyQueryBuilder = new FuzzyQueryBuilder("keyword", word);
		
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.minimumShouldMatch(1)
				.should(fuzzyQueryBuilder)
				.should(matchPhrasePrefixQueryBuilder);
		
		sourceBuilder.query(queryBuilder);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClientSSLIgnore().search(searchRequest, RequestOptions.DEFAULT);
		
	
	// news 기사 조회
	public List<Object> searchNews(String question, int pageNum) throws Exception {
		
		/**
		 * @author Juhui Park
		 * 
		 * news 기사 조회
		 * 
		 * @param question
		 * @param pageNum
		 * 
		 * @return List<Object> result
		 * 
		 * 
		 */
		
		int startPageNum = ((pageNum-1) * 9);
		
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();

		SearchRequest searchRequest = new SearchRequest("news");
		List<Object> result = new ArrayList<>();
		
		// 검색 조건 필터 : nouns 필드 외에 검색 안 되도록
		String[] includes = null;
		String[] excludes = new String[]{"nouns"};
		
		
		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(question, "title", "contents");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		
		highlightBuilder.field("contents");
		highlightBuilder.preTags("<AA>");
		highlightBuilder.postTags("<BB>");
		highlightBuilder.fragmentSize(30);
		
		sourceBuilder.fetchSource(fetchSourceContext);
		sourceBuilder.highlighter(highlightBuilder);
		
		sourceBuilder.query(multiMatchQueryBuilder);
		sourceBuilder.from(startPageNum);
		sourceBuilder.size(9);

		
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			result.add(hit.getSourceAsMap());
			result.add(hit.getHighlightFields());
		}
		
		return result;

	}
	
	
	// news인덱스 count 조회
	public long count() throws Exception {
		RestHighLevelClient restHighLevelClientSSLIgnore = restHighLevelClientSSLIgnore();
		
		CountRequest countRequest = new CountRequest("news"); 
		CountResponse countResponse = restHighLevelClientSSLIgnore.count(countRequest, RequestOptions.DEFAULT);
		System.out.println(countResponse.getCount());
		
		return countResponse.getCount();
	}
	
}
