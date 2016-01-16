package com.selvesperer.knoeien.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;

import com.selvesperer.knoeien.exception.SelvEBusinessException;

public class FacesUtils {
	public static Locale getViewLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}

	public static void redirect(String view) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(view);
	}

	public static void addMessage(String clientId, String message) {
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage("", message));
	}

	public static void addMessage(String clientId, String summary, String details) {
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(summary, details));
	}

	public static void addMessage(String clientId, FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(clientId, message);
	}

	public static void addMessages(String clientId, Collection<?> messages) {
		for (Object msg : messages) {
			if (msg instanceof String) {
				addMessage(clientId, (String) msg);
			} else if (msg instanceof FacesMessage) {
				addMessage(clientId, (FacesMessage) msg);
			}
		}
	}

	public static String getClientId(String componentId) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();

		UIComponent c = findComponent(root, componentId);
		if (c == null) {
			return null;
		}
		return c.getClientId(context);
	}

	public static UIComponent findComponent(String componentId) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();

		UIComponent c = findComponent(root, componentId);
		return c;
	}

	public static UIComponent findComponent(UIComponent c, String id) {
		if (id.equals(c.getId()) || id.equals(c.getClientId())) {
			return c;
		}
		Iterator<UIComponent> kids = c.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponent(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	public static String getClientIdEndWith(String componentId) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();

		UIComponent c = findComponentEndWith(root, componentId);
		if (c == null) {
			return null;
		}
		return c.getClientId(context);
	}

	public static UIComponent findComponentEndWith(UIComponent c, String id) {
		if (c.getId().endsWith(id) || c.getClientId().endsWith(id)) {
			return c;
		}
		Iterator<UIComponent> kids = c.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponentEndWith(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	public static String replaceWithHTMLBreak(String s) {
		return s.replace("\n", "<br/>");
	}

	public static void handleException(SelvEBusinessException selvBusinessException) {
		if (selvBusinessException != null) {
			Messages.addGlobalError(selvBusinessException.getMessage(), (Object[]) null);
			FacesContext.getCurrentInstance().validationFailed();
		}

	}	
}
