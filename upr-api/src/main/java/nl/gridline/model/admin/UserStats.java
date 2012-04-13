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

package nl.gridline.model.admin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * UserStats.java created Sep 30, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "user-stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserStats implements Serializable
{

	@XmlElement(name = "user-count")
	private long userCount;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1712929784027778185L;

	public UserStats()
	{

	}

	public UserStats(long count)
	{
		userCount = count;
	}

	/**
	 * @return The userCount.
	 */
	public long getUserCount()
	{
		return userCount;
	}

	/**
	 * @param userCount The userCount to set.
	 */
	public void setUserCount(long userCount)
	{
		this.userCount = userCount;
	}

}
