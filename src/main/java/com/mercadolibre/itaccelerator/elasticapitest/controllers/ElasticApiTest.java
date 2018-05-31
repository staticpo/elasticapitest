package com.mercadolibre.itaccelerator.elasticapitest.controllers;

import com.google.gson.Gson;
import static spark.Spark.*;


import com.mercadolibre.itaccelerator.elasticapitest.models.elasticInterface;
import com.mercadolibre.itaccelerator.elasticapitest.models.elasticOperations;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;


public class ElasticApiTest {

    //Determine if the Accept header contains "text/html" and return true or not.
    private static boolean shouldReturnHtml(spark.Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static void main(String[] args) {


        port(8080);

        elasticInterface elastic = new elasticOperations();


        //Get all items
        get("/items", (request, response) -> {

            SearchResponse elasticResponse = elastic.getAll("items");

            Map<String, Object> model = new HashMap<String, Object>();

            SearchHits elasticHits = elasticResponse.getHits();
            String json = "";

            for (SearchHit elasticHit: elasticHits) {
                json = json + elasticHit.getSourceAsString() + "\n\n";
            }
            model.put("json", json);

            if(shouldReturnHtml(request)) {
                response.type("text/html");

                return new VelocityTemplateEngine().render(
                        new ModelAndView(model, "spark/template/velocity/singleresult.vm")
                );

            }
            else {
                response.type("application/json");
                return elasticResponse.toString();
            }
        });



        //Get Item by Id
        get("/items/:id", (request, response) -> {

            response.type("application/json");
            SearchResponse elasticResponse = elastic.getById("items", request.params(":id"));

            Map<String, Object> model = new HashMap<String, Object>();

            SearchHits elasticHits = elasticResponse.getHits();
            for (SearchHit elasticHit: elasticHits) {
                model = elasticHit.getSourceAsMap();
                model.put("json", elasticHit.getSourceAsString());
            }

            if(shouldReturnHtml(request)) {
                response.type("text/html");

                return new VelocityTemplateEngine().render(
                        new ModelAndView(model, "spark/template/velocity/singleresult.vm")
                );
            }
            else {
                response.type("application/json");
                return elasticResponse.toString();
            }


        });


        //Search by title. Queries should be: /search?title=title
        get("/search", (request, response) -> {
            response.type("application/json");

            if(request.queryParams("title") != "") {
                SearchResponse elasticResponse = elastic.searchByTitle(request.queryParams("index"), request.queryParams("title"));

                return elasticResponse.toString();
            }
            else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
            }
        });


        //Update by Id
        put("/items/:id", (request, response) -> {
            response.type("application/json");
            Items item = new Gson().fromJson(request.body(), Items.class);

            UpdateResponse esResponse = null;
            esResponse = elastic.updateItemById(item, request.params(":id"));

            DocWriteResponse.Result esResult = esResponse.getResult();
            if (esResult == DocWriteResponse.Result.UPDATED || esResult == DocWriteResponse.Result.CREATED) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
            else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
            }

        });


        //Insert by Id
        post("/items/:id", (request, response) -> {
            response.type("application/json");
            Items item = new Gson().fromJson(request.body(), Items.class);

            //elastic.addItemById(item, request.queryParams(":id"));
            IndexResponse esResponse = null;
            esResponse = elastic.addItemById(item, request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });


        //Delete by Id
        delete("/items/:id", (request, response) -> {
            response.type("application/json");

            DeleteResponse esResponse = null;
            esResponse = elastic.deleteItem(request.params(":id"));

            DocWriteResponse.Result esResult = esResponse.getResult();
            if (esResult == DocWriteResponse.Result.DELETED) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
            else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
            }
        });


    }

}
