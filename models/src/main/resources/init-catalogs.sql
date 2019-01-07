INSERT INTO artists (name) VALUES ('Jan Lampič');
INSERT INTO artists (name) VALUES ('Jay-Z');
INSERT INTO artists (name) VALUES ('Miran Rudan');
INSERT INTO artists (name) VALUES ('SuperDuperKPop group');
INSERT INTO artists (name) Values ('Dave Grohl');

INSERT INTO genres(name) VALUES ('Pop');
INSERT INTO genres(name) VALUES ('Rock');
INSERT INTO genres(name) VALUES ('K-Pop');
INSERT INTO genres(name) VALUES ('Rap');
INSERT INTO genres(name) VALUES ('Country');

INSERT INTO albums(name, artist_id, genre_id) VALUES ('Merry Christmas', 1, 2);
INSERT INTO albums(name, artist_id, genre_id) VALUES ('The Blueprint 3', 2, 4);
INSERT INTO albums(name, artist_id, genre_id) VALUES ('Moj zadnji album, ampak res', 3, 1);
INSERT INTO albums(name, artist_id, genre_id) VALUES ('Same ta dobre od KPopa', 4, 3);
INSERT INTO albums(name, artist_id, genre_id) VALUES ('The Colour and the Shape', 5, 2);

INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Still no snow', 1, 1, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('And there was snow', 1, 1, 5)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Sploh ne rabm snega', 1, 1, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Kaj ti bo sneg če imaš kolo', 1, 1, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Krivo je more', 1, 1, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Smells like global warming', 1, 1, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Oh sweet Kentucky', 2, 2, 5)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Oh le kdaj se bom upokojil', 3, 3, 3)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Akonichisong-san', 4, 4, 4)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Everlong', 5, 5, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('My Hero', 5 ,5, 2)
INSERT INTO songs(title, artist_id, album_id, genre_id) VALUES ('Empire state of mind', 2, 2, 4)





INSERT INTO playlists(name, user_id) VALUES ('Ta dobre', 1);