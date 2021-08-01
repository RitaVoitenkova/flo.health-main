package com.example.health.configuration;

import com.example.health.entity.Permission;
import com.example.health.entity.Role;
import com.example.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void WebSecurityConfig (@Qualifier("userService") UserService userService){
        this.userService = userService;
    }

    @Override //метод настраивает интерсепторы(доступ к url в зависимости от роли пользователя)
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/user/login","/user/reg").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout", "POST"))// создание нового метода для логаута(более безопасный чем стандартный)
                .invalidateHttpSession(true)//закрытие сессии
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")//удаляет ключ по которому автоидентифицировался user
                .logoutSuccessUrl("/user/login");//страница на которую перенаправляет после logout
//                .permitAll();
//        http
//                .cors()
//                .and()
//                .csrf().ignoringAntMatchers("/db/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/user/login", "/user/reg").permitAll()
//                .antMatchers("/user/testUser").hasAuthority(Permission.USER_READ.getPermission())
//                .antMatchers("/user/testAdmin", "/product/editing/**").hasAuthority(Permission.USER_WRITE.getPermission())
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/user/login").permitAll()
//                .defaultSuccessUrl("/")
//                .and()
//                .logout().permitAll();
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout", "POST"))// создание нового метода для логаута(более безопасный чем стандартный)
//                .invalidateHttpSession(true)//закрытие сессии
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")//удаляет ключ по которому автоидентифицировался user
//                .logoutSuccessUrl("/user/login");//страница на которую перенаправляет после logout
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER").build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, user2);

    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10  );
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(encoder());
    }
//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        return daoAuthenticationProvider;
//    }

}
