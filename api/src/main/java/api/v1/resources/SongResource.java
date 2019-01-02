package api.v1.resources;

import api.v1.dtos.SongDto;
import beans.AlbumBean;
import beans.SongBean;
import com.kumuluz.ee.logs.cdi.Log;
import entities.Album;
import entities.Song;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Log
@ApplicationScoped
@Path("/songs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SongResource {

    @Inject
    private SongBean songBean;

    @Inject
    private AlbumBean albumBean;

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
    public Response addSong(SongDto song) {
        if (song == null || song.getTitle() == null || song.getArtist() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Album album = null;
        if (song.getAlbumId() != null) {
            album = albumBean.getAlbum(song.getAlbumId());
            if(song.getArtist().getId() != album.getArtist().getId()) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        Song toAdd = new Song(null, song.getTitle(), song.getArtist(), album, song.getGenre());
        songBean.addSong(toAdd);
        return Response.status(Response.Status.CREATED).entity(toAdd).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSong(@PathParam("id") int songId, Song song) {
        // TODO dodatni checki?
        if (song == null || songId < 0 || song.getTitle() == null || song.getArtist() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        song = songBean.updateSong(songId, song);
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
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
