/**
 * Copyright (C) 2010-2014 Morgner UG (haftungsbeschränkt)
 *
 * This file is part of Structr <http://structr.org>.
 *
 * Structr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Structr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Structr.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.structr.core.entity;

import org.structr.common.Permission;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import org.structr.common.AccessControllable;
import org.structr.core.entity.relationship.PrincipalOwnsNode;
import org.structr.core.graph.NodeInterface;
import org.structr.core.property.BooleanProperty;
import org.structr.core.property.EndNodes;
import org.structr.core.property.PasswordProperty;
import org.structr.core.property.Property;
import org.structr.core.property.StringProperty;

//~--- interfaces -------------------------------------------------------------

/**
 *
 * @author Axel Morgner
 *
 */
public interface Principal extends NodeInterface, AccessControllable {

	public static final Property<String>	          sessionId  = new StringProperty("sessionId").indexedWhenEmpty();
	public static final Property<Boolean>	          blocked    = new BooleanProperty("blocked");
	public static final Property<String>	          password   = new PasswordProperty("password");
	public static final Property<String>	          salt       = new StringProperty("salt");
	public static final Property<Boolean>	          isAdmin    = new BooleanProperty("isAdmin").readOnly();
	public static final Property<List<NodeInterface>> ownedNodes = new EndNodes<>("ownedNodes", PrincipalOwnsNode.class);
	
	public void grant(final Permission permission, final AbstractNode obj);

	public void revoke(final Permission permission, final AbstractNode obj);

	public List<Principal> getParents();

	public String getEncryptedPassword();

}
