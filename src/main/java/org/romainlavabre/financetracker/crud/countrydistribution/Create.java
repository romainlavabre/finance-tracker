package org.romainlavabre.financetracker.crud.countrydistribution;

import org.romainlavabre.financetracker.entity.CountryDistribution;
import org.romainlavabre.financetracker.parameter.CountryDistributionParameter;
import org.romainlavabre.financetracker.repository.CountryDistributionRepository;
import org.romainlavabre.financetracker.repository.CountryRepository;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createCountryDistribution" )
public class Create implements org.romainlavabre.crud.Create< CountryDistribution > {
    protected final CountryDistributionRepository countryDistributionRepository;
    protected final CountryRepository             countryRepository;
    protected final ExchangeTradedFundRepository  exchangeTradedFundRepository;


    public Create( CountryDistributionRepository countryDistributionRepository, CountryRepository countryRepository, ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.countryDistributionRepository = countryDistributionRepository;
        this.countryRepository             = countryRepository;
        this.exchangeTradedFundRepository  = exchangeTradedFundRepository;
    }


    @Override
    public void create( Request request, CountryDistribution countryDistribution ) {
        Float weight               = request.getParameter( CountryDistributionParameter.WEIGHT, Float.class );
        Long  countryId            = request.getParameter( CountryDistributionParameter.COUNTRY, Long.class );
        Long  exchangeTradedFundId = request.getParameter( CountryDistributionParameter.EXCHANGE_TRADED_FUND, Long.class );

        countryDistribution
                .setWeight( weight )
                .setCountry( countryRepository.findOrFail( countryId ) )
                .setExchangeTradedFund( exchangeTradedFundRepository.findOrFail( exchangeTradedFundId ) );

        countryDistributionRepository.persist( countryDistribution );
    }
}
