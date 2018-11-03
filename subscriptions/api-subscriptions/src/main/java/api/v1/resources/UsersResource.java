package api.v1.resources;

import response_entities.ResponseUser;
import beans.UsersBean;
import entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UsersBean usersBean;

    @GET
    public Response getUsers() {
        List<ResponseUser> users = usersBean.getUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("{id}")
    public Response getUsers(@PathParam("id") int userId) {
        ResponseUser user = usersBean.getUser(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    public Response addUser(User user) {
        if (user == null || user.getUsername() == null || user.getMail() == null || user.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // TODO: duplicates management
        Boolean success = usersBean.addUser(user);
        if (!success) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") int userId, User user) {
        if (user == null || userId < 0 || user.getUsername() == null || user.getMail() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        user = usersBean.updateUser(userId, user);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int userId) {

        boolean success = usersBean.removeUser(userId);

        if (success) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
