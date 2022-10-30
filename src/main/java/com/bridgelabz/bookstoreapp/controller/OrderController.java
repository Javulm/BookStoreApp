package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.Order;
import com.bridgelabz.bookstoreapp.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/placeorder")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestHeader String token, @RequestBody OrderDTO orderDTO){
        String order= orderService.placeOrder(token, orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("Order placed", order);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/getallbyuser")
    public ResponseEntity<ResponseDTO> getAllOrders(@RequestHeader String token){
        List<Order> order= orderService.getAllOrdersByUserId(token);
        ResponseDTO responseDTO = new ResponseDTO("order details", order);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllOrders(@RequestHeader boolean cancel){
        List<Order> order= orderService.getAllOrders(cancel);
        ResponseDTO responseDTO = new ResponseDTO("order details", order);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<ResponseDTO> cancelOrder(@RequestHeader String token, @RequestHeader int orderId){
        String order= orderService.cancelOrder(token,orderId);
        ResponseDTO responseDTO = new ResponseDTO("order details", order);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
