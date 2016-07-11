package webschedule.business.complaint;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.business.operator.Operator;
import webschedule.data.*;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import java.sql.Timestamp;
        
public class complaint
{
    protected complaintDO myDO = null;
    
    public complaint()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = complaintDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty complaint", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating object ID for complaint", ex);
        }
    }
    
    public complaint(complaintDO thecomplaint)
    {
        this.myDO = thecomplaint; 
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting handle for complaint", ex);
        }
    }
    
      public PersonDO getOwner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOwner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting complaint's owner", ex);
        }
    }


 public Timestamp getPostday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPostday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is postday", ex);
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
   
   
   
    public boolean getTime_prob()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTime_prob();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Time Problem", ex);
        }
    } 
   
    public boolean getCables_prob()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCables_prob();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Cables Problem", ex);
        }
    } 
    
    
     public boolean getMess_prob()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getMess_prob();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Mess Problem", ex);
        }
    } 
    
     public boolean getOther_prob()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOther_prob();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Other Problem", ex);
        }
    } 
    
    public String getSpecify() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getSpecify();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting complaint's specify", ex);
        }
    }
    
   
      public PersonDO getFault_pi()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getFault_pi();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting prev PI", ex);
        }
    }


  public OperatorDO getFault_op()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getFault_op();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting prev Operator", ex);
        }
    }

    
           
   public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting complaint's owner", ex);
        }
    }  
    
    public void setPostday(Timestamp postday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPostday(postday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Postday", ex);
        }
    }

  public void setReportername(String reportername) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setReportername(reportername);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting complaint reporter name", ex);
        }
    }
    
   
   
   public void setReporteremail(String reporteremail) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setReporteremail(reporteremail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting complaint reporter email", ex);
        }
    }

       public void setTime_prob(boolean Time_prob)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setTime_prob(Time_prob);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Time Problem", ex);
        }
    }
    
    
      public void setCables_prob(boolean Cables_prob)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setTime_prob(Cables_prob);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Cables Problem", ex);
        }
    }
    
   
    public void setMess_prob(boolean Mess_prob)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setMess_prob(Mess_prob);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Mess Problem", ex);
        }
    }
    
    
     public void setOther_prob(boolean Other_prob)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOther_prob(Other_prob);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting other Problem", ex);
        }
    }
   
   
    public void setSpecify(String specify) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setSpecify(specify);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting complaint other specify", ex);
        }
    }
    
   
   public void setFault_pi(Person Fault_pi) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setFault_pi(PersonDO.createExisting(Fault_pi.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting prev PI", ex);
        }
    }  
 
    
     public void setFault_op(Operator Fault_op) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setFault_op(OperatorDO.createExisting(Fault_op.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting prev Operator", ex);
        }
    }  
 
 
 
    
    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving complaint", ex);
        }
    }
}
