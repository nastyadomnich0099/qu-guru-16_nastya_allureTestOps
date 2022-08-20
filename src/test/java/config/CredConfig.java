package config;
import org.aeonbits.owner.Config;


@Config.Sources("classpath:demowebshopcred/credential.properties")
public interface CredConfig extends Config{
    String password();

    String email();

}
