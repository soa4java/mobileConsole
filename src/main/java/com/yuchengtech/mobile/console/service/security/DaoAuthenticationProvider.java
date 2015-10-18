/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuchengtech.mobile.console.service.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details
 * from an {@link UserDetailsService}.
 *
 * @author Ben Alex
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    //~ Instance fields ================================================================================================

    private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();

    private SaltSource saltSource;

    private UserDetailsService userDetailsService;

    private boolean includeDetailsObject = true;

    //~ Methods ========================================================================================================

    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;

        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
                    includeDetailsObject ? userDetails : null);
        }

        String presentedPassword = authentication.getCredentials().toString();
//        if (!isBobEmpNo(userDetails.getUsername())) {
        	if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
        		throw new BadCredentialsException(messages.getMessage(
        				"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
        				includeDetailsObject ? userDetails : null);
        	}			
//		}
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
//            if (isBobEmpNo(username)) {
//            	ehrAuthenticationService.check(loadedUser, (String)authentication.getCredentials());	
//			}
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }
        if (loadedUser == null) {
            throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
    
//    private boolean isBobEmpNo(String username) {
//		return username.matches("\\d{6}");
//	}

	/**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords.
     * If not set, {@link PlaintextPasswordEncoder} will be used by default.
     *
     * @param passwordEncoder The passwordEncoder to use
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * The source of salts to use when decoding passwords. <code>null</code>
     * is a valid value, meaning the <code>DaoAuthenticationProvider</code>
     * will present <code>null</code> to the relevant <code>PasswordEncoder</code>.
     *
     * @param saltSource to use when attempting to decode passwords via the <code>PasswordEncoder</code>
     */
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

	protected boolean isIncludeDetailsObject() {
        return includeDetailsObject;
    }

    /**
     * Determines whether the UserDetails will be included in the <tt>extraInformation</tt> field of a
     * thrown BadCredentialsException. Defaults to true, but can be set to false if the exception will be
     * used with a remoting protocol, for example.
     *
     * @deprecated use {@link org.springframework.security.authentication.ProviderManager#setClearExtraInformation(boolean)}
     */
    public void setIncludeDetailsObject(boolean includeDetailsObject) {
        this.includeDetailsObject = includeDetailsObject;
    }

}
