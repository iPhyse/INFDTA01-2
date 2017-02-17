package org.iphyse.infdta012.clustering;

import java.util.List;

/**
 *
 * @author RICKRICHTER
 */
    public interface ClusterAlgorithm {

    void assignPoints(List<ClusterPoint> points, Cluster[] clusters);

    void setCenters(Cluster[] clusters);

    void initClusters(Cluster[] clusters, List<ClusterPoint> points);

}