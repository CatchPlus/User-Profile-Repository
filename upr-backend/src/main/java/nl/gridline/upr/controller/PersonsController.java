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

import java.io.IOException;
import java.util.List;

import nl.gridline.model.foaf.Person;
import nl.gridline.model.foaf.Persons;
import nl.gridline.model.upr.User;
import nl.gridline.model.upr.UserExists;
import nl.gridline.model.upr.Users;
import nl.gridline.model.upr.Visit;
import nl.gridline.model.upr.Visits;
import nl.gridline.upr.dao.EventTable;
import nl.gridline.upr.dao.PersonTable;
import nl.gridline.upr.dao.UserTable;
import nl.gridline.upr.model.HBaseEventTable;
import nl.gridline.upr.model.HBasePersonTable;
import nl.gridline.zieook.commons.ServerState;
import nl.gridline.zieook.commons.ZieOokCommons;
import nl.gridline.zieook.commons.ZieOokManager;
import nl.gridline.zieook.configuration.Config;
import nl.gridline.zieook.data.hbase.HBaseManager;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Persons Controller, all person related operations (CRUD) and some other stuff
 * <p />
 * Project upr-backend<br />
 * PersonsController.java created 2 sep. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class PersonsController implements ZieOokManager
{

	private static final Logger LOG = LoggerFactory.getLogger(PersonsController.class);

	public static final String PERSONSCONTROLLER = "upr.persons-controller";

	public static final String UPR_PERSONCONTROLLER = "upr-config.xml";

	private boolean state;

	private Config config;

	// hbase manager:
	private HBaseManager manager;

	// tables:
	private PersonTable persons;
	private UserTable users;
	private EventTable events;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#startup()
	 */
	@Override
	public void startup() throws MasterNotRunningException, ZooKeeperConnectionException
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
			return;
		}

		// get HBase manager:
		manager = HBaseManager.getInstance(zookeeper);

		// get the HBase person table:
		HBasePersonTable usersTable = new HBasePersonTable(manager);
		if (!usersTable.tableExists())
		{
			usersTable.create();
		}

		// create the table wrappers:
		persons = new PersonTable(usersTable);
		users = new UserTable(usersTable);

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
		LOG.info("The controller '{}' is (as far as we know) correctly started", PERSONSCONTROLLER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#shutdown()
	 */
	@Override
	public void shutdown() throws Exception
	{
		// disconnect from back end.
		LOG.info("The controller '{}' is shutdown", PERSONSCONTROLLER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.commons.ZieOokManager#state()
	 */
	@Override
	public boolean state()
	{
		// return the state (true / false) after init was called
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
		// return a statistics state (jaxb)object - that shows the state of this controller.
		final PersonsControllerState result = new PersonsControllerState();
		// set data...

		return result;
	}

	/**
	 * @param id
	 * @return
	 */
	public Person getPerson(String id)
	{

		Person result = persons.findByEmail(id);

		// fallback for old data (no persons)
		if (result == null)
		{
			User u = users.findByEmail(id);
			if (u == null)
			{
				throw new InternalServerErrorException("cannot find user for <" + id + ">");
			}
			Person p = new Person(u.getEmail());
			p.setCreated(System.currentTimeMillis());
			p.setUpdated(System.currentTimeMillis());
			persons.createPerson(p);
			result = p;
		}

		return result;

	}

	/**
	 * @param startI
	 * @param sizeI
	 * @return
	 */
	public Persons getPersons(String startEmail, int size)
	{
		try
		{
			return new nl.gridline.model.foaf.Persons(persons.listPerons(startEmail, size));
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException(e);
		}
	}

	// users:
	public User findUser(long id, boolean isVisits)
	{
		try
		{
			User result = users.getUserById(id);
			if (result != null && isVisits)
			{
				List<Visit> visits = events.getVisited(result.getId(), null, 100);
				result.setVisits(new Visits(visits));
			}
			return result;
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException("failed to find record due to an internal error", e);
		}
	}

	/**
	 * @param uri
	 * @param b
	 * @return
	 */
	public User findByURI(String uri, boolean isVisits)
	{
		try
		{
			User result = users.findByUri(uri);
			if (result != null && isVisits)
			{
				List<Visit> visits = events.getVisited(result.getId(), null, 100);
				result.setVisits(new Visits(visits));
			}
			return result;
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException("failed to retrieve record due to an internal error", e);
		}
	}

	/**
	 * @param username
	 * @param isVisits
	 * @return
	 * @throws IOException
	 */
	public User findByUsername(String username, boolean isVisits)
	{
		try
		{
			User result = users.findByUsername(username);
			if (result != null && isVisits)
			{
				List<Visit> visits = events.getVisited(result.getId(), null, 100);
				result.setVisits(new Visits(visits));
			}
			return result;
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException("failed to retrieve record due to an internal error", e);

		}
	}

	/**
	 * @param email
	 * @param isVisits
	 * @return
	 */
	public User getByEmail(String email, boolean isVisits)
	{
		User result = users.findByEmail(email);
		if (result != null && isVisits)
		{
			List<Visit> visits = events.getVisited(result.getId(), null, 100);
			result.setVisits(new Visits(visits));
		}
		return result;
	}

	public boolean existsByEmail(String email)
	{
		return users.existsByEmail(email);
	}

	/**
	 * @param email
	 * @return
	 */
	public User createUserByEmail(String email)
	{
		try
		{
			long id = users.getId();
			// (Long id, String email, String digest, String username, String uri)
			final User user = new User(id, email, null, null, null, null);
			final Person person = new Person(email);
			createUserPerson(user, person);
			return user;
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException("failed to create record due to an internal error", e);
		}
	}

	/**
	 * @param user
	 * @return
	 */
	public boolean createUser(User user)
	{
		try
		{
			long id = users.getId(); // generate a user id (zieook-id)
			user.setId(id);
			final Person person = new Person(user.getEmail());
			return createUserPerson(user, person);
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException("failed to create record due to an internal error", e);
		}

	}

	/**
	 * @param user
	 * @return
	 */
	public boolean updateUser(String email, User updates)
	{
		return updateUserPerson(email, updates, null);
	}

	/**
	 * @param id
	 * @param person
	 * @return
	 */
	public boolean updatePerson(String email, Person updates)
	{
		return updateUserPerson(email, null, updates);
	}

	/**
	 * @param startId
	 * @param size
	 * @param b
	 * @return
	 */
	public Users getUsers(String startUsername, long size, boolean isVisits)
	{
		try
		{
			List<User> result = users.getUsers(startUsername, size);
			if (isVisits && result.size() > 0)
			{
				for (User u : result)
				{
					List<Visit> visits = events.getVisited(u.getId(), null, 100);
					u.setVisits(new Visits(visits));
				}
			}
			return new Users(result, startUsername, result.size());
		}
		catch (IOException e)
		{
			throw new InternalServerErrorException("failed to retrieve user records due to an internal error", e);
		}
	}

	/**
	 * @param user
	 * @return
	 */
	public UserExists userExists(User user)
	{
		final String username = user.getUsername();
		final String email = user.getEmail();
		final String uri = user.getUri();

		Boolean uExists = null;
		Boolean eExists = null;
		Boolean urExists = null;

		if (username != null)
		{
			uExists = findByUsername(username, false) != null;
		}
		if (email != null)
		{
			eExists = existsByEmail(user.getEmail());
		}
		if (uri != null)
		{
			urExists = findByURI(uri, false) != null;
		}

		return new UserExists(uExists, eExists, urExists);
	}

	/**
	 * @param email
	 * @return
	 */
	public boolean deleteUser(String email)
	{
		return deleteUserPerson(email);
	}

	// ------------------------
	// ---- helper methods ----
	// ------------------------

	private boolean deleteUserPerson(String email)
	{
		boolean result = users.findByEmail(email) != null;
		if (result)
		{
			users.deleteUser(email);
			persons.deletePerson(email);

			return users.findByEmail(email) == null;
		}
		else
		{
			return false;
		}
	}

	private boolean createUserPerson(User user, Person person)
	{
		String email = user.getEmail();

		// set create & update times:
		final long now = System.currentTimeMillis();
		user.setCreated(now);
		user.setUpdated(now);
		person.setCreated(now);
		person.setUpdated(now);

		Preconditions.checkArgument(email.equals(person.getEmail()));
		return persons.createPerson(person) && users.createUser(user);
	}

	private boolean updateUserPerson(String email, User user, Person person)
	{
		User newUser = users.findByEmail(email);

		// if there is no user, we cannot update:
		if (newUser == null)
		{
			return false;
		}

		Person newPerson = persons.findByEmail(email);

		if (newPerson == null)
		{
			newPerson = new Person(email);
		}

		newUser.update(user);

		newUser.setUpdated(System.currentTimeMillis());

		// fallback for old data:
		if (newUser.getCreated() == null)
		{
			newUser.setCreated(System.currentTimeMillis());
		}

		newPerson.update(person);
		newPerson.setUpdated(System.currentTimeMillis());

		// fallback for old data:
		if (newPerson.getCreated() == null)
		{
			newPerson.setCreated(System.currentTimeMillis());
		}

		newPerson.setEmail(newUser.getEmail());

		final boolean result = persons.createPerson(newPerson) && users.createUser(newUser);

		// delete old user, if the e-mail adress has changed:
		if (!email.equals(newUser.getEmail()))
		{

			persons.deletePerson(email);
			users.deleteUser(email);
		}

		return result;
	}
}
