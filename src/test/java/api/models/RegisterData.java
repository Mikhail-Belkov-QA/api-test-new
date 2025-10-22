package api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterData{
	private String pass;
	private List<Object> games;
	private int id;
	private String login;
}