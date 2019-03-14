CREATE VIEW DaylyReyting AS
  SELECT
    actionHistory.tarix_id,
    sum("total cost")                  AS 'total_cost',
    sum(sold_quantity)                 AS 'total_quantity',
    paid_date
  FROM actionHistory,product_sold_rate
    INNER JOIN sotuvchi
  WHERE
    actionHistory.sotuvchi_id = sotuvchi.sotuvchi_id
    AND
    actionHistory.creditAmount > 0
  GROUP BY  actionHistory.tarix_id

