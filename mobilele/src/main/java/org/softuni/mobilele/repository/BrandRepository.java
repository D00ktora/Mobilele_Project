package org.softuni.mobilele.repository;

import org.softuni.mobilele.model.dto.BrandsDTO;
import org.softuni.mobilele.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> getAllBy();
}
