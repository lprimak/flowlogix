/*
 * Copyright (C) 2011-2025 Flow Logix, Inc. All Rights Reserved.
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
package com.flowlogix.demo.jeedao;

import com.flowlogix.api.dao.JPAFinder;
import com.flowlogix.jeedao.EntityManagerSelector;
import com.flowlogix.demo.jeedao.entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.experimental.Delegate;

/**
 * Demonstrates injecting {@link JPAFinder} using default {@link EntityManager}
 */
// @start region="injectedNonDefaultExampleDAO"
// tag::injectedNonDefaultExampleDAO[] // @replace regex='.*\n' replacement=""
@Stateless
public class InjectedNonDefaultDAO {
    @Inject
    @Delegate
    @EntityManagerSelector(NonDefault.class)
    JPAFinder<UserEntity> jpaFinder;
}
// end::injectedNonDefaultExampleDAO[] // @replace regex='.*\n' replacement=""
// @end
