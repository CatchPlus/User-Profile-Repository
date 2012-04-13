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

import nl.gridline.model.foaf.Person;
import nl.gridline.upr.api.PersonsAPI;
import nl.gridline.upr.controller.PersonsController;
import nl.gridline.upr.exceptions.PersonDoesNotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p />
 * Project upr-backend<br />
 * Persons.java created 2 sep. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class Persons implements PersonsAPI
{

	private static final Logger LOG = LoggerFactory.getLogger(Persons.class);

	@Context
	private ServletContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.PersonsAPI#readPerson(java.lang.String)
	 */
	@Override
	public Person readPerson(String id)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);

		if (!controller.existsByEmail(id))
		{
			throw new PersonDoesNotExistException("could not find user: {}", id, null);
		}

		LOG.debug("READ person id={}", id);
		final Person person = controller.getPerson(id);
		if (person == null)
		{
			throw new PersonDoesNotExistException("could not find user: {}", id, null);
		}
		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.upr.api.PersonsAPI#readPersons()
	 */
	@Override
	public nl.gridline.model.foaf.Persons readPersons(String startEmail, Integer size)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);
		LOG.debug("READ persons start={}, size={}", startEmail, size);
		// defaults:
		int sizeI = 100;

		if (size != null)
		{
			sizeI = size;
		}

		final nl.gridline.model.foaf.Persons persons = controller.getPersons(startEmail, sizeI);
		if (persons == null)
		{
			throw new PersonDoesNotExistException("could not find user: {}", startEmail, null);
		}
		return persons;
	}

	/*
	 * (non-Javadoc)updatePerson
	 * 
	 * @see nl.gridline.upr.api.PersonsAPI#updatePerson(java.lang.String, nl.gridline.model.foaf.Person)
	 */
	@Override
	public Response updatePerson(String id, Person person)
	{
		PersonsController controller = (PersonsController) context.getAttribute(PersonsController.PERSONSCONTROLLER);
		LOG.debug("UPDATE person id={} person={}", id, person);
		controller.updatePerson(id, person);
		return Response.ok().build();
	}

}
