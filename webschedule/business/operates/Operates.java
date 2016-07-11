package webschedule.business.operates;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import webschedule.business.person.Person;
import webschedule.business.project.Project;
import webschedule.business.operator.Operator;
import webschedule.data.PersonDO;
import webschedule.data.ProjectDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import java.sql.Timestamp;  
  
        
public class Operates
{
    protected OperatesDO myDO = null;
    
    public Operates()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = OperatesDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty operates", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating object ID for operates", ex);
        }
    }
    
    public Operates(OperatesDO theOperates)
    {
        this.myDO = theOperates; 
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting handle for person", ex);
        }
    }
    
     public OperatorDO getOperator()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOperator();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting operator", ex);
        }
    }

    public String getOperatorID()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }
    
    
      public String getOperatorFN()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getFirstName();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }
    
    
      public String getOperatorLN()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getLastName();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }
    
     public int getOperatorLSD()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getLastscanday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    } 
    
    public int getOperatorLSM()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getLastscanmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    } 
    
    
    public int getOperatorLSY()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getLastscanyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    } 
    
    
     public int getOperatorReCD()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getRecertday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    } 
    
    public int getOperatorReCM()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getRecertmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    } 
    
    
    public int getOperatorReCY()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getRecertyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }
    
     public boolean getOperatorIsExp()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getIsExp();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }
    
      public Timestamp getOperatorSFTTS()
        throws webscheduleBusinessException
    {
        try {
            OperatorDO operator = getOperator();	
            return operator.getSFTTS();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error Operator's handle", ex);
        }
    }
    
    
     public ProjectDO getProject()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getProject();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project", ex);
        }
    }

    public String getProjectID()
        throws webscheduleBusinessException
    {
        try {
            ProjectDO Project = getProject();	
            return Project.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error Project's handle", ex);
        }
    }
    
    
    

 public void setOperator(Operator operator) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOperator(OperatorDO.createExisting(operator.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting operator", ex);
        }
    }


 public void setProject(Project project) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProject(ProjectDO.createExisting(project.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project", ex);
        }
    }

 public void setIsExp(boolean isexp) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsExp(isexp);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Is Exp", ex);
        }
    }


    
      public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving S_event", ex);
        }
    }
    
    public void delete() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting S_event", ex);
        }
    }
}
    
    
