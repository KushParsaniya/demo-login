package dev.kush.security2.controller;

import dev.kush.security2.models.CustomerDto;
import dev.kush.security2.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {


    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/secured")
    public String security() {
        return "security";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody CustomerDto customerDto) {
        return customerService.signUp(customerDto);
    }

    @GetMapping("/conformation/")
    public String enableUser(@RequestParam String token) {
        return customerService.confirmToken(token);
    }
}
