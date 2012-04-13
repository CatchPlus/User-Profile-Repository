/**
 * Copyright (C) 2012 GridLine <info@gridline.nl>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 *
 */

package nl.gridline.upr.api;

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

import nl.gridline.model.upr.User;
import nl.gridline.model.upr.UserExists;
import nl.gridline.model.upr.Users;

import org.jboss.resteasy.annotations.GZIP;

/**
 * Create / updates and looks up users for the UPR - can only be used
 * <p />
 * Project upr-api<br />
 * UsersAPI.java created Sep 27, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@Path(Constants.APIPATH)
public interface UsersAPI
{

	@GET
	@GZIP
	@Path("users")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Users getUsers(@QueryParam("start_username") String startUsername, @QueryParam("size") Long size,
			@QueryParam("visits") Boolean visits);

	@GET
	@GZIP
	@Path("users/find_by_uri")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findUserByURI(@QueryParam("uri") String uri, @QueryParam("visits") Boolean visits);

	@GET
	@GZIP
	@Path("users/find_by_email")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findByEmail(@QueryParam("email") String email, @QueryParam("visits") Boolean visits);

	@GET
	@GZIP
	@Path("users/find_or_create_by_email")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findOrCreateByEmail(@QueryParam("email") String email);

	@GET
	@GZIP
	@Path("users/find_by_username")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findByUsername(@QueryParam("username") String username, @QueryParam("visits") Boolean visits);

	@GET
	@GZIP
	@Path("users/find_by_id")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findById(@QueryParam("id") Long id, @QueryParam("visits") Boolean visits);

	@POST
	@GZIP
	@Path("users/check_existence")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	UserExists userExists(User user);

	@POST
	@GZIP
	@Path("users")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User createUser(User user);

	@PUT
	@GZIP
	@Path("users/{email}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response updateUser(@PathParam("email") String email, User user);

	@DELETE
	@GZIP
	@Path("users/{email}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response deleteUser(@PathParam("email") String email);

}
