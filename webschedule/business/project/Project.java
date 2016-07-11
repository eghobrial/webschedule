package webschedule.business.project;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.data.PersonDO;
import webschedule.data.ProjectDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;
 import com.lutris.dods.builder.generator.*;
import com.lutris.dods.builder.generator.dataobject.GenericDO;
import java.sql.Date;
  
     
public class Project
{
    protected ProjectDO myDO = null;
         
    public Project()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = ProjectDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty Project", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty Project", ex);
        }
    }
    
    protected Project(ProjectDO theProject)
        throws webscheduleBusinessException
    {
        this.myDO = theProject;
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting Project's handle", ex);
        }
    }
    
    public String getProj_name()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProj_name();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's name", ex);
        }
    }

     public String getPassword()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPassword();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's password", ex);
        }
    }

    public String getDescription()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDescription();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's description", ex);
        }
    }
    
    public String getIndexnum()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIndexnum();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's indexnum", ex);
        }
    }
    
    public double getTotalhours()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getTotalhours();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's total hours", ex);
        }
    }

   public double getDonehours()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDonehours();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's done hours", ex);
        }
    }

     public int getCodeofpay()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCodeofpay();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's code of pay", ex);
        }
    }


 public String getContactname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getContactname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's name", ex);
        }
    }


 public String getContactphone()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getContactphone();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's phone", ex);
        }
    }


 public String getBilladdr1()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBilladdr1();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Billing address line 1", ex);
        }
    }


public String getBilladdr2()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBilladdr2();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Billing address line 2", ex);
        }
    }
    
    public String getBilladdr3()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBilladdr3();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Billing address line 3", ex);
        }
    }


public String getCity()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCity();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting City", ex);
        }
    }

public String getState()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getState();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting State", ex);
        }
    }
   
   public String getZip()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getZip();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Zip", ex);
        }
    } 
public String getAccountid()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAccountid();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Account ID", ex);
        }
    }
public  int getExpday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getExpday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Exper Day", ex);
        }
    }



public  int getExpmonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getExpmonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Exper Month", ex);
        }
    }



public  int getExpyear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getExpyear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Exper year", ex);
        }
    }
    
 public boolean isOutside()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsoutside();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is outside", ex);
        }
    }
    
    
/*    public String getInstitute()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getInstitute();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Institute", ex);
        }
    }
    */
    
     public PersonDO getOwner()
        throws webscheduleBusinessException
    {
        try {
              return myDO.getOwner();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting S_event's owner", ex);
        }
    }

    public String getUserID()
        throws webscheduleBusinessException
    {
        try {
            PersonDO owner = getOwner();	
            return owner.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error User's handle", ex);
        }
    }


 public String getUserFirstName()
        throws webscheduleBusinessException
    {
        try {
            PersonDO owner = getOwner();	
            return owner.getFirstname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error User's first name", ex);
        }
    }
    
     public String getUserLastName()
        throws webscheduleBusinessException
    {
        try {
            PersonDO owner = getOwner();	
            return owner.getLastname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error User's last name", ex);
        }
    }

    
     public String getUserEmail()
        throws webscheduleBusinessException
    {
        try {
            PersonDO owner = getOwner();	
            return owner.getEmail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error User's email", ex);
        }
    }
    

 public String getNotifycontact()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getNotifycontact();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Notify contact", ex);
        }
    } 
    
 public String getInstitute()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getInstitute();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Institute", ex);
        }
    } 
    
    
     public boolean isExp()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsExp();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Expiered", ex);
        }
    }
    
     public int Cancredit()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCanCredit();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Expiered", ex);
        }
    }
    
     public String getProposalID()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalID();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting roposalID", ex);
        }
    } 
    
     public String getFpemail()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFpemail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting FP email", ex);
        }
    }
    
     public String getPOnum()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPOnum();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting PO num", ex);
        }
    }
    
    
    public java.sql.Date getPOexpdate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPOexpdate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting PO exp Date", ex);
        }
    }
    

  public double getPOhours()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPOhours();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Project's PO hours", ex);
        }
    }
   
     public java.sql.Date getIACUCDate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIACUCDate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting IACUC Date", ex);
        }
    }
    
      public String getModifiedby()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getModifiedby();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Modifiedby", ex);
        }
    }
    
    
      public java.sql.Date getModDate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getModDate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Modification Date", ex);
        }
    }
    
      
     public String getNotes()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getNotes();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Notes", ex);
        }
    }
    
    
     public String getIRBnum()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIRBnum();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting IRBnum", ex);
        }
    }
    
    
    
    // The Set Commands
    public void setProj_name(String proj_name)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setProj_name(proj_name);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's name", ex);
        }
    }

 

    public void setPassword(String password)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setPassword(password);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's password", ex);
        }
    }
    
	public void setDescription(String description)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setDescription(description);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's description", ex);
        }
    }
    
	public void setIndexnum(String indexnum)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIndexnum(indexnum);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's indexnum", ex);
        }
    }
    
    public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's owner", ex);
        }
    }
    
    public void setTotalhours(double totalhours)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setTotalhours(totalhours);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's total hours", ex);
        }
    }
public void setContactname(String contactname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setContactname(contactname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Contact name", ex);
        }
    }
public void setContactphone(String contactphone)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setContactphone (contactphone);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Contact phone", ex);
        }
    }
public void setBilladdr1(String billaddr1)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBilladdr1 (billaddr1);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Bill address line 1", ex);
        }
    }
public void setBilladdr2(String billaddr2)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBilladdr2 (billaddr2);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Bill address line 2", ex);
        }
    }
public void setBilladdr3(String billaddr3)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBilladdr3(billaddr3);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Bill address line 3", ex);
        }
    }
public void setCity(String city)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCity (city);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting City", ex);
        }
    }

public void setState(String state)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setState (state);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting State", ex);
        }
    }

public void setZip(String zip)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setZip (zip);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Zip", ex);
        }
    }

public void setAccountid(String accountid)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAccountid(accountid);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting accountid", ex);
        }
    }

 public void setOutside(boolean isOutside)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsoutside(isOutside);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Outside", ex);
        }
    }
    
 public void setExp(boolean isExp)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsExp(isExp);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exp", ex);
        }
    }

public void setExpday (int expday)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setExpday(expday);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exper Day", ex);
        }
    }



public void setExpmonth (int expmonth)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setExpmonth(expmonth);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exper Month", ex);
        }
    }


public void setExpyear (int expyear)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setExpyear(expyear);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exper Year", ex);
        }
    }

     public void setDonehours(double donehours)

       throws webscheduleBusinessException
    {
        try {
            this.myDO.setDonehours(donehours);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's done hours", ex);
        }
    }

     public void setCodeofpay(int codeofpay)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCodeofpay(codeofpay);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's code of pay", ex);
        }
    }



public void setNotifycontact(String notifycontact)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setNotifycontact (notifycontact);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Notify contact", ex);
        }
    }

 public void setCancredit(int Cancredit)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCanCredit(Cancredit);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Can Credit", ex);
        }
    }

 public void setInstitute(String institute)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setInstitute(institute);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's institute", ex);
        }
    }

 public void setProposalID(String proposalID)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setProposalID(proposalID);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's proposalID", ex);
        }
    }

 public void setFpemail(String fpemail)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setFpemail(fpemail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's fpemail", ex);
        }
    }
    
    
 public void setPOnum(String POnum)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setPOnum(POnum);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's POnum", ex);
        }
    }
    
  public void setPOexpdate(java.sql.Date POexpdate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPOexpdate(POexpdate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting PO exp date", ex);
        }
    }


  public void setPOhours(double POhours)

       throws webscheduleBusinessException
    {
        try {
            this.myDO.setPOhours(POhours);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's PO hours", ex);
        }
    }

  public void setIACUCDate(java.sql.Date IACUCDate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIACUCDate(IACUCDate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting IACUC date", ex);
        }
    }
    
    public void setModifiedby(String Modifiedby)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setModifiedby(Modifiedby);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's Modifiedby", ex);
        }
    }

  public void setModDate(java.sql.Date ModDate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setModDate(ModDate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Modification date", ex);
        }
    }

    public void setNotes(String Notes)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setNotes(Notes);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's Notes", ex);
        }
    }

 public void setIRBnum(String IRBnum)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setIRBnum(IRBnum);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's IRBnum", ex);
        }
    }


    public void save() 
        throws webscheduleBusinessException
    {
        try {
		CoreDO.versioning = false;
            this.myDO.commit();
	    CoreDO.versioning = true;
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error saving Project", ex);
        }
    }
    
    public void delete() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting Project", ex);
        }
    }
}
