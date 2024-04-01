SELECT
1 AS id,
SUM(ETF.amount) AS total
FROM exchange_traded_fund ETF;