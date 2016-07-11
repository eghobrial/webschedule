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
 * /home/emang/myapps/webschedule/webschedule/data/PersonBDO.java
 *-----------------------------------------------------------------------------
 */

package webschedule.data;

import java.sql.*;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;

import com.lutris.dods.builder.generator.query.*;

/**
 * PersonBDO contains the same set and get methods as
 * the PersonDO class.
 * Business Object (BO) classes typically need these set and get methods.
 * So by deriving a BO from a BDO, or by implementing a BO that 
 * contains a BDO, the developer of the BO is spared some work.
 *
 * @author emang
 * @version $Revision: 1.5 $
 */
public class PersonBDO implements java.io.Serializable {

    /**
     * The PersonDO object upon which the set and get methods operate.
     * This member is protected so that classes derived from PersonBDO
     * can access the underlying Data Object.
     */
    protected PersonDO DO;

    /**
     * Note:  This method is intended for use only by other BDO classes.
     * Presentation Layer classes should (theoretically) always use the
     * Business Layer (BDO) to create/access Data Layer (DO) objects.
     * The overhead for using BDO objects is small
     * (the BDO classes are fairly lightweight.)
     *
     * @return The DO object held by this BDO object.
     */
    public PersonDO getDO() { 
	return DO;
    }

    /**
     * Like the class <CODE>PersonDO</CODE>, 
     * this class acts as a factory.
     * Business Object (BO) classes typically need these set and get methods.
     * So by deriving a BO from a BDO, or by implementing a BO that 
     * contains one or more BDOs, the developer of the BO is spared some work.
     *
     * @exception Exception
     *   If an error occurs.
     */
    public static PersonBDO createVirgin() throws Exception { 
	PersonBDO bdo = new PersonBDO ();
	return bdo;
    }

    /**
     * Constructor for use by classes derived from <CODE>PersonBDO</CODE>.
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
    public PersonBDO( PersonDO DO ) { 
	this.DO = DO;
    }

    /**
     * Constructor required by <CODE>PersonBDO.create</CODE> methods.
     */
    public PersonBDO() throws Exception { 
	this.DO = PersonDO.createVirgin();
    }

    /**
     * The createExisting method is used to create a <CODE>PersonBDO</CODE>
     * from a <CODE>PersonDO</CODE> that was returned by 
     * the <CODE>PersonQuery</CODE> class.
     */
    public static PersonBDO createExisting( PersonDO DO ) { 
	PersonBDO bdo = new PersonBDO ( DO );
	return bdo;
    }

    /** 
     * The getBDOarray method performs a database query to
     * return an array of <CODE>PersonBDO</CODE> objects
     * representing all the rows in the <CODE>Person</CODE> table.
     *
     * This method is a minimal example of using a Query class.
     * To restrict the set of objects returned,  you could
     * invoke <CODE>query.setXxx()</CODE>, where <CODE>Xxx</CODE>
     * is an Attribute of <CODE>PersonDO</CODE> which was 
     * marked as "Can be queried" in the DODS Attribute Editor.
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static PersonBDO[] getBDOarray()
    throws DataObjectException {
        PersonDO[] DOarray = null;
        try {
            PersonQuery query = new PersonQuery();
	    // To restrict the set of objects returned,
	    // you could invoke query.setXxx(), where Xxx
	    // is an Attribute of <CODE>PersonDO</CODE> which was 
	    // marked as "Can be queried" in the DODS Attribute Editor.
            DOarray = query.getDOArray();
        } catch ( NonUniqueQueryException e1 ) {
            // PersonQuery will throw NonUniqueQueryException
            // only if query.requireUniqueInstance() is called
	    // and more than one DO was found.
        } catch ( DataObjectException e2 ) {
            // This could happen if the database server dies, etc.
            throw e2;
        }
        PersonBDO[] BDOarray = new PersonBDO[ DOarray.length ];
        for ( int i = 0; i < DOarray.length; i++ )
            BDOarray[i] = PersonBDO.createExisting( DOarray[i] );
 
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
     * HTML select lists with <CODE>PersonBDO</CODE> objects as options.
     *
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>
     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * This PersonBDO object holds a reference to a PersonDO object.
     * The id of this PersonBDO is the id of its PersonDO.
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
    * Get login of the PersonDO
    *
    * @return login of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getLogin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getLogin ();
   }

   
   /**
    * Set login of the PersonDO
    *
    * @param login of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setLogin ( String login ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setLogin ( login );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get password of the PersonDO
    *
    * @return password of the PersonDO
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
    * Set password of the PersonDO
    *
    * @param password of the PersonDO
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
    * Get lastname of the PersonDO
    *
    * @return lastname of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getLastname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getLastname ();
   }

   
   /**
    * Set lastname of the PersonDO
    *
    * @param lastname of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setLastname ( String lastname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setLastname ( lastname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get firstname of the PersonDO
    *
    * @return firstname of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getFirstname () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getFirstname ();
   }

   
   /**
    * Set firstname of the PersonDO
    *
    * @param firstname of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setFirstname ( String firstname ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setFirstname ( firstname );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get office of the PersonDO
    *
    * @return office of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getOffice () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getOffice ();
   }

   
   /**
    * Set office of the PersonDO
    *
    * @param office of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setOffice ( String office ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setOffice ( office );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get phone of the PersonDO
    *
    * @return phone of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public String getPhone () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getPhone ();
   }

   
   /**
    * Set phone of the PersonDO
    *
    * @param phone of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setPhone ( String phone ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setPhone ( phone );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isAdmin of the PersonDO
    *
    * @return isAdmin of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsAdmin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsAdmin ();
   }

   
   /**
    * Set isAdmin of the PersonDO
    *
    * @param isAdmin of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsAdmin ( boolean isAdmin ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsAdmin ( isAdmin );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isDeveloper of the PersonDO
    *
    * @return isDeveloper of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsDeveloper () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsDeveloper ();
   }

   
   /**
    * Set isDeveloper of the PersonDO
    *
    * @param isDeveloper of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsDeveloper ( boolean isDeveloper ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsDeveloper ( isDeveloper );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get email of the PersonDO
    *
    * @return email of the PersonDO
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
    * Set email of the PersonDO
    *
    * @param email of the PersonDO
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
    * Get isAuth of the PersonDO
    *
    * @return isAuth of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsAuth () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsAuth ();
   }

   
   /**
    * Set isAuth of the PersonDO
    *
    * @param isAuth of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsAuth ( boolean isAuth ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsAuth ( isAuth );
      afterAnySet();	// business actions/assertions after data assignment
   }


   

   /**
    * Get isPadmin of the PersonDO
    *
    * @return isPadmin of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public boolean getIsPadmin () 
   throws DataObjectException {
      beforeAnyGet();	// business actions/assertions prior to data return
      return DO.getIsPadmin ();
   }

   
   /**
    * Set isPadmin of the PersonDO
    *
    * @param isPadmin of the PersonDO
    *
    * @exception DataObjectException
    *   If the object is not found in the database.
    */
   public void setIsPadmin ( boolean isPadmin ) 
   throws DataObjectException {
      try {
	  // business actions/assertions prior to data assignment
	  beforeAnySet();
      } catch ( Exception e ) { 
	  throw new DataObjectException( "beforeAnySet: " + e.getMessage() );
      }
      DO.setIsPadmin ( isPadmin );
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
	    q.setQueryOwner( DO );
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
	q.setQueryOwner( DO );
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
	    q.setQueryOwner( DO );
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
	q.setQueryOwner( DO );
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
        rbdo.setOwner( this.DO );
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
	PersonDO rdo = rbdo.getOwner();
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
     * Get array of ProblemDO objects that refer to the DO held by this BDO.
     *
     * @return array of ProblemDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.ProblemDO[] getProblemDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.ProblemDO[] ret = null;
	try {
	    webschedule.data.ProblemQuery q = new webschedule.data.ProblemQuery();
	    q.setQueryOwner( DO );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProblemDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single ProblemDO object
     * that refers to the DO held by this BDO.
     *
     * @return ProblemDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one ProblemDO object was found.
     */
    public webschedule.data.ProblemDO getProblemDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.ProblemQuery q = new webschedule.data.ProblemQuery();
	q.setQueryOwner( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of ProblemBDO objects holding ProblemDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of ProblemBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.ProblemBDO[] getProblemBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.ProblemBDO[] ret = null;
	try {
	    webschedule.data.ProblemQuery q = new webschedule.data.ProblemQuery();
	    q.setQueryOwner( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProblemBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single ProblemBDO object holding a ProblemDO object
     * that refers to the DO held by this BDO.
     *
     * @return ProblemBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one ProblemBDO object was found.
     */
    public webschedule.data.ProblemBDO getProblemBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.ProblemQuery q = new webschedule.data.ProblemQuery();
	q.setQueryOwner( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a ProblemBDO object whose ProblemDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo ProblemBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProblemBDO( webschedule.data.ProblemBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addProblemBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a ProblemBDO object whose ProblemDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo ProblemBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProblemBDO( webschedule.data.ProblemBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setOwner( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a ProblemBDO object whose ProblemDO
     * refers to the DO held by this BDO.
     *
     * @param r ProblemBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProblemBDO( webschedule.data.ProblemBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeProblemBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a ProblemBDO object whose ProblemDO
     * refers to the DO held by this BDO.
     *
     * @param r ProblemBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProblemBDO( webschedule.data.ProblemBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO rdo = rbdo.getOwner();
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
     * Get array of ProjectDO objects that refer to the DO held by this BDO.
     *
     * @return array of ProjectDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.ProjectDO[] getProjectDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.ProjectDO[] ret = null;
	try {
	    webschedule.data.ProjectQuery q = new webschedule.data.ProjectQuery();
	    q.setQueryOwner( DO );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProjectDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single ProjectDO object
     * that refers to the DO held by this BDO.
     *
     * @return ProjectDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one ProjectDO object was found.
     */
    public webschedule.data.ProjectDO getProjectDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.ProjectQuery q = new webschedule.data.ProjectQuery();
	q.setQueryOwner( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of ProjectBDO objects holding ProjectDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of ProjectBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.ProjectBDO[] getProjectBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.ProjectBDO[] ret = null;
	try {
	    webschedule.data.ProjectQuery q = new webschedule.data.ProjectQuery();
	    q.setQueryOwner( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.ProjectBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single ProjectBDO object holding a ProjectDO object
     * that refers to the DO held by this BDO.
     *
     * @return ProjectBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one ProjectBDO object was found.
     */
    public webschedule.data.ProjectBDO getProjectBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.ProjectQuery q = new webschedule.data.ProjectQuery();
	q.setQueryOwner( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a ProjectBDO object whose ProjectDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo ProjectBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProjectBDO( webschedule.data.ProjectBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addProjectBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a ProjectBDO object whose ProjectDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo ProjectBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addProjectBDO( webschedule.data.ProjectBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setOwner( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a ProjectBDO object whose ProjectDO
     * refers to the DO held by this BDO.
     *
     * @param r ProjectBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProjectBDO( webschedule.data.ProjectBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeProjectBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a ProjectBDO object whose ProjectDO
     * refers to the DO held by this BDO.
     *
     * @param r ProjectBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeProjectBDO( webschedule.data.ProjectBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO rdo = rbdo.getOwner();
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
	    q.setQueryOwner( DO );
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
	q.setQueryOwner( DO );
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
	    q.setQueryOwner( DO );
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
	q.setQueryOwner( DO );
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
        rbdo.setOwner( this.DO );
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
	PersonDO rdo = rbdo.getOwner();
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
	    q.setQueryOwner( DO );
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
	q.setQueryOwner( DO );
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
	    q.setQueryOwner( DO );
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
	q.setQueryOwner( DO );
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
        rbdo.setOwner( this.DO );
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
	PersonDO rdo = rbdo.getOwner();
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
     * Get array of proposalDO objects that refer to the DO held by this BDO.
     *
     * @return array of proposalDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.proposalDO[] getproposalDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.proposalDO[] ret = null;
	try {
	    webschedule.data.proposalQuery q = new webschedule.data.proposalQuery();
	    q.setQueryOwner( DO );
	    ret = q.getDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.proposalDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single proposalDO object
     * that refers to the DO held by this BDO.
     *
     * @return proposalDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one proposalDO object was found.
     */
    public webschedule.data.proposalDO getproposalDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.proposalQuery q = new webschedule.data.proposalQuery();
	q.setQueryOwner( DO );
	q.requireUniqueInstance();
	return q.getNextDO();
    }

    /**
     * Get array of proposalBDO objects holding proposalDO objects
     * that refer to the DO held by this BDO.
     *
     * @return array of proposalBDO objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public webschedule.data.proposalBDO[] getproposalBDOArray () 
    throws DataObjectException, QueryException {
	webschedule.data.proposalBDO[] ret = null;
	try {
	    webschedule.data.proposalQuery q = new webschedule.data.proposalQuery();
	    q.setQueryOwner( DO );
	    ret = q.getBDOArray();
	} catch ( NonUniqueQueryException e ) { 
	    throw new DataObjectException( 
		"INTERNAL ERROR: unexpected NonUniqueQueryException" );
	} finally {
	    if ( null == ret )
		ret = new webschedule.data.proposalBDO[ 0 ];
	}
	return ret;
    }

    /**
     * Get the single proposalBDO object holding a proposalDO object
     * that refers to the DO held by this BDO.
     *
     * @return proposalBDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception NonUniqueQueryException
     *   If more than one proposalBDO object was found.
     */
    public webschedule.data.proposalBDO getproposalBDO () 
    throws DataObjectException, NonUniqueQueryException, QueryException {
	webschedule.data.proposalQuery q = new webschedule.data.proposalQuery();
	q.setQueryOwner( DO );
	q.requireUniqueInstance();
	return q.getNextBDO();
    }

 
    /**
     * Add (set & commit) a proposalBDO object whose proposalDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo proposalBDO to be set to point to this BDO and committed.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addproposalBDO( webschedule.data.proposalBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        addproposalBDO( rbdo, null );
    }
 
 
    /**
     * Add (set & commit) a proposalBDO object whose proposalDO
     * refers to the DO held by this BDO.
     *
     * @param rbdo proposalBDO to be set to point to this BDO and committed.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void addproposalBDO( webschedule.data.proposalBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        rbdo.setOwner( this.DO );
        rbdo.commit( tran );
    }

 
    /**
     * Remove (delete) a proposalBDO object whose proposalDO
     * refers to the DO held by this BDO.
     *
     * @param r proposalBDO to be deleted.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeproposalBDO( webschedule.data.proposalBDO rbdo )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        removeproposalBDO( rbdo, null );
    }
 
 
    /**
     * Remove (delete) a proposalBDO object whose proposalDO
     * refers to the DO held by this BDO.
     *
     * @param r proposalBDO to be deleted.
     *
     * @param tran The transaction to be used for the commit.
     * If null, a new transaction is created.
     *
     * @exception DatabaseManagerException if could not create a transaction
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException If object is not found in the database.
     */
    public void removeproposalBDO( webschedule.data.proposalBDO rbdo, DBTransaction tran )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
	PersonDO rdo = rbdo.getOwner();
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
	    webschedule.data.C_eventBDO[] a = getC_eventBDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.ProblemBDO[] a = getProblemBDOArray();
	    for ( int i = 0; i < a.length; i++ ) {
		a[ i ].delete( dbt );
	    }
	}
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.ProjectBDO[] a = getProjectBDOArray();
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
	
	
	{
	    // perform cascading delete on referring table
	    webschedule.data.proposalBDO[] a = getproposalBDOArray();
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
