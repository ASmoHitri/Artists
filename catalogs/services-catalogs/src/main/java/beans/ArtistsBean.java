package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Artist;
import helpers.DBHelpers;
import helpers.TransactionsHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        DBHelpers.addObject(entityManager, artist);
    }

    public Artist updateArtist(int artistId, Artist artist) {
        if (getArtist(artistId) == null || artist == null) {
            return null;
        }
        try{
            TransactionsHandler.beginTx(entityManager);
            artist.setId(artistId);
            entityManager.merge(artist);
            TransactionsHandler.commitTx(entityManager);
        } catch (Exception e) {
            TransactionsHandler.rollbackTx(entityManager);
        }
        return artist;
    }

    public boolean removeArtist(int artistId) {
        Artist artist = entityManager.find(Artist.class, artistId);
        return DBHelpers.removeObject(entityManager, artist);
    }
}