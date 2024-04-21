@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}

@Autowired
private OrderMapper orderMapper;
@Autowired
private RestTemplate restTemplate;

public Order queryOrderById(Long orderId) {
    Order order = orderMapper.findById(orderId);
    String url = "http://localhost:8081/user/" + order.getUserId();
    User user = restTemplate.getForObject(url, User.class);
    order.setUser(user);
    return order;
}