package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.Util.TokenUtility;
import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.Book;
import com.bridgelabz.bookstoreapp.model.Cart;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TokenUtility tokenUtility;

    @Override
    public String addToCart(CartDTO cartDTO) {
        Optional<User> user = userRepository.findById(cartDTO.getUserId());
        Optional<Book> book = bookRepository.findById(cartDTO.getBookId());
        if(book.isPresent() && user.isPresent()){
            Cart cart = new Cart(user.get(), book.get() , cartDTO.getQuantity());
            cartRepository.save(cart);
            String token = tokenUtility.createToken(cart.getCartId());
            return token;
        }else throw new BookStoreException("User or Book does not exists");
    }

    @Override
    public String removeFromCart(int cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isPresent()) {
            cartRepository.deleteById(cartId);
            return "Your item is removed from the cart";
        }else throw new BookStoreException("Did not get any cart item with id"+ cartId);
    }

    @Override
    public String updateQuantity(int cartId, int quantity) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        Optional<Book> book = bookRepository.findById(cart.get().getBook().getBookId());
        if(cart.isPresent()){
            if(quantity<book.get().getBookQuantity()){
                cart.get().setQuantity(quantity);
                cartRepository.save(cart.get());
                book.get().setBookQuantity(book.get().getBookQuantity()-(quantity-cart.get().getQuantity()));
                bookRepository.save(book.get());
                return "cart quantity updated";
            }else throw new BookStoreException("Requested quantity is not available or out of stock");
        }else throw new BookStoreException("Cart Record doesn't exists");
    }

    @Override
    public Optional<Cart> getAllCartItemsForUser(String token) {
        int cartId = tokenUtility.decodeJWT(token);
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isPresent()){
            return cart;
        }else throw new BookStoreException("Didn't find any record for this particular cartId");
    }

    @Override
    public List<Cart> getAllCartItems() {
        List<Cart> cart = cartRepository.findAll();
        if (!cart.isEmpty()){
            return cart;
        }else throw new BookStoreException("Cart is empty");
    }
}
