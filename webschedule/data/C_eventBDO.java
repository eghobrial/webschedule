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
 * /home/emang/myapps/webschedule/webschedule/data/C_eventBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * C_eventBDO contains the same set and get methods as
 * the C_eventDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class C_eventBDO implements java.io.Serializable {

    /**
     * The C_eventDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from C_eventBDO
     * can access the underlying Data Object.
     */
    protected C_eventDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public C_eventDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>C_eventDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static C_eventBDO createVirgin() throws Exception { 
	C_eventBDO bdo = new C_eventBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>C_eventBDO</CODE>.
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
    public C_eventBDO( C_eventDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>C_eventBDO.create</CODE> methods.
     */
    public C_eventBDO() throws Exception { 
	this.DO = C_eventDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>C_eventBDO</CODE>
     * from a <CODE>C_eventDO</CODE> that was returned by 
     * the <CODE>C_eventQuery</CODE> class.
     */
    public static C_eventBDO createExisting( C_eventDO DO ) { 
	C_eventBDO bdo = new C_eventBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>C_eventBDO</CODE> objects
     * representing all the rows in the <CODE>C_event</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>C_eventDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static C_eventBDO[] getBDOarray()
    throws DataObjectException {
        C_eventDO[] DOarray = null;
        try {
            C_eventQuery query = new C_eventQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>C_eventDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // C_eventQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        C_eventBDO[] BDOarray = new C_eventBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = C_eventBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>C_eventBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This C_eventBDO object holds a reference to a C_eventDO object.
     * The id of this C_eventBDO is the id of its C_eventDO.
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
    * Get eventday of the C_eventDO
    *
    * @return eventday of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEventday ();
   }

   
   /**
    * Set eventday of the C_eventDO
    *
    * @param eventday of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEventday ( int eventday ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEventday ( eventday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get eventm of the C_eventDO
    *
    * @return eventm of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEventm ();
   }

   
   /**
    * Set eventm of the C_eventDO
    *
    * @param eventm of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEventm ( int eventm ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEventm ( eventm );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get eventy of the C_eventDO
    *
    * @return eventy of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventy () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEventy ();
   }

   
   /**
    * Set eventy of the C_eventDO
    *
    * @param eventy of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEventy ( int eventy ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEventy ( eventy );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get starth of the C_eventDO
    *
    * @return starth of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStarth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStarth ();
   }

   
   /**
    * Set starth of the C_eventDO
    *
    * @param starth of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStarth ( int starth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStarth ( starth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get startm of the C_eventDO
    *
    * @return startm of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getStartm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getStartm ();
   }

   
   /**
    * Set startm of the C_eventDO
    *
    * @param startm of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setStartm ( int startm ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setStartm ( startm );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get endh of the C_eventDO
    *
    * @return endh of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndh () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEndh ();
   }

   
   /**
    * Set endh of the C_eventDO
    *
    * @param endh of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEndh ( int endh ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEndh ( endh );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get endm of the C_eventDO
    *
    * @return endm of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEndm () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEndm ();
   }

   
   /**
    * Set endm of the C_eventDO
    *
    * @param endm of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEndm ( int endm ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEndm ( endm );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todayd of the C_eventDO
    *
    * @return todayd of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayd () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodayd ();
   }

   
   /**
    * Set todayd of the C_eventDO
    *
    * @param todayd of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodayd ( int todayd ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodayd ( todayd );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todaym of the C_eventDO
    *
    * @return todaym of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodaym () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodaym ();
   }

   
   /**
    * Set todaym of the C_eventDO
    *
    * @param todaym of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodaym ( int todaym ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodaym ( todaym );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get owner of the C_eventDO
    *
    * @return owner of the C_eventDO
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
    * Set owner of the C_eventDO
    *
    * @param owner of the C_eventDO
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
    * Get BDO-wrapped owner of the C_eventDO
    *
    * @return BDO-wrapped owner of the C_eventDO
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
    * Set owner of the C_eventDO
    *
    * @param BDO-wrapped owner of the C_eventDO
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
		  "C_eventBDO.setOwner does not allow NULL." );
      } else {
          DO.setOwner ( owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get proj_owner of the C_eventDO
    *
    * @return proj_owner of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.ProjectDO getProj_owner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProj_owner ();
   }

   
   /**
    * Set proj_owner of the C_eventDO
    *
    * @param proj_owner of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProj_owner ( webschedule.data.ProjectDO proj_owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProj_owner ( proj_owner );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped proj_owner of the C_eventDO
    *
    * @return BDO-wrapped proj_owner of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.ProjectBDO getProj_ownerBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.ProjectBDO b = webschedule.data.ProjectBDO.createExisting(
					  DO.getProj_owner () );
      return b;
   }

   /**
    * Set proj_owner of the C_eventDO
    *
    * @param BDO-wrapped proj_owner of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProj_owner ( webschedule.data.ProjectBDO proj_owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == proj_owner ) {
	  if ( false )
	      DO.setProj_owner ( null );
	  else 
	      throw new DataObjectException( 
		  "C_eventBDO.setProj_owner does not allow NULL." );
      } else {
          DO.setProj_owner ( proj_owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get todayy of the C_eventDO
    *
    * @return todayy of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayy () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodayy ();
   }

   
   /**
    * Set todayy of the C_eventDO
    *
    * @param todayy of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodayy ( int todayy ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodayy ( todayy );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get cancelc of the C_eventDO
    *
    * @return cancelc of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCancelc () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCancelc ();
   }

   
   /**
    * Set cancelc of the C_eventDO
    *
    * @param cancelc of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCancelc ( int cancelc ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCancelc ( cancelc );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todayh of the C_eventDO
    *
    * @return todayh of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayh () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodayh ();
   }

   
   /**
    * Set todayh of the C_eventDO
    *
    * @param todayh of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodayh ( int todayh ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodayh ( todayh );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todaymin of the C_eventDO
    *
    * @return todaymin of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodaymin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodaymin ();
   }

   
   /**
    * Set todaymin of the C_eventDO
    *
    * @param todaymin of the C_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodaymin ( int todaymin ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodaymin ( todaymin );
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
     * for the proj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where proj_owner is not valid for writing to the database.
     */
    protected void okToCommitProj_owner( webschedule.data.ProjectDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the proj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where proj_owner is not valid for deletion from the database.
     */
    protected void okToDeleteProj_owner( webschedule.data.ProjectDO member ) 
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
		    "Cannot commit C_eventBDO ( " + toString() +
		    " ) because owner is not allowed to be null." );
	}
	webschedule.data.ProjectDO proj_owner_DO = DO.getProj_owner();
	if ( null != proj_owner_DO ) {
	    if ( proj_owner_DO.isLoaded() ) {
		okToCommitProj_owner( proj_owner_DO );
		webschedule.data.ProjectBDO b = 
		    webschedule.data.ProjectBDO.createExisting(
						    proj_owner_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit C_eventBDO ( " + toString() +
		    " ) because proj_owner is not allowed to be null." );
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
