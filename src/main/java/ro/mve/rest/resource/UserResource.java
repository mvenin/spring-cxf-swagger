package ro.mve.rest.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import io.swagger.annotations.Api;
import ro.mve.rest.data.UserData;
import ro.mve.rest.exception.ApiException;
import ro.mve.rest.exception.NotFoundException;
import ro.mve.rest.mapper.SourceBean;
import ro.mve.rest.mapper.TargetBean;
import ro.mve.rest.model.User;

@Api(value = "/user")
@Path("/user")
@Produces({"application/json", "application/xml"})
public class UserResource {
  static UserData userData = new UserData();

  @POST
  public Response createUser(User user) {
    userData.addUser(user);
    return Response.ok().entity("").build();
  }

  @POST
  @Path("/createWithArray")
  public Response createUsersWithArrayInput(User[] users) {
      for (User user : users) {
          userData.addUser(user);
      }
      return Response.ok().entity("").build();
  }

  @POST
  @Path("/createWithList")
  public Response createUsersWithListInput(java.util.List<User> users) {
      for (User user : users) {
          userData.addUser(user);
      }
      return Response.ok().entity("").build();
  }

  @PUT
  @Path("/{username}")
  public Response updateUser(@PathParam("username") String username, User user) {
    userData.addUser(user);
    return Response.ok().entity("").build();
  }

  @DELETE
  @Path("/{username}")
  public Response deleteUser(String username) {
    userData.removeUser(username);
    return Response.ok().entity("").build();
  }

  @GET
  @Path("/{username}")
  public Response getUserByName(@PathParam("username") String username)
    throws ApiException {
	  Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
	  
	  SourceBean sourceBean = new SourceBean();
	  TargetBean destObject = 
			    mapper.map(sourceBean, TargetBean.class);
	  
	  
    User user = userData.findUserByName(username);
    if (null != user) {
      return Response.ok().entity(user).build();
    } else {
      throw new NotFoundException(404, "User not found");
    }
  }

  @GET
  @Path("/login")
  public Response loginUser(@QueryParam("username") String username, @QueryParam("password") String password) {
    return Response.ok()
        .entity("logged in user session:" + System.currentTimeMillis())
        .build();
  }

  @GET
  @Path("/logout")
  public Response logoutUser() {
    return Response.ok().entity("").build();
  }
}
