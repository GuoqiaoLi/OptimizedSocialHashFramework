package graph;

import org.jgrapht.EdgeFactory;

/**
 * Created by weixin1 on 01/05/2017.
 * Extend this abstract class to create edges for social grpah
 */


public abstract class SocialGraphEdgeFactory implements EdgeFactory {

    //TODO: Override this method to create an edge
    public static SocialEdge createEdges(SocialVertex source, SocialVertex target) {

        return new SocialEdge() {
            @Override
            public SocialVertex getSource() {
                return null;
            }

            @Override
            public SocialVertex getTarget() {
                return null;
            }
        };
    }
}
