package com.service;

import com.model.Ward;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface WardService {

    Ward findById(Integer id);

    Ward save(Ward obj);

    Ward deleteById(Integer id);

    List<Ward> findAll();

    List<Ward> findAllByDistrict(Integer id);

    Long count(Specification specs);
}
