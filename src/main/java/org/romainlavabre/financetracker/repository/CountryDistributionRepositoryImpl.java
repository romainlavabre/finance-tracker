package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.CountryDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryDistributionRepositoryImpl extends AbstractRepository< CountryDistribution > implements CountryDistributionRepository {
    public CountryDistributionRepositoryImpl( EntityManager entityManager, JpaRepository< CountryDistribution, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< CountryDistribution > getClassType() {
        return CountryDistribution.class;
    }
}
