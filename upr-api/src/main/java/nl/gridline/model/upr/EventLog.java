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
import java.util.Date;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import nl.gridline.zieook.api.JSonParent;
import nl.gridline.zieook.api.StorableHBase;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * EventLog.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EventLog implements Serializable, JSonParent, StorableHBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6050028207181431829L;

	@XmlElement(name = "created_at", required = false, nillable = true)
	private Date created;

	@XmlElement(name = "updated_at", required = false, nillable = true)
	private Date updated;

	public EventLog()
	{
		// no-arg constructor
	}

	public EventLog(Date createdAt, Date updatedAt)
	{
		created = createdAt;
		updated = updatedAt;
	}

	public EventLog(NavigableMap<byte[], byte[]> map)
	{
		Long cr = ModelConstants.getCreatedAt(map);
		if (cr != null)
		{
			created = new Date(cr.longValue());
		}

		Long up = ModelConstants.getUpdatedAt(map);
		if (up != null)
		{
			updated = new Date(up.longValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.StorableHBase#toMap()
	 */
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
		if (created != null)
		{
			ModelConstants.putCreatedAt(map, created.getTime());
		}
		if (updated != null)
		{
			ModelConstants.putUpdatedAt(map, updated.getTime());
		}
		return map;
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

	public EventLog update(EventLog updates)
	{
		if (updates == null)
		{
			return this;
		}

		if (updates.created != null)
		{
			created = updates.created;
		}
		if (updates.updated != null)
		{
			updated = updates.updated;
		}
		return this;
	}

	/**
	 * @return The created.
	 */
	public Date getCreated()
	{
		return created;
	}

	/**
	 * @param created The created to set.
	 */
	public void setCreated(Date created)
	{
		this.created = created;
	}

	/**
	 * @return The updated.
	 */
	public Date getUpdated()
	{
		return updated;
	}

	/**
	 * @param updated The updated to set.
	 */
	public void setUpdated(Date updated)
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
		EventLog other = (EventLog) obj;
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
	 * @see nl.gridline.zieook.api.StorableHBase#getRow()
	 */
	public byte[] getRow()
	{
		// TODO implement sometime
		return null;
	}
}
