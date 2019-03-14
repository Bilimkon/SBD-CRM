CREATE VIEW actionHistory123404 as
  select
    s.id,
    h.tarix_id,
    h.sotuvchi_id,
    h.paid_date,
    sum(total_cost)   as 'Total_total',
    sum(cardAmount)   as 'Total_cardAmount',
    sum(creditAmount) as 'Total_creditAmount'
  from history h
    inner join savdoAction s on h.savdo_action_id = s.id



