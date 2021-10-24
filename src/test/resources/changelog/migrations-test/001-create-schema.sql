CREATE TABLE singer_test
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256)
);

CREATE TABLE recording_test
(
    id           BIGSERIAL PRIMARY KEY,
    song_code    VARCHAR(32),
    title        VARCHAR(4096),
    version      VARCHAR(128),
    release_time TIMESTAMP,
    singer_id    BIGINT REFERENCES singer (id)
);

CREATE TABLE company_test
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(256)
);

CREATE TABLE copyright_test
(
    id           BIGSERIAL PRIMARY KEY,
    start_date      TIMESTAMP,
    end_date        TIMESTAMP,
    royalty         BIGINT,
    company_id      BIGINT REFERENCES company (id),
    recording_id    BIGINT REFERENCES recording (id)
);

