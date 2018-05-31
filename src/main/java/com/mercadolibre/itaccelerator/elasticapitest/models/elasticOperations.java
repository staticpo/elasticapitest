package com.mercadolibre.itaccelerator.elasticapitest.models;

import com.google.gson.Gson;
import com.mercadolibre.itaccelerator.elasticapitest.controllers.Items;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;


public class elasticOperations implements elasticInterface {


    public RestHighLevelClient client;

    public elasticOperations() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "elastic"));
        /*
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
        */
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        RestHighLevelClient client = new RestHighLevelClient(builder);
        this.client = client;
    }


    public RestHighLevelClient getClient() {
        return this.client;
    }

    public void closeClient() {
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public SearchResponse getAll(String index) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = this.client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    public SearchResponse searchByTitle(String index, String title) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", title);

        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = this.client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    public SearchResponse getById(String index, String id) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("_id", id);

        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = this.client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void addItem(Items item) {

    }

    public IndexResponse addItemById(Items item, String id) {
        IndexRequest request = new IndexRequest("items", "_doc", id);
        String json = new Gson().toJson(item);
        request.source(json, XContentType.JSON);


        IndexResponse response = null;
        try {
            response = client.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    public UpdateResponse updateItemById(Items item, String id) {
        UpdateRequest request = new UpdateRequest("items", "_doc", id);
        String json = new Gson().toJson(item);
        request.doc(json, XContentType.JSON);


        UpdateResponse response = null;
        try {
            response = client.update(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }



    public DeleteResponse deleteItem(String id) {
        DeleteRequest request = new DeleteRequest("items", "_doc", id);

        DeleteResponse response = null;
        try {
            response = client.delete(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
