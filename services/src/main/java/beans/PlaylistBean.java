package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Playlist;
import entities.Song;
import helpers.DBHelpers;
import helpers.TransactionsHandler;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class PlaylistBean {
    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    public List<Playlist> getPlaylists() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Playlist.class, queryParameters);
    }

    @Timed
    public Playlist getPlaylist(int playlistId) {
        entityManager.clear();
        return entityManager.find(Playlist.class, playlistId);
    }

    public Playlist getPlaylistByNameAndUSer(String name, int userId) {
        Query query = entityManager.createNamedQuery("Playlists.getByName&User");
        query.setParameter("name", name);
        query.setParameter("userId", userId);
        List<Playlist> playlists = query.getResultList();   // one or none
        if (playlists.isEmpty()) {
            return null;
        }
        return playlists.get(0);
    }

    public boolean addPlaylist(Playlist playlist) {
        return DBHelpers.addObject(entityManager, playlist);
    }

    public Playlist updatePlaylist(Playlist playlist, String name) {
        if (playlist == null) {
            return null;
        }
        playlist.setName(name);
        Query query = entityManager.createNamedQuery("Playlists.updateName").setParameter("name", name);
        try {
            TransactionsHandler.beginTx(entityManager);
            query.executeUpdate();
            TransactionsHandler.commitTx(entityManager);
        } catch (Exception e) {
            TransactionsHandler.rollbackTx(entityManager);
            return null;
        }
        return  playlist;
    }

    public boolean removePlaylist(int playlistId) {
        Playlist playlist = getPlaylist(playlistId);
        return DBHelpers.removeObject(entityManager, playlist);
    }

    public boolean addSongToPlaylist(Playlist playlist, Song song) {
        playlist.getSongs().add(song);
        return DBHelpers.updateObject(entityManager, playlist);
    }

    public boolean removeSongFromPlaylist(int playlistId, Song song) {
        Playlist playlist = getPlaylist(playlistId);
        List<Song> playlistSongs = playlist.getSongs();
        for(Song s: playlistSongs) {
            if(s.getId().equals(song.getId())) {
                playlistSongs.remove(s);
                break;
            }
        }
        return DBHelpers.updateObject(entityManager, playlist);
    }
}
