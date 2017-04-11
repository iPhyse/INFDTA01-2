package org.iphyse.infdta012.prediction;

import java.util.ArrayList;
import java.util.List;
import org.iphyse.infdta012.genetic.GeneticAlgorithm;
import org.iphyse.infdta012.genetic.Individual;
import org.iphyse.infdta012.genetic.LongIndividual;

public class DoubleExponentialSmoothing {

    private static final int BITS = SimpleExponentialSmoothing.BITS * 2;
    private static final int ITERATIONS = 1000;
    private static final int MASK = (int) (Math.pow(2, SimpleExponentialSmoothing.BITS) - 1);
    private final List<Double> values;
    private final double[] smoothed;
    private final double[] trend;

    public DoubleExponentialSmoothing(List<Double> values) {
        this.values = values;
        smoothed = new double[values.size() - 1];
        trend = new double[values.size() - 1];
        smoothed[0] = values.get(1);
        trend[0] = values.get(1) - values.get(0);
    }

    private void calculateSmoothedValues(double alpha, double beta) {
        double inverseA = 1 - alpha;
        double inverseB = 1 - beta;
        for (int i = 1; i < smoothed.length; i++) {
            smoothed[i] = alpha * values.get(i + 1) + inverseA * (smoothed[i - 1] + trend[i - 1]);
            trend[i] = beta * (smoothed[i] - smoothed[i - 1]) + inverseB * trend[i - 1];
        }
    }

    private double getSumOfSquaredErrors() {
        double sum = 0;
        for (int i = 2; i < values.size(); i++) {
            double d = values.get(i) - (smoothed[i - 2] + trend[i - 2]);
            sum += d * d;
        }
        return sum;
    }

    private double getFitness(long input) {
        int alpha = (int) ((input >> SimpleExponentialSmoothing.BITS) & MASK);
        int beta = (int) (input & MASK);
        calculateSmoothedValues(SimpleExponentialSmoothing.getFactor(alpha), SimpleExponentialSmoothing.getFactor(beta));
        return -getSumOfSquaredErrors();
    }

    public double[] getBestFactors() {
        GeneticAlgorithm<Long> algorithm = new GeneticAlgorithm<>(BITS, SimpleExponentialSmoothing.CROSSOVER_RATE, SimpleExponentialSmoothing.MUTATION_RATE, true, initPopulation(), this::getFitness);
        List<Individual<Long>> out = algorithm.run(ITERATIONS);
        long input = out.get(0).getValue();
        int alpha = (int) ((input >> SimpleExponentialSmoothing.BITS) & MASK);
        int beta = (int) (input & MASK);
        return new double[]{SimpleExponentialSmoothing.getFactor(alpha), SimpleExponentialSmoothing.getFactor(beta)};
    }

    private List<Individual<Long>> initPopulation() {
        List<Individual<Long>> population = new ArrayList<>(SimpleExponentialSmoothing.POPULATION_SIZE);
        for (int i = 0; i < SimpleExponentialSmoothing.POPULATION_SIZE; i++) {
            population.add(new LongIndividual(BITS));
        }
        return population;
    }

    public double[] getPredictions(double alpha, double beta, int maxT) {
        calculateSmoothedValues(alpha, beta);
        double[] out = new double[maxT];
        int max = smoothed.length - 1;
        out[0] = values.get(0);
        out[1] = values.get(1);
        for (int i = 1; i <= maxT; i++) {
            if (i < smoothed.length) {
                out[i] = smoothed[i - 1] + trend[i - 1];
            } else if (i > smoothed.length) {
                out[i - 1] = smoothed[max] + (i - max) * trend[max];
            }
        }
        return out;
    }

    public double getErrorMeasure() {
        return Math.sqrt(getSumOfSquaredErrors() / (values.size() - 2));
    }
}
