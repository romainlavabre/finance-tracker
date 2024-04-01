package org.romainlavabre.financetracker.business.statistic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.romainlavabre.financetracker.business.statistic.dto.*;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StatisticBuilderImpl implements StatisticBuilder {
    protected final EntityManager                entityManager;
    protected final ResourceLoader               resourceLoader;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public StatisticBuilderImpl( EntityManager entityManager, ResourceLoader resourceLoader, ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.entityManager                = entityManager;
        this.resourceLoader               = resourceLoader;
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public Patrimony getPatrimony() {
        Query query = entityManager.createNativeQuery( getFileContent( "patrimony.sql" ), Patrimony.class );

        Patrimony patrimony = ( Patrimony ) query.getSingleResult();

        return patrimony == null
                ? new Patrimony()
                : patrimony;
    }


    @Override
    public List< RiskDistribution > getRiskDistribution() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< Byte, Float > result = new TreeMap<>();

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );

            if ( !result.containsKey( exchangeTradedFund.getRisk() ) ) {
                result.put( exchangeTradedFund.getRisk(), 0f );
            }

            result.put( exchangeTradedFund.getRisk(), result.get( exchangeTradedFund.getRisk() ) + exchangeTradedFundDistribution.weight );
        }

        List< RiskDistribution > riskDistributions = new ArrayList<>();

        for ( Map.Entry< Byte, Float > entry : result.entrySet() ) {
            riskDistributions.add( new RiskDistribution( entry.getKey(), entry.getValue() ) );
        }

        return riskDistributions;
    }


    @Override
    public List< ProviderDistribution > getProviderDistribution() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< String, Float > result = new TreeMap<>();

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );

            if ( !result.containsKey( exchangeTradedFund.getProvider().toUpperCase() ) ) {
                result.put( exchangeTradedFund.getProvider().toUpperCase(), 0f );
            }

            result.put( exchangeTradedFund.getProvider().toUpperCase(), result.get( exchangeTradedFund.getProvider().toUpperCase() ) + exchangeTradedFundDistribution.weight );
        }

        List< ProviderDistribution > providerDistributions = new ArrayList<>();

        for ( Map.Entry< String, Float > entry : result.entrySet() ) {
            providerDistributions.add( new ProviderDistribution( entry.getKey(), entry.getValue() ) );
        }

        return providerDistributions;
    }


    @Override
    public List< ExchangeTradedFundDistribution > getExchangeTradedFundDistribution() {
        List< ExchangeTradedFundDistribution > result = new ArrayList<>();

        float patrimony = getPatrimony().total;

        List< ExchangeTradedFund > exchangeTradedFunds = exchangeTradedFundRepository.findAll();


        for ( ExchangeTradedFund exchangeTradedFund : exchangeTradedFunds ) {
            result.add(
                    new ExchangeTradedFundDistribution(
                            exchangeTradedFund.getId(),
                            exchangeTradedFund.getPublicName(),
                            ( exchangeTradedFund.getAmount() * 100f / patrimony )
                    )
            );
        }

        return result;
    }


    @Override
    public List< CountryDistribution > getCountryDistribution() {
        return null;
    }


    @Override
    public List< ContinentDistribution > getContinentDistribution() {
        return null;
    }


    @Override
    public List< SectorDistribution > getSectorDistribution() {
        return null;
    }


    @Override
    public List< PastPerformanceAggregateByYear > getPastPerformanceAggregateByYear() {
        return null;
    }


    @Override
    public AveragePricing getAveragePricing() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        float pricing = 0;


        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );
            pricing += exchangeTradedFund.getPricing() * exchangeTradedFundDistribution.weight;
        }

        return new AveragePricing( pricing / 100 );
    }


    protected String getFileContent( String filename ) {
        Resource resource = resourceLoader.getResource( "classpath:sql/" + filename );


        try {
            StringBuilder textBuilder = new StringBuilder();
            try ( Reader reader = new BufferedReader( new InputStreamReader( resource.getInputStream(), Charset.forName( StandardCharsets.UTF_8.name() ) ) ) ) {
                int c;
                while ( ( c = reader.read() ) != -1 ) {
                    textBuilder.append( ( char ) c );
                }
            }

            return textBuilder.toString();
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new HttpInternalServerErrorException( "INTERNAL_SERVER_ERROR" );
        }
    }
}
