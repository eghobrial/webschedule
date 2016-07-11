/* This SQL was generated for a PostgreSQL database. */

create table blocktime
(
/* class Blocktime */
    startth INTEGER  NOT NULL   ,
    starttm INTEGER  NOT NULL   ,
    endth INTEGER  NOT NULL   ,
    endtm INTEGER  NOT NULL   ,
    month INTEGER  NOT NULL   ,
    year INTEGER  NOT NULL   ,
    flag INTEGER  NOT NULL   ,
    description VARCHAR(32) DEFAULT '' NOT NULL   ,
    day INTEGER  NOT NULL   ,

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


