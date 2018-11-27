package api.v1.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
public class InfoResource {
    @GET
    @Path("/info")
    public Response getInfo() {

        String demo = "{\n" +
                "\"clani\":[\"mu2729\", \"gb7532\"],\n" +
                "\"opis_projekta\":\"Najin projekt implementira aplikacijo za pretocenje glasbe.\",\n" +
                "\"mikrostoritve\":[\"http://159.122.186.127:30836/api/v1/artists\",\"http://159.122.186.127:30729/api/v1/users\"], \n" +
                "\"github\":[\"https://github.com/ASmoHitri/Catalogs\", \"https://github.com/ASmoHitri/Subscriptions\"],\n" +
                "\"travis\":[\"https://travis-ci.org/ASmoHitri/Catalogs\", \"https://travis-ci.org/ASmoHitri/Subscriptions\"],\n" +
                "\"dockerhub\":[\"https://hub.docker.com/r/mu2729/streaming-catalogs/\", \"https://hub.docker.com/r/mu2729/streaming-subscriptions/\"]\n" +
                "}\n";
        return Response.ok(demo).build();
    }
}
