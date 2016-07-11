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
 * /home/emang/myapps/webschedule/webschedule/data/complaintBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * complaintBDO contains the same set and get methods as
 * the complaintDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class complaintBDO implements java.io.Serializable {

    /**
     * The complaintDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from complaintBDO
     * can access the underlying Data Object.
     */
    protected complaintDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public complaintDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>complaintDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static complaintBDO createVirgin() throws Exception { 
	complaintBDO bdo = new complaintBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>complaintBDO</CODE>.
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
    public complaintBDO( complaintDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>complaintBDO.create</CODE> methods.
     */
    public complaintBDO() throws Exception { 
	this.DO = complaintDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>complaintBDO</CODE>
     * from a <CODE>complaintDO</CODE> that was returned by 
     * the <CODE>complaintQuery</CODE> class.
     */
    public static complaintBDO createExisting( complaintDO DO ) { 
	complaintBDO bdo = new complaintBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>complaintBDO</CODE> objects
     * representing all the rows in the <CODE>complaint</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>complaintDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static complaintBDO[] getBDOarray()
    throws DataObjectException {
        complaintDO[] DOarray = null;
        try {
            complaintQuery query = new complaintQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>complaintDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // complaintQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        complaintBDO[] BDOarray = new complaintBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = complaintBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>complaintBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This complaintBDO object holds a reference to a complaintDO object.
     * The id of this complaintBDO is the id of its complaintDO.
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
    * Get owner of the complaintDO
    *
    * @return owner of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonDO getOwner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOwner ();
   }

   
   /**
    * Set owner of the complaintDO
    *
    * @param owner of the complaintDO
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
      DO.setOwner ( owner );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped owner of the complaintDO
    *
    * @return BDO-wrapped owner of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonBDO getOwnerBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.PersonBDO b = webschedule.data.PersonBDO.createExisting(
					  DO.getOwner () );
      return b;
   }

   /**
    * Set owner of the complaintDO
    *
    * @param BDO-wrapped owner of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOwner ( webschedule.data.PersonBDO owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == owner ) {
	  if ( false )
	      DO.setOwner ( null );
	  else 
	      throw new DataObjectException( 
		  "complaintBDO.setOwner does not allow NULL." );
      } else {
          DO.setOwner ( owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get postday of the complaintDO
    *
    * @return postday of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Timestamp getPostday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPostday ();
   }

   
   /**
    * Set postday of the complaintDO
    *
    * @param postday of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPostday ( java.sql.Timestamp postday ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPostday ( postday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get reportername of the complaintDO
    *
    * @return reportername of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getReportername () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getReportername ();
   }

   
   /**
    * Set reportername of the complaintDO
    *
    * @param reportername of the complaintDO
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
      DO.setReportername ( reportername );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get reporteremail of the complaintDO
    *
    * @return reporteremail of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getReporteremail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getReporteremail ();
   }

   
   /**
    * Set reporteremail of the complaintDO
    *
    * @param reporteremail of the complaintDO
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
      DO.setReporteremail ( reporteremail );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Time_prob of the complaintDO
    *
    * @return Time_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getTime_prob () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTime_prob ();
   }

   
   /**
    * Set Time_prob of the complaintDO
    *
    * @param Time_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTime_prob ( boolean Time_prob ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTime_prob ( Time_prob );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Cables_prob of the complaintDO
    *
    * @return Cables_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getCables_prob () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCables_prob ();
   }

   
   /**
    * Set Cables_prob of the complaintDO
    *
    * @param Cables_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCables_prob ( boolean Cables_prob ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCables_prob ( Cables_prob );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Mess_prob of the complaintDO
    *
    * @return Mess_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getMess_prob () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getMess_prob ();
   }

   
   /**
    * Set Mess_prob of the complaintDO
    *
    * @param Mess_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setMess_prob ( boolean Mess_prob ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setMess_prob ( Mess_prob );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get other_prob of the complaintDO
    *
    * @return other_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getOther_prob () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOther_prob ();
   }

   
   /**
    * Set other_prob of the complaintDO
    *
    * @param other_prob of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOther_prob ( boolean other_prob ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOther_prob ( other_prob );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get specify of the complaintDO
    *
    * @return specify of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getSpecify () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getSpecify ();
   }

   
   /**
    * Set specify of the complaintDO
    *
    * @param specify of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setSpecify ( String specify ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setSpecify ( specify );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get fault_op of the complaintDO
    *
    * @return fault_op of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.OperatorDO getFault_op () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFault_op ();
   }

   
   /**
    * Set fault_op of the complaintDO
    *
    * @param fault_op of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFault_op ( webschedule.data.OperatorDO fault_op ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFault_op ( fault_op );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped fault_op of the complaintDO
    *
    * @return BDO-wrapped fault_op of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.OperatorBDO getFault_opBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.OperatorBDO b = webschedule.data.OperatorBDO.createExisting(
					  DO.getFault_op () );
      return b;
   }

   /**
    * Set fault_op of the complaintDO
    *
    * @param BDO-wrapped fault_op of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFault_op ( webschedule.data.OperatorBDO fault_op ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == fault_op ) {
	  if ( true )
	      DO.setFault_op ( null );
	  else 
	      throw new DataObjectException( 
		  "complaintBDO.setFault_op does not allow NULL." );
      } else {
          DO.setFault_op ( fault_op.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get fault_pi of the complaintDO
    *
    * @return fault_pi of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonDO getFault_pi () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFault_pi ();
   }

   
   /**
    * Set fault_pi of the complaintDO
    *
    * @param fault_pi of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFault_pi ( webschedule.data.PersonDO fault_pi ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFault_pi ( fault_pi );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped fault_pi of the complaintDO
    *
    * @return BDO-wrapped fault_pi of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonBDO getFault_piBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.PersonBDO b = webschedule.data.PersonBDO.createExisting(
					  DO.getFault_pi () );
      return b;
   }

   /**
    * Set fault_pi of the complaintDO
    *
    * @param BDO-wrapped fault_pi of the complaintDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFault_pi ( webschedule.data.PersonBDO fault_pi ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == fault_pi ) {
	  if ( true )
	      DO.setFault_pi ( null );
	  else 
	      throw new DataObjectException( 
		  "complaintBDO.setFault_pi does not allow NULL." );
      } else {
          DO.setFault_pi ( fault_pi.getDO() );
      }
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
     * A stub method for implementing pre-commit assertions 
     * for the fault_op data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where fault_op is not valid for writing to the database.
     */
    protected void okToCommitFault_op( webschedule.data.OperatorDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the fault_op data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where fault_op is not valid for deletion from the database.
     */
    protected void okToDeleteFault_op( webschedule.data.OperatorDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-commit assertions 
     * for the fault_pi data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where fault_pi is not valid for writing to the database.
     */
    protected void okToCommitFault_pi( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the fault_pi data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where fault_pi is not valid for deletion from the database.
     */
    protected void okToDeleteFault_pi( webschedule.data.PersonDO member ) 
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
	  	webschedule.data.PersonDO owner_DO = DO.getOwner();
	if ( null != owner_DO ) {
	    if ( owner_DO.isLoaded() ) {
		okToCommitOwner( owner_DO );
		webschedule.data.PersonBDO b = 
		    webschedule.data.PersonBDO.createExisting(
						    owner_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit complaintBDO ( " + toString() +
		    " ) because owner is not allowed to be null." );
	}
	webschedule.data.OperatorDO fault_op_DO = DO.getFault_op();
	if ( null != fault_op_DO ) {
	    if ( fault_op_DO.isLoaded() ) {
		okToCommitFault_op( fault_op_DO );
		webschedule.data.OperatorBDO b = 
		    webschedule.data.OperatorBDO.createExisting(
						    fault_op_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! true )
		throw new RefAssertionException(
		    "Cannot commit complaintBDO ( " + toString() +
		    " ) because fault_op is not allowed to be null." );
	}
	webschedule.data.PersonDO fault_pi_DO = DO.getFault_pi();
	if ( null != fault_pi_DO ) {
	    if ( fault_pi_DO.isLoaded() ) {
		okToCommitFault_pi( fault_pi_DO );
		webschedule.data.PersonBDO b = 
		    webschedule.data.PersonBDO.createExisting(
						    fault_pi_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! true )
		throw new RefAssertionException(
		    "Cannot commit complaintBDO ( " + toString() +
		    " ) because fault_pi is not allowed to be null." );
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
