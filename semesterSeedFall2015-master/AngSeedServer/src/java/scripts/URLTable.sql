DROP TABLE IF EXISTS URL;
CREATE TABLE URL (
URL VARCHAR(255),
AIRLINENAME VARCHAR(255),
PRIMARY KEY (URL)
);

INSERT INTO URL(URL, AIRLINENAME) VALUES('http://angularairline-plaul.rhcloud.com', 'AngularJS Airline');
INSERT INTO URL(URL, AIRLINENAME) VALUES('http://thebighornairline-ebski.rhcloud.com/GiantHornAirlineServer/', 'The Giant Horn Airline');