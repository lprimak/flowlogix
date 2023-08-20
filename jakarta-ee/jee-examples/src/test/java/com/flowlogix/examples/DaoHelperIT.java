/*
 * Copyright (C) 2011-2023 Flow Logix, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flowlogix.examples;

import com.flowlogix.demo.jeedao.DaoHelperDemo;
import com.flowlogix.demo.jeedao.UserDAO;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static com.flowlogix.examples.ExceptionPageIT.DEPLOYMENT_DEV_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class DaoHelperIT {
    @Inject
    UserDAO userDao;
    @Inject
    DaoHelperDemo demo;

    @Test
    @OperateOnDeployment(DEPLOYMENT_DEV_MODE)
    @SuppressWarnings("MagicNumber")
    void extractedCountAndList() {
        assertEquals(5, userDao.count());
        assertEquals(userDao.extractedCountAndList("Cool Cousin"), userDao.countAndList("Cool Cousin"));
    }

    @Test
    @OperateOnDeployment(DEPLOYMENT_DEV_MODE)
    @SuppressWarnings("MagicNumber")
    void demo() {
        assertEquals(5, demo.count());
        var users = demo.enhancedFind("Lovely Lady");
        assertEquals(1, users.count());
        var user = users.list().stream().findFirst().orElseThrow();
        assertEquals("jprimak", user.getUserId());
        assertEquals(user.getFullName(), demo.findById(user.getId()).getFullName());
        assertEquals(5, demo.injectedCount());
        assertEquals(5, demo.inheritedCount());
        assertEquals("anya", demo.nativeFind("""
                        select * from userentity
                        where zipcode = 68502 order by userid limit 2""")
                .getUserId());
    }

    @Deployment(name = DEPLOYMENT_DEV_MODE)
    public static WebArchive createDeployment() {
        return ExceptionPageIT.createDeploymentDev();
    }
}
