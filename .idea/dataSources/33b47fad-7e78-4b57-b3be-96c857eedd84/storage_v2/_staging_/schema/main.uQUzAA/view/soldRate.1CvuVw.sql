DROP VIEW if exists soldRate;
CREATE VIEW soldRate AS
  SELECT
    h.product_barcode           barcode,
    t.name                      type,
    h.product_name              name,
    h.cost                      cost,
    h.date_cr                   date,
    sum(product_quantity)       quantity,
    h.product_quantity * h.cost total_cost
  FROM history h
    JOIN type t
  WHERE h.product_type = t.id
   GROUP BY h.product_id ORDER BY quantity;

