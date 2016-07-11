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
 * /home/emang/myapps/webschedule/webschedule/data/OperatorBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * OperatorBDO contains the same set and get methods as
 * the OperatorDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class OperatorBDO implements java.io.Serializable {

    /**
     * The OperatorDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from OperatorBDO
     * can access the underlying Data Object.
     */
    protected OperatorDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public OperatorDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>OperatorDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static OperatorBDO createVirgin() throws Exception { 
	OperatorBDO bdo = new OperatorBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>OperatorBDO</CODE>.
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
    public OperatorBDO( OperatorDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>OperatorBDO.create</CODE> methods.
     */
    public OperatorBDO() throws Exception { 
	this.DO = OperatorDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>OperatorBDO</CODE>
     * from a <CODE>OperatorDO</CODE> that was returned by 
     * the <CODE>OperatorQuery</CODE> class.
     */
    public static OperatorBDO createExisting( OperatorDO DO ) { 
	OperatorBDO bdo = new OperatorBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>OperatorBDO</CODE> objects
     * representing all the rows in the <CODE>Operator</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>OperatorDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static OperatorBDO[] getBDOarray()
    throws DataObjectException {
        OperatorDO[] DOarray = null;
        try {
            OperatorQuery query = new OperatorQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>OperatorDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // OperatorQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        OperatorBDO[] BDOarray = new OperatorBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = OperatorBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>OperatorBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This OperatorBDO object holds a reference to a OperatorDO object.
     * The id of this OperatorBDO is the id of its OperatorDO.
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
    * Get FirstName of the OperatorDO
    *
    * @return FirstName of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFirstName () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFirstName ();
   }

   
   /**
    * Set FirstName of the OperatorDO
    *
    * @param FirstName of the OperatorDO
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
      DO.setFirstName ( FirstName );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get LastName of the OperatorDO
    *
    * @return LastName of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getLastName () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getLastName ();
   }

   
   /**
    * Set LastName of the OperatorDO
    *
    * @param LastName of the OperatorDO
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
      DO.setLastName ( LastName );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get certday of the OperatorDO
    *
    * @return certday of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCertday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCertday ();
   }

   
   /**
    * Set certday of the OperatorDO
    *
    * @param certday of the OperatorDO
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
      DO.setCertday ( certday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get certmonth of the OperatorDO
    *
    * @return certmonth of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCertmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCertmonth ();
   }

   
   /**
    * Set certmonth of the OperatorDO
    *
    * @param certmonth of the OperatorDO
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
      DO.setCertmonth ( certmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get certyear of the OperatorDO
    *
    * @return certyear of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCertyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCertyear ();
   }

   
   /**
    * Set certyear of the OperatorDO
    *
    * @param certyear of the OperatorDO
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
      DO.setCertyear ( certyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get lastscanday of the OperatorDO
    *
    * @return lastscanday of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getLastscanday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getLastscanday ();
   }

   
   /**
    * Set lastscanday of the OperatorDO
    *
    * @param lastscanday of the OperatorDO
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
      DO.setLastscanday ( lastscanday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get lastscanmonth of the OperatorDO
    *
    * @return lastscanmonth of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getLastscanmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getLastscanmonth ();
   }

   
   /**
    * Set lastscanmonth of the OperatorDO
    *
    * @param lastscanmonth of the OperatorDO
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
      DO.setLastscanmonth ( lastscanmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get lastscanyear of the OperatorDO
    *
    * @return lastscanyear of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getLastscanyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getLastscanyear ();
   }

   
   /**
    * Set lastscanyear of the OperatorDO
    *
    * @param lastscanyear of the OperatorDO
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
      DO.setLastscanyear ( lastscanyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get email of the OperatorDO
    *
    * @return email of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getEmail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEmail ();
   }

   
   /**
    * Set email of the OperatorDO
    *
    * @param email of the OperatorDO
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
      DO.setEmail ( email );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get recertday of the OperatorDO
    *
    * @return recertday of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getRecertday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getRecertday ();
   }

   
   /**
    * Set recertday of the OperatorDO
    *
    * @param recertday of the OperatorDO
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
      DO.setRecertday ( recertday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get recertmonth of the OperatorDO
    *
    * @return recertmonth of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getRecertmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getRecertmonth ();
   }

   
   /**
    * Set recertmonth of the OperatorDO
    *
    * @param recertmonth of the OperatorDO
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
      DO.setRecertmonth ( recertmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get recertyear of the OperatorDO
    *
    * @return recertyear of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getRecertyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getRecertyear ();
   }

   
   /**
    * Set recertyear of the OperatorDO
    *
    * @param recertyear of the OperatorDO
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
      DO.setRecertyear ( recertyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get SFTTS of the OperatorDO
    *
    * @return SFTTS of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Timestamp getSFTTS () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getSFTTS ();
   }

   
   /**
    * Set SFTTS of the OperatorDO
    *
    * @param SFTTS of the OperatorDO
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
      DO.setSFTTS ( SFTTS );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isExp of the OperatorDO
    *
    * @return isExp of the OperatorDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsExp () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsExp ();
   }

   
   /**
    * Set isExp of the OperatorDO
    *
    * @param isExp of the OperatorDO
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
      DO.setIsExp ( isExp );
      afterAnySet();	// business actions/assertions after data assignment
   }


   
    /**
     * Get array of OperatesDO objects that refer to the DO held by this BDO.
     *
     * @return array of OperatesDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.OperatesDO[] getOperatesDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.OperatesDO[] ret = null;
	try {
	    webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	    q.setQueryOperator( DO );
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
     * that refers to the DO held by this BDO.
     *
     * @return OperatesDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one OperatesDO object was found.
     */
    public webschedule.data.OperatesDO getOperatesDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryOperator( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of OperatesBDO objects holding OperatesDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of OperatesBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.OperatesBDO[] getOperatesBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.OperatesBDO[] ret = null;
	try {
	    webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	    q.setQueryOperator( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.OperatesBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single OperatesBDO object holding a OperatesDO object
     * that refers to the DO held by this BDO.
     *
     * @return OperatesBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one OperatesBDO object was found.
     */
    public webschedule.data.OperatesBDO getOperatesBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryOperator( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a OperatesBDO object whose OperatesDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo OperatesBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addOperatesBDO( webschedule.data.OperatesBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addOperatesBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a OperatesBDO object whose OperatesDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo OperatesBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addOperatesBDO( webschedule.data.OperatesBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setOperator( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a OperatesBDO object whose OperatesDO
     * refers to the DO held by this BDO.
     *
     * @param r OperatesBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeOperatesBDO( webschedule.data.OperatesBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeOperatesBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a OperatesBDO object whose OperatesDO
     * refers to the DO held by this BDO.
     *
     * @param r OperatesBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeOperatesBDO( webschedule.data.OperatesBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	OperatorDO rdo = rbdo.getOperator();
	String rdoHandle = rdo.getHandle();
	String mydoHandle = DO.getHandle();
	if ( null == rdoHandle || null == mydoHandle || 
	     ( ! rdoHandle.equals( mydoHandle ) ) ) {
	    throw new DataObjectException( "Object " + rdo +
		" does not refer to object " + DO +
		", cannot be removed this way." );
	}
        rbdo.delete( tran );
    }
 

    /**
     * Get array of S_eventDO objects that refer to the DO held by this BDO.
     *
     * @return array of S_eventDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.S_eventDO[] getS_eventDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.S_eventDO[] ret = null;
	try {
	    webschedule.data.S_eventQuery q = new webschedule.data.S_eventQuery();
	    q.setQueryOperator( DO );
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
     * that refers to the DO held by this BDO.
     *
     * @return S_eventDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one S_eventDO object was found.
     */
    public webschedule.data.S_eventDO getS_eventDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.S_eventQuery q = new webschedule.data.S_eventQuery();
	q.setQueryOperator( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of S_eventBDO objects holding S_eventDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of S_eventBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.S_eventBDO[] getS_eventBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.S_eventBDO[] ret = null;
	try {
	    webschedule.data.S_eventQuery q = new webschedule.data.S_eventQuery();
	    q.setQueryOperator( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.S_eventBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single S_eventBDO object holding a S_eventDO object
     * that refers to the DO held by this BDO.
     *
     * @return S_eventBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one S_eventBDO object was found.
     */
    public webschedule.data.S_eventBDO getS_eventBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.S_eventQuery q = new webschedule.data.S_eventQuery();
	q.setQueryOperator( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a S_eventBDO object whose S_eventDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo S_eventBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addS_eventBDO( webschedule.data.S_eventBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addS_eventBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a S_eventBDO object whose S_eventDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo S_eventBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addS_eventBDO( webschedule.data.S_eventBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setOperator( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a S_eventBDO object whose S_eventDO
     * refers to the DO held by this BDO.
     *
     * @param r S_eventBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeS_eventBDO( webschedule.data.S_eventBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeS_eventBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a S_eventBDO object whose S_eventDO
     * refers to the DO held by this BDO.
     *
     * @param r S_eventBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeS_eventBDO( webschedule.data.S_eventBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	OperatorDO rdo = rbdo.getOperator();
	String rdoHandle = rdo.getHandle();
	String mydoHandle = DO.getHandle();
	if ( null == rdoHandle || null == mydoHandle || 
	     ( ! rdoHandle.equals( mydoHandle ) ) ) {
	    throw new DataObjectException( "Object " + rdo +
		" does not refer to object " + DO +
		", cannot be removed this way." );
	}
        rbdo.delete( tran );
    }
 



    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * get array of ProjectDO objects that indirectly refer
     * to the DO held by this BDO.
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
     * to the DO held by this BDO.
     *
     * @param d The ProjectDO to add to the OperatesDO mapping
     * for this BDO.
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
     * add a ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The ProjectDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapProject_via_OperatesDO( webschedule.data.ProjectDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.ProjectBDO b = webschedule.data.ProjectBDO.createExisting( d );
	mapProject_via_OperatesBDO( b, tran );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The ProjectBDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapProject_via_OperatesBDO( webschedule.data.ProjectBDO b )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	mapProject_via_OperatesBDO( b, null );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The ProjectBDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapProject_via_OperatesBDO( webschedule.data.ProjectBDO b, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesBDO m = null;
	try {
	    m = webschedule.data.OperatesBDO.createVirgin();
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"webschedule.data.OperatesBDO.createVirgin failed", e );
	}
	m.setProject( b );
	m.setOperator( this );
	m.commit( tran );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The ProjectDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapProject_via_OperatesDO( webschedule.data.ProjectDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	unmapProject_via_OperatesDO( d, null );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The ProjectDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapProject_via_OperatesDO( webschedule.data.ProjectDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.ProjectBDO b = webschedule.data.ProjectBDO.createExisting( d );
	unmapProject_via_OperatesBDO( b, tran );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The ProjectBDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapProject_via_OperatesBDO( webschedule.data.ProjectBDO b )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	unmapProject_via_OperatesBDO( b, null );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the ProjectDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The ProjectBDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapProject_via_OperatesBDO( webschedule.data.ProjectBDO b, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryOperator( DO );
	q.setQueryProject( b.getDO() );
	q.requireUniqueInstance();
	webschedule.data.OperatesBDO m = null;
	try {
	    m = q.getNextBDO();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( "Multiple mappings for " +
		DO + " and " + b.getDO() + " in webschedule.data.Operates table." );
	}
	m.delete( tran );
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
	  	
	{
	    // perform cascading delete on referring table
	    webschedule.data.OperatesBDO[] a = getOperatesBDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.S_eventBDO[] a = getS_eventBDOArray();
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
