package api.v1.resources;


import beans.PlaylistBean;
import entities.Playlist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaylistResource {

    @Inject
    private PlaylistBean playlistBean;

    @GET
    public Response getPlaylists() {
        List<Playlist> playlists = playlistBean.getPlaylists();
        return Response.ok(playlists).build();
    }
}
