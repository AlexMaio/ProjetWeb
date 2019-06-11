--Id (Primary) , image, nom, prix
INSERT INTO Product VALUES(1, '1.jpg','Nescafe' , 4.99);
INSERT INTO Product VALUES(2, '2.jpg', 'Carte Noire' , 3.99);
INSERT INTO Product VALUES(3, '3.jpg', 'L''Or Expresso' , 2.99);
INSERT INTO Product VALUES(4, '4.jpg', 'Lavazza' , 3.99);

--Id (Primary) , email, nom, prenom, mot de passse, role
INSERT INTO User VALUES(16872, 'admin', 'Admin', 'Admin', 'password', 'ADMIN');
INSERT INTO User VALUES(6872, 'user', 'User', 'User', 'password', 'USER');
INSERT INTO User VALUES(997761, 'bill.gates@gmail.com', 'Gates', 'Bill', 'Bill91', 'USER');
INSERT INTO User VALUES(76872, 'steve.jobs@gmail.com', 'Jobs', 'Steve', 'Steve91', 'USER');

--Id (Primary) , adresse, crypto carte, mois expiration carte, numero carte, titulaire carte, anne expiration carte, ville, id user, telephone, total, code postal
INSERT INTO order_table VALUES(1, '10 Rue de Vanves', '000', '02', '0000000000000000', 'Bill Gates', '2022', 'Issy Les Moulineaux', 997761, '0606060606', 'Achevée', 12.97, '92130');
INSERT INTO order_table VALUES(2, '10 Rue de Vanves', 'C30', '02', '1234567891234567', 'Bill Gates', '2021', 'Issy Les Moulineaux', 997761, '0606060606', 'Achevée', 6.99, '92130');
INSERT INTO order_table VALUES(3, '10 Rue de Paris', '11', '03', '1111111111111111', 'Steve Job', '2021', 'Paris', 76872, '0707070707', 'Achevée', 2.99, '75010');

--Id (Primary) , id commande, id produit
INSERT INTO order_detail VALUES(1,1,1);
INSERT INTO order_detail VALUES(2,1,4);
INSERT INTO order_detail VALUES(3,1,2);
INSERT INTO order_detail VALUES(4,2,2);
INSERT INTO order_detail VALUES(5,2,3);
INSERT INTO order_detail VALUES(6,3,3);