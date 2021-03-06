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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class I18nLanguageSimpleDTO implements IsSerializable {

  public static I18nLanguageSimpleDTO create(final I18nLanguageDTO language) {
    return new I18nLanguageSimpleDTO(language.getCode(), language.getEnglishName());
  }

  private String code;

  private String englishName;

  public I18nLanguageSimpleDTO() {
  }

  public I18nLanguageSimpleDTO(final String code, final String englishName) {
    this.code = code;
    this.englishName = englishName;
  }

  public String getCode() {
    return code;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public void setEnglishName(final String englishName) {
    this.englishName = englishName;
  }

}
