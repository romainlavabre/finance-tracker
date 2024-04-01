package org.romainlavabre.financetracker.repository.jpa;

import org.romainlavabre.financetracker.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryJpa extends JpaRepository< Country, Long > {
}
