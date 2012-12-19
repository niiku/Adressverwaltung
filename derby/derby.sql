connect 'jdbc:derby:../../db/gkjava_db;create=true';
-- Der URL-Zusatz "create=true" wird nur zur Erstellung der Datenbank benötigt.
create table address (
	id integer not null generated always as identity,
	lastname varchar(40),
	firstname varchar(40),
	email varchar(40),
	email_additional varchar(40),
	homepage varchar(60),
	primary key (id)
);
insert into address (lastname, firstname, email, email_additional, homepage) values 
('Duck','Dagobert','d.duck@entenhausen.de','dagobert.duck@t-online.de','www.dagobert-duck.de'),
('Meier','Hugo','hugo.meier@fh-irgendwo.de','',''),
('Mück','Holger','hein.mueck@friesland.de','',''),
('Nolte','Emil','emil.nolte@abc.de','','www.emil-nolte.de'),
('Schmitz','Anton','anton.schmitz@gmx.de','',''),
('Abts','Dietmar','dietmar.abts@hs-niederrhein.de','dietmar.abts@t-online.de','www.dietmar-abts.de');
select * from address;
exit;
