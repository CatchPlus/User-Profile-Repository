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

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import nl.gridline.model.upr.User;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * PersonTest.java created 14 okt. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class PersonTest
{

	public static Person getPerson()
	{
		Person obj = new Person();
		obj.setEmail("upr@gridline.nl");
		obj.setFamilyName("familyname");
		obj.setFirstName("firstname");
		obj.setGender("f");
		obj.setHomepage("http://www.gridline.nl");
		obj.setImg(new Image());
		obj.setKnows(Arrays.asList(new Person(), new Person()));
		obj.setOpenid("openidurl");
		obj.setBirthday(new Date().getTime());
		obj.setCity("Amsterdam");
		obj.setCountry("Nederland");

		return obj;
	}

	public static User getUser()
	{
		User user = new User();
		user.setId(1L);
		// user.setEmail("upr@gridline.nl");
		// user.setDigest("asdfaksdfjkhasfkjhas234234");
		user.setUsername("username");
		// user.setName("name");
		// user.setUri("http://www.gridline.nl/~/user");
		// user.setLogHistory(false);
		user.setCreated(System.currentTimeMillis());
		user.setUpdated(System.currentTimeMillis());

		return user;
	}

	private static final Logger LOG = LoggerFactory.getLogger(PersonTest.class);

	@Test
	public void marshalTest() throws JAXBException
	{
		Person obj = getPerson();

		Writer w = new StringWriter();

		JAXBContext context = JAXBContext.newInstance(Person.class);

		context.createMarshaller().marshal(obj, w);

		// read:
		Person readObj = (Person) context.createUnmarshaller().unmarshal(new StringReader(w.toString()));

		LOG.info("old: {}", obj);
		LOG.info("new: {}", readObj);

		assertEquals(obj, readObj);
	}

	@Test
	public void marshalJSON() throws JAXBException
	{
		JAXBContext jc = JAXBContext.newInstance(Person.class);

		Configuration config = new Configuration();
		MappedNamespaceConvention con = new MappedNamespaceConvention(config);

		Writer writer = new StringWriter();
		XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.marshal(getPerson(), xmlStreamWriter);

		System.out.println(writer.toString());
	}

	@Test
	public void marshalUser() throws JAXBException
	{
		JAXBContext jc = JAXBContext.newInstance(User.class);

		Configuration config = new Configuration();
		MappedNamespaceConvention con = new MappedNamespaceConvention(config);
		con.getNamespaceURI("");

		Writer writer = new StringWriter();
		XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.marshal(getUser(), xmlStreamWriter);

		System.out.println(writer.toString());
	}

	@Test
	public void unMarshallPersonSimple() throws JAXBException, JSONException, XMLStreamException
	{
		JSONObject minimalPerson = new JSONObject(
				"{\"person\":{\"city\":\"Amstelveen\",\"country\":\"NL\",\"gender\":null,\"familyName\":null,\"firstName\":null,\"birthday\":null,\"homepage\":\"http://www.gridline.nl/\",\"email\":\"nielsslot@gmail.com\"}}");
		JAXBContext jc = JAXBContext.newInstance(Person.class);

		Configuration config = new Configuration();
		MappedNamespaceConvention con = new MappedNamespaceConvention(config);

		XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(minimalPerson, con);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Person person = (Person) unmarshaller.unmarshal(xmlStreamReader);

		System.out.println(person);
	}

	@Test
	public void unMarshalPerson() throws JAXBException, JSONException, XMLStreamException
	{

		JSONObject obj = new JSONObject(
				"{\"person\":{\"city\":\"Amstelveen\",\"created_at\":1326284839194,\"country\":\"NL\",\"updated_at\":\"2012-01-16T12:05:39+01:00\",\"gender\":null,\"familyName\":null,\"firstName\":null,\"birthday\":null,\"homepage\":\"http://www.gridline.nl/\",\"email\":\"nielsslot@gmail.com\"}}");

		// JSONObject obj = new JSONObject(
		// "{\"customer\":{\"first-name\":\"Jane\",\"last-name\":\"Doe\",\"address\":{\"street\":\"123 A Street\"},\"phone-number\":[{\"@type\":\"work\",\"$\":\"555-1111\"},{\"@type\":\"cell\",\"$\":\"555-2222\"}]}}");

		JAXBContext jc = JAXBContext.newInstance(Person.class);

		Configuration config = new Configuration();
		MappedNamespaceConvention con = new MappedNamespaceConvention(config);

		XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(obj, con);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Person person = (Person) unmarshaller.unmarshal(xmlStreamReader);

		System.out.println(person);

	}
}
