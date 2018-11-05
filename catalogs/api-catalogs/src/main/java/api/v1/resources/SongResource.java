package api.v1.resources;

import beans.SongBean;
import entities.Album;
import entities.Artist;
import entities.Genre;
import entities.Song;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SongResource {

    @Inject
    private SongBean songBean;

    @GET
    public Response getSongs() {
        List<Song> songs = songBean.getSongs();
        return Response.ok(songs).build();
    }

    @GET
    @Path("{id}")
    public Response getSong(@PathParam("id") int songId) {
        Song song = songBean.getSong(songId);
        if (song == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(song).build();
    }

    @POST
    public Response addSong(Song song) {
        // TODO: preveri kako je s preverjanjem artist==null!
        if (song == null || song.getTitle() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        songBean.addSong(song);
        return Response.status(Response.Status.CREATED).entity(song).build();
    }

    @PUT // popravi, ker je sedaj song list, dodaj genre.
    @Path("{id}") // je to okej? bi tu moral biti se Artist artist in Album album?, dodatni checki?
    public Response updateSong(@PathParam("id") int songId, Song song, List<Artist> artists, Album album, Genre genre) {
        // TODO
        if (song == null || songId < 0 || song.getTitle() == null || artists == null || album.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        song = songBean.updateSong(songId, song, artists, album);
        if (song == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(song).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSong(@PathParam("id") int songId) {
        boolean success = songBean.removeSong(songId);
        if (success) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
