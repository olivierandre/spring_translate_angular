package fr.oan.translate.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import fr.oan.translate.domain.User;
import fr.oan.translate.domain.UserTransfer;
import fr.oan.translate.service.UserService;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private final UserService userService;

	public AuthenticationTokenProcessingFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractAuthTokenFromRequest(httpRequest);
		String userName = TokenUtils.getUserNameFromToken(authToken);

		if (userName != null) {

			User user = userService.findUserByUserName(userName);

			if (user != null) {
				try {

					UserTransfer userTransfer = userService
							.getUserTransfer(user);
					if (TokenUtils.validateToken(authToken, userTransfer)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userName, null,
								AuthorityUtils.createAuthorityList("user"));
						authentication.setDetails(user);
						SecurityContextHolder.getContext().setAuthentication(
								authentication);
					}
				} catch (RuntimeException e) {
				}
			}

		}

		chain.doFilter(request, response);

	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Auth-Token");

		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("X-Auth-Token");
		}

		return authToken;
	}

}
