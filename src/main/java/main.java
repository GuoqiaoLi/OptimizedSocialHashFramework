/**
 * Created by weixin1 on 30/04/2017.
 */
import static spark.Spark.*;

public class main {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
