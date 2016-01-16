package com.selvesperer.knoeien.data.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.SelvEDate;

public class AuditEntityListener {
	private static final Logger log = (Logger) LoggerFactory.getLogger(AuditEntityListener.class);

	@PrePersist
	public void prePersist(AuditableEntity e) {
		try {
			if (!StringUtils.isEmpty(e.getCreatedByID()) && !StringUtils.isEmpty(e.getCreatedByName())) {
				return;//already set so don't override
			}
			if (SecurityManager.getCurrentUserId() != null && SecurityManager.isAuthenticated()) {

				try {
					UserService userService = ApplicationBeanFactory.getBean(UserService.class);
					User user = userService.findUserById(SecurityManager.getCurrentUserId());
					if (user != null && e.getCreatedByName() == null) {
						e.setCreatedByID(user.getId());
						e.setCreatedByName(user.getFullName());
					}
				} catch (Exception ex) {
					log.error("Exception happened in AuditableEntity.prePersist !!! " + ex.getMessage());
					log.error(e.getClass().getName());
					log.error(ex.getStackTrace() + "");
				}

			} else {
				e.setCreatedByName("SelvEsperer");// this has to be fixed
			}
		} catch (Exception ex) {
			log.error("Exception in Auditable Entity Listener" + ex);
		} finally {
			if( e.getCreatedDate() == null ){
				e.setCreatedDate(SelvEDate.now());
			}
		}
	}

	@PreUpdate
	public void preUpdate(AuditableEntity e) {
		try {
			if (SecurityManager.getCurrentUserId() != null && SecurityManager.isAuthenticated()) {
				try {
					UserService userService = ApplicationBeanFactory.getBean(UserService.class);
					User user = userService.findUserById(SecurityManager.getCurrentUserId());
					if (user != null) {
						e.setLastModifiedByID(user.getId());
						e.setLastModifiedByName(user.getFullName());
					}
				} catch (Exception ex) {
					log.error("Exception in AuditableEntity.preUpdate" + ex.getMessage());
				}
			} else {
				e.setLastModifiedByName("SelvEsperer");
			}
		} catch (Exception ex) {
			log.error("Exception in Auditable Entity Listener" + ex.getMessage());
		} finally {
			e.setLastModifiedDate(SelvEDate.now());
		}
	}

}
