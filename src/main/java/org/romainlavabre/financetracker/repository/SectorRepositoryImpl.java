package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SectorRepositoryImpl extends AbstractRepository< Sector > implements SectorRepository {
    public SectorRepositoryImpl( EntityManager entityManager, JpaRepository< Sector, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< Sector > getClassType() {
        return Sector.class;
    }
}
