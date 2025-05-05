package src.simulation;

import src.model.Person;
import src.model.Network;

public class Stats {
    public final int ignorant, spreader, stifler;
    public final int factCheckers, notFactCheckers;
    public final int timeSteps;

    // Private constructor to force use of the factory method
    private Stats(int ignorant, int spreader, int stifler, int factCheckers, int notFactCheckers, int timeSteps) {
        this.ignorant = ignorant;
        this.spreader = spreader;
        this.stifler = stifler;
        this.factCheckers = factCheckers;
        this.notFactCheckers = notFactCheckers;
        this.timeSteps = timeSteps;
    }

    // Factory method to create Stats from a RumourSimulation
    public static Stats fromSimulation(RumourSimulation sim) {
        int ignorant = 0, spreader = 0, stifler = 0;
        int factCheckers = 0, notFactCheckers = 0;

        Network network = sim.getNetwork(); // Getting the network from the simulation
        for (Person p : network.getPeople()) {
            switch (p.getState()) {
                case IGNORANT -> ignorant++;
                case SPREADER -> spreader++;
                case STIFLER -> stifler++;
            }
            if (p.isFactChecker()) {
                factCheckers++;
            } else if (p.getState() != Person.State.IGNORANT) {
                notFactCheckers++;
            }
        }

        return new Stats(ignorant, spreader, stifler, factCheckers, notFactCheckers, sim.getTimeSteps());
    }

    // Helper method to print stats nicely
    public void printStats() {
        System.out.println("Rumour stopped after " + timeSteps + " time steps.");
        System.out.println("Final states:");
        System.out.println("Ignorant: " + ignorant);
        System.out.println("Spreaders: " + spreader);
        System.out.println("Stiflers: " + stifler);
        System.out.println("Fact Checkers: " + factCheckers);
        System.out.println("Non Fact Checkers: " + notFactCheckers);
    }
}

