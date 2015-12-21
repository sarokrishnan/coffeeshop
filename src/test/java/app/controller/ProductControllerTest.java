package app.controller;

import app.Application;
import app.model.Price;
import app.model.Product;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class ProductControllerTest {

  //To Generate JSON content from Java objects
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  
  //Delete test data after test.
  //Invoke the APIs interacting with the DB
  @Autowired
  private ProductRepository productRepository;
  
  //Use RestTemplate to invoke the APIs.
  private RestTemplate restTemplate = new TestRestTemplate();
  
  @Test
  public void testCreateProductApi() throws JsonProcessingException{
    
    //Building the Request body data
    Map<String, Object> requestBody = new HashMap<String, Object>();
    Price price = new Price();
    price.setGrande((float) 2.05);
    price.setTall((float) 1.95);
    price.setVenti((float) 2.35);
    ArrayList size = new ArrayList();
    size.add("Tall");
    size.add("Grande");
    size.add("Venti");
    requestBody.put("name", "Espresso");
    requestBody.put("type", "Coffee");
    requestBody.put("size", (Object) size);
    requestBody.put("price",  (Object) price);


    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Create http entity object with request body / headers
    HttpEntity<String> httpEntity = 
        new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
    
    //Invoking the API
    Map<String, Object> apiResponse = 
        restTemplate.postForObject("http://localhost:8888/product", httpEntity, Map.class, Collections.EMPTY_MAP);

    assertNotNull(apiResponse);
    System.out.println(apiResponse);
    //Asserting the response of the API.
    String message = apiResponse.get("message").toString();
    System.out.println(message);
    assertEquals("Product created successfully", message);
    String productId = ((Map<String, Object>)apiResponse.get("product")).get("id").toString();
    
    assertNotNull(productId);

    //Delete data
    productRepository.delete(productId);

  }

  @Test
  public void testGetProductDetailsApi(){

    Price price = new Price();
    price.setGrande((float) 2.05);
    price.setTall((float) 1.95);
    price.setVenti((float) 2.35);
    ArrayList size = new ArrayList();
    size.add("Tall");
    size.add("Grande");
    size.add("Venti");


    Product product = new Product("Espresso","Coffee",size,price);
    productRepository.save(product);

    String productId = product.getId();

    //call api
    Product apiResponse = restTemplate.getForObject("http://localhost:8888/product/"+ productId, Product.class);

    //Verify that the data from the API and data saved in the DB are same
    assertNotNull(apiResponse);
    assertEquals(product.getName(), apiResponse.getName());
    assertEquals(product.getId(), apiResponse.getId());
    assertEquals(product.getType() , apiResponse.getType());
    //Delete test data
    productRepository.delete(productId);
  }

  @Test
  public void testUpdateProductDetails() throws JsonProcessingException{
    //Create a new Product using the ProductRepository API
    Price price = new Price();
    price.setGrande((float) 2.05);
    price.setTall((float) 1.95);
    price.setVenti((float) 2.35);
    ArrayList size = new ArrayList();
    size.add("Tall");
    size.add("Grande");
    size.add("Venti");

    //New product using the ProductRepository API
    Product product = new Product("Espresso","Coffee",size,price);
    productRepository.save(product);

    String productId = product.getId();


    Map<String, Object> requestBody = new HashMap<String, Object>();
    requestBody.put("name", "Latte");
    requestBody.put("type", "Coffee");
    requestBody.put("size", (Object) size);
    requestBody.put("price",  (Object) price);
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Creating http entity object with request body and headers
    HttpEntity<String> httpEntity =
            new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

    //Invoking the API
    Map<String, Object> apiResponse = (Map)restTemplate.exchange("http://localhost:8888/product/" + productId,
            HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();


    assertNotNull(apiResponse);
    assertTrue(!apiResponse.isEmpty());

    //Asserting the response of the API.
    String message = apiResponse.get("message").toString();
    assertEquals("Product Updated successfully", message);

    //Fetching the Product details from DB to verify the API result
    Product productFromDb = productRepository.findOne(productId);
    assertEquals(requestBody.get("name"), productFromDb.getName());
    assertEquals(requestBody.get("type"), productFromDb.getType());

    //Delete the data added for testing
    productRepository.delete(productId);

  }

  @Test
  public void testDeleteProductApi(){
    //New product using the ProuctRepository API
    Price price = new Price();
    price.setGrande((float) 2.05);
    price.setTall((float) 1.95);
    price.setVenti((float) 2.35);
    ArrayList size = new ArrayList();
    size.add("Tall");
    size.add("Grande");
    size.add("Venti");

    //New product using the ProductRepository API
    Product product = new Product("Espresso","Coffee",size,price);
    productRepository.save(product);

    String productId = product.getId();

    //Invoke the API to delete data
    restTemplate.delete("http://localhost:8888/product/"+ productId, Collections.EMPTY_MAP);

    //get data from the DB directly
    Product productFromDb = productRepository.findOne(productId);
    //and assert that there is no data found
    assertNull(productFromDb);
  }
}
