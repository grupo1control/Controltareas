create table EMPRESA
(
    RUT            VARCHAR2(255 char) not null
        primary key,
    CREADA         DATE               not null,
    GIRO_COMERCIAL VARCHAR2(255 char),
    MODIFICADA     DATE,
    NOMBRE         VARCHAR2(255 char) not null,
    RAZON_SOCIAL   VARCHAR2(255 char)
)
/

create table FUNCION
(
    CODIGO      NUMBER(19)         not null
        primary key,
    CREADA      DATE               not null,
    DESCRIPCION VARCHAR2(255 char),
    ESTADO      VARCHAR2(255 char),
    MODIFICADA  DATE,
    NOMBRE      VARCHAR2(255 char) not null
)
/

create table PERSONA
(
    RUT        VARCHAR2(255 char) not null
        primary key,
    APELLIDO   VARCHAR2(255 char) not null,
    CREADA     DATE               not null,
    MODIFICADA DATE,
    NATALICIO  DATE               not null,
    NOMBRE     VARCHAR2(255 char) not null
)
/

create table CONTRATO
(
    CARGO       VARCHAR2(255 char),
    CREADO      DATE               not null,
    FUNCION     VARCHAR2(255 char),
    MODIFICADO  DATE,
    SALARIO     NUMBER(10),
    RUT_PERSONA VARCHAR2(255 char) not null
        constraint FK8HYF3RC6GMHCPL1HAMSV0WFVJ
            references PERSONA,
    RUT_EMPRESA VARCHAR2(255 char) not null
        constraint FKG8YPY3KSGDGKSUEAD0YTRND01
            references EMPRESA,
    primary key (RUT_PERSONA, RUT_EMPRESA)
)
/

create table TAREA
(
    CODIGO      NUMBER(19)         not null
        primary key,
    CREADA      DATE               not null,
    DESCRIPCION VARCHAR2(255 char) not null,
    ESTADO      VARCHAR2(255 char) not null,
    MODIFICADA  DATE,
    NOMBRE      VARCHAR2(255 char) not null
)
/

create table FLUJO_TAREA
(
    INDICE         NUMBER(3)  not null,
    CREADO         DATE       not null,
    MODIFICADO     DATE,
    CODIGO_TAREA   NUMBER(19) not null
        constraint FK9TKUELI94PWRWC0TOX11QHWNY
            references TAREA,
    CODIGO_FUNCION NUMBER(19) not null
        constraint FKPSKBX5LSGVVT5HV5KTVQKJ9N1
            references FUNCION,
    primary key (CODIGO_TAREA, INDICE, CODIGO_FUNCION)
)
/

create table PLAZO
(
    CONTADOR     NUMBER(3)  not null,
    CREADO       DATE       not null,
    FECHA        DATE,
    MODIFICADO   DATE,
    CODIGO_TAREA NUMBER(19) not null
        constraint FKRA2BAAFUJCA0HJY4V5U2DCKDV
            references TAREA,
    primary key (CODIGO_TAREA, CONTADOR)
)
/

create table UNIDAD_INTERNA
(
    CODIGO      NUMBER(19)         not null
        primary key,
    CREADA      DATE               not null,
    DESCRIPCION VARCHAR2(255 char),
    MODIFICADA  DATE,
    NOMBRE      VARCHAR2(255 char) not null
)
/

create table USUARIO
(
    ID          NUMBER(19)         not null
        primary key,
    CLAVE       VARCHAR2(255 char) not null,
    CORREO      VARCHAR2(255 char) not null,
    CREADO      DATE               not null,
    ESTADO      NUMBER(1)          not null,
    MODIFICADO  DATE,
    NOMBRE      VARCHAR2(255 char) not null,
    RUT_PERSONA VARCHAR2(255 char) not null
        constraint FKEY3V539E85J9DHU9FF8WYSBRN
            references PERSONA
)
/

create table ADMINISTRADOR
(
    CREADO     DATE       not null,
    MODIFICADO DATE,
    ID         NUMBER(19) not null
        primary key
        constraint FK2POJW9WEQMKC0476CS86VYYRB
            references USUARIO
)
/

create table DISENNADOR
(
    CREADO     DATE       not null,
    MODIFICADO DATE,
    ID         NUMBER(19) not null
        primary key
        constraint FKIVDLAE8X2NPDG7M6XFX5VK5FV
            references USUARIO
)
/

create table FUNCIONARIO
(
    CREADO     DATE       not null,
    MODIFICADO DATE,
    ID         NUMBER(19) not null
        primary key
        constraint FKJSVLNMYMCPDTN32HFAINPXB7F
            references USUARIO
)
/

create table INTEGRANTE
(
    CREADO     DATE       not null,
    MODIFICADO DATE,
    ID_USUARIO NUMBER(19) not null
        constraint FK33AUO11RA5DNTQKIYI3R94POB
            references USUARIO,
    CODIGO_UI  NUMBER(19) not null
        constraint FKO1GH31YOQLH6IXXCUD5Q6ASL2
            references UNIDAD_INTERNA,
    primary key (ID_USUARIO, CODIGO_UI)
)
/

create table ASIGNACION
(
    CREADA       DATE               not null,
    ESTADO       VARCHAR2(255 char) not null,
    MODIFICADA   DATE,
    NOTA         VARCHAR2(255 char),
    ROL          VARCHAR2(255 char),
    CODIGO_TAREA NUMBER(19)         not null
        constraint FK34R5VI78S0J1PYYVNGM1LK9OP
            references TAREA,
    ID_USUARIO   NUMBER(19)         not null,
    CODIGO_UI    NUMBER(19)         not null,
    primary key (CODIGO_TAREA, ID_USUARIO, CODIGO_UI),
    constraint FKIXK5NWR96E6YOK1BJA64RCTYC
        foreign key (ID_USUARIO, CODIGO_UI) references INTEGRANTE
)
/

create table PROYECTO
(
    CODIGO           NUMBER(19)         not null
        primary key,
    CREADO           DATE               not null,
    ESTADO           VARCHAR2(255 char),
    MODIFICADO       DATE,
    NOMBRE           VARCHAR2(255 char) not null,
    ID_ADMINISTRADOR NUMBER(19)         not null
        constraint FK4BD9AIBKJWSRGX89HI69NGDWM
            references ADMINISTRADOR,
    RUT_EMPRESA      VARCHAR2(255 char)
        constraint FKM66MSTBPB2FLOPL1HSQ7N8N0I
            references EMPRESA
)
/

create table PROCESO
(
    CODIGO          NUMBER(19)         not null
        primary key,
    CREADO          DATE               not null,
    DESCRIPCION     VARCHAR2(255 char),
    ESTADO          VARCHAR2(255 char),
    INDICE          NUMBER(3),
    MODIFICADO      DATE,
    NOMBRE          VARCHAR2(255 char) not null,
    ID_DISENNADOR   NUMBER(19)
        constraint FKKXRKTU4GD7P5J747O1CQTU3Q0
            references DISENNADOR,
    CODIGO_PROYECTO NUMBER(19)         not null
        constraint FK6HVW11431018GD16YWBGYB5G1
            references PROYECTO,
    CODIGO_UI       NUMBER(19)
        constraint FKQ9Q2KL5I88OU5MMGFWCTT3I2O
            references UNIDAD_INTERNA
)
/

create table FLUJO_FUNCION
(
    INDICE         NUMBER(3)  not null,
    CREADO         DATE       not null,
    MODIFICADO     DATE,
    CODIGO_PROCESO NUMBER(19) not null
        constraint FKFY7EIPUPM7QUXA57RGOVKUKDE
            references PROCESO,
    CODIGO_FUNCION NUMBER(19) not null
        constraint FK18FEBAGMMRT3GUWV5ICTAEH2G
            references FUNCION,
    primary key (CODIGO_PROCESO, INDICE, CODIGO_FUNCION)
)
/

