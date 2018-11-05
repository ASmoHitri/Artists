package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Album;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class AlbumBean {

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

    public void addAlbum(Album album) {
        if (album != null) {
//            System.out.println(album.toString());
            try{
                beginTx();
                entityManager.persist(album);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        }
    }

    public Album updateAlbum(int albumId, Album album, List<Artist> artists, Genre genre) {
        if (getSong(albumId) == null || album == null) {
            return null;
        }
        try{
            beginTx();
            album.setId(albumId);
            album.setArtists(artists);
            album.setGenre(genre);
            entityManager.merge(album);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return album;
    }

    public boolean removeAlbum(int albumId) {
        Album album = entityManager.find(Album.class, albumId);
        if (album != null) {
            try{
                beginTx();
                entityManager.remove(album);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
        }
            return true;
        }
        return false;
    }

    private void beginTx() {
        if (!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();
    }

    private void commitTx() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    private void rollbackTx() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }
}