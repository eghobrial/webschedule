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
 * /home/emang/myapps/webschedule/webschedule/data/ProblemDO.java
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
 * Data core class, used to set, retrieve the ProblemDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class ProblemDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "Problem" );

    /**
     * Return Problem as the name of the table in the database
     * which contains ProblemDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "Problem";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for ProblemDO attributes are defined below. */

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
     * If the isView flag is true, ProblemDO.createExisting(ResultSet)
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
    private ProblemDataStruct data = null;

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
    protected ProblemDO ( boolean is_view )
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
    protected ProblemDO ()
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
	    
	    data = new ProblemDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	ProblemQuery query = new ProblemQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	ProblemDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "ProblemDO DO not found for id=" + id );
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
		    "Unable to load data for ProblemDO id=" + getOId() +
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
    protected ProblemDO( ObjectId id )
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
    public static ProblemDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new ProblemDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a ProblemDO object by searching for it
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
    public static ProblemDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>ProblemDO</CODE>
     * from a string handle.
     */
    public static ProblemDO createExisting( String handle ) {
	ProblemDO ret = null;
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
     * Factory method creates a ProblemDO object by searching for it
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
    protected static ProblemDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	ProblemDO ret = null;
	ret = new ProblemDO( id );
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
    protected static ProblemDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	ProblemDO ret = null;
	if ( isView ) {
	    ret = new ProblemDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new ProblemDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a ProblemDO object by searching for it
     * in the database using the ProblemDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a ProblemDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ProblemDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( ProblemDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create ProblemDO, row does not " +
		"contain ProblemDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a ProblemDO object by searching for it
     * in the database using the passed ProblemDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a ProblemDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ProblemDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( ProblemDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create ProblemDO, " +
		"RDBColumnValue is not ProblemDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create ProblemDO, bad primary key." );
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
    public static ProblemDO createCopy( ProblemDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	ProblemDO ret = new ProblemDO ();
	ret.data = ( ProblemDataStruct ) data.duplicate();
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
    public static ProblemDO createCopy( ProblemDO orig )
    throws DatabaseManagerException, ObjectIdException {
	ProblemDO ret = new ProblemDO ();
	if ( null != orig.data ) {
	    ret.data = ( ProblemDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( ProblemDO orig ) {
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
    * Get owner of the Problem
    *
    * @return owner of the Problem
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
    * Set owner of the Problem
    *
    * @param owner of the Problem
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
   


////////////////////////// data member Severitycode

   /* static final RDBColumn Severitycode for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Severitycode = 
			    new RDBColumn( table, "severitycode" );

   /**
    * Get severitycode of the Problem
    *
    * @return severitycode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getSeveritycode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.severitycode;
   }

   /**
    * Set severitycode of the Problem
    *
    * @param severitycode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setSeveritycode ( int severitycode )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.severitycode =  markNewValue(
	data.severitycode, severitycode  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Classcode

   /* static final RDBColumn Classcode for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Classcode = 
			    new RDBColumn( table, "classcode" );

   /**
    * Get classcode of the Problem
    *
    * @return classcode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getClasscode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.classcode;
   }

   /**
    * Set classcode of the Problem
    *
    * @param classcode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setClasscode ( int classcode )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.classcode =  markNewValue(
	data.classcode, classcode  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Prioritycode

   /* static final RDBColumn Prioritycode for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Prioritycode = 
			    new RDBColumn( table, "prioritycode" );

   /**
    * Get prioritycode of the Problem
    *
    * @return prioritycode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPrioritycode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.prioritycode;
   }

   /**
    * Set prioritycode of the Problem
    *
    * @param prioritycode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPrioritycode ( int prioritycode )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.prioritycode =  markNewValue(
	data.prioritycode, prioritycode  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Statuscode

   /* static final RDBColumn Statuscode for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Statuscode = 
			    new RDBColumn( table, "statuscode" );

   /**
    * Get statuscode of the Problem
    *
    * @return statuscode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStatuscode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.statuscode;
   }

   /**
    * Set statuscode of the Problem
    *
    * @param statuscode of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setStatuscode ( int statuscode )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.statuscode =  markNewValue(
	data.statuscode, statuscode  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Postday

   /* static final RDBColumn Postday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Postday = 
			    new RDBColumn( table, "postday" );

   /**
    * Get postday of the Problem
    *
    * @return postday of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.postday;
   }

   /**
    * Set postday of the Problem
    *
    * @param postday of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPostday ( int postday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.postday =  markNewValue(
	data.postday, postday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Postmonth

   /* static final RDBColumn Postmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Postmonth = 
			    new RDBColumn( table, "postmonth" );

   /**
    * Get postmonth of the Problem
    *
    * @return postmonth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.postmonth;
   }

   /**
    * Set postmonth of the Problem
    *
    * @param postmonth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPostmonth ( int postmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.postmonth =  markNewValue(
	data.postmonth, postmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Postyear

   /* static final RDBColumn Postyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Postyear = 
			    new RDBColumn( table, "postyear" );

   /**
    * Get postyear of the Problem
    *
    * @return postyear of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.postyear;
   }

   /**
    * Set postyear of the Problem
    *
    * @param postyear of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPostyear ( int postyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.postyear =  markNewValue(
	data.postyear, postyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Updateday

   /* static final RDBColumn Updateday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Updateday = 
			    new RDBColumn( table, "updateday" );

   /**
    * Get updateday of the Problem
    *
    * @return updateday of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getUpdateday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.updateday;
   }

   /**
    * Set updateday of the Problem
    *
    * @param updateday of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setUpdateday ( int updateday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.updateday =  markNewValue(
	data.updateday, updateday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Updatemonth

   /* static final RDBColumn Updatemonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Updatemonth = 
			    new RDBColumn( table, "updatemonth" );

   /**
    * Get updatemonth of the Problem
    *
    * @return updatemonth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getUpdatemonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.updatemonth;
   }

   /**
    * Set updatemonth of the Problem
    *
    * @param updatemonth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setUpdatemonth ( int updatemonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.updatemonth =  markNewValue(
	data.updatemonth, updatemonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Updateyear

   /* static final RDBColumn Updateyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Updateyear = 
			    new RDBColumn( table, "updateyear" );

   /**
    * Get updateyear of the Problem
    *
    * @return updateyear of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getUpdateyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.updateyear;
   }

   /**
    * Set updateyear of the Problem
    *
    * @param updateyear of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setUpdateyear ( int updateyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.updateyear =  markNewValue(
	data.updateyear, updateyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Closeday

   /* static final RDBColumn Closeday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Closeday = 
			    new RDBColumn( table, "closeday" );

   /**
    * Get closeday of the Problem
    *
    * @return closeday of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCloseday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.closeday;
   }

   /**
    * Set closeday of the Problem
    *
    * @param closeday of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCloseday ( int closeday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.closeday =  markNewValue(
	data.closeday, closeday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Closemonth

   /* static final RDBColumn Closemonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Closemonth = 
			    new RDBColumn( table, "closemonth" );

   /**
    * Get closemonth of the Problem
    *
    * @return closemonth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getClosemonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.closemonth;
   }

   /**
    * Set closemonth of the Problem
    *
    * @param closemonth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setClosemonth ( int closemonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.closemonth =  markNewValue(
	data.closemonth, closemonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Closeyear

   /* static final RDBColumn Closeyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Closeyear = 
			    new RDBColumn( table, "closeyear" );

   /**
    * Get closeyear of the Problem
    *
    * @return closeyear of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCloseyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.closeyear;
   }

   /**
    * Set closeyear of the Problem
    *
    * @param closeyear of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCloseyear ( int closeyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.closeyear =  markNewValue(
	data.closeyear, closeyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Reportername

   /* static final RDBColumn Reportername for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Reportername = 
			    new RDBColumn( table, "reportername" );

   /**
    * Get reportername of the Problem
    *
    * @return reportername of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getReportername () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.reportername;
   }

   /**
    * Set reportername of the Problem
    *
    * @param reportername of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setReportername ( String reportername )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.reportername =  markNewValue(
	data.reportername, reportername , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Reporteremail

   /* static final RDBColumn Reporteremail for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Reporteremail = 
			    new RDBColumn( table, "reporteremail" );

   /**
    * Get reporteremail of the Problem
    *
    * @return reporteremail of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getReporteremail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.reporteremail;
   }

   /**
    * Set reporteremail of the Problem
    *
    * @param reporteremail of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setReporteremail ( String reporteremail )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.reporteremail =  markNewValue(
	data.reporteremail, reporteremail , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Describ

   /* static final RDBColumn Describ for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Describ = 
			    new RDBColumn( table, "describ" );

   /**
    * Get describ of the Problem
    *
    * @return describ of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDescrib () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.describ;
   }

   /**
    * Set describ of the Problem
    *
    * @param describ of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setDescrib ( String describ )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.describ =  markNewValue(
	data.describ, describ , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member GE_called

   /* static final RDBColumn GE_called for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn GE_called = 
			    new RDBColumn( table, "GE_called" );

   /**
    * Get GE_called of the Problem
    *
    * @return GE_called of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getGE_called () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.GE_called;
   }

   /**
    * Set GE_called of the Problem
    *
    * @param GE_called of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setGE_called ( boolean GE_called )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.GE_called =  markNewValue(
	data.GE_called, GE_called  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Problemnum

   /* static final RDBColumn Problemnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Problemnum = 
			    new RDBColumn( table, "problemnum" );

   /**
    * Get problemnum of the Problem
    *
    * @return problemnum of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProblemnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.problemnum;
   }

   /**
    * Set problemnum of the Problem
    *
    * @param problemnum of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProblemnum ( String problemnum )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.problemnum =  markNewValue(
	data.problemnum, problemnum , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Posth

   /* static final RDBColumn Posth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Posth = 
			    new RDBColumn( table, "posth" );

   /**
    * Get posth of the Problem
    *
    * @return posth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPosth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.posth;
   }

   /**
    * Set posth of the Problem
    *
    * @param posth of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPosth ( int posth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.posth =  markNewValue(
	data.posth, posth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Postm

   /* static final RDBColumn Postm for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Postm = 
			    new RDBColumn( table, "postm" );

   /**
    * Get postm of the Problem
    *
    * @return postm of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.postm;
   }

   /**
    * Set postm of the Problem
    *
    * @param postm of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPostm ( int postm )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.postm =  markNewValue(
	data.postm, postm  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Postpm

   /* static final RDBColumn Postpm for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Postpm = 
			    new RDBColumn( table, "postpm" );

   /**
    * Get postpm of the Problem
    *
    * @return postpm of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPostpm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.postpm;
   }

   /**
    * Set postpm of the Problem
    *
    * @param postpm of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPostpm ( String postpm )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.postpm =  markNewValue(
	data.postpm, postpm , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Problemdetail

   /* static final RDBColumn Problemdetail for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Problemdetail = 
			    new RDBColumn( table, "problemdetail" );

   /**
    * Get problemdetail of the Problem
    *
    * @return problemdetail of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProblemdetail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.problemdetail;
   }

   /**
    * Set problemdetail of the Problem
    *
    * @param problemdetail of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProblemdetail ( String problemdetail )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.problemdetail =  markNewValue(
	data.problemdetail, problemdetail , 0, 3000, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Fixdetail

   /* static final RDBColumn Fixdetail for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Fixdetail = 
			    new RDBColumn( table, "fixdetail" );

   /**
    * Get fixdetail of the Problem
    *
    * @return fixdetail of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFixdetail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.fixdetail;
   }

   /**
    * Set fixdetail of the Problem
    *
    * @param fixdetail of the Problem
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setFixdetail ( String fixdetail )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.fixdetail =  markNewValue(
	data.fixdetail, fixdetail , 0, 6000, false );
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
    protected ProblemDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from Problem.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new ProblemDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setOwner( 
	    webschedule.data.PersonDO.createExisting( 
		rs.getBigDecimal( 
			"owner" , 0 )
	     )
	);
	
	
	setSeveritycode( 
	    
		rs.getInt( 
			"severitycode"  )
	    
	);
	
	
	setClasscode( 
	    
		rs.getInt( 
			"classcode"  )
	    
	);
	
	
	setPrioritycode( 
	    
		rs.getInt( 
			"prioritycode"  )
	    
	);
	
	
	setStatuscode( 
	    
		rs.getInt( 
			"statuscode"  )
	    
	);
	
	
	setPostday( 
	    
		rs.getInt( 
			"postday"  )
	    
	);
	
	
	setPostmonth( 
	    
		rs.getInt( 
			"postmonth"  )
	    
	);
	
	
	setPostyear( 
	    
		rs.getInt( 
			"postyear"  )
	    
	);
	
	
	setUpdateday( 
	    
		rs.getInt( 
			"updateday"  )
	    
	);
	
	
	setUpdatemonth( 
	    
		rs.getInt( 
			"updatemonth"  )
	    
	);
	
	
	setUpdateyear( 
	    
		rs.getInt( 
			"updateyear"  )
	    
	);
	
	
	setCloseday( 
	    
		rs.getInt( 
			"closeday"  )
	    
	);
	
	
	setClosemonth( 
	    
		rs.getInt( 
			"closemonth"  )
	    
	);
	
	
	setCloseyear( 
	    
		rs.getInt( 
			"closeyear"  )
	    
	);
	
	
	setReportername( 
	    
		rs.getString( 
			"reportername"  )
	    
	);
	
	
	setReporteremail( 
	    
		rs.getString( 
			"reporteremail"  )
	    
	);
	
	
	setDescrib( 
	    
		rs.getString( 
			"describ"  )
	    
	);
	
	
	setGE_called( 
	    
		rs.getBoolean( 
			"GE_called"  )
	    
	);
	
	
	setProblemnum( 
	    
		rs.getString( 
			"problemnum"  )
	    
	);
	
	
	setPosth( 
	    
		rs.getInt( 
			"posth"  )
	    
	);
	
	
	setPostm( 
	    
		rs.getInt( 
			"postm"  )
	    
	);
	
	
	setPostpm( 
	    
		rs.getString( 
			"postpm"  )
	    
	);
	
	
	setProblemdetail( 
	    
		rs.getString( 
			"problemdetail"  )
	    
	);
	
	
	setFixdetail( 
	    
		rs.getString( 
			"fixdetail"  )
	    
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
	    "insert into Problem ( owner, severitycode, classcode, prioritycode, statuscode, postday, postmonth, postyear, updateday, updatemonth, updateyear, closeday, closemonth, closeyear, reportername, reporteremail, describ, GE_called, problemnum, posth, postm, postpm, problemdetail, fixdetail, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_int( stmt, param,
		getSeveritycode() );
	setPrepStmtParam_int( stmt, param,
		getClasscode() );
	setPrepStmtParam_int( stmt, param,
		getPrioritycode() );
	setPrepStmtParam_int( stmt, param,
		getStatuscode() );
	setPrepStmtParam_int( stmt, param,
		getPostday() );
	setPrepStmtParam_int( stmt, param,
		getPostmonth() );
	setPrepStmtParam_int( stmt, param,
		getPostyear() );
	setPrepStmtParam_int( stmt, param,
		getUpdateday() );
	setPrepStmtParam_int( stmt, param,
		getUpdatemonth() );
	setPrepStmtParam_int( stmt, param,
		getUpdateyear() );
	setPrepStmtParam_int( stmt, param,
		getCloseday() );
	setPrepStmtParam_int( stmt, param,
		getClosemonth() );
	setPrepStmtParam_int( stmt, param,
		getCloseyear() );
	setPrepStmtParam_String( stmt, param,
		getReportername() );
	setPrepStmtParam_String( stmt, param,
		getReporteremail() );
	setPrepStmtParam_String( stmt, param,
		getDescrib() );
	setPrepStmtParam_boolean( stmt, param,
		getGE_called() );
	setPrepStmtParam_String( stmt, param,
		getProblemnum() );
	setPrepStmtParam_int( stmt, param,
		getPosth() );
	setPrepStmtParam_int( stmt, param,
		getPostm() );
	setPrepStmtParam_String( stmt, param,
		getPostpm() );
	setPrepStmtParam_String( stmt, param,
		getProblemdetail() );
	setPrepStmtParam_String( stmt, param,
		getFixdetail() );


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
	    "update Problem set " + getVersionColumnName() + " = ?, owner = ?, severitycode = ?, classcode = ?, prioritycode = ?, statuscode = ?, postday = ?, postmonth = ?, postyear = ?, updateday = ?, updatemonth = ?, updateyear = ?, closeday = ?, closemonth = ?, closeyear = ?, reportername = ?, reporteremail = ?, describ = ?, GE_called = ?, problemnum = ?, posth = ?, postm = ?, postpm = ?, problemdetail = ?, fixdetail = ? " +
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
		getSeveritycode() );
	setPrepStmtParam_int( stmt, param,
		getClasscode() );
	setPrepStmtParam_int( stmt, param,
		getPrioritycode() );
	setPrepStmtParam_int( stmt, param,
		getStatuscode() );
	setPrepStmtParam_int( stmt, param,
		getPostday() );
	setPrepStmtParam_int( stmt, param,
		getPostmonth() );
	setPrepStmtParam_int( stmt, param,
		getPostyear() );
	setPrepStmtParam_int( stmt, param,
		getUpdateday() );
	setPrepStmtParam_int( stmt, param,
		getUpdatemonth() );
	setPrepStmtParam_int( stmt, param,
		getUpdateyear() );
	setPrepStmtParam_int( stmt, param,
		getCloseday() );
	setPrepStmtParam_int( stmt, param,
		getClosemonth() );
	setPrepStmtParam_int( stmt, param,
		getCloseyear() );
	setPrepStmtParam_String( stmt, param,
		getReportername() );
	setPrepStmtParam_String( stmt, param,
		getReporteremail() );
	setPrepStmtParam_String( stmt, param,
		getDescrib() );
	setPrepStmtParam_boolean( stmt, param,
		getGE_called() );
	setPrepStmtParam_String( stmt, param,
		getProblemnum() );
	setPrepStmtParam_int( stmt, param,
		getPosth() );
	setPrepStmtParam_int( stmt, param,
		getPostm() );
	setPrepStmtParam_String( stmt, param,
		getPostpm() );
	setPrepStmtParam_String( stmt, param,
		getProblemdetail() );
	setPrepStmtParam_String( stmt, param,
		getFixdetail() );


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
            "delete from Problem \n" +
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
	String str = "ProblemDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "severitycode=" + data.severitycode
+ "\n" + indent + "classcode=" + data.classcode
+ "\n" + indent + "prioritycode=" + data.prioritycode
+ "\n" + indent + "statuscode=" + data.statuscode
+ "\n" + indent + "postday=" + data.postday
+ "\n" + indent + "postmonth=" + data.postmonth
+ "\n" + indent + "postyear=" + data.postyear
+ "\n" + indent + "updateday=" + data.updateday
+ "\n" + indent + "updatemonth=" + data.updatemonth
+ "\n" + indent + "updateyear=" + data.updateyear
+ "\n" + indent + "closeday=" + data.closeday
+ "\n" + indent + "closemonth=" + data.closemonth
+ "\n" + indent + "closeyear=" + data.closeyear
+ "\n" + indent + "reportername=" + data.reportername
+ "\n" + indent + "reporteremail=" + data.reporteremail
+ "\n" + indent + "describ=" + data.describ
+ "\n" + indent + "GE_called=" + data.GE_called
+ "\n" + indent + "problemnum=" + data.problemnum
+ "\n" + indent + "posth=" + data.posth
+ "\n" + indent + "postm=" + data.postm
+ "\n" + indent + "postpm=" + data.postpm
+ "\n" + indent + "problemdetail=" + data.problemdetail
+ "\n" + indent + "fixdetail=" + data.fixdetail
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
        String str = indent + "ProblemDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "severitycode=" + data.severitycode
+ "\n" + indent + "classcode=" + data.classcode
+ "\n" + indent + "prioritycode=" + data.prioritycode
+ "\n" + indent + "statuscode=" + data.statuscode
+ "\n" + indent + "postday=" + data.postday
+ "\n" + indent + "postmonth=" + data.postmonth
+ "\n" + indent + "postyear=" + data.postyear
+ "\n" + indent + "updateday=" + data.updateday
+ "\n" + indent + "updatemonth=" + data.updatemonth
+ "\n" + indent + "updateyear=" + data.updateyear
+ "\n" + indent + "closeday=" + data.closeday
+ "\n" + indent + "closemonth=" + data.closemonth
+ "\n" + indent + "closeyear=" + data.closeyear
+ "\n" + indent + "reportername=" + data.reportername
+ "\n" + indent + "reporteremail=" + data.reporteremail
+ "\n" + indent + "describ=" + data.describ
+ "\n" + indent + "GE_called=" + data.GE_called
+ "\n" + indent + "problemnum=" + data.problemnum
+ "\n" + indent + "posth=" + data.posth
+ "\n" + indent + "postm=" + data.postm
+ "\n" + indent + "postpm=" + data.postpm
+ "\n" + indent + "problemdetail=" + data.problemdetail
+ "\n" + indent + "fixdetail=" + data.fixdetail
;
        return str + "\n" + indent + "SUPER=" + super.toString( indentCount );
        //return str;
    }

    



    /**
     * A stub method for implementing pre-commit assertions 
     * for this ProblemDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this ProblemDO.
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
		    "Cannot commit ProblemDO ( " + toString() +
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
