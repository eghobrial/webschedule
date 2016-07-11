/* This SQL was generated for a PostgreSQL database. */

create table Project
(
/* class Project */
    proj_name VARCHAR(32) DEFAULT '' NOT NULL   ,
    Description VARCHAR(32) DEFAULT '' NOT NULL   ,
    indexnum VARCHAR(32) DEFAULT '' NOT NULL   ,
    codeofpay INTEGER  NOT NULL   ,
    password VARCHAR(32) DEFAULT '' NOT NULL   ,
    totalhours DOUBLE  NOT NULL   ,
    donehours DOUBLE     ,
    owner INT8  NOT NULL   ,
    contactname VARCHAR(32) DEFAULT '' NOT NULL   ,
    contactphone VARCHAR(32) DEFAULT '' NOT NULL   ,
    billaddr1 VARCHAR(32) DEFAULT '' NOT NULL   ,
    billaddr2 VARCHAR(32) DEFAULT '' NOT NULL   ,
    billaddr3 VARCHAR(32) DEFAULT '' NOT NULL   ,
    city VARCHAR(32) DEFAULT '' NOT NULL   ,
    state VARCHAR(32) DEFAULT '' NOT NULL   ,
    zip VARCHAR(32) DEFAULT '' NOT NULL   ,
    accountid VARCHAR(32) DEFAULT '' NOT NULL   ,
    isoutside BOOL DEFAULT 0 NOT NULL   ,
    expday INTEGER  NOT NULL   ,
    expmonth INTEGER  NOT NULL   ,
    expyear INTEGER  NOT NULL   ,
    notifycontact VARCHAR(150) DEFAULT '' NOT NULL   ,
    isExp BOOL DEFAULT 0 NOT NULL   ,
    canCredit INTEGER  NOT NULL   ,
    Institute VARCHAR(32) DEFAULT '' NOT NULL   ,
    proposalID VARCHAR(32) DEFAULT ''    ,
    fpemail VARCHAR(32) DEFAULT ''    ,
    POnum VARCHAR(32) DEFAULT ''    ,
    POexpdate DATE     ,
    POhours DOUBLE     ,
    IACUCDate DATE     ,
    Modifiedby VARCHAR(32) DEFAULT ''    ,
    ModDate DATE     ,
    Notes VARCHAR(3000) DEFAULT ''    ,
    IRBnum VARCHAR(32) DEFAULT ''    ,

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


