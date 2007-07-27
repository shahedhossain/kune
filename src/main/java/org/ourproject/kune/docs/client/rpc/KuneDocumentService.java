package org.ourproject.kune.docs.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface KuneDocumentService extends RemoteService {
    public String test();

    public static class App {
        private static KuneDocumentServiceAsync ourInstance = null;

        public static synchronized KuneDocumentServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (KuneDocumentServiceAsync) GWT.create(KuneDocumentService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneDocumentService");
            }
            return ourInstance;
        }
    }
}