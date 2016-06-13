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
	
	
	@Inject
	UserManager userManager;

	/**
	* @param user
	* @return
	*/
	@POST
	public Response create(final User user) {
		
		try {
			userManager.insertUser(user);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		//here we use User#getId(), assuming that it provides the identifier to retrieve the created User resource. 
		return Response.created(UriBuilder.fromResource(UserEndpoint.class).path(String.valueOf(user.getId())).build())
				.build();
	}

	/**
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
	* @param startPosition
	* @param maxResult
	* @return
	*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object[][] listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//userManager.updateTable("user_info");
		final Object[][] users = userManager.getTable("user_info");
		return users;
	}

	/**
	* @param id
	* @param user
	* @return
	*/
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") int id, final User user) {
		try {
			user.setId(id);
			userManager.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	/**
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