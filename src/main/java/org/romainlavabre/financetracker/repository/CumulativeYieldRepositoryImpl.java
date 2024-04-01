package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CumulativeYieldRepositoryImpl extends AbstractRepository< CumulativeYield > implements CumulativeYieldRepository {
    public CumulativeYieldRepositoryImpl( EntityManager entityManager, JpaRepository< CumulativeYield, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< CumulativeYield > getClassType() {
        return CumulativeYield.class;
    }
}
