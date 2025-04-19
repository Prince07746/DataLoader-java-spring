package org.dataloader.Components;

import org.dataloader.Service.OrderService;
import org.dataloader.Service.ProductService;
import org.dataloader.Service.UserService;
import org.dataloader.model.Order;
import org.dataloader.model.Product;
import org.dataloader.model.User;
import org.dataloader.security.IdPattern;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public DataLoader(ProductService productService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    public static String generateValidId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 24);
    }

    @Override
    public void run(String... args) {

        productService.deleteAllProduct();
        userService.deleteAllUsers();
        orderService.deleteAllOrders();

        loadProducts();
        loadUsersAndOrders();

        readData();
    }

    private void loadProducts() {
        List<Product> products = Arrays.asList(

                new Product(new IdPattern(generateValidId()).getId(), "USB Flash", 20.00),
                new Product(new IdPattern(generateValidId()).getId(), "Keyboard", 45.00),
                new Product(new IdPattern(generateValidId()).getId(), "Mouse", 25.00),
                new Product(new IdPattern(generateValidId()).getId(), "External HDD", 120.00),
                new Product(new IdPattern(generateValidId()).getId(), "Monitor", 250.00)
        );

        products.forEach(productService::addProduct);
        System.out.println("Products added successfully!");
    }

    private void loadUsersAndOrders() {
        List<User> users = Arrays.asList(
                new User(new IdPattern(generateValidId()).getId(), "Prince", "Matongo", "prince@example.com", LocalDate.of(2004, 1, 1)),
                new User(new IdPattern(generateValidId()).getId(), "Merino", "R", "merino@example.com", LocalDate.of(1998, 7, 12)),
                new User(new IdPattern(generateValidId()).getId(), "Daniele", "B", "daniele@example.com", LocalDate.of(1995, 3, 22)),
                new User(new IdPattern(generateValidId()).getId(), "Laura", "V", "laura@example.com", LocalDate.of(1999, 6, 30)),
                new User(new IdPattern(generateValidId()).getId(), "Marco", "N", "marco@example.com", LocalDate.of(1992, 11, 8))
        );

        users.forEach(userService::addUser);
        System.out.println("Users added successfully!");

        for (User user : users) {
            List<Order> orders = Arrays.asList(
                    new Order(new IdPattern(generateValidId()).getId(), user.getName(), 100.00 + Math.random() * 200, LocalDate.now()),
                    new Order(new IdPattern(generateValidId()).getId(), user.getName(), 150.00 + Math.random() * 250, LocalDate.now().minusDays(3))
            );
            orders.forEach(orderService::addOrder);
        }

        System.out.println("Orders added successfully!");
    }


    public void readData() {

        System.out.println("User List\n-------------");
        for (User user : userService.getUserList()) {
            System.out.println(user.toString());
        }
        ;

        System.out.println("---------------------------------");

        System.out.println("Product List\n-------------");
        for (Product product : productService.getProductList()) {
            System.out.println(product.toString());
        }
        ;

        System.out.println("---------------------------------");

        System.out.println("Order List\n-------------");
        for (Order order : orderService.getOrderList()) {
            System.out.println(order.toString());
        }
        ;

    }

}
