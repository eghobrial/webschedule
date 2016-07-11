package webschedule.business.proposal;

import webschedule.business.webscheduleBusinessException;
import webschedule.business.person.Person;
import webschedule.data.PersonDO;
import webschedule.data.proposalDO;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.appserver.server.sql.ObjectIdException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import com.lutris.appserver.server.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.*;
import com.lutris.dods.builder.generator.dataobject.GenericDO;
import java.sql.Date;

  
     
public class Proposal
{
    protected proposalDO myDO = null;
         
    public Proposal()
        throws webscheduleBusinessException
    {
        try {
            this.myDO = proposalDO.createVirgin();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error creating empty Proposal", ex);
        } catch(ObjectIdException ex) {
            throw new webscheduleBusinessException("Error creating empty Proposal", ex);
        }
    }
    
    protected Proposal(proposalDO theProposal)
        throws webscheduleBusinessException
    {
        this.myDO = theProposal;
    }
    
    public String getHandle()
        throws webscheduleBusinessException
    {
        try {
            return this.myDO.getHandle();
        } catch(DatabaseManagerException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's handle", ex);
        }
    }
    
     public int getToday()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getToday();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Propsal's day", ex);
        }
    }

     public int getMonth()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getMonth();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Propsal's month", ex);
        }
    }

    
    public int getYear()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getYear();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Propsal's month", ex);
        }
    }

      public boolean Isucsd()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsucsd();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is UCSD", ex);
        }
    }
    
      public boolean Isanimal()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIsanimal();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is animal", ex);
        }
    }
    
      public boolean Issample()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIssample();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Sample", ex);
        }
    }
    
    
    public String getProj_name()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProj_name();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Propsal's name", ex);
        }
    }
    
    public String getCname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's name", ex);
        }
    }


 public String getCphone()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCphone();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's phone", ex);
        }
    }


 public String getCemail()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCemail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's email", ex);
        }
    }

 public boolean CntrOp()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCntrOp();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Sample", ex);
        }
    }
    
    
     public String getOp1lastname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp1lastname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 1 last name", ex);
        }
    }

 public String getOp1firstname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp1firstname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 1 first name", ex);
        }
    }


 public String getOp1phone()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp1phone();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 1 phone", ex);
        }
    }
    
     public String getOp1email()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp1email();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 1 email", ex);
        }
}	
	
	public String getOp2lastname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp2lastname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 2 last name", ex);
        }
    }

 public String getOp2firstname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp2firstname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 2 first name", ex);
        }
    }


 public String getOp2phone()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp2phone();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 2 phone", ex);
        }
    }
    
     public String getOp2email()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp2email();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's Operator 2 email", ex);
        }
    }
    
    
    
   
    
    public String getIndexnum()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIndexnum();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's indexnum", ex);
        }
    }
    
     

 

 public String getBaline1()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBaline1();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Billing address line 1", ex);
        }
    }


public String getBaline2()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBaline2();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Billing address line 2", ex);
        }
    }
    
    public String getBaline3()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBaline3();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Billing address line 3", ex);
        }
    }


public String getBacity()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBacity();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting City", ex);
        }
    }

public String getBast()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBast();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting State", ex);
        }
    }
   
   public String getBazip()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBazip();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Zip", ex);
        }
    } 
    
     public String getFpname()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFpname();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's name", ex);
        }
    }


 public String getFpphone()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFpphone();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's phone", ex);
        }
    }


 public String getFpemail()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getFpemail();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Contact's email", ex);
        }
    }
    
    
     public int getThours()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getThours();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's total hours", ex);
        }
    }
    
/*public String getDuration()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDuration();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Duration", ex);
        }
    }
  */
    
     public String getWriteup()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getWriteup();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's writeup", ex);
        }
    }
    
public String getDataanalysis()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getDataanalysis();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's writeup", ex);
        }
    }
    
/*public  int getExpday()
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
  */
  
  
    
 public boolean IACUCFaxed()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIACUCFaxed();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is IACUC Faxed", ex);
        }
    }
    
    
  public boolean RFCoils()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getRFCoils();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is ", ex);
        }
    }
   
    public boolean Restraints()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getRestraints();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is ", ex);
        }
    }
    
    
     public boolean Physioeq()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getPhysioeq();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Physioeq ", ex);
        }
    }
    
     public boolean anesthetics()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnesthetics();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is ", ex);
        }
    }
    
     public boolean ancillary()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAncillary();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is ", ex);
        }
    }
    
      public boolean op1status()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp1status();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Op1 status ", ex);
        }
    }
      public boolean op2status()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getOp2status();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Op2 status ", ex);
        }
    }
    
     public boolean IACUC()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIACUC();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is IACUC on file", ex);
        }
    }
    
    public  int getStatus()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStatus();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Exper Day", ex);
        }
    }


 public java.sql.Date getStdate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStdate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting start Date", ex);
        }
    }
    
 public java.sql.Date getExpdate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getExpdate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting exp Date", ex);
        }
    }
    
    
    public String getBioHazard()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBioHazard();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's biohazard", ex);
        }
    }
    
    
      public boolean Stimuli()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getStimuli();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting has Stimuli", ex);
        }
    }
    
    
    
    
      public boolean AnimalTrans()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnimalTrans();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is Animal Transfer Approved", ex);
        }
    }
    
     public java.sql.Date getAnimTransDate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnimTransDate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting animal transfer Date", ex);
        }
    }
    
    
     public String getBproj_name()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBproj_name();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal's brief name", ex);
        }
    }
     public boolean Contrast()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getContrast();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting has Contrast", ex);
        }
    }
    
      public java.sql.Date getIACUCDate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIACUCDate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting IACUC exp Date", ex);
        }
    }
    

 public String getBookmark()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getBookmark();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Bookmark", ex);
        }
    }

    
     public String getComments()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getComments();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Commenrs", ex);
        }
    }

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
     
     public boolean Nighttime()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getNighttime();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting is night time", ex);
        }
    }

     
  public String getCopis()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getCopis();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Co PIs", ex);
        }
    }


 public String getIntcomments()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIntcomments();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Internal Comments", ex);
        }
    }
    
     public String getResponse()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getResponse();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Response", ex);
        }
    }
    
     public String getAnimalq1()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnimalq1();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Animal Q1", ex);
        }
    }


     public String getAnimalq2()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnimalq2();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Animal Q2", ex);
        }
    }


     public String getAnimalq3()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnimalq3();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Animal Q3", ex);
        }
    }



     public String getAnimalq4()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getAnimalq4();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Animal Q4", ex);
        }
    }


     public String getProposalq1()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq1();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q1", ex);
        }
    }


     public String getProposalq2()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq2();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposalal Q2", ex);
        }
    }

  public String getProposalq3()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq3();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q3", ex);
        }
    }
    
      public String getProposalq4()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq4();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q4", ex);
        }
    }
    
      public String getProposalq5()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq5();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q5", ex);
        }
    }
    
      public String getProposalq6()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq6();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q6", ex);
        }
    }
    
      public String getProposalq7()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq7();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q7", ex);
        }
    }
    
      public String getProposalq8()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getProposalq8();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Proposal Q8", ex);
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
    
      public java.sql.Date getApprovalDate()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getApprovalDate();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting Approval Date", ex);
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


   public String getIRBnum()
        throws webscheduleBusinessException
    {
        try {
            return myDO.getIRBnum();
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error getting IRBnum", ex);
        }
    }
    
    
    
 
    /* 
    *   set commands
    */
    
     public void setOwner(Person owner) 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOwner(PersonDO.createExisting(owner.getHandle()));
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal's owner", ex);
        }
    }
    
    public void setToday (int today)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setToday(today);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting  Today", ex);
        }
    }






public void setMonth (int month)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setMonth(month);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Month", ex);
        }
    }


public void setYear (int year)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setYear(year);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Year", ex);
        }
    }
    
    public void setIsucsd(boolean Isucsd)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsucsd(Isucsd);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exp", ex);
        }
    }

      public void setIsanimal(boolean Isanimal)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIsanimal(Isanimal);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exp", ex);
        }
    }

      public void setIssample(boolean Issample)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIssample(Issample);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Exp Is sample", ex);
        }
    }


    public void setProj_name(String proj_name)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setProj_name(proj_name);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal's name", ex);
        }
    }

public void setCname(String cname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCname(cname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Contact name", ex);
        }
    }
    
public void setCphone(String cphone)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCphone (cphone);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Contact phone", ex);
        }
    }
    
  public void setCemail(String cemail)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCemail (cemail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Contact email", ex);
        }
    }
      
   public void setCntrOp(boolean CntrOp)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCntrOp(CntrOp);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Center Operator ", ex);
        }
    }
    
      

    public void setOp1lastname(String op1lastname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp1lastname(op1lastname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 1 Last Name", ex);
        }
    }
    
     public void setOp1firstname(String op1firstname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp1firstname(op1firstname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 1 First Name", ex);
        }
    }
    
     public void setOp1phone(String op1phone)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp1phone(op1phone);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 1 Phone", ex);
        }
    }
    
     public void setOp1email(String op1email)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp1email(op1email);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 1 Email", ex);
        }
    }
    
     public void setOp2lastname(String op2lastname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp2lastname(op2lastname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 2 Last Name", ex);
        }
    }
    
     public void setOp2firstname(String op2firstname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp2firstname(op2firstname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 2 First Name", ex);
        }
    }
    
     public void setOp2phone(String op2phone)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp2phone(op2phone);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 2 Phone", ex);
        }
    }
    
     public void setOp2email(String op2email)
        throws webscheduleBusinessException
    {
        try {
            this.myDO .setOp2lastname(op2email);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 2 Email", ex);
        }
    }
    
    public void setIndexnum(String indexnum)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIndexnum(indexnum);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Propsal's indexnum", ex);
        }
    }
    
	
	    
public void setBaline1(String baline1)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBaline1 (baline1);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Bill address line 1", ex);
        }
    }
public void setBaline2(String baline2)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBaline2 (baline2);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Bill address line 2", ex);
        }
    }
public void setBaline3(String baline3)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBaline3(baline3);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Bill address line 3", ex);
        }
    }
public void setBacity(String bacity)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBacity (bacity);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting City", ex);
        }
    }

public void setBast(String bast)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBast (bast);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting State", ex);
        }
    }

public void setBazip(String bazip)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBazip (bazip);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Zip", ex);
        }
    }
    
    public void setFpname(String fpname)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setFpname(fpname);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Finantional name", ex);
        }
    }
    
public void setFpphone(String fpphone)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setFpphone (fpphone);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Finantional person phone", ex);
        }
    }
    
  public void setFpemail(String fpemail)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setFpemail (fpemail);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Finantional person email", ex);
        }
    }

   
    public void setThours(int thours)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setThours(thours);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Propsal's total hours", ex);
        }
    }
    
    
public void setWriteup(String writeup)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setWriteup(writeup);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal's writeup", ex);
        }
    }


public void setDataanalysis(String dataanalysis)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setDataanalysis(dataanalysis);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal's data analysis", ex);
        }
    }



 public void setIACUCFaxed(boolean IACUCFaxed)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIACUCFaxed(IACUCFaxed);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting IACUC Faxed ", ex);
        }
    }
  
  
   public void setRFCoils(boolean RFCoils)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setRFCoils(RFCoils);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting RFCoils ", ex);
        }
    }
    
    public void setRestraints(boolean Restraints)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setRestraints(Restraints);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Restrains", ex);
        }
    }
    
    
    public void setPhysioeq(boolean Physioeq)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPhysioeq(Physioeq);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Physioeq ", ex);
        }
    }
    
    
    
    public void setAnesthetics(boolean Anesthetics)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnesthetics(Anesthetics);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Anesthetics", ex);
        }
    }
    
    
    public void setAncillary(boolean Ancillary)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAncillary(Ancillary);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Ancillary", ex);
        }
    }
    
    public void setOp1status(boolean Op1status)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOp1status(Op1status);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 1 status", ex);
        }
    }
    
     public void setOp2status(boolean Op2status)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setOp1status(Op2status);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Operator 2 status", ex);
        }
    }
    
    
    
    public void setIACUC(boolean IACUC)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIACUC(IACUC);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting IACUC", ex);
        }
    }
    
    
          
public void setStatus(int status)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStatus(status);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Status", ex);
        }
    }

 
   public void setStdate(java.sql.Date stdate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStdate(stdate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting start date", ex);
        }
    }
   
 public void setExpdate(java.sql.Date expdate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setExpdate(expdate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting start date", ex);
        }
    }
    
     public void setBioHazard(String BioHazard)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBioHazard(BioHazard);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal's BioHazard", ex);
        }
    }

   
    public void setStimuli(boolean Stimuli)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setStimuli(Stimuli);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Stimuli", ex);
        }
    }
    
    public void setAnimalTrans(boolean AnimalTrans)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnimalTrans(AnimalTrans);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting AnimalTrans", ex);
        }
    }
    
    
    public void setAnimTransDate(java.sql.Date AnimTransDate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnimTransDate(AnimTransDate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting AnimTransDate date", ex);
        }
    }
    
    
     
     public void setAproj_name(String Aproj_name)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBproj_name(Aproj_name);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal's Aproj_name", ex);
        }
    }
    
    
     
    public void setContrast(boolean Contrast)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setContrast(Contrast);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Contrast", ex);
        }
    }
    
      
    public void setIACUCDate(java.sql.Date IACUCDate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIACUCDate(IACUCDate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting IACUCDate date", ex);
        }
    }
    
    public void setComments(String comments)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setComments(comments);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Comments", ex);
        }
    }
    
    public void setNighttime(boolean Nighttime)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setNighttime(Nighttime);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting AnimalTrans", ex);
        }
    }
    
    
      public void setCopis(String copis)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setCopis(copis);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting CO PIs", ex);
        }
    }
    
      public void setIntcomments(String intcomments)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setIntcomments(intcomments);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Internal Comments", ex);
        }
    }
    
    
      public void setResponse(String response)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setResponse(response);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Response", ex);
        }
    }
    
    
      public void setAnimalq1(String animalq1)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnimalq1(animalq1);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Animal Q1", ex);
        }
    }
    
     public void setAnimalq2(String animalq2)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnimalq2(animalq2);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Animal Q2", ex);
        }
    }
    
     public void setAnimalq3(String animalq3)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnimalq3(animalq3);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Animal Q3", ex);
        }
    }
    
     public void setAnimalq4(String animalq4)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setAnimalq4(animalq4);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Animal Q4", ex);
        }
    }
    
    
     public void setProposalq1(String proposalq1)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq1(proposalq1);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q1", ex);
        }
    }
    
     public void setProposalq2(String proposalq2)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq2(proposalq2);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q2", ex);
        }
    }
    
        
     public void setProposalq3(String proposalq3)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq3(proposalq3);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q3", ex);
        }
    }
    
    
     public void setProposalq4(String proposalq4)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq4(proposalq4);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q4", ex);
        }
    }
    
    
     public void setProposalq5(String proposalq5)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq5(proposalq5);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q5", ex);
        }
    }
    
        
     public void setProposalq6(String proposalq6)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq6(proposalq6);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q6", ex);
        }
    }
    
    
     public void setProposalq7(String proposalq7)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq7(proposalq7);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q7", ex);
        }
    }
    
    
     public void setProposalq8(String proposalq8)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setProposalq8(proposalq8);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Proposal Q8", ex);
        }
    }
    
    
      public void setBookmark(String bookmark)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setBookmark(bookmark);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting bookmark", ex);
        }
    }
   
   
   public void setPOnum(String POnum)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setPOnum(POnum);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's POnum", ex);
        }
    }
   
    // to be used as last activity date.
    public void setApprovalDate(java.sql.Date approvalDate)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setApprovalDate(approvalDate);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting approval date", ex);
        }
    } 
    
    
      public void setInstitute(String Institute)
        throws webscheduleBusinessException
    {
        try {
            this.myDO.setInstitute(Institute);
        } catch(DataObjectException ex) {
            throw new webscheduleBusinessException("Error setting Project's Institute", ex);
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
            throw new webscheduleBusinessException("Error saving Proposal", ex);
        }
    }
    
    public void delete() 
        throws webscheduleBusinessException
    {
        try {
            this.myDO.delete();
        } catch(Exception ex) {
            throw new webscheduleBusinessException("Error deleting Propsal", ex);
        }
    }
}
