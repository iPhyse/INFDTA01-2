package org.iphyse.infdta012.clustering;

import org.iphyse.infdta012.similarity.CalculateDistance;
import org.iphyse.infdta012.similarity.CompareDistance;

/**
 *
 * @author RICKRICHTER
 */
public class ClusterPoint implements CompareDistance<ClusterPoint> {
    private final double[] properties;

    public ClusterPoint(double[] properties) {
        this.properties = properties;
    }

    @Override
    public double compare(CalculateDistance calcDist, ClusterPoint other) {
        return calcDist.getDistance(properties, other.properties);
    }

    public double[] getProperties() {
        return properties;
    }
}
