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

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.util.List;

import nl.gridline.model.upr.User;
import nl.gridline.upr.model.HBasePersonTable;
import nl.gridline.zieook.configuration.Config;
import nl.gridline.zieook.data.hbase.HBaseManager;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-data<br />
 * UserTableTest.java created 9 jan. 2012
 * <p />
 * Copyright, all rights reserved 2012 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class UserTableTest
{
	private static final Logger LOG = LoggerFactory.getLogger(UserTableTest.class);

	private static HBaseManager manager;
	private static EventTable events;

	private static UserTable users;

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
		HBasePersonTable table = new HBasePersonTable(manager);
		assumeTrue(table.tableExists());

		users = new UserTable(table);
	}

	@Test
	@Ignore
	public void testUser() throws IOException
	{

		User user = users.findByEmail("jobtiel@gmail.com");
		System.out.println(user.toJSON());

		user.setAdmin(true);
		users.update(user);
		System.out.println(user.isAdmin());

		List<User> result = users.getUsers(null, 200);
		for (User u : result)
		{
			System.out.println(u.getId() + " " + u.getEmail());
		}

	}

}
