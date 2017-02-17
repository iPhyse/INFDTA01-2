package org.iphyse.infdta012.clustering;

import java.util.HashSet;
import java.util.Set;
import org.iphyse.infdta012.similarity.CalculateDistance;
import org.iphyse.infdta012.similarity.CompareDistance;
import org.iphyse.infdta012.similarity.EucledianDistance;

/**
 *
 * @author RICKRICHTER
 */
public class Cluster implements CompareDistance<ClusterPoint> {
    private static final CalculateDistance SQ_DISTANCE = new EucledianDistance();
    private final Set<ClusterPoint> POINTS;
    private ClusterPoint position;

    public Cluster(ClusterPoint position) {
        POINTS = new HashSet<>();
        this.position = position;
    }

    public Set<ClusterPoint> getPoints() {
        return POINTS;
    }

    @Override
    public double compare(CalculateDistance calculator, ClusterPoint other) {
        return position.compare(calculator, other);
    }

    public void setPosition(ClusterPoint position) {
        this.position = position;
    }

    public double[] getMean() {
        double[] sum = new double[position.getProperties().length];
        if(POINTS.isEmpty()) {
            return sum;
        }
        for(ClusterPoint point : POINTS) {
            for(int i = 0; i < point.getProperties().length; i++) {
                sum[i] += point.getProperties()[i];
            }
        }
        for(int i = 0; i < sum.length; i++) {
            sum[i] /= POINTS.size();
        }
        return sum;
    }

    public double getSquaredErrors() {
        double sum = 0;
        for(ClusterPoint point : POINTS) {
            sum += compare(SQ_DISTANCE, point);
        }
        return sum;
    }

    public ClusterPoint getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Cluster(" + POINTS.size() + ", " + getSquaredErrors() + ") {" + POINTS.toString() + "}";
    }
}