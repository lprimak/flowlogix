/*
 * Copyright 2020 lprimak.
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
package com.flowlogix.jeedao.primefaces.internal;

import com.flowlogix.jeedao.primefaces.interfaces.Filter;
import com.flowlogix.jeedao.primefaces.interfaces.Sorter;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author lprimak
 */
@Builder
public class MyModel<KK, TT>
{
    private final @Getter @NonNull Supplier<EntityManager> emg;
    private final @Getter @NonNull Function<String, TT> converter;
    @Builder.Default
    private final @Getter @NonNull Filter<TT> filter = (a, b, c) -> {};
    @Builder.Default
    private final @Getter @NonNull Sorter<TT> sorter = (a, b, c) -> {};
    @Builder.Default
    private final @Getter @NonNull Function<TT, TT> optimizer = (a) -> a;
}
