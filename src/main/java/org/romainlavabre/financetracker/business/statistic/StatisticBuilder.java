package org.romainlavabre.financetracker.business.statistic;

public interface StatisticBuilder {
    void getPatrimony();


    void getRiskDistribution();


    void getProviderDistribution();


    void getExchangeTradedFundDistribution();


    void getCountryDistribution();


    void getContinentDistribution();


    void getSectorDistribution();


    void getPastPerformanceAggregateByYear();
}
