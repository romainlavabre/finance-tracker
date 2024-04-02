package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.response.Message;

@Entity
public class Country {

    public static final byte CONTINENT_EUROPE    = 0;
    public static final byte CONTINENT_AFRICA    = 1;
    public static final byte CONTINENT_ASIA      = 2;
    public static final byte CONTINENT_AUSTRALIA = 3;
    public static final byte CONTINENT_AMERICA   = 4;

    @Json( groups = @Group )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    @Json( groups = @Group )
    @Column( nullable = false )
    protected String name;

    @Json( groups = @Group )
    protected byte continent;


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Country setName( String name ) {
        if ( name == null || name.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.COUNTRY_NAME_REQUIRED );
        }

        this.name = name.toUpperCase();

        return this;
    }


    public byte getContinent() {
        return continent;
    }


    public Country setContinent( Byte continent ) {
        if ( continent == null ) {
            throw new HttpUnprocessableEntityException( Message.COUNTRY_CONTINENT_REQUIRED );
        }

        if ( continent != CONTINENT_EUROPE
                && continent != CONTINENT_AFRICA
                && continent != CONTINENT_ASIA
                && continent != CONTINENT_AUSTRALIA
                && continent != CONTINENT_AMERICA ) {
            throw new HttpUnprocessableEntityException( Message.COUNTRY_CONTINENT_INVALID );
        }

        this.continent = continent;

        return this;
    }


    public String getContinentAsString() {
        switch ( continent ) {
            case CONTINENT_EUROPE -> {
                return "Europe";
            }
            case CONTINENT_AFRICA -> {
                return "Africa";
            }
            case CONTINENT_ASIA -> {
                return "Asia";
            }
            case CONTINENT_AUSTRALIA -> {
                return "Australia";
            }
            case CONTINENT_AMERICA -> {
                return "America";
            }
        }

        return null;
    }
}
