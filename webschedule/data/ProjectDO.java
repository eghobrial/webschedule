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
 * /home/emang/myapps/webschedule/webschedule/data/ProjectDO.java
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
 * Data core class, used to set, retrieve the ProjectDO information.
 *
 * @version $Revision: 1.8 $
 * @author  emang
 * @since   webschedule
 */
 public class ProjectDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements java.io.Serializable {

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
    static public final RDBTable  table = new RDBTable( "Project" );

    /**
     * Return Project as the name of the table in the database
     * which contains ProjectDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return the database table name
     * @see CoreDO
     * @author Jay Gunter
     */
    protected String getTableName() {
	return "Project";
    }

    static public final RDBColumn PrimaryKey = new RDBColumn( table,
					      GenericDO.getPrimaryKeyName() );
    /* RDBColumns for ProjectDO attributes are defined below. */

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
     * If the isView flag is true, ProjectDO.createExisting(ResultSet)
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
    private ProjectDataStruct data = null;

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
    protected ProjectDO ( boolean is_view )
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
    protected ProjectDO ()
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
	    
	    data = new ProjectDataStruct ();
	}

	ObjectId id = getOId();
	if ( null == id )
	    return;
	if ( ! isPersistent() )   // DO from createVirgin
	    return;

	// DO from createExisting.  Complain if no record in database.
	ProjectQuery query = new ProjectQuery ();
	query.setQueryOId( id );
	query.requireUniqueInstance();
	ProjectDO obj;
	try {
	    obj = query.getNextDO();
	    if ( null == obj )
		throw new DataObjectException(
		    "ProjectDO DO not found for id=" + id );
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
		    "Unable to load data for ProjectDO id=" + getOId() +
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
    protected ProjectDO( ObjectId id )
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
    public static ProjectDO createVirgin()
    throws DatabaseManagerException, ObjectIdException {
	return new ProjectDO ();
    }

    /**
     * createExisting( BigDecimal )
     *
     * Factory method creates a ProjectDO object by searching for it
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
    public static ProjectDO createExisting( BigDecimal bd )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == bd )
	    return null;
	return createExisting( new ObjectId( bd ) );
    }

    /**
     * The createExisting method is used to create a <CODE>ProjectDO</CODE>
     * from a string handle.
     */
    public static ProjectDO createExisting( String handle ) {
	ProjectDO ret = null;
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
     * Factory method creates a ProjectDO object by searching for it
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
    protected static ProjectDO createExisting( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException
    {
	if ( null == id )
	    return null;
	ProjectDO ret = null;
	ret = new ProjectDO( id );
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
    protected static ProjectDO createExisting( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == rs )
	    return null;
	ProjectDO ret = null;
	if ( isView ) {
	    ret = new ProjectDO ();
	    ret.initFromResultSet( rs );
	} else {
	    ret = new ProjectDO ( rs );
	}
	return ret;
    }

    /**
     * createExisting( RDBRow )
     *
     * Factory method creates a ProjectDO object by searching for it
     * in the database using the ProjectDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param RDBRow A row returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a ProjectDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ProjectDO createExisting( RDBRow row )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == row )
	    return null;
        RDBColumnValue pk = null;
        try {
	    pk = row.get( ProjectDO.PrimaryKey );
	    return createExisting( pk );
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create ProjectDO, row does not " +
		"contain ProjectDO primary key." );
        }
    }

    /**
     * createExisting( RDBColumnValue )
     *
     * Factory method creates a ProjectDO object by searching for it
     * in the database using the passed ProjectDO.PrimaryKey.
     *
     * @param RDBColumnValue a PrimaryKey column value from a row
     * that was returned by QueryBuilder.getNextRow().
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a ProjectDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static ProjectDO createExisting( RDBColumnValue pk )
    throws SQLException, ObjectIdException, DataObjectException
		, DatabaseManagerException
    {
	if ( null == pk )
	    return null;
	if ( ! pk.equals( ProjectDO.PrimaryKey ) )
	    throw new DataObjectException(
		"Cannot create ProjectDO, " +
		"RDBColumnValue is not ProjectDO.PrimaryKey." );
	BigDecimal bd = null;
        try {
	    bd = pk.getBigDecimal();
        } catch ( Exception e ) {
	    throw new DataObjectException(
		"Cannot create ProjectDO, bad primary key." );
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
    public static ProjectDO createCopy( ProjectDataStruct data )
    throws DatabaseManagerException, ObjectIdException {
	ProjectDO ret = new ProjectDO ();
	ret.data = ( ProjectDataStruct ) data.duplicate();
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
    public static ProjectDO createCopy( ProjectDO orig )
    throws DatabaseManagerException, ObjectIdException {
	ProjectDO ret = new ProjectDO ();
	if ( null != orig.data ) {
	    ret.data = ( ProjectDataStruct ) orig.data.duplicate();
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
    protected void makeIdentical( ProjectDO orig ) {
	super.makeIdentical(orig);
	data = orig.data;
    }

////////////////////////// data member Proj_name

   /* static final RDBColumn Proj_name for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Proj_name = 
			    new RDBColumn( table, "proj_name" );

   /**
    * Get proj_name of the Project
    *
    * @return proj_name of the Project
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
    * Set proj_name of the Project
    *
    * @param proj_name of the Project
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
	data.proj_name, proj_name , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Description

   /* static final RDBColumn Description for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Description = 
			    new RDBColumn( table, "Description" );

   /**
    * Get Description of the Project
    *
    * @return Description of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDescription () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Description;
   }

   /**
    * Set Description of the Project
    *
    * @param Description of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setDescription ( String Description )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Description =  markNewValue(
	data.Description, Description , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Indexnum

   /* static final RDBColumn Indexnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Indexnum = 
			    new RDBColumn( table, "indexnum" );

   /**
    * Get indexnum of the Project
    *
    * @return indexnum of the Project
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
    * Set indexnum of the Project
    *
    * @param indexnum of the Project
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
	data.indexnum, indexnum , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Codeofpay

   /* static final RDBColumn Codeofpay for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Codeofpay = 
			    new RDBColumn( table, "codeofpay" );

   /**
    * Get codeofpay of the Project
    *
    * @return codeofpay of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCodeofpay () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.codeofpay;
   }

   /**
    * Set codeofpay of the Project
    *
    * @param codeofpay of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCodeofpay ( int codeofpay )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.codeofpay =  markNewValue(
	data.codeofpay, codeofpay  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Password

   /* static final RDBColumn Password for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Password = 
			    new RDBColumn( table, "password" );

   /**
    * Get password of the Project
    *
    * @return password of the Project
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
    * Set password of the Project
    *
    * @param password of the Project
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
   


////////////////////////// data member Totalhours

   /* static final RDBColumn Totalhours for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Totalhours = 
			    new RDBColumn( table, "totalhours" );

   /**
    * Get totalhours of the Project
    *
    * @return totalhours of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public double getTotalhours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.totalhours;
   }

   /**
    * Set totalhours of the Project
    *
    * @param totalhours of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setTotalhours ( double totalhours )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.totalhours =  markNewValue(
	data.totalhours, totalhours  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Donehours

   /* static final RDBColumn Donehours for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Donehours = 
			    new RDBColumn( table, "donehours" );

   /**
    * Get donehours of the Project
    *
    * @return donehours of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public double getDonehours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.donehours;
   }

   /**
    * Set donehours of the Project
    *
    * @param donehours of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setDonehours ( double donehours )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.donehours =  markNewValue(
	data.donehours, donehours  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Owner

   /* static final RDBColumn Owner for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Owner = 
			    new RDBColumn( table, "owner" );

   /**
    * Get owner of the Project
    *
    * @return owner of the Project
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
    * Set owner of the Project
    *
    * @param owner of the Project
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
   


////////////////////////// data member Contactname

   /* static final RDBColumn Contactname for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Contactname = 
			    new RDBColumn( table, "contactname" );

   /**
    * Get contactname of the Project
    *
    * @return contactname of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getContactname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.contactname;
   }

   /**
    * Set contactname of the Project
    *
    * @param contactname of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setContactname ( String contactname )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.contactname =  markNewValue(
	data.contactname, contactname , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Contactphone

   /* static final RDBColumn Contactphone for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Contactphone = 
			    new RDBColumn( table, "contactphone" );

   /**
    * Get contactphone of the Project
    *
    * @return contactphone of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getContactphone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.contactphone;
   }

   /**
    * Set contactphone of the Project
    *
    * @param contactphone of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setContactphone ( String contactphone )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.contactphone =  markNewValue(
	data.contactphone, contactphone , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Billaddr1

   /* static final RDBColumn Billaddr1 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Billaddr1 = 
			    new RDBColumn( table, "billaddr1" );

   /**
    * Get billaddr1 of the Project
    *
    * @return billaddr1 of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBilladdr1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.billaddr1;
   }

   /**
    * Set billaddr1 of the Project
    *
    * @param billaddr1 of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBilladdr1 ( String billaddr1 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.billaddr1 =  markNewValue(
	data.billaddr1, billaddr1 , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Billaddr2

   /* static final RDBColumn Billaddr2 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Billaddr2 = 
			    new RDBColumn( table, "billaddr2" );

   /**
    * Get billaddr2 of the Project
    *
    * @return billaddr2 of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBilladdr2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.billaddr2;
   }

   /**
    * Set billaddr2 of the Project
    *
    * @param billaddr2 of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBilladdr2 ( String billaddr2 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.billaddr2 =  markNewValue(
	data.billaddr2, billaddr2 , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Billaddr3

   /* static final RDBColumn Billaddr3 for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Billaddr3 = 
			    new RDBColumn( table, "billaddr3" );

   /**
    * Get billaddr3 of the Project
    *
    * @return billaddr3 of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBilladdr3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.billaddr3;
   }

   /**
    * Set billaddr3 of the Project
    *
    * @param billaddr3 of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setBilladdr3 ( String billaddr3 )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.billaddr3 =  markNewValue(
	data.billaddr3, billaddr3 , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member City

   /* static final RDBColumn City for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn City = 
			    new RDBColumn( table, "city" );

   /**
    * Get city of the Project
    *
    * @return city of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCity () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.city;
   }

   /**
    * Set city of the Project
    *
    * @param city of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCity ( String city )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.city =  markNewValue(
	data.city, city , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member State

   /* static final RDBColumn State for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn State = 
			    new RDBColumn( table, "state" );

   /**
    * Get state of the Project
    *
    * @return state of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getState () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.state;
   }

   /**
    * Set state of the Project
    *
    * @param state of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setState ( String state )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.state =  markNewValue(
	data.state, state , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Zip

   /* static final RDBColumn Zip for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Zip = 
			    new RDBColumn( table, "zip" );

   /**
    * Get zip of the Project
    *
    * @return zip of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getZip () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.zip;
   }

   /**
    * Set zip of the Project
    *
    * @param zip of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setZip ( String zip )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.zip =  markNewValue(
	data.zip, zip , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Accountid

   /* static final RDBColumn Accountid for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Accountid = 
			    new RDBColumn( table, "accountid" );

   /**
    * Get accountid of the Project
    *
    * @return accountid of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAccountid () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.accountid;
   }

   /**
    * Set accountid of the Project
    *
    * @param accountid of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setAccountid ( String accountid )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.accountid =  markNewValue(
	data.accountid, accountid , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Isoutside

   /* static final RDBColumn Isoutside for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Isoutside = 
			    new RDBColumn( table, "isoutside" );

   /**
    * Get isoutside of the Project
    *
    * @return isoutside of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsoutside () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.isoutside;
   }

   /**
    * Set isoutside of the Project
    *
    * @param isoutside of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setIsoutside ( boolean isoutside )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.isoutside =  markNewValue(
	data.isoutside, isoutside  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Expday

   /* static final RDBColumn Expday for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Expday = 
			    new RDBColumn( table, "expday" );

   /**
    * Get expday of the Project
    *
    * @return expday of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getExpday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.expday;
   }

   /**
    * Set expday of the Project
    *
    * @param expday of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setExpday ( int expday )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.expday =  markNewValue(
	data.expday, expday  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Expmonth

   /* static final RDBColumn Expmonth for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Expmonth = 
			    new RDBColumn( table, "expmonth" );

   /**
    * Get expmonth of the Project
    *
    * @return expmonth of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getExpmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.expmonth;
   }

   /**
    * Set expmonth of the Project
    *
    * @param expmonth of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setExpmonth ( int expmonth )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.expmonth =  markNewValue(
	data.expmonth, expmonth  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Expyear

   /* static final RDBColumn Expyear for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Expyear = 
			    new RDBColumn( table, "expyear" );

   /**
    * Get expyear of the Project
    *
    * @return expyear of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getExpyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.expyear;
   }

   /**
    * Set expyear of the Project
    *
    * @param expyear of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setExpyear ( int expyear )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.expyear =  markNewValue(
	data.expyear, expyear  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Notifycontact

   /* static final RDBColumn Notifycontact for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Notifycontact = 
			    new RDBColumn( table, "notifycontact" );

   /**
    * Get notifycontact of the Project
    *
    * @return notifycontact of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getNotifycontact () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.notifycontact;
   }

   /**
    * Set notifycontact of the Project
    *
    * @param notifycontact of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setNotifycontact ( String notifycontact )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.notifycontact =  markNewValue(
	data.notifycontact, notifycontact , 0, 150, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IsExp

   /* static final RDBColumn IsExp for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IsExp = 
			    new RDBColumn( table, "isExp" );

   /**
    * Get isExp of the Project
    *
    * @return isExp of the Project
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
    * Set isExp of the Project
    *
    * @param isExp of the Project
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
   


////////////////////////// data member CanCredit

   /* static final RDBColumn CanCredit for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn CanCredit = 
			    new RDBColumn( table, "canCredit" );

   /**
    * Get canCredit of the Project
    *
    * @return canCredit of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCanCredit () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.canCredit;
   }

   /**
    * Set canCredit of the Project
    *
    * @param canCredit of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setCanCredit ( int canCredit )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.canCredit =  markNewValue(
	data.canCredit, canCredit  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Institute

   /* static final RDBColumn Institute for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Institute = 
			    new RDBColumn( table, "Institute" );

   /**
    * Get Institute of the Project
    *
    * @return Institute of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getInstitute () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Institute;
   }

   /**
    * Set Institute of the Project
    *
    * @param Institute of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setInstitute ( String Institute )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Institute =  markNewValue(
	data.Institute, Institute , 0, 32, false );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member ProposalID

   /* static final RDBColumn ProposalID for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn ProposalID = 
			    new RDBColumn( table, "proposalID" );

   /**
    * Get proposalID of the Project
    *
    * @return proposalID of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalID () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.proposalID;
   }

   /**
    * Set proposalID of the Project
    *
    * @param proposalID of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setProposalID ( String proposalID )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.proposalID =  markNewValue(
	data.proposalID, proposalID , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Fpemail

   /* static final RDBColumn Fpemail for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Fpemail = 
			    new RDBColumn( table, "fpemail" );

   /**
    * Get fpemail of the Project
    *
    * @return fpemail of the Project
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
    * Set fpemail of the Project
    *
    * @param fpemail of the Project
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
   


////////////////////////// data member POnum

   /* static final RDBColumn POnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn POnum = 
			    new RDBColumn( table, "POnum" );

   /**
    * Get POnum of the Project
    *
    * @return POnum of the Project
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
    * Set POnum of the Project
    *
    * @param POnum of the Project
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
   


////////////////////////// data member POexpdate

   /* static final RDBColumn POexpdate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn POexpdate = 
			    new RDBColumn( table, "POexpdate" );

   /**
    * Get POexpdate of the Project
    *
    * @return POexpdate of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getPOexpdate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.POexpdate;
   }

   /**
    * Set POexpdate of the Project
    *
    * @param POexpdate of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPOexpdate ( java.sql.Date POexpdate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.POexpdate =  markNewValue(
	data.POexpdate, POexpdate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member POhours

   /* static final RDBColumn POhours for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn POhours = 
			    new RDBColumn( table, "POhours" );

   /**
    * Get POhours of the Project
    *
    * @return POhours of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public double getPOhours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.POhours;
   }

   /**
    * Set POhours of the Project
    *
    * @param POhours of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setPOhours ( double POhours )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.POhours =  markNewValue(
	data.POhours, POhours  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IACUCDate

   /* static final RDBColumn IACUCDate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IACUCDate = 
			    new RDBColumn( table, "IACUCDate" );

   /**
    * Get IACUCDate of the Project
    *
    * @return IACUCDate of the Project
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
    * Set IACUCDate of the Project
    *
    * @param IACUCDate of the Project
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
   


////////////////////////// data member Modifiedby

   /* static final RDBColumn Modifiedby for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Modifiedby = 
			    new RDBColumn( table, "Modifiedby" );

   /**
    * Get Modifiedby of the Project
    *
    * @return Modifiedby of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getModifiedby () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Modifiedby;
   }

   /**
    * Set Modifiedby of the Project
    *
    * @param Modifiedby of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setModifiedby ( String Modifiedby )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Modifiedby =  markNewValue(
	data.Modifiedby, Modifiedby , 0, 32, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member ModDate

   /* static final RDBColumn ModDate for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn ModDate = 
			    new RDBColumn( table, "ModDate" );

   /**
    * Get ModDate of the Project
    *
    * @return ModDate of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getModDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.ModDate;
   }

   /**
    * Set ModDate of the Project
    *
    * @param ModDate of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setModDate ( java.sql.Date ModDate )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.ModDate =  markNewValue(
	data.ModDate, ModDate  );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member Notes

   /* static final RDBColumn Notes for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn Notes = 
			    new RDBColumn( table, "Notes" );

   /**
    * Get Notes of the Project
    *
    * @return Notes of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getNotes () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      checkLoad();
      return data.Notes;
   }

   /**
    * Set Notes of the Project
    *
    * @param Notes of the Project
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   
   public void setNotes ( String Notes )
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      checkLoad();
      data.Notes =  markNewValue(
	data.Notes, Notes , 0, 3000, true );
      afterAnySet();	// business actions/assertions after data assignment
   }
   


////////////////////////// data member IRBnum

   /* static final RDBColumn IRBnum for use with QueryBuilder.
    * See RDBColumn PrimaryKey at the top of this file for usage example.
    */
   static public final RDBColumn IRBnum = 
			    new RDBColumn( table, "IRBnum" );

   /**
    * Get IRBnum of the Project
    *
    * @return IRBnum of the Project
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
    * Set IRBnum of the Project
    *
    * @param IRBnum of the Project
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
    protected ProjectDO(ResultSet rs)
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
        super(rs);
	initFromResultSet( rs );
    }

    /**
     * initFromResultSet initializes the data members from Project.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     */
    private void initFromResultSet( ResultSet rs )
            throws SQLException, ObjectIdException, DataObjectException
 	    , DatabaseManagerException
    {
	// Constructing a DO from a ResultSet means we definitely need the 
	// DataStruct ready for the setXxx methods invoked below.
	data = new ProjectDataStruct ();
 
	// writeMemberStuff uses the ResultSetExtraction.template
	// to build up the value for this tag:
	// the value is a series of calls to the DO set methods.
		
	setProj_name( 
	    
		rs.getString( 
			"proj_name"  )
	    
	);
	
	
	setDescription( 
	    
		rs.getString( 
			"Description"  )
	    
	);
	
	
	setIndexnum( 
	    
		rs.getString( 
			"indexnum"  )
	    
	);
	
	
	setCodeofpay( 
	    
		rs.getInt( 
			"codeofpay"  )
	    
	);
	
	
	setPassword( 
	    
		rs.getString( 
			"password"  )
	    
	);
	
	
	setTotalhours( 
	    
		rs.getDouble( 
			"totalhours"  )
	    
	);
	
	
	setDonehours( 
	    
		rs.getDouble( 
			"donehours"  )
	    
	);
	
	
	setOwner( 
	    webschedule.data.PersonDO.createExisting( 
		rs.getBigDecimal( 
			"owner" , 0 )
	     )
	);
	
	
	setContactname( 
	    
		rs.getString( 
			"contactname"  )
	    
	);
	
	
	setContactphone( 
	    
		rs.getString( 
			"contactphone"  )
	    
	);
	
	
	setBilladdr1( 
	    
		rs.getString( 
			"billaddr1"  )
	    
	);
	
	
	setBilladdr2( 
	    
		rs.getString( 
			"billaddr2"  )
	    
	);
	
	
	setBilladdr3( 
	    
		rs.getString( 
			"billaddr3"  )
	    
	);
	
	
	setCity( 
	    
		rs.getString( 
			"city"  )
	    
	);
	
	
	setState( 
	    
		rs.getString( 
			"state"  )
	    
	);
	
	
	setZip( 
	    
		rs.getString( 
			"zip"  )
	    
	);
	
	
	setAccountid( 
	    
		rs.getString( 
			"accountid"  )
	    
	);
	
	
	setIsoutside( 
	    
		rs.getBoolean( 
			"isoutside"  )
	    
	);
	
	
	setExpday( 
	    
		rs.getInt( 
			"expday"  )
	    
	);
	
	
	setExpmonth( 
	    
		rs.getInt( 
			"expmonth"  )
	    
	);
	
	
	setExpyear( 
	    
		rs.getInt( 
			"expyear"  )
	    
	);
	
	
	setNotifycontact( 
	    
		rs.getString( 
			"notifycontact"  )
	    
	);
	
	
	setIsExp( 
	    
		rs.getBoolean( 
			"isExp"  )
	    
	);
	
	
	setCanCredit( 
	    
		rs.getInt( 
			"canCredit"  )
	    
	);
	
	
	setInstitute( 
	    
		rs.getString( 
			"Institute"  )
	    
	);
	
	
	setProposalID( 
	    
		rs.getString( 
			"proposalID"  )
	    
	);
	
	
	setFpemail( 
	    
		rs.getString( 
			"fpemail"  )
	    
	);
	
	
	setPOnum( 
	    
		rs.getString( 
			"POnum"  )
	    
	);
	
	
	setPOexpdate( 
	    
		rs.getDate( 
			"POexpdate"  )
	    
	);
	
	
	setPOhours( 
	    
		rs.getDouble( 
			"POhours"  )
	    
	);
	
	
	setIACUCDate( 
	    
		rs.getDate( 
			"IACUCDate"  )
	    
	);
	
	
	setModifiedby( 
	    
		rs.getString( 
			"Modifiedby"  )
	    
	);
	
	
	setModDate( 
	    
		rs.getDate( 
			"ModDate"  )
	    
	);
	
	
	setNotes( 
	    
		rs.getString( 
			"Notes"  )
	    
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
	    "insert into Project ( proj_name, Description, indexnum, codeofpay, password, totalhours, donehours, owner, contactname, contactphone, billaddr1, billaddr2, billaddr3, city, state, zip, accountid, isoutside, expday, expmonth, expyear, notifycontact, isExp, canCredit, Institute, proposalID, fpemail, POnum, POexpdate, POhours, IACUCDate, Modifiedby, ModDate, Notes, IRBnum, " + getOIdColumnName() + ", " + getVersionColumnName() + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

	param = new int[1]; param[0] = 1;
	// writeMemberStuff uses the JDBCsetCalls.template
	// to build up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined in GenericDO.
	try {
	    	setPrepStmtParam_String( stmt, param,
		getProj_name() );
	setPrepStmtParam_String( stmt, param,
		getDescription() );
	setPrepStmtParam_String( stmt, param,
		getIndexnum() );
	setPrepStmtParam_int( stmt, param,
		getCodeofpay() );
	setPrepStmtParam_String( stmt, param,
		getPassword() );
	setPrepStmtParam_double( stmt, param,
		getTotalhours() );
	setPrepStmtParam_double( stmt, param,
		getDonehours() );
	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_String( stmt, param,
		getContactname() );
	setPrepStmtParam_String( stmt, param,
		getContactphone() );
	setPrepStmtParam_String( stmt, param,
		getBilladdr1() );
	setPrepStmtParam_String( stmt, param,
		getBilladdr2() );
	setPrepStmtParam_String( stmt, param,
		getBilladdr3() );
	setPrepStmtParam_String( stmt, param,
		getCity() );
	setPrepStmtParam_String( stmt, param,
		getState() );
	setPrepStmtParam_String( stmt, param,
		getZip() );
	setPrepStmtParam_String( stmt, param,
		getAccountid() );
	setPrepStmtParam_boolean( stmt, param,
		getIsoutside() );
	setPrepStmtParam_int( stmt, param,
		getExpday() );
	setPrepStmtParam_int( stmt, param,
		getExpmonth() );
	setPrepStmtParam_int( stmt, param,
		getExpyear() );
	setPrepStmtParam_String( stmt, param,
		getNotifycontact() );
	setPrepStmtParam_boolean( stmt, param,
		getIsExp() );
	setPrepStmtParam_int( stmt, param,
		getCanCredit() );
	setPrepStmtParam_String( stmt, param,
		getInstitute() );
	setPrepStmtParam_String( stmt, param,
		getProposalID() );
	setPrepStmtParam_String( stmt, param,
		getFpemail() );
	setPrepStmtParam_String( stmt, param,
		getPOnum() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getPOexpdate() );
	setPrepStmtParam_double( stmt, param,
		getPOhours() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getIACUCDate() );
	setPrepStmtParam_String( stmt, param,
		getModifiedby() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getModDate() );
	setPrepStmtParam_String( stmt, param,
		getNotes() );
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
	    "update Project set " + getVersionColumnName() + " = ?, proj_name = ?, Description = ?, indexnum = ?, codeofpay = ?, password = ?, totalhours = ?, donehours = ?, owner = ?, contactname = ?, contactphone = ?, billaddr1 = ?, billaddr2 = ?, billaddr3 = ?, city = ?, state = ?, zip = ?, accountid = ?, isoutside = ?, expday = ?, expmonth = ?, expyear = ?, notifycontact = ?, isExp = ?, canCredit = ?, Institute = ?, proposalID = ?, fpemail = ?, POnum = ?, POexpdate = ?, POhours = ?, IACUCDate = ?, Modifiedby = ?, ModDate = ?, Notes = ?, IRBnum = ? " +
	    "where " + getOIdColumnName() + " = ? and " + getVersionColumnName() + " = ?" );

	param = new int[1]; param[0] = 1;
	//int[] param = {1};
	// writeMemberStuff builds up the value for this tag:
	// the value is a series of calls to setPrepStmtParam_TYPE methods.
	// Those methods are defined below.
	try {
	    setPrepStmtParam_int( stmt, param, getNewVersion() );
	    	setPrepStmtParam_String( stmt, param,
		getProj_name() );
	setPrepStmtParam_String( stmt, param,
		getDescription() );
	setPrepStmtParam_String( stmt, param,
		getIndexnum() );
	setPrepStmtParam_int( stmt, param,
		getCodeofpay() );
	setPrepStmtParam_String( stmt, param,
		getPassword() );
	setPrepStmtParam_double( stmt, param,
		getTotalhours() );
	setPrepStmtParam_double( stmt, param,
		getDonehours() );
	setPrepStmtParam_DO( stmt, param,
		getOwner() );
	setPrepStmtParam_String( stmt, param,
		getContactname() );
	setPrepStmtParam_String( stmt, param,
		getContactphone() );
	setPrepStmtParam_String( stmt, param,
		getBilladdr1() );
	setPrepStmtParam_String( stmt, param,
		getBilladdr2() );
	setPrepStmtParam_String( stmt, param,
		getBilladdr3() );
	setPrepStmtParam_String( stmt, param,
		getCity() );
	setPrepStmtParam_String( stmt, param,
		getState() );
	setPrepStmtParam_String( stmt, param,
		getZip() );
	setPrepStmtParam_String( stmt, param,
		getAccountid() );
	setPrepStmtParam_boolean( stmt, param,
		getIsoutside() );
	setPrepStmtParam_int( stmt, param,
		getExpday() );
	setPrepStmtParam_int( stmt, param,
		getExpmonth() );
	setPrepStmtParam_int( stmt, param,
		getExpyear() );
	setPrepStmtParam_String( stmt, param,
		getNotifycontact() );
	setPrepStmtParam_boolean( stmt, param,
		getIsExp() );
	setPrepStmtParam_int( stmt, param,
		getCanCredit() );
	setPrepStmtParam_String( stmt, param,
		getInstitute() );
	setPrepStmtParam_String( stmt, param,
		getProposalID() );
	setPrepStmtParam_String( stmt, param,
		getFpemail() );
	setPrepStmtParam_String( stmt, param,
		getPOnum() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getPOexpdate() );
	setPrepStmtParam_double( stmt, param,
		getPOhours() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getIACUCDate() );
	setPrepStmtParam_String( stmt, param,
		getModifiedby() );
	setPrepStmtParam_java_sql_Date( stmt, param,
		getModDate() );
	setPrepStmtParam_String( stmt, param,
		getNotes() );
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
            "delete from Project \n" +
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
	String str = "ProjectDO:";
	ObjectId oid = getOId();
	String id = "virgin";
	if ( null != oid ) 
	    id = oid.toString();
	str += " OID=" + id;
	if ( null != data ) 
	    str = str + "\n" + indent + "proj_name=" + data.proj_name
+ "\n" + indent + "Description=" + data.Description
+ "\n" + indent + "indexnum=" + data.indexnum
+ "\n" + indent + "codeofpay=" + data.codeofpay
+ "\n" + indent + "password=" + data.password
+ "\n" + indent + "totalhours=" + data.totalhours
+ "\n" + indent + "donehours=" + data.donehours
+ "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "contactname=" + data.contactname
+ "\n" + indent + "contactphone=" + data.contactphone
+ "\n" + indent + "billaddr1=" + data.billaddr1
+ "\n" + indent + "billaddr2=" + data.billaddr2
+ "\n" + indent + "billaddr3=" + data.billaddr3
+ "\n" + indent + "city=" + data.city
+ "\n" + indent + "state=" + data.state
+ "\n" + indent + "zip=" + data.zip
+ "\n" + indent + "accountid=" + data.accountid
+ "\n" + indent + "isoutside=" + data.isoutside
+ "\n" + indent + "expday=" + data.expday
+ "\n" + indent + "expmonth=" + data.expmonth
+ "\n" + indent + "expyear=" + data.expyear
+ "\n" + indent + "notifycontact=" + data.notifycontact
+ "\n" + indent + "isExp=" + data.isExp
+ "\n" + indent + "canCredit=" + data.canCredit
+ "\n" + indent + "Institute=" + data.Institute
+ "\n" + indent + "proposalID=" + data.proposalID
+ "\n" + indent + "fpemail=" + data.fpemail
+ "\n" + indent + "POnum=" + data.POnum
+ "\n" + indent + "POexpdate=" + data.POexpdate
+ "\n" + indent + "POhours=" + data.POhours
+ "\n" + indent + "IACUCDate=" + data.IACUCDate
+ "\n" + indent + "Modifiedby=" + data.Modifiedby
+ "\n" + indent + "ModDate=" + data.ModDate
+ "\n" + indent + "Notes=" + data.Notes
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
        String str = indent + "ProjectDO:";
        ObjectId oid = getOId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        str += " OID=" + id;
        if ( null != data )
            str = str + "\n" + indent + "proj_name=" + data.proj_name
+ "\n" + indent + "Description=" + data.Description
+ "\n" + indent + "indexnum=" + data.indexnum
+ "\n" + indent + "codeofpay=" + data.codeofpay
+ "\n" + indent + "password=" + data.password
+ "\n" + indent + "totalhours=" + data.totalhours
+ "\n" + indent + "donehours=" + data.donehours
+ "\n" + indent + "owner=" + ( null == data.owner ? null  : data.owner.toString( indentCount + 1 ) )
+ "\n" + indent + "contactname=" + data.contactname
+ "\n" + indent + "contactphone=" + data.contactphone
+ "\n" + indent + "billaddr1=" + data.billaddr1
+ "\n" + indent + "billaddr2=" + data.billaddr2
+ "\n" + indent + "billaddr3=" + data.billaddr3
+ "\n" + indent + "city=" + data.city
+ "\n" + indent + "state=" + data.state
+ "\n" + indent + "zip=" + data.zip
+ "\n" + indent + "accountid=" + data.accountid
+ "\n" + indent + "isoutside=" + data.isoutside
+ "\n" + indent + "expday=" + data.expday
+ "\n" + indent + "expmonth=" + data.expmonth
+ "\n" + indent + "expyear=" + data.expyear
+ "\n" + indent + "notifycontact=" + data.notifycontact
+ "\n" + indent + "isExp=" + data.isExp
+ "\n" + indent + "canCredit=" + data.canCredit
+ "\n" + indent + "Institute=" + data.Institute
+ "\n" + indent + "proposalID=" + data.proposalID
+ "\n" + indent + "fpemail=" + data.fpemail
+ "\n" + indent + "POnum=" + data.POnum
+ "\n" + indent + "POexpdate=" + data.POexpdate
+ "\n" + indent + "POhours=" + data.POhours
+ "\n" + indent + "IACUCDate=" + data.IACUCDate
+ "\n" + indent + "Modifiedby=" + data.Modifiedby
+ "\n" + indent + "ModDate=" + data.ModDate
+ "\n" + indent + "Notes=" + data.Notes
+ "\n" + indent + "IRBnum=" + data.IRBnum
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
	    q.setQueryProj_owner( this );
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
	q.setQueryProj_owner( this );
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
        referrer.setProj_owner( this );
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
	ProjectDO referred = referrer.getProj_owner();
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
	    q.setQueryProject( this );
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
	q.setQueryProject( this );
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
        referrer.setProject( this );
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
	ProjectDO referred = referrer.getProject();
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
	    q.setQueryProj_owner( this );
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
	q.setQueryProj_owner( this );
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
        referrer.setProj_owner( this );
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
	ProjectDO referred = referrer.getProj_owner();
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
	    q.setQueryProj_owner( this );
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
	q.setQueryProj_owner( this );
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
        referrer.setProj_owner( this );
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
	ProjectDO referred = referrer.getProj_owner();
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
     * get array of OperatorDO objects that indirectly refer
     * to this DO.
     *
     * @return array of OperatorDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.OperatorDO[] getOperatorDOArray_via_Operates () 
    throws DataObjectException {
	webschedule.data.OperatorDO[] ret = null;
	try {
	    webschedule.data.OperatesDO[] arr = getOperatesDOArray();
	    ret = new webschedule.data.OperatorDO[ arr.length ];
	    for ( int i = 0; i < arr.length; i++ ) {
		ret[ i ] = arr[ i ].getOperator();
	    }
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: ", e );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.OperatorDO[ 0 ];
	}
	return ret;
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a OperatorDO object that indirectly refers
     * to this DO.
     *
     * @param d The OperatorDO to add to the OperatesDO mapping
     * for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapOperator_via_OperatesDO( webschedule.data.OperatorDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	mapOperator_via_OperatesDO( d, null );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a OperatorDO object that indirectly refers to this DO.
     *
     * @param b The OperatorDO to add to the OperatesDO mapping for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapOperator_via_OperatesDO( webschedule.data.OperatorDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesDO m = null;
	try {
	    m = webschedule.data.OperatesDO.createVirgin();
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"webschedule.data.OperatesDO.createVirgin failed", e );
	}
	m.setOperator( d );
	m.setProject( this );
	m.commit( tran );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the OperatorDO object that indirectly refers
     * to this DO.
     *
     * @param d The OperatorDO to remove from the OperatesDO mapping
     * for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public void unmapOperator_via_OperatesDO( webschedule.data.OperatorDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	unmapOperator_via_OperatesDO( d, null );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the OperatorDO object that indirectly refers
     * to this DO.
     *
     * @param b The OperatorDO to remove from the OperatesDO mapping
     * for this DO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception QueryException
     *   If an error occured while building the query before execution.
     */
    public void unmapOperator_via_OperatesDO( webschedule.data.OperatorDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryProject( this );
	q.setQueryOperator( d );
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
     * for this ProjectDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     */
    protected void okToCommit() 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this ProjectDO.
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
	  	
	{
	    // perform cascading delete on referring table
	    webschedule.data.C_eventDO[] a = getC_eventDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.OperatesDO[] a = getOperatesDOArray();
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
		    "Cannot commit ProjectDO ( " + toString() +
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
