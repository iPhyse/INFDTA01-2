package org.iphyse.infdta012.genetic;

/**
 *
 * @author RICKRICHTER
 */
public abstract class Individual<T> {
    private final T value;

    public Individual(int bits) {
        value = randomize(bits);
    }

    protected Individual(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    protected abstract T randomize(int bits);

    protected static int getRandomBit(int position) {
        if(Math.random() < 0.5) {
            return 1 << position;
        }
        return 0;
    }

    public abstract Individual<T> breed(Individual<T> p2);

    public abstract Individual<T> mutate(double mutationRate, int bits);
}
