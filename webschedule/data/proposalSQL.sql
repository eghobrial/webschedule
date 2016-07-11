/* This SQL was generated for a PostgreSQL database. */

create table proposal
(
/* class proposal */
    owner INT8  NOT NULL   ,
    today INTEGER  NOT NULL   ,
    month INTEGER  NOT NULL   ,
    year INTEGER  NOT NULL   ,
    Isucsd BOOL DEFAULT 0 NOT NULL   ,
    Isanimal BOOL DEFAULT 0 NOT NULL   ,
    Issample BOOL DEFAULT 0 NOT NULL   ,
    proj_name VARCHAR(300) DEFAULT '' NOT NULL   ,
    cname VARCHAR(32) DEFAULT '' NOT NULL   ,
    cphone VARCHAR(32) DEFAULT '' NOT NULL   ,
    cemail VARCHAR(32) DEFAULT '' NOT NULL   ,
    CntrOp BOOL DEFAULT 0 NOT NULL   ,
    op1lastname VARCHAR(32) DEFAULT ''    ,
    op1firstname VARCHAR(32) DEFAULT ''    ,
    op1phone VARCHAR(32) DEFAULT ''    ,
    op1email VARCHAR(32) DEFAULT ''    ,
    op2lastname VARCHAR(32) DEFAULT ''    ,
    op2firstname VARCHAR(32) DEFAULT ''    ,
    op2phone VARCHAR(32) DEFAULT ''    ,
    op2email VARCHAR(32) DEFAULT ''    ,
    indexnum VARCHAR(32) DEFAULT ''    ,
    baline1 VARCHAR(32) DEFAULT ''    ,
    baline2 VARCHAR(32) DEFAULT ''    ,
    baline3 VARCHAR(32) DEFAULT ''    ,
    bacity VARCHAR(32) DEFAULT ''    ,
    bast VARCHAR(32) DEFAULT ''    ,
    bazip VARCHAR(32) DEFAULT ''    ,
    fpname VARCHAR(32) DEFAULT ''    ,
    fpphone VARCHAR(32) DEFAULT ''    ,
    fpemail VARCHAR(32) DEFAULT ''    ,
    thours INTEGER     ,
    writeup VARCHAR(6000) DEFAULT ''    ,
    dataanalysis VARCHAR(3000) DEFAULT ''    ,
    IACUCFaxed BOOL DEFAULT 0    ,
    RFCoils BOOL DEFAULT 0    ,
    restraints BOOL DEFAULT 0    ,
    physioeq BOOL DEFAULT 0    ,
    anesthetics BOOL DEFAULT 0    ,
    ancillary BOOL DEFAULT 0    ,
    op1status BOOL DEFAULT 0    ,
    op2status BOOL DEFAULT 0    ,
    IACUC BOOL DEFAULT 0    ,
    status INTEGER     ,
    BioHazard VARCHAR(1500) DEFAULT ''    ,
    stimuli BOOL DEFAULT 0    ,
    AnimalTrans BOOL DEFAULT 0    ,
    bproj_name VARCHAR(32) DEFAULT '' NOT NULL   ,
    contrast BOOL DEFAULT 0    ,
    AnimTransDate DATE     ,
    IACUCDate DATE     ,
    stdate DATE     ,
    expdate DATE     ,
    comments VARCHAR(3000) DEFAULT ''    ,
    nighttime BOOL DEFAULT 0    ,
    copis VARCHAR(32) DEFAULT ''    ,
    intcomments VARCHAR(3000) DEFAULT ''    ,
    response VARCHAR(3000) DEFAULT ''    ,
    animalq1 VARCHAR(200) DEFAULT ''    ,
    animalq2 VARCHAR(200) DEFAULT ''    ,
    animalq3 VARCHAR(200) DEFAULT ''    ,
    animalq4 VARCHAR(200) DEFAULT ''    ,
    proposalq1 VARCHAR(200) DEFAULT ''    ,
    proposalq2 VARCHAR(200) DEFAULT ''    ,
    proposalq3 VARCHAR(200) DEFAULT ''    ,
    proposalq4 VARCHAR(200) DEFAULT ''    ,
    proposalq5 VARCHAR(200) DEFAULT ''    ,
    proposalq6 VARCHAR(200) DEFAULT ''    ,
    proposalq7 VARCHAR(200) DEFAULT ''    ,
    proposalq8 VARCHAR(200) DEFAULT ''    ,
    bookmark VARCHAR(32) DEFAULT ''    ,
    approvalDate DATE     ,
    POnum VARCHAR(32) DEFAULT ''    ,
    institute VARCHAR(32) DEFAULT ''    ,
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


