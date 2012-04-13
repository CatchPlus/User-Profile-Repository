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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import nl.gridline.model.upr.ModelConstants;
import nl.gridline.model.upr.Visit;
import nl.gridline.upr.model.HBaseEventTable;
import nl.gridline.zieook.mapreduce.RowKeys;

import org.apache.hadoop.hbase.KeyValue;
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
 * EventTable.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class EventTable
{
	private static final Logger LOG = LoggerFactory.getLogger(EventTable.class);

	public static final byte[] VISITED_COLUMN = Bytes.toBytes(HBaseEventTable.VISITED_COLUMN);

	private final HBaseEventTable table;

	public EventTable(HBaseEventTable table)
	{
		this.table = table;
	}

	// create, update:
	public void putVisit(Visit visited)
	{
		Put put = new Put(RowKeys.getVisitEvent(visited.getUserId(), visited.getRecommender()));
		NavigableMap<byte[], byte[]> data = visited.toMap();
		for (Map.Entry<byte[], byte[]> entry : data.entrySet())
		{
			put.add(VISITED_COLUMN, entry.getKey(), entry.getValue());
		}
		table.put(put);
	}

	// read
	public Visit getVisited(long userId, String recommender)
	{
		Get get = new Get(RowKeys.getVisitEvent(userId, recommender)).addFamily(VISITED_COLUMN);
		Result result = table.get(get);
		if (!result.isEmpty())
		{
			return new Visit(result.getFamilyMap(VISITED_COLUMN));
		}
		return null;
	}

	public boolean isVisit(long userId, String recommender)
	{
		return table.exists(new Get(RowKeys.getVisitEvent(userId, recommender)).addFamily(VISITED_COLUMN));
	}

	public List<Visit> getVisited(long userId, String startRecommender, int size)
	{
		LOG.info("getting visited for {} - starting at {}", userId, startRecommender);
		byte[] startrow = null;
		if (startRecommender != null)
		{
			startrow = RowKeys.getVisitEvent(userId, startRecommender);
		}
		else
		{
			startrow = RowKeys.getVisitEvent(userId);
		}

		RowFilter filter = new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getVisitEvent(userId)));

		Scan scan = new Scan(startrow).addFamily(VISITED_COLUMN).setFilter(filter);

		List<Visit> result = readData(scan, size);

		LOG.info("found: {}", result.size());

		// sort by (reverse) date:
		Collections.sort(result, new Comparator<Visit>()
		{
			@Override
			public int compare(Visit o1, Visit o2)
			{
				if (o1 == null || o2 == null)
				{
					throw new NullPointerException("cannot sort null objects");
				}
				return -o1.getUpdated().compareTo(o2.getUpdated());
			}
		});

		return result;
	}

	/**
	 * delete all data of the given user
	 * @param userId
	 */
	public void deleteVisit(long userId)
	{
		RowFilter filter = new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getVisitEvent(userId)));
		deleteData(new Scan().addFamily(VISITED_COLUMN).setFilter(filter));
	}

	/**
	 * delete all data of the given recommender
	 * @param recommender
	 */
	public void deleteVisit(String recommender)
	{
		FilterList filters = new FilterList();

		RowFilter rowFilter = new RowFilter(CompareOp.EQUAL, new BinaryPrefixComparator(RowKeys.getVisitEvent()));
		filters.addFilter(rowFilter);
		SingleColumnValueFilter filter = new SingleColumnValueFilter(VISITED_COLUMN, ModelConstants.RECOMMENDER,
				CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(recommender)));
		filter.setFilterIfMissing(true);
		filters.addFilter(filter);

		deleteData(new Scan().addFamily(VISITED_COLUMN).setFilter(filters));
	}

	public void deleteVisit(String cp, long userId)
	{
		Delete delete = new Delete(RowKeys.getVisitEvent(userId, cp)).deleteFamily(VISITED_COLUMN);
		table.delete(delete);
	}

	private List<Visit> readData(Scan scan, int size)
	{
		List<Visit> result = new ArrayList<Visit>();
		int count = 0;
		try
		{
			HTableInterface tableInterface = table.getTable();
			ResultScanner scanner = tableInterface.getScanner(scan);
			try
			{
				for (Result rr = scanner.next(); rr != null; rr = scanner.next())
				{
					LOG.debug("adding data: {}", Bytes.toStringBinary(rr.getRow()));
					result.add(new Visit(rr.getFamilyMap(VISITED_COLUMN)));
					count++;
					if (count >= size)
					{
						// done, for now
						break;
					}
				}
			}
			finally
			{
				scanner.close();
			}
		}
		catch (IOException e)
		{
			LOG.error("failed to SCAN table '" + table.getTableName() + "' for events", e);
		}
		return result;
	}

	private void deleteData(Scan scan)
	{
		List<Delete> result = new ArrayList<Delete>();
		long count = 0;
		try
		{
			HTableInterface tableInterface = table.getTable();

			ResultScanner scanner = tableInterface.getScanner(scan);
			try
			{
				// get the rows from the table:
				for (Result rr = scanner.next(); rr != null; rr = scanner.next())
				{
					KeyValue kv = rr.getColumnLatest(VISITED_COLUMN, ModelConstants.RECOMMENDER);
					if (kv != null)
					{
						result.add(new Delete(rr.getRow()));
					}
					else
					{
						LOG.error("This is a bug: some items are left behind, while cleaning: {}", rr);
					}

					// delete every thousand rows:
					if (result.size() > 0 && result.size() % 1000 == 0)
					{
						count += result.size();
						table.delete(result);
						result.clear();
						LOG.info("intermediate delete <{}> total now <{}>", result.size(), count);
					}
				}
			}
			finally
			{
				scanner.close();
				table.putTable(tableInterface);
			}
		}
		catch (IOException e)
		{
			LOG.error("failed to SCAN table '" + table.getTableName() + "' for events", e);
		}

		// flush all that's left
		count += result.size();
		table.delete(result);
		result.clear();

		LOG.info("Deleted visited {} log data", count);
	}
}
