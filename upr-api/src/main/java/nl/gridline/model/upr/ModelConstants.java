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

import java.util.Map;
import java.util.NavigableMap;

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

	public static final byte[] DIGEST = Bytes.toBytes("password_digest");
	public static final byte[] USERNAME = Bytes.toBytes("username");
	public static final byte[] NAME = Bytes.toBytes("name");
	public static final byte[] URI = Bytes.toBytes("uri");
	public static final byte[] URL = Bytes.toBytes("url");
	public static final byte[] ID = Bytes.toBytes("id");
	public static final byte[] USER_ID = Bytes.toBytes("user_id");
	public static final byte[] EMAIL = Bytes.toBytes("email");
	public static final byte[] RECOMMENDER = Bytes.toBytes("recommender");
	public static final byte[] APIKEY = Bytes.toBytes("apikey");
	public static final byte[] LOG_HISTORY = Bytes.toBytes("log_history");
	public static final byte[] UPDATED_AT = Bytes.toBytes("updated_at");
	public static final byte[] CREATED_AT = Bytes.toBytes("created_at");
	public static final byte[] ADMIN = Bytes.toBytes("admin");
	public static final byte[] STATUS = Bytes.toBytes("status");
	public static final byte[] CONFIRMATION = Bytes.toBytes("confirmation");
	public static final byte[] CP = Bytes.toBytes("cp");

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

	static final Boolean getBoolean(byte[] value, Boolean defValue)
	{
		if (value == null)
		{
			return defValue;
		}
		return Bytes.toBoolean(value);
	}

	static final Map<byte[], byte[]> putBoolean(Map<byte[], byte[]> map, byte[] key, Boolean value)
	{
		if (value != null)
		{
			map.put(key, Bytes.toBytes(value.booleanValue()));
		}
		return map;
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
	 * add a String the to map by the given key
	 * @param map
	 * @param key
	 * @param value
	 * @return the map
	 */
	static final Map<byte[], byte[]> putString(Map<byte[], byte[]> map, byte[] key, String value)
	{
		// write empty strings too,
		if (value != null) // && !value.isEmpty())
		{
			map.put(key, Bytes.toBytes(value));
		}
		return map;
	}

	/**
	 * get the user name
	 * @param map
	 * @return
	 */
	static final String getUsername(Map<byte[], byte[]> map)
	{
		return getString(map.get(USERNAME), null);
	}

	/**
	 * set the user name
	 * @param map
	 * @param username
	 * @return
	 */
	static final Map<byte[], byte[]> putUsername(Map<byte[], byte[]> map, String username)
	{
		return putString(map, USERNAME, username);
	}

	/**
	 * @param map
	 * @return
	 */
	static final String getDigest(Map<byte[], byte[]> map)
	{
		return getString(map.get(DIGEST), null);
	}

	/**
	 * @param map
	 * @param digest
	 * @return
	 */
	static final Map<byte[], byte[]> putDigest(Map<byte[], byte[]> map, String digest)
	{
		return putString(map, DIGEST, digest);
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

	static final long getCreatedAt(Map<byte[], byte[]> map)
	{
		return getLong(map.get(CREATED_AT), 0L);
	}

	static final Map<byte[], byte[]> putCreatedAt(Map<byte[], byte[]> map, long created)
	{
		return putLong(map, CREATED_AT, created);
	}

	static final long getUpdatedAt(Map<byte[], byte[]> map)
	{
		return getLong(map.get(UPDATED_AT), 0L);
	}

	static final Map<byte[], byte[]> putUpdatedAt(Map<byte[], byte[]> map, long updated)
	{
		return putLong(map, UPDATED_AT, updated);
	}

	static final long getId(Map<byte[], byte[]> map)
	{
		return getLong(map.get(ID), 0L);
	}

	static final Map<byte[], byte[]> putId(Map<byte[], byte[]> map, long createdAt)
	{
		return putLong(map, ID, createdAt);
	}

	/**
	 * @param map
	 * @return
	 */
	static String getName(NavigableMap<byte[], byte[]> map)
	{
		return getString(map.get(NAME), null);
	}

	static Map<byte[], byte[]> putName(Map<byte[], byte[]> map, String name)
	{
		return putString(map, NAME, name);
	}

	/**
	 * A naive JSON writer
	 * @return
	 */
	static final String toJSON(Object obj)
	{
		return new GsonBuilder().create().toJson(obj);
	}

	/**
	 * @param map
	 * @param userId
	 */
	static Map<byte[], byte[]> putUserId(NavigableMap<byte[], byte[]> map, Long userId)
	{
		return putLong(map, USER_ID, userId);
	}

	/**
	 * @param map
	 * @return
	 */
	static Long getUserId(NavigableMap<byte[], byte[]> map)
	{
		return getLong(map.get(USER_ID), null);
	}

	/**
	 * @param map
	 * @param url
	 */
	static Map<byte[], byte[]> putUrl(NavigableMap<byte[], byte[]> map, String url)
	{
		return putString(map, URL, url);
	}

	/**
	 * @param map
	 * @return
	 */
	static String getUrl(NavigableMap<byte[], byte[]> map)
	{
		return getString(map.get(URL), null);
	}

	/**
	 * @param map
	 * @param cp
	 */
	static Map<byte[], byte[]> putRecommender(NavigableMap<byte[], byte[]> map, String cp)
	{
		return putString(map, RECOMMENDER, cp);
	}

	/**
	 * @param map
	 * @return
	 */
	static String getRecommender(NavigableMap<byte[], byte[]> map)
	{
		return getString(map.get(RECOMMENDER), null);
	}

	/**
	 * @param map
	 * @param logHistory
	 */
	static Map<byte[], byte[]> putLogHistory(NavigableMap<byte[], byte[]> map, Boolean logHistory)
	{
		return putBoolean(map, LOG_HISTORY, logHistory);

	}

	/**
	 * @param map
	 * @return
	 */
	static Boolean getLogHistory(NavigableMap<byte[], byte[]> map)
	{
		return getBoolean(map.get(LOG_HISTORY), null);
	}

	/**
	 * @param map
	 * @return
	 */
	protected static String getApiKey(NavigableMap<byte[], byte[]> map)
	{
		return getString(map.get(APIKEY), null);
	}

	/**
	 * @param map
	 * @param apikey
	 */
	protected static Map<byte[], byte[]> putApiKey(NavigableMap<byte[], byte[]> map, String apikey)
	{
		return putString(map, APIKEY, apikey);
	}

	protected static Map<byte[], byte[]> putAdmin(NavigableMap<byte[], byte[]> map, Boolean admin)
	{
		return putBoolean(map, ADMIN, admin);
	}

	/**
	 * @param map
	 * @return
	 */
	protected static Boolean getAdmin(NavigableMap<byte[], byte[]> map)
	{
		return getBoolean(map.get(ADMIN), null);
	}

	/**
	 * @param map
	 * @param status
	 */
	protected static Map<byte[], byte[]> putStatus(NavigableMap<byte[], byte[]> map, String status)
	{
		return putString(map, STATUS, status);
	}

	/**
	 * @param map
	 * @return
	 */
	protected static String getStatus(NavigableMap<byte[], byte[]> map)
	{
		return getString(map.get(STATUS), null);
	}

	/**
	 * @param map
	 * @return
	 */
	protected static String getConfirmation(NavigableMap<byte[], byte[]> map)
	{
		return getString(map.get(CONFIRMATION), null);
	}

	/**
	 * @param map
	 * @param confirmation
	 */
	public static Map<byte[], byte[]> putConfirmation(NavigableMap<byte[], byte[]> map, String confirmation)
	{
		return putString(map, CONFIRMATION, confirmation);
	}

	/**
	 * @param map
	 * @return
	 */
	static String getCp(Map<byte[], byte[]> map)
	{
		return getString(map.get(CP), null);
	}

	/**
	 * put CP in the map
	 * @param map
	 * @param cp
	 * @return
	 */
	static Map<byte[], byte[]> putCp(Map<byte[], byte[]> map, String cp)
	{
		return putString(map, CP, cp);
	}
}
