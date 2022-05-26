package com.service.impl;

import com.model.District;
import com.repository.DistrictRepository;
import com.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repo;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.repo = districtRepository;
    }

    @Override
    public District findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    public District save(District obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    public District deleteById(Integer id) {
        District obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public List<District> findAll() {
        return repo.findAll();
    }

    @Override
    public List<District> findAllByProvince(Integer id) {
        return repo.findAllByProvinceId(id);
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs);
    }
}
