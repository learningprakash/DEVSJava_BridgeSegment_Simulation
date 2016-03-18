package Homework2014.BridgeSegment;

import java.io.PrintWriter;
import java.util.Vector;

import simView.ViewableDigraph;

/**
 * The abstract bridge system class
 * 
 * @author Haidong
 */
public abstract class AbstractBridgeSystem extends ViewableDigraph
{
	private Vector<ExitEvent> results = new Vector<ExitEvent>();
	protected CarGenerator westCarGenerator = new CarGenerator("WestGen", 40);
	protected CarGenerator eastCarGenerator = new CarGenerator("EastGen", 5);
	protected Transducer transduser = new Transducer();

	public String getCarGeneratorString()
	{
		return "W-" + westCarGenerator.getSettingString() + " E-" + eastCarGenerator.getSettingString();
	}

	public AbstractBridgeSystem(String name)
	{
		super(name);
	}

	public void outputExitEvent(String carName, String bridge, double systemTime)
	{
		results.add(new ExitEvent(carName, bridge, systemTime));
	}

	public static enum BridgeState
	{
		WEST_TO_EAST, EAST_TO_WEST;
	}

	public static class ExitEvent
	{
		private String carName;
		private String bridgeName;
		private double systemTime;

		public ExitEvent(String car, String bridge, double time)
		{
			this.carName = car;
			this.systemTime = time;
			this.bridgeName = bridge;
		}

		public String getCarName()
		{
			return carName;
		}

		public double getSystemTime()
		{
			return systemTime;
		}

		public String getBridgeName()
		{
			return bridgeName;
		}
	}

	public static class BridgeSystemSetting
	{
		// Bridge1, the east most one
		public static BridgeState Bridge1InitialState = BridgeState.WEST_TO_EAST;
		public static double Bridge1TrafficLightDurationTime = 200;
		// Bridge2, the middle one
		public static BridgeState Bridge2InitialState = BridgeState.WEST_TO_EAST;
		public static double Bridge2TrafficLightDurationTime = 200;
		// Bridge3, the west most one
		public static BridgeState Bridge3InitialState = BridgeState.WEST_TO_EAST;
		public static double Bridge3TrafficLightDurationTime = 200;

		public static String getSettingString()
		{
			return Bridge3InitialState.toString() + "-" + Double.toString(Bridge3TrafficLightDurationTime) + " " + Bridge2InitialState.toString() + "-"
					+ Double.toString(Bridge2TrafficLightDurationTime) + " " + Bridge1InitialState.toString() + "-"
					+ Double.toString(Bridge1TrafficLightDurationTime);
		}
	}

	public void printResults()
	{
		System.out.println("Event number: " + this.results.size());
		for (ExitEvent e : this.results)
			System.out.println(e.carName + " exits from " + e.bridgeName + " at " + e.systemTime);
	}

	/**
	 * Note: if an event is different from the one here, but they have the same system time, it is not considered as a error
	 * 
	 * @param anotherResults
	 * @return the portion of correct results
	 */
	public double compareResults(AbstractBridgeSystem anotherSys)
	{
		Vector<ExitEvent> anotherResults = anotherSys.results;

		if (this.results.size() == 0 && anotherResults.size() == 0)
			return 1;
		
		if (this.results.size() == 0 && anotherResults.size() != 0)
			return 0;

		int size = Math.min(this.results.size(), anotherResults.size());

		double correctEventNumber = 0;

		for (int i = 0; i < size; i++)
		{
			ExitEvent e1 = results.elementAt(i);
			ExitEvent e2 = anotherResults.elementAt(i);
			if (Math.abs(e1.systemTime - e2.systemTime) < 1e-5)
				correctEventNumber++;
			else
				break;
		}

		return correctEventNumber / this.results.size();

	}

	
	public void printResultComparison(AbstractBridgeSystem anotherSys) // correct event is defined as a same event time only
	{
		int n = Math.min(this.results.size(), anotherSys.results.size());
		Vector<ExitEvent> anotherResults = anotherSys.results;

		System.out.println("event number: " + this.results.size());
		System.out.println("event number in comparison: " + anotherSys.results.size());

		for (int i = 0; i < n; i++)
		{
			ExitEvent e1 = results.elementAt(i);
			ExitEvent e2 = anotherResults.elementAt(i);
			System.out.println("Real: " + e1.carName + " exits from " + e1.bridgeName + " at " + e1.systemTime);
			System.out.print("Simulated: " + e2.carName + " exits from " + e2.bridgeName + " at " + e2.systemTime);
			if (/*e1.bridgeName.equals(e2.bridgeName) && e1.carName.equals(e2.carName) && */Math.abs(e1.systemTime - e2.systemTime) <= 1e-5)
				System.out.println(" ===> right.");
			else
				System.out.println(" ===> wrong.");
		}

		if (this.results.size() < anotherSys.results.size())
			System.out.println(" and the system in test has more events than the correct system");
		if (this.results.size() > anotherSys.results.size())
			System.out.println(" and the correct system has more events than the system in test");
	}
	
	public void printResultComparison(AbstractBridgeSystem anotherSys, PrintWriter pw) // correct event is defined as a same event time only
	{
		int n = Math.min(this.results.size(), anotherSys.results.size());
		Vector<ExitEvent> anotherResults = anotherSys.results;

		pw.println("A relaxed grading rule is employed: a event is considered as correct if it matches a real event in the correct order");
		
		pw.println("Event number in the real system: " + this.results.size());
		pw.println("Event number in your simulation: " + anotherSys.results.size());

		boolean firstWrong = false;
		for (int i = 0; i < n; i++)
		{
			ExitEvent e1 = results.elementAt(i);
			ExitEvent e2 = anotherResults.elementAt(i);
			pw.println("Real: " + e1.carName + " exits from " + e1.bridgeName + " at " + e1.systemTime);
			pw.print("Simulated: " + e2.carName + " exits from " + e2.bridgeName + " at " + e2.systemTime);
			if (!firstWrong && /*e1.bridgeName.equals(e2.bridgeName) && e1.carName.equals(e2.carName) && */Math.abs(e1.systemTime - e2.systemTime) <= 1e-5)
				pw.println(" ===> right.");
			else
			{ 
				firstWrong = true;
				pw.println(" ===> wrong.");
			}
		}

		if (this.results.size() < anotherSys.results.size())
		{
			for(int i=n; i<anotherSys.results.size(); i++)
			{
				ExitEvent e2 = anotherResults.elementAt(i);
				pw.print("Simulated: " + e2.carName + " exits from " + e2.bridgeName + " at " + e2.systemTime);
				pw.println(" ===> wrong.");
			}
		}
		if (this.results.size() > anotherSys.results.size())
		{
			for(int i=n; i<this.results.size(); i++)
			{
				ExitEvent e1 = results.elementAt(i);
				pw.println("Real: " + e1.carName + " exits from " + e1.bridgeName + " at " + e1.systemTime);
			}
		}
	}

}
