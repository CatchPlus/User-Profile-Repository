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

import nl.gridline.model.upr.User;
import nl.gridline.model.upr.UserExists;
import nl.gridline.upr.api.UsersAPI;
import nl.gridline.upr.controller.PersonsController;
import nl.gridline.upr.exceptions.UserAlreadyExsistsException;
import nl.gridline.upr.exceptions.UserDoesNotExistException;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.InternalServerErrorException;

/**
 * [purpose]
 * <p />
 * Project upr-backend<br />
 * Users.java created Sep 27, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class Users implements UsersAPI
{

	@Context
	private ServletContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#getUsers(java.lang.Long, java.lang.Long)
	 */
	@Override
	public nl.gridline.model.upr.Users getUsers(String startUsername, Long size, Boolean visits)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (startUsername == null)
		{
			startUsername = null;
		}
		if (size == null)
		{
			size = 100L;
		}
		if (visits == null)
		{
			visits = Boolean.FALSE;
		}

		return controller.getUsers(startUsername, size, visits.booleanValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#findUserByURI(java.lang.String)
	 */
	@Override
	public User findUserByURI(String uri, Boolean visits)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (uri == null)
		{
			throw new BadRequestException("parameter missing: uri");
		}
		if (visits == null)
		{
			visits = Boolean.FALSE;
		}

		final User result = controller.findByURI(uri, visits.booleanValue());
		if (result == null)
		{
			throw new UserDoesNotExistException("could not find user: {}", uri, null);
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String username, Boolean visits)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (username == null)
		{
			throw new BadRequestException("parameter missing: username");
		}
		if (visits == null)
		{
			visits = Boolean.FALSE;
		}

		final User result = controller.findByUsername(username, visits.booleanValue());
		if (result == null)
		{
			throw new UserDoesNotExistException("could not find user: {}", username, null);
		}

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#findByEmail(java.lang.String)
	 */
	@Override
	public User findByEmail(String email, Boolean visits)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);
		if (email == null)
		{
			throw new BadRequestException("parameter missing: username");
		}
		if (visits == null)
		{
			visits = Boolean.FALSE;
		}

		final User result = controller.getByEmail(email, visits.booleanValue());
		if (result == null)
		{
			throw new UserDoesNotExistException("could not find user: {}", email, null);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#findOrCreateByEmail(java.lang.String)
	 */
	@Override
	public User findOrCreateByEmail(String email)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (email == null)
		{
			throw new BadRequestException("parameter missing: username");
		}
		User result = controller.getByEmail(email, false);
		if (result == null)
		{
			result = controller.createUserByEmail(email);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#findById(java.lang.Long)
	 */
	@Override
	public User findById(Long id, Boolean visits)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (id == null)
		{
			throw new BadRequestException("parameter missing: id");
		}
		if (visits == null)
		{
			visits = Boolean.FALSE;
		}

		final User result = controller.findUser(id, visits.booleanValue());
		if (result == null)
		{
			throw new UserDoesNotExistException("could not find user: {}", id, null);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#createUser(org.apache.hadoop.hbase.security.User)
	 */
	@Override
	public User createUser(User user)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);
		if (user == null)
		{
			throw new BadRequestException("parameter missing: user");
		}

		if (user.getId() != null)
		{
			throw new BadRequestException("server decides on id");
		}

		final String email = user.getEmail();

		if (email == null)
		{
			throw new BadRequestException("at least provide the users email");
		}

		// see if the user already exists...

		if (controller.existsByEmail(email))
		{
			// throw a 304
			throw new UserAlreadyExsistsException(email);
		}

		boolean result = controller.createUser(user);
		if (result)
		{
			return controller.getByEmail(email, false);
		}
		else
		{
			throw new InternalServerErrorException("failed to create user");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#userExists(nl.gridline.model.upr.User)
	 */
	@Override
	public UserExists userExists(User user)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);
		if (user.getUsername() == null && user.getEmail() == null && user.getUri() == null)
		{
			throw new BadRequestException("at least provide username or e-mail");
		}
		final UserExists result = controller.userExists(user);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#updateUser(java.lang.String, org.apache.hadoop.hbase.security.User)
	 */
	@Override
	public Response updateUser(String email, User user)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (email == null)
		{
			throw new BadRequestException("parameter missing: id");
		}

		if (!controller.existsByEmail(email))
		{
			throw new UserDoesNotExistException("the given user does not exist");
		}

		// other data is allowed to change...
		boolean result = controller.updateUser(email, user);
		if (result)
		{
			return Response.status(201).build();
		}
		else
		{
			// throw internal server exception
			return Response.status(500).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.UsersAPI#deleteUser(java.lang.String)
	 */
	@Override
	public Response deleteUser(String email)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		boolean result = controller.deleteUser(email);
		if (result)
		{
			return Response.status(200).build();
		}
		else
		{
			return Response.status(404).build();
		}

	}
}
