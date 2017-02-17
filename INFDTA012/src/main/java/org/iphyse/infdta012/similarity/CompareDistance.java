package org.iphyse.infdta012.similarity;

/**
 *
 * @author RICKRICHTER
 */
public interface CompareDistance<T> {
	double compare(CalculateDistance calculator, T other);
}
