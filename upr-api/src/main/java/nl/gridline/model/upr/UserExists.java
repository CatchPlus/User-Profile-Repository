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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.gridline.zieook.api.JSonParent;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * UserExists.java created 3 okt. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "exists")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExists implements Serializable, JSonParent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5708252664258198491L;

	@XmlElement
	private Boolean username;

	@XmlElement
	private Boolean email;

	@XmlElement
	private Boolean uri;

	/**
	 * @param username
	 * @param email
	 */
	public UserExists(Boolean username, Boolean email, Boolean uri)
	{
		this.username = username;
		this.email = email;
		this.uri = uri;
	}

	public UserExists()
	{
		// no-arg contructor
	}

	/**
	 * @return The username.
	 */
	public Boolean isUsername()
	{
		return username;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(Boolean username)
	{
		this.username = username;
	}

	/**
	 * @return The email.
	 */
	public boolean isEmail()
	{
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(Boolean email)
	{
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * boolean
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (email ? 1231 : 1237);
		result = prime * result + (username ? 1231 : 1237);
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
		UserExists other = (UserExists) obj;
		if (email != other.email)
		{
			return false;
		}
		if (username != other.username)
		{
			return false;
		}
		return true;
	}

	/**
	 * @return The uri.
	 */
	public Boolean getUri()
	{
		return uri;
	}

	/**
	 * @param uri The uri to set.
	 */
	public void setUri(Boolean uri)
	{
		this.uri = uri;
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
