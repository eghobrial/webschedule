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
 * /home/emang/myapps/webschedule/webschedule/data/OperatorDO.java
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
 * Data core class, used to set, retrieve the OperatorDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class OperatorDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "Operator" );

    /**
     * Return Operator as the name of the table in the database
     * which contains OperatorDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "Operator";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for OperatorDO attributes are defined below. */

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
     * If the isView flag is true, OperatorDO.createExisting(ResultSet)
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
    private OperatorDataStruct data = null;

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
    protected OperatorDO ( boolean is_view )
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
    protected OperatorDO ()
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
	    
	    data = new OperatorDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	OperatorQuery query = new OperatorQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	OperatorDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "OperatorDO DO not found for id=" + id );
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
		    "Unable to load data for OperatorDO id=" + getOId() +
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
    protected OperatorDO( ObjectId id )
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
    public static OperatorDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new OperatorDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a OperatorDO object by searching for it
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
    public static OperatorDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>OperatorDO</CODE>
     * from a string handle.
     */
    public static OperatorDO createExisting( String handle ) {
	OperatorDO ret = null;
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
     * Factory method creates a OperatorDO object by searching for it
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
    protected static OperatorDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	OperatorDO ret = null;
	ret = new OperatorDO( id );
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
    protected static OperatorDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	OperatorDO ret = null;
	if ( isView ) {
	    ret = new OperatorDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new OperatorDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a OperatorDO object by searching for it
     * in the database using the OperatorDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a OperatorDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static OperatorDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( OperatorDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create OperatorDO, row does not " +
		"contain OperatorDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a OperatorDO object by searching for it
     * in the database using the passed OperatorDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a OperatorDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static OperatorDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( OperatorDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create OperatorDO, " +
		"RDBColumnValue is not OperatorDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create OperatorDO, bad primary key." );
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
    public static OperatorDO createCopy( OperatorDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	OperatorDO ret = new OperatorDO ();
	ret.data = ( OperatorDataStruct ) data.duplicate();
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
    public static OperatorDO createCopy( OperatorDO orig )
    throws DatabaseManagerException, ObjectIdException {
	OperatorDO ret = new OperatorDO ();
	if ( null != orig.data ) {
	    ret.data = ( OperatorDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( OperatorDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member FirstName

   /* static final RDBColumn FirstName for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn FirstName = 
			    new RDBColumn( table, "FirstName" );

   /**
    * Get FirstName of the Operator
    *
    * @return FirstName of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFirstName () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.FirstName;
   }

   /**
    * Set FirstName of the Operator
    *
    * @param FirstName of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setFirstName ( String FirstName )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.FirstName =  markNewValue(
	data.FirstName, FirstName , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member LastName

   /* static final RDBColumn LastName for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn LastName = 
			    new RDBColumn( table, "LastName" );

   /**
    * Get LastName of the Operator
    *
    * @return LastName of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getLastName () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.LastName;
   }

   /**
    * Set LastName of the Operator
    *
    * @param LastName of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setLastName ( String LastName )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.LastName =  markNewValue(
	data.LastName, LastName , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Certday

   /* static final RDBColumn Certday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Certday = 
			    new RDBColumn( table, "certday" );

   /**
    * Get certday of the Operator
    *
    * @return certday of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCertday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.certday;
   }

   /**
    * Set certday of the Operator
    *
    * @param certday of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCertday ( int certday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.certday =  markNewValue(
	data.certday, certday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Certmonth

   /* static final RDBColumn Certmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Certmonth = 
			    new RDBColumn( table, "certmonth" );

   /**
    * Get certmonth of the Operator
    *
    * @return certmonth of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCertmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.certmonth;
   }

   /**
    * Set certmonth of the Operator
    *
    * @param certmonth of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCertmonth ( int certmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.certmonth =  markNewValue(
	data.certmonth, certmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Certyear

   /* static final RDBColumn Certyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Certyear = 
			    new RDBColumn( table, "certyear" );

   /**
    * Get certyear of the Operator
    *
    * @return certyear of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCertyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.certyear;
   }

   /**
    * Set certyear of the Operator
    *
    * @param certyear of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCertyear ( int certyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.certyear =  markNewValue(
	data.certyear, certyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Lastscanday

   /* static final RDBColumn Lastscanday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Lastscanday = 
			    new RDBColumn( table, "lastscanday" );

   /**
    * Get lastscanday of the Operator
    *
    * @return lastscanday of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getLastscanday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.lastscanday;
   }

   /**
    * Set lastscanday of the Operator
    *
    * @param lastscanday of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setLastscanday ( int lastscanday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.lastscanday =  markNewValue(
	data.lastscanday, lastscanday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Lastscanmonth

   /* static final RDBColumn Lastscanmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Lastscanmonth = 
			    new RDBColumn( table, "lastscanmonth" );

   /**
    * Get lastscanmonth of the Operator
    *
    * @return lastscanmonth of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getLastscanmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.lastscanmonth;
   }

   /**
    * Set lastscanmonth of the Operator
    *
    * @param lastscanmonth of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setLastscanmonth ( int lastscanmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.lastscanmonth =  markNewValue(
	data.lastscanmonth, lastscanmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Lastscanyear

   /* static final RDBColumn Lastscanyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Lastscanyear = 
			    new RDBColumn( table, "lastscanyear" );

   /**
    * Get lastscanyear of the Operator
    *
    * @return lastscanyear of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getLastscanyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.lastscanyear;
   }

   /**
    * Set lastscanyear of the Operator
    *
    * @param lastscanyear of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setLastscanyear ( int lastscanyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.lastscanyear =  markNewValue(
	data.lastscanyear, lastscanyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Email

   /* static final RDBColumn Email for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Email = 
			    new RDBColumn( table, "email" );

   /**
    * Get email of the Operator
    *
    * @return email of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getEmail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.email;
   }

   /**
    * Set email of the Operator
    *
    * @param email of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setEmail ( String email )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.email =  markNewValue(
	data.email, email , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Recertday

   /* static final RDBColumn Recertday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Recertday = 
			    new RDBColumn( table, "recertday" );

   /**
    * Get recertday of the Operator
    *
    * @return recertday of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getRecertday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.recertday;
   }

   /**
    * Set recertday of the Operator
    *
    * @param recertday of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setRecertday ( int recertday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.recertday =  markNewValue(
	data.recertday, recertday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Recertmonth

   /* static final RDBColumn Recertmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Recertmonth = 
			    new RDBColumn( table, "recertmonth" );

   /**
    * Get recertmonth of the Operator
    *
    * @return recertmonth of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getRecertmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.recertmonth;
   }

   /**
    * Set recertmonth of the Operator
    *
    * @param recertmonth of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setRecertmonth ( int recertmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.recertmonth =  markNewValue(
	data.recertmonth, recertmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Recertyear

   /* static final RDBColumn Recertyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Recertyear = 
			    new RDBColumn( table, "recertyear" );

   /**
    * Get recertyear of the Operator
    *
    * @return recertyear of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getRecertyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.recertyear;
   }

   /**
    * Set recertyear of the Operator
    *
    * @param recertyear of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setRecertyear ( int recertyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.recertyear =  markNewValue(
	data.recertyear, recertyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member SFTTS

   /* static final RDBColumn SFTTS for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn SFTTS = 
			    new RDBColumn( table, "SFTTS" );

   /**
    * Get SFTTS of the Operator
    *
    * @return SFTTS of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Timestamp getSFTTS () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.SFTTS;
   }

   /**
    * Set SFTTS of the Operator
    *
    * @param SFTTS of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setSFTTS ( java.sql.Timestamp SFTTS )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.SFTTS =  markNewValue(
	data.SFTTS, SFTTS  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsExp

   /* static final RDBColumn IsExp for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsExp = 
			    new RDBColumn( table, "isExp" );

   /**
    * Get isExp of the Operator
    *
    * @return isExp of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsExp () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isExp;
   }

   /**
    * Set isExp of the Operator
    *
    * @param isExp of the Operator
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsExp ( boolean isExp )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isExp =  markNewValue(
	data.isExp, isExp  );
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
    protected OperatorDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from Operator.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new OperatorDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setFirstName( 
	    
		rs.getString( 
			"FirstName"  )
	    
	);
	
	
	setLastName( 
	    
		rs.getString( 
			"LastName"  )
	    
	);
	
	
	setCertday( 
	    
		rs.getInt( 
			"certday"  )
	    
	);
	
	
	setCertmonth( 
	    
		rs.getInt( 
			"certmonth"  )
	    
	);
	
	
	setCertyear( 
	    
		rs.getInt( 
			"certyear"  )
	    
	);
	
	
	setLastscanday( 
	    
		rs.getInt( 
			"lastscanday"  )
	    
	);
	
	
	setLastscanmonth( 
	    
		rs.getInt( 
			"lastscanmonth"  )
	    
	);
	
	
	setLastscanyear( 
	    
		rs.getInt( 
			"lastscanyear"  )
	    
	);
	
	
	setEmail( 
	    
		rs.getString( 
			"email"  )
	    
	);
	
	
	setRecertday( 
	    
		rs.getInt( 
			"recertday"  )
	    
	);
	
	
	setRecertmonth( 
	    
		rs.getInt( 
			"recertmonth"  )
	    
	);
	
	
	setRecertyear( 
	    
		rs.getInt( 
			"recertyear"  )
	    
	);
	
	
	setSFTTS( 
	    
		rs.getTimestamp( 
			"SFTTS"  )
	    
	);
	
	
	setIsExp( 
	    
		rs.getBoolean( 
			"isExp"  )
	    
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
	    "insert into Operator ( FirstName, LastName, certday, certmonth, certyear, lastscanday, lastscanmonth, lastscanyear, email, recertday, recertmonth, recertyear, SFTTS, isExp, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_String( stmt, param,
		getFirstName() );
	setPrepStmtParam_String( stmt, param,
		getLastName() );
	setPrepStmtParam_int( stmt, param,
		getCertday() );
	setPrepStmtParam_int( stmt, param,
		getCertmonth() );
	setPrepStmtParam_int( stmt, param,
		getCertyear() );
	setPrepStmtParam_int( stmt, param,
		getLastscanday() );
	setPrepStmtParam_int( stmt, param,
		getLastscanmonth() );
	setPrepStmtParam_int( stmt, param,
		getLastscanyear() );
	setPrepStmtParam_String( stmt, param,
		getEmail() );
	setPrepStmtParam_int( stmt, param,
		getRecertday() );
	setPrepStmtParam_int( stmt, param,
		getRecertmonth() );
	setPrepStmtParam_int( stmt, param,
		getRecertyear() );
	setPrepStmtParam_java_sql_Timestamp( stmt, param,
		getSFTTS() );
	setPrepStmtParam_boolean( stmt, param,
		getIsExp() );


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
	    "update Operator set " + getVersionColumnName() + " = ?, FirstName = ?, LastName = ?, certday = ?, certmonth = ?, certyear = ?, lastscanday = ?, lastscanmonth = ?, lastscanyear = ?, email = ?, recertday = ?, recertmonth = ?, recertyear = ?, SFTTS = ?, isExp = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_String( stmt, param,
		getFirstName() );
	setPrepStmtParam_String( stmt, param,
		getLastName() );
	setPrepStmtParam_int( stmt, param,
		getCertday() );
	setPrepStmtParam_int( stmt, param,
		getCertmonth() );
	setPrepStmtParam_int( stmt, param,
		getCertyear() );
	setPrepStmtParam_int( stmt, param,
		getLastscanday() );
	setPrepStmtParam_int( stmt, param,
		getLastscanmonth() );
	setPrepStmtParam_int( stmt, param,
		getLastscanyear() );
	setPrepStmtParam_String( stmt, param,
		getEmail() );
	setPrepStmtParam_int( stmt, param,
		getRecertday() );
	setPrepStmtParam_int( stmt, param,
		getRecertmonth() );
	setPrepStmtParam_int( stmt, param,
		getRecertyear() );
	setPrepStmtParam_java_sql_Timestamp( stmt, param,
		getSFTTS() );
	setPrepStmtParam_boolean( stmt, param,
		getIsExp() );


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
            "delete from Operator \n" +
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
	String str = "OperatorDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "FirstName=" + data.FirstName
+ "\n" + indent + "LastName=" + data.LastName
+ "\n" + indent + "certday=" + data.certday
+ "\n" + indent + "certmonth=" + data.certmonth
+ "\n" + indent + "certyear=" + data.certyear
+ "\n" + indent + "lastscanday=" + data.lastscanday
+ "\n" + indent + "lastscanmonth=" + data.lastscanmonth
+ "\n" + indent + "lastscanyear=" + data.lastscanyear
+ "\n" + indent + "email=" + data.email
+ "\n" + indent + "recertday=" + data.recertday
+ "\n" + indent + "recertmonth=" + data.recertmonth
+ "\n" + indent + "recertyear=" + data.recertyear
+ "\n" + indent + "SFTTS=" + data.SFTTS
+ "\n" + indent + "isExp=" + data.isExp
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
        String str = indent + "OperatorDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "FirstName=" + data.FirstName
+ "\n" + indent + "LastName=" + data.LastName
+ "\n" + indent + "certday=" + data.certday
+ "\n" + indent + "certmonth=" + data.certmonth
+ "\n" + indent + "certyear=" + data.certyear
+ "\n" + indent + "lastscanday=" + data.lastscanday
+ "\n" + indent + "lastscanmonth=" + data.lastscanmonth
+ "\n" + indent + "lastscanyear=" + data.lastscanyear
+ "\n" + indent + "email=" + data.email
+ "\n" + indent + "recertday=" + data.recertday
+ "\n" + indent + "recertmonth=" + data.recertmonth
+ "\n" + indent + "recertyear=" + data.recertyear
+ "\n" + indent + "SFTTS=" + data.SFTTS
+ "\n" + indent + "isExp=" + data.isExp
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    
    /**
     * Get array of OperatesDO objects that refer to this DO.
     *
     * @return array of OperatesDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.OperatesDO[] getOperatesDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.OperatesDO[] ret = null;
	try {
	    webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	    q.setQueryOperator( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.OperatesDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single OperatesDO object
     * that refers to this DO.
     *
     * @return OperatesDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one OperatesDO object was found.
     */
    public webschedule.data.OperatesDO getOperatesDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryOperator( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a OperatesDO object that refers to this DO.
     *
     * @param referrer OperatesDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addOperatesDO( webschedule.data.OperatesDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addOperatesDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a OperatesDO object that refers to this DO.
     *
     * @param referrer OperatesDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addOperatesDO( webschedule.data.OperatesDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOperator( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a OperatesDO object that refers to this DO.
     *
     * @param referrer OperatesDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeOperatesDO( webschedule.data.OperatesDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeOperatesDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a OperatesDO object that refers to this DO.
     *
     * @param referrer OperatesDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeOperatesDO( webschedule.data.OperatesDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	OperatorDO referred = referrer.getOperator();
	String referredHandle = referred.getHandle();
	String mydoHandle = this.getHandle();
	if ( null == referredHandle || null == mydoHandle || 
	     ( ! referredHandle.equals( mydoHandle ) ) ) {
	    throw new DataObjectException( "Object " + referrer +
		" does not refer to object " + this +
		", cannot be removed this way." );
	}
        referrer.delete( tran );
    }
 

    /**
     * Get array of S_eventDO objects that refer to this DO.
     *
     * @return array of S_eventDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.S_eventDO[] getS_eventDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.S_eventDO[] ret = null;
	try {
	    webschedule.data.S_eventQuery q = new webschedule.data.S_eventQuery();
	    q.setQueryOperator( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.S_eventDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single S_eventDO object
     * that refers to this DO.
     *
     * @return S_eventDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one S_eventDO object was found.
     */
    public webschedule.data.S_eventDO getS_eventDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.S_eventQuery q = new webschedule.data.S_eventQuery();
	q.setQueryOperator( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a S_eventDO object that refers to this DO.
     *
     * @param referrer S_eventDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addS_eventDO( webschedule.data.S_eventDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addS_eventDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a S_eventDO object that refers to this DO.
     *
     * @param referrer S_eventDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addS_eventDO( webschedule.data.S_eventDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOperator( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a S_eventDO object that refers to this DO.
     *
     * @param referrer S_eventDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeS_eventDO( webschedule.data.S_eventDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeS_eventDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a S_eventDO object that refers to this DO.
     *
     * @param referrer S_eventDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeS_eventDO( webschedule.data.S_eventDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	OperatorDO referred = referrer.getOperator();
	String referredHandle = referred.getHandle();
	String mydoHandle = this.getHandle();
	if ( null == referredHandle || null == mydoHandle || 
	     ( ! referredHandle.equals( mydoHandle ) ) ) {
	    throw new DataObjectException( "Object " + referrer +
		" does not refer to object " + this +
		", cannot be removed this way." );
	}
        referrer.delete( tran );
    }
 



    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * get array of ProjectDO objects that indirectly refer
     * to this DO.
     *
     * @return array of ProjectDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.ProjectDO[] getProjectDOArray_via_Operates () 
    throws DataObjectException {
	webschedule.data.ProjectDO[] ret = null;
	try {
	    webschedule.data.OperatesDO[] arr = getOperatesDOArray();
	    ret = new webschedule.data.ProjectDO[ arr.length ];
	    for ( int i = 0; i < arr.length; i++ ) {
		ret[ i ] = arr[ i ].getProject();
	    }
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: ", e );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProjectDO[ 0 ];
	}
	return ret;
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a ProjectDO object that indirectly refers
     * to this DO.
     *
     * @param d The ProjectDO to add to the OperatesDO mapping
     * for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapProject_via_OperatesDO( webschedule.data.ProjectDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	mapProject_via_OperatesDO( d, null );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a ProjectDO object that indirectly refers to this DO.
     *
     * @param b The ProjectDO to add to the OperatesDO mapping for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapProject_via_OperatesDO( webschedule.data.ProjectDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesDO m = null;
	try {
	    m = webschedule.data.OperatesDO.createVirgin();
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"webschedule.data.OperatesDO.createVirgin failed", e );
	}
	m.setProject( d );
	m.setOperator( this );
	m.commit( tran );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the ProjectDO object that indirectly refers
     * to this DO.
     *
     * @param d The ProjectDO to remove from the OperatesDO mapping
     * for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public void unmapProject_via_OperatesDO( webschedule.data.ProjectDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	unmapProject_via_OperatesDO( d, null );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the ProjectDO object that indirectly refers
     * to this DO.
     *
     * @param b The ProjectDO to remove from the OperatesDO mapping
     * for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public void unmapProject_via_OperatesDO( webschedule.data.ProjectDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryOperator( this );
	q.setQueryProject( d );
	q.requireUniqueInstance();
	webschedule.data.OperatesDO m = null;
	try {
	    m = q.getNextDO();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( "Multiple mappings for " +
		this + " and " + d + " in webschedule.data.Operates table." );
	}
	m.delete( tran );
    }


    /**
     * A stub method for implementing pre-commit assertions 
     * for this OperatorDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this OperatorDO.
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
	  	
	{
	    // perform cascading delete on referring table
	    webschedule.data.OperatesDO[] a = getOperatesDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.S_eventDO[] a = getS_eventDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	

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
