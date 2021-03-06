CoffeeShop Order API System

This is a simple coffeeshop application that demonstrate an api calls for
    listing the all products,
    listing products by its id,
    updating the existing products,
    deleting any existing products,
    create new order,
    filter orders by size and type,
    edit existing orders,
    deleting orders.

Technology/tools Stack,
    Spring Boot, no-xml Spring MVC 4 web application (Refer : http://projects.spring.io/spring-boot/)
    Spring Data MongoDB (Refer : http://projects.spring.io/spring-data-mongodb/)
    Database (MongoDB) uses default test database,
    Robomongo tool to test the collection / document in mongodb
    Testing (JUnit/Assert)
    Java 1.7
    Tomcat (app runs on 8989 and unit test runs on 8888) refer application.properties in Java / Test directories respectively)
    Deployed in Heroku/mongolab

    Why i choose the above stack,
    with SPRING BOOT, SPRING DATA MONGODB its easy to get an application up and running in less time.
    easy to configure, NoSQL helps altering the documents like adding new drinks , drink types, ingredients etc.
    Spring Data Mongo Repository handles the connections and its typesafe, for more information refer the above links.

How to run the application,

    Clone the github project
    Run the java class Application(main-->java-->app-->Application.java , this will start tomcat and use http://localhost:8989
    Use rest client or POSTMAN to access the api, i used POSTMAN

NOTE:
To test the rest api in Heroku, please replace the below localhost url with HeroKu url
https://coffeeshop-wego.herokuapp.com/

For example, https://coffeeshop-wego.herokuapp.com/order/:id
MongoLab URL: https://www.mongolab.com/databases/heroku_42kb2xk1

ORDER END POINTS:

    1) Create order:
    http://localhost:8989/order
    GET
    SAMPLE JSON Document -
     {
        "cust_name" : "Saro",
    	"items" : [
    	    {
        	"id" : 1,
        	"quantity" : 1,
        	"item_name":"espresso",
        	"type":"coffee",
        	"size" : "Tall",
        	"price" : "1.95"
    	    },
    	    {
        	"id" : 2,
        	"quantity" : 1,
        	"item_name":"green tea",
        	"type":"tea",
        	"size" : "venti",
        	"price" : "3.95"
    	    }
    	],
    	"total_price" : "5.95"
     }

     Success Message : "message": "Order created successfully",

     2) Get All orders
     http://localhost:8989/order
     GET
     List total orders with details

     3) Get order by Id
     http://localhost:8989/order/:id
     GET
     List the respective order

     4) Update an order
     http://localhost:8989/order/:id
     PUT
     SAMPLE JSON to update
      {
         "cust_name" : "Saro ",
     	"items" : [
     	    {
         	"id" : 1,
         	"quantity" : 1,
         	"item_name":"espresso",
         	"type":"coffee",
         	"size" : "Tall",
         	"price" : "1.95"
     	    },
     	    {
         	"id" : 2,
         	"quantity" : 1,
         	"item_name":"green tea",
         	"type":"tea",
         	"size" : "venti",
         	"price" : "3.95"
     	    }
     	],
     	"total_price" : "7.89"
      }

    5) Delete an order
     http://localhost:8989/order/:id
     DELETE
     Success message : "message": "Order deleted successfully"

    6) Filter by drink size and type
    http://localhost:8989/order/filter/bytype/:type (tea or coffee as example)
    GET
    Lists the orders filter by type
    http://localhost:8989/order/filter/bysize/:size (tall , grande etc.. as example)
    GET
    Lists the orders filter by size




PRODUCT end points:

1) Create order:
    http://localhost:8989/product
    GET
    SAMPLE JSON Document -
     {
      "name": "Green Tea",
      "type": "Tea",
      "size": [
        "Tall",
        "Grande",
        "Venti"
      ],
      "price": {
        "tall": 3.4,
        "grande": 4.45,
        "venti": 4.65
      }
     }


     Success Message : "message": "Order created successfully",

     2) Get All Products
     http://localhost:8989/product
     GET
     List all products with details

     3) Get products by Id
     http://localhost:8989/product/:id
     GET
     List the respective order

     4) Update an product
     http://localhost:8989/product/:id
     PUT
     SAMPLE JSON to update
      {
            "name": "Green Tea",
            "type": "Tea",
            "size": [
              "Tall",
              "Grande",
              "Venti"
            ],
            "price": {
              "tall": 3.4,
              "grande": 4.45,
              "venti": 4.65
            }
           }

    5) Delete an order
     http://localhost:8989/product/:id
     DELETE
     Success message : "message": "Product deleted successfully"


     0) What if we want a new type of coffee, a new type of tea?
     1) What if we want a new size?
     2) How would you change the model to add hot/cold options?
     3) (optional) How would you change the model to support adding condiments to drinks (perl, grass jelly,...)

    For all these above question one single answer is NoSQL (mongodb in our case), its just the document based not tied with RDBMS.
    Just add or modify the existing JSON documents and do the respective attribute, data type changes in model.

    Heroku commands,
    1) $ heroku create
    2) $ heroku apps:rename new-name (if required)
    3) $ heroku addons:create mongolab:sandbox
    4) $ git push heroku master
    5) $ heroku logs
    6) $ heroku open

    use POSTMAN or alternate rest client to access api deployed on heroku
    Make sure , Procfile and application.yml configured correctly to deploy to heroku