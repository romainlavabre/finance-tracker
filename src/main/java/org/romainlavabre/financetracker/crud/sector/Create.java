package org.romainlavabre.financetracker.crud.sector;

import org.romainlavabre.financetracker.entity.Sector;
import org.romainlavabre.financetracker.parameter.SectorParameter;
import org.romainlavabre.financetracker.repository.SectorRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createSector" )
public class Create implements org.romainlavabre.crud.Create< Sector > {
    protected final SectorRepository sectorRepository;


    public Create( SectorRepository sectorRepository ) {
        this.sectorRepository = sectorRepository;
    }


    @Override
    public void create( Request request, Sector sector ) {
        String name = request.getParameter( SectorParameter.NAME, String.class );

        sector.setName( name );

        sectorRepository.persist( sector );
    }
}
