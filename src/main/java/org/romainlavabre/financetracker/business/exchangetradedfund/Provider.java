package org.romainlavabre.financetracker.business.exchangetradedfund;

public interface Provider {

    Scrapper.ScrapperResult scrape( String pageLink );


    boolean isMatch( String pageLink );
}
