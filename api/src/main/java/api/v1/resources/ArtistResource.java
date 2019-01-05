package api.v1.resources;

import beans.ArtistsBean;
import com.kumuluz.ee.logs.cdi.Log;
import entities.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Log
@ApplicationScoped
@Path("/artists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistResource {

    @Inject
    private ArtistsBean artistsBean;

    @GET
    public Response getArtists() {
        List<Artist> artists = artistsBean.getArtists();
        return Response.ok(artists).build();
    }

    @GET
    @Path("{id}")
    public Response getArtist(@PathParam("id") int artistId) {
        Artist artist = artistsBean.getArtist(artistId);
        if (artist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(artist).build();
    }

    @POST
    public Response addArtist(Artist artist) {
        if (artist == null || artist.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Artist existing = artistsBean.getArtistsByName(artist.getName());
        if (existing != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        artistsBean.addArtist(artist);
        return Response.status(Response.Status.CREATED).entity(artist).build();
    }

    @PUT
    @Path("{id}") 
    public Response updateArtist(@PathParam("id") int artistId, Artist artist) {
        if (artist == null || artistId < 0 || artist.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        artist = artistsBean.updateArtist(artistId, artist);
        if (artist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(artist).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteArtist(@PathParam("id") int artistId) {

        boolean success = artistsBean.removeArtist(artistId);

        if (success) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
