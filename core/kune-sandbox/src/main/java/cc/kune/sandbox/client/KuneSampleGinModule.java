/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.kune.sandbox.client;

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.ui.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.events.EventBusInstance;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.UserNotifierPopup;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutRegisterDefault;
import cc.kune.common.shared.i18n.HasRTL;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.i18n.I18nTranslationServiceMocked;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class KuneSampleGinModule extends AbstractGinModule {
  @Override
  protected void configure() {
    bind(I18nTranslationService.class).to(I18nTranslationServiceMocked.class).in(Singleton.class);
    bind(HasRTL.class).to(I18nTranslationServiceMocked.class).in(Singleton.class);
    bind(I18n.class).in(Singleton.class);
    requestStaticInjection(I18n.class);
    requestStaticInjection(NotifyUser.class);
    bind(UserNotifierPopup.class).asEagerSingleton();
    requestStaticInjection(EventBusInstance.class);
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);
    bind(GwtGuiProvider.class).asEagerSingleton();

    bind(GlobalShortcutRegister.class).to(GlobalShortcutRegisterDefault.class).in(Singleton.class);

    bind(Toolbar.class).in(Singleton.class);
  }
}