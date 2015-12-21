package app.controller;

import app.model.Price;
import app.model.Product;
import app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;
  
  @RequestMapping(method = RequestMethod.POST)
  public Map<String, Object> createProduct(@RequestBody Map<String, Object> productsMap){
    Map<String, Object> priceMap = (Map) productsMap.get("price");
    System.out.println(priceMap);
    Price price = new Price(
                  Float.parseFloat( priceMap.get("tall").toString()),
                  Float.parseFloat( priceMap.get("grande").toString()),
                  Float.parseFloat( priceMap.get("venti").toString()));

    Product product = new Product(
            productsMap.get("name").toString(),
            productsMap.get("type").toString(),
            (ArrayList) productsMap.get("size"),
            price);
    
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("message", "Product created successfully");
    response.put("product", productRepository.save(product));
    return response;
  }
  
  @RequestMapping(method = RequestMethod.GET, value="/{productId}")
  public Product getproductDetails(@PathVariable("productId") String productId){
    return productRepository.findOne(productId);
  }
  
  @RequestMapping(method = RequestMethod.PUT, value="/{productId}")
  public Map<String, Object> editProduct(@PathVariable("productId") String productId,
      @RequestBody Map<String, Object> productsMap){
    Map<String, Object> priceMap = (Map) productsMap.get("price");
    Price price = new Price(
            Float.parseFloat(priceMap.get("tall").toString()),
            Float.parseFloat(priceMap.get("grande").toString()),
            Float.parseFloat(priceMap.get("venti").toString()));

    Product product = new Product(productsMap.get("name").toString(),
            productsMap.get("type").toString(),
            (ArrayList) productsMap.get("size"),
            price);
    product.setId(productId);
    
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("message", "Product Updated successfully");
    response.put("Product", productRepository.save(product));
    return response;
  }
  
  
  @RequestMapping(method = RequestMethod.DELETE, value="/{productId}")
  public Map<String, String> deleteProduct(@PathVariable("productId") String productId){
    productRepository.delete(productId);
    Map<String, String> response = new HashMap<String, String>();
    response.put("message", "Product deleted successfully");
    
    return response;
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public Map<String, Object> getAllProducts(){
    List<Product> products = productRepository.findAll();
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("totalProducts", products.size());
    response.put("products", products);
    return response;
  }
}
