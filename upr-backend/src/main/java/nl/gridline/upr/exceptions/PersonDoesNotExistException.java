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
package nl.gridline.upr.exceptions;

import nl.gridline.zieook.exceptions.DoesNotExists;

/**
 * [purpose]
 * <p />
 * Project upr-backend<br />
 * PersonDoesNotExistException.java created 9 jan. 2012
 * <p />
 * Copyright, all rights reserved 2012 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class PersonDoesNotExistException extends DoesNotExists
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7438845278554612139L;

	public PersonDoesNotExistException()
	{
		super();
	}

	public PersonDoesNotExistException(String message, Throwable t)
	{
		super(message, t);
	}

	public PersonDoesNotExistException(String message)
	{
		super(message);
	}

	public PersonDoesNotExistException(String fmt, Object a, Object b)
	{
		super(fmt, a, b);
	}

}
