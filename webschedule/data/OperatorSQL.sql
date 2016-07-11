/* This SQL was generated for a PostgreSQL database. */

create table Operator
(
/* class Operator */
    FirstName VARCHAR(32) DEFAULT '' NOT NULL   ,
    LastName VARCHAR(32) DEFAULT '' NOT NULL   ,
    certday INTEGER  NOT NULL   ,
    certmonth INTEGER  NOT NULL   ,
    certyear INTEGER  NOT NULL   ,
    lastscanday INTEGER  NOT NULL   ,
    lastscanmonth INTEGER  NOT NULL   ,
    lastscanyear INTEGER  NOT NULL   ,
    email VARCHAR(32) DEFAULT '' NOT NULL   ,
    recertday INTEGER  NOT NULL   ,
    recertmonth INTEGER  NOT NULL   ,
    recertyear INTEGER  NOT NULL   ,
    SFTTS TIMESTAMP  NOT NULL   ,
    isExp BOOL DEFAULT 0 NOT NULL   ,

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


