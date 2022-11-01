package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.Util.EmailSenderService;
import com.bridgelabz.bookstoreapp.Util.TokenUtility;
import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.Book;
import com.bridgelabz.bookstoreapp.model.Cart;
import com.bridgelabz.bookstoreapp.model.Order;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBookService bookService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private TokenUtility tokenUtility;

    @Override
    public String placeOrder(String token, int cartId, OrderDTO orderDTO) {
        User user = userService.getByToken(token);
        Optional<Cart> cart = cartService.getCartByCartId(cartId);
        Book bookFromCart = cart.get().getBook();
        Book bookFromBook = bookService.findBookById(cart.get().getBook().getBookId());
        double orderPrice = cart.get().getQuantity() * bookFromBook.getBookPrice();
        Order newOrder = new Order(orderPrice, cart.get().getQuantity(), orderDTO.getAddress(), user, bookFromCart, orderDTO.isCancel());
        if (bookFromCart != null && user != null) {
            if (cart.get().getQuantity() <= bookFromBook.getBookQuantity()) {
                orderRepository.save(newOrder);
                cartService.removeFromCart(cart.get().getCartId());
                bookFromBook.setBookQuantity(bookFromBook.getBookQuantity() - cart.get().getQuantity());
                bookRepository.save(bookFromBook);
                emailSenderService.sendEmail(user.getEmail(), "Order confirmation Mail", "Your Order is successfully placed.\n Order details\n"
                        + "Book Name :" + newOrder.getBook().getBookName() + "\n"
                        + "Book Description :" + newOrder.getBook().getBookDescription() + "\n"
                        + "Book Price :" + newOrder.getBook().getBookPrice() + "\n"
                        + "Order Quantity :" + cart.get().getQuantity()
                        + "\n" + "Order Price :" + orderPrice);
                return "Your Order is placed";
            } else throw new BookStoreException("Requested quantity is not available");
        } else throw new BookStoreException("Book or User doesn't exists");
    }

    @Override
    public List<Order> getAllOrdersByUserId(String token) {
        User user = userService.getByToken(token);
        if (user != null) {
            int userId = tokenUtility.decodeJWT(token);
            return orderRepository.findAllByUserId(userId);
        } else throw new BookStoreException("Orders for userId not found");
    }

    @Override
    public String cancelOrder(String token, int orderId) {
        User user = userService.getByToken(token);
        if (user != null) {
            Optional<Order> order = orderRepository.findById(orderId);
            Optional<Book> book = bookRepository.findById(order.get().getBook().getBookId());
            if (order.isPresent()) {
                order.get().setCancel(true);
                orderRepository.save(order.get());
                book.get().setBookQuantity(order.get().getQuantity() + book.get().getBookQuantity());
                bookRepository.save(book.get());
                emailSenderService.sendEmail(user.getEmail(), "Order details", "your order is cancelled");
                return "Order cancelled";
            } else throw new BookStoreException("Order details not found");
        } else throw new BookStoreException("User does not exists");
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }
}
