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

package nl.gridline.upr.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import java.util.List;

import nl.gridline.model.upr.Visit;
import nl.gridline.upr.model.HBaseEventTable;
import nl.gridline.zieook.configuration.Config;
import nl.gridline.zieook.data.hbase.HBaseManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-data<br />
 * EventTableTest.java created 21 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class EventTableTest
{

	private static final Logger LOG = LoggerFactory.getLogger(EventTableTest.class);

	private static HBaseManager manager;
	private static EventTable events;

	static
	{
		// set project name to upr
		System.setProperty("gridline.project.name", "upr");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{

		Config config = Config.getInstance();
		String zookeeper = config.getZooKeeperHost();
		assumeNotNull(zookeeper);

		// get HBase manager:
		manager = HBaseManager.getInstance(zookeeper);

		// create the table wrappers:

		events = new EventTable(new HBaseEventTable(manager));
	}

	@Test
	public void getVisits()
	{
		Visit v = new Visit("test", "test", "test-cp", 0L);
		events.putVisit(v);

		boolean found = false;
		List<Visit> visited = events.getVisited(0, null, 100);
		for (Visit vi : visited)
		{
			LOG.info("{}", vi);
			found = vi.equals(v);
			if (found)
			{
				break;
			}
		}
		assertTrue(found);

		events.deleteVisit(0L);

		visited = events.getVisited(0, null, 100);
		found = false;
		for (Visit vi : visited)
		{
			LOG.info("{}", vi);
			found = vi.equals(v);
			if (found)
			{
				break;
			}
		}
		assertTrue(!found);
	}
}
