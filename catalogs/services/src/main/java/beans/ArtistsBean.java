package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Artist;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class ArtistsBean {

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    public List<Artist> getArtists() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Artist.class, queryParameters);
    }

    public Artist getArtist(int artistId) {
        return entityManager.find(Artist.class, artistId);
    }

    public void addArtist(Artist artist) {
        if (artist != null) {
//            System.out.println(artist.toString());
            try{
                beginTx();
                entityManager.persist(artist);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }


        }
    }

    public Artist updateArtist(int artistId, Artist artist) {
        if (getArtist(artistId) == null || artist == null) {
            return null;
        }
        try{
            beginTx();
            artist.setId(artistId);
            entityManager.merge(artist);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return artist;
    }

    public boolean removeArtist(int artistId) {
        Artist artist = entityManager.find(Artist.class, artistId);
        if (artist != null) {
            try{
                beginTx();
                entityManager.remove(artist);
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