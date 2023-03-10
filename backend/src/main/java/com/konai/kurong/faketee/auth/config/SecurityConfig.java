package com.konai.kurong.faketee.auth.config;

import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.auth.CustomAuthenticationProvider;
import com.konai.kurong.faketee.auth.CustomOAuth2UserService;
import com.konai.kurong.faketee.auth.PrincipalDetailsService;
import com.konai.kurong.faketee.utils.handler.CustomLoginFailureHandler;
import com.konai.kurong.faketee.utils.handler.CustomLoginSuccessHandler;
import com.konai.kurong.faketee.utils.handler.CustomOAuthLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final PrincipalDetailsService principalDetailsService;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomOAuthLoginSuccessHandler customOauthLoginSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {

        return new PrincipalDetailsService(userRepository);
    }

    /** ???????????? ????????? ??? bean??? ???????????? AuthenticationManager??? ???????????? ????????? **/
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * Spring??? ????????? AbstractUserDetailAuthenticationProvider??? ???????????? ?????? CustomAuthenticationProvider??????
     * Spring ????????? AbstractUserDetailAuthenticationProvider??? ???????????? UserDetailService(PrincipalDetailsService)?????? ????????? ????????? Principal??? ???????????????
     * @AuthenticationPrincipal ????????????????????? PrincipalDetails ????????? ???????????????.
     * CustomAuthenticationProvider????????? PrincipalDetails??? username??? password ????????? ????????? String?????? UsernamePasswordAuthenticationToken??? ?????????.
     * @return
     */
    @Bean
    public CustomAuthenticationProvider authenticationProvider(){

        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider(principalDetailsService);
        customAuthenticationProvider.setbCryptPasswordEncoder(passwordEncoder());
        return customAuthenticationProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web -> web
                        .ignoring()
                        .antMatchers("/resources/**",
                                    "/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                        .cors().configurationSource(corsConfigurationSource());

        http
                .authorizeRequests()
                    .antMatchers( /**?????? permit all url ?????? */
                            "/",
                            "/account/",
                            "/account/login-form",
                            "/account/register-form",
                            "/account/register-auth",
                            "/account/login-auth",
                            "/account/auth-complete",
                            "/api/account/**"
                    )
                    .permitAll()
//                    .antMatchers("/api/**")
//                    .hasRole(Role.USER.name())
                    .antMatchers("/account/**").hasRole("USER")
                    .anyRequest()
                    .authenticated()
                /**
                 * ????????? ?????? ??? redirect ????????? ?????? ????????? ??????
                 * ????????? ?????? / ??????????????? ???????????? ???????????? ??????
                 */
                .and()
                    .formLogin()
//                        .usernameParameter("email")
                        .loginPage("/account/login-form")
                        .loginProcessingUrl("/login")
                        .successHandler(customLoginSuccessHandler)
                        .failureHandler(customLoginFailureHandler)
                        //.defaultSuccessUrl("/account/set-auth")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            if(session != null){
                                session.invalidate();
                            }
                        })
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true) // ?????? ?????????
//                        .permitAll()
                .and()
                    .oauth2Login()
                        //.defaultSuccessUrl("/account/set-auth")
                        .successHandler(customOauthLoginSuccessHandler)
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);

                http
                        .sessionManagement()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/account/login-form");

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTION"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
