--------------------------------------------------------
-- Creación de la Conexión
--------------------------------------------------------

ALTER SESSION SET "_ORACLE_SCRIPT"=true;
CREATE USER ControlTareas IDENTIFIED BY PFT8461;
GRANT DBA TO ControlTareas;