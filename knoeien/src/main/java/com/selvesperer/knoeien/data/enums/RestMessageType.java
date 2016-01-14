package com.selvesperer.knoeien.data.enums;

public enum RestMessageType {
	INFO("IF", "INFO"), ERROR("ER", "ERROR"), WARN("WR", "WARN");

	private String code;
	private String value;

	RestMessageType(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static RestMessageType getRestMessageType(String code) {
		RestMessageType[] types = RestMessageType.values();
		for (RestMessageType restMessageType : types) {
			if (restMessageType.getCode().equalsIgnoreCase(code))
				return restMessageType;
		}
		return null;
	}
}
