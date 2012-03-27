/*
 *  Copyright (C) 2011 Axel Morgner, structr <structr@structr.org>
 *
 *  This file is part of structr <http://structr.org>.
 *
 *  structr is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  structr is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with structr.  If not, see <http://www.gnu.org/licenses/>.
 */



package org.structr.common;

import org.structr.core.GraphObject;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.structr.core.entity.AbstractNode;

//~--- classes ----------------------------------------------------------------

/**
 *
 * @author axel
 */
public class GraphObjectComparator implements Comparator<GraphObject> {

	public static final String ASCENDING  = "asc";
	public static final String DESCENDING = "desc";
	private static final Logger logger    = Logger.getLogger(GraphObjectComparator.class.getName());

	//~--- fields ---------------------------------------------------------

	private String sortKey;
	private String sortOrder;

	//~--- constructors ---------------------------------------------------

	// public GraphObjectComparator() {};
	public GraphObjectComparator(final String sortKey, final String sortOrder) {

		this.sortKey   = sortKey;
		this.sortOrder = sortOrder;
	}

	//~--- methods --------------------------------------------------------

	@Override
	public int compare(GraphObject n1, GraphObject n2) {

		if(n1 == null || n2 == null) {
			logger.log(Level.WARNING, "Cannot compare null objects!");
			return -1;
		}
		
		Comparable c1 = (Comparable) n1.getProperty(sortKey);
		Comparable c2 = (Comparable) n2.getProperty(sortKey);

		if(c1 == null || c2 == null) {
			
			try {
				logger.log(Level.WARNING, "Cannot compare {0} of type {1} to {2} of type {3}, sort key {4} not found.",
					new Object[] {
						n1.getStringProperty(AbstractNode.Key.uuid.name()),
						n1.getStringProperty(AbstractNode.Key.type.name()),
	//					n1.getClass().getSimpleName(),
						n2.getStringProperty(AbstractNode.Key.uuid.name()),
						n2.getStringProperty(AbstractNode.Key.type.name()),
						n2.getClass().getSimpleName(),
						sortKey
					});
				
			} catch(Throwable t) {
				logger.log(Level.SEVERE, "Error in comparator", t);
			}
			
			return -1;
		}
		
		if (DESCENDING.equals(sortOrder)) {

			return (c2.compareTo(c1));

		} else {

			return (c1.compareTo(c2));

		}
	}
}
