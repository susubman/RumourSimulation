package src.model;

import java.util.*;

public class Network {
    private final List<Person> people = new ArrayList<>();
    private final Map<Person, List<Person>> connections = new HashMap<>();
    private final Random random = new Random();

    // Create network
    public Network(int populationSize, int numGroups) {
        createPeople(populationSize, numGroups);
        connectGroupMembers();
        createRandomConnections(populationSize);
    }

    private void createPeople(int populationSize, int numGroups) {
        for (int i = 0; i < populationSize; i++) {
            // Adds group ID an gullibility number to each person
            int groupId = random.nextInt(numGroups);
            double gullibility = 0.3 + random.nextDouble() * 0.7; // Range: 0.3 to 1.0
            var person = new Person(groupId, gullibility);
            people.add(person);
            connections.put(person, new ArrayList<>());
        }
    }

    private void connectGroupMembers() {
        for (var person : people) {
            for (var other : people) {
                if (person != other && person.getGroupId() == other.getGroupId()) {
                    connections.get(person).add(other);
                }
            }
        }
    }

    private void createRandomConnections(int numberOfConnections) {
        for (int i = 0; i < numberOfConnections; i++) {
            var p1 = getRandomPerson();
            var p2 = getRandomPerson();

            if (p1 != p2 && !connections.get(p1).contains(p2)) {
                connections.get(p1).add(p2);
                connections.get(p2).add(p1);
            }
        }
    }

    public List<Person> getPeople() {
        return people;
    }

    public Person getRandomPerson() {
        return people.get(random.nextInt(people.size()));
    }

    public boolean isRumourStillSpreading() {
        for (Person spreader : people) {
            if (spreader.getState() == Person.State.SPREADER) {
                for (Person neighbour : connections.get(spreader)) {
                    if (neighbour.getState() == Person.State.IGNORANT) {
                        return true; // Can still spread
                    }
                }
            }
        }
        return false; // No more possible spread
    }    

    public void spreadRumour(double spreadProbability) {
        List<Person> newSpreaders = new ArrayList<>();
        final double factCheckChance = 0.3;
    
        for (var spreader : people) {
            if (spreader.getState() != Person.State.SPREADER) continue;
    
            var neighbours = new ArrayList<>(connections.get(spreader));
            if (neighbours.isEmpty()) continue;
    
            Collections.shuffle(neighbours, random); // Shuffle to get random order
    
            boolean spread = false;
    
            // Try up to 3 different neighbours
            for (int i = 0; i < Math.min(3, neighbours.size()); i++) {
                var target = neighbours.get(i);
    
                if (target.getState() == Person.State.IGNORANT && shouldSpreadTo(target, spreadProbability)) {
                    if (random.nextDouble() < factCheckChance) {
                        target.setFactChecker(true);
                        target.setState(Person.State.STIFLER);
                    } else {
                        newSpreaders.add(target);
                    }
                    spread = true;
                    break; // stop trying if successful
                }
            }
    
            // Become stifler only if nothing spread and not a fact checker
            if (!spread && !spreader.isFactChecker()) {
                spreader.setState(Person.State.STIFLER);
            }
        }
    
        newSpreaders.forEach(p -> p.setState(Person.State.SPREADER));
    }
    

    private boolean shouldSpreadTo(Person target, double spreadProbability) {
        return random.nextDouble() < spreadProbability * target.getGullibility();
    }
}
