CREATE VIEW DaylyReytingTotal122 AS
  SELECT
    sum("total cost")  AS 'total_cost',
    sum(sold_quantity) AS 'total_quantity',
    sum(creditAmount) AS 'Total_CreditAmount',
    sum(cardAmount)  as  'Total_cardAmount',
     current_date
  FROM product_sold_rate CROSS JOIN actionHistory
  on product_sold_rate.tarix_id = actionHistory.tarix_id;

