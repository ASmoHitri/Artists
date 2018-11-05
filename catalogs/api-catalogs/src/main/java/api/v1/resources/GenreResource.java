package api.v1.resources;

import beans.GenreBean;
import entities.Genre;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/genres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResource {
    @Inject
    private GenreBean genreBean;

    @GET
    public Response getGenres() {
        List<Genre> genres = genreBean.getGenres();
        return Response.ok(genres).build();
    }

    @POST
    public Response addGenre(Genre genre) {
        if (genre == null || genre.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // check if genre already exists
        if (genreBean.getGenreByName(genre.getName()) != null) {
            Response.status(Response.Status.CONFLICT).build();
        }
        Boolean successfullyAdded = genreBean.addGenre(genre);
        if (successfullyAdded) {
            return Response.status(Response.Status.CREATED).entity(genre).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
