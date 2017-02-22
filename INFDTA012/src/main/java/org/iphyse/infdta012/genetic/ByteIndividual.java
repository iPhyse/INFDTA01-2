package org.iphyse.infdta012.genetic;

/**
 *
 * @author RICKRICHTER
 */
public class ByteIndividual extends Individual<Byte> {
    public ByteIndividual(int bits) {
        super(bits);
    }

    protected ByteIndividual(Byte value) {
        super(value);
    }

    @Override
    protected Byte randomize(int bits) {
        byte bit = 0;
        for(int p = 0; p < bits; p++) {
            bit |= getRandomBit(p);
        }
        return bit;
    }

    @Override
    public Individual<Byte> breed(Individual<Byte> parent) {
        byte value = getValue();
        byte parentValue = parent.getValue();
        for(int i = 0; i < Byte.SIZE; i++) {
            if(Math.random() < 0.5) {
                value |= (parentValue & (1 << i));
            }
        }
        return new ByteIndividual(Byte.valueOf(value));
    }

    @Override
    public Individual<Byte> mutate(double mutationRate, int bits) {
        byte value = getValue();
        for(int i = 0; i < bits; i++) {
            if(Math.random() < mutationRate) {
                value ^= (1 << i);
            }
        }
        return new ByteIndividual(Byte.valueOf(value));
    }

    @Override
    public String toString() {
        return "<" + getValue() + ">";
    }
}