
CREATE TABLE PROVEEDOR (
	rutEmpresa VARCHAR (20),
	nombreEmpresa VARCHAR (30),
	estado boolean,
	PRIMARY KEY (rutEmpresa)
);

CREATE TABLE PRODUCTO (
	idProducto INT NOT NULL PRIMARY KEY,
	stock INT NOT NULL,
	stockMin INT NOT NULL,
	detalleProducto VARCHAR (155) NOT NULL,
	valorCompra INT,
	valorVenta INT
);

CREATE TABLE USUARIO (
	rutUsuario VARCHAR (10) NOT NULL PRIMARY KEY,
	nombre VARCHAR (20) NOT NULL,
	apellido1 VARCHAR(20) NOT NULL,
	apellido2 VARCHAR (20),
	correoUsuario VARCHAR (50) NOT NULL,
	rolUsuario INT NOT NULL,
	contrasenia VARCHAR (200),
	contadorlogin INT,
);

CREATE TABLE CLIENTE(
	rutCliente VARCHAR (10) NOT NULL PRIMARY KEY,
	nombre VARCHAR (20) NOT NULL,
	apellido1 VARCHAR(20),
	apellido2 VARCHAR (20),
	telefono INT,
	comuna VARCHAR (20),
	calle VARCHAR (20),
	numeroCalle INT
);

CREATE TABLE REP_PROVEEDOR(
	rutRep VARCHAR (12) NOT NULL,
	rutEmp VARCHAR (20),
	nombre VARCHAR (20) NOT NULL,
	apellido1 VARCHAR(20) NOT NULL,
	apellido2 VARCHAR (20),
	correo VARCHAR (50),
	estado boolean,
	PRIMARY KEY (rutRep, rutEmp),
	FOREIGN KEY (rutEmp) REFERENCES PROVEEDOR (rutEmpresa)
);

CREATE TABLE COMPRA (
	idCompra SERIAL NOT NULL PRIMARY KEY,
	fecha TIMESTAMP,
	rutEmpresa VARCHAR (20),
	rutUsuario VARCHAR (12),
	FOREIGN KEY (rutEmpresa) REFERENCES PROVEEDOR (rutEmpresa),
	FOREIGN KEY (rutUsuario) REFERENCES USUARIO (rutUsuario)
);

CREATE TABLE VENTA(
	idVenta SERIAL NOT NULL PRIMARY KEY,
	fecha TIMESTAMP,
	tipoVenta VARCHAR (20),
	metodoPago VARCHAR (8),
	rutUsuario VARCHAR (12),
	rutCliente VARCHAR (10),
	FOREIGN KEY (rutCliente) REFERENCES CLIENTE(rutCliente),
	FOREIGN KEY (rutUsuario) REFERENCES USUARIO(rutUsuario) 
);

CREATE TABLE PERTENECE_COMPRA(
	idCompra INT,
	idProducto INT,
	cantidad INT,
	PRIMARY KEY (idCompra, idProducto),
	FOREIGN KEY (idCompra) REFERENCES COMPRA (idCompra),
	FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto)
);

CREATE TABLE ASOCIADA_VENTA(
	idProducto INT,
	idVenta INT,
	cantidad INT,
	PRIMARY KEY (idProducto, idVenta),
	FOREIGN KEY (idProducto) REFERENCES PRODUCTO (idProducto),
	FOREIGN KEY (idVenta) REFERENCES VENTA (idVenta)
);

CREATE TABLE TELEFONO_USUARIO(
	telefono INT,
	rutUsuario VARCHAR (12),
	PRIMARY KEY (telefono),
	FOREIGN KEY (rutUsuario) REFERENCES USUARIO (rutUsuario)
);


CREATE TABLE TELEFONO_REP(
	telefono INT PRIMARY KEY,
	rutRep VARCHAR (12),
	rutEmp VARCHAR (20),
	FOREIGN KEY (rutRep, rutEmp) REFERENCES REP_PROVEEDOR (rutRep, rutEmp)
);


CREATE OR REPLACE VIEW RegistroComprasDetalle AS
SELECT idcompra,idproducto,detalleproducto,fecha,nombreempresa AS proveedor,nombre||' '||apellido1 AS admindecompras,
				cantidad,valorcompra,(cantidad*valorcompra) AS valortotal 
FROM compra NATURAL JOIN pertenece_compra NATURAL JOIN producto NATURAL JOIN proveedor NATURAL JOIN usuario
ORDER BY fecha;

CREATE OR REPLACE  VIEW RegistroVentasDetalle AS
SELECT idventa,idproducto,detalleproducto,fecha,tipoventa,usuario.nombre||' '||usuario.apellido1 AS admindeventas,
				cantidad, valorventa, (cantidad*valorventa) AS valortotal
FROM venta NATURAL JOIN asociada_venta NATURAL JOIN producto NATURAL JOIN usuario
ORDER BY fecha;

CREATE OR REPLACE  VIEW RegistroComprasResumen AS
SELECT idcompra,fecha,nombreempresa AS proveedor, usuario.nombre || ' ' || usuario.apellido1 AS admindecompras, SUM(cantidad*valorcompra) AS valortotal
FROM compra NATURAL JOIN pertenece_compra NATURAL JOIN producto NATURAL JOIN proveedor NATURAL JOIN usuario 
GROUP BY idcompra,fecha,proveedor,admindecompras
ORDER BY fecha;

CREATE OR REPLACE VIEW RegistroVentasResumen AS
SELECT idventa,fecha,cliente.nombre ||' '||cliente.apellido1 AS cliente,tipoventa,usuario.nombre || ' ' || usuario.apellido1 AS admindeventas, SUM(cantidad*valorventa) AS valortotal 
FROM venta LEFT OUTER JOIN cliente ON venta.rutcliente = (cliente.rutcliente)
JOIN usuario ON usuario.rutusuario = venta.rutusuario
NATURAL JOIN asociada_venta 
NATURAL JOIN producto 
GROUP BY idventa,fecha,cliente,tipoventa,admindeventas;

CREATE OR REPLACE VIEW promedioventasmes AS
select to_char(fecha,'yyyy-mm') AS anio_mes,CAST(AVG(ALL valortotal) as int) AS promedio_mensual
from registroventasresumen
GROUP BY to_char(fecha,'yyyy-mm') ORDER BY to_char(fecha,'yyyy-mm');

CREATE OR REPLACE VIEW ProductoMasVendidoPorMes as 
select t1.fecha, detalleproducto, t1.cantidad from (
select to_char(fecha,'yyyy-mm') AS fecha, detalleproducto, sum(cantidad) as cantidad
from asociada_venta NATURAL JOIN producto NATURAL join VENTA
GROUP BY to_char(fecha,'yyyy-mm'),detalleproducto) as t1 
inner join 
(SELECT fecha, max(maxcantidad) as cantidad from (
SELECT to_char(fecha,'yyyy-mm') as fecha,sum(cantidad) AS maxcantidad
from asociada_venta NATURAL JOIN producto NATURAL join VENTA
group by to_char(fecha,'yyyy-mm'),detalleproducto
) as maxprod group by fecha) as t2 on t1.cantidad=t2.cantidad;

CREATE OR REPLACE VIEW ProductoMenosVendido as 
select distinct  t1.fecha, detalleproducto, t1.cantidad from (
select to_char(fecha,'yyyy-mm') AS fecha, detalleproducto, sum(cantidad) as cantidad
from asociada_venta NATURAL JOIN producto NATURAL join VENTA
GROUP BY to_char(fecha,'yyyy-mm'),detalleproducto) as t1 
inner join 
(SELECT fecha, min(maxcantidad) as cantidad from (
SELECT to_char(fecha,'yyyy-mm') as fecha,sum(cantidad) AS maxcantidad
from asociada_venta NATURAL JOIN producto NATURAL join VENTA
group by to_char(fecha,'yyyy-mm'),detalleproducto
) as minprod group by fecha) as t2 on t1.cantidad=t2.cantidad;

