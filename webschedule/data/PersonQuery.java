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
 * /home/emang/myapps/webschedule/webschedule/data/PersonQuery.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import com.lutris.dods.builder.generator.query.*;

import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.appserver.server.session.*;

import java.sql.*;
import java.util.*;
import java.util.Date;  // when I say Date, I don't mean java.sql.Date

/**
 * PersonQuery is used to query the Person table in the database.<BR>
 * It returns objects of type PersonDO.
 * <P>
 * General usage:
 * <P>
 *     In DODS:
 *        Create a Data Object named "Dog",
 *        and create an Attribute named "Name",
 *        and set that Attribute to "Can be queried."
 *        DODS will then generate the method DogQuery.setQueryName().
 * <P>
 *     In your Business Layer, prepare the query:<BR>
 * <P><PRE>
 *             DogQuery dq = new DogQuery();
 *             dq.setQueryName("Rex")
 *             if ( Rex is a reserved name )
 *                 dq.requireUniqueInstance();
 * </PRE>
 *     Then, get the query results one of two ways:
 * <P>
 *         #1:<PRE>
 *             String names = "";
 *             DogBDO[] dogs = dq.getBDOArray();
 *             for ( int i = 0; i < dogs.length; i++ ) {
 *                 names += dogs[i].getName() + " ";
 *             }
 * </PRE>
 *      or #2:<PRE>
 *             String names = "";
 *             DogBDO dog;
 *             while ( null != ( dog = dq.getNextBDO() ) ) {
 *                 names += dog.getName() + " ";
 *             }
 * </PRE>
 *     Note:   Methods <CODE>getDOArray()</CODE> and <CODE>getNextDO()</CODE>
 *             do exist, but they are not generally used
 *             in the Business or Presentation layers of an application.
 *             <B>All access to the Data Layer (DO classes) should occur via
 *             the Business Layer (BDO classes).
 *             Only the Business Layer (BDO classes and classes extending them)
 *             should need to manipulate the Data Layer (DO classes).</B>
 *             See also the comments in the BDO constructors.
 * <P>
 *     Note:   If <CODE>requireUniqueInstance()</CODE> was called,
 *             then <CODE>getBDOArray()</CODE> or <CODE>getNextBDO()</CODE>
 *             will throw an exception if more than one "Rex" was found.
 * <P>
 *     Note:   Results of the query will come from the Data Object cache if:
 *             -  The cache is available.
 *             -  Matches were found in the cache.
 *             -  No other tables (Data Objects of other types) were involved
 *                in the query.
 *                This can happen if you extend the <CODE>DogQuery</CODE> class
 *                and you make calls to the <CODE>QueryBuilder</CODE> object
 *                to add SQL involving other tables.
 *             If any of these conditions is not true,
 *             then any results from the query will come from the database.
 * <P>
 *     To reuse the query object, call:
 * <P><PRE>
 *             dq.reset();
 * </PRE>
 * @author emang
 * @version $Revision: 1.5.2.2 $
 */
final public class PersonQuery implements Query {

    private QueryBuilder builder;

    /**
     * Public constructor.
     */
    public PersonQuery() {
	builder = new QueryBuilder( "Person", "Person.*" );
	builder.setDatabaseVendor( "PostgreSQL" );
	builder.setStringMatchDetails( "LIKE", "%" );
	reset();
    }

    private ResultSet		resultSet	= null;
    private boolean 		uniqueInstance	= false;
    private PersonDO[]	DOs		= null;
    private int			arrayIndex	= -1;
    private boolean		needToRun	= true;
    private Vector		cacheHits	= null;

    private boolean partialCache = false;
    private boolean hitDb = true;
    public void hitDatabase() { hitDb = true; }

    /**
     * Perform the query on the database, and prepare the
     * array of returned DO objects.
     *
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    private void runQuery()
    throws DataObjectException, NonUniqueQueryException {
	needToRun = false;
	arrayIndex = -1;

	DBQuery dbQuery = null;
	try {
	    Vector results;
	    /*
	    if ( cacheHits.size() > 0 ) {
		// The setQuery methods build up the cacheHits.
		// If the cache had our desired objects,
		// we don't query the database, so we have no ResultSet.
		results = cacheHits;	 // executeQuery saw cache hits
	    } else {
		// If the cache doesn't exist, or could not handle the query,
		// then we actually query the database.
		dbQuery = Enhydra.getDatabaseManager().createQuery();
		dbQuery.query( this );    // invokes executeQuery
	        results = new Vector();
	        while ( resultSet.next() ) {
		    results.addElement( new PersonDO ( resultSet ) );
	        }
	    }
	    */
	    if ( /* partialCache && */ 0 == cacheHits.size() )
		hitDb = true;
	    if ( hitDb ) {
		dbQuery = Enhydra.getDatabaseManager().createQuery();
		dbQuery.query( this );    // invokes executeQuery
	        results = new Vector();
	        while ( resultSet.next() ) {
//		    results.addElement( new PersonDO ( resultSet ) );
		    results.addElement( PersonDO.createExisting ( resultSet ) );
	        }
	    } else {
		results = cacheHits;	 // executeQuery saw cache hits
	    }

	    if ( results.size() > 1 && uniqueInstance )
		throw new NonUniqueQueryException(
		    "Too many rows returned from database" );
	    DOs = new PersonDO [ results.size() ];
	    for ( int i = 0; i < results.size(); i++ ) {
		DOs[i] = ( PersonDO ) results.elementAt( i );
	    }
	    arrayIndex = 0;
	} catch ( SQLException se ) {
	    if (null == se.getSQLState() ) {
		throw new DataObjectException(
		    "Unknown SQLException", se );
	    }
	    if (	se.getSQLState().startsWith("02") &&
			se.getErrorCode() == 100 ) {
		throw new DataObjectException(
		    "Update or delete DO is out of synch", se );
	    } else if (	se.getSQLState().equals("S1000") &&
			se.getErrorCode() == -268 ) {
		throw new DataObjectException(
		    "Integrity constraint violation", se );
	    } else {
		throw new DataObjectException( "Data Object Error", se );
	    }
	} catch ( ObjectIdException oe ) {
	    throw new DataObjectException( "Object ID Error", oe );
	} catch ( DatabaseManagerException de ) {
	    throw new DataObjectException( "Database connection Error", de );
	} finally {
	    if ( null != dbQuery )
		dbQuery.release();
	}
    }


    /**
     * Return array of DOs constructed from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public PersonDO[] getDOArray()
    throws DataObjectException, NonUniqueQueryException {
	if ( needToRun )
	    runQuery();
	return DOs;
    }

    /**
     * Return successive DOs from array built from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public PersonDO getNextDO()
    throws DataObjectException, NonUniqueQueryException {
	if ( needToRun )
	    runQuery();
	if ( null == DOs ) {
	    /* This should never happen.
	     * runQuery() should either throw an exception
	     * or create an array of DOs, possibly of zero length.
	     */
	    return null;
	}
	if ( arrayIndex < DOs.length )
	    return DOs[ arrayIndex++ ];
	return null;
    }

    /**
     * Return array of BDOs constructed from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public PersonBDO[] getBDOArray()
    throws DataObjectException, NonUniqueQueryException {
	if ( needToRun )
	    runQuery();
	PersonBDO[] BDOs = new PersonBDO[ DOs.length ];
	for ( int i = 0; i < DOs.length; i++ )
	    BDOs[ i ] = PersonBDO.createExisting( DOs[ i ] );
	return BDOs;
    }

    /**
     * Return successive BDOs from array built from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public PersonBDO getNextBDO()
    throws DataObjectException, NonUniqueQueryException {
	PersonDO DO = getNextDO();
	if ( null == DO )
	    return null;
	return PersonBDO.createExisting( DO );
    }


    /**
     * Set the OID to query.
     * WARNING!  This method assumes that table <CODE>Person</CODE>
     * has a column named <CODE>"oid"</CODE>.
     * This method is called from the DO classes to retrieve an object by id.
     *
     * @param oid The object id to query.
     */
    public void setQueryOId(ObjectId oid) {
        // Remove from cacheHits any DOs that do not meet this
        // setQuery requirement.
	if ( null == oid )
	    return;
	requireUniqueInstance();
        for ( int i = 0; i < cacheHits.size(); i++ ) {
            PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
            if ( null == DO ) continue;
            boolean equals = true;
	    ObjectId id = DO.getOId();
	    if ( null == id || ! id.equals( oid ) )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database
        // in case there is no cache, or the query involves other tables.
	builder.addWhereClause( "ObjectId",  oid.toBigDecimal(),
						QueryBuilder.NOT_NULL );
    }

    /**
     * Set the object handle to query.
     * This is a variant of setQueryOId().
     *
     * @param handle The string version of the id to query.
     */
    public void setQueryHandle(String handle)
    throws ObjectIdException {
        ObjectId oid = new ObjectId( handle );
        setQueryOId( oid );
    }

    /**
     * Set "unique instance" assertion bit.
     * The first call to the next() method will throw an exception
     * if more than one object was found.
     */
    public void requireUniqueInstance()
    {
	uniqueInstance = true;
    }

    /**
     * Reset the query parameters.
     */
    public void reset() {
	cacheHits	= new Vector();
	DOs		= null;
	uniqueInstance	= false;
	needToRun	= true;
        builder.reset();
    }

    /**
     * Return the appropriate QueryBuilder flag for selecting
     * exact matches (SQL '=') or inexact matches (SQL 'matches').
     */
    private boolean exactFlag( boolean exact ) {
        return exact ? QueryBuilder.EXACT_MATCH : QueryBuilder.NOT_EXACT;
    }



    //
    // Implementation of Query interface
    //

    /**
     * Method to query objects from the database.
     * The following call in runQuery()
     *	    dbQuery.query( this );
     * causes the dbQuery object to invoke
     *      executeQuery()
     *
     * @param conn Handle to database connection.
     * @exception java.sql.SQLException If a database access error occurs.
     */
    public ResultSet executeQuery(DBConnection conn)
        throws SQLException
    {
	resultSet = builder.executeQuery( conn );
        return resultSet;
    }


    /**
     * WARNING!  This method is disabled.
     * It's implementation is forced by the Query interface.
     * This method is disabled for 2 reasons:
     * 1)  the getDOArray() and getNextDO() methods are better
     *     because they return DOs instead of JDBC objects.
     * 2)  the createExisting() method throws an exception
     *     that we cannot reasonably handle here,
     *     and that we cannot throw from here.
     *
     * @param rs JDBC result set from which the next object
     *   will be instantiated.
     * @exception java.sql.SQLException
     *   If a database access error occurs.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an invalid object id was queried from the database.
     */
    public Object next(ResultSet rs)
    throws SQLException, ObjectIdException {
	/*
        if (!rs.next())
            return null;

        return PersonDO.createExisting(rs);
	*/
	throw new ObjectIdException(
	    "next() should not be used.  Use getNextDO() instead." );
	//return null;
    }


    /**
     * Set the login to query.
     *
     * @param x The login of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryLogin(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getLogin();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "login", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the login to query
     * @param x The login of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryLogin( 
				String x )
    throws DataObjectException, QueryException {
	setQueryLogin( x, true );
    }

    /**
     * Add login to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByLogin(boolean direction_flag) {
        builder.addOrderByColumn("login",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add login to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByLogin() {
        builder.addOrderByColumn("login","ASC");
    }


    /**
     * Set the password to query.
     *
     * @param x The password of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPassword(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getPassword();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "password", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the password to query
     * @param x The password of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPassword( 
				String x )
    throws DataObjectException, QueryException {
	setQueryPassword( x, true );
    }

    /**
     * Add password to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPassword(boolean direction_flag) {
        builder.addOrderByColumn("password",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add password to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPassword() {
        builder.addOrderByColumn("password","ASC");
    }


    /**
     * Set the lastname to query.
     *
     * @param x The lastname of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryLastname(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getLastname();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "lastname", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the lastname to query
     * @param x The lastname of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryLastname( 
				String x )
    throws DataObjectException, QueryException {
	setQueryLastname( x, true );
    }

    /**
     * Add lastname to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByLastname(boolean direction_flag) {
        builder.addOrderByColumn("lastname",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add lastname to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByLastname() {
        builder.addOrderByColumn("lastname","ASC");
    }


    /**
     * Set the firstname to query.
     *
     * @param x The firstname of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryFirstname(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getFirstname();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "firstname", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the firstname to query
     * @param x The firstname of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryFirstname( 
				String x )
    throws DataObjectException, QueryException {
	setQueryFirstname( x, true );
    }

    /**
     * Add firstname to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByFirstname(boolean direction_flag) {
        builder.addOrderByColumn("firstname",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add firstname to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByFirstname() {
        builder.addOrderByColumn("firstname","ASC");
    }


    /**
     * Set the office to query.
     *
     * @param x The office of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryOffice(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getOffice();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "office", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the office to query
     * @param x The office of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryOffice( 
				String x )
    throws DataObjectException, QueryException {
	setQueryOffice( x, true );
    }

    /**
     * Add office to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByOffice(boolean direction_flag) {
        builder.addOrderByColumn("office",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add office to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByOffice() {
        builder.addOrderByColumn("office","ASC");
    }


    /**
     * Set the phone to query.
     *
     * @param x The phone of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPhone(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getPhone();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "phone", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the phone to query
     * @param x The phone of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPhone( 
				String x )
    throws DataObjectException, QueryException {
	setQueryPhone( x, true );
    }

    /**
     * Add phone to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPhone(boolean direction_flag) {
        builder.addOrderByColumn("phone",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add phone to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPhone() {
        builder.addOrderByColumn("phone","ASC");
    }


    /**
     * Set the isAdmin to query.
     *
     * @param x The isAdmin of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsAdmin(
				boolean x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getIsAdmin() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "isAdmin", x, "BOOL",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the isAdmin to query
     * @param x The isAdmin of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsAdmin( 
				boolean x )
    throws DataObjectException, QueryException {
	setQueryIsAdmin( x, true );
    }

    /**
     * Add isAdmin to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIsAdmin(boolean direction_flag) {
        builder.addOrderByColumn("isAdmin",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add isAdmin to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIsAdmin() {
        builder.addOrderByColumn("isAdmin","ASC");
    }


    /**
     * Set the isDeveloper to query.
     *
     * @param x The isDeveloper of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsDeveloper(
				boolean x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getIsDeveloper() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "isDeveloper", x, "BOOL",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the isDeveloper to query
     * @param x The isDeveloper of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsDeveloper( 
				boolean x )
    throws DataObjectException, QueryException {
	setQueryIsDeveloper( x, true );
    }

    /**
     * Add isDeveloper to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIsDeveloper(boolean direction_flag) {
        builder.addOrderByColumn("isDeveloper",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add isDeveloper to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIsDeveloper() {
        builder.addOrderByColumn("isDeveloper","ASC");
    }


    /**
     * Set the email to query.
     *
     * @param x The email of the Person to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEmail(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    PersonDO DO = ( PersonDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getEmail();
		if ( null == s && null == x ) {
		    equals = true;
		} else if ( null != s && null != x ) {
		    if ( exact ) 
			equals = s.equals( x );
		    else {
			equals = ( -1 != s.toLowerCase().indexOf(
					 x.toLowerCase() ) );
		    }
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "email", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the email to query
     * @param x The email of the Person to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEmail( 
				String x )
    throws DataObjectException, QueryException {
	setQueryEmail( x, true );
    }

    /**
     * Add email to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEmail(boolean direction_flag) {
        builder.addOrderByColumn("email",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add email to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEmail() {
        builder.addOrderByColumn("email","ASC");
    }

    /**
    * Returns the <code>QueryBuilder</code> that this <code>PersonQuery</code>
    * uses to construct and execute database queries.
    * <code>PersonQuery.setQueryXXX</code> methods use 
    * the <code>QueryBuilder</code> to
    * append SQL expressions to the <code>"WHERE"</code> clause to be executed.
    * The <code>QueryBuilder.addEndClause method.</code> can be used to
    * append freeform SQL expressions to the <code>WHERE</code> clause,
    * e.g. "ORDER BY name".
    *
    * <b>Notes regarding cache-enabled DO classes:</b>
    * DO classes can be cache-enabled.  
    * If when using a <code>PersonQuery</code>, the application developer
    * <b>does not call</b> <code>getQueryBuilder</code>,
    * then <code>PersonQuery.setQueryXXX</code> methods 
    * simply prune the DO cache and return the remaining results.
    * However, a <code>QueryBuilder</code> builds
    * <CODE>SELECT</CODE> statements for execution by the actual database,
    * and never searches the built-in cache for the DO.
    * So, if the DO class is cache-enabled, and <code>getQueryBuilder</code>
    * is called, this <CODE>PersonQuery</CODE> object ignores the cache 
    * and hits the actual database.
    */
    public QueryBuilder getQueryBuilder() {
	hitDatabase();
	return builder;
    }

    /**
     * Insert an <CODE>OR</CODE> between <CODE>WHERE</CODE> clauses.
     * Example:  find all the persons named Bob or Robert:
     * <CODE>
     *    PersonQuery pq = new PersonQuery();
     *    pq.setQueryFirstName( "Bob" );
     *    pq.or();
     *    pq.setQueryFirstName( "Robert" );
     * </CODE>
     * 
     * Note:  Calls to <CODE>setQueryXxx</CODE> methods
     * are implicitly <CODE>AND</CODE>ed together,
     * so the following example will always return nothing:
     * <CODE>
     *    PersonQuery pq = new PersonQuery();
     *    pq.setQueryFirstName( "Bob" );
     *    // AND automatically inserted here.
     *    pq.setQueryFirstName( "Robert" );
     * </CODE>
     * 
     * @see QueryBuilder to construct more elaborate queries.
     * @author Jay Gunter
     */
    public void or() {
	builder.addWhereOr();
    }

    /**
     * Place an open parenthesis in the <CODE>WHERE</CODE> clause.
     * Example usage:  find all the Bobs and Roberts who are 5 or 50 years old:
     * <CODE>
     *    PersonQuery pq = new PersonQuery();
     *    pq.openParen();
     *       pq.setQueryFirstName( "Bob" );
     *       pq.or();
     *       pq.setQueryFirstName( "Robert" );
     *    pq.closeParen();
     *    // AND automatically inserted here.
     *    pq.openParen();
     *       pq.setQueryAge( 5 );
     *       pq.or();
     *       pq.setQueryAge( 50 );
     *    pq.closeParen();
     * </CODE>
     * 
     * @see QueryBuilder to construct more elaborate queries.
     * @author Jay Gunter
     */
    public void openParen() {
	builder.addWhereOr();
    }

    /**
     * Place a closing parenthesis in the <CODE>WHERE</CODE> clause.
     * 
     * @see openParen
     * @author Jay Gunter
     */
    public void closeParen() {
	builder.addWhereOr();
    }
}
