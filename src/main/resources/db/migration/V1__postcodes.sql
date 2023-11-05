CREATE TABLE postcodes
(
    id bigint primary key,
    postCode varchar(255) not null,
    latitude decimal not null,
    longitude decimal not null,
    last_updated timestamp
);