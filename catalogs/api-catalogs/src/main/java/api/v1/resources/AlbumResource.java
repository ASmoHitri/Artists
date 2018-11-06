package api.v1.resources;

import beans.AlbumBean;
import beans.ArtistsBean;
import entities.Album;
import entities.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/albums")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlbumResource {
    // TODO check POST, PUT

    @Inject
    private AlbumBean albumBean;

    @Inject
    private ArtistsBean artistsBean;

    @GET
    public Response getAlbums() {
        List<Album> albums = albumBean.getAlbums();
        return Response.ok(albums).build();
    }

    @GET
    @Path("{id}")
    public Response getAlbum(@PathParam("id") int albumId) {
        Album album = albumBean.getAlbum(albumId);
        if (album == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(album).build();
    }

    @POST
    public Response addAlbum(Album album) {
        if (album == null || album.getName() == null || album.getArtist() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // TODO checking for duplicates
        albumBean.addAlbum(album);
        return Response.status(Response.Status.CREATED).entity(album).build();
    }

    @PUT
    @Path("{id}")
    public Response updateAlbum(@PathParam("id") int albumId, Album album) {
        Artist input_artist = artistsBean.getArtist(album.getArtist().getId());
        if (album == null || albumId < 0 || album.getName() == null ||
                input_artist == null || input_artist.getName().equals(album.getArtist().getName())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        album = albumBean.updateAlbum(albumId, album);
        if (album == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(album).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAlbum(@PathParam("id") int albumId) {
        boolean success = albumBean.removeAlbum(albumId);
        if (success) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
