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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.HashSet;
import java.util.Set;

import nl.gridline.model.foaf.Person;
import nl.gridline.model.upr.User;
import nl.gridline.model.upr.Users;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * [purpose]
 * <p />
 * Project upr-backend<br />
 * PersonsControllerTest.java created 10 jan. 2012
 * <p />
 * Copyright, all rights reserved 2012 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class PersonsControllerTest
{

	private static PersonsController controller;

	static
	{
		// set project name to upr
		System.setProperty("gridline.project.name", "upr");
	}

	/**
	 * @throws ZooKeeperConnectionException
	 * @throws MasterNotRunningException
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws MasterNotRunningException, ZooKeeperConnectionException
	{
		controller = new PersonsController();
		controller.startup();
		assumeTrue(controller.state());
	}

	static final String EMAIL = "testuser@gridline.nl";
	static final String USERNAME = "test-username";
	static final String URI = "test-uri";

	static final String CITY = "Amsterdam";
	static final String COUNTRY = "Netherlands";

	private User getTestUser()
	{
		User u = new User(null, EMAIL, "test-digest", USERNAME, "test-name", URI, 100, 100);
		return u;
	}

	@Before
	public void before()
	{
		User u = getTestUser();
		assertTrue(controller.createUser(u));
	}

	@After
	public void after()
	{
		assumeTrue(controller.deleteUser(EMAIL));
		assumeTrue(controller.getByEmail(EMAIL, false) == null);
	}

	private static void looselyEquals(User expected, User given)
	{
		assertEquals(expected.getEmail(), given.getEmail());
		assertEquals(expected.getDigest(), given.getDigest());
		assertEquals(expected.getUsername(), given.getUsername());
		assertEquals(expected.getName(), given.getName());
		assertEquals(expected.getUri(), given.getUri());
		assertEquals(expected.getLogHistory(), given.getLogHistory());
	}

	@Test
	public void testUserRead()
	{
		User u = getTestUser();

		looselyEquals(u, controller.getByEmail(EMAIL, false));
		looselyEquals(u, controller.findByUsername(USERNAME, false));
		looselyEquals(u, controller.findByURI(URI, false));
	}

	@Test
	public void testUserList()
	{
		User u = getTestUser();

		Users result = controller.getUsers(null, 100, false);

		Set<User> users = new HashSet<User>();
		users.addAll(result.getUsers());

		assertEquals(result.getUsers().size(), users.size());
		boolean done = false;
		for (User f : users)
		{
			if (EMAIL.equals(f.getEmail()))
			{
				looselyEquals(u, f);
				done = true;
				break;
			}
		}
		assertTrue(done);
	}

	@Test
	public void testPersonCRUD()
	{
		// User u = getTestUser();

		// test person read:
		Person p = controller.getPerson(EMAIL);
		assertEquals(EMAIL, p.getEmail());

		// update:
		p.setCity(CITY);
		p.setCountry(COUNTRY);
		controller.updatePerson(EMAIL, p);

		// read again:
		Person newP = controller.getPerson(EMAIL);
		assertEquals(CITY, newP.getCity());
		assertEquals(COUNTRY, newP.getCountry());

		// delete user, person should also be gone
		assumeTrue(controller.deleteUser(EMAIL));
		assertEquals(null, controller.getPerson(EMAIL));

	}

}
