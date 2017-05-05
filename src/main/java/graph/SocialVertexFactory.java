package graph;

import org.jgrapht.VertexFactory;

import java.util.List;

/**
 * Created by weixin1 on 01/05/2017.
 * Extend this abstract class to create vertices in the social graph
 */

public abstract class SocialVertexFactory implements VertexFactory{

    //TODO: override the create method to create the vertex.
    public SocialVertex createVertex() {
        return null;
    }

}
