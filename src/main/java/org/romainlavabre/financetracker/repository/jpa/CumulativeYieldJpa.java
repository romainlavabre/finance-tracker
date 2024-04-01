package org.romainlavabre.financetracker.repository.jpa;

import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CumulativeYieldJpa extends JpaRepository< CumulativeYield, Long > {
}
