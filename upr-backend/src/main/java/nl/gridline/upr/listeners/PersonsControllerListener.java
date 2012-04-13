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

import nl.gridline.upr.controller.PersonsController;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * starts the PersonsController (for Persons)
 * <p />
 * Project upr-backend<br />
 * PersonsControllerListener.java created 2 sep. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class PersonsControllerListener implements ServletContextListener
{

	static
	{
		// set project name to upr
		System.setProperty("gridline.project.name", "upr");
	}

	private static final Logger LOG = LoggerFactory.getLogger(PersonsControllerListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		PersonsController controller = new PersonsController();
		try
		{
			controller.startup();
		}
		catch (MasterNotRunningException e)
		{
			LOG.error("failed to startup <" + PersonsController.PERSONSCONTROLLER + ">", e);
		}
		catch (ZooKeeperConnectionException e)
		{
			LOG.error("failed to startup <" + PersonsController.PERSONSCONTROLLER + ">", e);
		}
		sce.getServletContext().setAttribute(PersonsController.PERSONSCONTROLLER, controller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent context)
	{
		PersonsController controller = (PersonsController) context.getServletContext().getAttribute(
				PersonsController.PERSONSCONTROLLER);
		if (controller != null)
		{
			try
			{
				controller.shutdown();
			}
			catch (Exception e)
			{
				LOG.error("Failed to shutdown <" + PersonsController.PERSONSCONTROLLER + ">", e);
			}
		}
		else
		{
			LOG.warn("Failed to shutdown <" + PersonsController.PERSONSCONTROLLER + "> - not found in servlet context");
		}
	}

}
