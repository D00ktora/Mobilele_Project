package org.softuni.mobilele.repository;

import org.softuni.mobilele.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<List<Offer>> getAllBy();
}
