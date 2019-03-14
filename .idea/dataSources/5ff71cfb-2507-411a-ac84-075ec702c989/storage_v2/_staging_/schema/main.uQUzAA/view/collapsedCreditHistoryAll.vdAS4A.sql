
  SELECT
    id,
    sotuvchi.sotuvchi_id,
    sotuvchi.first_name,
    sotuvchi.last_name,
    cardAmount,
    creditAmount                                AS 'credit',
    creditDescription,
    sum(total_cost)                             AS 'total_cost',
    sum(total_cost) - cardAmount - creditAmount AS 'paid_in_cash'
  FROM actionHistory
    INNER JOIN sotuvchi

  WHERE
    actionHistory.sotuvchi_id = sotuvchi.sotuvchi_id

  GROUP BY id;

