/*
 * Copyright 2006, 2012 Robert Hanson <iamroberthanson AT gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.kune.common.client.utils;

public class WindowUtils {

  /**
   * https://developer.mozilla.org/en/DOM/window.location
   * 
   * @param newUrl
   */
  public static native void changeHref(String newUrl) /*-{
                                                      $wnd.location.href = newUrl;
                                                      }-*/;

  /**
   * https://developer.mozilla.org/en/DOM/window.location
   * 
   * "If you need to change pathname but keep the hash as is, use the replace() method instead, which should work consistently across browsers."
   * 
   * @param newUrl
   */
  public static native void changeHrefKeepHash(String newUrl) /*-{
                                                              // $wnd.location.href = newUrl;
                                                              $wnd.location.replace(newUrl);
                                                              }-*/;

  public static native boolean dontHasWebSocket() /*-{
                                                  return !window.WebSocket
                                                  }-*/;

  private static native String getHash() /*-{
                                         return $wnd.location.hash;
                                         }-*/;

  private static native String getHost() /*-{
                                         return $wnd.location.host;
                                         }-*/;

  private static native String getHostName() /*-{
                                             return $wnd.location.hostname;
                                             }-*/;

  private static native String getHref() /*-{
                                         return $wnd.location.href;
                                         }-*/;

  public static Location getLocation() {
    final Location result = new Location();
    result.setHash(getHash());
    result.setHost(getHost());
    result.setHostName(getHostName());
    result.setHref(getHref());
    result.setPath(getPath());
    result.setPort(getPort());
    result.setProtocol(getProtocol());
    result.setQueryString(getQueryString());
    return result;
  }

  private static native String getPath() /*-{
                                         return $wnd.location.pathname;
                                         }-*/;

  private static native String getPort() /*-{
                                         return $wnd.location.port;
                                         }-*/;

  private static native String getProtocol() /*-{
                                             return $wnd.location.protocol;
                                             }-*/;

  private static native String getQueryString() /*-{
                                                return $wnd.location.search;
                                                }-*/;

  public static String getParameter(String param) {
    return getLocation().getParameter(param);
  }

}
