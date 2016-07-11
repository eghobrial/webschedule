/*-----------------------------------------------------------------------------
 * Enhydra Java Application Server
 * Copyright 1997-2000 Lutris Technologies, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *      This product includes Enhydra software developed by Lutris
 *      Technologies, Inc. and its contributors.
 * 4. Neither the name of Lutris Technologies nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY LUTRIS TECHNOLOGIES AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL LUTRIS TECHNOLOGIES OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *-----------------------------------------------------------------------------
 * /home/emang/myapps/webschedule/webschedule/data/ReporttrackDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import java.math.*;
import java.util.Hashtable;
import java.util.Enumeration;

import com.lutris.util.Config;
import com.lutris.util.KeywordValueTable;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.dataobject.GenericDO;
import com.lutris.dods.builder.generator.query.*;


/**
 * Data core class, used to set, retrieve the ReporttrackDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class ReporttrackDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

    /**
     * static final data members name the table and columns for this DO.
     * By using these members with an instance of the QueryBuilder class,
     * an application can perform straight SQL queries while retaining
     * compile-time checking of table and column usage.
     *
     * Example:  List the Cities containing Persons named Bob:
     *
     *    Using straight SQL with QueryBuilder:
     *      Pro: code runs faster because you create fewer objects
     *      Con: code is less clear
     *
     *         Vector fields = new Vector();
     *         fields.addElement( AddressDO.City );
     *         QueryBuilder qb = new QueryBuilder( fields );
     *         qb.addWhere( PersonDO.FirstName, "Bob" );
     *         qb.addWhere( PersonDO.PrimaryKey, AddressDO.Person );
     *         RDBRow row;
     *         while ( null != ( row = qb.getNextRow() ) ) {
     *             String city = row.get( AddressDO.City ).getString();
     *         }
     *
     *    Using Query/DO classes:
     *      Pro: code is (often) clearer
     *      Con: code runs slower because you create more objects
     *
     *         PersonQuery pq = new PersonQuery();
     *         pq.setQueryFirstName( "Bob" );
     *         PersonDO[] bobs = pq.getDOArray();
     *         for ( int i = 0; i < bobs.length; i++ ) {
     *             AddressQuery aq = new AddressQuery();
     *             aq.setQueryPerson( bobs[i] );
     *             AddressDO addr = aq.getNextDO();
     *             String city = addr.getCity();
     *         }
     */
    static public final RDBTable  table = new RDBTable( "Reporttrack" );

    /**
     * Return Reporttrack as the name of the table in the database
     * which contains ReporttrackDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "Reporttrack";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for ReporttrackDO attributes are defined below. */

    /* Using a DO (and its Query class) to access a VIEW instead of a TABLE:
     *
     * A DO (and its Query class) can be used to access a VIEW
     * instead of a TABLE.  The Data Object is created as usual in DODS,
     * but the "create table" SQL command for that DO is not used.
     * Instead, you substitute a "create view" command to create a
     * virtual table in the database; this is often done to provide
     * convenient access to a collection of tables joined together.
     *
     * A VIEW usually does not return "oid" and "version" columns;
     * often (but now always) a VIEW is defined to return the "oid" column
     * for one of the tables joined together in the definition of the VIEW.
     * If the isView flag is true, ReporttrackDO.createExisting(ResultSet)
     * will NOT invoke the GenericDO(ResultSet) constructor
     * so to avoid attempting to extract the "oid" and "version" columns
     * from the ResultSet.
     *
     * A future release of DODS will allow this flag to be set from the GUI.
     * In the meantime, if this DO class represents a VIEW,
     * change the isView flag to true.
     */
    static protected final boolean isView = false;

    /**
     * A DO class contains a reference to a DataStruct class.
     * This reference can be null (when the data for the DO
     * has not yet been retrieved from the database),
     * allowing a DO object to be a lightweight placeholder
     * until its data is needed.
     */
    private ReporttrackDataStruct data = null;

    /**
     * isReadOnly()
     * Returns true if the data for this object has been marked read-only.
     */
    public boolean isReadOnly() {
	if ( null == data ) return false;
	return data.readOnly;
    }

    /**
     * Protected constructor.  Only derived classes should call it.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     */
    protected ReporttrackDO ( boolean is_view )
    throws ObjectIdException, DatabaseManagerException {
        super( is_view );
    }

    /**
     * Protected constructor.  Only derived classes should call it.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     */
    protected ReporttrackDO ()
    throws ObjectIdException, DatabaseManagerException {
        super( isView );
    }

    /**
     * isLoaded()
     * Returns true if the data for this objects has been retrieved
     * from the database.
     */
    public boolean isLoaded() {
	return ( null != data );
    }

    /**
     * loadData()
     * Load the fields for the DO from the database.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public void loadData()
    throws SQLException, ObjectIdException, DataObjectException
    {
	if ( null == data ) {
	    
	    data = new ReporttrackDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	ReporttrackQuery query = new ReporttrackQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	ReporttrackDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "ReporttrackDO DO not found for id=" + id );
	    makeIdentical(obj);
	    setVersion(    obj.getVersion() );
	    setNewVersion( obj.getVersion() );

	} catch ( NonUniqueQueryException e ) {
	    throw new ObjectIdException( "Duplicate ObjectId" );
	}

    }
    /**
     * Load the actual DO data if necessary.
     * Called by get/set methods.
     *
     * @exception DataObjectException If a data access error occurs.
     */
    private void checkLoad()
    throws DataObjectException {
	if ( null == data )
	    try {
		loadData();
	    } catch ( Exception e ) {
		throw new DataObjectException(
		    "Unable to load data for ReporttrackDO id=" + getOId() +
		    ", error = " + e.getMessage() );
	    }
    }

    /**
     * The developer of a Business Object that derives from this class
     * can override the methods:<CODE>
     *     beforeAnyGet
     *     beforeAnySet
     *     afterAnySet
     * </CODE> to handle any general assertions or cleanup needed
     * for <CODE>get</CODE> and <CODE>set</CODE> methods.
     */
    protected void beforeAnyGet() {
    }
    // beforeAnySet throws plain Exception to allow overriding implementations
    // to throw any flavor needed.
    protected void beforeAnySet() throws Exception {
	if ( isReadOnly() )
	    throw new DataObjectException( this + " is read-only." );
    }
    protected void afterAnySet() {
    }

    /**
     * Protected constructor used by createExisting(ObjectId) above.
     *
     * Historical note (delete at will):
     * Formerly, createExisting(ObjectId) invoked the no-args GenericDO ctor,
     * which allocated a new ObjectId.  Then, createExisting(ObjectId)
     * would call setOId(id), discarding the newly allocated ObjectId;
     * this resulted in an ObjectId "leak" (needless consumption of oid's.)
     *
     * @param id The ObjectId for the object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   Should never see this exception since GenericDO.ctor(ObjectId)
     *   never accesses the database.
     */
    protected ReporttrackDO( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	super( id );
    }





    /**
     * createVirgin()
     * Creates a DO that has no ObjectId or data.
     * Such a DO is used to insert a new database entry
     * after its data has been set.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static ReporttrackDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new ReporttrackDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a ReporttrackDO object by searching for it
     * in the database using the passed BigDecimal value as the primary key.
     *
     * Creates a DO that represents an existing entry in the database.
     * Such a DO is used to examine and possibly update such an entry.
     * createExisting() is called only from the code that retrieves
     * an ObjectId from a ResultSet (database query result).
     * createExisting() is protected because no other DO or BO should ever
     * need to call it.
     * FIX unfortunately the createExisting(BigDecimal) form *does* need
     * to be public because it is called by the public ctors of other DOs.
     * For example,
     *    AaaDO contains a ref to a BbbDO,
     *    so there is a method AaaDO.setBbb(BbbDO).
     *    In the ctor AaaDO(ResultSet), we have the call
     *       setBbb( BbbDO.createExisting( rs.getBigDecimal( "bbb", 0 )));
     * Since AaaDO is not in the same package as BbbDO,
     * BbbDO.createExisting(BigDecimal) must be public, not protected.
     * Java needs the C++ 'friend' idea.
     *
     * @param bd The BigDecimal representation of the ObjectId for the object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public static ReporttrackDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>ReporttrackDO</CODE>
     * from a string handle.
     */
    public static ReporttrackDO createExisting( String handle ) {
	ReporttrackDO ret = null;
        try {
            BigDecimal bd = new BigDecimal( handle );
	    ret = createExisting( bd );
        } catch ( Exception e ) {
        }
	return ret;
    }

    /**
     * createExisting( ObjectId )
     *
     * Factory method creates a ReporttrackDO object by searching for it
     * in the database using the passed ObjectID value as the primary key.
     *
     * @param id The ObjectId for the object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ReporttrackDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	ReporttrackDO ret = null;
	ret = new ReporttrackDO( id );
	ret.setPersistent( true );  // mark DO as persistent (preexisting)
	if ( ! false ) // If not lazy-loading, fetch DO data now.
	    ret.loadData();
	// unset the GenericDO.dirty flag.
	ret.markClean();
	return ret;
    }

    /**
     * createExisting( ResultSet )
     *
     * Factory method used to create an instance of this class to
     * represent a Data Object already existing in the database.
     *
     * @param rs The ResultSet returned by the Query class for
     * an existing Data Object stored in the database.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ReporttrackDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	ReporttrackDO ret = null;
	if ( isView ) {
	    ret = new ReporttrackDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new ReporttrackDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a ReporttrackDO object by searching for it
     * in the database using the ReporttrackDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a ReporttrackDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ReporttrackDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( ReporttrackDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create ReporttrackDO, row does not " +
		"contain ReporttrackDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a ReporttrackDO object by searching for it
     * in the database using the passed ReporttrackDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a ReporttrackDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ReporttrackDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( ReporttrackDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create ReporttrackDO, " +
		"RDBColumnValue is not ReporttrackDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create ReporttrackDO, bad primary key." );
        }
	if ( null == bd )
            return null;
	return createExisting( bd );
    }

    /**
     * createCopy()
     * Creates a DO that has no ObjectId
     * but has a copy of an existing DO's data.
     * Such a DO is used to insert a new database entry
     * that is largely similar to an existing entry.
     *
     * @param data The data struct to copy values from.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static ReporttrackDO createCopy( ReporttrackDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	ReporttrackDO ret = new ReporttrackDO ();
	ret.data = ( ReporttrackDataStruct ) data.duplicate();
	return ret;
    }

    /**
     * createCopy()
     * Creates a DO that has no ObjectId
     * but has a copy of an existing DO's data.
     * Such a DO is used to insert a new database entry
     * that is largely similar to an existing entry.
     *
     * @param orig The original DO to copy.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static ReporttrackDO createCopy( ReporttrackDO orig )
    throws DatabaseManagerException, ObjectIdException {
	ReporttrackDO ret = new ReporttrackDO ();
	if ( null != orig.data ) {
	    ret.data = ( ReporttrackDataStruct ) orig.data.duplicate();
	}
	return ret;
    }

    /**
     * reload()
     * Causes the DO to refresh itself from the database
     * the next time a set or get method is called.
     */
    public void reload() {
	data = null;
    }


    /**
     * The methods <CODE>
     *     getHandle
     *     hasMatchingHandle
     * </CODE> are used by Presentation Objects that need to populate
     * HTML select lists with Data Objects as options.
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     *
     * @return id of this DO as a string
     *   If an object id can't be allocated for this object.
     */
    public        String  getHandle()
    throws DatabaseManagerException {
	String ret = null;
	    if ( null == getOId() )
		   throw new DatabaseManagerException( "ID not set" );
	    ret = getOId().toString();
        return ret;
    }

   /**
     * hasMatchingHandle
     *
     * @param handle
     *   <CODE>String</CODE> version of DO id
     * @return boolean
     *   True if the string version of the id of this DO matches passed handle
     * @see getHandle
     */
    public        boolean hasMatchingHandle( String handle ) {
	boolean ret = false;
	    if ( null == getOId() )
		   ret = false;
	    else
		   ret = getOId().toString().equals( handle );
        return ret;
    }



    /**
     * makeIdentical()
     *
     * Assigns the DataStruct of an existing DO to this DO.
     * Does not duplicate data. Just assigns the reference.
     *
     * @param orig The original DO.
     *
     */
    protected void makeIdentical( ReporttrackDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member Day

   /* static final RDBColumn Day for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Day = 
			    new RDBColumn( table, "day" );

   /**
    * Get day of the Reporttrack
    *
    * @return day of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getDay () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.day;
   }

   /**
    * Set day of the Reporttrack
    *
    * @param day of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setDay ( int day )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.day =  markNewValue(
	data.day, day  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Month

   /* static final RDBColumn Month for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Month = 
			    new RDBColumn( table, "month" );

   /**
    * Get month of the Reporttrack
    *
    * @return month of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getMonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.month;
   }

   /**
    * Set month of the Reporttrack
    *
    * @param month of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setMonth ( int month )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.month =  markNewValue(
	data.month, month  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Year

   /* static final RDBColumn Year for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Year = 
			    new RDBColumn( table, "year" );

   /**
    * Get year of the Reporttrack
    *
    * @return year of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getYear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.year;
   }

   /**
    * Set year of the Reporttrack
    *
    * @param year of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setYear ( int year )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.year =  markNewValue(
	data.year, year  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Reportinfo

   /* static final RDBColumn Reportinfo for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Reportinfo = 
			    new RDBColumn( table, "reportinfo" );

   /**
    * Get reportinfo of the Reporttrack
    *
    * @return reportinfo of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getReportinfo () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.reportinfo;
   }

   /**
    * Set reportinfo of the Reporttrack
    *
    * @param reportinfo of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setReportinfo ( int reportinfo )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.reportinfo =  markNewValue(
	data.reportinfo, reportinfo  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Startday

   /* static final RDBColumn Startday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Startday = 
			    new RDBColumn( table, "startday" );

   /**
    * Get startday of the Reporttrack
    *
    * @return startday of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.startday;
   }

   /**
    * Set startday of the Reporttrack
    *
    * @param startday of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStartday ( int startday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.startday =  markNewValue(
	data.startday, startday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Startmonth

   /* static final RDBColumn Startmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Startmonth = 
			    new RDBColumn( table, "startmonth" );

   /**
    * Get startmonth of the Reporttrack
    *
    * @return startmonth of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.startmonth;
   }

   /**
    * Set startmonth of the Reporttrack
    *
    * @param startmonth of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStartmonth ( int startmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.startmonth =  markNewValue(
	data.startmonth, startmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Startyear

   /* static final RDBColumn Startyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Startyear = 
			    new RDBColumn( table, "startyear" );

   /**
    * Get startyear of the Reporttrack
    *
    * @return startyear of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.startyear;
   }

   /**
    * Set startyear of the Reporttrack
    *
    * @param startyear of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStartyear ( int startyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.startyear =  markNewValue(
	data.startyear, startyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Endday

   /* static final RDBColumn Endday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Endday = 
			    new RDBColumn( table, "endday" );

   /**
    * Get endday of the Reporttrack
    *
    * @return endday of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.endday;
   }

   /**
    * Set endday of the Reporttrack
    *
    * @param endday of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEndday ( int endday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.endday =  markNewValue(
	data.endday, endday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Endmonth

   /* static final RDBColumn Endmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Endmonth = 
			    new RDBColumn( table, "endmonth" );

   /**
    * Get endmonth of the Reporttrack
    *
    * @return endmonth of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.endmonth;
   }

   /**
    * Set endmonth of the Reporttrack
    *
    * @param endmonth of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEndmonth ( int endmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.endmonth =  markNewValue(
	data.endmonth, endmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Endyear

   /* static final RDBColumn Endyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Endyear = 
			    new RDBColumn( table, "endyear" );

   /**
    * Get endyear of the Reporttrack
    *
    * @return endyear of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.endyear;
   }

   /**
    * Set endyear of the Reporttrack
    *
    * @param endyear of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEndyear ( int endyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.endyear =  markNewValue(
	data.endyear, endyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Preparedby

   /* static final RDBColumn Preparedby for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Preparedby = 
			    new RDBColumn( table, "preparedby" );

   /**
    * Get preparedby of the Reporttrack
    *
    * @return preparedby of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPreparedby () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.preparedby;
   }

   /**
    * Set preparedby of the Reporttrack
    *
    * @param preparedby of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPreparedby ( String preparedby )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.preparedby =  markNewValue(
	data.preparedby, preparedby , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Invoice_num

   /* static final RDBColumn Invoice_num for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Invoice_num = 
			    new RDBColumn( table, "invoice_num" );

   /**
    * Get invoice_num of the Reporttrack
    *
    * @return invoice_num of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getInvoice_num () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.invoice_num;
   }

   /**
    * Set invoice_num of the Reporttrack
    *
    * @param invoice_num of the Reporttrack
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setInvoice_num ( int invoice_num )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.invoice_num =  markNewValue(
	data.invoice_num, invoice_num  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   

    /**
     * Protected constructor.
     *
     * @param rs
     *   Result set from which to obtain product data.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected ReporttrackDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from Reporttrack.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new ReporttrackDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setDay( 
	    
		rs.getInt( 
			"day"  )
	    
	);
	
	
	setMonth( 
	    
		rs.getInt( 
			"month"  )
	    
	);
	
	
	setYear( 
	    
		rs.getInt( 
			"year"  )
	    
	);
	
	
	setReportinfo( 
	    
		rs.getInt( 
			"reportinfo"  )
	    
	);
	
	
	setStartday( 
	    
		rs.getInt( 
			"startday"  )
	    
	);
	
	
	setStartmonth( 
	    
		rs.getInt( 
			"startmonth"  )
	    
	);
	
	
	setStartyear( 
	    
		rs.getInt( 
			"startyear"  )
	    
	);
	
	
	setEndday( 
	    
		rs.getInt( 
			"endday"  )
	    
	);
	
	
	setEndmonth( 
	    
		rs.getInt( 
			"endmonth"  )
	    
	);
	
	
	setEndyear( 
	    
		rs.getInt( 
			"endyear"  )
	    
	);
	
	
	setPreparedby( 
	    
		rs.getString( 
			"preparedby"  )
	    
	);
	
	
	setInvoice_num( 
	    
		rs.getInt( 
			"invoice_num"  )
	    
	);
	

 
        markClean();
    }        


    

    

    private int[] param = null;

    /**
     * Prepares the statement used to insert this object
     * into the database.
     * @param conn the database connection.
     * @return the insert statement.
     * @exception java.sql.SQLException if an error occurs.
     */
    public PreparedStatement getInsertStatement(DBConnection conn)
            throws SQLException {
	/* 
	 * It would probably be better to have CoreDO implement
	 * 	void addToCache(CoreDO DO) {}
	 * and have each DO that has caching enabled override it as
	 *      void addToCache(CoreDO DO) { cache.put( DO.getOId(), DO ); }
	 * and change CoreDO to invoke addToCache()
	 * when it invokes getInsertStatement().
	 */

        ObjectId oid;

        PreparedStatement stmt = conn.prepareStatement( 
	    "insert into Reporttrack ( day, month, year, reportinfo, startday, startmonth, startyear, endday, endmonth, endyear, preparedby, invoice_num, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_int( stmt, param,
		getDay() );
	setPrepStmtParam_int( stmt, param,
		getMonth() );
	setPrepStmtParam_int( stmt, param,
		getYear() );
	setPrepStmtParam_int( stmt, param,
		getReportinfo() );
	setPrepStmtParam_int( stmt, param,
		getStartday() );
	setPrepStmtParam_int( stmt, param,
		getStartmonth() );
	setPrepStmtParam_int( stmt, param,
		getStartyear() );
	setPrepStmtParam_int( stmt, param,
		getEndday() );
	setPrepStmtParam_int( stmt, param,
		getEndmonth() );
	setPrepStmtParam_int( stmt, param,
		getEndyear() );
	setPrepStmtParam_String( stmt, param,
		getPreparedby() );
	setPrepStmtParam_int( stmt, param,
		getInvoice_num() );


	    /* The order of the values being inserted must match
	     * the order of the columns listed in the CREATE TABLE command
	     * that appears in the .sql file for this DO.
	     * Since the id and version number are always the last columns
	     * listed in the CREATE TABLE command, their values are added
	     * to the PreparedStatement after all other values.
	     */
	    setPrepStmtParam_BigDecimal( stmt, param, getOId().toBigDecimal() );
	    setPrepStmtParam_int(        stmt, param, getNewVersion() );

	} catch ( Exception e ) {
	    throw new SQLException( "Data Object error: " + e.getMessage() );
	}

        return stmt;
    }

    /**
     * Prepares the statement used to update this object
     * in the database.
     * @param conn the database connection
     * @return the update statement.
     * @exception java.sql.SQLException if an error occurs.
     */
    public PreparedStatement getUpdateStatement(DBConnection conn)
            throws SQLException {

        ObjectId oid;

        PreparedStatement stmt = conn.prepareStatement(
	    "update Reporttrack set " + getVersionColumnName() + " = ?, day = ?, month = ?, year = ?, reportinfo = ?, startday = ?, startmonth = ?, startyear = ?, endday = ?, endmonth = ?, endyear = ?, preparedby = ?, invoice_num = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_int( stmt, param,
		getDay() );
	setPrepStmtParam_int( stmt, param,
		getMonth() );
	setPrepStmtParam_int( stmt, param,
		getYear() );
	setPrepStmtParam_int( stmt, param,
		getReportinfo() );
	setPrepStmtParam_int( stmt, param,
		getStartday() );
	setPrepStmtParam_int( stmt, param,
		getStartmonth() );
	setPrepStmtParam_int( stmt, param,
		getStartyear() );
	setPrepStmtParam_int( stmt, param,
		getEndday() );
	setPrepStmtParam_int( stmt, param,
		getEndmonth() );
	setPrepStmtParam_int( stmt, param,
		getEndyear() );
	setPrepStmtParam_String( stmt, param,
		getPreparedby() );
	setPrepStmtParam_int( stmt, param,
		getInvoice_num() );


	    /* When updating a persistent object, the UPDATE_WHERE_CLAUSE tag
	     * used to build the SQL WHERE clause (above) uses the 
	     * DO's id and version number to locate the correct row in
	     * the database table.
	     */
	    setPrepStmtParam_BigDecimal( stmt, param, getOId().toBigDecimal() );
	    setPrepStmtParam_int(        stmt, param, getVersion() );

	} catch ( Exception e ) {
	    throw new SQLException( "Data Object error: " + e.getMessage() );
	}

        return stmt;
    }

    /**
     * Prepares the statement used to delete this object
     * from the database.
     * @param conn the database connection
     * @return the delete statement.
     * @exception java.sql.SQLException if an error occurs.
     */
    public PreparedStatement getDeleteStatement(DBConnection conn)
            throws SQLException {

        String sql =
            "delete from Reporttrack \n" +
            "where " + getOIdColumnName() + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBigDecimal(1, getOId().toBigDecimal());
        return stmt;
    }

    

    /*
     * toString - for debugging
     */
/*
    public String toString(){
	String str = "ReporttrackDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "day=" + data.day
+ "\n" + indent + "month=" + data.month
+ "\n" + indent + "year=" + data.year
+ "\n" + indent + "reportinfo=" + data.reportinfo
+ "\n" + indent + "startday=" + data.startday
+ "\n" + indent + "startmonth=" + data.startmonth
+ "\n" + indent + "startyear=" + data.startyear
+ "\n" + indent + "endday=" + data.endday
+ "\n" + indent + "endmonth=" + data.endmonth
+ "\n" + indent + "endyear=" + data.endyear
+ "\n" + indent + "preparedby=" + data.preparedby
+ "\n" + indent + "invoice_num=" + data.invoice_num
;
        return str + "; " + super.toString();
    }
*/

    /*
     * toString - for debugging
     */
    public String toString(){
        return toString( 1 );
    }
    public String toString( int indentCount ){
        String indent = "";
        for ( int i = 0; i < indentCount; i++ ) {
            indent += ". ";
        }
        String str = indent + "ReporttrackDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "day=" + data.day
+ "\n" + indent + "month=" + data.month
+ "\n" + indent + "year=" + data.year
+ "\n" + indent + "reportinfo=" + data.reportinfo
+ "\n" + indent + "startday=" + data.startday
+ "\n" + indent + "startmonth=" + data.startmonth
+ "\n" + indent + "startyear=" + data.startyear
+ "\n" + indent + "endday=" + data.endday
+ "\n" + indent + "endmonth=" + data.endmonth
+ "\n" + indent + "endyear=" + data.endyear
+ "\n" + indent + "preparedby=" + data.preparedby
+ "\n" + indent + "invoice_num=" + data.invoice_num
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    



    /**
     * A stub method for implementing pre-commit assertions 
     * for this ReporttrackDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this ReporttrackDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for deletion from the database.
     */
    protected void okToDelete() 
    throws RefAssertionException { }

  /**
   * Inserts/Updates the DO into its table.
   *
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void commit() 
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( null, false );
  }

  /**
   * Inserts/Updates the DO into its table.
   * The transaction is likely provided by the commit() method of another DO
   * which references this DO.
   * 
   * @param dbt The transaction object to use for this operation.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception com.lutris.appserver.server.sql.DBRowUpdateException if a version error occurs.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void commit(DBTransaction dbt)
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( dbt, false );
  }

  /**
   * Deletes the DO from its table.
   *
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void delete() 
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( null, true );
  }

  /**
   * Deletes the DO from its table.
   * The transaction is likely provided by the delete() method of another DO
   * which references this DO.
   *
   * @param dbt The transaction object to use for this operation.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception com.lutris.appserver.server.sql.DBRowUpdateException if a version error occurs.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  public void delete(DBTransaction dbt)
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    modifyDO( dbt, true );
  }

  

  /**
   * Modifies the DO within its table.
   * Performs recursive commit/delete on referenced DOs;
   * all operations occur within a single transaction
   * to allow rollback in the event of error.
   * Only the creator of the transaction releases it.
   *
   * @param dbt The transaction object to use for this operation.
   * @param delete True if doing a delete, otherwise doing insert/update.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
   * @exception com.lutris.appserver.server.sql.DBRowUpdateException if a version error occurs.
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  protected void modifyDO( DBTransaction dbt, boolean delete )
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
    if ( delete )
	okToDelete();
    else
	okToCommit();
    boolean ownTransaction = false;
    try {
      if ( null == dbt ) {
	DatabaseManager dbm = Enhydra.getDatabaseManager();
        dbt = dbm.createTransaction();      // create a transaction
	ownTransaction = true;
      }
      if ( null == dbt )
	throw new DatabaseManagerException(
		"DatabaseManager.createTransaction returned null." );
      if ( delete ) {
	  // Code to perform cascading deletes is generated here
	  // if cascading deletes are not supported by the database.      
	  
	  // The following line keeps the compiler happy 
	  // when the CASCADING_DELETES tag is empty.
          if ( false )
	      throw new QueryException("XXX");
      } else {
	  // commit referenced DOs.
	  
      }
      if ( false ) {
	  // This throw is here to keep the compiler happy
	  // in the case of a DO that does not refer to other DOs.
	  // In that case, the above delete/commit code blocks will be empty
	  // and throw nothing.
	  throw new DataObjectException( "foo" );	  
      }
      if ( delete ) {
          dbt.delete( this );
      } else {
	  if ( isLoaded() )
	      dbt.insert( this );   // dbt.insert() handles insertions and updates
      }
      if (ownTransaction) {
	  dbt.commit(); // commit the transaction
      }
    } catch (SQLException sqle) {
      StringBuffer message = new StringBuffer("Failed to insert/update DO: ");
      message.append(sqle.getMessage());

      // rollback, if necessary
      if (ownTransaction) {
        try {
          dbt.rollback();
        } catch (SQLException sqle2) {
          message.insert(0,"\n");
          message.insert(0,sqle2.getMessage());
          message.insert(0,"Rollback failed: ");
        }
      }
      throw new SQLException(message.toString());
    } finally {
      // release the transaction, if any
      if (ownTransaction) {
        dbt.release();
      }
    }
  }
}
