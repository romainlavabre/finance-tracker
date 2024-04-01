package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.SectorDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SectorDistributionRepositoryImpl extends AbstractRepository< SectorDistribution > implements SectorDistributionRepository {
    public SectorDistributionRepositoryImpl( EntityManager entityManager, JpaRepository< SectorDistribution, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< SectorDistribution > getClassType() {
        return SectorDistribution.class;
    }
}
