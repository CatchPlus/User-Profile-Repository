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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * Group.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
public class Group extends Agent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5374109497917067485L;

	@XmlElement(name = "agent")
	private List<Agent> agents;

	public Group()
	{

	}

	/**
	 * @return The agents.
	 */
	public List<Agent> getAgents()
	{
		return agents;
	}

	/**
	 * @param agents The agents to set.
	 */
	public void setAgents(List<Agent> agents)
	{
		this.agents = agents;
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
		result = prime * result + ((agents == null) ? 0 : agents.hashCode());
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
		Group other = (Group) obj;
		if (agents == null)
		{
			if (other.agents != null)
			{
				return false;
			}
		}
		else if (!agents.equals(other.agents))
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
