package api.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
public class InfoResource {
    @GET
    public Response getDemo() {
        return /* tisti JSON nekak?*/
    }
}
