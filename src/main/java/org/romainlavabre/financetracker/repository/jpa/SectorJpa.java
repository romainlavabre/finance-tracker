package org.romainlavabre.financetracker.repository.jpa;

import org.romainlavabre.financetracker.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorJpa extends JpaRepository< Sector, Long > {
}
