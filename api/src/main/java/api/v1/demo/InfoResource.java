package api.v1.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@ApplicationScoped
@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
public class InfoResource {
    @GET
    @Path("/info")
    public Response getInfo() {
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> clani = new ArrayList<String>();
        clani.add("mu2729");
        clani.add("gb7532");
        ArrayList<String> mikrostoritve = new ArrayList<String>();
        mikrostoritve.add("N/A");
        mikrostoritve.add("N/A");
        ArrayList<String> github = new ArrayList<String>();
        github.add("https://github.com/ASmoHitri/Catalogs");
        github.add("https://github.com/ASmoHitri/Subscriptions");
        ArrayList<String> travis = new ArrayList<String>();
        travis.add("https://travis-ci.org/ASmoHitri/Catalogs");
        travis.add("https://travis-ci.org/ASmoHitri/Subscriptions");
        ArrayList<String> dockerhub = new ArrayList<String>();
        dockerhub.add("https://hub.docker.com/r/mu2729/streaming-catalogs/");
        dockerhub.add("https://hub.docker.com/r/mu2729/streaming-subscriptions/");

        jsonObject.put("clani", new JSONArray(clani))
                .put("opis_projekta", "Najin projekt implementira aplikacijo za pretocenje glasbe.")
                .put("mikrostoritve", new JSONArray(mikrostoritve))
                .put("github", new JSONArray(github))
                .put("travis", new JSONArray(travis))
                .put("dockerhub", new JSONArray(dockerhub));
        return Response.ok(jsonObject.toString()).build();
    }
}
