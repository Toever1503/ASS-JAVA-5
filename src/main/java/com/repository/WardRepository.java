package com.repository;

import com.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer>, JpaSpecificationExecutor<Ward> {

    List<Ward> findAllByDistrictId(Integer id);
}