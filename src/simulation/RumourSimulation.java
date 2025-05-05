package src.simulation;

import src.model.Network;
import src.model.Person;

public class RumourSimulation {
    private Network network;
    private int timeSteps;
    private double spreadProbability;

    public RumourSimulation(int populationSize, int numGroups, double spreadProbability) {
        this.network = new Network(populationSize, numGroups);
        this.timeSteps = 0;
        this.spreadProbability = spreadProbability;

        // Initialise one random spreader
        Person randomPerson = network.getRandomPerson();
        randomPerson.setState(Person.State.SPREADER);
    }

    public void run() {
        while (network.isRumourStillSpreading()) {
            network.spreadRumour(spreadProbability);
            timeSteps++;
        }
    }
    

    // Getter methods for network and timeSteps
    public Network getNetwork() {
        return network;
    }

    public int getTimeSteps() {
        return timeSteps;
    }
}
