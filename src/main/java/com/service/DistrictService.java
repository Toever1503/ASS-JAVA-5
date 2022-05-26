package com.service;

import com.model.District;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DistrictService {
    District findById(Integer id);

    District save(District obj);

    District deleteById(Integer id);

    List<District> findAll();

    List<District> findAllByProvince(Integer id);

    Long count(Specification specs);
}
