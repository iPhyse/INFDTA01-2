package org.iphyse.infdta012.genetic;

/**
 *
 * @author RICKRICHTER
 */
public class IntegerIndividual extends Individual<Integer> {
    public IntegerIndividual(int bits) {
        super(bits);
    }

    protected IntegerIndividual(Integer value) {
        super(value);
    }

    @Override
    protected Integer randomize(int bits) {
        int bit = 0;
        for(int p = 0; p < bits; p++) {
            bit |= getRandomBit(p);
        }
        return bit;
    }

    @Override
    public Individual<Integer> breed(Individual<Integer> parent) {
        int value = getValue();
        int parentValue = parent.getValue();
        for(int i = 0; i < Integer.SIZE; i++) {
            if(Math.random() < 0.5) {
                value |= (parentValue & (1 << i));
            }
        }
        return new IntegerIndividual(Integer.valueOf(value));
    }

    @Override
    public Individual<Integer> mutate(double mutationRate, int bits) {
        int value = getValue();
        for(int i = 0; i < bits; i++) {
            if(Math.random() < mutationRate) {
                value ^= (1 << i);
            }
        }
        return new IntegerIndividual(Integer.valueOf(value));
    }

    @Override
    public String toString() {
            return "<" + getValue() + ">";
    }
}
