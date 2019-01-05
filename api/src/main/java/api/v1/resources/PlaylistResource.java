package api.v1.resources;


import beans.PlaylistBean;
import beans.SongBean;
import com.kumuluz.ee.logs.cdi.Log;
import entities.Playlist;
import entities.Song;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Log
@ApplicationScoped
@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaylistResource {

    @Inject
    private PlaylistBean playlistBean;

    @Inject
    private SongBean songBean;


    @GET
    public Response getPlaylists() {
        List<Playlist> playlists = playlistBean.getPlaylists();
        return Response.ok(playlists).build();
    }

    @GET
    @Path("{id}")
    public Response getPlaylist(@PathParam("id") int playlistId) {
        Playlist playlist = playlistBean.getPlaylist(playlistId);
        if (playlist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(playlist).build();
    }

    @POST
    public Response addPlaylist(Playlist playlist) {
        if (playlist == null  || playlist.getName() == null || playlist.getUserId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
            // checking for duplicates
        if (playlistBean.getPlaylistByNameAndUSer(playlist.getName(), playlist.getUserId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        boolean success = playlistBean.addPlaylist(playlist);
        if (!success) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).entity(playlist).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePlaylistName(@PathParam("id") int playlistId, Playlist playlist) {
        if (playlist == null || playlist.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String name = playlist.getName();
        Playlist dbPlaylist = playlistBean.getPlaylist(playlistId);
        if (playlistBean.getPlaylistByNameAndUSer(name, dbPlaylist.getUserId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        dbPlaylist = playlistBean.updatePlaylist(dbPlaylist, name);
        if (dbPlaylist == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(dbPlaylist).build();
    }

    @DELETE
    @Path("{id}")
    public Response removePlaylist(@PathParam("id") int playlistId) {
        boolean success = playlistBean.removePlaylist(playlistId);
        if (success) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    /************** PLAYLIST SONGS *************/
    @POST
    @Path("{id}/songs")
    public Response addSong(@PathParam("id") int playlistId, Song requestSong) {
        // check if song is already on playlist
        Playlist playlist = playlistBean.getPlaylist(playlistId);
        Song song = songBean.getSong(requestSong.getId());
//        System.out.println(playlist);
//        System.out.println(song);
        if (playlist == null || song == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Song> playlistSongs = playlist.getSongs();
        if (playlistSongs.contains(song)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        // add song to playlist
        Boolean success = playlistBean.addSongToPlaylist(playlistId, requestSong.getId());
        if (!success) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}/songs/{song_id}")
    public Response removeSong(@PathParam("id") int playlistId, @PathParam("song_id") int songId) {
        Boolean success = playlistBean.removeSongFromPlaylist(playlistId, songId);
        if (!success) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

}
