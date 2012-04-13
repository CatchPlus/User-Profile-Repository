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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nl.gridline.model.upr.Visit;
import nl.gridline.model.upr.Visits;
import nl.gridline.upr.api.EventLogAPI;
import nl.gridline.upr.controller.EventController;
import nl.gridline.zieook.exceptions.DoesNotExists;

import org.jboss.resteasy.spi.BadRequestException;

/**
 * [purpose]
 * <p />
 * Project upr-backend<br />
 * EventLogImpl.java created 8 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class EventLogImpl implements EventLogAPI
{

	// private static final Logger LOG = LoggerFactory.getLogger(EventLogImpl.class);

	@Context
	private ServletContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.EventLogAPI#createVisited(nl.gridline.model.upr.Visited)
	 */
	@Override
	public Response createVisited(Visit visit)
	{
		EventController controller = (EventController) context.getAttribute(EventController.EVENTCONTROLLER);

		// check user exists !?

		controller.createVisited(visit);

		return Response.status(201).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.EventLogAPI#getLastVisited(java.lang.Long, java.lang.String)
	 */
	@Override
	public Visits getLastVisited(Long userId, String recommender, Integer size)
	{
		if (userId == null)
		{
			throw new BadRequestException("at least provide 'user_id'");
		}

		if (size == null)
		{
			size = 100;
		}

		EventController controller = (EventController) context.getAttribute(EventController.EVENTCONTROLLER);

		Visits result = controller.getLastVisited(userId.longValue(), recommender, size.intValue());

		if (result == null)
		{
			throw new DoesNotExists("no data for request");
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.EventLogAPI#deleteVisited(java.lang.Long, java.lang.String)
	 */
	@Override
	public Response deleteVisited(Long userId, String recommender)
	{
		if (userId == null && recommender == null)
		{
			throw new BadRequestException("at least provide 'user_id' or 'cp' need to be provided");
		}

		EventController controller = (EventController) context.getAttribute(EventController.EVENTCONTROLLER);

		controller.deleteVisited(userId, recommender);

		return Response.status(200).build();
	}

}
