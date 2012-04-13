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

package nl.gridline.upr.model;

import java.util.List;

import nl.gridline.zieook.data.hbase.HBaseManager;
import nl.gridline.zieook.data.hbase.model.AbstractHBaseTable;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

/**
 * [purpose]
 * <p />
 * Project upr-data<br />
 * HBaseEventTable.java created 7 dec. 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class HBaseEventTable extends AbstractHBaseTable
{

	public static final String NAME = "UPR_eventlog";

	public static final String VISITED_COLUMN = "visited";

	/**
	 * @param manager
	 */
	public HBaseEventTable(HBaseManager manager)
	{
		super(manager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.data.hbase.TableOperations#getCurrentDescriptor(java.lang.String)
	 */
	@Override
	public HTableDescriptor getCurrentDescriptor(String name)
	{
		HTableDescriptor descriptor = new HTableDescriptor(getTableName());
		HColumnDescriptor visited = new HColumnDescriptor(VISITED_COLUMN);
		descriptor.addFamily(visited);
		return descriptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.data.hbase.TableOperations#getTableName(java.lang.String)
	 */
	@Override
	public String getTableName(String name)
	{
		return NAME;
	}

	public String getTableName()
	{
		return NAME;
	}

	public HTableInterface getTable()
	{
		return getTable(null);
	}

	public boolean create()
	{
		return create(null);
	}

	public boolean tableExists()
	{
		return super.tableExists(null);
	}

	public boolean drop()
	{
		return drop(null);
	}

	public Result get(Get get)
	{
		return get(null, get);
	}

	public void put(Put put)
	{
		put(null, put);
	}

	public boolean exists(Get get)
	{
		return exists(null, get);
	}

	public void delete(Delete delete)
	{
		delete(null, delete);
	}

	public void delete(List<Delete> deletes)
	{
		delete(null, deletes);
	}

}
