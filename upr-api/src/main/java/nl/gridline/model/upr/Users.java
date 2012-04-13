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

package nl.gridline.model.upr;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.gridline.zieook.api.JSonParent;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * Users.java created Sep 28, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users implements Serializable, JSonParent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9153599533801285222L;

	@XmlElement(name = "user")
	private List<User> users;

	@XmlElement(name = "start_username")
	private String startUsername;

	@XmlElement(name = "size")
	private long size;

	public Users()
	{

	}

	/**
	 * @param users
	 */
	public Users(List<User> users, String startUsername, long size)
	{
		super();
		this.users = users;
		this.startUsername = startUsername;
		this.size = size;
	}

	/**
	 * @return The users.
	 */
	public List<User> getUsers()
	{
		return users;
	}

	/**
	 * @param users The users to set.
	 */
	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	/**
	 * @return The start.
	 */
	public String getStart()
	{
		return startUsername;
	}

	/**
	 * @param start The start to set.
	 */
	public void setStart(String startUsername)
	{
		this.startUsername = startUsername;
	}

	/**
	 * @return The size.
	 */
	public long getSize()
	{
		return size;
	}

	/**
	 * @param size The size to set.
	 */
	public void setSize(long size)
	{
		this.size = size;
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
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		Users other = (Users) obj;
		if (users == null)
		{
			if (other.users != null)
			{
				return false;
			}
		}
		else if (!users.equals(other.users))
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
		StringBuilder b = new StringBuilder();
		for (User u : users)
		{
			b.append(u).append(" ");
		}
		return b.toString().trim();
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
