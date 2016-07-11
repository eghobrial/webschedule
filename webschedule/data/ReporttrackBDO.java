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
 * /home/emang/myapps/webschedule/webschedule/data/ReporttrackBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * ReporttrackBDO contains the same set and get methods as
 * the ReporttrackDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class ReporttrackBDO implements java.io.Serializable {

    /**
     * The ReporttrackDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from ReporttrackBDO
     * can access the underlying Data Object.
     */
    protected ReporttrackDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public ReporttrackDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>ReporttrackDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static ReporttrackBDO createVirgin() throws Exception { 
	ReporttrackBDO bdo = new ReporttrackBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>ReporttrackBDO</CODE>.
     * Example usage:<CODE>
     *      class CustomerBO extends CustomerBDO {
     *          // a BDO class is commonly extended in order to implement:
     *          public void beforeAnySet() throws Exception {
     *              if ( CustomerDO should not be altered )
     *                  throw new ApplicationException( "ERROR" );
     *          }
     *          public CustomerBO( CustomerDO DO ) {
     *              super( DO );
     *          }
     *      }
     *      class SomePresentationLayerClass {
     *          public CustomerBO findCustomer( String name ) {
     *              CustomerQuery q = new CustomerQuery();
     *              q.setQueryName( name );
     *              CustomerDO DO = q.getNextDO();
     *              // Here the CustomerBO ctor fires the CustomerBDO ctor.
     *              return new CustomerBO( DO );
     *          }
     *      }
     * </CODE>
     */
    public ReporttrackBDO( ReporttrackDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>ReporttrackBDO.create</CODE> methods.
     */
    public ReporttrackBDO() throws Exception { 
	this.DO = ReporttrackDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>ReporttrackBDO</CODE>
     * from a <CODE>ReporttrackDO</CODE> that was returned by 
     * the <CODE>ReporttrackQuery</CODE> class.
     */
    public static ReporttrackBDO createExisting( ReporttrackDO DO ) { 
	ReporttrackBDO bdo = new ReporttrackBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>ReporttrackBDO</CODE> objects
     * representing all the rows in the <CODE>Reporttrack</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>ReporttrackDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static ReporttrackBDO[] getBDOarray()
    throws DataObjectException {
        ReporttrackDO[] DOarray = null;
        try {
            ReporttrackQuery query = new ReporttrackQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>ReporttrackDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // ReporttrackQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        ReporttrackBDO[] BDOarray = new ReporttrackBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = ReporttrackBDO.createExisting( DOarray[i] );
 
        return BDOarray;
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
	if ( DO.isReadOnly() )
	    throw new DataObjectException( DO + " is read-only." ); 
    }
    protected void afterAnySet() {
    }

    /**
     * The methods <CODE>
     *     getHandle
     *     hasMatchingHandle
     * </CODE> are used by Presentation Objects that need to populate
     * HTML select lists with <CODE>ReporttrackBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This ReporttrackBDO object holds a reference to a ReporttrackDO object.
     * The id of this ReporttrackBDO is the id of its ReporttrackDO.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @return id of this BDO as a string
     *   If an object id can't be allocated for this object.
     */
    public String getHandle()
    throws DatabaseManagerException {
	return DO.getHandle();
    }

    /**
     * @param handle
     *   <CODE>String</CODE> representation of the id for this BDO
     * @return boolean
     *   True if the string version of the id of this DO matches passed handle
     * @see getHandle
     */
    public boolean hasMatchingHandle( String handle ) {
	return DO.hasMatchingHandle( handle );
    }


    /**
     * for debugging
     */
    public String toString() {
	if ( null == DO )
		return "NULL";
	return DO.toString();
    }

   /**
    * Get day of the ReporttrackDO
    *
    * @return day of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getDay () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getDay ();
   }

   
   /**
    * Set day of the ReporttrackDO
    *
    * @param day of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setDay ( int day ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setDay ( day );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get month of the ReporttrackDO
    *
    * @return month of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getMonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getMonth ();
   }

   
   /**
    * Set month of the ReporttrackDO
    *
    * @param month of the ReporttrackDO
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
      DO.setMonth ( month );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get year of the ReporttrackDO
    *
    * @return year of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getYear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getYear ();
   }

   
   /**
    * Set year of the ReporttrackDO
    *
    * @param year of the ReporttrackDO
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
      DO.setYear ( year );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get reportinfo of the ReporttrackDO
    *
    * @return reportinfo of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getReportinfo () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getReportinfo ();
   }

   
   /**
    * Set reportinfo of the ReporttrackDO
    *
    * @param reportinfo of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setReportinfo ( int reportinfo ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setReportinfo ( reportinfo );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get startday of the ReporttrackDO
    *
    * @return startday of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStartday ();
   }

   
   /**
    * Set startday of the ReporttrackDO
    *
    * @param startday of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStartday ( int startday ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStartday ( startday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get startmonth of the ReporttrackDO
    *
    * @return startmonth of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStartmonth ();
   }

   
   /**
    * Set startmonth of the ReporttrackDO
    *
    * @param startmonth of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStartmonth ( int startmonth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStartmonth ( startmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get startyear of the ReporttrackDO
    *
    * @return startyear of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStartyear ();
   }

   
   /**
    * Set startyear of the ReporttrackDO
    *
    * @param startyear of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStartyear ( int startyear ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStartyear ( startyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get endday of the ReporttrackDO
    *
    * @return endday of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEndday ();
   }

   
   /**
    * Set endday of the ReporttrackDO
    *
    * @param endday of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEndday ( int endday ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEndday ( endday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get endmonth of the ReporttrackDO
    *
    * @return endmonth of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEndmonth ();
   }

   
   /**
    * Set endmonth of the ReporttrackDO
    *
    * @param endmonth of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEndmonth ( int endmonth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEndmonth ( endmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get endyear of the ReporttrackDO
    *
    * @return endyear of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEndyear ();
   }

   
   /**
    * Set endyear of the ReporttrackDO
    *
    * @param endyear of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEndyear ( int endyear ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEndyear ( endyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get preparedby of the ReporttrackDO
    *
    * @return preparedby of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPreparedby () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPreparedby ();
   }

   
   /**
    * Set preparedby of the ReporttrackDO
    *
    * @param preparedby of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPreparedby ( String preparedby ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPreparedby ( preparedby );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get invoice_num of the ReporttrackDO
    *
    * @return invoice_num of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getInvoice_num () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getInvoice_num ();
   }

   
   /**
    * Set invoice_num of the ReporttrackDO
    *
    * @param invoice_num of the ReporttrackDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setInvoice_num ( int invoice_num ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setInvoice_num ( invoice_num );
      afterAnySet();	// business actions/assertions after data assignment
   }


   



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
   * The transaction is likely provided by the commit() method of another BDO
   * whose DO references this DO.
   * 
   * @param dbt The transaction object to use for this operation.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
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
   * The transaction is likely provided by the delete() method of another BDO
   * whose DO references this DO.
   *
   * @param dbt The transaction object to use for this operation.
   * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
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
   * @exception RefAssertionException thrown by okTo method.
   * @exception java.sql.SQLException if any SQL errors occur.
   */
  protected void modifyDO( DBTransaction dbt, boolean delete )
  throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
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
	  
      }
      if ( false ) {
	  // This throw is here to keep the compiler happy
	  // in the case of a DO that does not refer to other DOs.
	  // In that case, the above delete/commit code blocks will be empty
	  // and throw nothing.
	  throw new DataObjectException( "foo" );	  
      }
      if ( delete ) {
          dbt.delete( DO );
      } else {
	  if ( DO.isLoaded() )
	      dbt.insert( DO );   // dbt.insert() handles insertions and updates
      }
      if (ownTransaction) {
	  dbt.commit(); // commit the transaction
      }
    } catch (DataObjectException doe) {
      throw doe;
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
