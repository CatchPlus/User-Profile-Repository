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

package nl.gridline.upr.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import nl.gridline.model.upr.ModelConstants;
import nl.gridline.model.upr.User;
import nl.gridline.upr.model.HBasePersonTable;
import nl.gridline.zieook.mapreduce.RowKeys;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [purpose]
 * <p />
 * Project upr-data<br />
 * UserTable.java created Sep 27, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class UserTable
{
	private static final Logger LOG = LoggerFactory.getLogger(UserTable.class);

	public static final byte[] USER_COLUMN = Bytes.toBytes(HBasePersonTable.USER_COLUMN);

	// we prefix the person row's with a location string - just in case we need to scale up in the future
	private static String LOCATION = "nl";

	private static byte[] INCREMENT_ROW = Bytes.toBytes("000_increment_row");
	private static byte[] USER_ID = Bytes.toBytes("user_id");

	private final HBasePersonTable table;

	public UserTable(HBasePersonTable table)
	{
		this.table = table;
	}

	/**
	 * Creates a new unique user id - HBase uses row locking so incrementing a global value should not be a problem
	 * <a href="http://whynosql.com/aggregation-with-hbase/">this</a> blog post was used
	 * @return a unique user id.
	 * @throws IOException
	 */
	public long getId() throws IOException
	{
		// create a unique user id
		final long result = table.getTable().incrementColumnValue(INCREMENT_ROW, USER_COLUMN, USER_ID, 1L);
		LOG.debug("generated new id: {}", result);
		return result;
	}

	/**
	 * @param start
	 * @param size
	 * @return
	 */
	public List<User> getUsers(String startEmail, long size) throws IOException
	{
		Scan scan = getUserScanner();
		scan.setFilter(new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getPersonKey(LOCATION))));
		if (startEmail != null)
		{
			scan.setStartRow(RowKeys.getPersonKey(LOCATION, startEmail));
		}
		HTableInterface tableInterface = table.getTable();
		ResultScanner scanner = tableInterface.getScanner(scan);
		List<User> result = new ArrayList<User>();
		try
		{
			Result rr = null;
			int count = 0;
			while ((rr = scanner.next()) != null)
			{
				result.add(new User(rr.getFamilyMap(USER_COLUMN)));
				count++;
				if (count == size)
				{
					break;
				}
			}
		}
		finally
		{
			scanner.close();
			table.putTable(tableInterface);
		}
		return result;
	}

	/**
	 * create a new user
	 * @param user
	 * @return
	 */
	public boolean createUser(User user)
	{
		LOG.debug("creating a user: <{}>", user.getEmail());

		final Put put = new Put(RowKeys.getPersonKey(LOCATION, user.getEmail()));
		final NavigableMap<byte[], byte[]> data = user.toMap();
		for (Map.Entry<byte[], byte[]> entry : data.entrySet())
		{
			put.add(USER_COLUMN, entry.getKey(), entry.getValue());
		}
		table.put(put);

		return true;
	}

	/**
	 * @param user
	 */
	public boolean update(User user)
	{
		// we assume a complete user:
		final Put put = new Put(RowKeys.getPersonKey(LOCATION, user.getEmail()));
		final NavigableMap<byte[], byte[]> data = user.toMap();
		for (Map.Entry<byte[], byte[]> entry : data.entrySet())
		{
			put.add(USER_COLUMN, entry.getKey(), entry.getValue());
		}
		table.put(put);

		return true;
	}

	/**
	 * get the user by id
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public User getUserById(long id) throws IOException
	{
		LOG.debug("find user by id: {}", id);
		Scan scan = getUserScanner();

		final FilterList filter = new FilterList();
		filter.addFilter(new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getPersonKey(LOCATION))));

		// filter on USERNAME
		final SingleColumnValueFilter usernameFilter = new SingleColumnValueFilter(USER_COLUMN, ModelConstants.ID,
				CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(id)));
		usernameFilter.setFilterIfMissing(true);

		filter.addFilter(usernameFilter);

		scan.setFilter(filter);
		// execute scan & return result;
		return findUser(scan);

	}

	public void deleteUser(String email)
	{
		LOG.debug("deleting user: <{}>", email);
		Delete delete = new Delete(RowKeys.getPersonKey(LOCATION, email)).deleteFamily(USER_COLUMN);
		table.delete(delete);
	}

	/**
	 * find the user based on it's username
	 * @param username
	 * @return
	 * @throws IOException
	 */
	public User findByEmail(String email)
	{
		Get get = new Get(RowKeys.getPersonKey(LOCATION, email)).addFamily(USER_COLUMN);

		Result result = table.get(get);
		if (!result.isEmpty())
		{
			return new User(result.getFamilyMap(USER_COLUMN));
		}
		return null;
	}

	/**
	 * @param email
	 * @return
	 */
	public boolean existsByEmail(String email)
	{
		Get get = new Get(RowKeys.getPersonKey(LOCATION, email));
		return table.exists(get);
	}

	/**
	 * @param email
	 * @return
	 * @throws IOException
	 */
	public User findByUsername(String username) throws IOException
	{
		LOG.debug("find user by username: {}", username);
		Scan scan = getUserScanner();

		final FilterList filter = new FilterList();
		filter.addFilter(new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getPersonKey(LOCATION))));

		// filter on USERNAME
		final SingleColumnValueFilter emailFilter = new SingleColumnValueFilter(USER_COLUMN, ModelConstants.USERNAME,
				CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(username)));
		emailFilter.setFilterIfMissing(true);

		filter.addFilter(emailFilter);

		scan.setFilter(filter);
		// execute scan & return result;
		return findUser(scan);
	}

	public User findByUri(String uri) throws IOException
	{
		LOG.debug("find user by uri: {}", uri);
		Scan scan = getUserScanner();

		final FilterList filter = new FilterList();
		filter.addFilter(new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getPersonKey(LOCATION))));

		// filter on USERNAME
		final SingleColumnValueFilter usernameFilter = new SingleColumnValueFilter(USER_COLUMN, ModelConstants.URI,
				CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(uri)));
		usernameFilter.setFilterIfMissing(true);

		filter.addFilter(usernameFilter);

		scan.setFilter(filter);
		// execute scan & return result;
		return findUser(scan);
	}

	/**
	 * Get a scanner with column family set to <tt>USER_COLMN</tt>
	 * @return
	 */
	private Scan getUserScanner()
	{
		final Scan result = new Scan();
		result.addFamily(USER_COLUMN);
		return result;
	}

	/**
	 * Find a single user, using a given scan object. This will only work on the <tt>USER_COLUMN</tt>
	 * @param scan
	 * @return
	 * @throws IOException
	 */
	private User findUser(Scan scan) throws IOException
	{
		HTableInterface tableInterface = table.getTable();
		ResultScanner scanner = tableInterface.getScanner(scan);
		try
		{
			final Result rr = scanner.next();
			if (rr != null)
			{
				return new User(rr.getFamilyMap(USER_COLUMN));
			}
		}
		finally
		{
			scanner.close();
			table.putTable(tableInterface);
		}

		return null;
	}

}
