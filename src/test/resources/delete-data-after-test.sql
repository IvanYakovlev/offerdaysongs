DELETE FROM copyright;
ALTER TABLE copyright ALTER COLUMN ID RESTART WITH 1;
DELETE FROM recording;
ALTER TABLE recording ALTER COLUMN ID RESTART WITH 1;
DELETE FROM company;
ALTER TABLE company ALTER COLUMN ID RESTART WITH 1;
DELETE FROM singer;
ALTER TABLE singer ALTER COLUMN ID RESTART WITH 1;