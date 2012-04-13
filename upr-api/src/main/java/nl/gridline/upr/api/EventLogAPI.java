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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.gridline.model.upr.Visit;
import nl.gridline.model.upr.Visits;

import org.jboss.resteasy.annotations.GZIP;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * EventLogAPI.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@Path(Constants.APIPATH)
public interface EventLogAPI
{

	/**
	 * @param visit
	 * @return
	 */
	@POST
	@GZIP
	@Path("/visited")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createVisited(Visit visit);

	/**
	 * return the last visited url for a given content provider and user id
	 * @param userId - (zieook) user id
	 * @param cp - content provider
	 * @param size - number of items to return
	 * @return a visited item
	 */
	@GET
	@GZIP
	@Path("/visited")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Visits getLastVisited(@QueryParam("user_id") Long userId, @QueryParam("recommender") String recommender,
			@QueryParam("size") Integer size);

	/**
	 * @param userId
	 * @param cp
	 * @return
	 */
	@DELETE
	@GZIP
	@Path("/visited")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response deleteVisited(Long userId, String cp);

}
