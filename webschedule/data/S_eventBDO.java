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
 * /home/emang/myapps/webschedule/webschedule/data/S_eventBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * S_eventBDO contains the same set and get methods as
 * the S_eventDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class S_eventBDO implements java.io.Serializable {

    /**
     * The S_eventDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from S_eventBDO
     * can access the underlying Data Object.
     */
    protected S_eventDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public S_eventDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>S_eventDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static S_eventBDO createVirgin() throws Exception { 
	S_eventBDO bdo = new S_eventBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>S_eventBDO</CODE>.
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
    public S_eventBDO( S_eventDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>S_eventBDO.create</CODE> methods.
     */
    public S_eventBDO() throws Exception { 
	this.DO = S_eventDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>S_eventBDO</CODE>
     * from a <CODE>S_eventDO</CODE> that was returned by 
     * the <CODE>S_eventQuery</CODE> class.
     */
    public static S_eventBDO createExisting( S_eventDO DO ) { 
	S_eventBDO bdo = new S_eventBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>S_eventBDO</CODE> objects
     * representing all the rows in the <CODE>S_event</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>S_eventDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static S_eventBDO[] getBDOarray()
    throws DataObjectException {
        S_eventDO[] DOarray = null;
        try {
            S_eventQuery query = new S_eventQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>S_eventDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // S_eventQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        S_eventBDO[] BDOarray = new S_eventBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = S_eventBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>S_eventBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This S_eventBDO object holds a reference to a S_eventDO object.
     * The id of this S_eventBDO is the id of its S_eventDO.
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
    * Get description of the S_eventDO
    *
    * @return description of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getDescription () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getDescription ();
   }

   
   /**
    * Set description of the S_eventDO
    *
    * @param description of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setDescription ( String description ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setDescription ( description );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get starth of the S_eventDO
    *
    * @return starth of the S_eventDO
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
    * Set starth of the S_eventDO
    *
    * @param starth of the S_eventDO
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
    * Get startm of the S_eventDO
    *
    * @return startm of the S_eventDO
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
    * Set startm of the S_eventDO
    *
    * @param startm of the S_eventDO
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
    * Get endh of the S_eventDO
    *
    * @return endh of the S_eventDO
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
    * Set endh of the S_eventDO
    *
    * @param endh of the S_eventDO
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
    * Get endm of the S_eventDO
    *
    * @return endm of the S_eventDO
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
    * Set endm of the S_eventDO
    *
    * @param endm of the S_eventDO
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
    * Get eventday of the S_eventDO
    *
    * @return eventday of the S_eventDO
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
    * Set eventday of the S_eventDO
    *
    * @param eventday of the S_eventDO
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
    * Get eventmonth of the S_eventDO
    *
    * @return eventmonth of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEventmonth ();
   }

   
   /**
    * Set eventmonth of the S_eventDO
    *
    * @param eventmonth of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEventmonth ( int eventmonth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEventmonth ( eventmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get eventyear of the S_eventDO
    *
    * @return eventyear of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEventyear ();
   }

   
   /**
    * Set eventyear of the S_eventDO
    *
    * @param eventyear of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEventyear ( int eventyear ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEventyear ( eventyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get eventdayofw of the S_eventDO
    *
    * @return eventdayofw of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getEventdayofw () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getEventdayofw ();
   }

   
   /**
    * Set eventdayofw of the S_eventDO
    *
    * @param eventdayofw of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setEventdayofw ( int eventdayofw ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setEventdayofw ( eventdayofw );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get owner of the S_eventDO
    *
    * @return owner of the S_eventDO
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
    * Set owner of the S_eventDO
    *
    * @param owner of the S_eventDO
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
    * Get BDO-wrapped owner of the S_eventDO
    *
    * @return BDO-wrapped owner of the S_eventDO
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
    * Set owner of the S_eventDO
    *
    * @param BDO-wrapped owner of the S_eventDO
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
		  "S_eventBDO.setOwner does not allow NULL." );
      } else {
          DO.setOwner ( owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get proj_owner of the S_eventDO
    *
    * @return proj_owner of the S_eventDO
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
    * Set proj_owner of the S_eventDO
    *
    * @param proj_owner of the S_eventDO
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
    * Get BDO-wrapped proj_owner of the S_eventDO
    *
    * @return BDO-wrapped proj_owner of the S_eventDO
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
    * Set proj_owner of the S_eventDO
    *
    * @param BDO-wrapped proj_owner of the S_eventDO
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
		  "S_eventBDO.setProj_owner does not allow NULL." );
      } else {
          DO.setProj_owner ( proj_owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get todayday of the S_eventDO
    *
    * @return todayday of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodayday ();
   }

   
   /**
    * Set todayday of the S_eventDO
    *
    * @param todayday of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodayday ( int todayday ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodayday ( todayday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todaymonth of the S_eventDO
    *
    * @return todaymonth of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodaymonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodaymonth ();
   }

   
   /**
    * Set todaymonth of the S_eventDO
    *
    * @param todaymonth of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodaymonth ( int todaymonth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodaymonth ( todaymonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todayyear of the S_eventDO
    *
    * @return todayyear of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getTodayyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTodayyear ();
   }

   
   /**
    * Set todayyear of the S_eventDO
    *
    * @param todayyear of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTodayyear ( int todayyear ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTodayyear ( todayyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isDevelop of the S_eventDO
    *
    * @return isDevelop of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsDevelop () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsDevelop ();
   }

   
   /**
    * Set isDevelop of the S_eventDO
    *
    * @param isDevelop of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsDevelop ( boolean isDevelop ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsDevelop ( isDevelop );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isRepeat of the S_eventDO
    *
    * @return isRepeat of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsRepeat () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsRepeat ();
   }

   
   /**
    * Set isRepeat of the S_eventDO
    *
    * @param isRepeat of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsRepeat ( boolean isRepeat ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsRepeat ( isRepeat );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Operator of the S_eventDO
    *
    * @return Operator of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.OperatorDO getOperator () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOperator ();
   }

   
   /**
    * Set Operator of the S_eventDO
    *
    * @param Operator of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOperator ( webschedule.data.OperatorDO Operator ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOperator ( Operator );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped Operator of the S_eventDO
    *
    * @return BDO-wrapped Operator of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.OperatorBDO getOperatorBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.OperatorBDO b = webschedule.data.OperatorBDO.createExisting(
					  DO.getOperator () );
      return b;
   }

   /**
    * Set Operator of the S_eventDO
    *
    * @param BDO-wrapped Operator of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOperator ( webschedule.data.OperatorBDO Operator ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == Operator ) {
	  if ( false )
	      DO.setOperator ( null );
	  else 
	      throw new DataObjectException( 
		  "S_eventBDO.setOperator does not allow NULL." );
      } else {
          DO.setOperator ( Operator.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get prevowner of the S_eventDO
    *
    * @return prevowner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonDO getPrevowner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPrevowner ();
   }

   
   /**
    * Set prevowner of the S_eventDO
    *
    * @param prevowner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPrevowner ( webschedule.data.PersonDO prevowner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPrevowner ( prevowner );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped prevowner of the S_eventDO
    *
    * @return BDO-wrapped prevowner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.PersonBDO getPrevownerBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.PersonBDO b = webschedule.data.PersonBDO.createExisting(
					  DO.getPrevowner () );
      return b;
   }

   /**
    * Set prevowner of the S_eventDO
    *
    * @param BDO-wrapped prevowner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPrevowner ( webschedule.data.PersonBDO prevowner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == prevowner ) {
	  if ( false )
	      DO.setPrevowner ( null );
	  else 
	      throw new DataObjectException( 
		  "S_eventBDO.setPrevowner does not allow NULL." );
      } else {
          DO.setPrevowner ( prevowner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get prevproj_owner of the S_eventDO
    *
    * @return prevproj_owner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.ProjectDO getPrevproj_owner () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPrevproj_owner ();
   }

   
   /**
    * Set prevproj_owner of the S_eventDO
    *
    * @param prevproj_owner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPrevproj_owner ( webschedule.data.ProjectDO prevproj_owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPrevproj_owner ( prevproj_owner );
      afterAnySet();	// business actions/assertions after data assignment
   }

   

   /**
    * Get BDO-wrapped prevproj_owner of the S_eventDO
    *
    * @return BDO-wrapped prevproj_owner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public webschedule.data.ProjectBDO getPrevproj_ownerBDO () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      webschedule.data.ProjectBDO b = webschedule.data.ProjectBDO.createExisting(
					  DO.getPrevproj_owner () );
      return b;
   }

   /**
    * Set prevproj_owner of the S_eventDO
    *
    * @param BDO-wrapped prevproj_owner of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPrevproj_owner ( webschedule.data.ProjectBDO prevproj_owner ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      if ( null == prevproj_owner ) {
	  if ( false )
	      DO.setPrevproj_owner ( null );
	  else 
	      throw new DataObjectException( 
		  "S_eventBDO.setPrevproj_owner does not allow NULL." );
      } else {
          DO.setPrevproj_owner ( prevproj_owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get IsGrabbable of the S_eventDO
    *
    * @return IsGrabbable of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsGrabbable () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsGrabbable ();
   }

   
   /**
    * Set IsGrabbable of the S_eventDO
    *
    * @param IsGrabbable of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsGrabbable ( boolean IsGrabbable ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsGrabbable ( IsGrabbable );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get reasondropped of the S_eventDO
    *
    * @return reasondropped of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getReasondropped () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getReasondropped ();
   }

   
   /**
    * Set reasondropped of the S_eventDO
    *
    * @param reasondropped of the S_eventDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setReasondropped ( String reasondropped ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setReasondropped ( reasondropped );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get todayh of the S_eventDO
    *
    * @return todayh of the S_eventDO
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
    * Set todayh of the S_eventDO
    *
    * @param todayh of the S_eventDO
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
    * Get todaym of the S_eventDO
    *
    * @return todaym of the S_eventDO
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
    * Set todaym of the S_eventDO
    *
    * @param todaym of the S_eventDO
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
     * A stub method for implementing pre-commit assertions 
     * for the Operator data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where Operator is not valid for writing to the database.
     */
    protected void okToCommitOperator( webschedule.data.OperatorDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the Operator data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where Operator is not valid for deletion from the database.
     */
    protected void okToDeleteOperator( webschedule.data.OperatorDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-commit assertions 
     * for the prevowner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevowner is not valid for writing to the database.
     */
    protected void okToCommitPrevowner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the prevowner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevowner is not valid for deletion from the database.
     */
    protected void okToDeletePrevowner( webschedule.data.PersonDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-commit assertions 
     * for the prevproj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevproj_owner is not valid for writing to the database.
     */
    protected void okToCommitPrevproj_owner( webschedule.data.ProjectDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the prevproj_owner data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where prevproj_owner is not valid for deletion from the database.
     */
    protected void okToDeletePrevproj_owner( webschedule.data.ProjectDO member ) 
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
		    "Cannot commit S_eventBDO ( " + toString() +
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
		    "Cannot commit S_eventBDO ( " + toString() +
		    " ) because proj_owner is not allowed to be null." );
	}
	webschedule.data.OperatorDO Operator_DO = DO.getOperator();
	if ( null != Operator_DO ) {
	    if ( Operator_DO.isLoaded() ) {
		okToCommitOperator( Operator_DO );
		webschedule.data.OperatorBDO b = 
		    webschedule.data.OperatorBDO.createExisting(
						    Operator_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit S_eventBDO ( " + toString() +
		    " ) because Operator is not allowed to be null." );
	}
	webschedule.data.PersonDO prevowner_DO = DO.getPrevowner();
	if ( null != prevowner_DO ) {
	    if ( prevowner_DO.isLoaded() ) {
		okToCommitPrevowner( prevowner_DO );
		webschedule.data.PersonBDO b = 
		    webschedule.data.PersonBDO.createExisting(
						    prevowner_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit S_eventBDO ( " + toString() +
		    " ) because prevowner is not allowed to be null." );
	}
	webschedule.data.ProjectDO prevproj_owner_DO = DO.getPrevproj_owner();
	if ( null != prevproj_owner_DO ) {
	    if ( prevproj_owner_DO.isLoaded() ) {
		okToCommitPrevproj_owner( prevproj_owner_DO );
		webschedule.data.ProjectBDO b = 
		    webschedule.data.ProjectBDO.createExisting(
						    prevproj_owner_DO );
		b.commit( dbt );
	    } else {
		// since the referenced DO is not loaded,
		// it cannot be dirty, so there is no need to commit it.
	    }
	} else {
	    if ( ! false )
		throw new RefAssertionException(
		    "Cannot commit S_eventBDO ( " + toString() +
		    " ) because prevproj_owner is not allowed to be null." );
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
