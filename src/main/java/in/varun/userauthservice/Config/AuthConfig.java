package in.varun.userauthservice.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ser.jdk.StringSerializer;

import javax.crypto.SecretKey;

@Configuration
public class AuthConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecretKey secretKey() {
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey = algorithm.key().build();
        return  secretKey;
    }

//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(props);
//    }

    //Disable csrf
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/login", "/auth/register").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }





}
