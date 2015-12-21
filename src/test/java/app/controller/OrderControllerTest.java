package app.controller;

import app.Application;
import app.model.Item;
import app.model.Order;
import app.model.Price;
import app.model.Product;
import app.repository.OrderRepository;
import app.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class OrderControllerTest {

  //To Generate JSON content from Java objects
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  
  //Delete test data after test.
  //Invoke the APIs interacting with the DB
  @Autowired
  private OrderRepository orderRepository;
  
  //Use RestTemplate to invoke the APIs.
  private RestTemplate restTemplate = new TestRestTemplate();
  
  @Test
  public void testCreateOrderApi() throws JsonProcessingException{
    
    //Building the Request body data
    Map<String, Object> requestBody = new HashMap<String, Object>();


    Item item = new Item();
    item.setId(1);
    item.setItem_name("Espresso");
    item.setPrice((float) 1.95);
    item.setQuantity(1);
    item.setType("coffee");
    item.setSize("Tall");

    ArrayList<Item> itemList = new ArrayList<Item>();
    itemList.add(item);
    requestBody.put("cust_name", "Saro");
    requestBody.put("total_price", "7.35");
    requestBody.put("items", itemList);

    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Create http entity object with request body / headers
    HttpEntity<String> httpEntity = 
        new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
    
    //Invoking the API
    Map<String, Object> apiResponse = 
        restTemplate.postForObject("http://localhost:8888/order", httpEntity, Map.class, Collections.EMPTY_MAP);

    assertNotNull(apiResponse);
    System.out.println(apiResponse);
    //Asserting the response of the API.
    String message = apiResponse.get("message").toString();

    assertEquals("Order created successfully", message);
    String orderId = ((Map<String, Object>)apiResponse.get("order")).get("id").toString();
    
    assertNotNull(orderId);

    //Delete data
    orderRepository.delete(orderId);

  }

  @Test
  public void testGetOrderDetailsApi(){

    Item item = new Item();
    item.setId(1);
    item.setItem_name("Espresso");
    item.setPrice((float) 1.95);
    item.setQuantity(1);
    item.setType("coffee");
    item.setSize("Tall");
    ArrayList<Item> itemList = new ArrayList<Item>();
    itemList.add(item);
    float total_price = (float) 1.95;

    Order order = new Order("Saro",itemList,total_price);
    orderRepository.save(order);

    String orderId = order.getId();

    //call api
    Order apiResponse = restTemplate.getForObject("http://localhost:8888/order/"+ orderId, Order.class);

    //Verify that the data from the API and data saved in the DB are same
    assertNotNull(apiResponse);
    assertEquals(order.getCust_name(), apiResponse.getCust_name());
    assertEquals(order.getId(), apiResponse.getId());
    assertEquals(order.getTotal_price() , apiResponse.getTotal_price());
    assertEquals(order.getItems().get(0).getItem_name() , apiResponse.getItems().get(0).getItem_name());


    //Delete test data
    orderRepository.delete(orderId);
  }

  @Test
  public void testUpdateOrderDetails() throws JsonProcessingException{

    Map<String, Object> requestBody = new HashMap<String, Object>();

    Item item = new Item();
    item.setId(1);
    item.setItem_name("Espresso");
    item.setPrice((float) 1.95);
    item.setQuantity(1);
    item.setType("coffee");
    item.setSize("Tall");
    ArrayList<Item> itemList = new ArrayList<Item>();
    itemList.add(item);
    float total_price = (float) 1.95;

    Order order = new Order("Saro",itemList,total_price);
    orderRepository.save(order);

    String orderId = order.getId();

    requestBody.put("cust_name", "Saro");
    requestBody.put("total_price", "7.35");
    requestBody.put("items", itemList);

    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Creating http entity object with request body and headers
    HttpEntity<String> httpEntity =
            new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

    //Invoking the API
    Map<String, Object> apiResponse = (Map)restTemplate.exchange("http://localhost:8888/order/" + orderId,
            HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();


    assertNotNull(apiResponse);
    assertTrue(!apiResponse.isEmpty());

    //Asserting the response of the API.
    String message = apiResponse.get("message").toString();
    assertEquals("Order Updated successfully", message);

    //Fetching the Product details from DB to verify the API result
    Order orderFromDB = orderRepository.findOne(orderId);
    assertEquals(requestBody.get("cust_name"), orderFromDB.getCust_name());
    assertEquals(requestBody.get("total_price"), orderFromDB.getTotal_price().toString());

    //Delete the data added for testing
    orderRepository.delete(orderId);

  }

  @Test
  public void testDeleteOrderApi() {
    //Building the Request body data
    Map<String, Object> requestBody = new HashMap<String, Object>();


    Item item = new Item();
    item.setId(1);
    item.setItem_name("Espresso");
    item.setPrice((float) 1.95);
    item.setQuantity(1);
    item.setType("coffee");
    item.setSize("Tall");

    ArrayList<Item> itemList = new ArrayList<Item>();
    itemList.add(item);

    Order order = new Order("Saro", itemList, (float) 1.95);
    orderRepository.save(order);

    String orderId = order.getId();

    //Invoke the API to delete data
    restTemplate.delete("http://localhost:8888/order/"+ orderId, Collections.EMPTY_MAP);

    //get data from the DB directly
    Order orderFromDb = orderRepository.findOne(orderId);
    //and assert that there is no data found
    assertNull(orderFromDb);

  }
}
