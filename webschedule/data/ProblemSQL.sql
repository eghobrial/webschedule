/* This SQL was generated for a PostgreSQL database. */

create table Problem
(
/* class Problem */
    owner INT8  NOT NULL   ,
    severitycode INTEGER  NOT NULL   ,
    classcode INTEGER  NOT NULL   ,
    prioritycode INTEGER  NOT NULL   ,
    statuscode INTEGER  NOT NULL   ,
    postday INTEGER  NOT NULL   ,
    postmonth INTEGER  NOT NULL   ,
    postyear INTEGER  NOT NULL   ,
    updateday INTEGER  NOT NULL   ,
    updatemonth INTEGER  NOT NULL   ,
    updateyear INTEGER  NOT NULL   ,
    closeday INTEGER  NOT NULL   ,
    closemonth INTEGER  NOT NULL   ,
    closeyear INTEGER  NOT NULL   ,
    reportername VARCHAR(32) DEFAULT '' NOT NULL   ,
    reporteremail VARCHAR(32) DEFAULT '' NOT NULL   ,
    describ VARCHAR(32) DEFAULT '' NOT NULL   ,
    GE_called BOOL DEFAULT 0 NOT NULL   ,
    problemnum VARCHAR(32) DEFAULT ''    ,
    posth INTEGER  NOT NULL   ,
    postm INTEGER  NOT NULL   ,
    postpm VARCHAR(32) DEFAULT '' NOT NULL   ,
    problemdetail VARCHAR(3000) DEFAULT '' NOT NULL   ,
    fixdetail VARCHAR(6000) DEFAULT '' NOT NULL   ,

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


