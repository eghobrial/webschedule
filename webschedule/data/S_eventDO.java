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
 * /home/emang/myapps/webschedule/webschedule/data/S_eventDO.java
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
 * Data core class, used to set, retrieve the S_eventDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class S_eventDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "S_event" );

    /**
     * Return S_event as the name of the table in the database
     * which contains S_eventDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "S_event";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for S_eventDO attributes are defined below. */

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
     * If the isView flag is true, S_eventDO.createExisting(ResultSet)
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
    private S_eventDataStruct data = null;

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
    protected S_eventDO ( boolean is_view )
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
    protected S_eventDO ()
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
	    
	    data = new S_eventDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	S_eventQuery query = new S_eventQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	S_eventDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "S_eventDO DO not found for id=" + id );
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
		    "Unable to load data for S_eventDO id=" + getOId() +
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
    protected S_eventDO( ObjectId id )
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
    public static S_eventDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new S_eventDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a S_eventDO object by searching for it
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
    public static S_eventDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>S_eventDO</CODE>
     * from a string handle.
     */
    public static S_eventDO createExisting( String handle ) {
	S_eventDO ret = null;
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
     * Factory method creates a S_eventDO object by searching for it
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
    protected static S_eventDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	S_eventDO ret = null;
	ret = new S_eventDO( id );
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
    protected static S_eventDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	S_eventDO ret = null;
	if ( isView ) {
	    ret = new S_eventDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new S_eventDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a S_eventDO object by searching for it
     * in the database using the S_eventDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a S_eventDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static S_eventDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( S_eventDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create S_eventDO, row does not " +
		"contain S_eventDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a S_eventDO object by searching for it
     * in the database using the passed S_eventDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a S_eventDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static S_eventDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( S_eventDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create S_eventDO, " +
		"RDBColumnValue is not S_eventDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create S_eventDO, bad primary key." );
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
    public static S_eventDO createCopy( S_eventDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	S_eventDO ret = new S_eventDO ();
	ret.data = ( S_eventDataStruct ) data.duplicate();
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
    public static S_eventDO createCopy( S_eventDO orig )
    throws DatabaseManagerException, ObjectIdException {
	S_eventDO ret = new S_eventDO ();
	if ( null != orig.data ) {
	    ret.data = ( S_eventDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( S_eventDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member Description

   /* static final RDBColumn Description for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Description = 
			    new RDBColumn( table, "description" );

   /**
    * Get description of the S_event
    *
    * @return description of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDescription () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.description;
   }

   /**
    * Set description of the S_event
    *
    * @param description of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setDescription ( String description )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.description =  markNewValue(
	data.description, description , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Starth

   /* static final RDBColumn Starth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Starth = 
			    new RDBColumn( table, "starth" );

   /**
    * Get starth of the S_event
    *
    * @return starth of the S_event
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
    * Set starth of the S_event
    *
    * @param starth of the S_event
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
    * Get startm of the S_event
    *
    * @return startm of the S_event
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
    * Set startm of the S_event
    *
    * @param startm of the S_event
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
    * Get endh of the S_event
    *
    * @return endh of the S_event
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
    * Set endh of the S_event
    *
    * @param endh of the S_event
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
    * Get endm of the S_event
    *
    * @return endm of the S_event
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
    * Set endm of the S_event
    *
    * @param endm of the S_event
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
   


////////////////////////// data member Eventday

   /* static final RDBColumn Eventday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventday = 
			    new RDBColumn( table, "eventday" );

   /**
    * Get eventday of the S_event
    *
    * @return eventday of the S_event
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
    * Set eventday of the S_event
    *
    * @param eventday of the S_event
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
   


////////////////////////// data member Eventmonth

   /* static final RDBColumn Eventmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventmonth = 
			    new RDBColumn( table, "eventmonth" );

   /**
    * Get eventmonth of the S_event
    *
    * @return eventmonth of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.eventmonth;
   }

   /**
    * Set eventmonth of the S_event
    *
    * @param eventmonth of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEventmonth ( int eventmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.eventmonth =  markNewValue(
	data.eventmonth, eventmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Eventyear

   /* static final RDBColumn Eventyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventyear = 
			    new RDBColumn( table, "eventyear" );

   /**
    * Get eventyear of the S_event
    *
    * @return eventyear of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.eventyear;
   }

   /**
    * Set eventyear of the S_event
    *
    * @param eventyear of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEventyear ( int eventyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.eventyear =  markNewValue(
	data.eventyear, eventyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Eventdayofw

   /* static final RDBColumn Eventdayofw for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Eventdayofw = 
			    new RDBColumn( table, "eventdayofw" );

   /**
    * Get eventdayofw of the S_event
    *
    * @return eventdayofw of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventdayofw () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.eventdayofw;
   }

   /**
    * Set eventdayofw of the S_event
    *
    * @param eventdayofw of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEventdayofw ( int eventdayofw )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.eventdayofw =  markNewValue(
	data.eventdayofw, eventdayofw  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Owner

   /* static final RDBColumn Owner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Owner = 
			    new RDBColumn( table, "owner" );

   /**
    * Get owner of the S_event
    *
    * @return owner of the S_event
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
    * Set owner of the S_event
    *
    * @param owner of the S_event
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
    * Get proj_owner of the S_event
    *
    * @return proj_owner of the S_event
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
    * Set proj_owner of the S_event
    *
    * @param proj_owner of the S_event
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
   


////////////////////////// data member Todayday

   /* static final RDBColumn Todayday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todayday = 
			    new RDBColumn( table, "todayday" );

   /**
    * Get todayday of the S_event
    *
    * @return todayday of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todayday;
   }

   /**
    * Set todayday of the S_event
    *
    * @param todayday of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodayday ( int todayday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todayday =  markNewValue(
	data.todayday, todayday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todaymonth

   /* static final RDBColumn Todaymonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todaymonth = 
			    new RDBColumn( table, "todaymonth" );

   /**
    * Get todaymonth of the S_event
    *
    * @return todaymonth of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodaymonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todaymonth;
   }

   /**
    * Set todaymonth of the S_event
    *
    * @param todaymonth of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodaymonth ( int todaymonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todaymonth =  markNewValue(
	data.todaymonth, todaymonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todayyear

   /* static final RDBColumn Todayyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todayyear = 
			    new RDBColumn( table, "todayyear" );

   /**
    * Get todayyear of the S_event
    *
    * @return todayyear of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.todayyear;
   }

   /**
    * Set todayyear of the S_event
    *
    * @param todayyear of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTodayyear ( int todayyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.todayyear =  markNewValue(
	data.todayyear, todayyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsDevelop

   /* static final RDBColumn IsDevelop for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsDevelop = 
			    new RDBColumn( table, "isDevelop" );

   /**
    * Get isDevelop of the S_event
    *
    * @return isDevelop of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsDevelop () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isDevelop;
   }

   /**
    * Set isDevelop of the S_event
    *
    * @param isDevelop of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsDevelop ( boolean isDevelop )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isDevelop =  markNewValue(
	data.isDevelop, isDevelop  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsRepeat

   /* static final RDBColumn IsRepeat for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsRepeat = 
			    new RDBColumn( table, "isRepeat" );

   /**
    * Get isRepeat of the S_event
    *
    * @return isRepeat of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsRepeat () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isRepeat;
   }

   /**
    * Set isRepeat of the S_event
    *
    * @param isRepeat of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsRepeat ( boolean isRepeat )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isRepeat =  markNewValue(
	data.isRepeat, isRepeat  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Operator

   /* static final RDBColumn Operator for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Operator = 
			    new RDBColumn( table, "Operator" );

   /**
    * Get Operator of the S_event
    *
    * @return Operator of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.OperatorDO getOperator () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Operator;
   }

   /**
    * Set Operator of the S_event
    *
    * @param Operator of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOperator ( webschedule.data.OperatorDO Operator )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Operator = (webschedule.data.OperatorDO) markNewValue(
	data.Operator, Operator  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Prevowner

   /* static final RDBColumn Prevowner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Prevowner = 
			    new RDBColumn( table, "prevowner" );

   /**
    * Get prevowner of the S_event
    *
    * @return prevowner of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonDO getPrevowner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.prevowner;
   }

   /**
    * Set prevowner of the S_event
    *
    * @param prevowner of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPrevowner ( webschedule.data.PersonDO prevowner )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.prevowner = (webschedule.data.PersonDO) markNewValue(
	data.prevowner, prevowner  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Prevproj_owner

   /* static final RDBColumn Prevproj_owner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Prevproj_owner = 
			    new RDBColumn( table, "prevproj_owner" );

   /**
    * Get prevproj_owner of the S_event
    *
    * @return prevproj_owner of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.ProjectDO getPrevproj_owner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.prevproj_owner;
   }

   /**
    * Set prevproj_owner of the S_event
    *
    * @param prevproj_owner of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPrevproj_owner ( webschedule.data.ProjectDO prevproj_owner )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.prevproj_owner = (webschedule.data.ProjectDO) markNewValue(
	data.prevproj_owner, prevproj_owner  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsGrabbable

   /* static final RDBColumn IsGrabbable for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsGrabbable = 
			    new RDBColumn( table, "IsGrabbable" );

   /**
    * Get IsGrabbable of the S_event
    *
    * @return IsGrabbable of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsGrabbable () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.IsGrabbable;
   }

   /**
    * Set IsGrabbable of the S_event
    *
    * @param IsGrabbable of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsGrabbable ( boolean IsGrabbable )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.IsGrabbable =  markNewValue(
	data.IsGrabbable, IsGrabbable  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Reasondropped

   /* static final RDBColumn Reasondropped for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Reasondropped = 
			    new RDBColumn( table, "reasondropped" );

   /**
    * Get reasondropped of the S_event
    *
    * @return reasondropped of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getReasondropped () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.reasondropped;
   }

   /**
    * Set reasondropped of the S_event
    *
    * @param reasondropped of the S_event
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setReasondropped ( String reasondropped )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.reasondropped =  markNewValue(
	data.reasondropped, reasondropped , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Todayh

   /* static final RDBColumn Todayh for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todayh = 
			    new RDBColumn( table, "todayh" );

   /**
    * Get todayh of the S_event
    *
    * @return todayh of the S_event
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
    * Set todayh of the S_event
    *
    * @param todayh of the S_event
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
   


////////////////////////// data member Todaym

   /* static final RDBColumn Todaym for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Todaym = 
			    new RDBColumn( table, "todaym" );

   /**
    * Get todaym of the S_event
    *
    * @return todaym of the S_event
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
    * Set todaym of the S_event
    *
    * @param todaym of the S_event
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
    protected S_eventDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from S_event.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new S_eventDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setDescription( 
	    
		rs.getString( 
			"description"  )
	    
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
	
	
	setEventday( 
	    
		rs.getInt( 
			"eventday"  )
	    
	);
	
	
	setEventmonth( 
	    
		rs.getInt( 
			"eventmonth"  )
	    
	);
	
	
	setEventyear( 
	    
		rs.getInt( 
			"eventyear"  )
	    
	);
	
	
	setEventdayofw( 
	    
		rs.getInt( 
			"eventdayofw"  )
	    
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
	
	
	setTodayday( 
	    
		rs.getInt( 
			"todayday"  )
	    
	);
	
	
	setTodaymonth( 
	    
		rs.getInt( 
			"todaymonth"  )
	    
	);
	
	
	setTodayyear( 
	    
		rs.getInt( 
			"todayyear"  )
	    
	);
	
	
	setIsDevelop( 
	    
		rs.getBoolean( 
			"isDevelop"  )
	    
	);
	
	
	setIsRepeat( 
	    
		rs.getBoolean( 
			"isRepeat"  )
	    
	);
	
	
	setOperator( 
	    webschedule.data.OperatorDO.createExisting( 
		rs.getBigDecimal( 
			"Operator" , 0 )
	     )
	);
	
	
	setPrevowner( 
	    webschedule.data.PersonDO.createExisting( 
		rs.getBigDecimal( 
			"prevowner" , 0 )
	     )
	);
	
	
	setPrevproj_owner( 
	    webschedule.data.ProjectDO.createExisting( 
		rs.getBigDecimal( 
			"prevproj_owner" , 0 )
	     )
	);
	
	
	setIsGrabbable( 
	    
		rs.getBoolean( 
			"IsGrabbable"  )
	    
	);
	
	
	setReasondropped( 
	    
		rs.getString( 
			"reasondropped"  )
	    
	);
	
	
	setTodayh( 
	    
		rs.getInt( 
			"todayh"  )
	    
	);
	
	
	setTodaym( 
	    
		rs.getInt( 
			"todaym"  )
	    
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
	    "insert into S_event ( description, starth, startm, endh, endm, eventday, eventmonth, eventyear, eventdayofw, owner, proj_owner, todayday, todaymonth, todayyear, isDevelop, isRepeat, Operator, prevowner, prevproj_owner, IsGrabbable, reasondropped, todayh, todaym, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_String( stmt, param,
		getDescription() );
	setPrepStmtParam_int( stmt, param,
		getStarth() );
	setPrepStmtParam_int( stmt, param,
		getStartm() );
	setPrepStmtParam_int( stmt, param,
		getEndh() );
	setPrepStmtParam_int( stmt, param,
		getEndm() );
	setPrepStmtParam_int( stmt, param,
		getEventday() );
	setPrepStmtParam_int( stmt, param,
		getEventmonth() );
	setPrepStmtParam_int( stmt, param,
		getEventyear() );
	setPrepStmtParam_int( stmt, param,
		getEventdayofw() );
	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_DO( stmt, param,
		getProj_owner() );
	setPrepStmtParam_int( stmt, param,
		getTodayday() );
	setPrepStmtParam_int( stmt, param,
		getTodaymonth() );
	setPrepStmtParam_int( stmt, param,
		getTodayyear() );
	setPrepStmtParam_boolean( stmt, param,
		getIsDevelop() );
	setPrepStmtParam_boolean( stmt, param,
		getIsRepeat() );
	setPrepStmtParam_DO( stmt, param,
		getOperator() );
	setPrepStmtParam_DO( stmt, param,
		getPrevowner() );
	setPrepStmtParam_DO( stmt, param,
		getPrevproj_owner() );
	setPrepStmtParam_boolean( stmt, param,
		getIsGrabbable() );
	setPrepStmtParam_String( stmt, param,
		getReasondropped() );
	setPrepStmtParam_int( stmt, param,
		getTodayh() );
	setPrepStmtParam_int( stmt, param,
		getTodaym() );


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
	    "update S_event set " + getVersionColumnName() + " = ?, description = ?, starth = ?, startm = ?, endh = ?, endm = ?, eventday = ?, eventmonth = ?, eventyear = ?, eventdayofw = ?, owner = ?, proj_owner = ?, todayday = ?, todaymonth = ?, todayyear = ?, isDevelop = ?, isRepeat = ?, Operator = ?, prevowner = ?, prevproj_owner = ?, IsGrabbable = ?, reasondropped = ?, todayh = ?, todaym = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_String( stmt, param,
		getDescription() );
	setPrepStmtParam_int( stmt, param,
		getStarth() );
	setPrepStmtParam_int( stmt, param,
		getStartm() );
	setPrepStmtParam_int( stmt, param,
		getEndh() );
	setPrepStmtParam_int( stmt, param,
		getEndm() );
	setPrepStmtParam_int( stmt, param,
		getEventday() );
	setPrepStmtParam_int( stmt, param,
		getEventmonth() );
	setPrepStmtParam_int( stmt, param,
		getEventyear() );
	setPrepStmtParam_int( stmt, param,
		getEventdayofw() );
	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_DO( stmt, param,
		getProj_owner() );
	setPrepStmtParam_int( stmt, param,
		getTodayday() );
	setPrepStmtParam_int( stmt, param,
		getTodaymonth() );
	setPrepStmtParam_int( stmt, param,
		getTodayyear() );
	setPrepStmtParam_boolean( stmt, param,
		getIsDevelop() );
	setPrepStmtParam_boolean( stmt, param,
		getIsRepeat() );
	setPrepStmtParam_DO( stmt, param,
		getOperator() );
	setPrepStmtParam_DO( stmt, param,
		getPrevowner() );
	setPrepStmtParam_DO( stmt, param,
		getPrevproj_owner() );
	setPrepStmtParam_boolean( stmt, param,
		getIsGrabbable() );
	setPrepStmtParam_String( stmt, param,
		getReasondropped() );
	setPrepStmtParam_int( stmt, param,
		getTodayh() );
	setPrepStmtParam_int( stmt, param,
		getTodaym() );


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
            "delete from S_event \n" +
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
	String str = "S_eventDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "description=" + data.description
+ "\n" + indent + "starth=" + data.starth
+ "\n" + indent + "startm=" + data.startm
+ "\n" + indent + "endh=" + data.endh
+ "\n" + indent + "endm=" + data.endm
+ "\n" + indent + "eventday=" + data.eventday
+ "\n" + indent + "eventmonth=" + data.eventmonth
+ "\n" + indent + "eventyear=" + data.eventyear
+ "\n" + indent + "eventdayofw=" + data.eventdayofw
+ "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "proj_owner=" + ( null == data.proj_owner ? null  : data.proj_owner.toString( indentCount + 1 ) )
+ "\n" + indent + "todayday=" + data.todayday
+ "\n" + indent + "todaymonth=" + data.todaymonth
+ "\n" + indent + "todayyear=" + data.todayyear
+ "\n" + indent + "isDevelop=" + data.isDevelop
+ "\n" + indent + "isRepeat=" + data.isRepeat
+ "\n" + indent + "Operator=" + ( null == data.Operator ? null  : data.Operator.toString( indentCount + 1 ) )
+ "\n" + indent + "prevowner=" + ( null == data.prevowner ? null  : data.prevowner.toString( indentCount + 1 ) )
+ "\n" + indent + "prevproj_owner=" + ( null == data.prevproj_owner ? null  : data.prevproj_owner.toString( indentCount + 1 ) )
+ "\n" + indent + "IsGrabbable=" + data.IsGrabbable
+ "\n" + indent + "reasondropped=" + data.reasondropped
+ "\n" + indent + "todayh=" + data.todayh
+ "\n" + indent + "todaym=" + data.todaym
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
        String str = indent + "S_eventDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "description=" + data.description
+ "\n" + indent + "starth=" + data.starth
+ "\n" + indent + "startm=" + data.startm
+ "\n" + indent + "endh=" + data.endh
+ "\n" + indent + "endm=" + data.endm
+ "\n" + indent + "eventday=" + data.eventday
+ "\n" + indent + "eventmonth=" + data.eventmonth
+ "\n" + indent + "eventyear=" + data.eventyear
+ "\n" + indent + "eventdayofw=" + data.eventdayofw
+ "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "proj_owner=" + ( null == data.proj_owner ? null  : data.proj_owner.toString( indentCount + 1 ) )
+ "\n" + indent + "todayday=" + data.todayday
+ "\n" + indent + "todaymonth=" + data.todaymonth
+ "\n" + indent + "todayyear=" + data.todayyear
+ "\n" + indent + "isDevelop=" + data.isDevelop
+ "\n" + indent + "isRepeat=" + data.isRepeat
+ "\n" + indent + "Operator=" + ( null == data.Operator ? null  : data.Operator.toString( indentCount + 1 ) )
+ "\n" + indent + "prevowner=" + ( null == data.prevowner ? null  : data.prevowner.toString( indentCount + 1 ) )
+ "\n" + indent + "prevproj_owner=" + ( null == data.prevproj_owner ? null  : data.prevproj_owner.toString( indentCount + 1 ) )
+ "\n" + indent + "IsGrabbable=" + data.IsGrabbable
+ "\n" + indent + "reasondropped=" + data.reasondropped
+ "\n" + indent + "todayh=" + data.todayh
+ "\n" + indent + "todaym=" + data.todaym
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    



    /**
     * A stub method for implementing pre-commit assertions 
     * for this S_eventDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this S_eventDO.
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
     * A stub method for implementing pre-commit assertions 
     * for the Operator data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where Operator is not valid for writing to the database.
     */
    protected void okToCommitOperator( webschedule.data.OperatorDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the Operator data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where Operator is not valid for deletion from the database.
     */
    protected void okToDeleteOperator( webschedule.data.OperatorDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-commit assertions 
     * for the prevowner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevowner is not valid for writing to the database.
     */
    protected void okToCommitPrevowner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the prevowner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevowner is not valid for deletion from the database.
     */
    protected void okToDeletePrevowner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-commit assertions 
     * for the prevproj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevproj_owner is not valid for writing to the database.
     */
    protected void okToCommitPrevproj_owner( webschedule.data.ProjectDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the prevproj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevproj_owner is not valid for deletion from the database.
     */
    protected void okToDeletePrevproj_owner( webschedule.data.ProjectDO member ) 
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
		    "Cannot commit S_eventDO ( " + toString() +
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
		    "Cannot commit S_eventDO ( " + toString() +
		    " ) because proj_owner is not allowed to be null." );
	}
	webschedule.data.OperatorDO Operator_DO = getOperator();
	if ( null != Operator_DO ) {
	    if ( Operator_DO.isLoaded() ) {
		okToCommitOperator( Operator_DO );
		Operator_DO.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit S_eventDO ( " + toString() +
		    " ) because Operator is not allowed to be null." );
	}
	webschedule.data.PersonDO prevowner_DO = getPrevowner();
	if ( null != prevowner_DO ) {
	    if ( prevowner_DO.isLoaded() ) {
		okToCommitPrevowner( prevowner_DO );
		prevowner_DO.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit S_eventDO ( " + toString() +
		    " ) because prevowner is not allowed to be null." );
	}
	webschedule.data.ProjectDO prevproj_owner_DO = getPrevproj_owner();
	if ( null != prevproj_owner_DO ) {
	    if ( prevproj_owner_DO.isLoaded() ) {
		okToCommitPrevproj_owner( prevproj_owner_DO );
		prevproj_owner_DO.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit S_eventDO ( " + toString() +
		    " ) because prevproj_owner is not allowed to be null." );
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
