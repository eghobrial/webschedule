/**--------------------------------------------------------------
* webschedule demo
*
* @class: ProjectFactoryImpl
* @version 
* @author  
* @since   
*
*
*
*
*--------------------------------------------------------------*/

package webschedule.business.project;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.person.Person;
import com.lutris.dods.builder.generator.query.*;
import webschedule.business.person.Person;
/*import webschedule.data.PersonDO;*/
import com.lutris.appserver.server.sql.ObjectId;

public class ProjectFactory
{    
    /** 
     * The findProjectsForPerson method performs a database query to
     * return an array of <CODE>Project</CODE> objects
     * representing all the rows in the <CODE>disc</CODE> table
     * that have and owner matching <CODE>Person owner</CODE>. 
     *
     * @exception DataObjectException
     *   If an object is not found in the database.
     */
    public static Project[] findProjectsForPerson(Person owner)
        throws webscheduleBusinessException
    {
	    Project[] theProjectArray = null;
        
	    try {
            ProjectQuery query = new ProjectQuery();
	        // To restrict the set of discs returned
	        // to the that of the specific owner set in the query
	        query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
            // Order discs alphabetically by artist
            //query.addOrderByArtist();
	        ProjectDO[] DOarray = query.getDOArray();
	        theProjectArray = new Project[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProjectArray[i] = new Project(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProjectsForPerson()", ex);
        }
        
        return theProjectArray;
    }
    
    /**
     * 
     */
    public static Project findProjectByID(String id)
        throws webscheduleBusinessException
    {
        Project theProject = null;
        
	    try {
            ProjectQuery query = new ProjectQuery();
	        // Require that we only find ONE disc
            query.requireUniqueInstance();
	        query.setQueryOId(new ObjectId(id));
	        ProjectDO theProjectDO = query.getNextDO();
	    	theProject = new Project(theProjectDO);
            return theProject;
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProjectsForPerson()", ex);
        }
    }

 /* find project by name*/

  public static Project findProjectByProj_nameOwner(String proj_name, Person owner)
        throws webscheduleBusinessException
    {


	    try {
            ProjectQuery query = new ProjectQuery();
            query.setQueryProj_name(proj_name);
	     query.setQueryOwner(PersonDO.createExisting(owner.getHandle()));
	        // Require that we only find ONE project
            query.requireUniqueInstance();
	
	        ProjectDO[] foundProject = query.getDOArray();
	        if (foundProject.length != 0) {
	    	return new Project(foundProject[0]);
	    	} else  {
	    	return null;
	    	}

	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProjectByProj_name()", ex);
        }
    }


   /* find project by name*/

  public static Project findProjectByProj_name(String proj_name)
        throws webscheduleBusinessException
    {


	    try {
            ProjectQuery query = new ProjectQuery();
            query.setQueryProj_name(proj_name);
	        // Require that we only find ONE project
            query.requireUniqueInstance();
	
	        ProjectDO[] foundProject = query.getDOArray();
	        if (foundProject.length != 0) {
	    	return new Project(foundProject[0]);
	    	} else  {
	    	return null;
	    	}

	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in findProjectByProj_name()", ex);
        }
    }


 /* find project by Outside*/
/*public static Project[] getProjectsListByisoutside(Boolean isoutside)
 	throws webscheduleBusinessException
 	 {
 	
 		
 	    Project[] theProjectArray = null;

	    try {
            ProjectQuery query = new ProjectQuery();
	    query.setQueryIsoutside(isoutside);
	    
	
            // Order projects alphabetically by project name
            query.addOrderByProj_name();
	        ProjectDO[] DOarray = query.getDOArray();
	        theProjectArray = new Project[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProjectArray[i] = new Project(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getProjectsList()", ex);
        }

        return theProjectArray;
    }
 	*/

 
/* find project list */
 public static Project[] getProjectsList()
 	throws webscheduleBusinessException
 	 {
 	
 		
 	    Project[] theProjectArray = null;

	    try {
            ProjectQuery query = new ProjectQuery();
	
            // Order projects alphabetically by project name
            query.addOrderByProj_name();
	        ProjectDO[] DOarray = query.getDOArray();
	        theProjectArray = new Project[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProjectArray[i] = new Project(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getProjectsList()", ex);
        }

        return theProjectArray;
    }
 	

/* find project list */
 public static Project[] getProjectsListNotExp()
 	throws webscheduleBusinessException
 	 {
 	
 		
 	    Project[] theProjectArray = null;

	    try {
            ProjectQuery query = new ProjectQuery();
	query.setQueryIsExp(false);
            // Order projects alphabetically by project name
            query.addOrderByProj_name();
	        ProjectDO[] DOarray = query.getDOArray();
	        theProjectArray = new Project[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProjectArray[i] = new Project(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getProjectsList()", ex);
        }

        return theProjectArray;
    }


/* find pilot project list */
 public static Project[] getPilotProjectsList()
 	throws webscheduleBusinessException
 	 {
 	
	
 	String pilot1="Pilot%";	
 	    Project[] theProjectArray = null;

	    try {
            ProjectQuery query = new ProjectQuery();
	
	QueryBuilder qb = query.getQueryBuilder();
	  //qb.select(ProjectDO.Proj_name);
            qb.addWhere("Project.Proj_name like 'Pilot%'");
            // Order projects alphabetically by project name
            query.addOrderByProj_name();
            qb.debug();
	
            
	        ProjectDO[] DOarray = query.getDOArray();
	        theProjectArray = new Project[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theProjectArray[i] = new Project(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getProjectsList()", ex);
        }

        return theProjectArray;
    }
 	
}

