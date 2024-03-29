package org.romainlavabre.financetracker.repository.jpa;

import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeTradedFundJpa extends JpaRepository< ExchangeTradedFund, Long > {
}
