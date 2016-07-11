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
 * /home/emang/myapps/webschedule/webschedule/data/ProblemBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * ProblemBDO contains the same set and get methods as
 * the ProblemDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class ProblemBDO implements java.io.Serializable {

    /**
     * The ProblemDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from ProblemBDO
     * can access the underlying Data Object.
     */
    protected ProblemDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public ProblemDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>ProblemDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static ProblemBDO createVirgin() throws Exception { 
	ProblemBDO bdo = new ProblemBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>ProblemBDO</CODE>.
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
    public ProblemBDO( ProblemDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>ProblemBDO.create</CODE> methods.
     */
    public ProblemBDO() throws Exception { 
	this.DO = ProblemDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>ProblemBDO</CODE>
     * from a <CODE>ProblemDO</CODE> that was returned by 
     * the <CODE>ProblemQuery</CODE> class.
     */
    public static ProblemBDO createExisting( ProblemDO DO ) { 
	ProblemBDO bdo = new ProblemBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>ProblemBDO</CODE> objects
     * representing all the rows in the <CODE>Problem</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>ProblemDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static ProblemBDO[] getBDOarray()
    throws DataObjectException {
        ProblemDO[] DOarray = null;
        try {
            ProblemQuery query = new ProblemQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>ProblemDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // ProblemQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        ProblemBDO[] BDOarray = new ProblemBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = ProblemBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>ProblemBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This ProblemBDO object holds a reference to a ProblemDO object.
     * The id of this ProblemBDO is the id of its ProblemDO.
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
    * Get owner of the ProblemDO
    *
    * @return owner of the ProblemDO
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
    * Set owner of the ProblemDO
    *
    * @param owner of the ProblemDO
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
    * Get BDO-wrapped owner of the ProblemDO
    *
    * @return BDO-wrapped owner of the ProblemDO
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
    * Set owner of the ProblemDO
    *
    * @param BDO-wrapped owner of the ProblemDO
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
		  "ProblemBDO.setOwner does not allow NULL." );
      } else {
          DO.setOwner ( owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get severitycode of the ProblemDO
    *
    * @return severitycode of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getSeveritycode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getSeveritycode ();
   }

   
   /**
    * Set severitycode of the ProblemDO
    *
    * @param severitycode of the ProblemDO
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
      DO.setSeveritycode ( severitycode );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get classcode of the ProblemDO
    *
    * @return classcode of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getClasscode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getClasscode ();
   }

   
   /**
    * Set classcode of the ProblemDO
    *
    * @param classcode of the ProblemDO
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
      DO.setClasscode ( classcode );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get prioritycode of the ProblemDO
    *
    * @return prioritycode of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPrioritycode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPrioritycode ();
   }

   
   /**
    * Set prioritycode of the ProblemDO
    *
    * @param prioritycode of the ProblemDO
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
      DO.setPrioritycode ( prioritycode );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get statuscode of the ProblemDO
    *
    * @return statuscode of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStatuscode () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStatuscode ();
   }

   
   /**
    * Set statuscode of the ProblemDO
    *
    * @param statuscode of the ProblemDO
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
      DO.setStatuscode ( statuscode );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get postday of the ProblemDO
    *
    * @return postday of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPostday ();
   }

   
   /**
    * Set postday of the ProblemDO
    *
    * @param postday of the ProblemDO
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
      DO.setPostday ( postday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get postmonth of the ProblemDO
    *
    * @return postmonth of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPostmonth ();
   }

   
   /**
    * Set postmonth of the ProblemDO
    *
    * @param postmonth of the ProblemDO
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
      DO.setPostmonth ( postmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get postyear of the ProblemDO
    *
    * @return postyear of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPostyear ();
   }

   
   /**
    * Set postyear of the ProblemDO
    *
    * @param postyear of the ProblemDO
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
      DO.setPostyear ( postyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get updateday of the ProblemDO
    *
    * @return updateday of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getUpdateday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getUpdateday ();
   }

   
   /**
    * Set updateday of the ProblemDO
    *
    * @param updateday of the ProblemDO
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
      DO.setUpdateday ( updateday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get updatemonth of the ProblemDO
    *
    * @return updatemonth of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getUpdatemonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getUpdatemonth ();
   }

   
   /**
    * Set updatemonth of the ProblemDO
    *
    * @param updatemonth of the ProblemDO
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
      DO.setUpdatemonth ( updatemonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get updateyear of the ProblemDO
    *
    * @return updateyear of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getUpdateyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getUpdateyear ();
   }

   
   /**
    * Set updateyear of the ProblemDO
    *
    * @param updateyear of the ProblemDO
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
      DO.setUpdateyear ( updateyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get closeday of the ProblemDO
    *
    * @return closeday of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCloseday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCloseday ();
   }

   
   /**
    * Set closeday of the ProblemDO
    *
    * @param closeday of the ProblemDO
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
      DO.setCloseday ( closeday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get closemonth of the ProblemDO
    *
    * @return closemonth of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getClosemonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getClosemonth ();
   }

   
   /**
    * Set closemonth of the ProblemDO
    *
    * @param closemonth of the ProblemDO
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
      DO.setClosemonth ( closemonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get closeyear of the ProblemDO
    *
    * @return closeyear of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCloseyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCloseyear ();
   }

   
   /**
    * Set closeyear of the ProblemDO
    *
    * @param closeyear of the ProblemDO
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
      DO.setCloseyear ( closeyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get reportername of the ProblemDO
    *
    * @return reportername of the ProblemDO
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
    * Set reportername of the ProblemDO
    *
    * @param reportername of the ProblemDO
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
    * Get reporteremail of the ProblemDO
    *
    * @return reporteremail of the ProblemDO
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
    * Set reporteremail of the ProblemDO
    *
    * @param reporteremail of the ProblemDO
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
    * Get describ of the ProblemDO
    *
    * @return describ of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDescrib () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getDescrib ();
   }

   
   /**
    * Set describ of the ProblemDO
    *
    * @param describ of the ProblemDO
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
      DO.setDescrib ( describ );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get GE_called of the ProblemDO
    *
    * @return GE_called of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getGE_called () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getGE_called ();
   }

   
   /**
    * Set GE_called of the ProblemDO
    *
    * @param GE_called of the ProblemDO
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
      DO.setGE_called ( GE_called );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get problemnum of the ProblemDO
    *
    * @return problemnum of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProblemnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProblemnum ();
   }

   
   /**
    * Set problemnum of the ProblemDO
    *
    * @param problemnum of the ProblemDO
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
      DO.setProblemnum ( problemnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get posth of the ProblemDO
    *
    * @return posth of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPosth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPosth ();
   }

   
   /**
    * Set posth of the ProblemDO
    *
    * @param posth of the ProblemDO
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
      DO.setPosth ( posth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get postm of the ProblemDO
    *
    * @return postm of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getPostm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPostm ();
   }

   
   /**
    * Set postm of the ProblemDO
    *
    * @param postm of the ProblemDO
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
      DO.setPostm ( postm );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get postpm of the ProblemDO
    *
    * @return postpm of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPostpm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPostpm ();
   }

   
   /**
    * Set postpm of the ProblemDO
    *
    * @param postpm of the ProblemDO
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
      DO.setPostpm ( postpm );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get problemdetail of the ProblemDO
    *
    * @return problemdetail of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProblemdetail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProblemdetail ();
   }

   
   /**
    * Set problemdetail of the ProblemDO
    *
    * @param problemdetail of the ProblemDO
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
      DO.setProblemdetail ( problemdetail );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get fixdetail of the ProblemDO
    *
    * @return fixdetail of the ProblemDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFixdetail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFixdetail ();
   }

   
   /**
    * Set fixdetail of the ProblemDO
    *
    * @param fixdetail of the ProblemDO
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
      DO.setFixdetail ( fixdetail );
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
		    "Cannot commit ProblemBDO ( " + toString() +
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
