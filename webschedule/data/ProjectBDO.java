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
 * /home/emang/myapps/webschedule/webschedule/data/ProjectBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * ProjectBDO contains the same set and get methods as
 * the ProjectDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class ProjectBDO implements java.io.Serializable {

    /**
     * The ProjectDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from ProjectBDO
     * can access the underlying Data Object.
     */
    protected ProjectDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public ProjectDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>ProjectDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static ProjectBDO createVirgin() throws Exception { 
	ProjectBDO bdo = new ProjectBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>ProjectBDO</CODE>.
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
    public ProjectBDO( ProjectDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>ProjectBDO.create</CODE> methods.
     */
    public ProjectBDO() throws Exception { 
	this.DO = ProjectDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>ProjectBDO</CODE>
     * from a <CODE>ProjectDO</CODE> that was returned by 
     * the <CODE>ProjectQuery</CODE> class.
     */
    public static ProjectBDO createExisting( ProjectDO DO ) { 
	ProjectBDO bdo = new ProjectBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>ProjectBDO</CODE> objects
     * representing all the rows in the <CODE>Project</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>ProjectDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static ProjectBDO[] getBDOarray()
    throws DataObjectException {
        ProjectDO[] DOarray = null;
        try {
            ProjectQuery query = new ProjectQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>ProjectDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // ProjectQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        ProjectBDO[] BDOarray = new ProjectBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = ProjectBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>ProjectBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This ProjectBDO object holds a reference to a ProjectDO object.
     * The id of this ProjectBDO is the id of its ProjectDO.
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
    * Get proj_name of the ProjectDO
    *
    * @return proj_name of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProj_name () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProj_name ();
   }

   
   /**
    * Set proj_name of the ProjectDO
    *
    * @param proj_name of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProj_name ( String proj_name ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProj_name ( proj_name );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Description of the ProjectDO
    *
    * @return Description of the ProjectDO
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
    * Set Description of the ProjectDO
    *
    * @param Description of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setDescription ( String Description ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setDescription ( Description );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get indexnum of the ProjectDO
    *
    * @return indexnum of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIndexnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIndexnum ();
   }

   
   /**
    * Set indexnum of the ProjectDO
    *
    * @param indexnum of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIndexnum ( String indexnum ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIndexnum ( indexnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get codeofpay of the ProjectDO
    *
    * @return codeofpay of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCodeofpay () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCodeofpay ();
   }

   
   /**
    * Set codeofpay of the ProjectDO
    *
    * @param codeofpay of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCodeofpay ( int codeofpay ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCodeofpay ( codeofpay );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get password of the ProjectDO
    *
    * @return password of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPassword () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPassword ();
   }

   
   /**
    * Set password of the ProjectDO
    *
    * @param password of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPassword ( String password ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPassword ( password );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get totalhours of the ProjectDO
    *
    * @return totalhours of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public double getTotalhours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getTotalhours ();
   }

   
   /**
    * Set totalhours of the ProjectDO
    *
    * @param totalhours of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setTotalhours ( double totalhours ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setTotalhours ( totalhours );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get donehours of the ProjectDO
    *
    * @return donehours of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public double getDonehours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getDonehours ();
   }

   
   /**
    * Set donehours of the ProjectDO
    *
    * @param donehours of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setDonehours ( double donehours ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setDonehours ( donehours );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get owner of the ProjectDO
    *
    * @return owner of the ProjectDO
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
    * Set owner of the ProjectDO
    *
    * @param owner of the ProjectDO
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
    * Get BDO-wrapped owner of the ProjectDO
    *
    * @return BDO-wrapped owner of the ProjectDO
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
    * Set owner of the ProjectDO
    *
    * @param BDO-wrapped owner of the ProjectDO
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
		  "ProjectBDO.setOwner does not allow NULL." );
      } else {
          DO.setOwner ( owner.getDO() );
      }
      afterAnySet();	// business actions/assertions after data assignment
   }
   

   

   /**
    * Get contactname of the ProjectDO
    *
    * @return contactname of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getContactname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getContactname ();
   }

   
   /**
    * Set contactname of the ProjectDO
    *
    * @param contactname of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setContactname ( String contactname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setContactname ( contactname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get contactphone of the ProjectDO
    *
    * @return contactphone of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getContactphone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getContactphone ();
   }

   
   /**
    * Set contactphone of the ProjectDO
    *
    * @param contactphone of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setContactphone ( String contactphone ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setContactphone ( contactphone );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get billaddr1 of the ProjectDO
    *
    * @return billaddr1 of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBilladdr1 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBilladdr1 ();
   }

   
   /**
    * Set billaddr1 of the ProjectDO
    *
    * @param billaddr1 of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBilladdr1 ( String billaddr1 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBilladdr1 ( billaddr1 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get billaddr2 of the ProjectDO
    *
    * @return billaddr2 of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBilladdr2 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBilladdr2 ();
   }

   
   /**
    * Set billaddr2 of the ProjectDO
    *
    * @param billaddr2 of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBilladdr2 ( String billaddr2 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBilladdr2 ( billaddr2 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get billaddr3 of the ProjectDO
    *
    * @return billaddr3 of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getBilladdr3 () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getBilladdr3 ();
   }

   
   /**
    * Set billaddr3 of the ProjectDO
    *
    * @param billaddr3 of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setBilladdr3 ( String billaddr3 ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setBilladdr3 ( billaddr3 );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get city of the ProjectDO
    *
    * @return city of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getCity () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCity ();
   }

   
   /**
    * Set city of the ProjectDO
    *
    * @param city of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCity ( String city ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCity ( city );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get state of the ProjectDO
    *
    * @return state of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getState () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getState ();
   }

   
   /**
    * Set state of the ProjectDO
    *
    * @param state of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setState ( String state ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setState ( state );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get zip of the ProjectDO
    *
    * @return zip of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getZip () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getZip ();
   }

   
   /**
    * Set zip of the ProjectDO
    *
    * @param zip of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setZip ( String zip ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setZip ( zip );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get accountid of the ProjectDO
    *
    * @return accountid of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getAccountid () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getAccountid ();
   }

   
   /**
    * Set accountid of the ProjectDO
    *
    * @param accountid of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setAccountid ( String accountid ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setAccountid ( accountid );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isoutside of the ProjectDO
    *
    * @return isoutside of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsoutside () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsoutside ();
   }

   
   /**
    * Set isoutside of the ProjectDO
    *
    * @param isoutside of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsoutside ( boolean isoutside ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsoutside ( isoutside );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get expday of the ProjectDO
    *
    * @return expday of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getExpday () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getExpday ();
   }

   
   /**
    * Set expday of the ProjectDO
    *
    * @param expday of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setExpday ( int expday ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setExpday ( expday );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get expmonth of the ProjectDO
    *
    * @return expmonth of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getExpmonth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getExpmonth ();
   }

   
   /**
    * Set expmonth of the ProjectDO
    *
    * @param expmonth of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setExpmonth ( int expmonth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setExpmonth ( expmonth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get expyear of the ProjectDO
    *
    * @return expyear of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getExpyear () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getExpyear ();
   }

   
   /**
    * Set expyear of the ProjectDO
    *
    * @param expyear of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setExpyear ( int expyear ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setExpyear ( expyear );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get notifycontact of the ProjectDO
    *
    * @return notifycontact of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getNotifycontact () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getNotifycontact ();
   }

   
   /**
    * Set notifycontact of the ProjectDO
    *
    * @param notifycontact of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setNotifycontact ( String notifycontact ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setNotifycontact ( notifycontact );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isExp of the ProjectDO
    *
    * @return isExp of the ProjectDO
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
    * Set isExp of the ProjectDO
    *
    * @param isExp of the ProjectDO
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
    * Get canCredit of the ProjectDO
    *
    * @return canCredit of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public int getCanCredit () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getCanCredit ();
   }

   
   /**
    * Set canCredit of the ProjectDO
    *
    * @param canCredit of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setCanCredit ( int canCredit ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setCanCredit ( canCredit );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Institute of the ProjectDO
    *
    * @return Institute of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getInstitute () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getInstitute ();
   }

   
   /**
    * Set Institute of the ProjectDO
    *
    * @param Institute of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setInstitute ( String Institute ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setInstitute ( Institute );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get proposalID of the ProjectDO
    *
    * @return proposalID of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getProposalID () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getProposalID ();
   }

   
   /**
    * Set proposalID of the ProjectDO
    *
    * @param proposalID of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setProposalID ( String proposalID ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setProposalID ( proposalID );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get fpemail of the ProjectDO
    *
    * @return fpemail of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFpemail () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFpemail ();
   }

   
   /**
    * Set fpemail of the ProjectDO
    *
    * @param fpemail of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFpemail ( String fpemail ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFpemail ( fpemail );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get POnum of the ProjectDO
    *
    * @return POnum of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPOnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPOnum ();
   }

   
   /**
    * Set POnum of the ProjectDO
    *
    * @param POnum of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPOnum ( String POnum ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPOnum ( POnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get POexpdate of the ProjectDO
    *
    * @return POexpdate of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getPOexpdate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPOexpdate ();
   }

   
   /**
    * Set POexpdate of the ProjectDO
    *
    * @param POexpdate of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPOexpdate ( java.sql.Date POexpdate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPOexpdate ( POexpdate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get POhours of the ProjectDO
    *
    * @return POhours of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public double getPOhours () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPOhours ();
   }

   
   /**
    * Set POhours of the ProjectDO
    *
    * @param POhours of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPOhours ( double POhours ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPOhours ( POhours );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get IACUCDate of the ProjectDO
    *
    * @return IACUCDate of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getIACUCDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIACUCDate ();
   }

   
   /**
    * Set IACUCDate of the ProjectDO
    *
    * @param IACUCDate of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIACUCDate ( java.sql.Date IACUCDate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIACUCDate ( IACUCDate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Modifiedby of the ProjectDO
    *
    * @return Modifiedby of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getModifiedby () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getModifiedby ();
   }

   
   /**
    * Set Modifiedby of the ProjectDO
    *
    * @param Modifiedby of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setModifiedby ( String Modifiedby ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setModifiedby ( Modifiedby );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get ModDate of the ProjectDO
    *
    * @return ModDate of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public java.sql.Date getModDate () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getModDate ();
   }

   
   /**
    * Set ModDate of the ProjectDO
    *
    * @param ModDate of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setModDate ( java.sql.Date ModDate ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setModDate ( ModDate );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get Notes of the ProjectDO
    *
    * @return Notes of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getNotes () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getNotes ();
   }

   
   /**
    * Set Notes of the ProjectDO
    *
    * @param Notes of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setNotes ( String Notes ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setNotes ( Notes );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get IRBnum of the ProjectDO
    *
    * @return IRBnum of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getIRBnum () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIRBnum ();
   }

   
   /**
    * Set IRBnum of the ProjectDO
    *
    * @param IRBnum of the ProjectDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIRBnum ( String IRBnum ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIRBnum ( IRBnum );
      afterAnySet();	// business actions/assertions after data assignment
   }


   
    /**
     * Get array of C_eventDO objects that refer to the DO held by this BDO.
     *
     * @return array of C_eventDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.C_eventDO[] getC_eventDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.C_eventDO[] ret = null;
	try {
	    webschedule.data.C_eventQuery q = new webschedule.data.C_eventQuery();
	    q.setQueryProj_owner( DO );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.C_eventDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single C_eventDO object
     * that refers to the DO held by this BDO.
     *
     * @return C_eventDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one C_eventDO object was found.
     */
    public webschedule.data.C_eventDO getC_eventDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.C_eventQuery q = new webschedule.data.C_eventQuery();
	q.setQueryProj_owner( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of C_eventBDO objects holding C_eventDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of C_eventBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.C_eventBDO[] getC_eventBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.C_eventBDO[] ret = null;
	try {
	    webschedule.data.C_eventQuery q = new webschedule.data.C_eventQuery();
	    q.setQueryProj_owner( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.C_eventBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single C_eventBDO object holding a C_eventDO object
     * that refers to the DO held by this BDO.
     *
     * @return C_eventBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one C_eventBDO object was found.
     */
    public webschedule.data.C_eventBDO getC_eventBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.C_eventQuery q = new webschedule.data.C_eventQuery();
	q.setQueryProj_owner( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a C_eventBDO object whose C_eventDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo C_eventBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addC_eventBDO( webschedule.data.C_eventBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addC_eventBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a C_eventBDO object whose C_eventDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo C_eventBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addC_eventBDO( webschedule.data.C_eventBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setProj_owner( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a C_eventBDO object whose C_eventDO
     * refers to the DO held by this BDO.
     *
     * @param r C_eventBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeC_eventBDO( webschedule.data.C_eventBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeC_eventBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a C_eventBDO object whose C_eventDO
     * refers to the DO held by this BDO.
     *
     * @param r C_eventBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeC_eventBDO( webschedule.data.C_eventBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	ProjectDO rdo = rbdo.getProj_owner();
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
	    q.setQueryProject( DO );
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
	q.setQueryProject( DO );
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
	    q.setQueryProject( DO );
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
	q.setQueryProject( DO );
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
        rbdo.setProject( this.DO );
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
	ProjectDO rdo = rbdo.getProject();
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
     * Get array of R_eventDO objects that refer to the DO held by this BDO.
     *
     * @return array of R_eventDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.R_eventDO[] getR_eventDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.R_eventDO[] ret = null;
	try {
	    webschedule.data.R_eventQuery q = new webschedule.data.R_eventQuery();
	    q.setQueryProj_owner( DO );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.R_eventDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single R_eventDO object
     * that refers to the DO held by this BDO.
     *
     * @return R_eventDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one R_eventDO object was found.
     */
    public webschedule.data.R_eventDO getR_eventDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.R_eventQuery q = new webschedule.data.R_eventQuery();
	q.setQueryProj_owner( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of R_eventBDO objects holding R_eventDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of R_eventBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.R_eventBDO[] getR_eventBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.R_eventBDO[] ret = null;
	try {
	    webschedule.data.R_eventQuery q = new webschedule.data.R_eventQuery();
	    q.setQueryProj_owner( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.R_eventBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single R_eventBDO object holding a R_eventDO object
     * that refers to the DO held by this BDO.
     *
     * @return R_eventBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one R_eventBDO object was found.
     */
    public webschedule.data.R_eventBDO getR_eventBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.R_eventQuery q = new webschedule.data.R_eventQuery();
	q.setQueryProj_owner( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a R_eventBDO object whose R_eventDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo R_eventBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addR_eventBDO( webschedule.data.R_eventBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addR_eventBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a R_eventBDO object whose R_eventDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo R_eventBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addR_eventBDO( webschedule.data.R_eventBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setProj_owner( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a R_eventBDO object whose R_eventDO
     * refers to the DO held by this BDO.
     *
     * @param r R_eventBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeR_eventBDO( webschedule.data.R_eventBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeR_eventBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a R_eventBDO object whose R_eventDO
     * refers to the DO held by this BDO.
     *
     * @param r R_eventBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeR_eventBDO( webschedule.data.R_eventBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	ProjectDO rdo = rbdo.getProj_owner();
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
	    q.setQueryProj_owner( DO );
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
	q.setQueryProj_owner( DO );
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
	    q.setQueryProj_owner( DO );
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
	q.setQueryProj_owner( DO );
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
        rbdo.setProj_owner( this.DO );
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
	ProjectDO rdo = rbdo.getProj_owner();
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
     * get array of OperatorDO objects that indirectly refer
     * to the DO held by this BDO.
     *
     * @return array of OperatorDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.OperatorDO[] getOperatorDOArray_via_Operates () 
    throws DataObjectException {
	webschedule.data.OperatorDO[] ret = null;
	try {
	    webschedule.data.OperatesDO[] arr = getOperatesDOArray();
	    ret = new webschedule.data.OperatorDO[ arr.length ];
	    for ( int i = 0; i < arr.length; i++ ) {
		ret[ i ] = arr[ i ].getOperator();
	    }
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: ", e );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.OperatorDO[ 0 ];
	}
	return ret;
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The OperatorDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapOperator_via_OperatesDO( webschedule.data.OperatorDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	mapOperator_via_OperatesDO( d, null );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The OperatorDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapOperator_via_OperatesDO( webschedule.data.OperatorDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatorBDO b = webschedule.data.OperatorBDO.createExisting( d );
	mapOperator_via_OperatesBDO( b, tran );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The OperatorBDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapOperator_via_OperatesBDO( webschedule.data.OperatorBDO b )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	mapOperator_via_OperatesBDO( b, null );
    }

    /**
     * To the many-to-many relationship expressed by OperatesDO,
     * add a OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The OperatorBDO to add to the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void mapOperator_via_OperatesBDO( webschedule.data.OperatorBDO b, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesBDO m = null;
	try {
	    m = webschedule.data.OperatesBDO.createVirgin();
	} catch ( Exception e ) { 
	    throw new DataObjectException( 
		"webschedule.data.OperatesBDO.createVirgin failed", e );
	}
	m.setOperator( b );
	m.setProject( this );
	m.commit( tran );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The OperatorDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapOperator_via_OperatesDO( webschedule.data.OperatorDO d )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	unmapOperator_via_OperatesDO( d, null );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param d The OperatorDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapOperator_via_OperatesDO( webschedule.data.OperatorDO d, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatorBDO b = webschedule.data.OperatorBDO.createExisting( d );
	unmapOperator_via_OperatesBDO( b, tran );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The OperatorBDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapOperator_via_OperatesBDO( webschedule.data.OperatorBDO b )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	unmapOperator_via_OperatesBDO( b, null );
    }

    /**
     * From the many-to-many relationship expressed by OperatesDO,
     * remove (delete) the OperatorDO object that indirectly refers
     * to the DO held by this BDO.
     *
     * @param b The OperatorBDO to remove from the OperatesDO mapping
     * for this BDO.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void unmapOperator_via_OperatesBDO( webschedule.data.OperatorBDO b, DBTransaction tran )
    throws DataObjectException, DatabaseManagerException, RefAssertionException, SQLException, DBRowUpdateException, QueryException {
	webschedule.data.OperatesQuery q = new webschedule.data.OperatesQuery();
	q.setQueryProject( DO );
	q.setQueryOperator( b.getDO() );
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
	  	
	{
	    // perform cascading delete on referring table
	    webschedule.data.C_eventBDO[] a = getC_eventBDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.OperatesBDO[] a = getOperatesBDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.R_eventBDO[] a = getR_eventBDOArray();
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
		    "Cannot commit ProjectBDO ( " + toString() +
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
