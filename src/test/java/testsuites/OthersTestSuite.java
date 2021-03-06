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
package testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/{chat,docs,gallery,wiki,workspace,blogs,rack}  -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/    /.class, /g'
 * 
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
// ChatToolTest.class, XmppManagerTest.class, DocumentToolTest.class,
// DocumentServerToolTest.class,
// GalleryServerToolTest.class, WikiServerToolTest.class,
// EntityLogoPresenterTest.class,
// ToolSelectorPresenterTest.class, TagsSummaryPresenterTest.class,
// SNRolActionTest.class, RolComparatorTest.class,
// LicenseWizardPresenterTest.class, BlogServerToolTest.class,
// TestRESTServiceDefinition.class,
// TestRESTMethodFinder.class
})
public class OthersTestSuite {
}
