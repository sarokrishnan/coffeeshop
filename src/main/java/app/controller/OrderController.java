package app.controller;

import app.model.Item;
import app.model.Order;
import app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;
  
  @RequestMapping(method = RequestMethod.POST)
  public Map<String, Object> createOrder(@RequestBody Map<String, Object> ordersMap){
    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>() ;
    itemList =  (ArrayList<Map<String, Object>>) ordersMap.get("items");
    Item item;
    ArrayList<Item> orderedItem = new ArrayList<Item>();

    for (Map<String, Object> itemMap : itemList) {
      item = new Item(
              Integer.parseInt(itemMap.get("id").toString()),
              Integer.parseInt(itemMap.get("quantity").toString()),
              itemMap.get("item_name").toString(),
              itemMap.get("type").toString(),
              itemMap.get("size").toString(),
              Float.parseFloat(itemMap.get("price").toString()));
      orderedItem.add(item);
      }


    Order order = new Order(
            ordersMap.get("cust_name").toString(),
            orderedItem,
            Float.parseFloat((String)ordersMap.get("total_price")));
    
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("message", "Order created successfully");
    response.put("order", orderRepository.save(order));
    return response;
  }
  
  @RequestMapping(method = RequestMethod.GET, value="/{orderId}")
  public Order getOrderDetails(@PathVariable("orderId") String orderId){
    return orderRepository.findOne(orderId);
  }
  
  @RequestMapping(method = RequestMethod.PUT, value="/{orderId}")
  public Map<String, Object> editOrder(@PathVariable("orderId") String orderId,
      @RequestBody Map<String, Object> ordersMap){
    List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>() ;
    itemList =  (ArrayList<Map<String, Object>>) ordersMap.get("items");
    Item item;
    ArrayList<Item> orderedItem = new ArrayList<Item>();

    for (Map<String, Object> itemMap : itemList) {
      item = new Item(
              Integer.parseInt(itemMap.get("id").toString()),
              Integer.parseInt(itemMap.get("quantity").toString()),
              itemMap.get("item_name").toString(),
              itemMap.get("type").toString(),
              itemMap.get("size").toString(),
              Float.parseFloat( itemMap.get("price").toString()));
      orderedItem.add(item);
    }


    Order order = new Order(
            ordersMap.get("cust_name").toString(),
            orderedItem,
            Float.parseFloat((String)ordersMap.get("total_price")));
    order.setId(orderId);

    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("message", "Order Updated successfully");
    response.put("order", orderRepository.save(order));
    return response;
  }
  
  
  @RequestMapping(method = RequestMethod.DELETE, value="/{orderId}")
  public Map<String, String> deleteOrder(@PathVariable("orderId") String orderId){
    orderRepository.delete(orderId);
    Map<String, String> response = new HashMap<String, String>();
    response.put("message", "Order deleted successfully");
    
    return response;
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public Map<String, Object> getAllOrders(){
    List<Order> orders = orderRepository.findAll();
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("totalorders", orders.size());
    response.put("order", orders);
    return response;
  }

  @RequestMapping(method = RequestMethod.GET, value="/filter/bysize/{size}")
  public Map<String, Object> getAllOrderBySize(@PathVariable("size") String size){
    List<Order> orders = orderRepository.findBySize(size);
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("totalorders", orders.size());
    response.put("order", orders);
    return response;
  }

  @RequestMapping(method = RequestMethod.GET, value="/filter/bytype/{type}")
  public Map<String, Object> getAllOrderByType(@PathVariable("type") String type){
    List<Order> orders = orderRepository.findByType(type);
    Map<String, Object> response = new LinkedHashMap<String, Object>();
    response.put("totalorders", orders.size());
    response.put("order", orders);
    return response;
  }
}
