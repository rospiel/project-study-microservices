package com.study.microservices.studyapplication.core.security;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(GET, "/kitchens/**").hasAuthority("SEARCH_KITCHEN")

                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter() {

            @Override
            protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
                Collection<GrantedAuthority> authorities = super.extractAuthorities(jwt);

                Map<String, Object> resourceAccess = jwt.getClaims();

                Map<String, Object> resource = MapUtils.isNotEmpty(resourceAccess) ?
                        (Map<String, Object>) resourceAccess.get("realm_access") : MapUtils.EMPTY_SORTED_MAP;

                Collection<String> resourceRoles = MapUtils.isNotEmpty(resource) ?
                        (Collection<String>) resource.get("roles") : CollectionUtils.EMPTY_COLLECTION;

                authorities.addAll(resourceRoles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(toSet()));

                return authorities;
            }
        };
    }
}
