package com.service.impl;

import com.model.Cart;
import com.model.Cartdetail;
import com.model.CartdetailId;
import com.repository.CartRepository;
import com.repository.CartdetailRepository;
import com.service.CartServices;
import com.service.dto.CartDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServicesImpl implements CartServices {
    @Autowired
    public CartdetailRepository cartdetailRepository;

    @Autowired
    public CartRepository cartRepository;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public Cartdetail addItemLine(Cartdetail cartItem) {
        return cartdetailRepository.save(cartItem);
    }

    @Override
    @Transactional
    public Boolean deleteItemLine(CartdetailId id) {
        cartdetailRepository.deleteById(id);
        return true;
    }

    @Override
    public Cartdetail findItemLineById(CartdetailId id) {
        return cartdetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<CartDetailsDto> findAllLineItems(Long cartId) {
        List<Cartdetail> cartdetails = cartdetailRepository.findAllByIdCartId(cartId);

        List<CartDetailsDto> cartDetailsDtos = new ArrayList<>();
        cartdetails.forEach(item -> {
            CartDetailsDto cartDetailsDto = new CartDetailsDto();
            cartDetailsDto.setProduct(item.getId().getProductId());
            cartDetailsDto.setQuantity(item.getQuantity());
            cartDetailsDtos.add(cartDetailsDto);
        });
        return cartDetailsDtos;
    }

    @Override
    @Transactional
    public Boolean deleteAllLineItems(Long cartId) {
        cartdetailRepository.deleteAllById_CartId(cartId);
        return true;
    }
}
