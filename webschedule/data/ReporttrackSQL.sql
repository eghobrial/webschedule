/* This SQL was generated for a PostgreSQL database. */

create table Reporttrack
(
/* class Reporttrack */
    day INTEGER  NOT NULL   ,
    month INTEGER  NOT NULL   ,
    year INTEGER  NOT NULL   ,
    reportinfo INTEGER  NOT NULL   ,
    startday INTEGER  NOT NULL   ,
    startmonth INTEGER  NOT NULL   ,
    startyear INTEGER  NOT NULL   ,
    endday INTEGER  NOT NULL   ,
    endmonth INTEGER  NOT NULL   ,
    endyear INTEGER  NOT NULL   ,
    preparedby VARCHAR(32) DEFAULT '' NOT NULL   ,
    invoice_num INTEGER  NOT NULL   ,

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


