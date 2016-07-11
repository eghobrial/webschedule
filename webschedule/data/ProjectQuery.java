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
 * /home/emang/myapps/webschedule/webschedule/data/ProjectQuery.java
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
 * ProjectQuery is used to query the Project table in the database.<BR>
 * It returns objects of type ProjectDO.
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
final public class ProjectQuery implements Query {

    private QueryBuilder builder;

    /**
     * Public constructor.
     */
    public ProjectQuery() {
	builder = new QueryBuilder( "Project", "Project.*" );
	builder.setDatabaseVendor( "PostgreSQL" );
	builder.setStringMatchDetails( "LIKE", "%" );
	reset();
    }

    private ResultSet		resultSet	= null;
    private boolean 		uniqueInstance	= false;
    private ProjectDO[]	DOs		= null;
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
		    results.addElement( new ProjectDO ( resultSet ) );
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
//		    results.addElement( new ProjectDO ( resultSet ) );
		    results.addElement( ProjectDO.createExisting ( resultSet ) );
	        }
	    } else {
		results = cacheHits;	 // executeQuery saw cache hits
	    }

	    if ( results.size() > 1 && uniqueInstance )
		throw new NonUniqueQueryException(
		    "Too many rows returned from database" );
	    DOs = new ProjectDO [ results.size() ];
	    for ( int i = 0; i < results.size(); i++ ) {
		DOs[i] = ( ProjectDO ) results.elementAt( i );
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
    public ProjectDO[] getDOArray()
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
    public ProjectDO getNextDO()
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
    public ProjectBDO[] getBDOArray()
    throws DataObjectException, NonUniqueQueryException {
	if ( needToRun )
	    runQuery();
	ProjectBDO[] BDOs = new ProjectBDO[ DOs.length ];
	for ( int i = 0; i < DOs.length; i++ )
	    BDOs[ i ] = ProjectBDO.createExisting( DOs[ i ] );
	return BDOs;
    }

    /**
     * Return successive BDOs from array built from ResultSet returned by query.
     * @exception DataObjectException If a database access error occurs.
     * @exception NonUniqueQueryException If too many rows were found.
     */
    public ProjectBDO getNextBDO()
    throws DataObjectException, NonUniqueQueryException {
	ProjectDO DO = getNextDO();
	if ( null == DO )
	    return null;
	return ProjectBDO.createExisting( DO );
    }


    /**
     * Set the OID to query.
     * WARNING!  This method assumes that table <CODE>Project</CODE>
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
            ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
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

        return ProjectDO.createExisting(rs);
	*/
	throw new ObjectIdException(
	    "next() should not be used.  Use getNextDO() instead." );
	//return null;
    }


    /**
     * Set the proj_name to query.
     *
     * @param x The proj_name of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryProj_name(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getProj_name();
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
	    builder.addWhereClause( "proj_name", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the proj_name to query
     * @param x The proj_name of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryProj_name( 
				String x )
    throws DataObjectException, QueryException {
	setQueryProj_name( x, true );
    }

    /**
     * Add proj_name to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByProj_name(boolean direction_flag) {
        builder.addOrderByColumn("proj_name",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add proj_name to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByProj_name() {
        builder.addOrderByColumn("proj_name","ASC");
    }


    /**
     * Set the Description to query.
     *
     * @param x The Description of the Project to query.
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
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
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
	    builder.addWhereClause( "Description", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the Description to query
     * @param x The Description of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryDescription( 
				String x )
    throws DataObjectException, QueryException {
	setQueryDescription( x, true );
    }

    /**
     * Add Description to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByDescription(boolean direction_flag) {
        builder.addOrderByColumn("Description",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add Description to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByDescription() {
        builder.addOrderByColumn("Description","ASC");
    }


    /**
     * Set the indexnum to query.
     *
     * @param x The indexnum of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIndexnum(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getIndexnum();
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
	    builder.addWhereClause( "indexnum", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the indexnum to query
     * @param x The indexnum of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIndexnum( 
				String x )
    throws DataObjectException, QueryException {
	setQueryIndexnum( x, true );
    }

    /**
     * Add indexnum to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIndexnum(boolean direction_flag) {
        builder.addOrderByColumn("indexnum",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add indexnum to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIndexnum() {
        builder.addOrderByColumn("indexnum","ASC");
    }


    /**
     * Set the codeofpay to query.
     *
     * @param x The codeofpay of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCodeofpay(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getCodeofpay() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "codeofpay", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the codeofpay to query
     * @param x The codeofpay of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCodeofpay( 
				int x )
    throws DataObjectException, QueryException {
	setQueryCodeofpay( x, true );
    }

    /**
     * Add codeofpay to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByCodeofpay(boolean direction_flag) {
        builder.addOrderByColumn("codeofpay",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add codeofpay to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByCodeofpay() {
        builder.addOrderByColumn("codeofpay","ASC");
    }


    /**
     * Set the password to query.
     *
     * @param x The password of the Project to query.
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
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
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
     * @param x The password of the Project to query.
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
     * Set the totalhours to query.
     *
     * @param x The totalhours of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTotalhours(
				double x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getTotalhours() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "totalhours", x, "DOUBLE",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the totalhours to query
     * @param x The totalhours of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryTotalhours( 
				double x )
    throws DataObjectException, QueryException {
	setQueryTotalhours( x, true );
    }

    /**
     * Add totalhours to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByTotalhours(boolean direction_flag) {
        builder.addOrderByColumn("totalhours",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add totalhours to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByTotalhours() {
        builder.addOrderByColumn("totalhours","ASC");
    }


    /**
     * Set the donehours to query.
     *
     * @param x The donehours of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryDonehours(
				double x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getDonehours() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "donehours", x, "DOUBLE",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the donehours to query
     * @param x The donehours of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryDonehours( 
				double x )
    throws DataObjectException, QueryException {
	setQueryDonehours( x, true );
    }

    /**
     * Add donehours to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByDonehours(boolean direction_flag) {
        builder.addOrderByColumn("donehours",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add donehours to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByDonehours() {
        builder.addOrderByColumn("donehours","ASC");
    }


    /**
     * Set the owner to query.
     *
     * @param x The owner of the Project to query.
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
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
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
     * @param x The owner of the Project to query.
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
     * Set the contactname to query.
     *
     * @param x The contactname of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryContactname(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getContactname();
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
	    builder.addWhereClause( "contactname", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the contactname to query
     * @param x The contactname of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryContactname( 
				String x )
    throws DataObjectException, QueryException {
	setQueryContactname( x, true );
    }

    /**
     * Add contactname to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByContactname(boolean direction_flag) {
        builder.addOrderByColumn("contactname",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add contactname to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByContactname() {
        builder.addOrderByColumn("contactname","ASC");
    }


    /**
     * Set the contactphone to query.
     *
     * @param x The contactphone of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryContactphone(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getContactphone();
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
	    builder.addWhereClause( "contactphone", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the contactphone to query
     * @param x The contactphone of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryContactphone( 
				String x )
    throws DataObjectException, QueryException {
	setQueryContactphone( x, true );
    }

    /**
     * Add contactphone to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByContactphone(boolean direction_flag) {
        builder.addOrderByColumn("contactphone",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add contactphone to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByContactphone() {
        builder.addOrderByColumn("contactphone","ASC");
    }


    /**
     * Set the billaddr1 to query.
     *
     * @param x The billaddr1 of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryBilladdr1(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getBilladdr1();
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
	    builder.addWhereClause( "billaddr1", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the billaddr1 to query
     * @param x The billaddr1 of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryBilladdr1( 
				String x )
    throws DataObjectException, QueryException {
	setQueryBilladdr1( x, true );
    }

    /**
     * Add billaddr1 to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByBilladdr1(boolean direction_flag) {
        builder.addOrderByColumn("billaddr1",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add billaddr1 to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByBilladdr1() {
        builder.addOrderByColumn("billaddr1","ASC");
    }


    /**
     * Set the billaddr2 to query.
     *
     * @param x The billaddr2 of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryBilladdr2(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getBilladdr2();
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
	    builder.addWhereClause( "billaddr2", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the billaddr2 to query
     * @param x The billaddr2 of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryBilladdr2( 
				String x )
    throws DataObjectException, QueryException {
	setQueryBilladdr2( x, true );
    }

    /**
     * Add billaddr2 to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByBilladdr2(boolean direction_flag) {
        builder.addOrderByColumn("billaddr2",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add billaddr2 to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByBilladdr2() {
        builder.addOrderByColumn("billaddr2","ASC");
    }


    /**
     * Set the billaddr3 to query.
     *
     * @param x The billaddr3 of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryBilladdr3(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getBilladdr3();
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
	    builder.addWhereClause( "billaddr3", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the billaddr3 to query
     * @param x The billaddr3 of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryBilladdr3( 
				String x )
    throws DataObjectException, QueryException {
	setQueryBilladdr3( x, true );
    }

    /**
     * Add billaddr3 to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByBilladdr3(boolean direction_flag) {
        builder.addOrderByColumn("billaddr3",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add billaddr3 to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByBilladdr3() {
        builder.addOrderByColumn("billaddr3","ASC");
    }


    /**
     * Set the city to query.
     *
     * @param x The city of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCity(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getCity();
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
	    builder.addWhereClause( "city", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the city to query
     * @param x The city of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCity( 
				String x )
    throws DataObjectException, QueryException {
	setQueryCity( x, true );
    }

    /**
     * Add city to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByCity(boolean direction_flag) {
        builder.addOrderByColumn("city",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add city to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByCity() {
        builder.addOrderByColumn("city","ASC");
    }


    /**
     * Set the state to query.
     *
     * @param x The state of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryState(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getState();
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
	    builder.addWhereClause( "state", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the state to query
     * @param x The state of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryState( 
				String x )
    throws DataObjectException, QueryException {
	setQueryState( x, true );
    }

    /**
     * Add state to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByState(boolean direction_flag) {
        builder.addOrderByColumn("state",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add state to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByState() {
        builder.addOrderByColumn("state","ASC");
    }


    /**
     * Set the zip to query.
     *
     * @param x The zip of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryZip(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getZip();
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
	    builder.addWhereClause( "zip", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the zip to query
     * @param x The zip of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryZip( 
				String x )
    throws DataObjectException, QueryException {
	setQueryZip( x, true );
    }

    /**
     * Add zip to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByZip(boolean direction_flag) {
        builder.addOrderByColumn("zip",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add zip to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByZip() {
        builder.addOrderByColumn("zip","ASC");
    }


    /**
     * Set the accountid to query.
     *
     * @param x The accountid of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryAccountid(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getAccountid();
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
	    builder.addWhereClause( "accountid", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the accountid to query
     * @param x The accountid of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryAccountid( 
				String x )
    throws DataObjectException, QueryException {
	setQueryAccountid( x, true );
    }

    /**
     * Add accountid to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByAccountid(boolean direction_flag) {
        builder.addOrderByColumn("accountid",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add accountid to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByAccountid() {
        builder.addOrderByColumn("accountid","ASC");
    }


    /**
     * Set the isoutside to query.
     *
     * @param x The isoutside of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsoutside(
				boolean x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getIsoutside() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "isoutside", x, "BOOL",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the isoutside to query
     * @param x The isoutside of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsoutside( 
				boolean x )
    throws DataObjectException, QueryException {
	setQueryIsoutside( x, true );
    }

    /**
     * Add isoutside to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIsoutside(boolean direction_flag) {
        builder.addOrderByColumn("isoutside",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add isoutside to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIsoutside() {
        builder.addOrderByColumn("isoutside","ASC");
    }


    /**
     * Set the expday to query.
     *
     * @param x The expday of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryExpday(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getExpday() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "expday", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the expday to query
     * @param x The expday of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryExpday( 
				int x )
    throws DataObjectException, QueryException {
	setQueryExpday( x, true );
    }

    /**
     * Add expday to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByExpday(boolean direction_flag) {
        builder.addOrderByColumn("expday",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add expday to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByExpday() {
        builder.addOrderByColumn("expday","ASC");
    }


    /**
     * Set the expmonth to query.
     *
     * @param x The expmonth of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryExpmonth(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getExpmonth() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "expmonth", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the expmonth to query
     * @param x The expmonth of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryExpmonth( 
				int x )
    throws DataObjectException, QueryException {
	setQueryExpmonth( x, true );
    }

    /**
     * Add expmonth to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByExpmonth(boolean direction_flag) {
        builder.addOrderByColumn("expmonth",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add expmonth to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByExpmonth() {
        builder.addOrderByColumn("expmonth","ASC");
    }


    /**
     * Set the expyear to query.
     *
     * @param x The expyear of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryExpyear(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getExpyear() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "expyear", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the expyear to query
     * @param x The expyear of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryExpyear( 
				int x )
    throws DataObjectException, QueryException {
	setQueryExpyear( x, true );
    }

    /**
     * Add expyear to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByExpyear(boolean direction_flag) {
        builder.addOrderByColumn("expyear",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add expyear to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByExpyear() {
        builder.addOrderByColumn("expyear","ASC");
    }


    /**
     * Set the isExp to query.
     *
     * @param x The isExp of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsExp(
				boolean x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getIsExp() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "isExp", x, "BOOL",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the isExp to query
     * @param x The isExp of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIsExp( 
				boolean x )
    throws DataObjectException, QueryException {
	setQueryIsExp( x, true );
    }

    /**
     * Add isExp to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIsExp(boolean direction_flag) {
        builder.addOrderByColumn("isExp",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add isExp to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIsExp() {
        builder.addOrderByColumn("isExp","ASC");
    }


    /**
     * Set the canCredit to query.
     *
     * @param x The canCredit of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCanCredit(
				int x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getCanCredit() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "canCredit", x, "INTEGER",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the canCredit to query
     * @param x The canCredit of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryCanCredit( 
				int x )
    throws DataObjectException, QueryException {
	setQueryCanCredit( x, true );
    }

    /**
     * Add canCredit to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByCanCredit(boolean direction_flag) {
        builder.addOrderByColumn("canCredit",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add canCredit to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByCanCredit() {
        builder.addOrderByColumn("canCredit","ASC");
    }


    /**
     * Set the Institute to query.
     *
     * @param x The Institute of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryInstitute(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getInstitute();
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
	    builder.addWhereClause( "Institute", x, "VARCHAR",
                QueryBuilder.NOT_NULL, exactFlag( exact ) );
    }

    /**
     * Set the Institute to query
     * @param x The Institute of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryInstitute( 
				String x )
    throws DataObjectException, QueryException {
	setQueryInstitute( x, true );
    }

    /**
     * Add Institute to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByInstitute(boolean direction_flag) {
        builder.addOrderByColumn("Institute",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add Institute to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByInstitute() {
        builder.addOrderByColumn("Institute","ASC");
    }


    /**
     * Set the fpemail to query.
     *
     * @param x The fpemail of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryFpemail(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getFpemail();
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
	    builder.addWhereClause( "fpemail", x, "VARCHAR",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the fpemail to query
     * @param x The fpemail of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryFpemail( 
				String x )
    throws DataObjectException, QueryException {
	setQueryFpemail( x, true );
    }

    /**
     * Add fpemail to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByFpemail(boolean direction_flag) {
        builder.addOrderByColumn("fpemail",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add fpemail to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByFpemail() {
        builder.addOrderByColumn("fpemail","ASC");
    }


    /**
     * Set the POnum to query.
     *
     * @param x The POnum of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPOnum(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getPOnum();
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
	    builder.addWhereClause( "POnum", x, "VARCHAR",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the POnum to query
     * @param x The POnum of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPOnum( 
				String x )
    throws DataObjectException, QueryException {
	setQueryPOnum( x, true );
    }

    /**
     * Add POnum to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPOnum(boolean direction_flag) {
        builder.addOrderByColumn("POnum",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add POnum to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPOnum() {
        builder.addOrderByColumn("POnum","ASC");
    }


    /**
     * Set the POexpdate to query.
     *
     * @param x The POexpdate of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPOexpdate(
				java.sql.Date x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		Date d = (Date) DO.getPOexpdate();
		if ( null == d && null == x ) {
		    equals = true;
		} else if ( null != d && null != x ) {
		    equals = d.equals( x );
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "POexpdate", x, "DATE",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the POexpdate to query
     * @param x The POexpdate of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPOexpdate( 
				java.sql.Date x )
    throws DataObjectException, QueryException {
	setQueryPOexpdate( x, true );
    }

    /**
     * Add POexpdate to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPOexpdate(boolean direction_flag) {
        builder.addOrderByColumn("POexpdate",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add POexpdate to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPOexpdate() {
        builder.addOrderByColumn("POexpdate","ASC");
    }


    /**
     * Set the POhours to query.
     *
     * @param x The POhours of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPOhours(
				double x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		// primitive types are compared using the == operator.
		equals = ( DO.getPOhours() == x );
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "POhours", x, "DOUBLE",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the POhours to query
     * @param x The POhours of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryPOhours( 
				double x )
    throws DataObjectException, QueryException {
	setQueryPOhours( x, true );
    }

    /**
     * Add POhours to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByPOhours(boolean direction_flag) {
        builder.addOrderByColumn("POhours",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add POhours to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByPOhours() {
        builder.addOrderByColumn("POhours","ASC");
    }


    /**
     * Set the IACUCDate to query.
     *
     * @param x The IACUCDate of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIACUCDate(
				java.sql.Date x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		Date d = (Date) DO.getIACUCDate();
		if ( null == d && null == x ) {
		    equals = true;
		} else if ( null != d && null != x ) {
		    equals = d.equals( x );
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "IACUCDate", x, "DATE",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the IACUCDate to query
     * @param x The IACUCDate of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIACUCDate( 
				java.sql.Date x )
    throws DataObjectException, QueryException {
	setQueryIACUCDate( x, true );
    }

    /**
     * Add IACUCDate to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIACUCDate(boolean direction_flag) {
        builder.addOrderByColumn("IACUCDate",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add IACUCDate to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIACUCDate() {
        builder.addOrderByColumn("IACUCDate","ASC");
    }


    /**
     * Set the Modifiedby to query.
     *
     * @param x The Modifiedby of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryModifiedby(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getModifiedby();
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
	    builder.addWhereClause( "Modifiedby", x, "VARCHAR",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the Modifiedby to query
     * @param x The Modifiedby of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryModifiedby( 
				String x )
    throws DataObjectException, QueryException {
	setQueryModifiedby( x, true );
    }

    /**
     * Add Modifiedby to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByModifiedby(boolean direction_flag) {
        builder.addOrderByColumn("Modifiedby",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add Modifiedby to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByModifiedby() {
        builder.addOrderByColumn("Modifiedby","ASC");
    }


    /**
     * Set the ModDate to query.
     *
     * @param x The ModDate of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryModDate(
				java.sql.Date x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		Date d = (Date) DO.getModDate();
		if ( null == d && null == x ) {
		    equals = true;
		} else if ( null != d && null != x ) {
		    equals = d.equals( x );
		} else {  // one is null, the other isn't
		    equals = false;
		}
	    
	    if ( ! equals )
		cacheHits.removeElementAt( i-- );
	}

	// Also prepare the SQL needed to query the database 
	// in case there is no cache, or the query involves other tables.
	if ( partialCache || hitDb )
	    builder.addWhereClause( "ModDate", x, "DATE",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the ModDate to query
     * @param x The ModDate of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryModDate( 
				java.sql.Date x )
    throws DataObjectException, QueryException {
	setQueryModDate( x, true );
    }

    /**
     * Add ModDate to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByModDate(boolean direction_flag) {
        builder.addOrderByColumn("ModDate",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add ModDate to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByModDate() {
        builder.addOrderByColumn("ModDate","ASC");
    }


    /**
     * Set the IRBnum to query.
     *
     * @param x The IRBnum of the Project to query.
     * @param exact to use matches or not
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIRBnum(
				String x, boolean exact)
    throws DataObjectException, QueryException
    {
	// Remove from cacheHits any DOs that do not meet this
	// setQuery requirement.
	for ( int i = 0; i < cacheHits.size() && ! hitDb; i++ ) {
	    ProjectDO DO = ( ProjectDO ) cacheHits.elementAt( i );
	    if ( null == DO ) continue;
	    boolean equals = true;
	    
		String s = DO.getIRBnum();
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
	    builder.addWhereClause( "IRBnum", x, "VARCHAR",
                QueryBuilder.NULL_OK, exactFlag( exact ) );
    }

    /**
     * Set the IRBnum to query
     * @param x The IRBnum of the Project to query.
     * @exception DataObjectException If a database access error occurs.
     */
    public void setQueryIRBnum( 
				String x )
    throws DataObjectException, QueryException {
	setQueryIRBnum( x, true );
    }

    /**
     * Add IRBnum to the ORDER BY clause.
     *
     * @param direction_flag  True for ascending order, false for descending
     */
    public void addOrderByIRBnum(boolean direction_flag) {
        builder.addOrderByColumn("IRBnum",
					(direction_flag) ? "ASC" : "DESC");
    }


    /**
     * Add IRBnum to the ORDER BY clause.  This convenience
     * method assumes ascending order.
     */
    public void addOrderByIRBnum() {
        builder.addOrderByColumn("IRBnum","ASC");
    }

    /**
    * Returns the <code>QueryBuilder</code> that this <code>ProjectQuery</code>
    * uses to construct and execute database queries.
    * <code>ProjectQuery.setQueryXXX</code> methods use 
    * the <code>QueryBuilder</code> to
    * append SQL expressions to the <code>"WHERE"</code> clause to be executed.
    * The <code>QueryBuilder.addEndClause method.</code> can be used to
    * append freeform SQL expressions to the <code>WHERE</code> clause,
    * e.g. "ORDER BY name".
    *
    * <b>Notes regarding cache-enabled DO classes:</b>
    * DO classes can be cache-enabled.  
    * If when using a <code>ProjectQuery</code>, the application developer
    * <b>does not call</b> <code>getQueryBuilder</code>,
    * then <code>ProjectQuery.setQueryXXX</code> methods 
    * simply prune the DO cache and return the remaining results.
    * However, a <code>QueryBuilder</code> builds
    * <CODE>SELECT</CODE> statements for execution by the actual database,
    * and never searches the built-in cache for the DO.
    * So, if the DO class is cache-enabled, and <code>getQueryBuilder</code>
    * is called, this <CODE>ProjectQuery</CODE> object ignores the cache 
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
