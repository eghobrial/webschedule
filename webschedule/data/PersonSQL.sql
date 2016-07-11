/* This SQL was generated for a PostgreSQL database. */

create table Person
(
/* class Person */
    login VARCHAR(32) DEFAULT '' NOT NULL   ,
    password VARCHAR(32) DEFAULT '' NOT NULL   ,
    lastname VARCHAR(32) DEFAULT '' NOT NULL   ,
    firstname VARCHAR(32) DEFAULT '' NOT NULL   ,
    office VARCHAR(32) DEFAULT '' NOT NULL   ,
    phone VARCHAR(32) DEFAULT '' NOT NULL   ,
    isAdmin BOOL DEFAULT 0 NOT NULL   ,
    isDeveloper BOOL DEFAULT 0 NOT NULL   ,
    email VARCHAR(32) DEFAULT '' NOT NULL   ,
    isAuth BOOL DEFAULT 0 NOT NULL   ,
    isPadmin BOOL DEFAULT 0 NOT NULL   ,

    /* NOTICE: */
    /* This table represents a Data Object class  */
    /* derived from com.lutris.dods.builder.generator.dataobject.GenericDO. */
    /* The class GenericDO contains: */
    /* 1) an ObjectId (oid)     which is stored as a DECIMAL(19,0) */
    /* 2) an int      (version) which is stored as an INTEGER */
    /* When creating Data Objects in DODS, these two columns */
    /* (a.k.a class members, Data Object Attributes) */
    /* should never be defined explicitly. */

    /* These columns are defined here at the end of the */
    /* CREATE TABLE statement to simplify the handling of commas. */
    ObjectId INT8 NOT NULL PRIMARY KEY,
    ObjectVersion INTEGER NOT NULL

);


