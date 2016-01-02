package com.selvesperer.knoeien.utils;


public interface Constants {

	// Common
	public static final String DOUBLE_QUOTE = "\"";
	public static final String COLON = ":";
	public static final String COMMA = ",";
	public static final String BRACE_SECOND_START = "{";
	public static final String BRACE_SECOND_END = "}";
	public static final String BRACE_THIRD_START = "[";
	public static final String BRACE_THIRD_END = "]";
	public static final String LINE_BREAK = "\n";

	public static final String CURRENT_MONTH_ABBREVIATION = "CM";
	public static final String CURRENT_QUARTER_ABBREVIATION = "CQ";
	public static final String CURRENT_YEAR_ABBREVIATION = "CY";

	public static final String LAST_MONTH_ABBREVIATION = "LM";
	public static final String LAST_QUARTER_ABBREVIATION = "LQ";
	public static final String LAST_YEAR_ABBREVIATION = "LY";
	public static final String REWARD_DEPARTMENT = "Rewards";

	/*
	 * To be used as session variable keys
	 */
	public static final String CURRENT_USER_ID = "curUserID";
	public static final String CURRENT_COMPANY_ID = "curCompanyID";
	public static final int TOKEN_EXPIRES_IN_MINUTES = 10;

	/*
	 * Cookie Names
	 */
	public static final String HOME_FEEDSCOPE_COOKIE = "dfs";
	public static final String MILESTONE_FEEDSCOPE_COOKIE = "milestoneScope";

	/*
	 * Images
	 */
	public static final String DEFAULT_AVATAR = "user-placeholder.png";
	public static final String DEFAULT_INVENTORY_GIFT = "default-gift.png";
	public static final String DEFAULT_BADGE_ICON = "global/badges/4.png";
	public static final Integer BADGE_DIMENSIONS_INPIX = 90;
	public static final Integer INVENTORY_ITEM_WIDTH_INPIX = 250;
	public static final Integer INVENTORY_ITEM_HEIGHT_INPIX = 160;

	/*
	 * Page Names
	 */
	public static final String AWARDS_PAGE_NAME = "awards.xhtml";
	public static final String INVENTORY_PAGE_NAME = "inventory.xhtml";
	public static final String ACCOUNT_ACTIVATION_URL = "setup/account-activation.xhtml";
	public static final String STANDBY_URL = "standBy.xhtml";
	public static final String TERMINATED_FLOW_URL = "message.xhtml";
	public static final String EXPIRED_ACCOUNT_URL = "account-inactive.xhtml";
	public static final String WELCOME_NEW_USER_URL = "welcome.xhtml";

	
	/*
	 * Static configurations
	 */
	public static final int INTEREST_LIST_SIZE = 15;
	public static final int ANNOUNCEMENT_SIZE = 5;
	public static final int NEWHIRE_SIZE = 10;
	public static final int NEWHIRE_DAY_LIMIT = 21;	
	/*
	 * URLs
	 */
	public static final String LOGIN_URL = "login.xhtml";

	/*
	 * Email
	 */
	public static final String SUPPORT_EMAIL = "support@selvesperer.com";
	public static final String PICKADAY_CALENDAR_DATE_FORMAT = "EEE MMM dd yyyy";
	
	/* action */
	public static final String ACTION_SAVE = "SAVE";
	public static final String ACTION_DELETE = "DELETE";
	public static final String ACTION_ADD = "ADD";
	public static final String ACTION_UPDATE = "UPDATE";

	public static final int LICENSE_PRICE_PER_YEAR = 35;
	public static final int TYPEAHEAD_MAX_RESULTS = 10;
	public static final String[] CHARSET = { "a", "A", "b", "B", "1", "!", "c", "C", "d", "D", "e", "E", "@", "2", "#", "f", "F", "$", "g", "G", "h", "H", "3", "i", "I", "%", "j", "J", "k", "^", "K", "4", "l", "?", "L", "m", "M", "&", "n", "N", "o", "O", "5", "p", "*", "P", "q", "Q", "r", "R", "6", "s", "S", "t", "T", "7", "u", "U", "<", "v", "8", "w", "W", "x", "X", "y", "Y", ">", "9", "z", "Z" };
	public static final int REMEMBERME_KEY_LENGTH = 27;
	public static final int REMEMBERME_SECRET_LENGTH = 7;
	public static final int TYPEAHEAD_MAX_RESULTS_NEW = 20;	
}
