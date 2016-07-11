package webschedule.business.problem;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.data.*;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
        
public class Problem
{
    protected ProblemDO myDO = null;
    
    public Problem()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = ProblemDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty problem", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating object ID for problem", ex);
        }
    }
    
    public Problem(ProblemDO theProblem)
    {
        this.myDO = theProblem; 
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting handle for Problem", ex);
        }
    }
    
      public PersonDO getOwner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOwner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting problem's owner", ex);
        }
    }

    
    public String getReportername() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getReportername();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporter name", ex);
        }
    }
    
    public String getReporteremail() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getReporteremail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Reporter email", ex);
        }
    }
    
    public String getDescrib() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDescrib();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting problem's describ", ex);
        }
    }
    
    public String getFixdetail() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFixdetail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's fix detail", ex);
        }
    }

     public String getProblemdetail()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProblemdetail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's detail", ex);
        }
    }

    public int getSeveritycode()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getSeveritycode();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Severity code", ex);
        }
    }

   
    public int getClasscode()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getClasscode();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Class code", ex);
        }
    }
   
    public int getPrioritycode()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPrioritycode();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Priority code", ex);
        }
    }

    public int getStatuscode()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStatuscode();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Status code", ex);
        }
    }

   
    public int getPostday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPostday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Post day", ex);
        }
    }


 public int getPostmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPostmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Post month", ex);
        }
    }


 public int getPostyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPostyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Post year", ex);
        }
    }

  public int getPosth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPosth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Post hour", ex);
        }
    }


 public int getPostm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPostm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Post minute", ex);
        }
    }

  
    public int getCloseday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCloseday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Close day", ex);
        }
    }


 public int getClosemonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getClosemonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Close month", ex);
        }
    }


 public int getCloseyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCloseyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Close year", ex);
        }
    }
    
    
    
      
    public int getUpdateday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getUpdateday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Update day", ex);
        }
    }


 public int getUpdatemonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getUpdatemonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Update month", ex);
        }
    }


 public int getUpdateyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getUpdateyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's Update year", ex);
        }
    }
    
    
    public boolean GE_called()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getGE_called();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting GE called", ex);
        }
    } 
    
    
     public String getProblemnum()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProblemnum();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Problem's number", ex);
        }
    }


     public String getPostpm()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPostpm();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Post pm", ex);
        }
    }


  public String getOwnerLogin()
     throws webscheduleBusinessException
    {
    try {
    	PersonDO owner = getOwner();
    	return owner.getLogin();
            } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's owner Login", ex);
        }
    }

    
   public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's owner", ex);
        }
    }  
    

  public void setReportername(String reportername) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setReportername(reportername);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem reporter name", ex);
        }
    }
    
   
   
   public void setReporteremail(String reporteremail) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setReporteremail(reporteremail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem reporter email", ex);
        }
    }
    
   
    public void setDescrib(String describ) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setDescrib(describ);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem describ", ex);
        }
    }
    
   
public void setProblemdetail(String problemdetail) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setProblemdetail(problemdetail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem detail", ex);
        }
    }
    
   
   public void setFixdetail(String fixdetail) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setFixdetail(fixdetail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem fixdetail", ex);
        }
    }
      
   public void setPostpm(String postpm) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setPostpm(postpm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Post pm", ex);
        }
    }
    
    public void setSeveritycode(int severitycode)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setSeveritycode(severitycode);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's severity code", ex);
        }
    }
    
       public void setClasscode(int classcode)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setClasscode(classcode);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's class code", ex);
        }
    }
    
       public void setPrioritycode(int prioritycode)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPrioritycode(prioritycode);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's priority code", ex);
        }
    }
    
   public void setStatuscode(int statuscode)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStatuscode(statuscode);
        } catch(DataObjectException ex) {
        throw new webscheduleBusinessException("Error setting Problem's status code", ex);
        }
    }
   
   
     public void setPostday(int postday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPostday(postday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's post day", ex);
        }
    }
   
   public void setPostmonth(int postmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPostmonth(postmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's Post month", ex);
        }
    }
    
    public void setPostyear(int postyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPostyear(postyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's Post year", ex);
        }
    }
    
    
        public void setPosth(int posth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPosth(posth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's post hour", ex);
        }
    }
   
   public void setPostm(int postm)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPostm(postm);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's Post minute", ex);
        }
    }
  
     public void setUpdateday(int updateday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setUpdateday(updateday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's Update day", ex);
        }
    }
   
   public void setUpdatemonth(int updatemonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setUpdatemonth(updatemonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's Update month", ex);
        }
    }
    
    public void setUpdateyear(int updateyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setUpdateyear(updateyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's Update year", ex);
        }
    }
    
    
     public void setCloseday(int closeday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCloseday(closeday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's close day", ex);
        }
    }
   
   public void setClosemonth(int closemonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setClosemonth(closemonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's close month", ex);
        }
    }
    
    public void setCloseyear(int closeyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCloseyear(closeyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem's close year", ex);
        }
    }
    
      public void setProblemnum(String problemnum) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setProblemnum(problemnum);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Problem number", ex);
        }
    }
    
    
       public void setGE_called(boolean GE_called)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setGE_called(GE_called);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting GE called", ex);
        }
    }
    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving Problem", ex);
        }
    }
}
