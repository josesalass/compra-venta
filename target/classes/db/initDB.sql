
CREATE TABLE PROVEEDOR (
	rutEmpresa VARCHAR (20),
	nombreEmpresa VARCHAR (30),
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
	contrasenia VARCHAR (12)
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
	PRIMARY KEY (rutRep, rutEmp),
	FOREIGN KEY (rutEmp) REFERENCES PROVEEDOR (rutEmpresa)
);

CREATE TABLE COMPRA (
	idCompra INT PRIMARY KEY,
	fecha TIMESTAMP,
	rutEmpresa VARCHAR (20),
	rutUsuario VARCHAR (12),
	FOREIGN KEY (rutEmpresa) REFERENCES PROVEEDOR (rutEmpresa),
	FOREIGN KEY (rutUsuario) REFERENCES USUARIO (rutUsuario)
);

CREATE TABLE VENTA(
	idVenta INT PRIMARY KEY,
	fecha TIMESTAMP,
	metodoPago VARCHAR (8),
	rutUsuario VARCHAR (12),
	FOREIGN KEY (rutUsuario) REFERENCES USUARIO(rutUsuario) 
);

CREATE TABLE FACTURA(
	rutCliente VARCHAR(20),
	idVenta INT,
	PRIMARY KEY (rutCliente,idVenta),
	FOREIGN KEY (idVenta) REFERENCES VENTA (idVenta)
);

CREATE TABLE BOLETA (
	idVenta INT PRIMARY KEY,
	FOREIGN KEY (idVenta) REFERENCES VENTA (idVenta)
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




