DROP TABLE item IF EXISTS;
DROP TABLE promotion IF EXISTS;

CREATE TABLE item
(
    id    VARCHAR(100) PRIMARY KEY,
    name  VARCHAR(100),
    price DECIMAL,
    unit  VARCHAR(50)
);

CREATE TABLE promotion
(
    id                             INTEGER  PRIMARY KEY,
    item_id                        VARCHAR(100),
    type                           VARCHAR(100),
    eligible_quantity_for_discount INTEGER,
    promotion_price                DECIMAL,
    promotion_unit                 VARCHAR(50),
    coupon_discount_percentage     INTEGER
);

ALTER TABLE promotion
    ADD CONSTRAINT fk_promotion_item FOREIGN KEY (item_id) REFERENCES item (id);