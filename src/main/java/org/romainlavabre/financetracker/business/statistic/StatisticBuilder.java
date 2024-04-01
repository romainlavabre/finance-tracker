package org.romainlavabre.financetracker.business.statistic;

import org.romainlavabre.financetracker.business.statistic.dto.*;

import java.util.List;

public interface StatisticBuilder {
    /**
     * Sum of the ETF amount
     *
     * @return Total of patrimony
     */
    Patrimony getPatrimony();


    /**
     * @return Risk distribution weighted by the weight of ETF
     */
    List< RiskDistribution > getRiskDistribution();


    /**
     * @return Provider distribution weighted by the weight of ETF
     */
    List< ProviderDistribution > getProviderDistribution();


    /**
     * @return ETF distribution
     */
    List< ExchangeTradedFundDistribution > getExchangeTradedFundDistribution();


    /**
     * @return Country distribution weighted by the weight of ETF
     */
    List< CountryDistribution > getCountryDistribution();


    /**
     * @return Continent distribution weighted by the weight of ETF
     */
    List< ContinentDistribution > getContinentDistribution();


    /**
     * @return Sector distribution weighted by the weight of ETF
     */
    List< SectorDistribution > getSectorDistribution();


    /**
     * @return Past performance of your portfolio by year
     */
    List< PastPerformanceAggregateByYear > getPastPerformanceAggregateByYear();


    /**
     * @return Average pricing weighted by the weight of ETF
     */
    AveragePricing getAveragePricing();
}
