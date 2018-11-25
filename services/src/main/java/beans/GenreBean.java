package beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import entities.Genre;
import helpers.DBHelpers;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class GenreBean {
    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "catalogs-jpa")
    private EntityManager entityManager;

    public List<Genre> getGenres() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        return JPAUtils.queryEntities(entityManager, Genre.class, queryParameters);
    }

    public Genre getGenre(int genreId) {
        return entityManager.find(Genre.class, genreId);
    }

    public Genre getGenreByName(String genre) {
        Query query = entityManager.createNamedQuery("Genres.getGenreByName");
        query.setParameter("name", genre);
        List genres = query.getResultList();
        if (genres.isEmpty()) {
            return null;
        }
        return (Genre) genres.get(0);
    }

    public boolean addGenre(Genre genre) {
        return DBHelpers.addObject(entityManager, genre);
    }
}
