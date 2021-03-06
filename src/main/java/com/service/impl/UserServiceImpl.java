package com.service.impl;

import com.model.Authority;
import com.model.Cart;
import com.model.User;
import com.repository.AuthorityRepository;
import com.repository.UserRepository;
import com.repository.specification.UserSpecifications;
import com.service.CartServices;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repo;

    private final AuthorityRepository authorityRepository;

    private final CartServices cartServices;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, CartServices cartServices) {
        this.repo = userRepository;
        this.authorityRepository = authorityRepository;
        this.cartServices = cartServices;
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public User save(User obj) {
        Set<Authority> auths = new HashSet<Authority>(); // make new auth list


        if (obj.getUserAuths() != null) { //walk through list
            obj.getUserAuths().forEach(auth -> {
                if (auth.getId() != null) {
                    // here we get reference to authority entity by id
                    auths.add(authorityRepository.findById(auth.getId()).get());
                }
            });
        }
        obj.setUserAuths(auths); // set auth agains

        obj = repo.save(obj);
        if (obj.getUserCart() == null) {
            Cart cart = new Cart();
            cart.setCartUser(obj);
            cartServices.createCart(cart);
        }
        return obj;
    }

    @Override
    public User deleteById(Long id) {
        User obj = findById(id); // fetch user by id
        obj.setUserAuths(null); // set null for auths, if not set, jpa will clear all 3 tables
        repo.delete(obj); // delete
        return obj;
    }

    @Override
    public List<User> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        System.out.println(repo.count(specs) / 30);
        return repo.count(specs);
    }

    @Override
    public List<User> searchByUser(User u) {
        return repo.findAll(Specification
                .where(UserSpecifications.byUsername(u.getUsername()))
                .or(UserSpecifications.byEmail(u.getEmail()))).stream().toList();
    }

    @Override
    public User findByEmailOrUsername(String email, String username) {
        // TODO Auto-generated method stub
        return repo.findByEmailOrUsername(email, username).orElse(null);
    }

    @Override
    public List<User> findAllByEmailOrUsername(String email, String username) {
        return repo.findAllByEmailOrUsername(email, username);
    }

    @Override
    public User findByUserName(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return repo.findByEmailEquals(email).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = findByUserName(username);
        if (u == null) {
            throw new UsernameNotFoundException("NOT FOUND USERS");
        }
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(),
                u.getUserAuths().stream().map(authority -> (GrantedAuthority) () -> authority.getName()).collect(Collectors.toList())
        );
    }
}
