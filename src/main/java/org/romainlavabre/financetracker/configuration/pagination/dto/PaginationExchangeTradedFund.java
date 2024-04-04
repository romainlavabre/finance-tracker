package org.romainlavabre.financetracker.configuration.pagination.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.pagination.annotation.ModeType;
import org.romainlavabre.pagination.annotation.Pagination;

@Entity
@Pagination( mode = ModeType.FILE, filePath = "classpath:pagination/exchange-traded-fund.sql" )
public class PaginationExchangeTradedFund {
    @Json( groups = @Group )
    @Id
    protected long exchange_traded_fund_id;

    @Json( groups = @Group )
    protected String exchange_traded_fund_public_name;

    @Json( groups = @Group )
    protected float exchange_traded_fund_amount;

    @Json( groups = @Group )
    protected int country_distribution_number;

    @Json( groups = @Group )
    protected int sector_distribution_number;

    @Json( groups = @Group )
    protected Float country_distribution_total;

    @Json( groups = @Group )
    protected Float sector_distribution_total;
}
