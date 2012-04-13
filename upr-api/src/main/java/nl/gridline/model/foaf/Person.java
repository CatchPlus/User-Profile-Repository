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

import java.util.List;
import java.util.NavigableMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import nl.gridline.zieook.api.Updatable;

/**
 * Person <- is an instance of Agent, the agent superclass containst the unique id.
 * <p />
 * Project upr-api<br />
 * User.java created Aug 31, 2011
 * <p />
 * Copyright, all rights reserved 2011 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person extends Agent implements Updatable<Person>
{
	// TODO: implement 'knows' (only in case we use it)
	// TODO: implement 'img' -> the (user) image, should be a link to

	/**
	 * 
	 */
	private static final long serialVersionUID = 5618784988557075109L;

	@XmlElement(name = "homepage")
	private String homepage;
	@XmlElement(name = "img")
	private Image img;
	@XmlElement(name = "familyName")
	private String familyName;
	@XmlElement(name = "firstName")
	private String firstName;
	@XmlElement(name = "gender")
	private String gender;
	@XmlElement(name = "email")
	private String email;

	@XmlElement(name = "birthday")
	private Long birthday;

	@XmlElement(name = "city")
	private String city;

	@XmlElement(name = "country")
	private String country;

	// foaf:knows - the basic version, not sure (yet is this will be used)
	List<Person> knows;

	public Person()
	{
		// no-arg constructor
	}

	public Person(String email)
	{
		this.email = email;
	}

	public Person(NavigableMap<byte[], byte[]> map)
	{
		super(map);
		homepage = ModelConstants.getHomePage(map);
		// img = ModelConstants.getImage(map);
		familyName = ModelConstants.getFamilyName(map);
		firstName = ModelConstants.getFirstName(map);
		gender = ModelConstants.getGender(map);
		email = ModelConstants.getEmail(map);
		city = ModelConstants.getCity(map);
		country = ModelConstants.getCountry(map);
		birthday = ModelConstants.getBirthDay(map);

	}

	@Override
	public Person update(Person updates)
	{
		if (updates == null)
		{
			return this;
		}

		super.update(updates);
		if (updates.homepage != null)
		{
			homepage = updates.homepage;
		}
		if (updates.familyName != null)
		{
			familyName = updates.familyName;
		}
		if (updates.firstName != null)
		{
			firstName = updates.firstName;
		}
		if (updates.gender != null)
		{
			gender = updates.gender;
		}
		if (updates.email != null)
		{
			email = updates.email;
		}
		if (updates.city != null)
		{
			city = updates.city;
		}
		if (updates.country != null)
		{
			country = updates.country;
		}
		if (updates.birthday != null)
		{
			birthday = updates.birthday;
		}

		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.model.foaf.Agent#toMap()
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap()
	{
		NavigableMap<byte[], byte[]> map = super.toMap();
		return toMap(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.model.foaf.Agent#toMap(java.util.NavigableMap)
	 */
	@Override
	public NavigableMap<byte[], byte[]> toMap(NavigableMap<byte[], byte[]> map)
	{
		super.toMap(map);
		ModelConstants.putHomePage(map, homepage);
		// ModelConstants.putImage(map, img);
		ModelConstants.putFamilyName(map, familyName);
		ModelConstants.putFirstName(map, firstName);
		ModelConstants.putGender(map, gender);
		ModelConstants.putEmail(map, email);
		ModelConstants.putCity(map, city);
		ModelConstants.putCountry(map, country);
		ModelConstants.putBirthDay(map, birthday);
		return map;
	}

	/**
	 * @return The homepage.
	 */
	public String getHomepage()
	{
		return homepage;
	}

	/**
	 * @param homepage The homepage to set.
	 */
	public void setHomepage(String homepage)
	{
		this.homepage = homepage;
	}

	/**
	 * @return The img.
	 */
	public Image getImg()
	{
		return img;
	}

	/**
	 * @param img The img to set.
	 */
	public void setImg(Image img)
	{
		this.img = img;
	}

	/**
	 * @return The familyName.
	 */
	public String getFamilyName()
	{
		return familyName;
	}

	/**
	 * @param familyName The familyName to set.
	 */
	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	/**
	 * @return The firstName.
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return The gender.
	 */
	public String getGender()
	{
		return gender;
	}

	/**
	 * @param gender The gender to set.
	 */
	public void setGender(String gender)
	{
		this.gender = gender;
	}

	/**
	 * @return The email.
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return The knows.
	 */
	public List<Person> getKnows()
	{
		return knows;
	}

	/**
	 * @param knows The knows to set.
	 */
	public void setKnows(List<Person> knows)
	{
		this.knows = knows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.model.foaf.Agent#toString()
	 */
	@Override
	public String toString()
	{
		return ModelConstants.toJSON(this);
	}

	/**
	 * @return The birthdate.
	 */
	public Long getBirthday()
	{
		return birthday;
	}

	/**
	 * @param birthday The birthdate to set.
	 */
	public void setBirthday(Long birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return The city.
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city The city to set.
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return The country.
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((familyName == null) ? 0 : familyName.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((homepage == null) ? 0 : homepage.hashCode());
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((knows == null) ? 0 : knows.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Person other = (Person) obj;
		if (birthday == null)
		{
			if (other.birthday != null)
			{
				return false;
			}
		}
		else if (!birthday.equals(other.birthday))
		{
			return false;
		}
		if (city == null)
		{
			if (other.city != null)
			{
				return false;
			}
		}
		else if (!city.equals(other.city))
		{
			return false;
		}
		if (country == null)
		{
			if (other.country != null)
			{
				return false;
			}
		}
		else if (!country.equals(other.country))
		{
			return false;
		}
		if (email == null)
		{
			if (other.email != null)
			{
				return false;
			}
		}
		else if (!email.equals(other.email))
		{
			return false;
		}
		if (familyName == null)
		{
			if (other.familyName != null)
			{
				return false;
			}
		}
		else if (!familyName.equals(other.familyName))
		{
			return false;
		}
		if (firstName == null)
		{
			if (other.firstName != null)
			{
				return false;
			}
		}
		else if (!firstName.equals(other.firstName))
		{
			return false;
		}
		if (gender == null)
		{
			if (other.gender != null)
			{
				return false;
			}
		}
		else if (!gender.equals(other.gender))
		{
			return false;
		}
		if (homepage == null)
		{
			if (other.homepage != null)
			{
				return false;
			}
		}
		else if (!homepage.equals(other.homepage))
		{
			return false;
		}
		if (img == null)
		{
			if (other.img != null)
			{
				return false;
			}
		}
		else if (!img.equals(other.img))
		{
			return false;
		}
		if (knows == null)
		{
			if (other.knows != null)
			{
				return false;
			}
		}
		else if (!knows.equals(other.knows))
		{
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.gridline.zieook.api.StorableHBase#getRow()
	 */
	@Override
	public byte[] getRow()
	{
		// TODO implement sometime
		return null;
	}
}
