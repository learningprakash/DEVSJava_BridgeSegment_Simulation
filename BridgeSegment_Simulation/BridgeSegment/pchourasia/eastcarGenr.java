package Homework2014.BridgeSegment.pchourasia;

import simView.*;

import genDevs.modeling.*;
import GenCol.*;
import statistics.*;

public class eastcarGenr extends ViewableAtomic
{
    protected double int_gen_time;
    protected int count;
    protected rand r;

    public String getSettingString()
    {
    	return "MeanInterval=" + int_gen_time;
    }
    
    public eastcarGenr() 
    {
        this("carGenr", 7);
    }

    public eastcarGenr(String name, double period)
    {
        super(name);
        addInport("in");
        addOutport("out");

        int_gen_time = period ;
    }

    public void initialize()
    {
        r = new rand(12345);
        holdIn("active", r.uniform(int_gen_time));
        count = 0;
    }

    public void deltext(double e, message x) 
    {
        Continue(e);
        for (int i=0; i< x.getLength();i++)
        {
            if (messageOnPort(x, "in", i)) 
            { 
                //the stop message from tranducer
                passivate();
            }
        }
    }

    public void deltint( ) 
    {
        if(phaseIs("active"))
        {
            count = count +1;
            //   holdIn("active",int_gen_time);
            holdIn("active",r.uniform(int_gen_time));
        } 
        else 
            passivate();
    }

    public message out( ) 
    {
        //System.out.println(name+" out count "+count);
        message m = new message();
        content con = makeContent("out", new entity(name + "_Car" + count));
        m.add(con);
        //System.out.println(name + "_Car" + count + " \t" + this.getSimulationTime());
        return m;
    }
   
}

