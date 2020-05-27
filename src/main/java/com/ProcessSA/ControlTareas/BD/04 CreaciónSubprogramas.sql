--------------------------------------------------------
--  DDL for Trigger TRG_CR_FUNCION
--------------------------------------------------------

CREATE OR REPLACE TRIGGER "TRG_CR_FUNCION"
    BEFORE INSERT
    ON funcion
    FOR EACH ROW
BEGIN
    SELECT SEQ_cod_funcion.nextval
    INTO :new.codigo
    FROM DUAL;
END;
/
ALTER TRIGGER "TRG_CR_FUNCION" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_CR_PROCESO
--------------------------------------------------------

CREATE OR REPLACE TRIGGER "TRG_CR_PROCESO"
    BEFORE INSERT
    ON proceso
    FOR EACH ROW
BEGIN
    SELECT SEQ_cod_proceso.nextval
    INTO :new.codigo
    FROM DUAL;
END;

/
ALTER TRIGGER "TRG_CR_PROCESO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_CR_PROYECTO
--------------------------------------------------------

CREATE OR REPLACE TRIGGER "TRG_CR_PROYECTO"
    BEFORE INSERT
    ON proyecto
    FOR EACH ROW
BEGIN
    SELECT SEQ_cod_proyecto.nextval
    INTO :new.codigo
    FROM DUAL;
END;

/
ALTER TRIGGER "TRG_CR_PROYECTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_CR_TAREA
--------------------------------------------------------

CREATE OR REPLACE TRIGGER "TRG_CR_TAREA"
    BEFORE INSERT
    ON tarea
    FOR EACH ROW
BEGIN
    SELECT SEQ_cod_tarea.nextval
    INTO :new.codigo
    FROM DUAL;
END;
/
ALTER TRIGGER "TRG_CR_TAREA" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_CR_UI
--------------------------------------------------------

CREATE OR REPLACE TRIGGER "TRG_CR_UI"
    BEFORE INSERT
    ON UNIDAD_INTERNA
    FOR EACH ROW
BEGIN
    SELECT SEQ_cod_ui.nextval
    INTO :new.codigo
    FROM DUAL;
END;

/
ALTER TRIGGER "TRG_CR_UI" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRG_CR_USUARIO
--------------------------------------------------------

CREATE OR REPLACE TRIGGER "TRG_CR_USUARIO"
    BEFORE INSERT
    ON USUARIO
    FOR EACH ROW
BEGIN
    SELECT SEQ_id_usuario.nextval
    INTO :new.id
    FROM DUAL;
END;
/
ALTER TRIGGER "TRG_CR_USUARIO" ENABLE;

-- Función FN_GET_GLOSA_ERROR & Procedimientos Almacenados CRUD

create or replace FUNCTION "FN_GET_GLOSA_ERROR" RETURN VARCHAR2
AS

/***********************************************************************************************************************
   NAME:    FN_GET_GLOSA_ERROR
   PURPOSE: Funcion que retorna glosa de error, incluye stacktrace con nombre del SP y linea donde se produce el error

   REVISIONS:
   Ver          Date           Author                       Description
   ---------    ----------     -------------------          -------------------------------------------------------------
   X.X.X-X.X    DD/MM/YYYY     AUTOR DE LA FUNCION          1. Funcion que retorna detalles del error ocurrido

************************************************************************************************************************/

    LV_BACKTRACE VARCHAR2(4000) := DBMS_UTILITY.format_error_backtrace;
    LV_MENSAJE VARCHAR2(4000) := '';

BEGIN

    LV_MENSAJE := CONCAT(LV_BACKTRACE, SUBSTR(SQLERRM, 1, 255));

    RETURN SUBSTR(LV_MENSAJE, 1, 4000);

EXCEPTION
    WHEN OTHERS THEN
        RETURN NULL;
END FN_GET_GLOSA_ERROR;
/

create or replace PROCEDURE "SP_GET_PERSONAS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PERSONAS OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_PERSONAS
   PURPOSE		Retorna todos los registros de PERSONA en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PERSONAS ejecutado exitósamente 👍';

    OPEN OUT_PERSONAS FOR
        SELECT * FROM PERSONA;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PERSONAS;
/

create or replace PROCEDURE "SP_DEL_ASIGNACION" (
    IN_id_usuario IN ASIGNACION.ID_USUARIO%TYPE,
    IN_codigo_ui IN ASIGNACION.CODIGO_UI%TYPE,
    IN_codigo_tarea IN ASIGNACION.CODIGO_TAREA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_ASIGNACION
   PURPOSE		Ejecuta la eliminación del registro de una ASIGNACION con el id y los códigos ingresados

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_ASIGNACION ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM ASIGNACION WHERE ID_USUARIO = IN_id_usuario AND  CODIGO_UI = IN_codigo_ui AND CODIGO_TAREA = in_codigo_tarea;

    IF hay_registro = 1 THEN
        DELETE FROM ASIGNACION WHERE ID_USUARIO = IN_id_usuario AND  CODIGO_UI = IN_codigo_ui AND CODIGO_TAREA = in_codigo_tarea;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'ASIGNACION no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_ASIGNACION;
/

create or replace PROCEDURE "SP_DEL_CONTRATO" (
    IN_RUT_persona IN CONTRATO.RUT_PERSONA%TYPE,
    IN_RUT_empresa IN CONTRATO.RUT_EMPRESA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_CONTRATO
   PURPOSE		Ejecuta la eliminación del registro de un CONTRATO con el rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_CONTRATO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM CONTRATO WHERE RUT_EMPRESA = IN_RUT_empresa AND  RUT_PERSONA = IN_RUT_persona;

    IF hay_registro = 1 THEN
        DELETE FROM CONTRATO WHERE RUT_EMPRESA = IN_RUT_empresa AND  RUT_PERSONA = IN_RUT_persona;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'CONTRATO no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_CONTRATO;
/

create or replace PROCEDURE "SP_DEL_EMPRESA" (
    IN_RUT IN EMPRESA.RUT%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_EMPRESA
   PURPOSE		Ejecuta la eliminación del registro de una EMPRESA con el rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_EMPRESA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM EMPRESA WHERE RUT = in_rut;

    IF hay_registro = 1 THEN
        DELETE FROM EMPRESA WHERE RUT = IN_RUT;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Rut no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_EMPRESA;
/

create or replace PROCEDURE "SP_DEL_FLUJO_F" (
    IN_indice IN FLUJO_FUNCION.INDICE %TYPE,
    IN_codigo_funcion IN FLUJO_FUNCION.CODIGO_FUNCION%TYPE,
    IN_codigo_proceso IN FLUJO_FUNCION.CODIGO_PROCESO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_FLUJO_F
   PURPOSE		Ejecuta la eliminación del registro de un FLUJO_FUNCION con el indice y los códigos ingresados

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_FLUJO_F ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FLUJO_FUNCION WHERE INDICE = IN_indice AND  CODIGO_FUNCION = IN_codigo_funcion AND CODIGO_PROCESO = in_codigo_proceso;

    IF hay_registro = 1 THEN
        DELETE FROM FLUJO_FUNCION WHERE INDICE = IN_indice AND  CODIGO_FUNCION = IN_codigo_funcion AND CODIGO_PROCESO = in_codigo_proceso;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'FLUJO_FUNCION no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_FLUJO_F;
/

create or replace PROCEDURE "SP_DEL_FLUJO_T" (
    IN_indice IN FLUJO_TAREA.INDICE %TYPE,
    IN_codigo_funcion IN FLUJO_TAREA.CODIGO_FUNCION%TYPE,
    IN_codigo_tarea IN FLUJO_TAREA.CODIGO_TAREA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_FLUJO_T
   PURPOSE		Ejecuta la eliminación del registro de un FLUJO_TAREA con el indice y los códigos ingresados

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_FLUJO_T ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FLUJO_TAREA WHERE INDICE = IN_indice AND  CODIGO_FUNCION = IN_codigo_funcion AND CODIGO_TAREA = in_codigo_tarea;

    IF hay_registro = 1 THEN
        DELETE FROM FLUJO_TAREA WHERE INDICE = IN_indice AND  CODIGO_FUNCION = IN_codigo_funcion AND CODIGO_TAREA = in_codigo_tarea;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'FLUJO_TAREA no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_FLUJO_T;
/

create or replace PROCEDURE "SP_DEL_FUNCION" (
    IN_codigo IN FUNCION.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_FUNCION
   PURPOSE		Ejecuta la eliminación del registro de una FUNCION con el codigo ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_FUNCION ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FUNCION WHERE CODIGO = IN_codigo;

    IF hay_registro = 1 THEN
        DELETE FROM FUNCION WHERE CODIGO = IN_codigo;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_FUNCION;
/

create or replace PROCEDURE "SP_DEL_INTEGRANTE" (
    IN_id_usuario IN INTEGRANTE.ID_USUARIO%TYPE,
    IN_codigo_ui IN INTEGRANTE.CODIGO_UI%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_INTEGRANTE
   PURPOSE		Ejecuta la eliminación del registro de un INTEGRANTE con el id y código ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_INTEGRANTE ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM INTEGRANTE WHERE ID_USUARIO = IN_id_usuario AND  CODIGO_UI = IN_codigo_ui;

    IF hay_registro = 1 THEN
        DELETE FROM INTEGRANTE WHERE ID_USUARIO = IN_id_usuario AND  CODIGO_UI = IN_codigo_ui;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'INTEGRANTE no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_INTEGRANTE;
/

create or replace PROCEDURE "SP_DEL_PERSONA" (
    IN_RUT IN PERSONA.RUT%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS


/**************************************************************************************************************
   NAME:       	SP_DEL_PERSONA
   PURPOSE		Ejecuta la eliminación del registro de PERSONA con el rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_PERSONA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PERSONA WHERE RUT = in_rut;

    IF hay_registro = 1 THEN
        DELETE FROM PERSONA WHERE RUT = IN_RUT;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Rut no registrado';
    END IF;


EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_PERSONA;
/

create or replace PROCEDURE "SP_DEL_PLAZO" (
    IN_codigo_tarea IN PLAZO.CODIGO_TAREA %TYPE,
    IN_contador IN PLAZO.CODIGO_TAREA %TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_PLAZO
   PURPOSE		Ejecuta la eliminación del registro de un PLAZO con los códigos ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_PLAZO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PLAZO WHERE CODIGO_TAREA = in_codigo_tarea AND contador = in_contador;

    IF hay_registro = 1 THEN
        DELETE FROM PLAZO WHERE CODIGO_TAREA = in_codigo_tarea AND contador = in_contador;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'PLAZO no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_PLAZO;
/

create or replace PROCEDURE "SP_DEL_PROCESO" (
    IN_codigo IN PROCESO.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_PROCESO
   PURPOSE		Ejecuta la eliminación del registro de un PROCESO con el codigo ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_PROCESO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PROCESO WHERE CODIGO = IN_codigo;

    IF hay_registro = 1 THEN
        DELETE FROM PROCESO WHERE CODIGO = IN_codigo;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_PROCESO;
/

create or replace PROCEDURE "SP_DEL_PROYECTO" (
    IN_codigo IN PROYECTO.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_PROYECTO
   PURPOSE		Ejecuta la eliminación del registro de un PROYECTO con el codigo ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_PROYECTO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PROYECTO WHERE CODIGO = IN_codigo;

    IF hay_registro = 1 THEN
        DELETE FROM PROYECTO WHERE CODIGO = IN_codigo;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_PROYECTO;
/

create or replace PROCEDURE "SP_DEL_TAREA" (
    IN_codigo IN TAREA.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_TAREA
   PURPOSE		Ejecuta la eliminación del registro de una TAREA con el codigo ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_TAREA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM TAREA WHERE CODIGO = IN_codigo;

    IF hay_registro = 1 THEN
        DELETE FROM TAREA WHERE CODIGO = IN_codigo;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_TAREA;
/

create or replace PROCEDURE "SP_DEL_UI" (
    IN_codigo IN UNIDAD_INTERNA.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_UI
   PURPOSE		Ejecuta la eliminación del registro de una UNIDAD_INTERNA con el codigo ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_UI ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM UNIDAD_INTERNA WHERE CODIGO = IN_codigo;

    IF hay_registro = 1 THEN
        DELETE FROM UNIDAD_INTERNA WHERE CODIGO = IN_codigo;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_UI;
/

create or replace PROCEDURE "SP_DEL_USUARIO" (
    IN_id IN USUARIO.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_DEL_USUARIO
   PURPOSE		Ejecuta la eliminación del registro de un USUARIO con el rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_DEL_USUARIO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM USUARIO WHERE ID = IN_id;

    IF hay_registro = 1 THEN
        DELETE FROM USUARIO WHERE ID = IN_id;
    ELSE
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Rut no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_DEL_USUARIO;
/

create or replace PROCEDURE "SP_GET_ADMINISTRADOR" (
    IN_id IN ADMINISTRADOR.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ADMINISTRADOR OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_ADMINISTRADOR
   PURPOSE		Retorna un registro de un ADMINISTRADOR con el id ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_ADMINISTRADOR ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM ADMINISTRADOR WHERE ID = IN_id;

    OPEN OUT_ADMINISTRADOR FOR
        SELECT * FROM ADMINISTRADOR WHERE ID = IN_id;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Id no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_ADMINISTRADOR;
/

create or replace PROCEDURE "SP_GET_ADMINISTRADORES" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ADMINISTRADORES OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_ADMINISTRADORES
   PURPOSE		Retorna todos los registro de ADMINISTRADOR en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_ADMINISTRADORES ejecutado exitósamente 👍';

    OPEN OUT_ADMINISTRADORES FOR
        SELECT * FROM ADMINISTRADOR;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_ADMINISTRADORES;
/

create or replace PROCEDURE "SP_GET_ALL_UI" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_All_UI OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_All_UI
   PURPOSE		Retorna todos los registro de UNIDAD_INTERNA en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_All_UI ejecutado exitósamente 👍';

    OPEN OUT_All_UI FOR
        SELECT * FROM UNIDAD_INTERNA;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_All_UI;
/

create or replace PROCEDURE "SP_GET_ASIGNACION" (
    IN_codigo_ui IN ASIGNACION.CODIGO_UI%TYPE,
    IN_id_usuario IN ASIGNACION.ID_USUARIO%TYPE,
    IN_codigo_tarea IN ASIGNACION.CODIGO_TAREA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ASIGNACION OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_ASIGNACION
   PURPOSE		Retorna un registro de una ASIGNACION con los códigos y el id ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_ASIGNACION ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM ASIGNACION WHERE CODIGO_UI = IN_codigo_ui AND ID_USUARIO = IN_id_usuario AND CODIGO_TAREA = IN_codigo_tarea;

    OPEN OUT_ASIGNACION FOR
        SELECT * FROM ASIGNACION WHERE CODIGO_UI = IN_codigo_ui AND ID_USUARIO = IN_id_usuario AND CODIGO_TAREA = IN_codigo_tarea;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Asignación no registrada';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_ASIGNACION;
/

create or replace PROCEDURE "SP_GET_ASIGNACIONES" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ASIGNACIONES OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_ASIGNACIONES
   PURPOSE		Retorna todos los registro de ASIGNACION en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_ASIGNACIONES ejecutado exitósamente 👍';

    OPEN OUT_ASIGNACIONES FOR
        SELECT * FROM ASIGNACION;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_ASIGNACIONES;
/

create or replace PROCEDURE "SP_GET_CONTRATO" (
    IN_rut IN CONTRATO.RUT_EMPRESA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_CONTRATO OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_CONTRATO
   PURPOSE		Retorna un/los registro(s) de un CONTRATO con los datos del rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_CONTRATO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM CONTRATO WHERE RUT_PERSONA = IN_rut OR RUT_EMPRESA = IN_rut;

    OPEN OUT_CONTRATO FOR
        SELECT * FROM CONTRATO WHERE RUT_PERSONA = IN_rut OR RUT_EMPRESA = IN_rut;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Rut sin contrato(s)';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_CONTRATO;
/

create or replace PROCEDURE "SP_GET_CONTRATOS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_CONTRATOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_CONTRATOS
   PURPOSE		Retorna todos los registro de CONTRATO en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_CONTRATOS ejecutado exitósamente 👍';

    OPEN OUT_CONTRATOS FOR
        SELECT * FROM CONTRATO;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_CONTRATOS;
/

create or replace PROCEDURE "SP_GET_DISENNADOR" (
    IN_id IN DISENNADOR.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_DISENNADOR OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_DISENNADOR
   PURPOSE		Retorna un registro de un DISENNADOR con el id ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_DISENNADOR ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM DISENNADOR WHERE ID = IN_id;

    OPEN OUT_DISENNADOR FOR
        SELECT * FROM DISENNADOR WHERE ID = IN_id;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Id no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_DISENNADOR;
/

create or replace PROCEDURE "SP_GET_DISENNADORES" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_DISENNADORES OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_DISENNADORES
   PURPOSE		Retorna todos los registro de DISENNADOR en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_DISENNADORES ejecutado exitósamente 👍';

    OPEN OUT_DISENNADORES FOR
        SELECT * FROM DISENNADOR;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_DISENNADORES;
/

create or replace PROCEDURE "SP_GET_EMPRESA" (
    IN_RUT IN EMPRESA.RUT%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_EMPRESA OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_EMPRESA
   PURPOSE		Retorna un registro de EMPRESA con el rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_EMPRESA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM EMPRESA WHERE RUT = in_rut;

    OPEN OUT_EMPRESA FOR
        SELECT * FROM EMPRESA WHERE RUT = IN_RUT;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Rut no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_EMPRESA;
/

create or replace PROCEDURE "SP_GET_EMPRESAS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_EMPRESAS OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_EMPRESAS
   PURPOSE		Retorna todos los registros de EMPRESA en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_EMPRESAS ejecutado exitósamente 👍';

    OPEN OUT_EMPRESAS FOR
        SELECT * FROM EMPRESA;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_EMPRESAS;
/

create or replace PROCEDURE               "SP_GET_FLUJO_F" (
    IN_codigo_funcion IN FLUJO_FUNCION.CODIGO_FUNCION%TYPE,
    IN_codigo_proceso IN FLUJO_FUNCION.CODIGO_PROCESO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FLUJO_T OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_FLUJO_F
   PURPOSE		Retorna los registros de FLUJO_FUNCION con los códigos ingresados

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FLUJO_F ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FLUJO_FUNCION WHERE CODIGO_FUNCION = IN_codigo_funcion OR CODIGO_PROCESO = IN_codigo_proceso;

    OPEN OUT_FLUJO_T FOR
        SELECT * FROM FLUJO_FUNCION WHERE CODIGO_FUNCION = IN_codigo_funcion OR CODIGO_PROCESO = IN_codigo_proceso;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Flujo no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FLUJO_F;
/

create or replace PROCEDURE "SP_GET_FLUJOS_F" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FLUJOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_FLUJOS_F
   PURPOSE		Retorna todos los registro de FLUJO_FUNCION en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FLUJOS_F ejecutado exitósamente 👍';

    OPEN OUT_FLUJOS FOR
        SELECT * FROM FLUJO_FUNCION;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FLUJOS_F;
/

create or replace PROCEDURE "SP_GET_FLUJOS_T" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FLUJOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_FLUJOS_T
   PURPOSE		Retorna todos los registro de FLUJO_TAREA en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FLUJOS_T ejecutado exitósamente 👍';

    OPEN OUT_FLUJOS FOR
        SELECT * FROM FLUJO_TAREA;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FLUJOS_T;
/

create or replace PROCEDURE               "SP_GET_FLUJO_T" (
    IN_codigo_funcion IN FLUJO_TAREA.CODIGO_FUNCION%TYPE,
    IN_codigo_tarea IN FLUJO_TAREA.CODIGO_TAREA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FLUJO_T OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_FLUJO_T
   PURPOSE		Retorna los registros de FLUJO_TAREA con los códigos ingresados

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FLUJO_T ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FLUJO_TAREA WHERE CODIGO_FUNCION = IN_codigo_funcion OR CODIGO_TAREA = IN_codigo_tarea;

    OPEN OUT_FLUJO_T FOR
        SELECT * FROM FLUJO_TAREA WHERE CODIGO_FUNCION = IN_codigo_funcion OR CODIGO_TAREA = IN_codigo_tarea;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Flujo no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FLUJO_T;
/

create or replace PROCEDURE "SP_GET_FUNCION" (
    IN_codigo IN FUNCION.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FUNCION OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_FUNCION
   PURPOSE		Retorna un registro de una FUNCION con el código ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FUNCION ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FUNCION WHERE CODIGO = IN_codigo;

    OPEN OUT_FUNCION FOR
        SELECT * FROM FUNCION WHERE CODIGO = IN_codigo;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FUNCION;
/

create or replace PROCEDURE "SP_GET_FUNCIONARIO" (
    IN_id IN FUNCIONARIO.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FUNCIONARIO OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_FUNCIONARIO
   PURPOSE		Retorna un registro de un FUNCIONARIO con el id ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FUNCIONARIO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FUNCIONARIO WHERE ID = IN_id;

    OPEN OUT_FUNCIONARIO FOR
        SELECT * FROM FUNCIONARIO WHERE ID = IN_id;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Id no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FUNCIONARIO;
/

create or replace PROCEDURE "SP_GET_FUNCIONARIOS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FUNCIONARIOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_FUNCIONARIOS
   PURPOSE		Retorna todos los registro de FUNCIONARIO en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FUNCIONARIOS ejecutado exitósamente 👍';

    OPEN OUT_FUNCIONARIOS FOR
        SELECT * FROM FUNCIONARIO;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FUNCIONARIOS;
/

create or replace PROCEDURE "SP_GET_INTEGRANTE" (
    IN_codigo_ui IN INTEGRANTE.CODIGO_UI%TYPE,
    IN_id_usuario IN INTEGRANTE.ID_USUARIO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_INTEGRANTE OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_INTEGRANTE
   PURPOSE		Retorna un registro de un INTEGRANTE con el código y el id ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_INTEGRANTE ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM INTEGRANTE WHERE CODIGO_UI = IN_codigo_ui AND ID_USUARIO = IN_id_usuario;

    OPEN OUT_INTEGRANTE FOR
        SELECT * FROM INTEGRANTE WHERE CODIGO_UI = IN_codigo_ui AND ID_USUARIO = IN_id_usuario;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Integración no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_INTEGRANTE;
/

create or replace PROCEDURE "SP_GET_INTEGRANTES" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_INTEGRANTES OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_INTEGRANTES
   PURPOSE		Retorna todos los registro de INTEGRANTE en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_INTEGRANTES ejecutado exitósamente 👍';

    OPEN OUT_INTEGRANTES FOR
        SELECT * FROM INTEGRANTE;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_INTEGRANTES;
/

create or replace PROCEDURE "SP_GET_PERSONA" (
    IN_RUT IN PERSONA.RUT%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PERSONA OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_PERSONA
   PURPOSE		Retorna un registro de PERSONA con el rut ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PERSONA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PERSONA WHERE RUT = in_rut;

    OPEN OUT_PERSONA FOR
        SELECT * FROM PERSONA WHERE RUT = IN_RUT;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Rut no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PERSONA;
/

create or replace PROCEDURE "SP_GET_PLAZO" (
    IN_codigo_tarea IN PLAZO.CODIGO_TAREA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PLAZO OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_PLAZO
   PURPOSE		Retorna el/los registro(s) de un PLAZO con el código ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PLAZO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PLAZO WHERE CODIGO_TAREA = IN_codigo_tarea;

    OPEN OUT_PLAZO FOR
        SELECT * FROM PLAZO WHERE CODIGO_TAREA = IN_codigo_tarea;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PLAZO;
/

create or replace PROCEDURE "SP_GET_PLAZOS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PLAZOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_PLAZOS
   PURPOSE		Retorna todos los registro de PLAZO en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PLAZOS ejecutado exitósamente 👍';

    OPEN OUT_PLAZOS FOR
        SELECT * FROM PLAZO;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PLAZOS;
/

create or replace PROCEDURE "SP_GET_PROCESO" (
    IN_codigo IN PROCESO.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PROCESO OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_PROCESO
   PURPOSE		Retorna un registro de un PROCESO con el código ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PROCESO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PROCESO WHERE CODIGO = IN_codigo;

    OPEN OUT_PROCESO FOR
        SELECT * FROM PROCESO WHERE CODIGO = IN_codigo;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PROCESO;
/

create or replace PROCEDURE "SP_GET_PROCESOS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PROCESOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_PROCESOS
   PURPOSE		Retorna todos los registro de PROCESO en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PROCESOS ejecutado exitósamente 👍';

    OPEN OUT_PROCESOS FOR
        SELECT * FROM PROCESO;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PROCESOS;
/

create or replace PROCEDURE "SP_GET_PROYECTO" (
    IN_codigo IN PROYECTO.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PROYECTO OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_PROYECTO
   PURPOSE		Retorna un registro de un PROYECTO con el codigo ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PROYECTO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PROYECTO WHERE CODIGO = IN_codigo;

    OPEN OUT_PROYECTO FOR
        SELECT * FROM PROYECTO WHERE CODIGO = IN_codigo;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PROYECTO;
/

create or replace PROCEDURE "SP_GET_PROYECTOS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_PROYECTOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_PROYECTOS
   PURPOSE		Retorna todos los registro de PROYECTO en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_PROYECTOS ejecutado exitósamente 👍';

    OPEN OUT_PROYECTOS FOR
        SELECT * FROM PROYECTO;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_PROYECTOS;
/

create or replace PROCEDURE "SP_GET_TAREA" (
    IN_codigo IN TAREA.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_TAREA OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_TAREA
   PURPOSE		Retorna un registro de una TAREA con el código ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_TAREA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM TAREA WHERE CODIGO = IN_codigo;

    OPEN OUT_TAREA FOR
        SELECT * FROM TAREA WHERE CODIGO = IN_codigo;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_TAREA;
/

create or replace PROCEDURE "SP_GET_TAREAS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_TAREAS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_TAREAS
   PURPOSE		Retorna todos los registro de TAREA en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_TAREAS ejecutado exitósamente 👍';

    OPEN OUT_TAREAS FOR
        SELECT * FROM TAREA;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_TAREAS;
/

create or replace PROCEDURE "SP_GET_UI" (
    IN_codigo IN UNIDAD_INTERNA.CODIGO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_UI OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_UI
   PURPOSE		Retorna un registro de una UNIDAD_INTERNA con el código ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_UI ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM UNIDAD_INTERNA WHERE CODIGO = IN_codigo;

    OPEN OUT_UI FOR
        SELECT * FROM UNIDAD_INTERNA WHERE CODIGO = IN_codigo;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Código no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_UI;
/

create or replace PROCEDURE "SP_GET_USUARIO" (
    IN_id IN USUARIO.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_USUARIO OUT SYS_REFCURSOR
) AS
/**************************************************************************************************************
   NAME:       	SP_GET_USUARIO
   PURPOSE		Retorna un registro de un USUARIO con el id ingresado

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_USUARIO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM USUARIO WHERE ID = IN_id;

    OPEN OUT_USUARIO FOR
        SELECT * FROM USUARIO WHERE ID = IN_id;

    IF hay_registro = 0 THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'Id no registrado';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_USUARIO;
/

create or replace PROCEDURE "SP_GET_USUARIOS" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_USUARIOS OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_USUARIOS
   PURPOSE		Retorna todos los registro de USUARIO en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_USUARIOS ejecutado exitósamente 👍';

    OPEN OUT_USUARIOS FOR
        SELECT * FROM USUARIO;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_USUARIOS;
/

create or replace PROCEDURE "SP_REG_ADMINISTRADOR" (
    in_id IN ADMINISTRADOR.id%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ID_SALIDA OUT ADMINISTRADOR.ID%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_ADMINISTRADOR
   PURPOSE		Procedimiento por el cual se ingresa un ADMINISTRADOR

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_ADMINISTRADOR ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM ADMINISTRADOR WHERE ID = in_id;

    IF (hay_registro = 1) THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'ADMINISTRADOR ya registrado';
    ELSE
        INSERT INTO ADMINISTRADOR(id, creado)
        VALUES (in_id, SYSDATE())
        RETURNING ID INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_ADMINISTRADOR;
/

create or replace PROCEDURE "SP_REG_ASIGNACION" (
    in_codigo_tarea IN ASIGNACION.CODIGO_TAREA%TYPE,
    in_codigo_ui IN ASIGNACION.CODIGO_UI%TYPE,
    in_id_usuario IN ASIGNACION.ID_USUARIO%TYPE,
    in_rol IN ASIGNACION.ROL%TYPE,
    in_estado IN ASIGNACION.ESTADO%TYPE,
    in_nota IN asignacion.nota%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_ASIGNACION
   PURPOSE		Procedimiento por el cual se ingresa o actualiza una ASIGNACION

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_ASIGNACION ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM ASIGNACION WHERE CODIGO_TAREA = in_codigo_tarea AND CODIGO_UI = in_codigo_ui AND ID_USUARIO = in_id_usuario;

    IF (hay_registro = 1) THEN
        UPDATE ASIGNACION
        SET
            ROL = in_rol,
            ESTADO = in_estado,
            NOTA = in_nota,
            MODIFICADA = SYSDATE()
        WHERE CODIGO_TAREA = in_codigo_tarea AND CODIGO_UI = in_codigo_ui AND ID_USUARIO = in_id_usuario;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO ASIGNACION (CODIGO_TAREA, CODIGO_UI, ID_USUARIO, ROL, ESTADO, NOTA, CREADA)
        VALUES (in_codigo_tarea, in_codigo_ui, in_id_usuario, in_rol, in_estado, in_nota, SYSDATE());
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_ASIGNACION;
/

create or replace PROCEDURE "SP_REG_CONTRATO" (
    in_rut_EMPRESA IN CONTRATO.RUT_EMPRESA%TYPE,
    in_rut_PERSONA IN CONTRATO.RUT_PERSONA%TYPE,
    in_salario IN CONTRATO.SALARIO%TYPE,
    in_cargo IN CONTRATO.CARGO%TYPE,
    in_funcion IN CONTRATO.FUNCION%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_CONTRATO
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un CONTRATO

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_CONTRATO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM CONTRATO WHERE RUT_EMPRESA = in_rut_EMPRESA AND RUT_PERSONA = in_rut_PERSONA;

    IF (hay_registro = 1) THEN
        UPDATE CONTRATO
        SET
            SALARIO = in_salario,
            CARGO = in_cargo,
            FUNCION = in_funcion,
            MODIFICADO = SYSDATE()
        WHERE RUT_EMPRESA = in_rut_EMPRESA AND RUT_PERSONA = in_rut_PERSONA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO CONTRATO (RUT_EMPRESA, RUT_PERSONA, SALARIO, CARGO, FUNCION, CREADO)
        VALUES (in_rut_EMPRESA, in_rut_PERSONA, in_salario, in_cargo, in_funcion, SYSDATE());
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_CONTRATO;
/

create or replace PROCEDURE "SP_REG_DISENNADOR" (
    in_id IN DISENNADOR.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ID_SALIDA OUT DISENNADOR.ID%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_DISENNADOR
   PURPOSE		Procedimiento por el cual se ingresa un DISENNADOR

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_DISENNADOR ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM DISENNADOR WHERE ID = in_id;

    IF (hay_registro = 1) THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'DISENNADOR ya registrado';
    ELSE
        INSERT INTO DISENNADOR(id, creado)
        VALUES (in_id, SYSDATE())
        RETURNING ID INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_DISENNADOR;
/

create or replace PROCEDURE "SP_REG_EMPRESA" (
    in_rut IN EMPRESA.RUT%TYPE,
    in_razon_social IN EMPRESA.RAZON_SOCIAL%TYPE,
    in_nombre IN EMPRESA.NOMBRE%TYPE,
    in_giro_comercial IN EMPRESA.GIRO_COMERCIAL%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ID_SALIDA OUT EMPRESA.RUT%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_EMPRESA
   PURPOSE		Procedimiento por el cual se ingresa o actualiza una EMPRESA

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_EMPRESA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM EMPRESA WHERE RUT = in_rut;

    IF (hay_registro = 1) THEN
        UPDATE EMPRESA
        SET
            RAZON_SOCIAL = in_razon_social,
            NOMBRE = in_nombre,
            GIRO_COMERCIAL = in_giro_comercial,
            MODIFICADA = SYSDATE()
        WHERE RUT = in_rut
        RETURNING RUT INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO EMPRESA (RUT, RAZON_SOCIAL, NOMBRE, GIRO_COMERCIAL, CREADA)
        VALUES (in_rut, in_razon_social, in_nombre, in_giro_comercial, SYSDATE())
        RETURNING RUT INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_EMPRESA;
/

create or replace PROCEDURE               "SP_REG_FLUJO_F" (
    in_indice IN FLUJO_FUNCION.INDICE%TYPE,
    in_codigo_proceso IN FLUJO_FUNCION.CODIGO_PROCESO%TYPE,
    in_codigo_FUNCION IN FLUJO_FUNCION.CODIGO_FUNCION%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_index_SALIDA OUT FLUJO_FUNCION.INDICE%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_FLUJO_F
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un FLUJO_FUNCION

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_FLUJO_F ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FLUJO_FUNCION WHERE CODIGO_FUNCION = in_codigo_FUNCION AND CODIGO_PROCESO = in_codigo_proceso;

    IF (hay_registro = 1) THEN
        UPDATE FLUJO_FUNCION
        SET
            INDICE = in_indice,
            MODIFICADO = SYSDATE()
        WHERE CODIGO_FUNCION = in_codigo_FUNCION AND CODIGO_PROCESO = in_codigo_proceso
        RETURNING INDICE INTO OUT_index_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO FLUJO_FUNCION (CODIGO_FUNCION, CODIGO_PROCESO , INDICE, CREADO)
        VALUES (in_codigo_FUNCION, in_codigo_proceso, in_indice, SYSDATE())
        RETURNING INDICE INTO OUT_index_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_FLUJO_F;
/

create or replace PROCEDURE               "SP_REG_FLUJO_T" (
    in_indice IN FLUJO_TAREA.INDICE%TYPE,
    in_codigo_funcion IN FLUJO_TAREA.CODIGO_FUNCION%TYPE,
    in_codigo_tarea IN FLUJO_TAREA.CODIGO_TAREA%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_index_SALIDA OUT FLUJO_TAREA.INDICE%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_FLUJO_T
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un FLUJO_TAREA

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_FLUJO_T ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FLUJO_TAREA WHERE CODIGO_TAREA = in_codigo_tarea AND CODIGO_FUNCION = in_codigo_funcion;

    IF (hay_registro = 1) THEN
        UPDATE FLUJO_TAREA
        SET
            INDICE = in_indice,
            MODIFICADO = SYSDATE()
        WHERE CODIGO_TAREA = in_codigo_tarea AND CODIGO_FUNCION = in_codigo_funcion
        RETURNING INDICE INTO OUT_index_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO FLUJO_TAREA (CODIGO_TAREA, CODIGO_FUNCION, INDICE, CREADO)
        VALUES (in_codigo_tarea, in_codigo_funcion, in_indice, SYSDATE())
        RETURNING INDICE INTO OUT_index_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_FLUJO_T;
/

create or replace PROCEDURE "SP_REG_FUNCION" (
    in_codigo IN FUNCION.CODIGO%TYPE,
    in_nombre IN FUNCION.NOMBRE%TYPE,
    in_descripcion IN FUNCION.DESCRIPCION%TYPE,
    in_estado IN FUNCION.ESTADO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_cod_SALIDA OUT FUNCION.CODIGO%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_FUNCION
   PURPOSE		Procedimiento por el cual se ingresa o actualiza una FUNCION

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_FUNCION ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FUNCION WHERE CODIGO = in_codigo;

    IF (hay_registro = 1) THEN
        UPDATE FUNCION
        SET
            NOMBRE = in_nombre,
            DESCRIPCION = in_descripcion,
            ESTADO = in_estado,
            MODIFICADA = SYSDATE()
        WHERE CODIGO = in_codigo
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO FUNCION (NOMBRE, DESCRIPCION, ESTADO, CREADA)
        VALUES (in_nombre, in_descripcion, in_estado, SYSDATE())
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_FUNCION;
/

create or replace PROCEDURE "SP_REG_FUNCIONARIO" (
    in_id IN FUNCIONARIO.ID%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ID_SALIDA OUT FUNCIONARIO.ID%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_FUNCIONARIO
   PURPOSE		Procedimiento por el cual se ingresa un FUNCIONARIO

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_FUNCIONARIO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM FUNCIONARIO WHERE ID = in_id;

    IF (hay_registro = 1) THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := 'FUNCIONARIO ya registrado';
    ELSE
        INSERT INTO FUNCIONARIO(id, creado)
        VALUES (in_id, SYSDATE())
        RETURNING ID INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_FUNCIONARIO;
/

create or replace PROCEDURE "SP_REG_INTEGRANTE" (
    in_id_usuario IN INTEGRANTE.ID_USUARIO%TYPE,
    in_codigo_ui IN INTEGRANTE.CODIGO_UI%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_INTEGRANTE
   PURPOSE		Procedimiento por el cual se ingresa un INTEGRANTE

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_INTEGRANTE ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM INTEGRANTE WHERE CODIGO_UI = in_codigo_ui AND ID_USUARIO = in_id_usuario;

    IF (hay_registro = 1) THEN
        OUT_GLOSA := 'USUARIO / UNIDAD_INTERNA ya se están integrados';
    ELSE
        INSERT INTO INTEGRANTE (ID_USUARIO, CODIGO_UI)
        VALUES (in_id_usuario, in_codigo_ui);
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_INTEGRANTE;
/

create or replace PROCEDURE "SP_REG_PERSONA" (
    in_rut IN PERSONA.RUT%TYPE,
    in_nombre IN PERSONA.NOMBRE%TYPE,
    in_apellido IN PERSONA.APELLIDO %TYPE,
    in_natalicio IN PERSONA.NATALICIO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ID_SALIDA OUT PERSONA.RUT%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_PERSONA
   PURPOSE		Procedimiento por el cual se ingresa o actualiza una PERSONA

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_PERSONA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PERSONA WHERE RUT = in_rut;

    IF (hay_registro = 1) THEN
        UPDATE PERSONA
        SET
            NOMBRE = in_nombre,
            APELLIDO = in_apellido,
            NATALICIO = in_natalicio,
            modificada = SYSDATE()
        WHERE RUT = in_rut
        RETURNING RUT INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO PERSONA (RUT, NOMBRE, APELLIDO, NATALICIO, creada)
        VALUES (in_rut, in_nombre, in_apellido, in_natalicio, SYSDATE())
        RETURNING RUT INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_PERSONA;
/

create or replace PROCEDURE "SP_REG_PLAZO" (
    in_fecha IN PLAZO.FECHA%TYPE,
    in_codigo_tarea IN PLAZO.CODIGO_TAREA%TYPE,
    in_contador IN PLAZO.contador%TYPE DEFAULT 0,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_cod_SALIDA OUT PLAZO.CODIGO_TAREA%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_PLAZO
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un PLAZO

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_PLAZO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PLAZO WHERE CODIGO_TAREA = in_codigo_tarea AND contador = in_contador;

    IF (hay_registro = 1) THEN
        UPDATE PLAZO
        SET
            FECHA = in_fecha,
            contador = (SELECT NVL(MAX(contador), 0) + 1  FROM PLAZO WHERE CODIGO_TAREA =  in_codigo_tarea),
            modificado = SYSDATE()
        WHERE CODIGO_TAREA = in_codigo_tarea AND contador = in_contador
        RETURNING codigo_tarea INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO PLAZO (codigo_tarea, fecha, contador, creado)
        VALUES (
                   in_codigo_tarea,
                   in_fecha,
                   (SELECT NVL(MAX(contador), 0) + 1 FROM PLAZO WHERE CODIGO_TAREA = in_codigo_tarea),
                   SYSDATE())
        RETURNING codigo_tarea INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_PLAZO;
/

create or replace PROCEDURE "SP_REG_PROCESO" (
    in_codigo IN PROCESO.CODIGO%TYPE,
    in_indice IN PROCESO.INDICE%TYPE,
    in_nombre IN PROCESO.NOMBRE%TYPE,
    in_descripcion IN PROCESO.DESCRIPCION%TYPE,
    in_estado IN PROCESO.ESTADO%TYPE,
    in_codigo_ui IN PROCESO.CODIGO_UI%TYPE,
    in_id_disennador IN PROCESO.ID_DISENNADOR%TYPE,
    in_codigo_proyecto IN PROCESO.CODIGO_PROYECTO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_cod_SALIDA OUT PROCESO.CODIGO%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_PROCESO
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un PROCESO

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_PROCESO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PROCESO WHERE CODIGO = in_codigo;

    IF (hay_registro = 1) THEN
        UPDATE PROCESO
        SET
            INDICE = in_indice,
            NOMBRE = in_nombre,
            DESCRIPCION = in_descripcion,
            ESTADO = in_estado,
            CODIGO_UI = in_codigo_ui,
            MODIFICADO = SYSDATE()
        WHERE CODIGO = in_codigo
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO PROCESO (INDICE, NOMBRE, DESCRIPCION, ESTADO, CREADO, CODIGO_UI, ID_DISENNADOR, CODIGO_PROYECTO)
        VALUES (in_indice, in_nombre, in_descripcion, in_estado, SYSDATE(), in_codigo_ui, in_id_disennador, in_codigo_proyecto)
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_PROCESO;
/

create or replace PROCEDURE "SP_REG_PROYECTO" (
    in_codigo IN PROYECTO.CODIGO%TYPE,
    in_nombre IN PROYECTO.NOMBRE%TYPE,
    in_estado IN PROYECTO.ESTADO%TYPE,
    in_rut_empresa IN PROYECTO.RUT_EMPRESA%TYPE,
    in_id_administrador IN PROYECTO.ID_ADMINISTRADOR%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_cod_SALIDA OUT PROYECTO.CODIGO%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_PROYECTO
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un PROYECTO

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_PROYECTO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM PROYECTO WHERE CODIGO = in_codigo;

    IF (hay_registro = 1) THEN
        UPDATE PROYECTO
        SET
            NOMBRE = in_nombre,
            ESTADO = in_estado,
            RUT_EMPRESA = in_rut_empresa,
            MODIFICADO = SYSDATE()
        WHERE CODIGO = in_codigo
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO PROYECTO (NOMBRE, ESTADO, RUT_EMPRESA, ID_ADMINISTRADOR, CREADO)
        VALUES (in_nombre, in_estado, in_rut_empresa, in_id_administrador, SYSDATE())
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_PROYECTO;
/

create or replace PROCEDURE "SP_REG_TAREA" (
    in_codigo IN TAREA.CODIGO%TYPE,
    in_nombre IN TAREA.NOMBRE%TYPE,
    in_descripcion IN TAREA.DESCRIPCION%TYPE,
    in_estado IN TAREA.ESTADO%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_cod_SALIDA OUT TAREA.CODIGO%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_TAREA
   PURPOSE		Procedimiento por el cual se ingresa o actualiza una TAREA

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_TAREA ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM TAREA WHERE CODIGO = in_codigo;

    IF (hay_registro = 1) THEN
        UPDATE TAREA
        SET
            NOMBRE = in_nombre,
            DESCRIPCION = in_descripcion,
            ESTADO = in_estado,
            MODIFICADA = SYSDATE()
        WHERE CODIGO = in_codigo
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO TAREA (NOMBRE, DESCRIPCION, ESTADO, CREADA)
        VALUES (in_nombre, in_descripcion, in_estado, SYSDATE())
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_TAREA;
/

create or replace PROCEDURE "SP_REG_UI" (
    in_codigo IN UNIDAD_INTERNA.CODIGO%TYPE,
    in_nombre IN UNIDAD_INTERNA.NOMBRE%TYPE,
    in_descripcion IN UNIDAD_INTERNA.DESCRIPCION%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_cod_SALIDA OUT UNIDAD_INTERNA.CODIGO%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_UI
   PURPOSE		Procedimiento por el cual se ingresa o actualiza una UNIDAD_INTERNA

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_UI ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM UNIDAD_INTERNA WHERE CODIGO = in_codigo;

    IF (hay_registro = 1) THEN
        UPDATE UNIDAD_INTERNA
        SET
            NOMBRE = in_nombre,
            DESCRIPCION = in_descripcion,
            MODIFICADA = SYSDATE()
        WHERE CODIGO = in_codigo
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO UNIDAD_INTERNA (NOMBRE, DESCRIPCION, CREADA)
        VALUES (in_nombre, in_descripcion, SYSDATE())
        RETURNING codigo INTO OUT_cod_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_UI;
/

create or replace PROCEDURE "SP_REG_USUARIO" (
    in_rut IN USUARIO.RUT_PERSONA%TYPE,
    in_nombre IN USUARIO.NOMBRE%TYPE,
    in_correo IN USUARIO.CORREO%TYPE,
    in_clave IN USUARIO.CLAVE%TYPE,
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_ID_SALIDA OUT USUARIO.ID%TYPE
) AS

/**************************************************************************************************************
   NAME:       	SP_REG_USUARIO
   PURPOSE		Procedimiento por el cual se ingresa o actualiza un USUARIO

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0			11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

    hay_registro NUMBER(1);

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_REG_USUARIO ejecutado exitósamente 👍';

    SELECT COUNT(*) INTO hay_registro FROM USUARIO WHERE RUT_PERSONA = in_rut;

    IF (hay_registro = 1) THEN
        UPDATE USUARIO
        SET
            NOMBRE = in_nombre,
            CORREO = in_correo,
            CLAVE = in_clave,
            MODIFICADO = SYSDATE()
        WHERE RUT_PERSONA = in_rut
        RETURNING RUT_PERSONA INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha actualizado el registro';
    ELSE
        INSERT INTO USUARIO (NOMBRE, CORREO, CLAVE, RUT_PERSONA, CREADO)
        VALUES (in_nombre, in_correo, in_clave, in_rut, SYSDATE())
        RETURNING ID INTO OUT_ID_SALIDA;
        OUT_GLOSA := OUT_GLOSA || ', se ha ingresado un nuevo registro';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_REG_USUARIO;
/

create or replace PROCEDURE "SP_GET_FUNCIONES" (
    OUT_GLOSA OUT VARCHAR2,
    OUT_ESTADO OUT NUMBER,
    OUT_FUNCIONES OUT SYS_REFCURSOR
) AS

/**************************************************************************************************************
   NAME:       	SP_GET_FUNCIONES
   PURPOSE		Retorna todos los registro de FUNCION en el esquema

   REVISIONS:
   Ver          Date           Author                               Description
   ---------    ----------     -------------------                  ----------------------------------------------
   1.0		    11/05/2020     Jesús José Daniel Murga Fernández	1. Creación del procedimiento almacenado

***************************************************************************************************************/

BEGIN
    OUT_ESTADO := 0;
    OUT_GLOSA := 'SP_GET_FUNCIONES ejecutado exitósamente 👍';

    OPEN OUT_FUNCIONES FOR
        SELECT * FROM FUNCION;

EXCEPTION
    WHEN OTHERS THEN
        OUT_ESTADO := -1;
        OUT_GLOSA := FN_GET_GLOSA_ERROR;

END SP_GET_FUNCIONES;
/
