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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * Visits.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "visits")
@XmlAccessorType(XmlAccessType.FIELD)
public class Visits implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463994778137465572L;

	private List<Visit> visits;

	public Visits()
	{

	}

	public Visits(List<Visit> visits)
	{
		this.visits = visits;
	}

	/**
	 * @return The visits.
	 */
	public List<Visit> getVisits()
	{
		return visits;
	}

	/**
	 * @param visits The visits to set.
	 */
	public void setVisits(List<Visit> visits)
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
		result = prime * result + ((visits == null) ? 0 : visits.hashCode());
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
		Visits other = (Visits) obj;
		if (visits == null)
		{
			if (other.visits != null)
			{
				return false;
			}
		}
		else if (!visits.equals(other.visits))
		{
			return false;
		}
		return true;
	}

}
