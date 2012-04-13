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

package nl.gridline.upr.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nl.gridline.upr.controller.EventController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-backend<br />
 * EventLogControllerListener.java created 8 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class EventControllerListener implements ServletContextListener
{
	private static final Logger LOG = LoggerFactory.getLogger(EventControllerListener.class);

	static
	{
		// set project name to upr
		System.setProperty("gridline.project.name", "upr");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		EventController controller = new EventController();
		try
		{
			controller.startup();
		}
		catch (Exception e)
		{
			LOG.error("failed to startup <" + EventController.EVENTCONTROLLER + ">", e);
		}
		sce.getServletContext().setAttribute(EventController.EVENTCONTROLLER, controller);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		EventController controller = (EventController) sce.getServletContext().getAttribute(
				EventController.EVENTCONTROLLER);
		if (controller != null)
		{
			try
			{
				controller.shutdown();
			}
			catch (Exception e)
			{
				LOG.error("Failed to shutdown <" + EventController.EVENTCONTROLLER + ">", e);
			}
		}
		else
		{
			LOG.warn("Failed to shutdown <" + EventController.EVENTCONTROLLER + "> - not found in servlet context");
		}

	}

}
