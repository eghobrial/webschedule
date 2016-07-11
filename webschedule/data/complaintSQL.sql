/* This SQL was generated for a PostgreSQL database. */

create table complaint
(
/* class complaint */
    owner INT8  NOT NULL   ,
    postday TIMESTAMP     ,
    reportername VARCHAR(32) DEFAULT '' NOT NULL   ,
    reporteremail VARCHAR(32) DEFAULT '' NOT NULL   ,
    Time_prob BOOL DEFAULT 0 NOT NULL   ,
    Cables_prob BOOL DEFAULT 0 NOT NULL   ,
    Mess_prob BOOL DEFAULT 0 NOT NULL   ,
    other_prob BOOL DEFAULT 0 NOT NULL   ,
    specify VARCHAR(32) DEFAULT '' NOT NULL   ,
    fault_op INT8     ,
    fault_pi INT8     ,

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


