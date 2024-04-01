package org.romainlavabre.financetracker.crud.sectordistribution;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.SectorDistribution;
import org.romainlavabre.financetracker.parameter.SectorDistributionParameter;
import org.romainlavabre.financetracker.repository.SectorDistributionRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateSectorDistributionWeight" )
public class UpdateWeight implements Update< SectorDistribution > {

    protected final SectorDistributionRepository sectorDistributionRepository;


    public UpdateWeight( SectorDistributionRepository sectorDistributionRepository ) {
        this.sectorDistributionRepository = sectorDistributionRepository;
    }


    @Override
    public void update( Request request, SectorDistribution sectorDistribution ) {
        Float weight = request.getParameter( SectorDistributionParameter.WEIGHT, Float.class );

        sectorDistribution.setWeight( weight );

        sectorDistributionRepository.persist( sectorDistribution );
    }
}
