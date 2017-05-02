package graph;

import org.jgrapht.graph.AbstractGraph;
import java.util.List;

/**
 * Created by weixin1 on 01/05/2017.
 * Implement this graph class to implement the specific social graph needed
 * for your application.
 */

public abstract class SocialGraph extends AbstractGraph{

    private List<SocialVertex> vertices;
    private List<SocialEdge> edges;

    public SocialGraph() {
        super();
    }

    public List getVertices(){
        return this.vertices;
    }

    public List getEdges() {
        return this.edges;
    }

    public List getNeighbours(SocialVertex vertex) {
        return vertex.getNeighbours();
    }

}
