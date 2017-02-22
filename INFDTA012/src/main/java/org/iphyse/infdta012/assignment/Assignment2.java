package org.iphyse.infdta012.assignment;



import org.iphyse.infdta012.genetic.ByteIndividual;
import org.iphyse.infdta012.genetic.GeneticAlgorithm;
import org.iphyse.infdta012.genetic.Individual;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author RICKRICHTER
 */
public class Assignment2 {
    private static final int BITS = 5;
        private static double crossover, mutation;
        private static boolean elitism;
        private static int population, iterations;
        
        public Assignment2(double crossover, double mutation, boolean elitism, int population, int iterations){
            Assignment2.crossover = crossover;
            Assignment2.mutation = mutation;
            Assignment2.elitism = elitism;
            Assignment2.population = population;
            Assignment2.iterations = iterations;
        }
        
	public static void executeAssignment() {
            double crossoverRate = crossover;
            double mutationRate = mutation;
            int populationSize = population;
            int numIterations = iterations;
            if(populationSize < 2) {
                System.err.println("Not enough possible parents");
                return;
            }
            List<Individual<Byte>> listPopulation = initPopulation(populationSize);
            Function<Byte, Double> fitnessAlgorithm = b -> {
                int x = b & 0xFF;
                return (double) (x * (7 - x));
            };

            GeneticAlgorithm<Byte> algorithm = new GeneticAlgorithm<>(BITS, crossoverRate, mutationRate, elitism, listPopulation, fitnessAlgorithm);
            printResult(algorithm.run(numIterations), algorithm);
	}

	private static void printResult(List<Individual<Byte>> population, GeneticAlgorithm<Byte> algorithm) {
            //System.out.println(population);
            double avgFitness = 0;
            //for(Individual<Byte> individual : population) {
            //    avgFitness += algorithm.getFitness(individual);
            //}
            // Functional solution
            avgFitness = population.stream().map((individual) -> algorithm.getFitness(individual)).reduce(avgFitness, (accumulator, _item) -> accumulator + _item);
            avgFitness /= population.size();
            
            System.out.println("Average fitness: " + avgFitness);
            System.out.println("Best fitness: " + algorithm.getFitness(population.get(0)));
            System.out.println("Best individual: " + population.get(0).getValue());
	}

	private static List<Individual<Byte>> initPopulation(int size) {
            List<Individual<Byte>> populations = new ArrayList<>(size);
            for(int i = 0; i < size; i++) {
                populations.add(new ByteIndividual(BITS));
            }
            return populations;
	}
}
