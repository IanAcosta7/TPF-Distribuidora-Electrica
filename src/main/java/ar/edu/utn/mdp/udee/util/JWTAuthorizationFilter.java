package ar.edu.utn.mdp.udee.util;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (containsJWT(request)) {
                Claims claims = validateToken(request);

                if (claims.get("userid") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private void setUpSpringAuthentication(Claims claims) {
        try {
            List<String> authorities = (List) claims.get("authorities");
            Integer userId = (int)claims.get("userid");
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(Constants.JWT_HEADER).replace(Constants.JWT_PREFIX, "");
        return Jwts.parser().setSigningKey(Constants.JWT_SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private boolean containsJWT(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(Constants.JWT_HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(Constants.JWT_PREFIX);
    }
}
