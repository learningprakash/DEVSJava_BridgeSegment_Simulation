package Homework2014.BridgeSegment;

import Homework2014.BridgeSegment.pchourasia.bridgeSystem;
import genDevs.simulation.coordinator;
import genDevs.simulation.heapSim.HeapCoord;

//import homework2013.bridgeSegment.hXue.BridgeSystem; // change it to your bridge system class

/**
 * The class performing basic test for a bridge system Note: when performing your test, you need to change the BridgeSystem class to yours
 * 
 * @author Haidong
 */
public class Test
{
	public static void main(String[] args)
	{
		// Set the initial states for the system
		AbstractBridgeSystem.BridgeSystemSetting.Bridge3InitialState = AbstractBridgeSystem.BridgeState.WEST_TO_EAST;
		AbstractBridgeSystem.BridgeSystemSetting.Bridge3TrafficLightDurationTime = 100;
		AbstractBridgeSystem.BridgeSystemSetting.Bridge2InitialState = AbstractBridgeSystem.BridgeState.WEST_TO_EAST;
		AbstractBridgeSystem.BridgeSystemSetting.Bridge2TrafficLightDurationTime = 200;
		AbstractBridgeSystem.BridgeSystemSetting.Bridge1InitialState = AbstractBridgeSystem.BridgeState.WEST_TO_EAST;
		AbstractBridgeSystem.BridgeSystemSetting.Bridge1TrafficLightDurationTime = 200;

		// Create a bridge system object
		AbstractBridgeSystem sys = new bridgeSystem("a_bridge_system"); // change it to your bridge system class

		// Simulate the system
		//HeapCoord r = new HeapCoord(sys);
		
		coordinator r = new coordinator(sys);
		r.initialize();
		System.out.println("Simulation started");
		r.simulate(100);
		System.out.println("Simulation finished");

		// Show results
		sys.printResults();

	}

}
