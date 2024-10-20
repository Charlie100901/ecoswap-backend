package com.ecoswap.ecoswap.configuration;

import com.ecoswap.ecoswap.configuration.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();

                    authorize.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/product/category/{category}").permitAll();

                    authorize.requestMatchers(HttpMethod.POST, "/api/v1/product/create").authenticated();
                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/product").permitAll();
                    authorize.requestMatchers(HttpMethod.DELETE, "/product/{id}").authenticated();
                    authorize.requestMatchers(HttpMethod.PUT, "/product/{id}").authenticated();
                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/product/{id}").permitAll();

                    authorize.requestMatchers(HttpMethod.GET, "/images/**").permitAll();

                    authorize.requestMatchers(HttpMethod.POST, "/api/v1/create-exchange").authenticated();
                    authorize.requestMatchers(HttpMethod.POST, "/api/v1/select-exchange").authenticated();
                    authorize.requestMatchers(HttpMethod.POST, "/api/v1/exchanges").permitAll();


                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/user/me").authenticated();



                    // authorize.requestMatchers(HttpMethod.POST, "/api/v1/create").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());

                    authorize.anyRequest().denyAll();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
