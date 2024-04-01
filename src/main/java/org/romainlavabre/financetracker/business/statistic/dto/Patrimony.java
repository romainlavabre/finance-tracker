package org.romainlavabre.financetracker.business.statistic.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

@Entity
@Immutable
@Table( name = "statistic" )
public class Patrimony {

    @Id
    protected long id;

    @Json( groups = @Group )
    public float total;
}
