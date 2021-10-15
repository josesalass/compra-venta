INSERT INTO cliente (rutcliente,nombre,apellido1,apellido2,telefono,comuna,calle,numerocalle) 
VALUES('200772814','yo','apellido','apellido2',1272238,'billa', 'calle',43) ON CONFLICT DO NOTHING;

