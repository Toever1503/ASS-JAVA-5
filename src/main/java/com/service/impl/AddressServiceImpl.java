package com.service.impl;

import com.repository.AddressRepository;
import com.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.model._Address;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.repo = addressRepository;
    }

    @Override
    public _Address findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public _Address save(_Address obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    public _Address deleteById(Long id) {
        _Address obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<_Address> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs);
    }
}
