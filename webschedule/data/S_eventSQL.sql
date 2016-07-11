/* This SQL was generated for a PostgreSQL database. */

create table S_event
(
/* class S_event */
    description VARCHAR(32) DEFAULT '' NOT NULL   ,
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
    todayday INTEGER  NOT NULL   ,
    todaymonth INTEGER  NOT NULL   ,
    todayyear INTEGER  NOT NULL   ,
    isDevelop BOOL DEFAULT 0 NOT NULL   ,
    isRepeat BOOL DEFAULT 0 NOT NULL   ,
    Operator INT8  NOT NULL   ,
    prevowner INT8  NOT NULL   ,
    prevproj_owner INT8  NOT NULL   ,
    IsGrabbable BOOL DEFAULT 0 NOT NULL   ,
    reasondropped VARCHAR(32) DEFAULT ''    ,
    todayh INTEGER  NOT NULL   ,
    todaym INTEGER  NOT NULL   ,

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


