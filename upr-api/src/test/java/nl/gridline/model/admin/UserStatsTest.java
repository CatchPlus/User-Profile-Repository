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

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * UserStatsTest.java created 14 okt. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class UserStatsTest
{

	private static final Logger LOG = LoggerFactory.getLogger(UserStatsTest.class);

	@Test
	public void marshallTest() throws JAXBException
	{
		UserStats obj = new UserStats(10);
		Writer w = new StringWriter();

		JAXBContext context = JAXBContext.newInstance(UserStats.class);

		context.createMarshaller().marshal(obj, w);

		LOG.info("{}", w);
		LOG.info(obj.toString());

		// read:
		UserStats readObj = (UserStats) context.createUnmarshaller().unmarshal(new StringReader(w.toString()));

		assertEquals(obj, readObj);
	}

}
