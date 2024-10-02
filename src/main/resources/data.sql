create table if not exists impression
(
    id            VARCHAR(255) not null primary key,
    advertiser_id bigint       not null,
    app_id        bigint       not null,
    country_code  varchar(255)
);

create table if not exists adv_click
(
    id            VARCHAR(255) not null primary key,
    revenue       decimal(19, 2) not null,
    impression_id VARCHAR(255) references impression
);
