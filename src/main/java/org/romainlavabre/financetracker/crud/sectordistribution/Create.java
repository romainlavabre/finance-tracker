package org.romainlavabre.financetracker.crud.sectordistribution;

import org.romainlavabre.financetracker.entity.SectorDistribution;
import org.romainlavabre.financetracker.parameter.SectorDistributionParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.financetracker.repository.SectorDistributionRepository;
import org.romainlavabre.financetracker.repository.SectorRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createSectorDistribution" )
public class Create implements org.romainlavabre.crud.Create< SectorDistribution > {
    protected final SectorDistributionRepository countryDistributionRepository;
    protected final SectorRepository             sectorRepository;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public Create( SectorDistributionRepository countryDistributionRepository, SectorRepository sectorRepository, ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.countryDistributionRepository = countryDistributionRepository;
        this.sectorRepository              = sectorRepository;
        this.exchangeTradedFundRepository  = exchangeTradedFundRepository;
    }


    @Override
    public void create( Request request, SectorDistribution sectorDistribution ) {
        Float weight               = request.getParameter( SectorDistributionParameter.WEIGHT, Float.class );
        Long  sectorId             = request.getParameter( SectorDistributionParameter.SECTOR, Long.class );
        Long  exchangeTradedFundId = request.getParameter( SectorDistributionParameter.EXCHANGE_TRADED_FUND, Long.class );

        sectorDistribution
                .setWeight( weight )
                .setSector( sectorRepository.findOrFail( sectorId ) )
                .setExchangeTradedFund( exchangeTradedFundRepository.findOrFail( exchangeTradedFundId ) );

        countryDistributionRepository.persist( sectorDistribution );
    }
}
