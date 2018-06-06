# elasticapitest

A simple RESTful API to do CRUD operations on an elasticsearch storage engine. It will listen to requests on http://localhost:8080/items


## Item structure - JSON example
This is an example of a Json request and it shows the structure of an Item.
	
	{
	  "title":"Item de test - No Ofertar",
	  "category_id":"MLA5529",
	  "price":10,
	  "currency_id":"ARS",
	  "available_quantity":1,
	  "buying_mode":"buy_it_now",
	  "listing_type_id":"bronze",
	  "condition":"new",
	  "description": "Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box",
	  "video_id": "YOUTUBE_ID_HERE",
	  "warranty": "12 months by Ray Ban",
	  "Pictures":[
	    {"source":"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"},
	    {"source":"http://en.wikipedia.org/wiki/File:Teashades.gif"}
	  ]
	}



## Elastic Search

**Name of the index:** items
**Name of the type:** \_doc


### Index Mapping
Use this request via curl or Postman to map the index, if you need to.

	PUT /items

	{
		"mappings": {
			"_doc": {
				"properties": {
					"title": { "type": "text"},
					"category_id": { "type": "keyword"},
					"price": { "type": "double"},
					"currency_id": { "type": "keyword"},
					"available_quantity": { "type": "integer"},
					"buying_mode": { "type": "keyword"},
					"listing_type_id": { "type": "keyword"},
					"condition": { "type": "keyword"},
					"description": { "type": "text"},
					"video_id": { "type": "text"},
					"warranty": { "type": "text"},
					"Pictures": { 
	            		"properties": {
	                	"source": { "type": "text" }
	            		}
					}
				}
			}
		}
	}


## Using the API

Create CRUD requests via REST operations.

### Get item by Id

Use a `GET /items/[id]`request to get a singe item with the desired _[id]_. This endpoint will analyze the request for the __Accept__ header and if it contains __text/html__ it will use a Spark template to serve the response. If it does not, it will return the raw JSON text.

#### Inserting items

A `POST /items/[id]` request will insert a new item in the desired _[id]_ position. You must replace the variable with the id you want, and use a JSON string as the body of the request. See the [Item structure - JSON example] [#item-structure---json-example] section for an example.

#### Updating items

A `PUT /items/[id]` request will update an item in the desired _[id]_ position. You must replace the variable with the id you want, and use a JSON string as the body of the request. See the [Item structure - JSON example] [#item-structure---json-example] section for an example.

#### Deleting items

Use a `DELETE /items/[id]` request to delete an item from ElasticSearch. Replace _[id]_ with the item's id. No body is necessary for the request.


## Docker

I've added a Dockerfile via instructions from this link: [https://hub.docker.com/_/maven/](https://hub.docker.com/_/maven/)

To run it, execute this command in your shell:
`docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install`

The tests fail because I didn't have time to mock their responses.

