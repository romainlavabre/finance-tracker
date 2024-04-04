SELECT
    ETF.id AS id,
    ETF.id AS exchange_traded_fund_id,
    ETF.public_name AS exchange_traded_fund_public_name,
    ETF.amount AS exchange_traded_fund_amount,
    (SELECT COUNT(id) FROM country_distribution WHERE exchange_traded_fund_id = ETF.id) AS country_distribution_number,
    (SELECT COUNT(id) FROM sector_distribution WHERE exchange_traded_fund_id = ETF.id) AS sector_distribution_number,
    (SELECT SUM(weight) FROM country_distribution WHERE exchange_traded_fund_id = ETF.id) AS country_distribution_total,
    (SELECT SUM(weight) FROM sector_distribution WHERE exchange_traded_fund_id = ETF.id) AS sector_distribution_total
FROM exchange_traded_fund ETF
WHERE {{condition}}
GROUP BY ETF.id