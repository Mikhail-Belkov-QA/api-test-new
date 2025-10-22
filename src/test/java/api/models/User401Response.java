package api.models;

import lombok.Data;

@Data
public class User401Response{
	private String path;
	private String error;
	private String timestamp;
	private int status;
}