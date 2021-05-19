package project.seccurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceImpl userServiceimpl;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/assets/**","/resources/**","/assets/**","/images/**").permitAll()
                .antMatchers("/","/login","/error","/add","/register/**","/add","get","/getVerifyCode","/register","/forgetPassword/**","/resetPassword/**","/job","/findStaff/**","/AboutUs","/JoinUs").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(customAuthenticationSuccessHandler).failureUrl("/login?error").permitAll()
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login")//退出返回登录界面
                    .permitAll();

    // 关闭CSRF跨域
    http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 配置用户及其对应的角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceimpl);
    }
//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService(){
//        return (UserDetailsService) new MyUserDetails();
//    }

}
