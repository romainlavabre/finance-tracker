package org.romainlavabre.financetracker.parameter;

public interface CumulativeYieldParameter {
    String PREFIX               = "cumulative_yield_";
    String TO_TEN_YEAR          = PREFIX + "to_ten_year";
    String TO_FIVE_YEAR         = PREFIX + "to_five_year";
    String TO_THREE_YEAR        = PREFIX + "to_three_year";
    String TO_ONE_YEAR          = PREFIX + "to_one_year";
    String SINCE_CREATION       = PREFIX + "since_creation";
    String EXCHANGE_TRADED_FUND = PREFIX + "exchange_traded_fund_id";
}
