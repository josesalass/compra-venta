
INSERT INTO Usuario (rutusuario,nombre,apellido1, apellido2,correousuario,rolusuario,contrasenia)
VALUES ('164536512','Fernando','Tormenta','China','tormentaxina@yopmail.com',1,'Huevos01'),
('132675624','katherine','Elder','Scrolls','kathoeso@hotmail.com',3, 'Cypher123'),('203766542','Matias','Sprite','Enjoy','ultraxasesino@gmail.com',2,'Contraseña'),
('86351462','Jose','Pollo','Undas','chocojump@hotmail.com',3,'AsdqW123')
;

INSERT INTO cliente (rutcliente,nombre,apellido1,apellido2,telefono,comuna,calle,numerocalle) 
VALUES('164631952','Marco','Perez','Parada',92724238,'Chillan', 'los raudales',165), 
('203749912','Luis','Gonzalez','Gatica',91245472,'Chillan', 'Calle falsa',123),
('615349950','Leorio','Perez','Zapata', 976166213,'Chillan', 'Simon Bolivar',1672)
;

INSERT INTO producto (idproducto, stock, stockmin, valorcompra, valorventa, detalleproducto)
VALUES( 247, 100, 10, 690, 1390, 'aceite vegetal')
,( 627, 100, 10, 590, 1300, 'galletas 3pack')
,( 264, 100, 10, 1000, 1690, 'bebida lactea 2lts')
,( 145, 100, 10, 900, 1650, 'tarro de cafe mediano')
,( 92, 100, 10, 750, 1500, 'tarro de leche condensada')
,( 63, 100, 10, 350, 690, 'chocolate pequeño')
,( 123, 100, 10, 100, 500, 'paquete de gomitas')
,( 254, 100, 10, 10, 290, 'lapiz pasta')
,( 341, 100, 10, 1500, 2690, 'Tarro de leche grande')
;

INSERT INTO Proveedor(rutempresa, nombreempresa)
VALUES ('142954367','Carozzi')
,('792954367','Iansa')
,('796354362','Merkat')
,('364925517','Lobos')
,('649317125','Soprole')
;


INSERT INTO Rep_Proveedor (rutrep, rutemp,nombre,apellido1, apellido2, correo)
VALUES ('163254764','142954367','Manuel','Garcia','Marquez','m.garcia@yopmail.com'),('203465241','792954367','Luciano','Pereira','Parada','lp.parada@hotmail.com'),
('123466257','796354362','Aurelio','Castro','Troncoso','acastroncoso@msg.cl'),('156324975','364925517','Fausto','Herrera','Poblete','wolfsur@lobos.com'),
('186345721','649317125','Martin','Riquelme','Gallegos','mrgallegos@gmail.com')
;



INSERT INTO Telefono_rep (telefono, rutrep, rutemp)
VALUES (953167264,'163254764','142954367'),(975613425,'203465241','792954367'),(975564233,'123466257','796354362'),
(998765432,'156324975','364925517'),(974563252,'186345721','649317125')
;

INSERT INTO Telefono_Usuario (telefono, rutusuario)
VALUES ( 956348247,'164536512'), ( 967823451,'132675624'), ( 946656731,'203766542'), ( 964566546,'86351462')
;

INSERT INTO compra ( fecha, rutEmpresa, rutUsuario)
VALUES ( '2021-09-16',796354362, 203766542),
( '2021-09-16', 142954367, 203766542),
( '2021-10-10', 649317125, 203766542), 
( '2021-10-16', 364925517, 203766542),('2021-10-17', 792954367, 203766542)
;


INSERT INTO pertenece_compra (idcompra, idproducto, cantidad)
VALUES (1,247,43),(2,627,3),(3,264,6),(4,145,3),(5,92,4)
;

INSERT INTO Venta (fecha, tipoVenta, metodopago, rutusuario, rutCliente)
VALUES ('05-09-2021','factura','efectivo','132675624','164631952'),
('07-09-2021','boleta','debito','86351462',NULL),
('15-09-2021','factura','credito','132675624','203749912'),
('10-10-2021','boleta','efectivo','86351462',NULL),
('12-10-2021','boleta','debito','132675624',NULL),
('14-10-2021','factura','efectivo','86351462','615349950'),
('16-10-2021','boleta','credito','132675624',NULL)
;


INSERT INTO Asociada_Venta (idproducto, idventa, cantidad)
VALUES (123,1,43)
,(254,2,30)
,(341,3,60)
,(145,4,30)
,(92,5,40)
;











