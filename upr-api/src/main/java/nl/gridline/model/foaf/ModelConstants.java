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

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

import com.google.gson.GsonBuilder;

/**
 * An extensive set of translate methods that translate between Map<byte[],byte[]> (used in HBase) and readable elements
 * for the POJO's, xml, json versions of the objects
 * <p />
 * Project zieook-api-data<br />
 * MapUtils.java created 3 mrt. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision: 2226 $, $Date: 2011-09-02 17:24:13 +0200 (vr, 02 sep 2011) $
 */
/** 
 * [purpose]
 * <p />
 * Project upr-api<br /> 
 * ModelConstants.java created 5 sep. 2011
 * <p />
 *  Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author    <a href="mailto:job@gridline.nl">Job</a>  
 * @version   $Revision:$, $Date:$ 
 */
/**
 * [purpose]
 * <p />
 * Project upr-api<br />
 * ModelConstants.java created 5 sep. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public final class ModelConstants
{

	public static final byte[] URI = Bytes.toBytes("uri");
	public static final byte[] OPENID = Bytes.toBytes("openid");
	public static final byte[] HOMEPAGE = Bytes.toBytes("homepage");
	public static final byte[] IMG = Bytes.toBytes("img");
	public static final byte[] FAMILYNAME = Bytes.toBytes("familyName");
	public static final byte[] FIRSTNAME = Bytes.toBytes("firstName");
	public static final byte[] GENDER = Bytes.toBytes("gender");
	public static final byte[] BIRTHDATE = Bytes.toBytes("birth_date");
	public static final byte[] EMAIL = Bytes.toBytes("email");
	public static final byte[] CITY = Bytes.toBytes("city");
	public static final byte[] COUNTRY = Bytes.toBytes("country");
	public static final byte[] UPDATED_AT = Bytes.toBytes("updated_at");
	public static final byte[] CREATED_AT = Bytes.toBytes("created_at");
	public static final byte[] ID = Bytes.toBytes("id");

	public static final byte[] TIMESTAMP = Bytes.toBytes("timestamp");

	private ModelConstants()
	{
		// hide constructor
	}

	/**
	 * Get a long from the given byte[]
	 * @param value
	 * @param defValue
	 * @return
	 */
	static final Long getLong(byte[] value, Long defValue)
	{
		if (value == null)
		{
			return defValue;
		}
		try
		{
			return Bytes.toLong(value);
		}
		catch (IllegalArgumentException e)
		{
			return defValue;
		}
	}

	/**
	 * Get a double from the given byte[]
	 * @param value
	 * @param defValue
	 * @return
	 */
	static final double getDouble(byte[] value, double defValue)
	{
		if (value == null)
		{
			return defValue;
		}
		try
		{
			return Bytes.toDouble(value);
		}
		catch (IllegalArgumentException e)
		{
			return defValue;
		}
	}

	/**
	 * Get a String from the given byte[]
	 * @param value
	 * @param defValue
	 * @return
	 */
	static final String getString(byte[] value, String defValue)
	{
		if (value == null)
		{
			return defValue;
		}
		return Bytes.toString(value);

	}

	/**
	 * add a long to the map by the given key
	 * @param map
	 * @param key
	 * @param value
	 * @return the map
	 */
	static final Map<byte[], byte[]> putLong(Map<byte[], byte[]> map, byte[] key, long value)
	{
		map.put(key, Bytes.toBytes(value));
		return map;
	}

	/**
	 * add a long to the map by the given key
	 * @param map
	 * @param key
	 * @param value
	 * @return the map
	 */
	static final Map<byte[], byte[]> putLong(Map<byte[], byte[]> map, byte[] key, Long value)
	{
		if (value != null)
		{
			map.put(key, Bytes.toBytes(value));
		}
		return map;
	}

	/**
	 * add a String the to map by the given key
	 * @param map
	 * @param key
	 * @param value
	 * @return the map
	 */
	static final Map<byte[], byte[]> putString(Map<byte[], byte[]> map, byte[] key, String value)
	{
		if (value != null && !value.isEmpty())
		{
			map.put(key, Bytes.toBytes(value));
		}
		return map;
	}

	/**
	 * add a double to the map by the given key
	 * @param map
	 * @param key
	 * @param value
	 * @return the map
	 */
	static final Map<byte[], byte[]> putDouble(Map<byte[], byte[]> map, byte[] key, double value)
	{
		map.put(key, Bytes.toBytes(value));
		return map;
	}

	/**
	 * return the FIRSTNAME from the map
	 * @param map
	 * @return
	 */
	static final String getFirstName(Map<byte[], byte[]> map)
	{
		return getString(map.get(FIRSTNAME), null);
	}

	/**
	 * put a FIRSTNAME in the map
	 * @param map
	 * @param user
	 * @return
	 */
	static final Map<byte[], byte[]> putFirstName(Map<byte[], byte[]> map, String firstname)
	{
		return putString(map, FIRSTNAME, firstname);
	}

	/**
	 * return FAMILYNAME from the map
	 * @param map
	 * @return
	 */
	static final String getFamilyName(Map<byte[], byte[]> map)
	{
		return getString(map.get(FAMILYNAME), null);
	}

	/**
	 * put FAMILYNAME in the map
	 * @param map
	 * @param familyname
	 * @return
	 */
	static final Map<byte[], byte[]> putFamilyName(Map<byte[], byte[]> map, String familyname)
	{
		return putString(map, FAMILYNAME, familyname);
	}

	/**
	 * return URI from the map
	 * @param map
	 * @return
	 */
	static final String getURI(Map<byte[], byte[]> map)
	{
		return getString(map.get(URI), null);
	}

	/**
	 * put the URI in the map
	 * @param map
	 * @param uri
	 * @return
	 */
	static final Map<byte[], byte[]> putURI(Map<byte[], byte[]> map, String uri)
	{
		return putString(map, URI, uri);
	}

	/**
	 * get EMAIL from the map
	 * @param map
	 * @return
	 */
	static final String getEmail(Map<byte[], byte[]> map)
	{
		return getString(map.get(EMAIL), null);
	}

	/**
	 * put EMAIL in the map
	 * @param map
	 * @param email
	 * @return
	 */
	static final Map<byte[], byte[]> putEmail(Map<byte[], byte[]> map, String email)
	{
		return putString(map, EMAIL, email);
	}

	/**
	 * get the GENDER from the map
	 * @param map
	 * @return
	 */
	static final String getGender(Map<byte[], byte[]> map)
	{
		return getString(map.get(GENDER), null);
	}

	/**
	 * put the GENDER in the map
	 * @param map
	 * @param gender
	 * @return
	 */
	static final Map<byte[], byte[]> putGender(Map<byte[], byte[]> map, String gender)
	{
		return putString(map, GENDER, gender);
	}

	/**
	 * get the HOMEPAGE from the map
	 * @param map
	 * @return
	 */
	static final String getHomePage(Map<byte[], byte[]> map)
	{
		return getString(map.get(HOMEPAGE), null);
	}

	/**
	 * put the HOMEPAGE in the map
	 * @param map
	 * @param homepage
	 * @return
	 */
	static final Map<byte[], byte[]> putHomePage(Map<byte[], byte[]> map, String homepage)
	{
		return putString(map, HOMEPAGE, homepage);
	}

	/**
	 * get the OPENID from the tag
	 * @param map
	 * @return
	 */
	static final String getOpenId(Map<byte[], byte[]> map)
	{
		return getString(map.get(OPENID), null);
	}

	/**
	 * put the OPENID in the map
	 * @param map
	 * @param openid
	 * @return
	 */
	static final Map<byte[], byte[]> putOpenId(Map<byte[], byte[]> map, String openid)
	{
		return putString(map, OPENID, openid);
	}

	/**
	 * get an image from the map using the IMG tag
	 * @param map
	 * @return
	 */
	static final byte[] getImage(Map<byte[], byte[]> map)
	{
		return map.get(IMG);
	}

	/**
	 * put an image in the using the IMG tag, image can be any byte array
	 * @param map
	 * @param img
	 * @return
	 */
	static final Map<byte[], byte[]> putImage(Map<byte[], byte[]> map, byte[] img)
	{
		if (img != null && img.length > 0)
		{
			map.put(IMG, img);
		}
		return map;
	}

	/**
	 * get TIMESTAMP from the map
	 * @param map
	 * @return
	 */
	static final Long getStamp(Map<byte[], byte[]> map)
	{
		return getLong(map.get(TIMESTAMP), null);
	}

	/**
	 * put TIMESTEMP in the map
	 * @param map
	 * @param stamp
	 * @return
	 */
	static final Map<byte[], byte[]> putStamp(Map<byte[], byte[]> map, Long stamp)
	{
		if (stamp != null)
		{
			return putLong(map, TIMESTAMP, stamp);
		}
		return map;
	}

	static final Long getCreatedAt(Map<byte[], byte[]> map)
	{
		return getLong(map.get(CREATED_AT), null);
	}

	static final Map<byte[], byte[]> putCreatedAt(Map<byte[], byte[]> map, Long createdAt)
	{
		return putLong(map, CREATED_AT, createdAt);
	}

	static final Long getUpdatedAt(Map<byte[], byte[]> map)
	{
		return getLong(map.get(UPDATED_AT), null);
	}

	static final Map<byte[], byte[]> putUpdatedAt(Map<byte[], byte[]> map, Long createdAt)
	{
		return putLong(map, UPDATED_AT, createdAt);
	}

	static final Long getBirthDay(Map<byte[], byte[]> map)
	{
		return getLong(map.get(BIRTHDATE), null);
	}

	static final Map<byte[], byte[]> putBirthDay(Map<byte[], byte[]> map, Long birthdate)
	{
		return putLong(map, BIRTHDATE, birthdate);
	}

	static final String getId(Map<byte[], byte[]> map)
	{
		return getString(map.get(ID), null);
	}

	static final Map<byte[], byte[]> putId(Map<byte[], byte[]> map, String id)
	{
		return putString(map, ID, id);
	}

	static final String getCity(Map<byte[], byte[]> map)
	{
		return getString(map.get(CITY), null);
	}

	static final Map<byte[], byte[]> putCity(Map<byte[], byte[]> map, String city)
	{
		return putString(map, CITY, city);
	}

	static final String getCountry(Map<byte[], byte[]> map)
	{
		return getString(map.get(COUNTRY), null);
	}

	static final Map<byte[], byte[]> putCountry(Map<byte[], byte[]> map, String country)
	{
		return putString(map, COUNTRY, country);
	}

	/**
	 * A naive JSON writer
	 * @return
	 */
	static final String toJSON(Object obj)
	{
		return new GsonBuilder().create().toJson(obj);
	}
}
