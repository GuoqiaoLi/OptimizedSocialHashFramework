package graph;

import org.jgrapht.graph.AbstractGraph;

import java.util.HashMap;
import java.util.List;
import net.sf.javaml.core.Dataset;

/**
 * Created by weixin1 on 01/05/2017.
 * Implement this graph class to implement the specific social graph needed
 * for your application.
 */

public abstract class SocialGraph extends AbstractGraph implements Dataset {

    private List<SocialVertex> vertices;
    private List<SocialEdge> edges;
    private HashMap<Integer, SocialVertex> paritions;

    public SocialGraph() {
        super();
    }

    public List getVertices(){
        return this.vertices;
    }

    public List getEdges() {
        return this.edges;
    }

    public HashMap getPartitions() {
        return this.paritions;
    }

    public List getNeighbours(SocialVertex vertex) {
        return vertex.getNeighbours();
    }

}
