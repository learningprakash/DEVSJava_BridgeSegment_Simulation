package Homework2014.BridgeSegment.pchourasia;

import simView.*;

import java.lang.*;

import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import Homework2014.BridgeSegment.AbstractBridgeSystem.BridgeState;
import util.*;
import statistics.*;

public class bridgeSegment extends ViewableAtomic{

  protected double carTravelTime;
  protected double trafficlightdurationtime;
  protected double SignalRemainingTime;
  protected entity car, currentcar = null;
  protected BridgeState stateofbridge;
  protected DEVSQueue q1,q2;

  public bridgeSegment() 
  {
  	this("bridgeSegment");
  	//trafficlightdurationtime = TLdurationtime;
  	//phase = BridgeState;
  }

public bridgeSegment(String name)
      {
      super(name);
      
      addInport("WestcarIn");
      addInport("EastcarIn");
      addOutport("WestcarOut");
      addOutport("EastcarOut");
      /*trafficlightdurationtime = AbstractBridgeSystem.BridgeSystemSetting.Bridge1TrafficLightDurationTime;
      stateofbridge="";
      if (AbstractBridgeSystem.BridgeSystemSetting.Bridge3InitialState == AbstractBridgeSystem.BridgeState.WEST_TO_EAST)
    	  stateofbridge= "West_To_East";
      else if (AbstractBridgeSystem.BridgeSystemSetting.Bridge3InitialState == AbstractBridgeSystem.BridgeState.EAST_TO_WEST)
    	  stateofbridge= "East_To_West";*/
      }
public bridgeSegment(String name, BridgeState bState, double TLTime)
{
	this(name);
	stateofbridge = bState;
	trafficlightdurationtime = TLTime;
	funcinitialize();
}
public void funcinitialize()
  {
  	SignalRemainingTime = trafficlightdurationtime;
  	sigma = trafficlightdurationtime; 
       //holdIn(stateofbridge, sigma);
       phase = stateofbridge.toString();
     if(phase == "WEST_TO_EAST")
    	   phase = "West_To_East";      // Test Cases Values will be handled here
       else if(phase== "EAST_TO_WEST")
    	   phase = "East_To_West";
     holdIn(phase, sigma);
  }
public void initialize()
{
 	carTravelTime = 10;
  	currentcar = null;
  	q1 = new DEVSQueue();
    q2 = new DEVSQueue();
    
}
 public void  deltext(double e,message x)
{

Continue(e);
//SignalRemainingTime = SignalRemainingTime - e;

for (int i=0; i< x.getLength();i++)
	   {
		   if (messageOnPort(x, "EastcarIn", i) )
          {
	   
			   if (phaseIs("East_To_West") || phaseIs("EtoW") && currentcar==null)
			   {
				   SignalRemainingTime = SignalRemainingTime - e;
				   if(q2.isEmpty())
				   {
					   if(SignalRemainingTime >= carTravelTime)
					   {
						   SignalRemainingTime = SignalRemainingTime-carTravelTime ;
						   car = x.getValOnPort("EastcarIn", i);
						   currentcar = car;
						   holdIn("EtoW", carTravelTime);
						   System.out.println("Again you are here East car come directly on bridge");
	   
					   }
					   else
					   {
						   car = x.getValOnPort("EastcarIn", i);
						   q2.add(car);
						   System.out.println("Queing up east car");
              
					   }
	   
				   }
				else
				{
					car = x.getValOnPort("EastcarIn", i);
					q2.add(car);
					System.out.println("Queing up in Mars(Stage 2) I Dont Care but is east car");
      
				}
				   
				}
			   else if (!q2.isEmpty() || currentcar != null )
			   {
				     car = x.getValOnPort("EastcarIn", i);
		             q2.add(car);
		             System.out.println("Queing up east car in stage 2");
		          
			   }
			 }
		   

		   else if (messageOnPort(x, "WestcarIn", i) )
          {
	   
			   if (phaseIs("West_To_East") || phaseIs("WtoE") && currentcar==null)
			   {
				   SignalRemainingTime = SignalRemainingTime - e;
				   if(q1.isEmpty())
				   {
					   if(SignalRemainingTime >= carTravelTime)
					   {
						   SignalRemainingTime = SignalRemainingTime-carTravelTime ;
						   car = x.getValOnPort("WestcarIn", i);
						   currentcar = car;
						   holdIn("WtoE", carTravelTime);
						   System.out.println("Again you are here when No car on Bridge and car comes directly without queue");
	   
					   }
					   else
					   {
						   car = x.getValOnPort("WestcarIn", i);
						   q1.add(car);
						   System.out.println("Queing up west car");
              
					   }
	   
				   }
				else
				{
					car = x.getValOnPort("WestcarIn", i);
					q1.add(car);
					System.out.println("Queing up in Mars (Stage 2) I Dont Care but its west car");
      
				}
				   
				}
			   else if (!q1.isEmpty() || currentcar != null )
			   {
				     car = x.getValOnPort("WestcarIn", i);
		             q1.add(car);
		             System.out.println("Queing up West car in stage 2");
		          
			   }
			 }
		   
		   
		   
			}
		   }
 // ----------------------------West Car Handled below ----------------------//
		   
 
 
 public void  deltint( )
{
	 if (phaseIs("EtoW"))
	 {

		  if ((SignalRemainingTime >= carTravelTime) && (!q2.isEmpty()))
		 {
					
			SignalRemainingTime = SignalRemainingTime - carTravelTime;
			car = (entity)q2.first();
			currentcar = car;
			holdIn("EtoW", carTravelTime);
			q2.remove();
			
		 }
		 else if ((SignalRemainingTime >= carTravelTime) && (q2.isEmpty()))
		 {
			holdIn("East_To_West", SignalRemainingTime);
			currentcar= null;
		 }
		 else if (SignalRemainingTime < carTravelTime)
			{
				System.out.println("Signal will be green for few more secs");
				holdIn("East_To_West", SignalRemainingTime);
				currentcar=null;
				
			}
	
		  if(SignalRemainingTime <= 0)
			{
				
				holdIn("East_To_West", SignalRemainingTime);
			}

		
	 }

	 
	 else if (phaseIs("WtoE"))
	 {

		  if ((SignalRemainingTime >= carTravelTime) && (!q1.isEmpty()))
		 {
					
			SignalRemainingTime = SignalRemainingTime - carTravelTime;
			car = (entity)q1.first();
			currentcar = car;
			holdIn("WtoE", carTravelTime);
			q1.remove();
			
		 }
		 else if ((SignalRemainingTime >= carTravelTime) && (q1.isEmpty()))
		 {
			holdIn("West_To_East", SignalRemainingTime);
			currentcar= null;
		 }
		 else if (SignalRemainingTime < carTravelTime)
			{
				System.out.println("Signal will be green for few more secs");
				holdIn("West_To_East", SignalRemainingTime);
				currentcar=null;
				
			}
	
	        if(SignalRemainingTime <= 0)
				{
					// SignalRemainingTime = trafficlightdurationtime;
					holdIn("West_To_East", SignalRemainingTime);
				}

		
	 }

	 
	 else if(phaseIs("East_To_West") || phaseIs("West_To_East"))
   {
	


			SignalRemainingTime = trafficlightdurationtime;
			 if (phaseIs("East_To_West")) 
			 {
                if (q1.isEmpty())
                {
                 holdIn("West_To_East", trafficlightdurationtime);
                }
                else
                {
                car = (entity)q1.first();
        		currentcar = car;
        		holdIn("WtoE", carTravelTime);
        		q1.remove();
                }
		     }
			 else if (phaseIs("West_To_East")) 
			 {
                if (q2.isEmpty())
                {
                 holdIn("East_To_West", trafficlightdurationtime);
                }
                else
                {
                car = (entity)q2.first();
        		currentcar = car;
        		holdIn("EtoW", carTravelTime);
        		q2.remove();
                }
		     }
   }
			  
}


public message  out()
{
	
	 message  m = new message();
	
	 if (phaseIs("WtoE"))
	 {
		m.add(makeContent("WestcarOut", currentcar));
	 }
	
	 if (phaseIs("EtoW"))
	 {
		m.add(makeContent("EastcarOut", currentcar));
	 }
	 
	 
	 return m; 
}


}

