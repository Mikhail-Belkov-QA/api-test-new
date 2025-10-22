package api.service;

import io.restassured.specification.RequestSpecification;

import static api.helper.RequestHelper.getDefaultRequestSpec;

public class ServiceManager {


    public static UserServiceMisha getUserServiceMisha(){
        return new UserServiceMisha(getDefaultRequestSpec());
    }


    public static UserServiceMisha getUserServiceMisha2(final RequestSpecification specification){
        return new UserServiceMisha(specification);
    }
}
