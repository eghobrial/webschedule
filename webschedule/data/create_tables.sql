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


/* This SQL was generated for a PostgreSQL database. */

create table Downtime
(
/* class Downtime */
    starth INTEGER  NOT NULL   ,
    startm INTEGER  NOT NULL   ,
    endh INTEGER  NOT NULL   ,
    endm INTEGER  NOT NULL   ,
    day INTEGER  NOT NULL   ,
    month INTEGER  NOT NULL   ,
    year INTEGER  NOT NULL   ,
    description VARCHAR(32) DEFAULT '' NOT NULL   ,

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


/* This SQL was generated for a PostgreSQL database. */

create table Operates
(
/* class Operates */
    operator INT8  NOT NULL   ,
    project INT8  NOT NULL   ,
    isExp BOOL DEFAULT 0    ,

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


/* This SQL was generated for a PostgreSQL database. */

create table C_event
(
/* class C_event */
    eventday INTEGER  NOT NULL   ,
    eventm INTEGER  NOT NULL   ,
    eventy INTEGER  NOT NULL   ,
    starth INTEGER  NOT NULL   ,
    startm INTEGER  NOT NULL   ,
    endh INTEGER  NOT NULL   ,
    endm INTEGER  NOT NULL   ,
    todayd INTEGER  NOT NULL   ,
    todaym INTEGER  NOT NULL   ,
    owner INT8  NOT NULL   ,
    proj_owner INT8  NOT NULL   ,
    todayy INTEGER  NOT NULL   ,
    cancelc INTEGER  NOT NULL   ,
    todayh INTEGER  NOT NULL   ,
    todaymin INTEGER  NOT NULL   ,

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


create table objectid(
 next INT8 NOT NULL 
);
