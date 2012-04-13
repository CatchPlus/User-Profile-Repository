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

import nl.gridline.model.foaf.Person;
import nl.gridline.upr.model.HBasePersonTable;
import nl.gridline.zieook.mapreduce.RowKeys;

import org.apache.commons.lang.NotImplementedException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * <p />
 * Project upr-data<br />
 * PersonTable.java created 5 sep. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class PersonTable
{
	public static final byte[] PERSON_COLUMN = Bytes.toBytes(HBasePersonTable.PERSON_COLUMN);

	// we prefix the person row's with a location string - just in case we need to scale up in the future
	private static String LOCATION = "nl";

	private final HBasePersonTable table;

	public PersonTable(HBasePersonTable table)
	{
		this.table = table;
	}

	public boolean createPerson(Person person)
	{
		final Put put = new Put(RowKeys.getPersonKey(LOCATION, person.getEmail()));
		final NavigableMap<byte[], byte[]> data = person.toMap();
		for (Map.Entry<byte[], byte[]> entry : data.entrySet())
		{
			put.add(PERSON_COLUMN, entry.getKey(), entry.getValue());
		}
		table.put(put);

		return true;
	}

	public boolean updatePerson(String email, Person person)
	{
		final Put put = new Put(RowKeys.getPersonKey(LOCATION, email));
		final NavigableMap<byte[], byte[]> data = person.toMap();
		for (Map.Entry<byte[], byte[]> entry : data.entrySet())
		{
			put.add(PERSON_COLUMN, entry.getKey(), entry.getValue());
		}
		table.put(put);
		return true;
	}

	/**
	 * find the user based on it's username
	 * @param username
	 * @return
	 * @throws IOException
	 */
	public Person findByEmail(String email)
	{
		Get get = new Get(RowKeys.getPersonKey(LOCATION, email)).addFamily(PERSON_COLUMN);

		Result result = table.get(get);
		if (!result.isEmpty())
		{
			return new Person(result.getFamilyMap(PERSON_COLUMN));
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
	 */
	public void deletePerson(String email)
	{
		Delete get = new Delete(RowKeys.getPersonKey(LOCATION, email)).deleteFamily(PERSON_COLUMN);
		table.delete(get);
	}

	/**
	 * @param numId
	 * @return
	 */
	public Person getPersonById(long numId)
	{
		throw new NotImplementedException();
	}

	/**
	 * @param startEmail
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public List<Person> listPerons(String startEmail, int size) throws IOException
	{
		Scan scan = new Scan().addFamily(PERSON_COLUMN).setFilter(
				new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getPersonKey(LOCATION))));

		if (startEmail != null)
		{
			scan.setStartRow(RowKeys.getPersonKey(LOCATION, startEmail));
		}
		HTableInterface tableInterface = table.getTable();
		ResultScanner scanner = tableInterface.getScanner(scan);
		List<Person> result = new ArrayList<Person>();
		try
		{
			Result rr = null;
			int count = 0;
			while ((rr = scanner.next()) != null)
			{
				result.add(new Person(rr.getFamilyMap(PERSON_COLUMN)));
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
}
