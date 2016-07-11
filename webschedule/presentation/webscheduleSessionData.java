package webschedule.presentation;

import webschedule.business.person.Person;
import webschedule.business.project.Project;
import webschedule.business.operator.Operator;
import webschedule.MyDate;

//import webschedule.collections.*;
//import webschedule.collections.impl.LList;
import java.util.Enumeration;
import java.util.LinkedList;

public class webscheduleSessionData
{
    /**
     * Hash key to save session data for the DiscRack app in the Session
     */
    public static final String SESSION_KEY = "webscheduleSessionData";
    
    protected Person myUser = null;
    protected Project myProject = null;
    protected Project mySwitchProject = null;
    protected Operator myOperator = null;
    protected int myYear = 0;
    protected int myMonth = 0;
    protected int myDay = 0;
    protected String myS_eventID = null;
    //protected MyDate myeDate = null;
    protected String userMessage = null;
    protected int myMyeventflag = 0;
    protected int mySummaryflag = 0;
    protected int myBlockflag = 0;
   protected int myBillableflag = 0;
    protected String mybillreportflag = null;
    protected int mybillsday = 0;
    protected int mybilleday = 0;
    protected int mybillsmonth = 0;
    protected int mybillemonth = 0;
    protected int mybillmonth = 0;
    protected int mybillyear = 0;    
    protected String mybillprojID = null;
    protected String mybilldescription = null;	
    protected  String  myProb_id = null;
    protected  String  mypropflag = null;
    protected String myproposalID = null;
     protected String mypiID = null;
      protected String myprojectID = null;
      //holds the proposal type in session data
      protected String myPbookmark = null; 
    

    protected LinkedList myconflict_list = new LinkedList();


    protected LinkedList mygood_list = new LinkedList();


    

    public void setConflictlist (LinkedList conflict_list)
    {
    	this.myconflict_list = conflict_list;
    }

    public LinkedList getConflictlist ()
    {	
     	return  this.myconflict_list;
    }

    public void removeConflictlist()
    {
    	
     this.myconflict_list.clear();

    }


    public void setGoodlist (LinkedList good_list)
    {
    	this.mygood_list = good_list;
    }

    public LinkedList getGoodlist ()
    {	
     	return  this.mygood_list;
    }


    public void removeGoodlist()
    {
    this.mygood_list.clear();

    }


    /**
     * 
     */
    public void setUser(Person thePerson)
    {
        this.myUser = thePerson;
    }
   
    /**
     * 
     */
    public Person getUser()
    {
        return this.myUser;
    }    
    
    /**
    * Method to remove the current user from the session
    */
   public void removeUser()
   {
       this.myUser = null;
   }


   /* Method to set the current project */

       public void setProject(Project theProject)
    {
        this.myProject = theProject;
    }

    /**
     *
     */
    public Project getProject()
    {
        return this.myProject;
    }

    /**
    * Method to remove the current project from the session
    */
   public void removeProject()
   {
       this.myProject = null;
   }




/* Method to set the current operator */

       public void setOperator(Operator theOperator)
    {
        this.myOperator = theOperator;
    }

    /**
     *
     */
    public Operator getOperator()
    {
        return this.myOperator;
    }

    /**
    * Method to remove the current operator from the session
    */
   public void removeOperator()
   {
       this.myOperator = null;
   }



   /* Method to set the switched project (to be used when switching one of the events project */

       public void setSwitchProject(Project theSwitchProject)
    {
        this.mySwitchProject = theSwitchProject;
    }

    /**
     *
     */
    public Project getSwitchProject()
    {
        return this.mySwitchProject;
    }

    /**
    * Method to remove the current project from the session
    */
   public void removeSwitchProject()
   {
       this.mySwitchProject = null;
   }


   /* Method to set the current date */

/*       public void setDate(MyDate theDate)
    {
        this.myeDate = theDate;
    }*/


    public void setYear(int theYear)
    {
    	this.myYear = theYear;
    }

    public void setMonth(int theMonth)
    {
    	this.myMonth = theMonth;
    }
    	
    public void setDay(int theDay)
    {
    	this.myDay = theDay;
    }


    public void setS_eventID(String theS_eventID)
    {
    	this.myS_eventID = theS_eventID;
    }	

  public void setProb_id(String theProb_id)
    {
    	this.myProb_id = theProb_id;
    }	

    public void setMyeventflag(int theMyeventflag)
    {
    	this.myMyeventflag = theMyeventflag;
    }

    public void setSummaryflag(int theSummaryflag)
    {
    	this.mySummaryflag = theSummaryflag;
    }

    public void setBlockflag(int theBlockflag)
    {
    	this.myBlockflag = theBlockflag;
    }
    

    public void setBillableflag(int theBillableflag)
    {
    	this.myBillableflag = theBillableflag;
    }

    public void setbillsday(int thebillsday)
    {
    	this.mybillsday = thebillsday;
    }
    
     public void setbilleday(int thebilleday)
    {
    	this.mybilleday = thebilleday;
    }

    public void setbillmonth(int thebillmonth)
    {
    	this.mybillmonth = thebillmonth;
    }
     
     
      public void setbillsmonth(int thebillsmonth)
    {
    	this.mybillsmonth = thebillsmonth;
    }
    
     public void setbillemonth(int thebillemonth)
    {
    	this.mybillemonth = thebillemonth;
    }
     
     
     public void setbillyear(int thebillyear)
    {
    	this.mybillyear = thebillyear;
    }
    
        public void setbillprojID(String thebillprojID)
    {
    	this.mybillprojID = thebillprojID;
    }	

        public void setbillreportflag(String thebillreportflag)
    {
    	this.mybillreportflag = thebillreportflag;
    }	

    public void setbilldescription(String thebilldescription)
    {
    	this.mybilldescription = thebilldescription;
    }	
    
    
    /* 
    * added session var for proposal Mgmt
    */
    
    public void setPropFlag(String thepropflag)
    {
    this.mypropflag = thepropflag;
    }
    
  public void setProposalID(String theproposalID)
    {
    this.myproposalID = theproposalID;
    }
    
     public void setProjectID(String theprojectID)
    {
    this.myprojectID = theprojectID;
    }
    
      public void setPiID(String thepiID)
    {
    this.mypiID = thepiID;
    }
    
    /**
     *
     */
/*    public MyDate getDate()
    {
        return this.myeDate;
    }
  */


  public int getYear()
  {
     return this.myYear;
   }


    public int getMonth()
  {
     return this.myMonth;
   }


    public int getDay()
  {
     return this.myDay;
   }


   public String getS_eventID()
    {
    	return this.myS_eventID ;
    }

 public String getProb_id()
    {
    	return this.myProb_id ;
    }
    
     public int getMyeventflag()
  {
     return this.myMyeventflag;
   }	


   public int getSummaryflag()
  {
     return this.mySummaryflag;
   }	

    public int getBlockflag()
  {
     return this.myBlockflag;
   }	

  public int getBillableflag()
  {
     return this.myBillableflag;
   }


    public int getbillsday()
    {
    	return this.mybillsday;
    }
    
     public int getbilleday()
    {
      return this.mybilleday ;
    }

    public int getbillmonth()
    {
    	return this.mybillmonth;
    }
    
    
     public int getbillsmonth()
    {
    	return this.mybillsmonth;
    }
    
     public int getbillemonth()
    {
    	return this.mybillemonth;
    }
     
     public int getbillyear()
    {
    	return this.mybillyear;
    }
    
        public String getbillprojID()
    {
    	return this.mybillprojID ;
    }	


        public String getbillreportflag()
    {
    	return this.mybillreportflag;
    }	

        public String getbilldescription()
    {
    	return this.mybilldescription;
    }	


    /* 
    * added session var for proposal Mgmt
    */
    
    
     public String getPropFlag()
    {
    	return this.mypropflag;
    }	
    
     public String getProposalID()
    {
    	return this.myproposalID;
    }	
    
     public String getProjectID()
    {
    	return this.myprojectID;
    }	
     public String getPiID()
    {
    	return this.mypiID;
    }	 

    /**
    * Method to remove the current date from the session
    */
  /* public void removeDate()
   {
       this.myeDate = null;
   }*/



   public void removeYear()
   {
       this.myYear = 0;
   }

   public void removeMonth()
   {
       this.myMonth = 0;
   }

   public void removeDay()
   {
       this.myDay = 0;
   }

   public void removeS_eventID()
    {
    	this.myS_eventID  = null;
    }	


     public void removeMyeventflag()
   {
       this.myMyeventflag = 0;
   }

    public void removeSummaryflag()
   {
       this.mySummaryflag = 0;
   }

        public void removebillreportflag()
    {
    	this.mybillreportflag = null;
    }	


    public void setUserMessage(String msg)
    {
        this.userMessage = msg;
    }




    public void removebillsday()
    {
    	this.mybillsday = 0;
    }
    
     public void removebilleday()
    {
      this.mybilleday = 0 ;
    }

    public void removebillmonth()
    {
    	this.mybillmonth = 0;
    }
     
     
       public void removebillsmonth()
    {
    	this.mybillsmonth = 0;
    }
    
      public void removebillemonth()
    {
    	this.mybillemonth = 0;
    }
     
     public void removebillyear()
    {
    	this.mybillyear = 0;
    }
    
        public void removebillprojID()
    {
    	this.mybillprojID = null;
    }	
    
      public void removeProb_id()
    {
    	this.myProb_id = null;
    }	
       
       
        public void removebilldescription()
    {
    	this.mybilldescription = null;
    }	

        public void removePropFlagFromSession()
    {
    	this.mypropflag = null;
    }	


 public void removeProposalIDFromSession()
    {
    	this.myproposalID = null;
    }	
   
   
    public void removePiIDFromSession()
    {
    	this.mypiID = null;
    }	
    
     public void removeProjectIDFromSession()
    {
    	this.myprojectID = null;
    }	
    
    
      public String getPbookmark()
    {
    	return this.myPbookmark ;
    }

    public void setPbookmark(String thepbookmark)
    {
    this.myPbookmark = thepbookmark;
    }
    
    
      public void removePbookmark()
    {
    	this.myPbookmark = null;
    }	
    
    /**
     * Retrieve the most recent user message and then clear it so no
     * other app tries to use it.
     */
    public String getAndClearUserMessage()
    {
        String msg = this.userMessage;
        this.userMessage = null;
        return msg;
    }
}
