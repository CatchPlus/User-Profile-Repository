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
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.gridline.model.foaf.Person;
import nl.gridline.model.foaf.Persons;

import org.jboss.resteasy.annotations.GZIP;

/**
 * CRUD on persons: create, read, update, delete persons
 * The create and delete are not implemented(!) - a person is tightly coupled with a user.
 * <p />
 * Project upr-api<br />
 * UserProfile.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@Path(Constants.APIPATH)
public interface PersonsAPI
{
	@GET
	@Path("/person/{email}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Person readPerson(@PathParam("email") String id);

	@GET
	@GZIP
	@Path("/persons")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Persons readPersons(@QueryParam("start") String startEmail, @QueryParam("size") Integer size);

	@PUT
	@GZIP
	@Path("/person/{email}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response updatePerson(@PathParam("email") String email, Person person);

	// use create user first, than update update:
	// @POST
	// @GZIP
	// @Path("/person/{id}")
	// @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	// Response createPerson(@PathParam("id") String id, Person person);

	// @DELETE
	// @GZIP
	// @Path("/person/{id}")
	// Response deletePerson(@PathParam("id") String id);

}
