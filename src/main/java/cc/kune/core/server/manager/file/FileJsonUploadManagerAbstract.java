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
 \*/
package cc.kune.core.server.manager.file;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public abstract class FileJsonUploadManagerAbstract extends FileUploadManagerAbstract {

  private static final long serialVersionUID = 8437422616838698647L;
  @Inject
  I18nTranslationService i18n;

  @Override
  protected void beforeRespond(final HttpServletResponse response, final Writer w) throws IOException {
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html");
  }

  protected JSONObject createJsonResponse(final boolean success, final String message) {
    return new JSONObject();
  }

  @Override
  protected void onFileUploadException(final HttpServletResponse response) throws IOException {
    doResponse(response, createJsonResponse(false, i18n.t("Error: File too large")).toString());
  }

  @Override
  protected void onOtherException(final HttpServletResponse response, final Exception e)
      throws IOException {
    super.onOtherException(response, e);
    LOG.info("Exception: " + e.getCause());
    // e.printStackTrace();
    doResponse(response, createJsonResponse(false, i18n.t("Error uploading file")).toString());
  }

  @Override
  protected void onSuccess(final HttpServletResponse response) throws IOException {
    doResponse(response, createJsonResponse(true, i18n.t("Uploading was successful")).toString());
  }
}
