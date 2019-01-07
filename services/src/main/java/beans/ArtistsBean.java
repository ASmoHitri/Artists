package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Artist;
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
public class ArtistsBean {

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    @Timed(name = "get-artists")
    public List<Artist> getArtists() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Artist.class, queryParameters);
    }

    public Artist getArtist(int artistId) {
        return entityManager.find(Artist.class, artistId);
    }

    public Artist getArtistsByName(String name) {
        Query query = entityManager.createNamedQuery("Artists.getArtistsByName");
        query.setParameter("name", name);
        List<Artist> artists =  query.getResultList();
        if (artists.isEmpty())
            return null;
        return artists.get(0);
    }

    public void addArtist(Artist artist) {
        DBHelpers.addObject(entityManager, artist);
    }

    public Artist updateArtist(int artistId, Artist artist) {
        if (getArtist(artistId) == null || artist == null) {
            return null;
        }
        artist.setId(artistId);
        Boolean success = DBHelpers.updateObject(entityManager, artist);
        if (!success) return null;
        return artist;
    }

    public boolean removeArtist(int artistId) {
        Artist artist = entityManager.find(Artist.class, artistId);
        return DBHelpers.removeObject(entityManager, artist);
    }
}