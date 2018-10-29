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

    @Transactional
    public Artist addArtist(Artist artist) {
        if (artist != null) {
            entityManager.persist(artist);
        }
        return artist;
    }

    @Transactional
    public Artist updateArtist(int artistId, Artist artist) {
        if (getArtist(artistId) == null || artist == null) {
            return null;
        }
        artist.setId(artistId);
        entityManager.merge(artist);
        return artist;
    }

    @Transactional
    public boolean removeArtist(int artistId) {
        Artist artist = entityManager.find(Artist.class, artistId);
        if (artist != null) {
            entityManager.remove(artist);
            return true;
        }
        return false;
    }
}