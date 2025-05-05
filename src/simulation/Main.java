package src.simulation;

public class Main {
    public static void main(String[] args) {
        //Set up the simulation
        RumourSimulation simulation = new RumourSimulation(1100, 20, 0.9);
        
        // Run the simulation
        simulation.run();
        
        // Create Stats from the simulation
        Stats stats = Stats.fromSimulation(simulation);
        
        // Print the stats using the printStats method in Stats
        stats.printStats();
    }
}
