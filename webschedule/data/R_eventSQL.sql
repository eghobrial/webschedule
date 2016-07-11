/* This SQL was generated for a PostgreSQL database. */

create table R_event
(
/* class R_event */
    starth INTEGER  NOT NULL   ,
    startm INTEGER  NOT NULL   ,
    endh INTEGER  NOT NULL   ,
    endm INTEGER  NOT NULL   ,
    eventday INTEGER  NOT NULL   ,
    eventmonth INTEGER  NOT NULL   ,
    eventyear INTEGER  NOT NULL   ,
    eventdayofw INTEGER  NOT NULL   ,
    owner INT8  NOT NULL   ,
    proj_owner INT8  NOT NULL   ,
    cancelday INTEGER  NOT NULL   ,
    cancelmonth INTEGER  NOT NULL   ,
    cancelyear INTEGER  NOT NULL   ,
    cancelh INTEGER  NOT NULL   ,
    canelm INTEGER  NOT NULL   ,
    isUsed INTEGER  NOT NULL   ,
    Operator INT8  NOT NULL   ,
    isLast BOOL DEFAULT 0 NOT NULL   ,

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


