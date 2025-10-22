package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:project.properties"})
public interface AppConfig extends Config {

    @Config.Key("baseUrl")
    String baseUrl();

    @Config.Key("remoteUrl")
    String remoteUrl();
}
