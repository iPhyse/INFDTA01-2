package org.iphyse.infdta012.clustering;

import java.util.List;
import org.iphyse.infdta012.similarity.CalculateDistance;

/**
 *
 * @author RICKRICHTER
 */
public class KMean implements ClusterAlgorithm {
    private CalculateDistance calculator;

    public KMean(CalculateDistance calculator) {
        this.calculator = calculator;
    }

    @Override
    public void initClusters(Cluster[] clusters, List<ClusterPoint> points) {
        for(int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster(getRandomPoint(points));
        }
    }

    private ClusterPoint getRandomPoint(List<ClusterPoint> points) {
        return points.get((int) (Math.random() * points.size()));
    }

    @Override
    public void assignPoints(List<ClusterPoint> points, Cluster[] clusters) {
        for(ClusterPoint point : points) {
            double positiveDouble = Double.POSITIVE_INFINITY;
            Cluster cluster = clusters[0];
            int i = 0;
            do {
                double delta = clusters[i].compare(calculator, point);
                if(delta < positiveDouble) {
                    positiveDouble = delta;
                    cluster = clusters[i];
                }
                i++;
            } while(i < clusters.length);
            cluster.getPoints().add(point);
        }
    }

    @Override
    public void setCenters(Cluster[] clusters) {
        for(Cluster cluster : clusters) {
            cluster.setPosition(new ClusterPoint(cluster.getMean()));
        }
    }
}
