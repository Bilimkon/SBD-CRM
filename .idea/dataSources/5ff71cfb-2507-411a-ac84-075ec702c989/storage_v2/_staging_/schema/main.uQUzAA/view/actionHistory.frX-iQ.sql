CREATE VIEW actionHistory as
  select
    s.id,
    h.tarix_id,
    h.sotuvchi_id,
    h.paid_date,
    h.total_cost,
    s.creditDescription,
    s.cardAmount,
    s.creditAmount,
    sum(total_cost)   as 'Total_total'
  from history h
    inner join savdoAction s on h.savdo_action_id = s.id;

