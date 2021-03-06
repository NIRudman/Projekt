/**
 * 
 */
package rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import WebApp.User;
import WebApp.UserManager;

/**
 * @author Niclas
 *
 */
@RequestScoped
@Path("/users")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class UserEndpoint {
	
	// Inject the user manager
	@Inject
	UserManager userManager;

	/**
	* POST for inserting new Users
	* @param user (as input)
	* @return
	*/
	@POST
	@Path("/insert")
	public Response create(final Object[] input) {
		
		try {
			userManager.insertUser(new User(1, input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//here we use User#getId(), assuming that it provides the identifier to retrieve the created User resource. 
		return Response.created(UriBuilder.fromResource(UserEndpoint.class).path(String.valueOf(input)).build())
				.build();
	}

	/**
	* GET for finding Users by Id
	* @param id
	* @return
	*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final int id) {
		
		User user = (User) userManager.getUserComponent(id);
		
		if (user == null) {
			return Response.ok("User not found").build();
		}
		return Response.ok(user.getUser()).build();
	}

	/**
	* GET for sending table of users
	* @param startPosition
	* @param maxResult
	* @return
	*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object[][] listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		final Object[][] users = userManager.getTable("user_info");
		return users;
	}
	
	/**
	* GET with search param
	* @param search
	* @return
	*/
	@GET
	@Path("/search/{search}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object[][] listSearch(@PathParam("search")String sWord) {
		userManager.searchTables(sWord);
		final Object[][] users = userManager.getTable("search");
		return users;
	}

	/**
	* PUT for updating user
	* @param user (as input)
	* @return
	*/
	@PUT
	@Path("/update")
	public Response update(final Object[] input) {
		try {
			userManager.updateUser(new User(1, input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	/**
	* DELETE user from id
	* @param id
	* @return
	*/
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final int id) {
		try {
			userManager.removeUser(userManager.getUserComponent(id));
			return Response.ok("Deleted").build();
		} catch (Exception e) {
			return Response.ok("User not found").build();
		}
	}

}
