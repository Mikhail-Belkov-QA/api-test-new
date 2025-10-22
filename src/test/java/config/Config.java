package config;

import org.aeonbits.owner.ConfigFactory;

public class Config {
    private static AppConfig instance;

    private Config(){}

    public static AppConfig getInstance() {
        if(instance == null){
            synchronized (Config.class){
                if (instance == null){
                    instance = ConfigFactory.newInstance().create(AppConfig.class, System.getProperties());
                }
            }
        }
        return instance;
    }
}
