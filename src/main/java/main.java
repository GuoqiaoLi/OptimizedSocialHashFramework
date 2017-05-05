/**
 * Created by weixin1 on 30/04/2017.
 * This is an example middle layer RESTful server of the social hash framework
 */
import static spark.Spark.*;

import algo.graphPartition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graph.SocialEdge;
import graph.SocialGraph;
import graph.SocialVertex;

import java.lang.reflect.Type;
import java.util.List;

public class main {

    public static void main(String[] args) {
        //Example of how to partition the graph
        get("/partition", (req, res) -> {
            Gson gson = new Gson();
            return gson.toJson(graphPartition.partition());
        });
        //Example of repartition the graph when the graph is changed
        get("/repartition", (req,res) -> {
            Gson gson = new Gson();
            Type edgesType = new TypeToken<List<SocialEdge>>() {}.getType();
            Type vertexType = new TypeToken<List<SocialVertex>>() {}.getType();
            List<SocialEdge> edges = gson.fromJson(req.params("edges"), edgesType);
            List<SocialVertex> vertices = gson.fromJson(req.params("vertices"), vertexType);
            return gson.toJson(graphPartition.rePartition(edges, vertices));
        });
        //Example of set the social graph.
        post("/setgraph", (req, res) -> {
            Gson gson = new Gson();
            Type graphType = new TypeToken<SocialGraph>() {}.getType();
            graphPartition.setGrpah(gson.fromJson(req.params("graph"), graphType));
            return "success";
        });
    }
}
