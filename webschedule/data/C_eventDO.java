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
 * /home/emang/myapps/webschedule/webschedule/data/C_eventDO.java
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
 * Data core class, used to set, retrieve the C_eventDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class C_eventDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "C_event" );

    /**
     * Return C_event as the name of the table in the database
     * which contains C_eventDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "C_event";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for C_eventDO attributes are defined below. */

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
     * If the isView flag is true, C_eventDO.createExisting(ResultSet)
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
    private C_eventDataStruct data = null;

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
    protected C_eventDO ( boolean is_view )
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
    protected C_eventDO ()
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
	    
	    data = new C_eventDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	C_eventQuery query = new C_eventQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	C_eventDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "C_eventDO DO not found for id=" + id );
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
		    "Unable to load data for C_eventDO id=" + getOId() +
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
    protected C_eventDO( ObjectId id )
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
    public static C_eventDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new C_eventDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a C_eventDO object by searching for it
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
    public static C_eventDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>C_eventDO</CODE>
     * from a string handle.
     */
    public static C_eventDO createExisting( String handle ) {
	C_eventDO ret = null;
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
     * Factory method creates a C_eventDO object by searching for it
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
    protected static C_eventDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	C_eventDO ret = null;
	ret = new C_eventDO( id );
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
    protected static C_eventDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	C_eventDO ret = null;
	if ( isView ) {
	    ret = new C_eventDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new C_eventDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a C_eventDO object by searching for it
     * in the database using the C_eventDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a C_eventDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static C_eventDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( C_eventDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create C_eventDO, row does not " +
		"contain C_eventDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a C_eventDO object by searching for it
     * in the database using the passed C_eventDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a C_eventDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static C_eventDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( C_eventDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create C_eventDO, " +
		"RDBColumnValue is not C_eventDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create C_eventDO, bad primary key." );
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
    public static C_eventDO createCopy( C_eventDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	C_eventDO ret = new C_eventDO ();
	ret.data = ( C_eventDataStruct ) data.duplicate();
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
    public static C_eventDO createCopy( C_eventDO orig )
    throws DatabaseManagerException, ObjectIdException {
	C_eventDO ret = new C_eventDO ();
	if ( null != orig.data ) {
	    ret.data = ( C_eventDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( C_eventDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member Eventday

   /* static final RDBColumn Eventday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventday = 
			    new RDBColumn( table, "eventday" );

   /**
    * Get eventday of the C_event
    *
    * @return eventday of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.eventday;
   }

   /**
    * Set eventday of the C_event
    *
    * @param eventday of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEventday ( int eventday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.eventday =  markNewValue(
	data.eventday, eventday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Eventm

   /* static final RDBColumn Eventm for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventm = 
			    new RDBColumn( table, "eventm" );

   /**
    * Get eventm of the C_event
    *
    * @return eventm of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.eventm;
   }

   /**
    * Set eventm of the C_event
    *
    * @param eventm of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEventm ( int eventm )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.eventm =  markNewValue(
	data.eventm, eventm  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Eventy

   /* static final RDBColumn Eventy for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventy = 
			    new RDBColumn( table, "eventy" );

   /**
    * Get eventy of the C_event
    *
    * @return eventy of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventy () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.eventy;
   }

   /**
    * Set eventy of the C_event
    *
    * @param eventy of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEventy ( int eventy )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.eventy =  markNewValue(
	data.eventy, eventy  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Starth

   /* static final RDBColumn Starth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Starth = 
			    new RDBColumn( table, "starth" );

   /**
    * Get starth of the C_event
    *
    * @return starth of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStarth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.starth;
   }

   /**
    * Set starth of the C_event
    *
    * @param starth of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStarth ( int starth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.starth =  markNewValue(
	data.starth, starth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Startm

   /* static final RDBColumn Startm for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Startm = 
			    new RDBColumn( table, "startm" );

   /**
    * Get startm of the C_event
    *
    * @return startm of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.startm;
   }

   /**
    * Set startm of the C_event
    *
    * @param startm of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStartm ( int startm )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.startm =  markNewValue(
	data.startm, startm  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Endh

   /* static final RDBColumn Endh for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Endh = 
			    new RDBColumn( table, "endh" );

   /**
    * Get endh of the C_event
    *
    * @return endh of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndh () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.endh;
   }

   /**
    * Set endh of the C_event
    *
    * @param endh of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEndh ( int endh )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.endh =  markNewValue(
	data.endh, endh  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Endm

   /* static final RDBColumn Endm for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Endm = 
			    new RDBColumn( table, "endm" );

   /**
    * Get endm of the C_event
    *
    * @return endm of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.endm;
   }

   /**
    * Set endm of the C_event
    *
    * @param endm of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEndm ( int endm )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.endm =  markNewValue(
	data.endm, endm  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todayd

   /* static final RDBColumn Todayd for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todayd = 
			    new RDBColumn( table, "todayd" );

   /**
    * Get todayd of the C_event
    *
    * @return todayd of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayd () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todayd;
   }

   /**
    * Set todayd of the C_event
    *
    * @param todayd of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodayd ( int todayd )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todayd =  markNewValue(
	data.todayd, todayd  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todaym

   /* static final RDBColumn Todaym for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todaym = 
			    new RDBColumn( table, "todaym" );

   /**
    * Get todaym of the C_event
    *
    * @return todaym of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodaym () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todaym;
   }

   /**
    * Set todaym of the C_event
    *
    * @param todaym of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodaym ( int todaym )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todaym =  markNewValue(
	data.todaym, todaym  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Owner

   /* static final RDBColumn Owner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Owner = 
			    new RDBColumn( table, "owner" );

   /**
    * Get owner of the C_event
    *
    * @return owner of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonDO getOwner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.owner;
   }

   /**
    * Set owner of the C_event
    *
    * @param owner of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOwner ( webschedule.data.PersonDO owner )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.owner = (webschedule.data.PersonDO) markNewValue(
	data.owner, owner  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proj_owner

   /* static final RDBColumn Proj_owner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proj_owner = 
			    new RDBColumn( table, "proj_owner" );

   /**
    * Get proj_owner of the C_event
    *
    * @return proj_owner of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.ProjectDO getProj_owner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proj_owner;
   }

   /**
    * Set proj_owner of the C_event
    *
    * @param proj_owner of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProj_owner ( webschedule.data.ProjectDO proj_owner )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proj_owner = (webschedule.data.ProjectDO) markNewValue(
	data.proj_owner, proj_owner  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todayy

   /* static final RDBColumn Todayy for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todayy = 
			    new RDBColumn( table, "todayy" );

   /**
    * Get todayy of the C_event
    *
    * @return todayy of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayy () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todayy;
   }

   /**
    * Set todayy of the C_event
    *
    * @param todayy of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodayy ( int todayy )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todayy =  markNewValue(
	data.todayy, todayy  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Cancelc

   /* static final RDBColumn Cancelc for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Cancelc = 
			    new RDBColumn( table, "cancelc" );

   /**
    * Get cancelc of the C_event
    *
    * @return cancelc of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCancelc () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.cancelc;
   }

   /**
    * Set cancelc of the C_event
    *
    * @param cancelc of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCancelc ( int cancelc )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.cancelc =  markNewValue(
	data.cancelc, cancelc  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todayh

   /* static final RDBColumn Todayh for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todayh = 
			    new RDBColumn( table, "todayh" );

   /**
    * Get todayh of the C_event
    *
    * @return todayh of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayh () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todayh;
   }

   /**
    * Set todayh of the C_event
    *
    * @param todayh of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodayh ( int todayh )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todayh =  markNewValue(
	data.todayh, todayh  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todaymin

   /* static final RDBColumn Todaymin for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todaymin = 
			    new RDBColumn( table, "todaymin" );

   /**
    * Get todaymin of the C_event
    *
    * @return todaymin of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodaymin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todaymin;
   }

   /**
    * Set todaymin of the C_event
    *
    * @param todaymin of the C_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodaymin ( int todaymin )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todaymin =  markNewValue(
	data.todaymin, todaymin  );
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
    protected C_eventDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from C_event.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new C_eventDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setEventday( 
	    
		rs.getInt( 
			"eventday"  )
	    
	);
	
	
	setEventm( 
	    
		rs.getInt( 
			"eventm"  )
	    
	);
	
	
	setEventy( 
	    
		rs.getInt( 
			"eventy"  )
	    
	);
	
	
	setStarth( 
	    
		rs.getInt( 
			"starth"  )
	    
	);
	
	
	setStartm( 
	    
		rs.getInt( 
			"startm"  )
	    
	);
	
	
	setEndh( 
	    
		rs.getInt( 
			"endh"  )
	    
	);
	
	
	setEndm( 
	    
		rs.getInt( 
			"endm"  )
	    
	);
	
	
	setTodayd( 
	    
		rs.getInt( 
			"todayd"  )
	    
	);
	
	
	setTodaym( 
	    
		rs.getInt( 
			"todaym"  )
	    
	);
	
	
	setOwner( 
	    webschedule.data.PersonDO.createExisting( 
		rs.getBigDecimal( 
			"owner" , 0 )
	     )
	);
	
	
	setProj_owner( 
	    webschedule.data.ProjectDO.createExisting( 
		rs.getBigDecimal( 
			"proj_owner" , 0 )
	     )
	);
	
	
	setTodayy( 
	    
		rs.getInt( 
			"todayy"  )
	    
	);
	
	
	setCancelc( 
	    
		rs.getInt( 
			"cancelc"  )
	    
	);
	
	
	setTodayh( 
	    
		rs.getInt( 
			"todayh"  )
	    
	);
	
	
	setTodaymin( 
	    
		rs.getInt( 
			"todaymin"  )
	    
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
	    "insert into C_event ( eventday, eventm, eventy, starth, startm, endh, endm, todayd, todaym, owner, proj_owner, todayy, cancelc, todayh, todaymin, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_int( stmt, param,
		getEventday() );
	setPrepStmtParam_int( stmt, param,
		getEventm() );
	setPrepStmtParam_int( stmt, param,
		getEventy() );
	setPrepStmtParam_int( stmt, param,
		getStarth() );
	setPrepStmtParam_int( stmt, param,
		getStartm() );
	setPrepStmtParam_int( stmt, param,
		getEndh() );
	setPrepStmtParam_int( stmt, param,
		getEndm() );
	setPrepStmtParam_int( stmt, param,
		getTodayd() );
	setPrepStmtParam_int( stmt, param,
		getTodaym() );
	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_DO( stmt, param,
		getProj_owner() );
	setPrepStmtParam_int( stmt, param,
		getTodayy() );
	setPrepStmtParam_int( stmt, param,
		getCancelc() );
	setPrepStmtParam_int( stmt, param,
		getTodayh() );
	setPrepStmtParam_int( stmt, param,
		getTodaymin() );


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
	    "update C_event set " + getVersionColumnName() + " = ?, eventday = ?, eventm = ?, eventy = ?, starth = ?, startm = ?, endh = ?, endm = ?, todayd = ?, todaym = ?, owner = ?, proj_owner = ?, todayy = ?, cancelc = ?, todayh = ?, todaymin = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_int( stmt, param,
		getEventday() );
	setPrepStmtParam_int( stmt, param,
		getEventm() );
	setPrepStmtParam_int( stmt, param,
		getEventy() );
	setPrepStmtParam_int( stmt, param,
		getStarth() );
	setPrepStmtParam_int( stmt, param,
		getStartm() );
	setPrepStmtParam_int( stmt, param,
		getEndh() );
	setPrepStmtParam_int( stmt, param,
		getEndm() );
	setPrepStmtParam_int( stmt, param,
		getTodayd() );
	setPrepStmtParam_int( stmt, param,
		getTodaym() );
	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_DO( stmt, param,
		getProj_owner() );
	setPrepStmtParam_int( stmt, param,
		getTodayy() );
	setPrepStmtParam_int( stmt, param,
		getCancelc() );
	setPrepStmtParam_int( stmt, param,
		getTodayh() );
	setPrepStmtParam_int( stmt, param,
		getTodaymin() );


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
            "delete from C_event \n" +
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
	String str = "C_eventDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "eventday=" + data.eventday
+ "\n" + indent + "eventm=" + data.eventm
+ "\n" + indent + "eventy=" + data.eventy
+ "\n" + indent + "starth=" + data.starth
+ "\n" + indent + "startm=" + data.startm
+ "\n" + indent + "endh=" + data.endh
+ "\n" + indent + "endm=" + data.endm
+ "\n" + indent + "todayd=" + data.todayd
+ "\n" + indent + "todaym=" + data.todaym
+ "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "proj_owner=" + ( null == data.proj_owner ? null  : data.proj_owner.toString( indentCount + 1 ) )
+ "\n" + indent + "todayy=" + data.todayy
+ "\n" + indent + "cancelc=" + data.cancelc
+ "\n" + indent + "todayh=" + data.todayh
+ "\n" + indent + "todaymin=" + data.todaymin
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
        String str = indent + "C_eventDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "eventday=" + data.eventday
+ "\n" + indent + "eventm=" + data.eventm
+ "\n" + indent + "eventy=" + data.eventy
+ "\n" + indent + "starth=" + data.starth
+ "\n" + indent + "startm=" + data.startm
+ "\n" + indent + "endh=" + data.endh
+ "\n" + indent + "endm=" + data.endm
+ "\n" + indent + "todayd=" + data.todayd
+ "\n" + indent + "todaym=" + data.todaym
+ "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "proj_owner=" + ( null == data.proj_owner ? null  : data.proj_owner.toString( indentCount + 1 ) )
+ "\n" + indent + "todayy=" + data.todayy
+ "\n" + indent + "cancelc=" + data.cancelc
+ "\n" + indent + "todayh=" + data.todayh
+ "\n" + indent + "todaymin=" + data.todaymin
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    



    /**
     * A stub method for implementing pre-commit assertions 
     * for this C_eventDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this C_eventDO.
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
     * A stub method for implementing pre-commit assertions 
     * for the owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where owner is not valid for writing to the database.
     */
    protected void okToCommitOwner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where owner is not valid for deletion from the database.
     */
    protected void okToDeleteOwner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-commit assertions 
     * for the proj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where proj_owner is not valid for writing to the database.
     */
    protected void okToCommitProj_owner( webschedule.data.ProjectDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the proj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where proj_owner is not valid for deletion from the database.
     */
    protected void okToDeleteProj_owner( webschedule.data.ProjectDO member ) 
    throws RefAssertionException { }



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
	  	webschedule.data.PersonDO owner_DO = getOwner();
	if ( null != owner_DO ) {
	    if ( owner_DO.isLoaded() ) {
		okToCommitOwner( owner_DO );
		owner_DO.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit C_eventDO ( " + toString() +
		    " ) because owner is not allowed to be null." );
	}
	webschedule.data.ProjectDO proj_owner_DO = getProj_owner();
	if ( null != proj_owner_DO ) {
	    if ( proj_owner_DO.isLoaded() ) {
		okToCommitProj_owner( proj_owner_DO );
		proj_owner_DO.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit C_eventDO ( " + toString() +
		    " ) because proj_owner is not allowed to be null." );
	}

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
