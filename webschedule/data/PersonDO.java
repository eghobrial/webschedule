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
 * /home/emang/myapps/webschedule/webschedule/data/PersonDO.java
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
 * Data core class, used to set, retrieve the PersonDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class PersonDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "Person" );

    /**
     * Return Person as the name of the table in the database
     * which contains PersonDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "Person";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for PersonDO attributes are defined below. */

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
     * If the isView flag is true, PersonDO.createExisting(ResultSet)
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
    private PersonDataStruct data = null;

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
    protected PersonDO ( boolean is_view )
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
    protected PersonDO ()
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
	    
	    data = new PersonDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	PersonQuery query = new PersonQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	PersonDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "PersonDO DO not found for id=" + id );
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
		    "Unable to load data for PersonDO id=" + getOId() +
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
    protected PersonDO( ObjectId id )
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
    public static PersonDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new PersonDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a PersonDO object by searching for it
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
    public static PersonDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>PersonDO</CODE>
     * from a string handle.
     */
    public static PersonDO createExisting( String handle ) {
	PersonDO ret = null;
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
     * Factory method creates a PersonDO object by searching for it
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
    protected static PersonDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	PersonDO ret = null;
	ret = new PersonDO( id );
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
    protected static PersonDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	PersonDO ret = null;
	if ( isView ) {
	    ret = new PersonDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new PersonDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a PersonDO object by searching for it
     * in the database using the PersonDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a PersonDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static PersonDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( PersonDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create PersonDO, row does not " +
		"contain PersonDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a PersonDO object by searching for it
     * in the database using the passed PersonDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a PersonDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static PersonDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( PersonDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create PersonDO, " +
		"RDBColumnValue is not PersonDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create PersonDO, bad primary key." );
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
    public static PersonDO createCopy( PersonDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	PersonDO ret = new PersonDO ();
	ret.data = ( PersonDataStruct ) data.duplicate();
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
    public static PersonDO createCopy( PersonDO orig )
    throws DatabaseManagerException, ObjectIdException {
	PersonDO ret = new PersonDO ();
	if ( null != orig.data ) {
	    ret.data = ( PersonDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( PersonDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member Login

   /* static final RDBColumn Login for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Login = 
			    new RDBColumn( table, "login" );

   /**
    * Get login of the Person
    *
    * @return login of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getLogin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.login;
   }

   /**
    * Set login of the Person
    *
    * @param login of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setLogin ( String login )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.login =  markNewValue(
	data.login, login , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Password

   /* static final RDBColumn Password for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Password = 
			    new RDBColumn( table, "password" );

   /**
    * Get password of the Person
    *
    * @return password of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPassword () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.password;
   }

   /**
    * Set password of the Person
    *
    * @param password of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPassword ( String password )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.password =  markNewValue(
	data.password, password , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Lastname

   /* static final RDBColumn Lastname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Lastname = 
			    new RDBColumn( table, "lastname" );

   /**
    * Get lastname of the Person
    *
    * @return lastname of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getLastname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.lastname;
   }

   /**
    * Set lastname of the Person
    *
    * @param lastname of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setLastname ( String lastname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.lastname =  markNewValue(
	data.lastname, lastname , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Firstname

   /* static final RDBColumn Firstname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Firstname = 
			    new RDBColumn( table, "firstname" );

   /**
    * Get firstname of the Person
    *
    * @return firstname of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFirstname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.firstname;
   }

   /**
    * Set firstname of the Person
    *
    * @param firstname of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setFirstname ( String firstname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.firstname =  markNewValue(
	data.firstname, firstname , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Office

   /* static final RDBColumn Office for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Office = 
			    new RDBColumn( table, "office" );

   /**
    * Get office of the Person
    *
    * @return office of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOffice () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.office;
   }

   /**
    * Set office of the Person
    *
    * @param office of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setOffice ( String office )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.office =  markNewValue(
	data.office, office , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Phone

   /* static final RDBColumn Phone for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Phone = 
			    new RDBColumn( table, "phone" );

   /**
    * Get phone of the Person
    *
    * @return phone of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPhone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.phone;
   }

   /**
    * Set phone of the Person
    *
    * @param phone of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPhone ( String phone )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.phone =  markNewValue(
	data.phone, phone , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsAdmin

   /* static final RDBColumn IsAdmin for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsAdmin = 
			    new RDBColumn( table, "isAdmin" );

   /**
    * Get isAdmin of the Person
    *
    * @return isAdmin of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsAdmin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isAdmin;
   }

   /**
    * Set isAdmin of the Person
    *
    * @param isAdmin of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsAdmin ( boolean isAdmin )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isAdmin =  markNewValue(
	data.isAdmin, isAdmin  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsDeveloper

   /* static final RDBColumn IsDeveloper for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsDeveloper = 
			    new RDBColumn( table, "isDeveloper" );

   /**
    * Get isDeveloper of the Person
    *
    * @return isDeveloper of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsDeveloper () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isDeveloper;
   }

   /**
    * Set isDeveloper of the Person
    *
    * @param isDeveloper of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsDeveloper ( boolean isDeveloper )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isDeveloper =  markNewValue(
	data.isDeveloper, isDeveloper  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Email

   /* static final RDBColumn Email for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Email = 
			    new RDBColumn( table, "email" );

   /**
    * Get email of the Person
    *
    * @return email of the Person
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
    * Set email of the Person
    *
    * @param email of the Person
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
   


////////////////////////// data member IsAuth

   /* static final RDBColumn IsAuth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsAuth = 
			    new RDBColumn( table, "isAuth" );

   /**
    * Get isAuth of the Person
    *
    * @return isAuth of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsAuth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isAuth;
   }

   /**
    * Set isAuth of the Person
    *
    * @param isAuth of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsAuth ( boolean isAuth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isAuth =  markNewValue(
	data.isAuth, isAuth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsPadmin

   /* static final RDBColumn IsPadmin for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsPadmin = 
			    new RDBColumn( table, "isPadmin" );

   /**
    * Get isPadmin of the Person
    *
    * @return isPadmin of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsPadmin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isPadmin;
   }

   /**
    * Set isPadmin of the Person
    *
    * @param isPadmin of the Person
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsPadmin ( boolean isPadmin )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isPadmin =  markNewValue(
	data.isPadmin, isPadmin  );
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
    protected PersonDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from Person.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new PersonDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setLogin( 
	    
		rs.getString( 
			"login"  )
	    
	);
	
	
	setPassword( 
	    
		rs.getString( 
			"password"  )
	    
	);
	
	
	setLastname( 
	    
		rs.getString( 
			"lastname"  )
	    
	);
	
	
	setFirstname( 
	    
		rs.getString( 
			"firstname"  )
	    
	);
	
	
	setOffice( 
	    
		rs.getString( 
			"office"  )
	    
	);
	
	
	setPhone( 
	    
		rs.getString( 
			"phone"  )
	    
	);
	
	
	setIsAdmin( 
	    
		rs.getBoolean( 
			"isAdmin"  )
	    
	);
	
	
	setIsDeveloper( 
	    
		rs.getBoolean( 
			"isDeveloper"  )
	    
	);
	
	
	setEmail( 
	    
		rs.getString( 
			"email"  )
	    
	);
	
	
	setIsAuth( 
	    
		rs.getBoolean( 
			"isAuth"  )
	    
	);
	
	
	setIsPadmin( 
	    
		rs.getBoolean( 
			"isPadmin"  )
	    
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
	    "insert into Person ( login, password, lastname, firstname, office, phone, isAdmin, isDeveloper, email, isAuth, isPadmin, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_String( stmt, param,
		getLogin() );
	setPrepStmtParam_String( stmt, param,
		getPassword() );
	setPrepStmtParam_String( stmt, param,
		getLastname() );
	setPrepStmtParam_String( stmt, param,
		getFirstname() );
	setPrepStmtParam_String( stmt, param,
		getOffice() );
	setPrepStmtParam_String( stmt, param,
		getPhone() );
	setPrepStmtParam_boolean( stmt, param,
		getIsAdmin() );
	setPrepStmtParam_boolean( stmt, param,
		getIsDeveloper() );
	setPrepStmtParam_String( stmt, param,
		getEmail() );
	setPrepStmtParam_boolean( stmt, param,
		getIsAuth() );
	setPrepStmtParam_boolean( stmt, param,
		getIsPadmin() );


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
	    "update Person set " + getVersionColumnName() + " = ?, login = ?, password = ?, lastname = ?, firstname = ?, office = ?, phone = ?, isAdmin = ?, isDeveloper = ?, email = ?, isAuth = ?, isPadmin = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_String( stmt, param,
		getLogin() );
	setPrepStmtParam_String( stmt, param,
		getPassword() );
	setPrepStmtParam_String( stmt, param,
		getLastname() );
	setPrepStmtParam_String( stmt, param,
		getFirstname() );
	setPrepStmtParam_String( stmt, param,
		getOffice() );
	setPrepStmtParam_String( stmt, param,
		getPhone() );
	setPrepStmtParam_boolean( stmt, param,
		getIsAdmin() );
	setPrepStmtParam_boolean( stmt, param,
		getIsDeveloper() );
	setPrepStmtParam_String( stmt, param,
		getEmail() );
	setPrepStmtParam_boolean( stmt, param,
		getIsAuth() );
	setPrepStmtParam_boolean( stmt, param,
		getIsPadmin() );


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
            "delete from Person \n" +
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
	String str = "PersonDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "login=" + data.login
+ "\n" + indent + "password=" + data.password
+ "\n" + indent + "lastname=" + data.lastname
+ "\n" + indent + "firstname=" + data.firstname
+ "\n" + indent + "office=" + data.office
+ "\n" + indent + "phone=" + data.phone
+ "\n" + indent + "isAdmin=" + data.isAdmin
+ "\n" + indent + "isDeveloper=" + data.isDeveloper
+ "\n" + indent + "email=" + data.email
+ "\n" + indent + "isAuth=" + data.isAuth
+ "\n" + indent + "isPadmin=" + data.isPadmin
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
        String str = indent + "PersonDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "login=" + data.login
+ "\n" + indent + "password=" + data.password
+ "\n" + indent + "lastname=" + data.lastname
+ "\n" + indent + "firstname=" + data.firstname
+ "\n" + indent + "office=" + data.office
+ "\n" + indent + "phone=" + data.phone
+ "\n" + indent + "isAdmin=" + data.isAdmin
+ "\n" + indent + "isDeveloper=" + data.isDeveloper
+ "\n" + indent + "email=" + data.email
+ "\n" + indent + "isAuth=" + data.isAuth
+ "\n" + indent + "isPadmin=" + data.isPadmin
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    
    /**
     * Get array of C_eventDO objects that refer to this DO.
     *
     * @return array of C_eventDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.C_eventDO[] getC_eventDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.C_eventDO[] ret = null;
	try {
	    webschedule.data.C_eventQuery q = new webschedule.data.C_eventQuery();
	    q.setQueryOwner( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.C_eventDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single C_eventDO object
     * that refers to this DO.
     *
     * @return C_eventDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one C_eventDO object was found.
     */
    public webschedule.data.C_eventDO getC_eventDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.C_eventQuery q = new webschedule.data.C_eventQuery();
	q.setQueryOwner( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a C_eventDO object that refers to this DO.
     *
     * @param referrer C_eventDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addC_eventDO( webschedule.data.C_eventDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addC_eventDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a C_eventDO object that refers to this DO.
     *
     * @param referrer C_eventDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addC_eventDO( webschedule.data.C_eventDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOwner( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a C_eventDO object that refers to this DO.
     *
     * @param referrer C_eventDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeC_eventDO( webschedule.data.C_eventDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeC_eventDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a C_eventDO object that refers to this DO.
     *
     * @param referrer C_eventDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeC_eventDO( webschedule.data.C_eventDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO referred = referrer.getOwner();
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
     * Get array of ProblemDO objects that refer to this DO.
     *
     * @return array of ProblemDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.ProblemDO[] getProblemDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.ProblemDO[] ret = null;
	try {
	    webschedule.data.ProblemQuery q = new webschedule.data.ProblemQuery();
	    q.setQueryOwner( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProblemDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single ProblemDO object
     * that refers to this DO.
     *
     * @return ProblemDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one ProblemDO object was found.
     */
    public webschedule.data.ProblemDO getProblemDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.ProblemQuery q = new webschedule.data.ProblemQuery();
	q.setQueryOwner( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a ProblemDO object that refers to this DO.
     *
     * @param referrer ProblemDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProblemDO( webschedule.data.ProblemDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addProblemDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a ProblemDO object that refers to this DO.
     *
     * @param referrer ProblemDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProblemDO( webschedule.data.ProblemDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOwner( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a ProblemDO object that refers to this DO.
     *
     * @param referrer ProblemDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProblemDO( webschedule.data.ProblemDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeProblemDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a ProblemDO object that refers to this DO.
     *
     * @param referrer ProblemDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProblemDO( webschedule.data.ProblemDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO referred = referrer.getOwner();
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
     * Get array of ProjectDO objects that refer to this DO.
     *
     * @return array of ProjectDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.ProjectDO[] getProjectDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.ProjectDO[] ret = null;
	try {
	    webschedule.data.ProjectQuery q = new webschedule.data.ProjectQuery();
	    q.setQueryOwner( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProjectDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single ProjectDO object
     * that refers to this DO.
     *
     * @return ProjectDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one ProjectDO object was found.
     */
    public webschedule.data.ProjectDO getProjectDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.ProjectQuery q = new webschedule.data.ProjectQuery();
	q.setQueryOwner( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a ProjectDO object that refers to this DO.
     *
     * @param referrer ProjectDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProjectDO( webschedule.data.ProjectDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addProjectDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a ProjectDO object that refers to this DO.
     *
     * @param referrer ProjectDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProjectDO( webschedule.data.ProjectDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOwner( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a ProjectDO object that refers to this DO.
     *
     * @param referrer ProjectDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProjectDO( webschedule.data.ProjectDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeProjectDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a ProjectDO object that refers to this DO.
     *
     * @param referrer ProjectDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProjectDO( webschedule.data.ProjectDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO referred = referrer.getOwner();
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
     * Get array of R_eventDO objects that refer to this DO.
     *
     * @return array of R_eventDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.R_eventDO[] getR_eventDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.R_eventDO[] ret = null;
	try {
	    webschedule.data.R_eventQuery q = new webschedule.data.R_eventQuery();
	    q.setQueryOwner( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.R_eventDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single R_eventDO object
     * that refers to this DO.
     *
     * @return R_eventDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one R_eventDO object was found.
     */
    public webschedule.data.R_eventDO getR_eventDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.R_eventQuery q = new webschedule.data.R_eventQuery();
	q.setQueryOwner( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a R_eventDO object that refers to this DO.
     *
     * @param referrer R_eventDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addR_eventDO( webschedule.data.R_eventDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addR_eventDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a R_eventDO object that refers to this DO.
     *
     * @param referrer R_eventDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addR_eventDO( webschedule.data.R_eventDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOwner( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a R_eventDO object that refers to this DO.
     *
     * @param referrer R_eventDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeR_eventDO( webschedule.data.R_eventDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeR_eventDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a R_eventDO object that refers to this DO.
     *
     * @param referrer R_eventDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeR_eventDO( webschedule.data.R_eventDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO referred = referrer.getOwner();
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
	    q.setQueryOwner( this );
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
	q.setQueryOwner( this );
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
        referrer.setOwner( this );
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
	PersonDO referred = referrer.getOwner();
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
     * Get array of proposalDO objects that refer to this DO.
     *
     * @return array of proposalDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public webschedule.data.proposalDO[] getproposalDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.proposalDO[] ret = null;
	try {
	    webschedule.data.proposalQuery q = new webschedule.data.proposalQuery();
	    q.setQueryOwner( this );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.proposalDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single proposalDO object
     * that refers to this DO.
     *
     * @return proposalDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     * @exception NonUniqueQueryException
     *   If more than one proposalDO object was found.
     */
    public webschedule.data.proposalDO getproposalDO () 
    throws DataObjectException, QueryException, NonUniqueQueryException {
	webschedule.data.proposalQuery q = new webschedule.data.proposalQuery();
	q.setQueryOwner( this );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Add (set & commit) a proposalDO object that refers to this DO.
     *
     * @param referrer proposalDO to be set to point to this DO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addproposalDO( webschedule.data.proposalDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addproposalDO( referrer, null );
    }
 
 
    /**
     * Add (set & commit) a proposalDO object that refers to this DO.
     *
     * @param referrer proposalDO to be set to point to this DO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addproposalDO( webschedule.data.proposalDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        referrer.setOwner( this );
        referrer.commit( tran );
    }

 
    /**
     * Remove (delete) a proposalDO object that refers to this DO.
     *
     * @param referrer proposalDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeproposalDO( webschedule.data.proposalDO referrer )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeproposalDO( referrer, null );
    }
 
 
    /**
     * Remove (delete) a proposalDO object that refers to this DO.
     *
     * @param referrer proposalDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeproposalDO( webschedule.data.proposalDO referrer, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO referred = referrer.getOwner();
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
     * A stub method for implementing pre-commit assertions 
     * for this PersonDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this PersonDO.
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
	    webschedule.data.C_eventDO[] a = getC_eventDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.ProblemDO[] a = getProblemDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.ProjectDO[] a = getProjectDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.R_eventDO[] a = getR_eventDOArray();
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
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.proposalDO[] a = getproposalDOArray();
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
