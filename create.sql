PRAGMA foreign_keys = ON ; 
create table if not exists Pokemon( 
	id int, 
	name varchar(25),
	description varchar(150),
	nature varchar(25), 
	PRIMARY KEY (id) 
);

insert into Pokemon values(1, 'Bulbasaur', 'Bulbasaur. It bears the seed of a plant on its back from birth. The seed slowly develops. Researchers are unsure whether to classify Bulbasaur as a plant or animal. Bulbasaur are extremely calm and very difficult to capture in the wild.','Jolly');

insert into Pokemon values(4, 'Charmander', 'Charmander. A flame burns on the tip of its tail from birth. It is said that a Charmander dies if its flame ever goes out.','Rash');

insert into Pokemon values(7, 'Squirtle', 'Squirtle. This Tiny Turtle Pokémon draws its long neck into its shell to launch incredible water attacks with amazing range and accuracy. The blasts can be quite powerful.','Relaxed');

create table if not exists Trainer(
	id int, 
	name varchar(50),
	badges int, 
	PRIMARY KEY (id) 
);

insert into Trainer values(1, 'Jon', 8);
insert into Trainer values(2, 'Ash', 4); 
insert into Trainer values(3, 'Gary', 0);

create table if not exists Partners(
	id int, 
	trainerID int, 
	pkmnID int,
	location varchar(50),
	PRIMARY KEY(id), 
	FOREIGN KEY(pkmnID) REFERENCES Pokemon(id),
	FOREIGN KEY(trainerID) REFERENCES Trainer(id)
);

insert into Partners values (1, 1, 1, "Lavendar Town");
insert into Partners values(2, 2, 4, "Oak Lab"); 
insert into Partners values(3, 3, 7, "Victory Road");  
