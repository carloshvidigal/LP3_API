package com.example.lp3.config;

import com.example.lp3.security.JwtService;
import com.example.lp3.security.JwtAuthFilter;
import com.example.lp3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("api/v1/cargos/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("api/v1/categorias/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/clientes/**")
                        .hasAnyRole("USER", "ADMIN")
                     .antMatchers("/api/v1/compras/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/descartes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/descontos/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/fabricantes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/fornecedores/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/funcionarios/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/itensCompra/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/itensVenda/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/lotes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/motivosDescarte/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/produtos/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/usuarios/**")
                        .permitAll()
                    .antMatchers("/api/v1/vendas/**")
                        .hasAnyRole("USER", "ADMIN")
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(sessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
