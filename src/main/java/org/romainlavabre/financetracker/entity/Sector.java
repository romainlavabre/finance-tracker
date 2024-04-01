package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.response.Message;

@Entity
public class Sector {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    @Column( nullable = false )
    protected String name;


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Sector setName( String name ) {
        if ( name == null || name.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.SECTOR_NAME_REQUIRED );
        }
        
        this.name = name;

        return this;
    }
}
