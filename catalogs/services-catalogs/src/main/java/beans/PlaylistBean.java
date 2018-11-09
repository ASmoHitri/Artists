package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Playlist;
import helpers.DBHelpers;
import helpers.TransactionsHandler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class PlaylistBean {
    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    public List<Playlist> getPlaylists() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Playlist.class, queryParameters);
    }

    public Playlist getPlaylist(int playlistId) {
        return entityManager.find(Playlist.class, playlistId);
    }

    public boolean addPlaylist(Playlist playlist) {
        return DBHelpers.addObject(entityManager, playlist);
    }

    public Playlist updatePlaylist(int playlistId, String name) {
        Playlist playlist = getPlaylist(playlistId);
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
}
