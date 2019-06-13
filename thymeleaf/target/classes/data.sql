--Id (Primary) , image, nom, prix
INSERT INTO Product VALUES(1, '1.jpg','Nescafe - Classic' , 4.99);
INSERT INTO Product VALUES(2, '2.jpg', 'Carte Noire - Arabica' , 3.99);
INSERT INTO Product VALUES(3, '3.jpg', 'L''Or Expresso - Forza' , 2.99);
INSERT INTO Product VALUES(4, '4.jpg', 'Lavazza - Rossa' , 3.99);
INSERT INTO Product VALUES(5, '5.jpg', 'L''Or Expresso - Absolu' , 5.99);
INSERT INTO Product VALUES(6, '6.jpg', 'Lavazza - Oro' , 5.99);
INSERT INTO Product VALUES(7, '7.jpg', 'Nestlé - Ricoré' , 4.54);
INSERT INTO Product VALUES(8, '8.jpg', 'Illy - MonoArabica' , 8.99);
INSERT INTO Product VALUES(9, '9.jpg', 'Maxwell House - Classic' , 4.99);
INSERT INTO Product VALUES(10, '10.jpg', 'Néo Café - 100% Arabica' , 10.99);
INSERT INTO Product VALUES(11, '11.jpg', 'Jacques Vabre - Bahia' , 8.99);

--Id (Primary) , email, nom, prenom, mot de passse, role
INSERT INTO User VALUES(1, 'admin', 'Admin', 'Admin', 'password', 'ADMIN');
INSERT INTO User VALUES(2, 'fournisseur', 'Fournisseur', 'Fournisseur', 'password', 'ADMIN');
INSERT INTO User VALUES(3, 'bill.gates@gmail.com', 'Gates', 'Bill', 'Bill91', 'USER');
INSERT INTO User VALUES(5, 'currysteph@gmail.com', 'Curry', 'Stephen', 'stephenC', 'USER');
INSERT INTO User VALUES(6, 'mbapepite@gmail.com', 'Mbappé', 'Killian', 'password', 'USER');
INSERT INTO User VALUES(7, 'leslieD@gmail.com', 'Dainelli', 'Leslie', 'password', 'USER');
INSERT INTO User VALUES(8, 'dreazzydrake@gmail.com', 'Drake', 'Aubrey', 'password', 'USER');
INSERT INTO User VALUES(9, 'steve.jobs@gmail.com', 'Jobs', 'Steve', 'Steve91', 'USER');
INSERT INTO User VALUES(10, 'sodu95@live.com', 'Henocq', 'Sofiane-Henri', 'Sofian95', 'USER');
INSERT INTO User VALUES(11, 'cricridu7@neuf.fr', 'Ronaldo', 'Cristiano', 'cricri777', 'USER');
INSERT INTO User VALUES(12, 'alexmaio@hotmail.fr', 'Maio', 'Alex', 'password', 'USER');

--Id (Primary) , adresse, crypto carte, mois expiration carte, numero carte, titulaire carte, anne expiration carte, ville, id user, telephone, total, code postal
INSERT INTO order_table VALUES(1, '10 Rue de Vanves', '090', '02', '9378761947008736', 'Bill Gates', '2022', 'Issy Les Moulineaux', 3, '0696443554', 'Achevée', 12.97, '92130');
INSERT INTO order_table VALUES(2, '10 Rue de Vanves', 'C30', '02', '1239967891234567', 'Bill Gates', '2021', 'Issy Les Moulineaux', 3, '0697656788', 'Achevée', 6.99, '92130');
INSERT INTO order_table VALUES(3, '10 Rue de Paris', '1F1', '03', '5673678982763567', 'Steve Job', '2021', 'Bordeaux', 4, '0768753455', 'Achevée', 2.99, '31010');
INSERT INTO order_table VALUES(4, '10 Rue du Zenith', '311', '04', '1287653785635489', 'Aubrey Drake', '2020', 'Paris', 8, '0798658733', 'Achevée', 21.96, '75002');
INSERT INTO order_table VALUES(5, '20 rue de Buhoterie', '329', '05', '6789653786635489', 'Alex Maio', '2020', 'Saulx', 12, '0698587033', 'Achevée', 36.95, '91160');
INSERT INTO order_table VALUES(6, '2389 Champs Elysées', '0C5', '12', '3387652286907380', 'Killian Mbappé', '2022', 'Paris', 6, '0906536037', 'Achevée', 19.51, '75002');

--Id (Primary) , id commande, id produit
INSERT INTO order_detail VALUES(1,1,1);
INSERT INTO order_detail VALUES(2,1,4);
INSERT INTO order_detail VALUES(3,1,2);

INSERT INTO order_detail VALUES(4,2,2);
INSERT INTO order_detail VALUES(5,2,3);

INSERT INTO order_detail VALUES(6,3,3);

INSERT INTO order_detail VALUES(7,4,6);
INSERT INTO order_detail VALUES(8,4,1);
INSERT INTO order_detail VALUES(9,4,5);
INSERT INTO order_detail VALUES(10,4,9);

INSERT INTO order_detail VALUES(11,5,10);
INSERT INTO order_detail VALUES(12,5,8);
INSERT INTO order_detail VALUES(13,5,1);
INSERT INTO order_detail VALUES(14,5,3);
INSERT INTO order_detail VALUES(15,5,11);

INSERT INTO order_detail VALUES(16,6,7);
INSERT INTO order_detail VALUES(17,6,5);
INSERT INTO order_detail VALUES(18,6,2);
INSERT INTO order_detail VALUES(19,6,9);
