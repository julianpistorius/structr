/*
 *  Copyright (C) 2011 Axel Morgner
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



package org.structr.websocket.command;

import org.structr.common.SecurityContext;
import org.structr.core.EntityContext;
import org.structr.core.Services;
import org.structr.core.entity.AbstractNode;
import org.structr.core.entity.DirectedRelationship;
import org.structr.core.entity.Group;
import org.structr.core.entity.StructrRelationship;
import org.structr.core.node.StructrTransaction;
import org.structr.core.node.TransactionCommand;
import org.structr.websocket.message.MessageBuilder;
import org.structr.websocket.message.WebSocketMessage;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

//~--- classes ----------------------------------------------------------------

/**
 *
 * @author Christian Morgner
 */
public class RemoveCommand extends AbstractCommand {

	@Override
	public void processMessage(WebSocketMessage webSocketData) {

		final SecurityContext securityContext = SecurityContext.getSuperUserInstance();

		// create static relationship
		String sourceId = webSocketData.getId();
		String targetId = (String) webSocketData.getData().get("id");

		if ((sourceId != null) && (targetId != null)) {

			final AbstractNode sourceNode = getNode(sourceId);
			final AbstractNode targetNode = getNode(targetId);

			if ((sourceNode != null) && (targetNode != null)) {

				DirectedRelationship rel = EntityContext.getRelation(sourceNode.getClass(), targetNode.getClass(), true);

				if (rel != null) {

					final List<StructrRelationship> rels = sourceNode.getRelationships(rel.getRelType(), rel.getDirection());
					StructrTransaction transaction       = new StructrTransaction() {

						@Override
						public Object execute() throws Throwable {

							for (StructrRelationship rel : rels) {

								if (rel.getOtherNode(sourceNode).equals(targetNode)) {

									rel.delete(securityContext);

								}

							}

							return null;
						}
					};

					// execute transaction
					Services.command(securityContext, TransactionCommand.class).execute(transaction);

					// re-throw exception that may occur during transaction
					if (transaction.getCause() != null) {

						getWebSocket().send(MessageBuilder.status().code(400).message(transaction.getCause().getMessage()).build(), true);

					}

				}

			} else {

				getWebSocket().send(MessageBuilder.status().code(404).build(), true);

			}

		} else {

			getWebSocket().send(MessageBuilder.status().code(400).message("Add needs id and data.id!").build(), true);

		}
	}

	//~--- get methods ----------------------------------------------------

	@Override
	public String getCommand() {
		return "REMOVE";
	}
}