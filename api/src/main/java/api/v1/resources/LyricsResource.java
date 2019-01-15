package api.v1.resources;

import beans.LyricsBean;
import beans.SongBean;
import dtos.Lyrics;
import entities.Song;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/lyrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LyricsResource {

    @Inject
    private LyricsBean lyricsBean;

    @Inject
    private SongBean songBean;

    @GET
    @Path("{song_id}")
    public Response getSongLyrics(@PathParam("song_id") int songId) {
        if (songId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Song song = songBean.getSong(songId);
        Lyrics lyrics = lyricsBean.getSongLyrics(song);
        if (lyrics == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(lyrics).build();
    }

}
