package org.ourproject.kune.sitebar.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 * 
 */
public interface Images extends ImageBundle {

    public static class App {
        private static Images ourInstance = null;

        public static synchronized Images getInstance() {
            if (ourInstance == null) {
                ourInstance = (Images) GWT.create(Images.class);
            }
            return ourInstance;
        }
    }

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-search-ico.png
     */
    AbstractImagePrototype kuneSearchIco();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-search-ico-push.png
     */
    AbstractImagePrototype kuneSearchIcoPush();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/spin-kune-thund-green.gif
     */
    AbstractImagePrototype spinKuneThundGreen();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-logo-16px.png
     */
    AbstractImagePrototype kuneLogo16px();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/cross.png
     */
    AbstractImagePrototype cross();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/cross-dark.png
     */
    AbstractImagePrototype crossDark();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/emblem-important.png
     */
    AbstractImagePrototype emblemImportant();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/error.png
     */
    AbstractImagePrototype error();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/important.png
     */
    AbstractImagePrototype important();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/info.png
     */
    AbstractImagePrototype info();

}