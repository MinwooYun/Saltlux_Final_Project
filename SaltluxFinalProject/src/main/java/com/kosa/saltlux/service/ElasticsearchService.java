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
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.saltlux.config.ElasticSearchConfig;

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
		
		List<Object> result = new ArrayList<>();
		
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			result.addAll(hit.getSourceAsMap().values());
		}
		
		return result;
	}
	
}