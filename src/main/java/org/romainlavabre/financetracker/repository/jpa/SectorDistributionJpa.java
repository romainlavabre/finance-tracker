package org.romainlavabre.financetracker.repository.jpa;

import org.romainlavabre.financetracker.entity.SectorDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorDistributionJpa extends JpaRepository< SectorDistribution, Long > {
}
