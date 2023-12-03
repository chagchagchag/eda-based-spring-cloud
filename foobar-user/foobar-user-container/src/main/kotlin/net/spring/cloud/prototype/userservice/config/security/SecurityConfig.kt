package net.spring.cloud.prototype.userservice.config.security

import net.spring.cloud.prototype.userservice.config.security.userdetails.CustomUserDetailsService
import net.spring.cloud.prototype.userservice.config.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
@Configuration
class SecurityConfig (
    val customUserDetailsService: CustomUserDetailsService,
){
    @Bean
    fun passwordEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(httpSecurity: HttpSecurity,
                    authenticationConfiguration: AuthenticationConfiguration,
                    corsFilter: CorsFilter)
    : SecurityFilterChain {
        try{
            val authenticationManager = authenticationConfiguration.authenticationManager

            return httpSecurity
                .csrf { it.disable() }
                .formLogin { it.disable() }
                .httpBasic { it.disable() }
                .addFilter(corsFilter)
                .addFilterBefore(JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter::class.java)
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .headers { customizer -> customizer.frameOptions { it.disable() } }
                .authorizeHttpRequests {
                    it.requestMatchers(
                        AntPathRequestMatcher("/"),
                        AntPathRequestMatcher("/welcome"),
                        AntPathRequestMatcher("/img/**"),
                        AntPathRequestMatcher("/css/**"),
                        AntPathRequestMatcher("/signup"),
                        AntPathRequestMatcher("/login"),
                    )
                    .permitAll()
                    .requestMatchers(
                        AntPathRequestMatcher("/logout"),
                        AntPathRequestMatcher("/users/**"),
                        // TODO /users/me 와 /users/{userId} 의 권한을 분리해둬야 함. 지금은 바쁘기도 하고 다른 기획도 생각하느라 ROLE_ADMIN 까지 적용해둠.
                        AntPathRequestMatcher("/users/health-check"),
                    )
                    .hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                }
                .userDetailsService(customUserDetailsService)
                .build()
        }
        catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}