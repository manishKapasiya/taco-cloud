package com.example.tacocloud.controller;

import com.example.tacocloud.domain.Order;
import com.example.tacocloud.domain.User;
import com.example.tacocloud.repository.OrderRepository;
import com.example.tacocloud.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/current")
    public String orderForm(Model model) {
        //model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: " + order);

        //User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        //User user = (User) authentication.getPrincipal();
        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
