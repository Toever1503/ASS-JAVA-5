package com.service.impl;

import com.model.Authority;
import com.repository.AuthorityRepository;
import com.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "authorities")
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repo;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.repo = authorityRepository;
    }

    @Override
    public Authority findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Authority save(Authority obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    public Authority deleteById(Long id) {
        Authority obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<Authority> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs);
    }
}
