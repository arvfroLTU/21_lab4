package Sim;

import java.util.Random;

public class LossyLink extends Link{
private SimEnt _connectorA=null;
private SimEnt _connectorB=null;
private int _now=0;

private int delay = 0;
private int jitter = 0;
private int probDrop = 0;

Random randInt = new Random();
int randomInt = randInt.nextInt(100);

public LossyLink(int delay, int jitter, int probDrop) {
super();
this.delay = delay;
this.jitter = jitter;
this.probDrop = probDrop;
}

public void setConnector(SimEnt connectTo)
{
if (_connectorA == null) 
_connectorA=connectTo;
else
_connectorB=connectTo;
}

public void recv(SimEnt src, Event ev)
{
int jitterInterval = randInt.nextInt((this.jitter)*2) -this.jitter;  //allows for positive and negative values
if (ev instanceof Message)
{
if (randomInt < probDrop) {
System.out.println("Unlucky, LossyLink dropped your package! :/");
return;
} else {
System.out.println("Link recv msg, passes it through");
if (src == _connectorA)
{
send(_connectorB, ev, _now + delay + jitterInterval);
}
else
{
send(_connectorA, ev, _now + delay + jitterInterval);
}
}

}
}
}