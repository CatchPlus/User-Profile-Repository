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

package nl.gridline.upr.controller;

import java.util.Date;
import java.util.List;

import nl.gridline.model.upr.Visit;
import nl.gridline.model.upr.Visits;
import nl.gridline.upr.dao.EventTable;
import nl.gridline.upr.model.HBaseEventTable;
import nl.gridline.zieook.commons.ServerState;
import nl.gridline.zieook.commons.ZieOokCommons;
import nl.gridline.zieook.commons.ZieOokManager;
import nl.gridline.zieook.configuration.Config;
import nl.gridline.zieook.data.hbase.HBaseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-backend<br />
 * EventController.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class EventController implements ZieOokManager
{
	private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

	public static final String EVENTCONTROLLER = "eventcontroller";

	public static final String UPR_PERSONCONTROLLER = "upr-config.xml";

	private Config config;

	private boolean state;

	private HBaseManager manager;

	private EventTable events;

	// --------------------------
	// --- lifecycle methods: ---
	// --------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#startup()
	 */
	@Override
	public void startup() throws Exception
	{
		LOG.info(ZieOokCommons.someinformation("PersonsController"));

		LOG.info("loading configuration from: {}", UPR_PERSONCONTROLLER);
		config = Config.getInstance(UPR_PERSONCONTROLLER);

		String zookeeper = config.getZooKeeperHost();
		state = zookeeper != null;

		if (!state)
		{
			// failed...
			LOG.error("could not find key for zookeeper, startup failed. Please set <{}>", Config.ZIEOOK_ZOOKEEPER_HOST);
		}

		// get HBase manager:
		manager = HBaseManager.getInstance(zookeeper);

		// get the HBase person table:
		HBaseEventTable eventTable = new HBaseEventTable(manager);
		if (!eventTable.tableExists())
		{
			eventTable.create();
		}

		// create the table wrappers:

		events = new EventTable(eventTable);

		// connect to back end, check tables.
		state = true;
		LOG.info("The controller '{}' is (as far as we know) correctly started", EVENTCONTROLLER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#shutdown()
	 */
	@Override
	public void shutdown() throws Exception
	{
		LOG.info("The controller '{}' is shutdown", EVENTCONTROLLER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#state()
	 */
	@Override
	public boolean state()
	{
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#getState()
	 */
	@Override
	public ServerState getState()
	{
		final EventControllerState result = new EventControllerState();
		return result;
	}

	// ----------------------
	// --- data operation ---
	// ----------------------

	/**
	 * @param visit
	 */
	public void createVisited(Visit visit)
	{
		if (events.isVisit(visit.getUserId(), visit.getRecommender()))
		{
			visit.setCreated(new Date());
		}
		visit.setUpdated(new Date());
		events.putVisit(visit);
	}

	/**
	 * @param userId
	 * @param recommender
	 * @return
	 */
	public Visits getLastVisited(long user, String recommender, int size)
	{
		List<Visit> result = events.getVisited(user, recommender, size);
		if (result != null && !result.isEmpty())
		{
			return new Visits(result);
		}
		return null;
	}

	/**
	 * @param userId
	 * @param recommender
	 */
	public void deleteVisited(Long userId, String recommender)
	{
		if (userId == null && recommender != null)
		{
			events.deleteVisit(recommender);
		}
		else if (userId != null && recommender == null)
		{
			events.deleteVisit(userId.longValue());
		}
		else
		{
			events.deleteVisit(recommender, userId.longValue());
		}
	}
}
