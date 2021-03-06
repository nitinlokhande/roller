/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.apache.roller.weblogger.business;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.TestUtils;
import org.apache.roller.weblogger.business.themes.ThemeManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ThemeManagerTest  {
    public static Log log = LogFactory.getLog(ThemeManagerTest.class);

    @BeforeEach
    public void setUp() throws Exception {
        TestUtils.setupWeblogger();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void testThemeAssumptions() throws Exception {

        ThemeManager themeManager = WebloggerFactory.getWeblogger().getThemeManager();
        themeManager.initialize();

        assertNotNull( themeManager.getTheme("basic") );
        assertNotNull( themeManager.getTheme("basic").getStylesheet() );
        assertNull( themeManager.getTheme("frontpage").getStylesheet() );
    }
        
}




