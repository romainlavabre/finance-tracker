package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ExchangeTradedFundRepositoryImpl extends AbstractRepository< ExchangeTradedFund > implements ExchangeTradedFundRepository {
    public ExchangeTradedFundRepositoryImpl( EntityManager entityManager, JpaRepository< ExchangeTradedFund, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< ExchangeTradedFund > getClassType() {
        return ExchangeTradedFund.class;
    }
}
