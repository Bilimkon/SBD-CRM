create definer = root@localhost view collapsedcredithistory as
select `actionhistory`.`id`                                                                                  AS `id`,
       `sbd_market`.`sotuvchi`.`sotuvchi_id`                                                                 AS `sotuvchi_id`,
       `sbd_market`.`sotuvchi`.`first_name`                                                                  AS `first_name`,
       `sbd_market`.`sotuvchi`.`last_name`                                                                   AS `last_name`,
       `actionhistory`.`cardAmount`                                                                          AS `cardAmount`,
       `actionhistory`.`creditAmount`                                                                        AS `credit`,
       `actionhistory`.`creditDescription`                                                                   AS `creditDescription`,
       `actionhistory`.`paid_date`                                                                           AS `paid_date`,
       sum(`actionhistory`.`total_cost`)                                                                     AS `total_cost`,
       ((sum(`actionhistory`.`total_cost`) - `actionhistory`.`cardAmount`) -
        `actionhistory`.`creditAmount`)                                                                      AS `paid_in_cash`
from (`sbd_market`.`actionhistory`
       join `sbd_market`.`sotuvchi`)
where ((`actionhistory`.`sotuvchi_id` = `sbd_market`.`sotuvchi`.`sotuvchi_id`))
group by `actionhistory`.`id`;

