package org.romainlavabre.financetracker.business.exchangetradedfund;

import org.romainlavabre.exception.HttpNotFoundException;
import org.romainlavabre.financetracker.configuration.response.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapperImpl implements Scrapper {

    protected final List< Provider > providers;


    public ScrapperImpl( List< Provider > providers ) {
        this.providers = providers;
    }


    @Override
    public ScrapperResult scrape( String pageLink ) {
        for ( Provider provider : providers ) {
            if ( provider.isMatch( pageLink ) ) {
                return provider.scrape( pageLink );
            }
        }

        throw new HttpNotFoundException( Message.EXCHANGE_TRADED_FUND_AUTOMATION_NOT_SUPPORT_PROVIDER );
    }
}
