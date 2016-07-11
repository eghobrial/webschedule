package webschedule.business.operator;

import webschedule.business.webscheduleBusinessException;
import webschedule.data.*;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import java.sql.Timestamp;
        
public class Operator
{
    protected OperatorDO myDO = null;
    
    public Operator()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = OperatorDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty person", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating object ID for person", ex);
        }
    }
    
    public Operator(OperatorDO theOperator)
    {
        this.myDO = theOperator; 
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
    
    public String getFirstName() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFirstName();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting operator's first name", ex);
        }
    }
    
     public String getLastName() 
        throws webscheduleBusinessException
    {
        try {
            return myDO.getLastName();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting operator's last name", ex);
        }
    }
     
   
     public String getEmail()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getEmail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting user's email", ex);
        }
    }


   public int getCertday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCertday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Certday", ex);
        }
    }


   public int getCertmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCertmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Certmonth", ex);
        }
    }

   public int getCertyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCertyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Certyear", ex);
        }
    }


   public int getlastscanday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getLastscanday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is lastscanday", ex);
        }
    }


   public int getlastscanmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getLastscanmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is lastscanmonth", ex);
        }
    }

   public int getlastscanyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getLastscanyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is lastscanyear", ex);
        }
    }


   public int getrecertday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getRecertday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is recertday", ex);
        }
    }


   public int getrecertmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getRecertmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is recertmonth", ex);
        }
    }

   public int getrecertyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getRecertyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is recertyear", ex);
        }
    }

 public boolean isExp()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsExp();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Exp", ex);
        }
    }


   public Timestamp getSFTTS()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getSFTTS();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is recertyear", ex);
        }
    }




    public void setFirstname(String firstname) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setFirstName(firstname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's first name", ex);
        }
    }



    public void setLastname(String lastname) 
        throws webscheduleBusinessException
    {
        try {
            myDO.setLastName(lastname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's last name", ex);
        }
    }

    public void setEmail(String email)
        throws webscheduleBusinessException
    {
        try {
            myDO.setEmail(email);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting user's email", ex);
        }
    }


public void setCertday(int Certday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCertday(Certday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Cert day", ex);
        }
    }
    
    
   public void setCertmonth(int Certmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCertmonth(Certmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Cert month", ex);
        }
    }
    
    
    public void setCertyear(int Certyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCertyear(Certyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Cert year", ex);
        }
    }
     
     
     public void setlastscanday(int lastscanday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setLastscanday(lastscanday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting lastscan day", ex);
        }
    }
    
    
   public void setlastscanmonth(int lastscanmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setLastscanmonth(lastscanmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting lastscan month", ex);
        }
    }
    
    
    public void setlastscanyear(int lastscanyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setLastscanyear(lastscanyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting lastscan year", ex);
        }
    }
     
 
 
 public void setrecertday(int recertday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setRecertday(recertday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting recert day", ex);
        }
    }
    
    
   public void setrecertmonth(int recertmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setRecertmonth(recertmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting recert month", ex);
        }
    }
    
    
    public void setrecertyear(int recertyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setRecertyear(recertyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting recert year", ex);
        }
    }
        
	public void setExp(boolean isExp)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsExp(isExp);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting authorized user", ex);
        }
    }
    
public void setSFTTS(Timestamp SFTTS)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setSFTTS(SFTTS);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting SFTTS", ex);
        }
    }
   


    public void save() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.commit();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving person", ex);
        }
    }
}
