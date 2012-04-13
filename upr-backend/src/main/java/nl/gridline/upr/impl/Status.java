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

package nl.gridline.upr.impl;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nl.gridline.upr.api.Constants;
import nl.gridline.upr.controller.PersonsController;
import nl.gridline.zieook.commons.ServerState;

import org.jboss.resteasy.annotations.GZIP;

/**
 * the simple purpose of returning some status info about this server
 * <p />
 * Project upr-backend<br />
 * Status.java created Sep 28, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@Path(Constants.APIPATH)
public class Status
{
	@Context
	private ServletContext context;

	@GET
	@GZIP
	@Path("/status")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ServerState getStatus()
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		return controller.getState();
	}
}
