package com.repository;

import com.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {
    List<District> findAllByProvinceId(Integer id);
}