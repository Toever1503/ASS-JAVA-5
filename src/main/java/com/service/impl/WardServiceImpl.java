package com.service.impl;

import com.model.Ward;
import com.repository.WardRepository;
import com.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WardServiceImpl implements WardService {

    private final WardRepository repo;

    @Autowired
    public WardServiceImpl(WardRepository wardRepository) {
        this.repo = wardRepository;
    }

    @Override
    public Ward findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override

    public Ward save(Ward obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    public Ward deleteById(Integer id) {
        Ward obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Ward> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Ward> findAllByDistrict(Integer id) {
        return repo.findAllByDistrictId(id);
    }

    @Override
    public Long count(Specification specs) {
        System.out.println(repo.count(specs) / 30);
        return repo.count(specs);
    }
}
