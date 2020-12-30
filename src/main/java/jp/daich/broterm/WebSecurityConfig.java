package jp.daich.broterm;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // "/" と /home は全ユーザーがアクセス可能
                .antMatchers("/", "/menu").permitAll()
                // 上記以外へのアクセスは認証が必要
                .anyRequest().authenticated().and()
                // ログイン、ログアウトのURL指定と全ユーザーへのアクセス許可
                .formLogin().loginPage("/login").permitAll().and().logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // パスワード
        String password = passwordEncoder().encode("password");

        // インメモリの認証を行うための設定
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password(password)
                .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
