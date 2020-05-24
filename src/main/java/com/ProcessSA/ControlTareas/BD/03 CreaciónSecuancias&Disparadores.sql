--------------------------------------------------------
--  DDL for Sequences
--------------------------------------------------------

create sequence SEQ_COD_FUNCION
    minvalue 0
    order
/

create sequence SEQ_COD_PROCESO
    minvalue 0
    order
    nocache
/

create sequence SEQ_COD_PROYECTO
    minvalue 0
    order
    nocache
/

create sequence SEQ_COD_TAREA
    minvalue 0
    order
    nocache
/

create sequence SEQ_COD_UI
    minvalue 0
    order
    nocache
/

create sequence SEQ_ID_USUARIO
    minvalue 0
    order
    nocache
/


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