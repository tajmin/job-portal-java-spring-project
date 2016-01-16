package com.selvesperer.knoeien.config;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationPasswordService implements PasswordService {

	@Inject
	StandardPasswordEncoder standardPasswordEncoder;

	private static Logger log = (Logger) LoggerFactory.getLogger(ApplicationPasswordService.class);

	@Override
	public String encryptPassword(Object paramObject) throws IllegalArgumentException {
		if (log.isDebugEnabled()) log.debug("@ApplicationPasswordService");
		return standardPasswordEncoder.encode(paramObject.toString());
	}

	@Override
	public boolean passwordsMatch(Object paramObject, String paramString) {
		if (paramObject != null && StringUtils.isNotBlank(paramString)) {
			char[] characters = (char[]) paramObject;
			StringBuffer input = new StringBuffer();
			for (char c : characters) {
				input.append(c);
			}

			boolean result = standardPasswordEncoder.matches(input.toString(), paramString);
			if (!result)
				result = input.toString().equals(paramString);
			if (log.isDebugEnabled())
				log.debug("password match result:" + result);
			return result;
		} else
			return false;
	}

}
