/**--------------------------------------------------------------
* webschedule
*
* @class: OperatesFactory
* @version
* @author Eman Ghobrial
* @since  April 2005
* @Update August 2011, fixed operates for a certain project
*--------------------------------------------------------------*/


package webschedule.business.operates;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.project.Project;
import webschedule.business.operator.Operator;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.appserver.server.Enhydra;
import com.lutris.logging.*;
import com.lutris.appserver.server.sql.ObjectId;

public class OperatesFactory
{
   
   
   
    public static Operates[] getOperatesList()
 	throws webscheduleBusinessException
 	 {
  	    Operates[] theOperatesArray = null;

	    try {
            OperatesQuery query = new OperatesQuery();
	
            
	        OperatesDO[] DOarray = query.getDOArray();
	        theOperatesArray = new Operates[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theOperatesArray[i] = new Operates(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getOperatessList()", ex);
        }

        return theOperatesArray;
    }
   
   
     /**
     * Gets the Operates object.
     */
    
    public static Operates[] getOperatorsListforProj(Project project)
 	throws webscheduleBusinessException
 	 {
  	    Operates[] theOperatesArray = null;

	    try {
            OperatesQuery query = new OperatesQuery();
	
            // Order operators alphabetically by operator name

             query.setQueryProject(ProjectDO.createExisting(project.getHandle()));
	        OperatesDO[] DOarray = query.getDOArray();
	        theOperatesArray = new Operates[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theOperatesArray[i] = new Operates(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getOperatorsList()", ex);
        }

        return theOperatesArray;
    }

  
   
     /**
     * Gets the Operates object.
     */
    
   /*  public static Operator[] getOperatorsList(Project project)
 	throws webscheduleBusinessException
 	 {
  	    Operator[] theOperatorArray = null;

	    try {
            OperatesQuery query = new OperatesQuery();
	
            // Order operators alphabetically by operator name
             query.setQueryOperator(ProjectDO.createExisting(project.getHandle()));
	        OperatorDO[] DOarray = query.getDOArray();
	        theOperatorArray = new Operator[ DOarray.length ];
	        for ( int i = 0; i < DOarray.length; i++ )
	    	theOperatorArray[i] = new Operator(DOarray[i]);
	    }catch(Exception ex) {
            throw new webscheduleBusinessException("Exception in getOperatorsList()", ex);
        }

        return theOperatorArray;
    }

  */

}
