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
package org.structr.core.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.structr.common.SecurityContext;
import org.structr.common.error.FrameworkException;
import org.structr.core.auth.exception.AuthenticationException;
import org.structr.core.entity.Principal;

/**
 * An authenticator interface that defines how the system can obtain a principal
 * from a HttpServletRequest.
 *
 * @author Christian Morgner
 * @author Axel Morgner
 */
public interface Authenticator {
	
	/*
	 * Indicate that the authenticator has already examined the request
	 */
	public boolean hasExaminedRequest();

	/**
	 * Set a boolean flag to indicate that a user should be created
	 * automatically on registration and after successful authentification.
	 * 
	 * @param userAutoCreate 
	 * @param userClass
	 */
	public void setUserAutoCreate(final boolean userAutoCreate, final Class userClass);

	/**
	 * Return boolean flag that indicates that a user should be created
	 * automatically on registration and after successful authentification.
	 */
	public boolean getUserAutoCreate();

	/**
	 * Return user class
	 */
	public Class getUserClass();

	/**
	 * Initializes the authenticator with data from the given request.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws FrameworkException 
	 */
	public SecurityContext initializeAndExamineRequest(HttpServletRequest request, HttpServletResponse response) throws FrameworkException;
	
	/**
	 * 
	 * @param request
	 * @param resourceSignature
	 * @param propertyView
	 * @throws FrameworkException 
	 */
	public void checkResourceAccess(HttpServletRequest request, final String resourceSignature, final String propertyView) throws FrameworkException;
	
	/**
	 *
	 * Tries to authenticate the given HttpServletRequest.
	 *
	 * @param request the request to authenticate
	 * @param emailOrUsername the (optional) email/username
	 * @param password the (optional) password
	 * 
	 * @return the user that was just logged in
	 * @throws AuthenticationException
	 */
	public Principal doLogin(HttpServletRequest request, final String emailOrUsername, final String password) throws AuthenticationException;

	/**
	 * Logs the given request out.
	 *
	 * @param request the request to log out
	 */
	public void doLogout(HttpServletRequest request);

	/**
	 * Returns the user that is currently logged into the system,
	 * or null if the session is not authenticated.
	 *
	 * @param request the request
	 * @param tryLogin if true, try to login the user
	 * @return the logged-in user or null
	 * @throws FrameworkException
	 */
	public Principal getUser(HttpServletRequest request, final boolean tryLogin) throws FrameworkException;
}
