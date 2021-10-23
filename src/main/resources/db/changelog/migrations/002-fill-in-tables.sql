INSERT INTO singer (name) VALUES
('Madonna'),
('Michael Jackson'),
('Billie Eilish'),
('Metallica');
commit;

INSERT INTO company (name) VALUES
('Black Company'),
('The best company'),
('Europe label'),
('Imagine label');
commit;


INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 123', 'Frozen', '1', '1008-01-15', 1);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 125', 'Earth songs', '1', '1982-01-15', 2);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 126', 'Bad Guy', '1', '2019-01-15', 3);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 127', 'Nothing else matters', '1', '1991-01-15', 4);

INSERT INTO recording (song_code, title, version, release_time, singer_id ) VALUES
('SN 128', 'mother', '1', '1995-01-15', 1);

INSERT INTO copyright (start_date, end_date, royalty, company_id, recording_id) VALUES
('2019-01-15', '2030-01-15', 1000000, 1, 3);

INSERT INTO copyright (start_date, end_date, royalty, company_id, recording_id) VALUES
('2012-01-15', '2031-01-15', 2000000, 2, 2);