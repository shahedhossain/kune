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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.errors.NotImplementedException;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public abstract class AbstractGxtMenuItemGui extends AbstractChildGuiItem {

  private MenuItem item;

  public AbstractGxtMenuItemGui() {
    super();
  }

  public AbstractGxtMenuItemGui(final MenuItemDescriptor descriptor) {
    super(descriptor);

  }

  private void confCheckListener(final MenuItemDescriptor descriptor, final CheckMenuItem checkItem) {
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
          checkItem.setChecked((Boolean) event.getNewValue());
        }
      }
    });
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    if (descriptor instanceof MenuRadioItemDescriptor) {
      final CheckMenuItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
      checkItem.setGroup(((MenuRadioItemDescriptor) descriptor).getGroup());
      confCheckListener((MenuItemDescriptor) descriptor, checkItem);
      item = checkItem;
    } else if (descriptor instanceof MenuCheckItemDescriptor) {
      final CheckMenuItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
      confCheckListener((MenuItemDescriptor) descriptor, checkItem);
      item = checkItem;
    } else {
      item = new MenuItem("");
    }

    final String id = descriptor.getId();
    if (id != null) {
      item.ensureDebugId(id);
    }
    item.addSelectionListener(new SelectionListener<MenuEvent>() {
      @Override
      public void componentSelected(final MenuEvent ce) {
        final AbstractAction action = descriptor.getAction();
        if (action != null) {
          action.actionPerformed(new ActionEvent(item, getTargetObjectOfAction(descriptor),
              Event.getCurrentEvent()));
        }
      }
    });
    child = item;
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  private CheckMenuItem createCheckItem(final MenuItemDescriptor descriptor) {
    final CheckMenuItem checkItem = new CheckMenuItem();
    checkItem.setChecked(((MenuCheckItemDescriptor) descriptor).isChecked());
    return checkItem;
  }

  private String createShortCut(final KeyStroke key, final String style) {
    // See: https://yui-ext.com/forum/showthread.php?t=5762
    final Element keyLabel = DOM.createSpan();
    keyLabel.setId(style);
    keyLabel.setInnerText(key.toString());
    return keyLabel.getString();
  }

  public MenuItem getItem() {
    return item;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    item.setVisible(enabled);
  }

  @Override
  public void setIconBackground(final String back) {
    throw new NotImplementedException();
  }

  @Override
  public void setIconResource(final ImageResource icon) {
    item.setIcon(AbstractImagePrototype.create(icon));
  }

  @Override
  protected void setIconStyle(final String style) {
    item.setIconStyle(style);
  }

  @Override
  public void setIconUrl(final String url) {
    throw new NotImplementedException();
  }

  @Override
  protected void setText(final String text) {
    if (text != null) {
      final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
      if (key == null) {
        item.setText(text);
      } else {
        item.setText(text + createShortCut(key, "oc-mshortcut-hidden")
            + createShortCut(key, "oc-mshortcut"));
      }
    }
  }

  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return false;
  }
}
