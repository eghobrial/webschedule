package webschedule.presentation.s_eventMgmt;


  public class temp_hold_change {
      public int starth, startm, endh, endm, eventday, eventmonth, eventyear;
      public int eventdayofw;
      public String  description, projID;

      public temp_hold_change (String projID, String description, int starth,int startm,int endh,int endm,int eventday,int eventmonth,int eventyear,int eventdayofw)
      	{
		this.projID = projID;
		this.description = description;
      		this.starth = starth;
      		this.startm = startm;
      		this.endh = endh;
      		this.endm = endm;
      		this.eventday = eventday;
      		this.eventmonth = eventmonth;
      		this.eventyear = eventyear;      		      		
      		this.eventdayofw = eventdayofw;
         }

	public String getprojID() { return projID;}
      public String getcdescription() { return description;}	
      public int getcstarth() { return starth;}		
      public int getcstartm() { return startm;}		
      public int getcendh() { return endh;}		
      public int getcendm() { return endm;}		
      public int getceventday() { return eventday;}
      public int getceventmonth() { return eventmonth;}
      public int getceventyear() { return eventyear;}
      public int getceventdayofw() { return eventdayofw;}             		
 }


