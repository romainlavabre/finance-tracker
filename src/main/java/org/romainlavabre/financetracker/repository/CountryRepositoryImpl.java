package org.romainlavabre.financetracker.repository;

import jakarta.persistence.EntityManager;
import org.romainlavabre.financetracker.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryRepositoryImpl extends AbstractRepository< Country > implements CountryRepository {
    
    public CountryRepositoryImpl( EntityManager entityManager, JpaRepository< Country, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< Country > getClassType() {
        return Country.class;
    }
}
