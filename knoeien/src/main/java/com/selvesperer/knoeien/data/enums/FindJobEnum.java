package com.selvesperer.knoeien.data.enums;

public enum FindJobEnum {
	LATEST_JOB("LGB", "created_date", "desc"), 
	BEST_PAID_JOB("BPJ", "total_price", "desc"), 
	EARLIEST_DEADLINE_JOB("EDJ", "deadline", "asc"), 
	NEAREST_JOB("NRJ", "", "");

	private String code;
	private String orderBy;
	private String orderType;

	FindJobEnum(String code, String orderBy, String orderType) {
		this.code = code;
		this.orderBy = orderBy;
		this.orderType = orderType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public static FindJobEnum getFindJobEnum(String code) {
		FindJobEnum[] types = FindJobEnum.values();
		for (FindJobEnum findJobEnum : types) {
			if (findJobEnum.getCode().equalsIgnoreCase(code)) return findJobEnum;
		}
		return null;
	}
}
