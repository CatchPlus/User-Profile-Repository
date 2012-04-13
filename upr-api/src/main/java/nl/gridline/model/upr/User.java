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
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.gridline.zieook.api.JSonParent;
import nl.gridline.zieook.api.StorableHBase;
import nl.gridline.zieook.api.Updatable;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * A UPR user (for use with the front-end rails application)
 * <p />
 * Project upr-api<br />
 * User.java created Sep 27, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements StorableHBase, JSonParent, Serializable, Updatable<User>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2356326611183633690L;

	@XmlElement(name = "id")
	private Long id;

	@XmlElement(name = "email", required = true)
	private String email;

	@XmlElement(name = "password_digest")
	private String digest;

	@XmlElement(name = "username", required = true)
	private String username;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "uri")
	private String uri;

	@XmlElement(name = "log_history")
	private Boolean logHistory;

	@XmlElement(name = "created_at", required = false)
	private Long created;

	@XmlElement(name = "updated_at", required = false)
	private Long updated;

	@XmlElement(name = "status", required = false)
	private String status;

	@XmlElement(name = "admin")
	private Boolean admin;

	@XmlElement(name = "confirmation_code")
	private String confirmation;

	@XmlElement(name = "visits")
	private Visits visits;

	/**
	 * create a user
	 */
	public User()
	{
		// no-arg constructor
	}

	/**
	 * create a user
	 * @param id
	 * @param email
	 * @param digest
	 * @param username
	 * @param uri
	 */
	public User(Long id, String email, String digest, String username, String name, String uri)
	{
		this.id = id;
		this.email = email;
		this.digest = digest;
		this.name = name;
		this.username = username;
		this.uri = uri;
	}

	/**
	 * create a user with
	 * @param id
	 * @param email
	 * @param digest
	 * @param username
	 * @param uri
	 * @param created
	 * @param updated
	 */
	public User(Long id, String email, String digest, String username, String name, String uri, long created,
			long updated)
	{
		super();
		this.id = id;
		this.email = email.toLowerCase();
		this.digest = digest;
		this.username = username;
		this.name = name;
		this.uri = uri;
		this.created = created;
		this.updated = updated;
	}

	/**
	 * Create a user based on the map
	 * @param map
	 */
	public User(NavigableMap<byte[], byte[]> map)
	{
		id = ModelConstants.getId(map);
		email = ModelConstants.getEmail(map);
		digest = ModelConstants.getDigest(map);
		username = ModelConstants.getUsername(map);
		uri = ModelConstants.getURI(map);
		created = ModelConstants.getCreatedAt(map);
		updated = ModelConstants.getUpdatedAt(map);
		name = ModelConstants.getName(map);
		logHistory = ModelConstants.getLogHistory(map);
		admin = ModelConstants.getAdmin(map);
		status = ModelConstants.getStatus(map);
		confirmation = ModelConstants.getConfirmation(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.StorableHBase#toMap(java.util.NavigableMap)
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap(NavigableMap<byte[], byte[]> map)
	{
		ModelConstants.putId(map, id);
		ModelConstants.putEmail(map, email);
		ModelConstants.putDigest(map, digest);
		ModelConstants.putUsername(map, username);
		ModelConstants.putName(map, name);
		ModelConstants.putURI(map, uri);
		ModelConstants.putLogHistory(map, logHistory);
		ModelConstants.putCreatedAt(map, created);
		ModelConstants.putUpdatedAt(map, updated);
		ModelConstants.putAdmin(map, admin);
		ModelConstants.putStatus(map, status);
		ModelConstants.putConfirmation(map, confirmation);
		return map;
	}

	/**
	 * Get a navigable map for this user (directly suitable for HBase)
	 * @return
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap()
	{
		return toMap(new TreeMap<byte[], byte[]>(Bytes.BYTES_COMPARATOR));
	}

	@Override
	public User update(User updates)
	{
		if (updates == null)
		{
			return this;
		}

		if (updates.getDigest() != null)
		{
			setDigest(updates.getDigest());
		}
		if (updates.getEmail() != null)
		{
			setEmail(updates.getEmail());
		}
		if (updates.getUri() != null)
		{
			setUri(updates.getUri());
		}
		if (updates.getUsername() != null)
		{
			setUsername(updates.getUsername());
		}
		if (updates.getName() != null)
		{
			setName(updates.getName());
		}
		if (updates.getUri() != null)
		{
			setUri(updates.getUri());
		}
		if (updates.getUpdated() != null)
		{
			setUpdated(updates.getUpdated());
		}
		if (updates.getCreated() != null)
		{
			setCreated(updates.getCreated());
		}
		if (updates.logHistory != null)
		{
			setLogHistory(updates.logHistory);
		}
		if (updates.admin != null)
		{
			setAdmin(updates.admin);
		}
		if (updates.status != null)
		{
			setStatus(updates.status);
		}
		if (updates.confirmation != null)
		{
			setConfirmation(updates.confirmation);
		}

		return this;
	}

	/**
	 * @return The logHistory.
	 */
	public Boolean getLogHistory()
	{
		return logHistory;
	}

	/**
	 * @param logHistory The logHistory to set.
	 */
	public void setLogHistory(Boolean logHistory)
	{
		this.logHistory = logHistory;
	}

	/**
	 * @return The id.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return The created.
	 */
	public Long getCreated()
	{
		return created;
	}

	/**
	 * @param created The created to set.
	 */
	public void setCreated(Long created)
	{
		this.created = created;
	}

	/**
	 * @return The updated.
	 */
	public Long getUpdated()
	{
		return updated;
	}

	/**
	 * @param updated The updated to set.
	 */
	public void setUpdated(Long updated)
	{
		this.updated = updated;
	}

	/**
	 * @return The email.
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email)
	{
		this.email = email.toLowerCase();
	}

	/**
	 * @return The digest.
	 */
	public String getDigest()
	{
		return digest;
	}

	/**
	 * @param digest The digest to set.
	 */
	public void setDigest(String digest)
	{
		this.digest = digest;
	}

	/**
	 * @return The username.
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return The uri.
	 */
	public String getUri()
	{
		return uri;
	}

	/**
	 * @param uri The uri to set.
	 */
	public void setUri(String uri)
	{
		this.uri = uri;
	}

	/**
	 * @return The name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean isAdmin()
	{
		return admin;
	}

	public void setAdmin(Boolean admin)
	{
		this.admin = admin;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getConfirmation()
	{
		return confirmation;
	}

	public void setConfirmation(String confirmation)
	{
		this.confirmation = confirmation;
	}

	public Visits getVisits()
	{
		return visits;
	}

	public void setVisits(Visits visits)
	{
		this.visits = visits;
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
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((digest == null) ? 0 : digest.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (created == null)
		{
			if (other.created != null)
			{
				return false;
			}
		}
		else if (!created.equals(other.created))
		{
			return false;
		}
		if (digest == null)
		{
			if (other.digest != null)
			{
				return false;
			}
		}
		else if (!digest.equals(other.digest))
		{
			return false;
		}
		if (email == null)
		{
			if (other.email != null)
			{
				return false;
			}
		}
		else if (!email.equals(other.email))
		{
			return false;
		}
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (updated == null)
		{
			if (other.updated != null)
			{
				return false;
			}
		}
		else if (!updated.equals(other.updated))
		{
			return false;
		}
		if (uri == null)
		{
			if (other.uri != null)
			{
				return false;
			}
		}
		else if (!uri.equals(other.uri))
		{
			return false;
		}
		if (username == null)
		{
			if (other.username != null)
			{
				return false;
			}
		}
		else if (!username.equals(other.username))
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
		return new StringBuilder("[").append(id).append(',').append(email).append(',').append(digest).append(',')
				.append(username).append(',').append(name).append(',').append(uri).append(',').append(logHistory)
				.append(',').append(created).append(',').append(updated).append(']').toString();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.StorableHBase#getRow()
	 */
	public byte[] getRow()
	{
		// TODO implement sometime
		return null;
	}
}
