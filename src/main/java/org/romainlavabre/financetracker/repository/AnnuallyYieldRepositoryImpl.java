package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.AnnuallyYield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnuallyYieldRepositoryImpl extends AbstractRepository< AnnuallyYield > implements AnnuallyYieldRepository {
    public AnnuallyYieldRepositoryImpl( EntityManager entityManager, JpaRepository< AnnuallyYield, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< AnnuallyYield > getClassType() {
        return AnnuallyYield.class;
    }
}
