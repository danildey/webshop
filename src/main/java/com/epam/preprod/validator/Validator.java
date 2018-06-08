package com.epam.preprod.validator;

import java.util.Map;

public interface Validator<T> {
	Map<String, String> validate(T req);
}
