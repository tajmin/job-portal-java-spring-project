package com.selvesperer.knoeien.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;
import java.lang.ref.WeakReference;

public class ViewScopeViewMapListener implements ViewMapListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewScopeViewMapListener.class);
    private String name;
    private Runnable callback;
    private boolean callbackCalled = false;
    private WeakReference<UIViewRoot> uiViewRootWeakReference;

    public ViewScopeViewMapListener(UIViewRoot root, String name, Runnable callback, ViewScope viewScope) {
        this.name = name;
        this.callback = callback;
        this.uiViewRootWeakReference = new WeakReference<>(root);
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PreDestroyViewMapEvent) {
            LOGGER.debug("Going call callback for bean {}", name);
            doCallback();
        }
    }

    public boolean checkRoot() {
        if (uiViewRootWeakReference.get() == null) {
            doCallback();
            return true;
        }
        return false;
    }

    public synchronized void doCallback() {
        LOGGER.debug("Going call callback for bean {}", name);
        if (!callbackCalled) {
            try {
                callback.run();
            } finally {
                callbackCalled = true;
            }
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source == uiViewRootWeakReference.get());
    }
}