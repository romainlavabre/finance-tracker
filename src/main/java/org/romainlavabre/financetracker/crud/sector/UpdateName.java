package org.romainlavabre.financetracker.crud.sector;

import org.romainlavabre.financetracker.entity.Sector;
import org.romainlavabre.financetracker.parameter.SectorParameter;
import org.romainlavabre.financetracker.repository.SectorRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateSectorName" )
public class UpdateName implements org.romainlavabre.crud.Update< Sector > {
    protected final SectorRepository sectorRepository;


    public UpdateName( SectorRepository sectorRepository ) {
        this.sectorRepository = sectorRepository;
    }


    @Override
    public void update( Request request, Sector sector ) {
        String name = request.getParameter( SectorParameter.NAME, String.class );

        sector.setName( name );

        sectorRepository.persist( sector );
    }
}
