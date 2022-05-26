package com.service;

import com.model.Cart;
import com.model.Cartdetail;
import com.model.CartdetailId;
import com.service.dto.CartDetailsDto;

import java.util.List;

public interface CartServices {

    public Cart createCart(Cart cart);

    public Cart getCartById(Long userId);

    public Cartdetail addItemLine(Cartdetail cartItem);

    public Boolean deleteItemLine(CartdetailId id);

    public Cartdetail findItemLineById(CartdetailId id);

    public List<CartDetailsDto> findAllLineItems(Long cartId);

    public Boolean deleteAllLineItems(Long cartId);
}
