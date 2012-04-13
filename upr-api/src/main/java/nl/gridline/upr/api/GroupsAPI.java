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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.gridline.model.foaf.Group;
import nl.gridline.model.foaf.Groups;

import org.jboss.resteasy.annotations.GZIP;

/**
 * CRUD on groups: create, read, update, delete groups
 * <p />
 * Project upr-api<br />
 * GroupsAPI.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@Path(Constants.APIPATH)
public interface GroupsAPI
{
	@POST
	@GZIP
	@Path("/group")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response createGroup(Group group);

	@GET
	@GZIP
	@Path("/group/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Group readGroup(@PathParam("id") String id);

	@GET
	@GZIP
	@Path("/groups")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Groups readGroups();

	@PUT
	@GZIP
	@Path("/group/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response updateGroup(@PathParam("id") String id, Group group);

	@DELETE
	@GZIP
	@Path("/group/{id}")
	Response deleteGroup(@PathParam("id") String id);

}
