package com.intellias.intellistart.interviewplanning.config;


import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import com.intellias.intellistart.interviewplanning.services.JwtTokenProvider;
import com.intellias.intellistart.interviewplanning.services.UserService;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Jwt token authentication filter class.
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private final JwtConfig jwtConfig;
  private final JwtTokenProvider tokenProvider;
  private final UserService userService;

  /**
   * Jwt token authentication filter constructor.
   */
  @Lazy
  public JwtTokenAuthenticationFilter(JwtConfig jwtConfig, JwtTokenProvider tokenProvider,
      UserService userService) {
    this.jwtConfig = jwtConfig;
    this.tokenProvider = tokenProvider;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain chain) throws ServletException, IOException {

    // 1. get the authentication header.
    // Tokens are supposed to be passed in the authentication header
    String header = request.getHeader(jwtConfig.getHeader());

    // 2. validate the header and check the prefix
    if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
      chain.doFilter(request, response);      // If not valid, go to the next filter.
      return;
    }

    // If there is no token provided and hence the user won't be authenticated.
    // It's Ok. Maybe the user accessing a public path or asking for a token.
    //
    // All secured paths that needs a token are already defined and secured in config class.
    // And If user tried to access without access token,
    // then he won't be authenticated and an exception will be thrown.

    // 3. Get the token
    String token = header.replace(jwtConfig.getPrefix(), "");

    if (tokenProvider.validateToken(token)) {
      Claims claims = tokenProvider.getClaimsFromJwt(token);
      String username = claims.getSubject();

      UsernamePasswordAuthenticationToken auth = Optional.of(userService.findUserByEmail(username))
          .map(FacebookUserDetails::new).map(userDetails -> {
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            return authentication;
          }).orElse(null);

      SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
      SecurityContextHolder.clearContext();
    }

    SecurityContextHolder.getContext().setAuthentication(null);

    // go to the next filter in the filter chain
    chain.doFilter(request, response);
  }

}
