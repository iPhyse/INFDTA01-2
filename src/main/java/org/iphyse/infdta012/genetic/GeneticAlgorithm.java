package org.iphyse.infdta012.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author RICKRICHTER
 */
public class GeneticAlgorithm<T> {
    private final Comparator<Individual<T>> COMPARATOR = (left, right) -> getFitness(right).compareTo(getFitness(left));
    private final Map<T, Double> FITNESS = new HashMap<>();
    private final int BITS;
    private final double CROSSOVER_RATE;
    private final double MUTATION_RATE;
    private final boolean ELITISM;
    private List<Individual<T>> population;
    private final Function<T, Double> FITNESS_ALGORITHM;

    public GeneticAlgorithm(int bits, double crossoverRate, double mutationRate, boolean elitism, List<Individual<T>> population, Function<T, Double> fitnessAlgorithm) {
        this.BITS = bits;
        this.CROSSOVER_RATE = crossoverRate;
        this.MUTATION_RATE = mutationRate;
        this.ELITISM = elitism;
        this.population = population;
        this.FITNESS_ALGORITHM = fitnessAlgorithm;
    }

    public List<Individual<T>> run(int amountIterations) {
        for(int i = 0; i < amountIterations; i++) {
            population.sort(COMPARATOR);
            createNewPopulation();
        }
        population.sort(COMPARATOR);
        return population;
    }

    private void createNewPopulation() {
        int iterations = population.size();
        List<Individual<T>> newPopulation = new ArrayList<>(population.size());
        if(ELITISM) {
            iterations--;
            newPopulation.add(population.get(0));
        }
        double fitnessSum = 0;
        for(Individual<T> individual : population) {
            fitnessSum += getFitness(individual);
        }
        while(iterations > 0) {
            Collection<Individual<T>> children = getChildren(fitnessSum);
            for(Individual<T> child : children) {
                if(newPopulation.size() < population.size()) {
                    newPopulation.add(child.mutate(MUTATION_RATE, BITS));
                } else {
                    iterations = 0;
                    break;
                }
            }
        }
        population = newPopulation;
    }

    private Collection<Individual<T>> getChildren(double fitnessSum) {
        Individual<T> firstParent = selectParent(fitnessSum, null);
        Individual<T> secondParent = selectParent(fitnessSum - getFitness(firstParent), firstParent);
        if(Math.random() < CROSSOVER_RATE) {
            return Arrays.asList(firstParent.breed(secondParent), secondParent.breed(firstParent));
        }
        return Arrays.asList(firstParent, secondParent);
    }

    private Individual<T> selectParent(double fitnessSum, Individual<T> skip) {
        double sum = 0;
        double random = fitnessSum * Math.random();
        Individual<T> last = null;
        for(Individual<T> individual : population) {
            if(individual == skip) {
                continue;
            }
            last = individual;
            sum += getFitness(individual) / fitnessSum;
            if(random < sum) {
                return individual;
            }
        }
        return last;
    }

    public Double getFitness(Individual<T> individual) {
        Double value = FITNESS.get(individual.getValue());
        if(value == null) {
            value = FITNESS_ALGORITHM.apply(individual.getValue());
            FITNESS.put(individual.getValue(), value);
        }
        return value;
    }
}
