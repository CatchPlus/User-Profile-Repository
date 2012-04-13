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

import java.util.Date;
import java.util.NavigableMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.gridline.zieook.api.Updatable;

/**
 * Tracks what url's a user has visited in the UPR, specifically it tracks the URL's of collection items.
 * <p />
 * Project upr-api<br />
 * Visited.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "visited")
@XmlAccessorType(XmlAccessType.FIELD)
public class Visit extends EventLog implements Updatable<Visit>
{

	@XmlElement(name = "recommender")
	private String recommender;

	@XmlElement(name = "recommender-apikey")
	private String apikey;

	@XmlElement(name = "url")
	private String url;

	@XmlElement(name = "user_id")
	private Long userId;

	@XmlElement(name = "content_provider")
	private String cp;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3593628095422562232L;

	public Visit()
	{

	}

	/**
	 * @param cp
	 * @param url
	 * @param userId
	 */
	public Visit(String recommender, String url, String cp, Long userId)
	{
		this.userId = userId;
		this.cp = cp;
		this.recommender = recommender;
		this.url = url;
	}

	public Visit(String recommender, String url, String cp, Long userId, Date created, Date updated)
	{
		super(created, updated);
		this.userId = userId;
		this.cp = cp;
		this.recommender = recommender;
		this.url = url;
	}

	public Visit(NavigableMap<byte[], byte[]> map)
	{
		super(map);
		userId = ModelConstants.getUserId(map);
		url = ModelConstants.getUrl(map);
		recommender = ModelConstants.getRecommender(map);
		apikey = ModelConstants.getApiKey(map);
		cp = ModelConstants.getCp(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.model.upr.EventLog#toMap()
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap()
	{
		NavigableMap<byte[], byte[]> map = super.toMap();
		return toMap(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.model.upr.EventLog#toMap(java.util.NavigableMap)
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap(NavigableMap<byte[], byte[]> map)
	{
		super.toMap(map);

		ModelConstants.putUserId(map, userId);
		ModelConstants.putUrl(map, url);
		ModelConstants.putRecommender(map, recommender);
		ModelConstants.putApiKey(map, apikey);
		ModelConstants.putCp(map, cp);

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.Updatable#update(java.lang.Object)
	 */
	@Override
	public Visit update(Visit updates)
	{
		if (updates == null)
		{
			return this;
		}
		super.update(updates);

		if (updates.apikey != null)
		{
			apikey = updates.apikey;
		}
		if (updates.url != null)
		{
			url = updates.url;
		}
		if (updates.recommender != null)
		{
			recommender = updates.recommender;
		}
		if (updates.userId != null)
		{
			userId = updates.userId;
		}
		if (updates.cp != null)
		{
			cp = updates.cp;
		}
		return this;
	}

	/**
	 * @return The recommender.
	 */
	public String getRecommender()
	{
		return recommender;
	}

	/**
	 * @param recommender The cp to set.
	 */
	public void setRecommender(String recommender)
	{
		this.recommender = recommender;
	}

	/**
	 * @return The url.
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return The userId.
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * @return The apikey.
	 */
	public String getApikey()
	{
		return apikey;
	}

	/**
	 * @param apikey The apikey to set.
	 */
	public void setApikey(String apikey)
	{
		this.apikey = apikey;
	}

	public String getCp()
	{
		return cp;
	}

	public void setCp(String cp)
	{
		this.cp = cp;
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
		result = prime * result + ((apikey == null) ? 0 : apikey.hashCode());
		result = prime * result + ((cp == null) ? 0 : cp.hashCode());
		result = prime * result + ((recommender == null) ? 0 : recommender.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Visit other = (Visit) obj;
		if (apikey == null)
		{
			if (other.apikey != null)
			{
				return false;
			}
		}
		else if (!apikey.equals(other.apikey))
		{
			return false;
		}
		if (cp == null)
		{
			if (other.cp != null)
			{
				return false;
			}
		}
		else if (!cp.equals(other.cp))
		{
			return false;
		}
		if (recommender == null)
		{
			if (other.recommender != null)
			{
				return false;
			}
		}
		else if (!recommender.equals(other.recommender))
		{
			return false;
		}
		if (url == null)
		{
			if (other.url != null)
			{
				return false;
			}
		}
		else if (!url.equals(other.url))
		{
			return false;
		}
		if (userId == null)
		{
			if (other.userId != null)
			{
				return false;
			}
		}
		else if (!userId.equals(other.userId))
		{
			return false;
		}
		return true;
	}

	// naam van instelling
	// URL waarop actie is uitgevoerd
	// Timestamp van actie

}
