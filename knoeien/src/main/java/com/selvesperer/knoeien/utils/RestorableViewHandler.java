package com.selvesperer.knoeien.utils;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestorableViewHandler extends ViewHandlerWrapper {

	private static final Logger log = (Logger) LoggerFactory.getLogger(RestorableViewHandler.class);

	private ViewHandler wrapped;

	public RestorableViewHandler(ViewHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public UIViewRoot restoreView(FacesContext context, String viewId) {
		try {
			UIViewRoot restoredView = super.restoreView(context, viewId);
			if (!(restoredView == null && context.isPostback())) {
				return restoredView;
			}

			UIViewRoot createdView = createView(context, viewId);
			context.setViewRoot(createdView);
			getViewDeclarationLanguage(context, viewId).buildView(context, createdView);

			// the following condition always returns null, that's why view is
			// not restored, why we added it ?

			/*
			 * if
			 * (createdView.getAttributes().get(EnableRestorableView.class.getName
			 * ()) == Boolean.TRUE) { return createdView; } else { return null;
			 * }
			 */

			return createdView;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("Exception happened in restoreView: " + e.getMessage() + ",viewId: " + viewId);
			}
			return null;
		}

	}

	@Override
	public ViewHandler getWrapped() {
		return wrapped;
	}

}
