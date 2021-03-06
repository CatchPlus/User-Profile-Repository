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

package nl.gridline.model.foaf;

import java.io.Serializable;
import java.util.List;

import nl.gridline.zieook.api.JSonParent;

import com.google.common.collect.ImmutableList;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * Persons.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class Persons implements Serializable, JSonParent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2649295415973642015L;

	private List<Person> persons;

	public Persons()
	{
		persons = new ImmutableList.Builder<Person>().build();
	}

	public Persons(List<Person> persons)
	{
		this.persons = persons;
	}

	/**
	 * @return The persons.
	 */
	public List<Person> getPersons()
	{
		return persons;
	}

	/**
	 * @param persons The persons to set.
	 */
	public void setPersons(List<Person> persons)
	{
		this.persons = persons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Persons other = (Persons) obj;
		if (persons == null)
		{
			if (other.persons != null)
			{
				return false;
			}
		}
		else if (!persons.equals(other.persons))
		{
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return ModelConstants.toJSON(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.JSonParent#toJSON()
	 */
	@Override
	public String toJSON()
	{
		return ModelConstants.toJSON(this);
	}
}
