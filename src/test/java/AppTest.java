import com.jayway.restassured.*;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import com.mercadolibre.itaccelerator.elasticapitest.controllers.Items;
import com.mercadolibre.itaccelerator.elasticapitest.controllers.Pictures;
import org.elasticsearch.action.get.MultiGetRequest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class AppTest {

    //https://semaphoreci.com/community/tutorials/testing-rest-endpoints-using-rest-assured

    @Test
    public void basicPingTest() {
        given().when().get("/items").then().statusCode(200);
    }


    @Test
    public void invalidItemId() {
        given().when().get("/items/9999NONEXISTANTID9999")
                .then().statusCode(200)
                .body("hits.total", equalTo(0));
    }
    

    //Test - add an item with id = 1000TEST
    @Test
    public void testAddingItem() {

        Pictures pic1 = new Pictures("http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg");
        Pictures pic2 = new Pictures("http://en.wikipedia.org/wiki/File:Teashades.gif");

        Items item = new Items();
        item.setTitle("Item de test - No Ofertar");
        item.setCategory_id("MLA5529");
        item.setPrice(BigDecimal.TEN);
        item.setCurrency_id("ARS");
        item.setAvailable_quantity(1);
        item.setBuying_mode("buy_it_now");
        item.setListing_type_id("bronze");
        item.setCondition("new");
        item.setDescription("Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban itemrying Case and Cleaning Cloth. New in Box");
        item.setVideo_id("YOUTUBE_ID_HERE");
        item.setWarranty("12 months by Ray Ban");
        //item.setPictures(pic1, pic2);

        given()
                .contentType("application/json")
                .body(item)
                .when().post("/items/1002TEST").then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"));
    }

    //Test if the item exists
    @Test
    public void validItemId() {
        given().when().get("/items/1000TEST")
                .then().statusCode(200);
    }

    //Test - updating the item
    @Test
    public void testUpdatingItem() {

        Items item = new Items();
        item.setTitle("This is the new title for the item"); //changed the title
        item.setCategory_id("MLA5529");
        item.setPrice(BigDecimal.TEN);
        item.setCurrency_id("ARS");
        item.setAvailable_quantity(1);
        item.setBuying_mode("buy_it_now11");
        item.setListing_type_id("bronze");
        item.setCondition("new");
        item.setDescription("Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban itemrying Case and Cleaning Cloth. New in Box");
        item.setVideo_id("YOUTUBE_ID_HERE");
        item.setWarranty("12 months by Ray Ban");


        given()
                .contentType("application/json")
                .body(item)
                .when().put("/items/1001TEST").then()
                .body("status", equalTo("SUCCESS"));
    }




}
