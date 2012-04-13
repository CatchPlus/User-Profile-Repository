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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * Document.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
public class Document extends Agent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4536938434004334796L;

	// TODO needs some values, it should point to a url of some sort, that locates the item.
	// TODO possible this will only be a asset placeholder.

	@XmlElement(name = "location")
	private String location;

	public Document()
	{

	}

	/**
	 * @return The location.
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.model.foaf.Agent#toString()
	 */
	@Override
	public String toString()
	{
		return ModelConstants.toJSON(this);

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
		int result = super.hashCode();
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		if (!super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Document other = (Document) obj;
		if (location == null)
		{
			if (other.location != null)
			{
				return false;
			}
		}
		else if (!location.equals(other.location))
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
	@Override
	public byte[] getRow()
	{
		// TODO implement sometime
		return null;
	}
}
