package org.romainlavabre.financetracker.business.statistic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.romainlavabre.financetracker.business.statistic.dto.*;
import org.romainlavabre.financetracker.entity.AnnuallyYield;
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
import java.util.*;

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
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< String, Float > value = new HashMap<>();

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );

            for ( org.romainlavabre.financetracker.entity.CountryDistribution countryDistribution : exchangeTradedFund.getCountryDistributions() ) {
                String countryName = countryDistribution.getCountry().getName();

                if ( !value.containsKey( countryName ) ) {
                    value.put( countryName, 0f );
                }

                value.put( countryName, value.get( countryName ) + ( countryDistribution.getWeight() * exchangeTradedFundDistribution.weight ) );
            }
        }

        List< CountryDistribution > countryDistributions = new ArrayList<>();
        float                       total                = 0;

        for ( Map.Entry< String, Float > entry : value.entrySet() ) {
            total += entry.getValue() / 100;

            countryDistributions.add( new CountryDistribution( entry.getKey(), entry.getValue() / 100 ) );
        }

        if ( total > 0 ) {
            countryDistributions.add( new CountryDistribution( "UNKNOWN", 100 - total ) );
        }

        return countryDistributions;
    }


    @Override
    public List< ContinentDistribution > getContinentDistribution() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< String, Float > value = new HashMap<>();

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );

            for ( org.romainlavabre.financetracker.entity.CountryDistribution countryDistribution : exchangeTradedFund.getCountryDistributions() ) {
                String continentName = countryDistribution.getCountry().getContinentAsString();

                if ( !value.containsKey( continentName ) ) {
                    value.put( continentName, 0f );
                }

                value.put( continentName, value.get( continentName ) + ( countryDistribution.getWeight() * exchangeTradedFundDistribution.weight ) );
            }
        }

        List< ContinentDistribution > continentDistributions = new ArrayList<>();
        float                         total                  = 0;

        for ( Map.Entry< String, Float > entry : value.entrySet() ) {
            total += entry.getValue() / 100;
            continentDistributions.add( new ContinentDistribution( entry.getKey(), entry.getValue() / 100 ) );
        }

        if ( total > 0 ) {
            continentDistributions.add( new ContinentDistribution( "UNKNOWN", 100 - total ) );
        }

        return continentDistributions;
    }


    @Override
    public List< SectorDistribution > getSectorDistribution() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< String, Float > value = new HashMap<>();

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );

            for ( org.romainlavabre.financetracker.entity.SectorDistribution sectorDistribution : exchangeTradedFund.getSectorDistributions() ) {
                String sectorName = sectorDistribution.getSector().getName();

                if ( !value.containsKey( sectorName ) ) {
                    value.put( sectorName, 0f );
                }

                value.put( sectorName, value.get( sectorName ) + ( sectorDistribution.getWeight() * exchangeTradedFundDistribution.weight ) );
            }
        }

        List< SectorDistribution > sectorDistributions = new ArrayList<>();
        float                      total               = 0;

        for ( Map.Entry< String, Float > entry : value.entrySet() ) {
            total += entry.getValue() / 100;

            sectorDistributions.add( new SectorDistribution( entry.getKey(), entry.getValue() / 100 ) );
        }

        if ( total > 0 ) {
            sectorDistributions.add( new SectorDistribution( "UNKNOWN", 100 - total ) );
        }

        return sectorDistributions;
    }


    @Override
    public List< PastPerformanceAggregateByYear > getPastPerformanceAggregateByYear() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< Short, Float > value       = new HashMap<>();
        Map< Short, Float > coefficient = new HashMap<>();

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );

            for ( AnnuallyYield annuallyYield : exchangeTradedFund.getAnnuallyYields() ) {
                short year = annuallyYield.getYear();

                if ( !value.containsKey( year ) ) {
                    value.put( year, 0f );
                }

                if ( !coefficient.containsKey( year ) ) {
                    coefficient.put( year, 0f );
                }

                value.put( year, value.get( year ) + ( annuallyYield.getYield() * exchangeTradedFundDistribution.weight ) );
                coefficient.put( year, coefficient.get( year ) + exchangeTradedFundDistribution.weight );
            }
        }

        List< PastPerformanceAggregateByYear > pastPerformanceAggregateByYears = new ArrayList<>();

        for ( Map.Entry< Short, Float > entry : value.entrySet() ) {
            pastPerformanceAggregateByYears.add( new PastPerformanceAggregateByYear( entry.getKey(), entry.getValue() / coefficient.get( entry.getKey() ) ) );
        }

        return pastPerformanceAggregateByYears;
    }


    @Override
    public CumulativeYield getCumulativeYield() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistributions = getExchangeTradedFundDistribution();

        Map< String, Float > value = new HashMap<>();
        value.put( "to_ten_year", 0f );
        value.put( "to_five_year", 0f );
        value.put( "to_three_year", 0f );
        value.put( "to_one_year", 0f );
        value.put( "since_creation", 0f );

        Map< String, Float > coefficient = new HashMap<>();
        coefficient.put( "to_ten_year", 0f );
        coefficient.put( "to_five_year", 0f );
        coefficient.put( "to_three_year", 0f );
        coefficient.put( "to_one_year", 0f );
        coefficient.put( "since_creation", 0f );

        for ( ExchangeTradedFundDistribution exchangeTradedFundDistribution : exchangeTradedFundDistributions ) {
            ExchangeTradedFund                                      exchangeTradedFund = exchangeTradedFundRepository.findOrFail( exchangeTradedFundDistribution.id );
            org.romainlavabre.financetracker.entity.CumulativeYield cumulativeYield    = exchangeTradedFund.getCumulativeYield();

            if ( cumulativeYield == null ) {
                continue;
            }

            if ( cumulativeYield.getToTenYear() != null ) {
                value.put( "to_ten_year", value.get( "to_ten_year" ) + ( cumulativeYield.getToTenYear() * exchangeTradedFundDistribution.weight ) );
                coefficient.put( "to_ten_year", coefficient.get( "to_ten_year" ) + exchangeTradedFundDistribution.weight );
            }

            if ( cumulativeYield.getToFiveYear() != null ) {
                value.put( "to_five_year", value.get( "to_five_year" ) + ( cumulativeYield.getToFiveYear() * exchangeTradedFundDistribution.weight ) );
                coefficient.put( "to_five_year", coefficient.get( "to_five_year" ) + exchangeTradedFundDistribution.weight );
            }

            if ( cumulativeYield.getToThreeYear() != null ) {
                value.put( "to_three_year", value.get( "to_three_year" ) + ( cumulativeYield.getToThreeYear() * exchangeTradedFundDistribution.weight ) );
                coefficient.put( "to_three_year", coefficient.get( "to_three_year" ) + exchangeTradedFundDistribution.weight );
            }

            if ( cumulativeYield.getToOneYear() != null ) {
                value.put( "to_one_year", value.get( "to_one_year" ) + ( cumulativeYield.getToOneYear() * exchangeTradedFundDistribution.weight ) );
                coefficient.put( "to_one_year", coefficient.get( "to_one_year" ) + exchangeTradedFundDistribution.weight );
            }

            if ( cumulativeYield.getSinceCreation() != null ) {
                value.put( "since_creation", value.get( "since_creation" ) + ( cumulativeYield.getSinceCreation() * exchangeTradedFundDistribution.weight ) );
                coefficient.put( "since_creation", coefficient.get( "since_creation" ) + exchangeTradedFundDistribution.weight );
            }

        }

        return new CumulativeYield(
                value.get( "to_ten_year" ) / coefficient.get( "to_ten_year" ),
                value.get( "to_five_year" ) / coefficient.get( "to_five_year" ),
                value.get( "to_three_year" ) / coefficient.get( "to_three_year" ),
                value.get( "to_one_year" ) / coefficient.get( "to_one_year" ),
                value.get( "since_creation" ) / coefficient.get( "since_creation" )
        );
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
