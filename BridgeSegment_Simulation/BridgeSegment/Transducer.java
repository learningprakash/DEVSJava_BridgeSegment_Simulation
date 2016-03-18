package Homework2014.BridgeSegment;

import genDevs.modeling.message;
import GenCol.entity;
import simView.ViewableAtomic;

public class Transducer extends ViewableAtomic
{		
	public Transducer(){
		this("transducer");
	}
	
	public Transducer(String name){
		super(name);
		
	}
	@Override
	public void initialize()
	{
		this.passivate();
	}
	
	@Override
	public void deltint()
	{
		this.passivate();
	}
	
	@Override
	public void deltext(double e, message x)
	{
		
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "Bridge1_EastOut", i))
				{ 
					entity car = x.getValOnPort("Bridge1_EastOut", i);
					AbstractBridgeSystem p = (AbstractBridgeSystem)this.getParent();
		            p.outputExitEvent(car.getName() , "BridgeSegment1", this.getSimulationTime());
					
				}
				else if (messageOnPort(x, "Bridge2_EastOut", i))
				{
					entity car = x.getValOnPort("Bridge2_EastOut", i);
					AbstractBridgeSystem p = (AbstractBridgeSystem)this.getParent();
		            p.outputExitEvent(car.getName() , "BridgeSegment2", this.getSimulationTime());
				}
				else if (messageOnPort(x, "Bridge3_EastOut", i))
				{
					entity car = x.getValOnPort("Bridge3_EastOut", i);
					AbstractBridgeSystem p = (AbstractBridgeSystem)this.getParent();
					/*System.out.println("car: " + car);
					System.out.println("p: " + p);*/
		            p.outputExitEvent(car.getName() , "BridgeSegment3", this.getSimulationTime());
				}
				else if (messageOnPort(x, "Bridge1_WestOut", i))
				{ 
					entity car = x.getValOnPort("Bridge1_WestOut", i);
					AbstractBridgeSystem p = (AbstractBridgeSystem)this.getParent();
		            p.outputExitEvent(car.getName() , "BridgeSegment1", this.getSimulationTime());
					
				}
				else if (messageOnPort(x, "Bridge2_WestOut", i))
				{
					entity car = x.getValOnPort("Bridge2_WestOut", i);
					AbstractBridgeSystem p = (AbstractBridgeSystem)this.getParent();
		            p.outputExitEvent(car.getName() , "BridgeSegment2", this.getSimulationTime());
				}
				else if (messageOnPort(x, "Bridge3_WestOut", i))
				{
					entity car = x.getValOnPort("Bridge3_WestOut", i);
					AbstractBridgeSystem p = (AbstractBridgeSystem)this.getParent();
					/*System.out.println("car: " + car);
					System.out.println("p: " + p);*/
		            p.outputExitEvent(car.getName() , "BridgeSegment3", this.getSimulationTime());
				}
			}
		
	} 

}
