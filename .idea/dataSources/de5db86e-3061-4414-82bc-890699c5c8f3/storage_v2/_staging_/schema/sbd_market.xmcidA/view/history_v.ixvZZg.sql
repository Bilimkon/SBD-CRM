create or replace view history_v as
select `t`.`tarix_id`      AS `tarix_id`,
       `t`.`item_id`       AS `item_id`,
       `t`.`item_name`     AS `item_name`,
       `t`.`item_type`     AS `item_type`,
       `t`.`item_quantity` AS `item_quantity`,
       `t`.`paid_date`     AS `paid_date`,
       `t`.`total_cost`    AS `total_cost`
from `sbd_market`.`history` `t`
order by `t`.`tarix_id` desc;

