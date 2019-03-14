CREATE VIEW DaylyReytingFull1 AS
  SELECT
    sum("total cost")                  AS 'total_cost',
    sum(product_sold_rate.sold_quantity)                 AS 'total_quantity',
    sum(s.creditAmount)                  AS 'total_creditAmount',
    sum(s.cardAmount)                    AS 'total_cardAmount',
    s.savdoTime
  FROM product_sold_rate,savdoAction s

