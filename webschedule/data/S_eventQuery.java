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
 * /home/emang/myapps/webschedule/webschedule/data/S_eventQuery.java
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
 * S_eventQuery is used to query the S_event table in the database.<BR>
 * It returns objects of type S_eventDO.
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
final public class S_eventQuery implements Query {

    private QueryBuilder builder;

    /**
     * Public constructor.
     */
    public S_eventQuery() {
	builder = new QueryBuilder( "S_event", "S_event.*" );
	builder.setDatabaseVendor( "PostgreSQL" );
	builder.setStringMatchDetails( "LIKE", "%" );
	reset();
    }

    private ResultSet		resultSet	= null;
    private boolean 		uniqueInstance	= false;
    private S_eventDO[]	DOs		= null;
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
		    results.addElement( new S_eventDO ( resultSet ) );
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
//		    results.addElement( new S_eventDO ( resultSet ) );
		    results.addElement( S_eventDO.createExisting ( resultSet ) );
	        }
	    } else {
		results = cacheHits;	 // executeQuery saw cache hits
	    }

	    if ( results.size() > 1 && uniqueInstance )
		throw new NonUniqueQueryException(
		    "Too many rows returned from database" );
	    DOs = new S_eventDO [ results.size() ];
	    for ( int i = 0; i < results.size(); i++ ) {
		DOs[i] = ( S_eventDO ) results.elementAt( i );
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
    public S_eventDO[] getDOArray()
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
    public S_eventDO getNextDO()
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
    public S_eventBDO[] getBDOArray()
    throws DataObjectException, NonUniqueQueryException {
	if ( needToRun )
	    runQuery();
	S_eventBDO[] BDOs = new S_eventBDO[ DOs.length ];
	for ( int i = 0; i < DOs.length; i++ )
	    BDOs[ i ] = S_eventBDO.createExisting( DOs[ i ] );
	return BDOs;
    }

    /**
     * Return successive BDOs from array built from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public S_eventBDO getNextBDO()
    throws DataObjectException, NonUniqueQueryException {
	S_eventDO DO = getNextDO();
	if ( null == DO )
	    return null;
	return S_eventBDO.createExisting( DO );
    }


    /**
     * Set the OID to query.
     * WARNING!  This method assumes that table <CODE>S_event</CODE>
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
            S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
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

        return S_eventDO.createExisting(rs);
	*/
	throw new ObjectIdException(
	    "next() should not be used.  Use getNextDO() instead." );
	//return null;
    }


    /**
     * Set the description to query.
     *
     * @param x The description of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryDescription(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getDescription();
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
	    builder.addWhereClause( "description", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the description to query
     * @param x The description of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryDescription( 
				String x )
    throws DataObjectException, QueryException {
	setQueryDescription( x, true );
    }

    /**
     * Add description to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByDescription(boolean direction_flag) {
        builder.addOrderByColumn("description",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add description to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByDescription() {
        builder.addOrderByColumn("description","ASC");
    }


    /**
     * Set the starth to query.
     *
     * @param x The starth of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryStarth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getStarth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "starth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the starth to query
     * @param x The starth of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryStarth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryStarth( x, true );
    }

    /**
     * Add starth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByStarth(boolean direction_flag) {
        builder.addOrderByColumn("starth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add starth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByStarth() {
        builder.addOrderByColumn("starth","ASC");
    }


    /**
     * Set the startm to query.
     *
     * @param x The startm of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryStartm(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getStartm() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "startm", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the startm to query
     * @param x The startm of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryStartm( 
				int x )
    throws DataObjectException, QueryException {
	setQueryStartm( x, true );
    }

    /**
     * Add startm to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByStartm(boolean direction_flag) {
        builder.addOrderByColumn("startm",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add startm to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByStartm() {
        builder.addOrderByColumn("startm","ASC");
    }


    /**
     * Set the endh to query.
     *
     * @param x The endh of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEndh(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getEndh() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "endh", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the endh to query
     * @param x The endh of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEndh( 
				int x )
    throws DataObjectException, QueryException {
	setQueryEndh( x, true );
    }

    /**
     * Add endh to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEndh(boolean direction_flag) {
        builder.addOrderByColumn("endh",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add endh to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEndh() {
        builder.addOrderByColumn("endh","ASC");
    }


    /**
     * Set the endm to query.
     *
     * @param x The endm of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEndm(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getEndm() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "endm", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the endm to query
     * @param x The endm of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEndm( 
				int x )
    throws DataObjectException, QueryException {
	setQueryEndm( x, true );
    }

    /**
     * Add endm to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEndm(boolean direction_flag) {
        builder.addOrderByColumn("endm",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add endm to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEndm() {
        builder.addOrderByColumn("endm","ASC");
    }


    /**
     * Set the eventday to query.
     *
     * @param x The eventday of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventday(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getEventday() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "eventday", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the eventday to query
     * @param x The eventday of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventday( 
				int x )
    throws DataObjectException, QueryException {
	setQueryEventday( x, true );
    }

    /**
     * Add eventday to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEventday(boolean direction_flag) {
        builder.addOrderByColumn("eventday",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add eventday to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEventday() {
        builder.addOrderByColumn("eventday","ASC");
    }


    /**
     * Set the eventmonth to query.
     *
     * @param x The eventmonth of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventmonth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getEventmonth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "eventmonth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the eventmonth to query
     * @param x The eventmonth of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventmonth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryEventmonth( x, true );
    }

    /**
     * Add eventmonth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEventmonth(boolean direction_flag) {
        builder.addOrderByColumn("eventmonth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add eventmonth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEventmonth() {
        builder.addOrderByColumn("eventmonth","ASC");
    }


    /**
     * Set the eventyear to query.
     *
     * @param x The eventyear of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventyear(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getEventyear() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "eventyear", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the eventyear to query
     * @param x The eventyear of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventyear( 
				int x )
    throws DataObjectException, QueryException {
	setQueryEventyear( x, true );
    }

    /**
     * Add eventyear to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEventyear(boolean direction_flag) {
        builder.addOrderByColumn("eventyear",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add eventyear to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEventyear() {
        builder.addOrderByColumn("eventyear","ASC");
    }


    /**
     * Set the eventdayofw to query.
     *
     * @param x The eventdayofw of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventdayofw(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getEventdayofw() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "eventdayofw", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the eventdayofw to query
     * @param x The eventdayofw of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryEventdayofw( 
				int x )
    throws DataObjectException, QueryException {
	setQueryEventdayofw( x, true );
    }

    /**
     * Add eventdayofw to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByEventdayofw(boolean direction_flag) {
        builder.addOrderByColumn("eventdayofw",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add eventdayofw to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByEventdayofw() {
        builder.addOrderByColumn("eventdayofw","ASC");
    }


    /**
     * Set the owner to query.
     *
     * @param x The owner of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryOwner(
				webschedule.data.PersonDO x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// DOs are compared by their handles..
		webschedule.data.PersonDO m = DO.getOwner();
		if ( null == m && null == x ) {
		    equals = true;
		} else if ( null == m || null == x ) {
		    equals = false;
		} else {
		    equals = ( DO.getOwner().getOId().toString().equals( x.getOId().toString() ) );
if ( equals && m != x ) {
System.err.println("\n----------------------------------------------------------");
System.err.println("m ="+m );
System.err.println("x ="+x );
}
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "owner", x, "INT8",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the owner to query
     * @param x The owner of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryOwner( 
				webschedule.data.PersonDO x )
    throws DataObjectException, QueryException {
	setQueryOwner( x, true );
    }

    /**
     * Add owner to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByOwner(boolean direction_flag) {
        builder.addOrderByColumn("owner",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add owner to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByOwner() {
        builder.addOrderByColumn("owner","ASC");
    }


    /**
     * Set the proj_owner to query.
     *
     * @param x The proj_owner of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryProj_owner(
				webschedule.data.ProjectDO x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// DOs are compared by their handles..
		webschedule.data.ProjectDO m = DO.getProj_owner();
		if ( null == m && null == x ) {
		    equals = true;
		} else if ( null == m || null == x ) {
		    equals = false;
		} else {
		    equals = ( DO.getProj_owner().getOId().toString().equals( x.getOId().toString() ) );
if ( equals && m != x ) {
System.err.println("\n----------------------------------------------------------");
System.err.println("m ="+m );
System.err.println("x ="+x );
}
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "proj_owner", x, "INT8",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the proj_owner to query
     * @param x The proj_owner of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryProj_owner( 
				webschedule.data.ProjectDO x )
    throws DataObjectException, QueryException {
	setQueryProj_owner( x, true );
    }

    /**
     * Add proj_owner to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByProj_owner(boolean direction_flag) {
        builder.addOrderByColumn("proj_owner",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add proj_owner to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByProj_owner() {
        builder.addOrderByColumn("proj_owner","ASC");
    }


    /**
     * Set the todayday to query.
     *
     * @param x The todayday of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTodayday(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getTodayday() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "todayday", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the todayday to query
     * @param x The todayday of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTodayday( 
				int x )
    throws DataObjectException, QueryException {
	setQueryTodayday( x, true );
    }

    /**
     * Add todayday to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByTodayday(boolean direction_flag) {
        builder.addOrderByColumn("todayday",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add todayday to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByTodayday() {
        builder.addOrderByColumn("todayday","ASC");
    }


    /**
     * Set the todaymonth to query.
     *
     * @param x The todaymonth of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTodaymonth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getTodaymonth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "todaymonth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the todaymonth to query
     * @param x The todaymonth of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTodaymonth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryTodaymonth( x, true );
    }

    /**
     * Add todaymonth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByTodaymonth(boolean direction_flag) {
        builder.addOrderByColumn("todaymonth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add todaymonth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByTodaymonth() {
        builder.addOrderByColumn("todaymonth","ASC");
    }


    /**
     * Set the todayyear to query.
     *
     * @param x The todayyear of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTodayyear(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getTodayyear() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "todayyear", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the todayyear to query
     * @param x The todayyear of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTodayyear( 
				int x )
    throws DataObjectException, QueryException {
	setQueryTodayyear( x, true );
    }

    /**
     * Add todayyear to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByTodayyear(boolean direction_flag) {
        builder.addOrderByColumn("todayyear",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add todayyear to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByTodayyear() {
        builder.addOrderByColumn("todayyear","ASC");
    }


    /**
     * Set the isDevelop to query.
     *
     * @param x The isDevelop of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsDevelop(
				boolean x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getIsDevelop() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "isDevelop", x, "BOOL",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the isDevelop to query
     * @param x The isDevelop of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsDevelop( 
				boolean x )
    throws DataObjectException, QueryException {
	setQueryIsDevelop( x, true );
    }

    /**
     * Add isDevelop to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIsDevelop(boolean direction_flag) {
        builder.addOrderByColumn("isDevelop",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add isDevelop to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIsDevelop() {
        builder.addOrderByColumn("isDevelop","ASC");
    }


    /**
     * Set the isRepeat to query.
     *
     * @param x The isRepeat of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsRepeat(
				boolean x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getIsRepeat() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "isRepeat", x, "BOOL",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the isRepeat to query
     * @param x The isRepeat of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsRepeat( 
				boolean x )
    throws DataObjectException, QueryException {
	setQueryIsRepeat( x, true );
    }

    /**
     * Add isRepeat to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIsRepeat(boolean direction_flag) {
        builder.addOrderByColumn("isRepeat",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add isRepeat to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIsRepeat() {
        builder.addOrderByColumn("isRepeat","ASC");
    }


    /**
     * Set the Operator to query.
     *
     * @param x The Operator of the S_event to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryOperator(
				webschedule.data.OperatorDO x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    S_eventDO DO = ( S_eventDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// DOs are compared by their handles..
		webschedule.data.OperatorDO m = DO.getOperator();
		if ( null == m && null == x ) {
		    equals = true;
		} else if ( null == m || null == x ) {
		    equals = false;
		} else {
		    equals = ( DO.getOperator().getOId().toString().equals( x.getOId().toString() ) );
if ( equals && m != x ) {
System.err.println("\n----------------------------------------------------------");
System.err.println("m ="+m );
System.err.println("x ="+x );
}
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "Operator", x, "INT8",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the Operator to query
     * @param x The Operator of the S_event to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryOperator( 
				webschedule.data.OperatorDO x )
    throws DataObjectException, QueryException {
	setQueryOperator( x, true );
    }

    /**
     * Add Operator to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByOperator(boolean direction_flag) {
        builder.addOrderByColumn("Operator",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add Operator to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByOperator() {
        builder.addOrderByColumn("Operator","ASC");
    }

    /**
    * Returns the <code>QueryBuilder</code> that this <code>S_eventQuery</code>
    * uses to construct and execute database queries.
    * <code>S_eventQuery.setQueryXXX</code> methods use 
    * the <code>QueryBuilder</code> to
    * append SQL expressions to the <code>"WHERE"</code> clause to be executed.
    * The <code>QueryBuilder.addEndClause method.</code> can be used to
    * append freeform SQL expressions to the <code>WHERE</code> clause,
    * e.g. "ORDER BY name".
    *
    * <b>Notes regarding cache-enabled DO classes:</b>
    * DO classes can be cache-enabled.  
    * If when using a <code>S_eventQuery</code>, the application developer
    * <b>does not call</b> <code>getQueryBuilder</code>,
    * then <code>S_eventQuery.setQueryXXX</code> methods 
    * simply prune the DO cache and return the remaining results.
    * However, a <code>QueryBuilder</code> builds
    * <CODE>SELECT</CODE> statements for execution by the actual database,
    * and never searches the built-in cache for the DO.
    * So, if the DO class is cache-enabled, and <code>getQueryBuilder</code>
    * is called, this <CODE>S_eventQuery</CODE> object ignores the cache 
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
