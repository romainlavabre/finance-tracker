package org.romainlavabre.financetracker.repository.jpa;

import org.romainlavabre.financetracker.entity.CountryDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDistributionJpa extends JpaRepository< CountryDistribution, Long > {
}
