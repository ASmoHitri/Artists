package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Album;
import entities.Artist;
import helpers.DBHelpers;
import helpers.TransactionsHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class AlbumBean {
    // TODO fix post, put
    // mislim, da je okej

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    public List<Album> getAlbums() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Album.class, queryParameters);
    }

    public Album getAlbum(int albumId) {
        return entityManager.find(Album.class, albumId);
    }

    public boolean addAlbum(Album album) { return DBHelpers.addObject(entityManager, album);
    }

    public Album updateAlbum(int albumId, Album album) {
        if (getAlbum(albumId) == null || album.getArtist() == null || album.getGenre() == null || album.getName() == null) {
            return null;
        }
        try{
            TransactionsHandler.beginTx(entityManager);
            album.setId(albumId);
            entityManager.merge(album);
            TransactionsHandler.commitTx(entityManager);
        } catch (Exception e) {
            TransactionsHandler.rollbackTx(entityManager);
        }
        return album;
    }

    public boolean removeAlbum(int albumId) {
        Album album = entityManager.find(Album.class, albumId);
        return DBHelpers.removeObject(entityManager, album);
    }

    public List<Album> getAlbumsbyArtist(Artist artist){
        Query query = entityManager.createNamedQuery("Albums.getbyArtist");
        query.setParameter("artist", artist);
        return query.getResultList();
    }
}