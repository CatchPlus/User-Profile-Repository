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
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.gridline.zieook.api.JSonParent;
import nl.gridline.zieook.api.StorableHBase;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * Agent.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Agent implements Serializable, JSonParent, StorableHBase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6140924529651058693L;

	@XmlElement(name = "openid")
	private String openid;

	@XmlElement(name = "created_at")
	private Long created;

	@XmlElement(name = "updated_at")
	private Long updated;

	public Agent()
	{

	}

	public Agent(NavigableMap<byte[], byte[]> map)
	{
		openid = ModelConstants.getOpenId(map);
		created = ModelConstants.getCreatedAt(map);
		updated = ModelConstants.getUpdatedAt(map);
	}

	@Override
	public NavigableMap<byte[], byte[]> toMap()
	{
		return toMap(new TreeMap<byte[], byte[]>(Bytes.BYTES_COMPARATOR));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.StorableHBase#toMap(java.util.NavigableMap)
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap(NavigableMap<byte[], byte[]> map)
	{
		ModelConstants.putOpenId(map, openid);
		ModelConstants.putCreatedAt(map, created);
		ModelConstants.putUpdatedAt(map, updated);

		return map;
	}

	public Agent update(Agent updates)
	{
		if (updates.openid != null)
		{
			openid = updates.openid;
		}
		if (updates.updated != null)
		{
			updated = updates.updated;
		}
		return this;
	}

	/**
	 * @return The openid.
	 */
	public String getOpenid()
	{
		return openid;
	}

	/**
	 * @param openid The openid to set.
	 */
	public void setOpenid(String openid)
	{
		this.openid = openid;
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
		result = prime * result + ((openid == null) ? 0 : openid.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		Agent other = (Agent) obj;
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
		if (openid == null)
		{
			if (other.openid != null)
			{
				return false;
			}
		}
		else if (!openid.equals(other.openid))
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
		return true;
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

	public abstract byte[] getRow();

}
