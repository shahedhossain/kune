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
package cc.kune.core.client.sitebar.search;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.shared.SearcherConstants;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBoxBase;

public class SearchBoxFactory {

  public static MultivalueSuggestBox create(final I18nUITranslationService i18n,
      final boolean searchOnlyUsers, final boolean showNoResult, final String id,
      final OnEntitySelectedInSearch callback) {
    final MultivalueSuggestBox multivalueSBox = new MultivalueSuggestBox(
        i18n,
        showNoResult,
        searchOnlyUsers ? SearcherConstants.USER_DATA_PROXY_URL : SearcherConstants.GROUP_DATA_PROXY_URL,
        false, new OnExactMatch() {

          @Override
          public void onExactMatch(final String match) {
            // NotifyUser.info(match);
          }
        }) {

      @Override
      public void onSelection(
          final com.google.gwt.event.logical.shared.SelectionEvent<com.google.gwt.user.client.ui.SuggestOracle.Suggestion> event) {
        super.onSelection(event);
        final Suggestion suggestion = event.getSelectedItem();
        if (suggestion instanceof OptionSuggestion) {
          final OptionSuggestion osugg = (OptionSuggestion) suggestion;
          final String value = osugg.getValue();
          if (!OptionSuggestion.NEXT_VALUE.equals(value)
              && !OptionSuggestion.PREVIOUS_VALUE.equals(value)) {
            callback.onSeleted(value);
            this.getSuggestBox().setValue("", false);
          }
        }
      };
    };

    final String siteCommonName = i18n.getSiteCommonName();
    final SuggestBox suggestBox = multivalueSBox.getSuggestBox();
    Tooltip.to(
        suggestBox,
        searchOnlyUsers ? i18n.t("Type something to search for users in [%s]", siteCommonName) : i18n.t(
            "Type something to search for users and groups in [%s]", siteCommonName));
    final TextBoxBase textBox = suggestBox.getTextBox();
    textBox.setDirection(i18n.isRTL() ? Direction.RTL : Direction.LTR);
    textBox.ensureDebugId(id);
    return multivalueSBox;
  }

}
