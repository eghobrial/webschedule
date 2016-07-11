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
 * /home/emang/myapps/webschedule/webschedule/data/proposalDO.java
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
 * Data core class, used to set, retrieve the proposalDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class proposalDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "proposal" );

    /**
     * Return proposal as the name of the table in the database
     * which contains proposalDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "proposal";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for proposalDO attributes are defined below. */

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
     * If the isView flag is true, proposalDO.createExisting(ResultSet)
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
    private proposalDataStruct data = null;

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
    protected proposalDO ( boolean is_view )
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
    protected proposalDO ()
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
	    
	    data = new proposalDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	proposalQuery query = new proposalQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	proposalDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "proposalDO DO not found for id=" + id );
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
		    "Unable to load data for proposalDO id=" + getOId() +
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
    protected proposalDO( ObjectId id )
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
    public static proposalDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new proposalDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a proposalDO object by searching for it
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
    public static proposalDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>proposalDO</CODE>
     * from a string handle.
     */
    public static proposalDO createExisting( String handle ) {
	proposalDO ret = null;
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
     * Factory method creates a proposalDO object by searching for it
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
    protected static proposalDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	proposalDO ret = null;
	ret = new proposalDO( id );
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
    protected static proposalDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	proposalDO ret = null;
	if ( isView ) {
	    ret = new proposalDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new proposalDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a proposalDO object by searching for it
     * in the database using the proposalDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a proposalDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static proposalDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( proposalDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create proposalDO, row does not " +
		"contain proposalDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a proposalDO object by searching for it
     * in the database using the passed proposalDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a proposalDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static proposalDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( proposalDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create proposalDO, " +
		"RDBColumnValue is not proposalDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create proposalDO, bad primary key." );
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
    public static proposalDO createCopy( proposalDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	proposalDO ret = new proposalDO ();
	ret.data = ( proposalDataStruct ) data.duplicate();
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
    public static proposalDO createCopy( proposalDO orig )
    throws DatabaseManagerException, ObjectIdException {
	proposalDO ret = new proposalDO ();
	if ( null != orig.data ) {
	    ret.data = ( proposalDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( proposalDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member Owner

   /* static final RDBColumn Owner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Owner = 
			    new RDBColumn( table, "owner" );

   /**
    * Get owner of the proposal
    *
    * @return owner of the proposal
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
    * Set owner of the proposal
    *
    * @param owner of the proposal
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
   


////////////////////////// data member Today

   /* static final RDBColumn Today for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Today = 
			    new RDBColumn( table, "today" );

   /**
    * Get today of the proposal
    *
    * @return today of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getToday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.today;
   }

   /**
    * Set today of the proposal
    *
    * @param today of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setToday ( int today )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.today =  markNewValue(
	data.today, today  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Month

   /* static final RDBColumn Month for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Month = 
			    new RDBColumn( table, "month" );

   /**
    * Get month of the proposal
    *
    * @return month of the proposal
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
    * Set month of the proposal
    *
    * @param month of the proposal
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
    * Get year of the proposal
    *
    * @return year of the proposal
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
    * Set year of the proposal
    *
    * @param year of the proposal
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
   


////////////////////////// data member Isucsd

   /* static final RDBColumn Isucsd for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Isucsd = 
			    new RDBColumn( table, "Isucsd" );

   /**
    * Get Isucsd of the proposal
    *
    * @return Isucsd of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsucsd () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Isucsd;
   }

   /**
    * Set Isucsd of the proposal
    *
    * @param Isucsd of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsucsd ( boolean Isucsd )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Isucsd =  markNewValue(
	data.Isucsd, Isucsd  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Isanimal

   /* static final RDBColumn Isanimal for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Isanimal = 
			    new RDBColumn( table, "Isanimal" );

   /**
    * Get Isanimal of the proposal
    *
    * @return Isanimal of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsanimal () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Isanimal;
   }

   /**
    * Set Isanimal of the proposal
    *
    * @param Isanimal of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsanimal ( boolean Isanimal )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Isanimal =  markNewValue(
	data.Isanimal, Isanimal  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Issample

   /* static final RDBColumn Issample for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Issample = 
			    new RDBColumn( table, "Issample" );

   /**
    * Get Issample of the proposal
    *
    * @return Issample of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIssample () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Issample;
   }

   /**
    * Set Issample of the proposal
    *
    * @param Issample of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIssample ( boolean Issample )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Issample =  markNewValue(
	data.Issample, Issample  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proj_name

   /* static final RDBColumn Proj_name for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proj_name = 
			    new RDBColumn( table, "proj_name" );

   /**
    * Get proj_name of the proposal
    *
    * @return proj_name of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProj_name () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proj_name;
   }

   /**
    * Set proj_name of the proposal
    *
    * @param proj_name of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProj_name ( String proj_name )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proj_name =  markNewValue(
	data.proj_name, proj_name , 0, 300, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Cname

   /* static final RDBColumn Cname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Cname = 
			    new RDBColumn( table, "cname" );

   /**
    * Get cname of the proposal
    *
    * @return cname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.cname;
   }

   /**
    * Set cname of the proposal
    *
    * @param cname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCname ( String cname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.cname =  markNewValue(
	data.cname, cname , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Cphone

   /* static final RDBColumn Cphone for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Cphone = 
			    new RDBColumn( table, "cphone" );

   /**
    * Get cphone of the proposal
    *
    * @return cphone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCphone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.cphone;
   }

   /**
    * Set cphone of the proposal
    *
    * @param cphone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCphone ( String cphone )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.cphone =  markNewValue(
	data.cphone, cphone , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Cemail

   /* static final RDBColumn Cemail for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Cemail = 
			    new RDBColumn( table, "cemail" );

   /**
    * Get cemail of the proposal
    *
    * @return cemail of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCemail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.cemail;
   }

   /**
    * Set cemail of the proposal
    *
    * @param cemail of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCemail ( String cemail )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.cemail =  markNewValue(
	data.cemail, cemail , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member CntrOp

   /* static final RDBColumn CntrOp for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn CntrOp = 
			    new RDBColumn( table, "CntrOp" );

   /**
    * Get CntrOp of the proposal
    *
    * @return CntrOp of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getCntrOp () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.CntrOp;
   }

   /**
    * Set CntrOp of the proposal
    *
    * @param CntrOp of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCntrOp ( boolean CntrOp )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.CntrOp =  markNewValue(
	data.CntrOp, CntrOp  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op1lastname

   /* static final RDBColumn Op1lastname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op1lastname = 
			    new RDBColumn( table, "op1lastname" );

   /**
    * Get op1lastname of the proposal
    *
    * @return op1lastname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1lastname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op1lastname;
   }

   /**
    * Set op1lastname of the proposal
    *
    * @param op1lastname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp1lastname ( String op1lastname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op1lastname =  markNewValue(
	data.op1lastname, op1lastname , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op1firstname

   /* static final RDBColumn Op1firstname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op1firstname = 
			    new RDBColumn( table, "op1firstname" );

   /**
    * Get op1firstname of the proposal
    *
    * @return op1firstname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1firstname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op1firstname;
   }

   /**
    * Set op1firstname of the proposal
    *
    * @param op1firstname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp1firstname ( String op1firstname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op1firstname =  markNewValue(
	data.op1firstname, op1firstname , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op1phone

   /* static final RDBColumn Op1phone for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op1phone = 
			    new RDBColumn( table, "op1phone" );

   /**
    * Get op1phone of the proposal
    *
    * @return op1phone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1phone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op1phone;
   }

   /**
    * Set op1phone of the proposal
    *
    * @param op1phone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp1phone ( String op1phone )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op1phone =  markNewValue(
	data.op1phone, op1phone , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op1email

   /* static final RDBColumn Op1email for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op1email = 
			    new RDBColumn( table, "op1email" );

   /**
    * Get op1email of the proposal
    *
    * @return op1email of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp1email () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op1email;
   }

   /**
    * Set op1email of the proposal
    *
    * @param op1email of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp1email ( String op1email )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op1email =  markNewValue(
	data.op1email, op1email , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op2lastname

   /* static final RDBColumn Op2lastname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op2lastname = 
			    new RDBColumn( table, "op2lastname" );

   /**
    * Get op2lastname of the proposal
    *
    * @return op2lastname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2lastname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op2lastname;
   }

   /**
    * Set op2lastname of the proposal
    *
    * @param op2lastname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp2lastname ( String op2lastname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op2lastname =  markNewValue(
	data.op2lastname, op2lastname , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op2firstname

   /* static final RDBColumn Op2firstname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op2firstname = 
			    new RDBColumn( table, "op2firstname" );

   /**
    * Get op2firstname of the proposal
    *
    * @return op2firstname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2firstname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op2firstname;
   }

   /**
    * Set op2firstname of the proposal
    *
    * @param op2firstname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp2firstname ( String op2firstname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op2firstname =  markNewValue(
	data.op2firstname, op2firstname , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op2phone

   /* static final RDBColumn Op2phone for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op2phone = 
			    new RDBColumn( table, "op2phone" );

   /**
    * Get op2phone of the proposal
    *
    * @return op2phone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2phone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op2phone;
   }

   /**
    * Set op2phone of the proposal
    *
    * @param op2phone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp2phone ( String op2phone )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op2phone =  markNewValue(
	data.op2phone, op2phone , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op2email

   /* static final RDBColumn Op2email for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op2email = 
			    new RDBColumn( table, "op2email" );

   /**
    * Get op2email of the proposal
    *
    * @return op2email of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOp2email () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op2email;
   }

   /**
    * Set op2email of the proposal
    *
    * @param op2email of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp2email ( String op2email )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op2email =  markNewValue(
	data.op2email, op2email , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Indexnum

   /* static final RDBColumn Indexnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Indexnum = 
			    new RDBColumn( table, "indexnum" );

   /**
    * Get indexnum of the proposal
    *
    * @return indexnum of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIndexnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.indexnum;
   }

   /**
    * Set indexnum of the proposal
    *
    * @param indexnum of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIndexnum ( String indexnum )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.indexnum =  markNewValue(
	data.indexnum, indexnum , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Baline1

   /* static final RDBColumn Baline1 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Baline1 = 
			    new RDBColumn( table, "baline1" );

   /**
    * Get baline1 of the proposal
    *
    * @return baline1 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBaline1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.baline1;
   }

   /**
    * Set baline1 of the proposal
    *
    * @param baline1 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBaline1 ( String baline1 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.baline1 =  markNewValue(
	data.baline1, baline1 , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Baline2

   /* static final RDBColumn Baline2 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Baline2 = 
			    new RDBColumn( table, "baline2" );

   /**
    * Get baline2 of the proposal
    *
    * @return baline2 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBaline2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.baline2;
   }

   /**
    * Set baline2 of the proposal
    *
    * @param baline2 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBaline2 ( String baline2 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.baline2 =  markNewValue(
	data.baline2, baline2 , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Baline3

   /* static final RDBColumn Baline3 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Baline3 = 
			    new RDBColumn( table, "baline3" );

   /**
    * Get baline3 of the proposal
    *
    * @return baline3 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBaline3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.baline3;
   }

   /**
    * Set baline3 of the proposal
    *
    * @param baline3 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBaline3 ( String baline3 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.baline3 =  markNewValue(
	data.baline3, baline3 , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Bacity

   /* static final RDBColumn Bacity for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Bacity = 
			    new RDBColumn( table, "bacity" );

   /**
    * Get bacity of the proposal
    *
    * @return bacity of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBacity () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.bacity;
   }

   /**
    * Set bacity of the proposal
    *
    * @param bacity of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBacity ( String bacity )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.bacity =  markNewValue(
	data.bacity, bacity , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Bast

   /* static final RDBColumn Bast for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Bast = 
			    new RDBColumn( table, "bast" );

   /**
    * Get bast of the proposal
    *
    * @return bast of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBast () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.bast;
   }

   /**
    * Set bast of the proposal
    *
    * @param bast of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBast ( String bast )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.bast =  markNewValue(
	data.bast, bast , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Bazip

   /* static final RDBColumn Bazip for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Bazip = 
			    new RDBColumn( table, "bazip" );

   /**
    * Get bazip of the proposal
    *
    * @return bazip of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBazip () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.bazip;
   }

   /**
    * Set bazip of the proposal
    *
    * @param bazip of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBazip ( String bazip )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.bazip =  markNewValue(
	data.bazip, bazip , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Fpname

   /* static final RDBColumn Fpname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Fpname = 
			    new RDBColumn( table, "fpname" );

   /**
    * Get fpname of the proposal
    *
    * @return fpname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.fpname;
   }

   /**
    * Set fpname of the proposal
    *
    * @param fpname of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setFpname ( String fpname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.fpname =  markNewValue(
	data.fpname, fpname , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Fpphone

   /* static final RDBColumn Fpphone for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Fpphone = 
			    new RDBColumn( table, "fpphone" );

   /**
    * Get fpphone of the proposal
    *
    * @return fpphone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpphone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.fpphone;
   }

   /**
    * Set fpphone of the proposal
    *
    * @param fpphone of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setFpphone ( String fpphone )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.fpphone =  markNewValue(
	data.fpphone, fpphone , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Fpemail

   /* static final RDBColumn Fpemail for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Fpemail = 
			    new RDBColumn( table, "fpemail" );

   /**
    * Get fpemail of the proposal
    *
    * @return fpemail of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpemail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.fpemail;
   }

   /**
    * Set fpemail of the proposal
    *
    * @param fpemail of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setFpemail ( String fpemail )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.fpemail =  markNewValue(
	data.fpemail, fpemail , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Thours

   /* static final RDBColumn Thours for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Thours = 
			    new RDBColumn( table, "thours" );

   /**
    * Get thours of the proposal
    *
    * @return thours of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getThours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.thours;
   }

   /**
    * Set thours of the proposal
    *
    * @param thours of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setThours ( int thours )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.thours =  markNewValue(
	data.thours, thours  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Writeup

   /* static final RDBColumn Writeup for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Writeup = 
			    new RDBColumn( table, "writeup" );

   /**
    * Get writeup of the proposal
    *
    * @return writeup of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getWriteup () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.writeup;
   }

   /**
    * Set writeup of the proposal
    *
    * @param writeup of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setWriteup ( String writeup )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.writeup =  markNewValue(
	data.writeup, writeup , 0, 6000, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Dataanalysis

   /* static final RDBColumn Dataanalysis for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Dataanalysis = 
			    new RDBColumn( table, "dataanalysis" );

   /**
    * Get dataanalysis of the proposal
    *
    * @return dataanalysis of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDataanalysis () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.dataanalysis;
   }

   /**
    * Set dataanalysis of the proposal
    *
    * @param dataanalysis of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setDataanalysis ( String dataanalysis )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.dataanalysis =  markNewValue(
	data.dataanalysis, dataanalysis , 0, 3000, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IACUCFaxed

   /* static final RDBColumn IACUCFaxed for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IACUCFaxed = 
			    new RDBColumn( table, "IACUCFaxed" );

   /**
    * Get IACUCFaxed of the proposal
    *
    * @return IACUCFaxed of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIACUCFaxed () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.IACUCFaxed;
   }

   /**
    * Set IACUCFaxed of the proposal
    *
    * @param IACUCFaxed of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIACUCFaxed ( boolean IACUCFaxed )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.IACUCFaxed =  markNewValue(
	data.IACUCFaxed, IACUCFaxed  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member RFCoils

   /* static final RDBColumn RFCoils for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn RFCoils = 
			    new RDBColumn( table, "RFCoils" );

   /**
    * Get RFCoils of the proposal
    *
    * @return RFCoils of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getRFCoils () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.RFCoils;
   }

   /**
    * Set RFCoils of the proposal
    *
    * @param RFCoils of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setRFCoils ( boolean RFCoils )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.RFCoils =  markNewValue(
	data.RFCoils, RFCoils  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Restraints

   /* static final RDBColumn Restraints for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Restraints = 
			    new RDBColumn( table, "restraints" );

   /**
    * Get restraints of the proposal
    *
    * @return restraints of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getRestraints () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.restraints;
   }

   /**
    * Set restraints of the proposal
    *
    * @param restraints of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setRestraints ( boolean restraints )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.restraints =  markNewValue(
	data.restraints, restraints  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Physioeq

   /* static final RDBColumn Physioeq for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Physioeq = 
			    new RDBColumn( table, "physioeq" );

   /**
    * Get physioeq of the proposal
    *
    * @return physioeq of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getPhysioeq () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.physioeq;
   }

   /**
    * Set physioeq of the proposal
    *
    * @param physioeq of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPhysioeq ( boolean physioeq )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.physioeq =  markNewValue(
	data.physioeq, physioeq  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Anesthetics

   /* static final RDBColumn Anesthetics for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Anesthetics = 
			    new RDBColumn( table, "anesthetics" );

   /**
    * Get anesthetics of the proposal
    *
    * @return anesthetics of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getAnesthetics () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.anesthetics;
   }

   /**
    * Set anesthetics of the proposal
    *
    * @param anesthetics of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnesthetics ( boolean anesthetics )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.anesthetics =  markNewValue(
	data.anesthetics, anesthetics  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Ancillary

   /* static final RDBColumn Ancillary for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Ancillary = 
			    new RDBColumn( table, "ancillary" );

   /**
    * Get ancillary of the proposal
    *
    * @return ancillary of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getAncillary () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.ancillary;
   }

   /**
    * Set ancillary of the proposal
    *
    * @param ancillary of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAncillary ( boolean ancillary )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.ancillary =  markNewValue(
	data.ancillary, ancillary  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op1status

   /* static final RDBColumn Op1status for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op1status = 
			    new RDBColumn( table, "op1status" );

   /**
    * Get op1status of the proposal
    *
    * @return op1status of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getOp1status () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op1status;
   }

   /**
    * Set op1status of the proposal
    *
    * @param op1status of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp1status ( boolean op1status )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op1status =  markNewValue(
	data.op1status, op1status  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Op2status

   /* static final RDBColumn Op2status for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Op2status = 
			    new RDBColumn( table, "op2status" );

   /**
    * Get op2status of the proposal
    *
    * @return op2status of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getOp2status () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.op2status;
   }

   /**
    * Set op2status of the proposal
    *
    * @param op2status of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOp2status ( boolean op2status )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.op2status =  markNewValue(
	data.op2status, op2status  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IACUC

   /* static final RDBColumn IACUC for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IACUC = 
			    new RDBColumn( table, "IACUC" );

   /**
    * Get IACUC of the proposal
    *
    * @return IACUC of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIACUC () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.IACUC;
   }

   /**
    * Set IACUC of the proposal
    *
    * @param IACUC of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIACUC ( boolean IACUC )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.IACUC =  markNewValue(
	data.IACUC, IACUC  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Status

   /* static final RDBColumn Status for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Status = 
			    new RDBColumn( table, "status" );

   /**
    * Get status of the proposal
    *
    * @return status of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStatus () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.status;
   }

   /**
    * Set status of the proposal
    *
    * @param status of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStatus ( int status )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.status =  markNewValue(
	data.status, status  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member BioHazard

   /* static final RDBColumn BioHazard for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn BioHazard = 
			    new RDBColumn( table, "BioHazard" );

   /**
    * Get BioHazard of the proposal
    *
    * @return BioHazard of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBioHazard () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.BioHazard;
   }

   /**
    * Set BioHazard of the proposal
    *
    * @param BioHazard of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBioHazard ( String BioHazard )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.BioHazard =  markNewValue(
	data.BioHazard, BioHazard , 0, 1500, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Stimuli

   /* static final RDBColumn Stimuli for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Stimuli = 
			    new RDBColumn( table, "stimuli" );

   /**
    * Get stimuli of the proposal
    *
    * @return stimuli of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getStimuli () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.stimuli;
   }

   /**
    * Set stimuli of the proposal
    *
    * @param stimuli of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStimuli ( boolean stimuli )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.stimuli =  markNewValue(
	data.stimuli, stimuli  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member AnimalTrans

   /* static final RDBColumn AnimalTrans for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn AnimalTrans = 
			    new RDBColumn( table, "AnimalTrans" );

   /**
    * Get AnimalTrans of the proposal
    *
    * @return AnimalTrans of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getAnimalTrans () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.AnimalTrans;
   }

   /**
    * Set AnimalTrans of the proposal
    *
    * @param AnimalTrans of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnimalTrans ( boolean AnimalTrans )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.AnimalTrans =  markNewValue(
	data.AnimalTrans, AnimalTrans  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Bproj_name

   /* static final RDBColumn Bproj_name for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Bproj_name = 
			    new RDBColumn( table, "bproj_name" );

   /**
    * Get bproj_name of the proposal
    *
    * @return bproj_name of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBproj_name () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.bproj_name;
   }

   /**
    * Set bproj_name of the proposal
    *
    * @param bproj_name of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBproj_name ( String bproj_name )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.bproj_name =  markNewValue(
	data.bproj_name, bproj_name , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Contrast

   /* static final RDBColumn Contrast for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Contrast = 
			    new RDBColumn( table, "contrast" );

   /**
    * Get contrast of the proposal
    *
    * @return contrast of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getContrast () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.contrast;
   }

   /**
    * Set contrast of the proposal
    *
    * @param contrast of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setContrast ( boolean contrast )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.contrast =  markNewValue(
	data.contrast, contrast  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member AnimTransDate

   /* static final RDBColumn AnimTransDate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn AnimTransDate = 
			    new RDBColumn( table, "AnimTransDate" );

   /**
    * Get AnimTransDate of the proposal
    *
    * @return AnimTransDate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getAnimTransDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.AnimTransDate;
   }

   /**
    * Set AnimTransDate of the proposal
    *
    * @param AnimTransDate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnimTransDate ( java.sql.Date AnimTransDate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.AnimTransDate =  markNewValue(
	data.AnimTransDate, AnimTransDate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IACUCDate

   /* static final RDBColumn IACUCDate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IACUCDate = 
			    new RDBColumn( table, "IACUCDate" );

   /**
    * Get IACUCDate of the proposal
    *
    * @return IACUCDate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getIACUCDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.IACUCDate;
   }

   /**
    * Set IACUCDate of the proposal
    *
    * @param IACUCDate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIACUCDate ( java.sql.Date IACUCDate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.IACUCDate =  markNewValue(
	data.IACUCDate, IACUCDate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Stdate

   /* static final RDBColumn Stdate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Stdate = 
			    new RDBColumn( table, "stdate" );

   /**
    * Get stdate of the proposal
    *
    * @return stdate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getStdate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.stdate;
   }

   /**
    * Set stdate of the proposal
    *
    * @param stdate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStdate ( java.sql.Date stdate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.stdate =  markNewValue(
	data.stdate, stdate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Expdate

   /* static final RDBColumn Expdate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Expdate = 
			    new RDBColumn( table, "expdate" );

   /**
    * Get expdate of the proposal
    *
    * @return expdate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getExpdate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.expdate;
   }

   /**
    * Set expdate of the proposal
    *
    * @param expdate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setExpdate ( java.sql.Date expdate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.expdate =  markNewValue(
	data.expdate, expdate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Comments

   /* static final RDBColumn Comments for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Comments = 
			    new RDBColumn( table, "comments" );

   /**
    * Get comments of the proposal
    *
    * @return comments of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getComments () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.comments;
   }

   /**
    * Set comments of the proposal
    *
    * @param comments of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setComments ( String comments )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.comments =  markNewValue(
	data.comments, comments , 0, 3000, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Nighttime

   /* static final RDBColumn Nighttime for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Nighttime = 
			    new RDBColumn( table, "nighttime" );

   /**
    * Get nighttime of the proposal
    *
    * @return nighttime of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getNighttime () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.nighttime;
   }

   /**
    * Set nighttime of the proposal
    *
    * @param nighttime of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setNighttime ( boolean nighttime )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.nighttime =  markNewValue(
	data.nighttime, nighttime  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Copis

   /* static final RDBColumn Copis for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Copis = 
			    new RDBColumn( table, "copis" );

   /**
    * Get copis of the proposal
    *
    * @return copis of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCopis () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.copis;
   }

   /**
    * Set copis of the proposal
    *
    * @param copis of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCopis ( String copis )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.copis =  markNewValue(
	data.copis, copis , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Intcomments

   /* static final RDBColumn Intcomments for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Intcomments = 
			    new RDBColumn( table, "intcomments" );

   /**
    * Get intcomments of the proposal
    *
    * @return intcomments of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIntcomments () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.intcomments;
   }

   /**
    * Set intcomments of the proposal
    *
    * @param intcomments of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIntcomments ( String intcomments )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.intcomments =  markNewValue(
	data.intcomments, intcomments , 0, 3000, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Response

   /* static final RDBColumn Response for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Response = 
			    new RDBColumn( table, "response" );

   /**
    * Get response of the proposal
    *
    * @return response of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getResponse () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.response;
   }

   /**
    * Set response of the proposal
    *
    * @param response of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setResponse ( String response )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.response =  markNewValue(
	data.response, response , 0, 3000, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Animalq1

   /* static final RDBColumn Animalq1 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Animalq1 = 
			    new RDBColumn( table, "animalq1" );

   /**
    * Get animalq1 of the proposal
    *
    * @return animalq1 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.animalq1;
   }

   /**
    * Set animalq1 of the proposal
    *
    * @param animalq1 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnimalq1 ( String animalq1 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.animalq1 =  markNewValue(
	data.animalq1, animalq1 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Animalq2

   /* static final RDBColumn Animalq2 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Animalq2 = 
			    new RDBColumn( table, "animalq2" );

   /**
    * Get animalq2 of the proposal
    *
    * @return animalq2 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.animalq2;
   }

   /**
    * Set animalq2 of the proposal
    *
    * @param animalq2 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnimalq2 ( String animalq2 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.animalq2 =  markNewValue(
	data.animalq2, animalq2 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Animalq3

   /* static final RDBColumn Animalq3 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Animalq3 = 
			    new RDBColumn( table, "animalq3" );

   /**
    * Get animalq3 of the proposal
    *
    * @return animalq3 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.animalq3;
   }

   /**
    * Set animalq3 of the proposal
    *
    * @param animalq3 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnimalq3 ( String animalq3 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.animalq3 =  markNewValue(
	data.animalq3, animalq3 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Animalq4

   /* static final RDBColumn Animalq4 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Animalq4 = 
			    new RDBColumn( table, "animalq4" );

   /**
    * Get animalq4 of the proposal
    *
    * @return animalq4 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAnimalq4 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.animalq4;
   }

   /**
    * Set animalq4 of the proposal
    *
    * @param animalq4 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAnimalq4 ( String animalq4 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.animalq4 =  markNewValue(
	data.animalq4, animalq4 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq1

   /* static final RDBColumn Proposalq1 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq1 = 
			    new RDBColumn( table, "proposalq1" );

   /**
    * Get proposalq1 of the proposal
    *
    * @return proposalq1 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq1;
   }

   /**
    * Set proposalq1 of the proposal
    *
    * @param proposalq1 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq1 ( String proposalq1 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq1 =  markNewValue(
	data.proposalq1, proposalq1 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq2

   /* static final RDBColumn Proposalq2 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq2 = 
			    new RDBColumn( table, "proposalq2" );

   /**
    * Get proposalq2 of the proposal
    *
    * @return proposalq2 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq2;
   }

   /**
    * Set proposalq2 of the proposal
    *
    * @param proposalq2 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq2 ( String proposalq2 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq2 =  markNewValue(
	data.proposalq2, proposalq2 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq3

   /* static final RDBColumn Proposalq3 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq3 = 
			    new RDBColumn( table, "proposalq3" );

   /**
    * Get proposalq3 of the proposal
    *
    * @return proposalq3 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq3;
   }

   /**
    * Set proposalq3 of the proposal
    *
    * @param proposalq3 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq3 ( String proposalq3 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq3 =  markNewValue(
	data.proposalq3, proposalq3 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq4

   /* static final RDBColumn Proposalq4 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq4 = 
			    new RDBColumn( table, "proposalq4" );

   /**
    * Get proposalq4 of the proposal
    *
    * @return proposalq4 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq4 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq4;
   }

   /**
    * Set proposalq4 of the proposal
    *
    * @param proposalq4 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq4 ( String proposalq4 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq4 =  markNewValue(
	data.proposalq4, proposalq4 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq5

   /* static final RDBColumn Proposalq5 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq5 = 
			    new RDBColumn( table, "proposalq5" );

   /**
    * Get proposalq5 of the proposal
    *
    * @return proposalq5 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq5 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq5;
   }

   /**
    * Set proposalq5 of the proposal
    *
    * @param proposalq5 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq5 ( String proposalq5 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq5 =  markNewValue(
	data.proposalq5, proposalq5 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq6

   /* static final RDBColumn Proposalq6 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq6 = 
			    new RDBColumn( table, "proposalq6" );

   /**
    * Get proposalq6 of the proposal
    *
    * @return proposalq6 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq6 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq6;
   }

   /**
    * Set proposalq6 of the proposal
    *
    * @param proposalq6 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq6 ( String proposalq6 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq6 =  markNewValue(
	data.proposalq6, proposalq6 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq7

   /* static final RDBColumn Proposalq7 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq7 = 
			    new RDBColumn( table, "proposalq7" );

   /**
    * Get proposalq7 of the proposal
    *
    * @return proposalq7 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq7 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq7;
   }

   /**
    * Set proposalq7 of the proposal
    *
    * @param proposalq7 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq7 ( String proposalq7 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq7 =  markNewValue(
	data.proposalq7, proposalq7 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Proposalq8

   /* static final RDBColumn Proposalq8 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proposalq8 = 
			    new RDBColumn( table, "proposalq8" );

   /**
    * Get proposalq8 of the proposal
    *
    * @return proposalq8 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalq8 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalq8;
   }

   /**
    * Set proposalq8 of the proposal
    *
    * @param proposalq8 of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalq8 ( String proposalq8 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalq8 =  markNewValue(
	data.proposalq8, proposalq8 , 0, 200, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Bookmark

   /* static final RDBColumn Bookmark for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Bookmark = 
			    new RDBColumn( table, "bookmark" );

   /**
    * Get bookmark of the proposal
    *
    * @return bookmark of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBookmark () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.bookmark;
   }

   /**
    * Set bookmark of the proposal
    *
    * @param bookmark of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBookmark ( String bookmark )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.bookmark =  markNewValue(
	data.bookmark, bookmark , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member ApprovalDate

   /* static final RDBColumn ApprovalDate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn ApprovalDate = 
			    new RDBColumn( table, "approvalDate" );

   /**
    * Get approvalDate of the proposal
    *
    * @return approvalDate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getApprovalDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.approvalDate;
   }

   /**
    * Set approvalDate of the proposal
    *
    * @param approvalDate of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setApprovalDate ( java.sql.Date approvalDate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.approvalDate =  markNewValue(
	data.approvalDate, approvalDate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member POnum

   /* static final RDBColumn POnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn POnum = 
			    new RDBColumn( table, "POnum" );

   /**
    * Get POnum of the proposal
    *
    * @return POnum of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPOnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.POnum;
   }

   /**
    * Set POnum of the proposal
    *
    * @param POnum of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPOnum ( String POnum )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.POnum =  markNewValue(
	data.POnum, POnum , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Institute

   /* static final RDBColumn Institute for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Institute = 
			    new RDBColumn( table, "institute" );

   /**
    * Get institute of the proposal
    *
    * @return institute of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getInstitute () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.institute;
   }

   /**
    * Set institute of the proposal
    *
    * @param institute of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setInstitute ( String institute )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.institute =  markNewValue(
	data.institute, institute , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IRBnum

   /* static final RDBColumn IRBnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IRBnum = 
			    new RDBColumn( table, "IRBnum" );

   /**
    * Get IRBnum of the proposal
    *
    * @return IRBnum of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIRBnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.IRBnum;
   }

   /**
    * Set IRBnum of the proposal
    *
    * @param IRBnum of the proposal
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIRBnum ( String IRBnum )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.IRBnum =  markNewValue(
	data.IRBnum, IRBnum , 0, 32, true );
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
    protected proposalDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from proposal.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new proposalDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setOwner( 
	    webschedule.data.PersonDO.createExisting( 
		rs.getBigDecimal( 
			"owner" , 0 )
	     )
	);
	
	
	setToday( 
	    
		rs.getInt( 
			"today"  )
	    
	);
	
	
	setMonth( 
	    
		rs.getInt( 
			"month"  )
	    
	);
	
	
	setYear( 
	    
		rs.getInt( 
			"year"  )
	    
	);
	
	
	setIsucsd( 
	    
		rs.getBoolean( 
			"Isucsd"  )
	    
	);
	
	
	setIsanimal( 
	    
		rs.getBoolean( 
			"Isanimal"  )
	    
	);
	
	
	setIssample( 
	    
		rs.getBoolean( 
			"Issample"  )
	    
	);
	
	
	setProj_name( 
	    
		rs.getString( 
			"proj_name"  )
	    
	);
	
	
	setCname( 
	    
		rs.getString( 
			"cname"  )
	    
	);
	
	
	setCphone( 
	    
		rs.getString( 
			"cphone"  )
	    
	);
	
	
	setCemail( 
	    
		rs.getString( 
			"cemail"  )
	    
	);
	
	
	setCntrOp( 
	    
		rs.getBoolean( 
			"CntrOp"  )
	    
	);
	
	
	setOp1lastname( 
	    
		rs.getString( 
			"op1lastname"  )
	    
	);
	
	
	setOp1firstname( 
	    
		rs.getString( 
			"op1firstname"  )
	    
	);
	
	
	setOp1phone( 
	    
		rs.getString( 
			"op1phone"  )
	    
	);
	
	
	setOp1email( 
	    
		rs.getString( 
			"op1email"  )
	    
	);
	
	
	setOp2lastname( 
	    
		rs.getString( 
			"op2lastname"  )
	    
	);
	
	
	setOp2firstname( 
	    
		rs.getString( 
			"op2firstname"  )
	    
	);
	
	
	setOp2phone( 
	    
		rs.getString( 
			"op2phone"  )
	    
	);
	
	
	setOp2email( 
	    
		rs.getString( 
			"op2email"  )
	    
	);
	
	
	setIndexnum( 
	    
		rs.getString( 
			"indexnum"  )
	    
	);
	
	
	setBaline1( 
	    
		rs.getString( 
			"baline1"  )
	    
	);
	
	
	setBaline2( 
	    
		rs.getString( 
			"baline2"  )
	    
	);
	
	
	setBaline3( 
	    
		rs.getString( 
			"baline3"  )
	    
	);
	
	
	setBacity( 
	    
		rs.getString( 
			"bacity"  )
	    
	);
	
	
	setBast( 
	    
		rs.getString( 
			"bast"  )
	    
	);
	
	
	setBazip( 
	    
		rs.getString( 
			"bazip"  )
	    
	);
	
	
	setFpname( 
	    
		rs.getString( 
			"fpname"  )
	    
	);
	
	
	setFpphone( 
	    
		rs.getString( 
			"fpphone"  )
	    
	);
	
	
	setFpemail( 
	    
		rs.getString( 
			"fpemail"  )
	    
	);
	
	
	setThours( 
	    
		rs.getInt( 
			"thours"  )
	    
	);
	
	
	setWriteup( 
	    
		rs.getString( 
			"writeup"  )
	    
	);
	
	
	setDataanalysis( 
	    
		rs.getString( 
			"dataanalysis"  )
	    
	);
	
	
	setIACUCFaxed( 
	    
		rs.getBoolean( 
			"IACUCFaxed"  )
	    
	);
	
	
	setRFCoils( 
	    
		rs.getBoolean( 
			"RFCoils"  )
	    
	);
	
	
	setRestraints( 
	    
		rs.getBoolean( 
			"restraints"  )
	    
	);
	
	
	setPhysioeq( 
	    
		rs.getBoolean( 
			"physioeq"  )
	    
	);
	
	
	setAnesthetics( 
	    
		rs.getBoolean( 
			"anesthetics"  )
	    
	);
	
	
	setAncillary( 
	    
		rs.getBoolean( 
			"ancillary"  )
	    
	);
	
	
	setOp1status( 
	    
		rs.getBoolean( 
			"op1status"  )
	    
	);
	
	
	setOp2status( 
	    
		rs.getBoolean( 
			"op2status"  )
	    
	);
	
	
	setIACUC( 
	    
		rs.getBoolean( 
			"IACUC"  )
	    
	);
	
	
	setStatus( 
	    
		rs.getInt( 
			"status"  )
	    
	);
	
	
	setBioHazard( 
	    
		rs.getString( 
			"BioHazard"  )
	    
	);
	
	
	setStimuli( 
	    
		rs.getBoolean( 
			"stimuli"  )
	    
	);
	
	
	setAnimalTrans( 
	    
		rs.getBoolean( 
			"AnimalTrans"  )
	    
	);
	
	
	setBproj_name( 
	    
		rs.getString( 
			"bproj_name"  )
	    
	);
	
	
	setContrast( 
	    
		rs.getBoolean( 
			"contrast"  )
	    
	);
	
	
	setAnimTransDate( 
	    
		rs.getDate( 
			"AnimTransDate"  )
	    
	);
	
	
	setIACUCDate( 
	    
		rs.getDate( 
			"IACUCDate"  )
	    
	);
	
	
	setStdate( 
	    
		rs.getDate( 
			"stdate"  )
	    
	);
	
	
	setExpdate( 
	    
		rs.getDate( 
			"expdate"  )
	    
	);
	
	
	setComments( 
	    
		rs.getString( 
			"comments"  )
	    
	);
	
	
	setNighttime( 
	    
		rs.getBoolean( 
			"nighttime"  )
	    
	);
	
	
	setCopis( 
	    
		rs.getString( 
			"copis"  )
	    
	);
	
	
	setIntcomments( 
	    
		rs.getString( 
			"intcomments"  )
	    
	);
	
	
	setResponse( 
	    
		rs.getString( 
			"response"  )
	    
	);
	
	
	setAnimalq1( 
	    
		rs.getString( 
			"animalq1"  )
	    
	);
	
	
	setAnimalq2( 
	    
		rs.getString( 
			"animalq2"  )
	    
	);
	
	
	setAnimalq3( 
	    
		rs.getString( 
			"animalq3"  )
	    
	);
	
	
	setAnimalq4( 
	    
		rs.getString( 
			"animalq4"  )
	    
	);
	
	
	setProposalq1( 
	    
		rs.getString( 
			"proposalq1"  )
	    
	);
	
	
	setProposalq2( 
	    
		rs.getString( 
			"proposalq2"  )
	    
	);
	
	
	setProposalq3( 
	    
		rs.getString( 
			"proposalq3"  )
	    
	);
	
	
	setProposalq4( 
	    
		rs.getString( 
			"proposalq4"  )
	    
	);
	
	
	setProposalq5( 
	    
		rs.getString( 
			"proposalq5"  )
	    
	);
	
	
	setProposalq6( 
	    
		rs.getString( 
			"proposalq6"  )
	    
	);
	
	
	setProposalq7( 
	    
		rs.getString( 
			"proposalq7"  )
	    
	);
	
	
	setProposalq8( 
	    
		rs.getString( 
			"proposalq8"  )
	    
	);
	
	
	setBookmark( 
	    
		rs.getString( 
			"bookmark"  )
	    
	);
	
	
	setApprovalDate( 
	    
		rs.getDate( 
			"approvalDate"  )
	    
	);
	
	
	setPOnum( 
	    
		rs.getString( 
			"POnum"  )
	    
	);
	
	
	setInstitute( 
	    
		rs.getString( 
			"institute"  )
	    
	);
	
	
	setIRBnum( 
	    
		rs.getString( 
			"IRBnum"  )
	    
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
	    "insert into proposal ( owner, today, month, year, Isucsd, Isanimal, Issample, proj_name, cname, cphone, cemail, CntrOp, op1lastname, op1firstname, op1phone, op1email, op2lastname, op2firstname, op2phone, op2email, indexnum, baline1, baline2, baline3, bacity, bast, bazip, fpname, fpphone, fpemail, thours, writeup, dataanalysis, IACUCFaxed, RFCoils, restraints, physioeq, anesthetics, ancillary, op1status, op2status, IACUC, status, BioHazard, stimuli, AnimalTrans, bproj_name, contrast, AnimTransDate, IACUCDate, stdate, expdate, comments, nighttime, copis, intcomments, response, animalq1, animalq2, animalq3, animalq4, proposalq1, proposalq2, proposalq3, proposalq4, proposalq5, proposalq6, proposalq7, proposalq8, bookmark, approvalDate, POnum, institute, IRBnum, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_int( stmt, param,
		getToday() );
	setPrepStmtParam_int( stmt, param,
		getMonth() );
	setPrepStmtParam_int( stmt, param,
		getYear() );
	setPrepStmtParam_boolean( stmt, param,
		getIsucsd() );
	setPrepStmtParam_boolean( stmt, param,
		getIsanimal() );
	setPrepStmtParam_boolean( stmt, param,
		getIssample() );
	setPrepStmtParam_String( stmt, param,
		getProj_name() );
	setPrepStmtParam_String( stmt, param,
		getCname() );
	setPrepStmtParam_String( stmt, param,
		getCphone() );
	setPrepStmtParam_String( stmt, param,
		getCemail() );
	setPrepStmtParam_boolean( stmt, param,
		getCntrOp() );
	setPrepStmtParam_String( stmt, param,
		getOp1lastname() );
	setPrepStmtParam_String( stmt, param,
		getOp1firstname() );
	setPrepStmtParam_String( stmt, param,
		getOp1phone() );
	setPrepStmtParam_String( stmt, param,
		getOp1email() );
	setPrepStmtParam_String( stmt, param,
		getOp2lastname() );
	setPrepStmtParam_String( stmt, param,
		getOp2firstname() );
	setPrepStmtParam_String( stmt, param,
		getOp2phone() );
	setPrepStmtParam_String( stmt, param,
		getOp2email() );
	setPrepStmtParam_String( stmt, param,
		getIndexnum() );
	setPrepStmtParam_String( stmt, param,
		getBaline1() );
	setPrepStmtParam_String( stmt, param,
		getBaline2() );
	setPrepStmtParam_String( stmt, param,
		getBaline3() );
	setPrepStmtParam_String( stmt, param,
		getBacity() );
	setPrepStmtParam_String( stmt, param,
		getBast() );
	setPrepStmtParam_String( stmt, param,
		getBazip() );
	setPrepStmtParam_String( stmt, param,
		getFpname() );
	setPrepStmtParam_String( stmt, param,
		getFpphone() );
	setPrepStmtParam_String( stmt, param,
		getFpemail() );
	setPrepStmtParam_int( stmt, param,
		getThours() );
	setPrepStmtParam_String( stmt, param,
		getWriteup() );
	setPrepStmtParam_String( stmt, param,
		getDataanalysis() );
	setPrepStmtParam_boolean( stmt, param,
		getIACUCFaxed() );
	setPrepStmtParam_boolean( stmt, param,
		getRFCoils() );
	setPrepStmtParam_boolean( stmt, param,
		getRestraints() );
	setPrepStmtParam_boolean( stmt, param,
		getPhysioeq() );
	setPrepStmtParam_boolean( stmt, param,
		getAnesthetics() );
	setPrepStmtParam_boolean( stmt, param,
		getAncillary() );
	setPrepStmtParam_boolean( stmt, param,
		getOp1status() );
	setPrepStmtParam_boolean( stmt, param,
		getOp2status() );
	setPrepStmtParam_boolean( stmt, param,
		getIACUC() );
	setPrepStmtParam_int( stmt, param,
		getStatus() );
	setPrepStmtParam_String( stmt, param,
		getBioHazard() );
	setPrepStmtParam_boolean( stmt, param,
		getStimuli() );
	setPrepStmtParam_boolean( stmt, param,
		getAnimalTrans() );
	setPrepStmtParam_String( stmt, param,
		getBproj_name() );
	setPrepStmtParam_boolean( stmt, param,
		getContrast() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getAnimTransDate() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getIACUCDate() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getStdate() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getExpdate() );
	setPrepStmtParam_String( stmt, param,
		getComments() );
	setPrepStmtParam_boolean( stmt, param,
		getNighttime() );
	setPrepStmtParam_String( stmt, param,
		getCopis() );
	setPrepStmtParam_String( stmt, param,
		getIntcomments() );
	setPrepStmtParam_String( stmt, param,
		getResponse() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq1() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq2() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq3() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq4() );
	setPrepStmtParam_String( stmt, param,
		getProposalq1() );
	setPrepStmtParam_String( stmt, param,
		getProposalq2() );
	setPrepStmtParam_String( stmt, param,
		getProposalq3() );
	setPrepStmtParam_String( stmt, param,
		getProposalq4() );
	setPrepStmtParam_String( stmt, param,
		getProposalq5() );
	setPrepStmtParam_String( stmt, param,
		getProposalq6() );
	setPrepStmtParam_String( stmt, param,
		getProposalq7() );
	setPrepStmtParam_String( stmt, param,
		getProposalq8() );
	setPrepStmtParam_String( stmt, param,
		getBookmark() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getApprovalDate() );
	setPrepStmtParam_String( stmt, param,
		getPOnum() );
	setPrepStmtParam_String( stmt, param,
		getInstitute() );
	setPrepStmtParam_String( stmt, param,
		getIRBnum() );


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
	    "update proposal set " + getVersionColumnName() + " = ?, owner = ?, today = ?, month = ?, year = ?, Isucsd = ?, Isanimal = ?, Issample = ?, proj_name = ?, cname = ?, cphone = ?, cemail = ?, CntrOp = ?, op1lastname = ?, op1firstname = ?, op1phone = ?, op1email = ?, op2lastname = ?, op2firstname = ?, op2phone = ?, op2email = ?, indexnum = ?, baline1 = ?, baline2 = ?, baline3 = ?, bacity = ?, bast = ?, bazip = ?, fpname = ?, fpphone = ?, fpemail = ?, thours = ?, writeup = ?, dataanalysis = ?, IACUCFaxed = ?, RFCoils = ?, restraints = ?, physioeq = ?, anesthetics = ?, ancillary = ?, op1status = ?, op2status = ?, IACUC = ?, status = ?, BioHazard = ?, stimuli = ?, AnimalTrans = ?, bproj_name = ?, contrast = ?, AnimTransDate = ?, IACUCDate = ?, stdate = ?, expdate = ?, comments = ?, nighttime = ?, copis = ?, intcomments = ?, response = ?, animalq1 = ?, animalq2 = ?, animalq3 = ?, animalq4 = ?, proposalq1 = ?, proposalq2 = ?, proposalq3 = ?, proposalq4 = ?, proposalq5 = ?, proposalq6 = ?, proposalq7 = ?, proposalq8 = ?, bookmark = ?, approvalDate = ?, POnum = ?, institute = ?, IRBnum = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_int( stmt, param,
		getToday() );
	setPrepStmtParam_int( stmt, param,
		getMonth() );
	setPrepStmtParam_int( stmt, param,
		getYear() );
	setPrepStmtParam_boolean( stmt, param,
		getIsucsd() );
	setPrepStmtParam_boolean( stmt, param,
		getIsanimal() );
	setPrepStmtParam_boolean( stmt, param,
		getIssample() );
	setPrepStmtParam_String( stmt, param,
		getProj_name() );
	setPrepStmtParam_String( stmt, param,
		getCname() );
	setPrepStmtParam_String( stmt, param,
		getCphone() );
	setPrepStmtParam_String( stmt, param,
		getCemail() );
	setPrepStmtParam_boolean( stmt, param,
		getCntrOp() );
	setPrepStmtParam_String( stmt, param,
		getOp1lastname() );
	setPrepStmtParam_String( stmt, param,
		getOp1firstname() );
	setPrepStmtParam_String( stmt, param,
		getOp1phone() );
	setPrepStmtParam_String( stmt, param,
		getOp1email() );
	setPrepStmtParam_String( stmt, param,
		getOp2lastname() );
	setPrepStmtParam_String( stmt, param,
		getOp2firstname() );
	setPrepStmtParam_String( stmt, param,
		getOp2phone() );
	setPrepStmtParam_String( stmt, param,
		getOp2email() );
	setPrepStmtParam_String( stmt, param,
		getIndexnum() );
	setPrepStmtParam_String( stmt, param,
		getBaline1() );
	setPrepStmtParam_String( stmt, param,
		getBaline2() );
	setPrepStmtParam_String( stmt, param,
		getBaline3() );
	setPrepStmtParam_String( stmt, param,
		getBacity() );
	setPrepStmtParam_String( stmt, param,
		getBast() );
	setPrepStmtParam_String( stmt, param,
		getBazip() );
	setPrepStmtParam_String( stmt, param,
		getFpname() );
	setPrepStmtParam_String( stmt, param,
		getFpphone() );
	setPrepStmtParam_String( stmt, param,
		getFpemail() );
	setPrepStmtParam_int( stmt, param,
		getThours() );
	setPrepStmtParam_String( stmt, param,
		getWriteup() );
	setPrepStmtParam_String( stmt, param,
		getDataanalysis() );
	setPrepStmtParam_boolean( stmt, param,
		getIACUCFaxed() );
	setPrepStmtParam_boolean( stmt, param,
		getRFCoils() );
	setPrepStmtParam_boolean( stmt, param,
		getRestraints() );
	setPrepStmtParam_boolean( stmt, param,
		getPhysioeq() );
	setPrepStmtParam_boolean( stmt, param,
		getAnesthetics() );
	setPrepStmtParam_boolean( stmt, param,
		getAncillary() );
	setPrepStmtParam_boolean( stmt, param,
		getOp1status() );
	setPrepStmtParam_boolean( stmt, param,
		getOp2status() );
	setPrepStmtParam_boolean( stmt, param,
		getIACUC() );
	setPrepStmtParam_int( stmt, param,
		getStatus() );
	setPrepStmtParam_String( stmt, param,
		getBioHazard() );
	setPrepStmtParam_boolean( stmt, param,
		getStimuli() );
	setPrepStmtParam_boolean( stmt, param,
		getAnimalTrans() );
	setPrepStmtParam_String( stmt, param,
		getBproj_name() );
	setPrepStmtParam_boolean( stmt, param,
		getContrast() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getAnimTransDate() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getIACUCDate() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getStdate() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getExpdate() );
	setPrepStmtParam_String( stmt, param,
		getComments() );
	setPrepStmtParam_boolean( stmt, param,
		getNighttime() );
	setPrepStmtParam_String( stmt, param,
		getCopis() );
	setPrepStmtParam_String( stmt, param,
		getIntcomments() );
	setPrepStmtParam_String( stmt, param,
		getResponse() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq1() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq2() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq3() );
	setPrepStmtParam_String( stmt, param,
		getAnimalq4() );
	setPrepStmtParam_String( stmt, param,
		getProposalq1() );
	setPrepStmtParam_String( stmt, param,
		getProposalq2() );
	setPrepStmtParam_String( stmt, param,
		getProposalq3() );
	setPrepStmtParam_String( stmt, param,
		getProposalq4() );
	setPrepStmtParam_String( stmt, param,
		getProposalq5() );
	setPrepStmtParam_String( stmt, param,
		getProposalq6() );
	setPrepStmtParam_String( stmt, param,
		getProposalq7() );
	setPrepStmtParam_String( stmt, param,
		getProposalq8() );
	setPrepStmtParam_String( stmt, param,
		getBookmark() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getApprovalDate() );
	setPrepStmtParam_String( stmt, param,
		getPOnum() );
	setPrepStmtParam_String( stmt, param,
		getInstitute() );
	setPrepStmtParam_String( stmt, param,
		getIRBnum() );


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
            "delete from proposal \n" +
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
	String str = "proposalDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "today=" + data.today
+ "\n" + indent + "month=" + data.month
+ "\n" + indent + "year=" + data.year
+ "\n" + indent + "Isucsd=" + data.Isucsd
+ "\n" + indent + "Isanimal=" + data.Isanimal
+ "\n" + indent + "Issample=" + data.Issample
+ "\n" + indent + "proj_name=" + data.proj_name
+ "\n" + indent + "cname=" + data.cname
+ "\n" + indent + "cphone=" + data.cphone
+ "\n" + indent + "cemail=" + data.cemail
+ "\n" + indent + "CntrOp=" + data.CntrOp
+ "\n" + indent + "op1lastname=" + data.op1lastname
+ "\n" + indent + "op1firstname=" + data.op1firstname
+ "\n" + indent + "op1phone=" + data.op1phone
+ "\n" + indent + "op1email=" + data.op1email
+ "\n" + indent + "op2lastname=" + data.op2lastname
+ "\n" + indent + "op2firstname=" + data.op2firstname
+ "\n" + indent + "op2phone=" + data.op2phone
+ "\n" + indent + "op2email=" + data.op2email
+ "\n" + indent + "indexnum=" + data.indexnum
+ "\n" + indent + "baline1=" + data.baline1
+ "\n" + indent + "baline2=" + data.baline2
+ "\n" + indent + "baline3=" + data.baline3
+ "\n" + indent + "bacity=" + data.bacity
+ "\n" + indent + "bast=" + data.bast
+ "\n" + indent + "bazip=" + data.bazip
+ "\n" + indent + "fpname=" + data.fpname
+ "\n" + indent + "fpphone=" + data.fpphone
+ "\n" + indent + "fpemail=" + data.fpemail
+ "\n" + indent + "thours=" + data.thours
+ "\n" + indent + "writeup=" + data.writeup
+ "\n" + indent + "dataanalysis=" + data.dataanalysis
+ "\n" + indent + "IACUCFaxed=" + data.IACUCFaxed
+ "\n" + indent + "RFCoils=" + data.RFCoils
+ "\n" + indent + "restraints=" + data.restraints
+ "\n" + indent + "physioeq=" + data.physioeq
+ "\n" + indent + "anesthetics=" + data.anesthetics
+ "\n" + indent + "ancillary=" + data.ancillary
+ "\n" + indent + "op1status=" + data.op1status
+ "\n" + indent + "op2status=" + data.op2status
+ "\n" + indent + "IACUC=" + data.IACUC
+ "\n" + indent + "status=" + data.status
+ "\n" + indent + "BioHazard=" + data.BioHazard
+ "\n" + indent + "stimuli=" + data.stimuli
+ "\n" + indent + "AnimalTrans=" + data.AnimalTrans
+ "\n" + indent + "bproj_name=" + data.bproj_name
+ "\n" + indent + "contrast=" + data.contrast
+ "\n" + indent + "AnimTransDate=" + data.AnimTransDate
+ "\n" + indent + "IACUCDate=" + data.IACUCDate
+ "\n" + indent + "stdate=" + data.stdate
+ "\n" + indent + "expdate=" + data.expdate
+ "\n" + indent + "comments=" + data.comments
+ "\n" + indent + "nighttime=" + data.nighttime
+ "\n" + indent + "copis=" + data.copis
+ "\n" + indent + "intcomments=" + data.intcomments
+ "\n" + indent + "response=" + data.response
+ "\n" + indent + "animalq1=" + data.animalq1
+ "\n" + indent + "animalq2=" + data.animalq2
+ "\n" + indent + "animalq3=" + data.animalq3
+ "\n" + indent + "animalq4=" + data.animalq4
+ "\n" + indent + "proposalq1=" + data.proposalq1
+ "\n" + indent + "proposalq2=" + data.proposalq2
+ "\n" + indent + "proposalq3=" + data.proposalq3
+ "\n" + indent + "proposalq4=" + data.proposalq4
+ "\n" + indent + "proposalq5=" + data.proposalq5
+ "\n" + indent + "proposalq6=" + data.proposalq6
+ "\n" + indent + "proposalq7=" + data.proposalq7
+ "\n" + indent + "proposalq8=" + data.proposalq8
+ "\n" + indent + "bookmark=" + data.bookmark
+ "\n" + indent + "approvalDate=" + data.approvalDate
+ "\n" + indent + "POnum=" + data.POnum
+ "\n" + indent + "institute=" + data.institute
+ "\n" + indent + "IRBnum=" + data.IRBnum
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
        String str = indent + "proposalDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "today=" + data.today
+ "\n" + indent + "month=" + data.month
+ "\n" + indent + "year=" + data.year
+ "\n" + indent + "Isucsd=" + data.Isucsd
+ "\n" + indent + "Isanimal=" + data.Isanimal
+ "\n" + indent + "Issample=" + data.Issample
+ "\n" + indent + "proj_name=" + data.proj_name
+ "\n" + indent + "cname=" + data.cname
+ "\n" + indent + "cphone=" + data.cphone
+ "\n" + indent + "cemail=" + data.cemail
+ "\n" + indent + "CntrOp=" + data.CntrOp
+ "\n" + indent + "op1lastname=" + data.op1lastname
+ "\n" + indent + "op1firstname=" + data.op1firstname
+ "\n" + indent + "op1phone=" + data.op1phone
+ "\n" + indent + "op1email=" + data.op1email
+ "\n" + indent + "op2lastname=" + data.op2lastname
+ "\n" + indent + "op2firstname=" + data.op2firstname
+ "\n" + indent + "op2phone=" + data.op2phone
+ "\n" + indent + "op2email=" + data.op2email
+ "\n" + indent + "indexnum=" + data.indexnum
+ "\n" + indent + "baline1=" + data.baline1
+ "\n" + indent + "baline2=" + data.baline2
+ "\n" + indent + "baline3=" + data.baline3
+ "\n" + indent + "bacity=" + data.bacity
+ "\n" + indent + "bast=" + data.bast
+ "\n" + indent + "bazip=" + data.bazip
+ "\n" + indent + "fpname=" + data.fpname
+ "\n" + indent + "fpphone=" + data.fpphone
+ "\n" + indent + "fpemail=" + data.fpemail
+ "\n" + indent + "thours=" + data.thours
+ "\n" + indent + "writeup=" + data.writeup
+ "\n" + indent + "dataanalysis=" + data.dataanalysis
+ "\n" + indent + "IACUCFaxed=" + data.IACUCFaxed
+ "\n" + indent + "RFCoils=" + data.RFCoils
+ "\n" + indent + "restraints=" + data.restraints
+ "\n" + indent + "physioeq=" + data.physioeq
+ "\n" + indent + "anesthetics=" + data.anesthetics
+ "\n" + indent + "ancillary=" + data.ancillary
+ "\n" + indent + "op1status=" + data.op1status
+ "\n" + indent + "op2status=" + data.op2status
+ "\n" + indent + "IACUC=" + data.IACUC
+ "\n" + indent + "status=" + data.status
+ "\n" + indent + "BioHazard=" + data.BioHazard
+ "\n" + indent + "stimuli=" + data.stimuli
+ "\n" + indent + "AnimalTrans=" + data.AnimalTrans
+ "\n" + indent + "bproj_name=" + data.bproj_name
+ "\n" + indent + "contrast=" + data.contrast
+ "\n" + indent + "AnimTransDate=" + data.AnimTransDate
+ "\n" + indent + "IACUCDate=" + data.IACUCDate
+ "\n" + indent + "stdate=" + data.stdate
+ "\n" + indent + "expdate=" + data.expdate
+ "\n" + indent + "comments=" + data.comments
+ "\n" + indent + "nighttime=" + data.nighttime
+ "\n" + indent + "copis=" + data.copis
+ "\n" + indent + "intcomments=" + data.intcomments
+ "\n" + indent + "response=" + data.response
+ "\n" + indent + "animalq1=" + data.animalq1
+ "\n" + indent + "animalq2=" + data.animalq2
+ "\n" + indent + "animalq3=" + data.animalq3
+ "\n" + indent + "animalq4=" + data.animalq4
+ "\n" + indent + "proposalq1=" + data.proposalq1
+ "\n" + indent + "proposalq2=" + data.proposalq2
+ "\n" + indent + "proposalq3=" + data.proposalq3
+ "\n" + indent + "proposalq4=" + data.proposalq4
+ "\n" + indent + "proposalq5=" + data.proposalq5
+ "\n" + indent + "proposalq6=" + data.proposalq6
+ "\n" + indent + "proposalq7=" + data.proposalq7
+ "\n" + indent + "proposalq8=" + data.proposalq8
+ "\n" + indent + "bookmark=" + data.bookmark
+ "\n" + indent + "approvalDate=" + data.approvalDate
+ "\n" + indent + "POnum=" + data.POnum
+ "\n" + indent + "institute=" + data.institute
+ "\n" + indent + "IRBnum=" + data.IRBnum
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    



    /**
     * A stub method for implementing pre-commit assertions 
     * for this proposalDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this proposalDO.
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
		    "Cannot commit proposalDO ( " + toString() +
		    " ) because owner is not allowed to be null." );
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
