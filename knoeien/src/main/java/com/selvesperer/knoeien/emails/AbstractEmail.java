package com.selvesperer.knoeien.emails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;

/**
 * @author Mithun <shahinur.bd@gmail.com>
 *
 */
public abstract class AbstractEmail implements Serializable{

	private static final long serialVersionUID = 9222202495427414321L;

	private static Logger log = LoggerFactory.getLogger(AbstractEmail.class);	
	
	private List<User> users;
	private String replyToAddress;
	private Locale locale;
	List<User> bccUserList;
	List<User> ccUserList;
	List<String> listOfStrings;
	
	public AbstractEmail(User user) {
		this.users = new ArrayList<User>();
		if (user != null) {
			users.add(user);
		}
	}
	
	public AbstractEmail(List<User> users) {
		this.users = users;
	}
		
	public Locale getLocale() {
		if (locale != null) return locale;		
		if (locale == null) {
			try {
				locale = Faces.getLocale();
				
			} catch (Exception e){
				return Locale.US;
			}
		}
		return locale;
	}
	
	public abstract HashMap<String, String> addProperties(HashMap<String, String>vals);
	public abstract HashMap<String, Object> addPropertiesAsObject(HashMap<String, Object>vals);
	
	public abstract String getSubject();
	public abstract String getTitle();

	public String getEmailTemplateName() { return "temp"; }
	
	public final boolean hasUsers() {
		if (getUsers() != null && !getUsers().isEmpty()) return true;
		if (getCcUserList() != null && !getCcUserList().isEmpty()) return true;
		if (getBccUserList() != null && !getBccUserList().isEmpty()) return true;
		return false;
	}
	
	public final String getCompanyID() {
		String companyID = null;
		List<User> u = getUsers();
		if (u != null && !u.isEmpty() && !StringUtils.isBlank(u.get(0).getCompanyID())) return u.get(0).getCompanyID();
		u = getCcUserList();
		if (u != null && !u.isEmpty() && !StringUtils.isBlank(u.get(0).getCompanyID())) return u.get(0).getCompanyID();
		u = getBccUserList();
		if (u != null && !u.isEmpty() && !StringUtils.isBlank(u.get(0).getCompanyID())) return u.get(0).getCompanyID();
		return "temp";
	}
	
	public HashMap<String, String> addBaseURL(HashMap<String, String>vals) {
		vals.put("baseURL", getBaseURL());
		return vals;	
	}
	public HashMap<String, String> addSubject(HashMap<String, String>vals) {
		vals.put("subject", getSubject());
		return vals;	
	}

	public HashMap<String, String> addTitle(HashMap<String, String>vals) {
		vals.put("title", getTitle());
		return vals;	
	}	
	
	public String getBaseURL() {
		return ConfigurationUtil.getBaseUrl();
	}
	
	public String getImageText() {
		return "";
	}
	
	public String getSubtitle() {
		return "";
	}

	public void setToUser(User user) {
		this.users = new ArrayList<User>();
		users.add(user);
	}
	
	public User getUser() {
		if (users != null && users.size() > 0) return users.get(0);
		return null;
	}
	
	public List<User> getAllUsers() {
		List<User> allusers = new ArrayList<User>();
		if (users != null) allusers.addAll(users);
		if (bccUserList!=null) allusers.addAll(bccUserList);
		if (ccUserList!=null) allusers.addAll(ccUserList);
		return allusers;
	}
	
	public void addToUsers(MultiPartEmail email) throws EmailException {
		if (users.size() == 1) {
			email.addTo(users.get(0).getEmail());
		} else {
			for (User user: users) {
				email.addBcc(user.getEmail());
			}
		}
	}
	
	protected void addValFromMsgKey(HashMap<String, String>vals, String key, String msg, Object... msgArgs) {
		if (key != null) {
			vals.put(key, LocalizationUtil.findLocalizedString(locale, msg, msgArgs));
		}
	}
	
	public HashMap<String, String> addImageText(HashMap<String, String>vals) {
		vals.put("image",  getImageText());
		return vals;
	}

	public List<User> getBccUserList() {
		return bccUserList;
	}

	public void setBccUserList(List<User> bccUserList) {
		this.bccUserList = bccUserList;
	}

	public List<User> getCcUserList() {
		return ccUserList;
	}

	public void setCcUserList(List<User> ccUserList) {
		this.ccUserList = ccUserList;
	}	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String getReplyToAddress() {
		return replyToAddress;
	}
	
	public void setReplyToAddress(String s) {
		replyToAddress = s;
	}
}
