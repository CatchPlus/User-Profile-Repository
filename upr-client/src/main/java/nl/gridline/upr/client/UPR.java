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

package nl.gridline.upr.client;

import nl.gridline.zieook.configuration.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-client<br />
 * UPR.java created 3 okt. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class UPR
{
	static
	{
		// set project name to upr
		System.setProperty("gridline.project.name", "upr");
	}

	private static final Logger LOG = LoggerFactory.getLogger(UPR.class);
	private final Config config;

	public UPR()
	{
		config = Config.getInstance();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		UPR tools = new UPR();

		String process = args[0];
		String[] options = new String[args.length - 1];
		System.arraycopy(args, 1, options, 0, args.length - 1);

		if ("hbase".equals(process))
		{
			// do stuff directly on hbase
			System.out.println("hbase tools");
		}
		else if ("upr".equals(process))
		{
			// do admin stuff with upr
			System.out.println("upr tools");
		}

	}

}
