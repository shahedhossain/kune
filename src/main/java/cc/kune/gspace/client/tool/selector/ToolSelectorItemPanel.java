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
package cc.kune.gspace.client.tool.selector;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ToolSelectorItemPanel extends Composite implements ToolSelectorItemView {

  interface ToolSelectorItemPanelUiBinder extends UiBinder<Widget, ToolSelectorItemPanel> {
  }

  public static final String TOOL_ID_PREFIX = "k-tool-item-";

  private static ToolSelectorItemPanelUiBinder uiBinder = GWT.create(ToolSelectorItemPanelUiBinder.class);

  @UiField
  HTMLPanel arrow;
  @UiField
  FlowPanel flow;
  @UiField
  Image iconLeft;
  @UiField
  Image iconRight;
  @UiField
  InlineLabel label;
  @UiField
  FocusPanel self;
  private final String shortName;

  public ToolSelectorItemPanel(final String shortName, final ImageResource icon) {
    this.shortName = shortName;
    initWidget(uiBinder.createAndBindUi(this));
    ensureDebugId(TOOL_ID_PREFIX + shortName);
    setVisibleImpl(false);
    iconLeft.setResource(icon);
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  private void focus() {
    self.addStyleDependentName("focus");
    self.removeStyleDependentName("nofocus");
  }

  @Override
  public HasClickHandlers getFocus() {
    return self;
  }

  public Widget getFocusPanel() {
    return flow;
  }

  @Override
  public HasText getLabel() {
    return label;
  }

  public String getName() {
    return shortName;
  }

  @Override
  public Object getTarget() {
    return this;
  }

  @UiHandler("self")
  void onSelfMouseOut(final MouseOutEvent event) {
    unfocus();
  }

  @UiHandler("self")
  void onSelfMouseOver(final MouseOverEvent event) {
    focus();
  }

  @Override
  public void setSelected(final boolean selected) {
    if (selected) {
      self.addStyleDependentName("selected");
      self.removeStyleDependentName("notselected");
      arrow.setVisible(true);
      // iconRight.setVisible(true);
    } else {
      self.addStyleDependentName("notselected");
      self.removeStyleDependentName("selected");
      iconRight.setVisible(false);
      arrow.setVisible(false);
    }
  }

  @Override
  public void setTooltip(final String tooltip) {
    Tooltip.to(this, tooltip);
  }

  @Override
  public void setVisible(final boolean visible) {
    setVisibleImpl(visible);
  }

  private void setVisibleImpl(final boolean visible) {
    self.setVisible(visible);
  }

  private void unfocus() {
    self.addStyleDependentName("nofocus");
    self.removeStyleDependentName("focus");
  }

}
