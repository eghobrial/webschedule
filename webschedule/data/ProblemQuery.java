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
 * /home/emang/myapps/webschedule/webschedule/data/ProblemQuery.java
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
 * ProblemQuery is used to query the Problem table in the database.<BR>
 * It returns objects of type ProblemDO.
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
final public class ProblemQuery implements Query {

    private QueryBuilder builder;

    /**
     * Public constructor.
     */
    public ProblemQuery() {
	builder = new QueryBuilder( "Problem", "Problem.*" );
	builder.setDatabaseVendor( "PostgreSQL" );
	builder.setStringMatchDetails( "LIKE", "%" );
	reset();
    }

    private ResultSet		resultSet	= null;
    private boolean 		uniqueInstance	= false;
    private ProblemDO[]	DOs		= null;
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
		    results.addElement( new ProblemDO ( resultSet ) );
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
//		    results.addElement( new ProblemDO ( resultSet ) );
		    results.addElement( ProblemDO.createExisting ( resultSet ) );
	        }
	    } else {
		results = cacheHits;	 // executeQuery saw cache hits
	    }

	    if ( results.size() > 1 && uniqueInstance )
		throw new NonUniqueQueryException(
		    "Too many rows returned from database" );
	    DOs = new ProblemDO [ results.size() ];
	    for ( int i = 0; i < results.size(); i++ ) {
		DOs[i] = ( ProblemDO ) results.elementAt( i );
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
    public ProblemDO[] getDOArray()
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
    public ProblemDO getNextDO()
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
    public ProblemBDO[] getBDOArray()
    throws DataObjectException, NonUniqueQueryException {
	if ( needToRun )
	    runQuery();
	ProblemBDO[] BDOs = new ProblemBDO[ DOs.length ];
	for ( int i = 0; i < DOs.length; i++ )
	    BDOs[ i ] = ProblemBDO.createExisting( DOs[ i ] );
	return BDOs;
    }

    /**
     * Return successive BDOs from array built from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public ProblemBDO getNextBDO()
    throws DataObjectException, NonUniqueQueryException {
	ProblemDO DO = getNextDO();
	if ( null == DO )
	    return null;
	return ProblemBDO.createExisting( DO );
    }


    /**
     * Set the OID to query.
     * WARNING!  This method assumes that table <CODE>Problem</CODE>
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
            ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
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

        return ProblemDO.createExisting(rs);
	*/
	throw new ObjectIdException(
	    "next() should not be used.  Use getNextDO() instead." );
	//return null;
    }


    /**
     * Set the owner to query.
     *
     * @param x The owner of the Problem to query.
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
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
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
     * @param x The owner of the Problem to query.
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
     * Set the severitycode to query.
     *
     * @param x The severitycode of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQuerySeveritycode(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getSeveritycode() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "severitycode", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the severitycode to query
     * @param x The severitycode of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQuerySeveritycode( 
				int x )
    throws DataObjectException, QueryException {
	setQuerySeveritycode( x, true );
    }

    /**
     * Add severitycode to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderBySeveritycode(boolean direction_flag) {
        builder.addOrderByColumn("severitycode",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add severitycode to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderBySeveritycode() {
        builder.addOrderByColumn("severitycode","ASC");
    }


    /**
     * Set the classcode to query.
     *
     * @param x The classcode of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryClasscode(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getClasscode() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "classcode", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the classcode to query
     * @param x The classcode of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryClasscode( 
				int x )
    throws DataObjectException, QueryException {
	setQueryClasscode( x, true );
    }

    /**
     * Add classcode to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByClasscode(boolean direction_flag) {
        builder.addOrderByColumn("classcode",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add classcode to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByClasscode() {
        builder.addOrderByColumn("classcode","ASC");
    }


    /**
     * Set the prioritycode to query.
     *
     * @param x The prioritycode of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPrioritycode(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getPrioritycode() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "prioritycode", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the prioritycode to query
     * @param x The prioritycode of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPrioritycode( 
				int x )
    throws DataObjectException, QueryException {
	setQueryPrioritycode( x, true );
    }

    /**
     * Add prioritycode to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPrioritycode(boolean direction_flag) {
        builder.addOrderByColumn("prioritycode",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add prioritycode to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPrioritycode() {
        builder.addOrderByColumn("prioritycode","ASC");
    }


    /**
     * Set the statuscode to query.
     *
     * @param x The statuscode of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryStatuscode(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getStatuscode() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "statuscode", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the statuscode to query
     * @param x The statuscode of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryStatuscode( 
				int x )
    throws DataObjectException, QueryException {
	setQueryStatuscode( x, true );
    }

    /**
     * Add statuscode to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByStatuscode(boolean direction_flag) {
        builder.addOrderByColumn("statuscode",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add statuscode to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByStatuscode() {
        builder.addOrderByColumn("statuscode","ASC");
    }


    /**
     * Set the postday to query.
     *
     * @param x The postday of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPostday(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getPostday() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "postday", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the postday to query
     * @param x The postday of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPostday( 
				int x )
    throws DataObjectException, QueryException {
	setQueryPostday( x, true );
    }

    /**
     * Add postday to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPostday(boolean direction_flag) {
        builder.addOrderByColumn("postday",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add postday to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPostday() {
        //builder.addOrderByColumn("postday","ASC");
	builder.addOrderByColumn("postday","DESC");
    }


    /**
     * Set the postmonth to query.
     *
     * @param x The postmonth of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPostmonth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getPostmonth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "postmonth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the postmonth to query
     * @param x The postmonth of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPostmonth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryPostmonth( x, true );
    }

    /**
     * Add postmonth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPostmonth(boolean direction_flag) {
        builder.addOrderByColumn("postmonth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add postmonth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPostmonth() {
        //builder.addOrderByColumn("postmonth","ASC");
    builder.addOrderByColumn("postmonth","DESC");
    }


    /**
     * Set the postyear to query.
     *
     * @param x The postyear of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPostyear(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getPostyear() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "postyear", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the postyear to query
     * @param x The postyear of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPostyear( 
				int x )
    throws DataObjectException, QueryException {
	setQueryPostyear( x, true );
    }

    /**
     * Add postyear to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPostyear(boolean direction_flag) {
        builder.addOrderByColumn("postyear",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add postyear to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPostyear() {
        //builder.addOrderByColumn("postyear","ASC");
    builder.addOrderByColumn("postyear","DESC");
    }


    /**
     * Set the updateday to query.
     *
     * @param x The updateday of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryUpdateday(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getUpdateday() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "updateday", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the updateday to query
     * @param x The updateday of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryUpdateday( 
				int x )
    throws DataObjectException, QueryException {
	setQueryUpdateday( x, true );
    }

    /**
     * Add updateday to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByUpdateday(boolean direction_flag) {
        builder.addOrderByColumn("updateday",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add updateday to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByUpdateday() {
        builder.addOrderByColumn("updateday","ASC");
    }


    /**
     * Set the updatemonth to query.
     *
     * @param x The updatemonth of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryUpdatemonth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getUpdatemonth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "updatemonth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the updatemonth to query
     * @param x The updatemonth of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryUpdatemonth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryUpdatemonth( x, true );
    }

    /**
     * Add updatemonth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByUpdatemonth(boolean direction_flag) {
        builder.addOrderByColumn("updatemonth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add updatemonth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByUpdatemonth() {
        builder.addOrderByColumn("updatemonth","ASC");
    }


    /**
     * Set the updateyear to query.
     *
     * @param x The updateyear of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryUpdateyear(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getUpdateyear() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "updateyear", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the updateyear to query
     * @param x The updateyear of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryUpdateyear( 
				int x )
    throws DataObjectException, QueryException {
	setQueryUpdateyear( x, true );
    }

    /**
     * Add updateyear to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByUpdateyear(boolean direction_flag) {
        builder.addOrderByColumn("updateyear",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add updateyear to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByUpdateyear() {
        builder.addOrderByColumn("updateyear","ASC");
    }


    /**
     * Set the closeday to query.
     *
     * @param x The closeday of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCloseday(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getCloseday() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "closeday", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the closeday to query
     * @param x The closeday of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCloseday( 
				int x )
    throws DataObjectException, QueryException {
	setQueryCloseday( x, true );
    }

    /**
     * Add closeday to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByCloseday(boolean direction_flag) {
        builder.addOrderByColumn("closeday",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add closeday to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByCloseday() {
        builder.addOrderByColumn("closeday","ASC");
    }


    /**
     * Set the closemonth to query.
     *
     * @param x The closemonth of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryClosemonth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getClosemonth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "closemonth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the closemonth to query
     * @param x The closemonth of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryClosemonth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryClosemonth( x, true );
    }

    /**
     * Add closemonth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByClosemonth(boolean direction_flag) {
        builder.addOrderByColumn("closemonth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add closemonth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByClosemonth() {
        builder.addOrderByColumn("closemonth","ASC");
    }


    /**
     * Set the closeyear to query.
     *
     * @param x The closeyear of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCloseyear(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getCloseyear() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "closeyear", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the closeyear to query
     * @param x The closeyear of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCloseyear( 
				int x )
    throws DataObjectException, QueryException {
	setQueryCloseyear( x, true );
    }

    /**
     * Add closeyear to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByCloseyear(boolean direction_flag) {
        builder.addOrderByColumn("closeyear",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add closeyear to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByCloseyear() {
        builder.addOrderByColumn("closeyear","ASC");
    }


    /**
     * Set the reportername to query.
     *
     * @param x The reportername of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryReportername(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getReportername();
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
	    builder.addWhereClause( "reportername", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the reportername to query
     * @param x The reportername of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryReportername( 
				String x )
    throws DataObjectException, QueryException {
	setQueryReportername( x, true );
    }

    /**
     * Add reportername to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByReportername(boolean direction_flag) {
        builder.addOrderByColumn("reportername",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add reportername to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByReportername() {
        builder.addOrderByColumn("reportername","ASC");
    }


    /**
     * Set the reporteremail to query.
     *
     * @param x The reporteremail of the Problem to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryReporteremail(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProblemDO DO = ( ProblemDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getReporteremail();
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
	    builder.addWhereClause( "reporteremail", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the reporteremail to query
     * @param x The reporteremail of the Problem to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryReporteremail( 
				String x )
    throws DataObjectException, QueryException {
	setQueryReporteremail( x, true );
    }

    /**
     * Add reporteremail to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByReporteremail(boolean direction_flag) {
        builder.addOrderByColumn("reporteremail",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add reporteremail to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByReporteremail() {
        builder.addOrderByColumn("reporteremail","ASC");
    }

    /**
    * Returns the <code>QueryBuilder</code> that this <code>ProblemQuery</code>
    * uses to construct and execute database queries.
    * <code>ProblemQuery.setQueryXXX</code> methods use 
    * the <code>QueryBuilder</code> to
    * append SQL expressions to the <code>"WHERE"</code> clause to be executed.
    * The <code>QueryBuilder.addEndClause method.</code> can be used to
    * append freeform SQL expressions to the <code>WHERE</code> clause,
    * e.g. "ORDER BY name".
    *
    * <b>Notes regarding cache-enabled DO classes:</b>
    * DO classes can be cache-enabled.  
    * If when using a <code>ProblemQuery</code>, the application developer
    * <b>does not call</b> <code>getQueryBuilder</code>,
    * then <code>ProblemQuery.setQueryXXX</code> methods 
    * simply prune the DO cache and return the remaining results.
    * However, a <code>QueryBuilder</code> builds
    * <CODE>SELECT</CODE> statements for execution by the actual database,
    * and never searches the built-in cache for the DO.
    * So, if the DO class is cache-enabled, and <code>getQueryBuilder</code>
    * is called, this <CODE>ProblemQuery</CODE> object ignores the cache 
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
