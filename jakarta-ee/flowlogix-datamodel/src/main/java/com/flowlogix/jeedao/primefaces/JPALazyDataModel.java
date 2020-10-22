/*
 * Copyright 2014 lprimak.
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
package com.flowlogix.jeedao.primefaces;

import com.flowlogix.jeedao.primefaces.internal.JPAModelImpl;
import com.flowlogix.jeedao.primefaces.support.FilterData;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Beans;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 * Easy implementation of PrimeFaces lazy data model
 * using Lambdas
 *
 * @author lprimak
 * @param <KK> Key Type
 * @param <TT> Data Type
 */
@Dependent
public class JPALazyDataModel<KK, TT> extends LazyDataModel<TT> {
    private static final long serialVersionUID = 2L;
    private final JPAModelImpl impl;

    /**
     * Set up this particular instance of the data model
     * with entity manager, class and key converter
     *
     * @param <K1>
     * @param <T1>
     * @param emg
     * @param entityClass
     * @param converter
     * @return newly-created data model
     */
    public static<K1, T1> JPALazyDataModel<K1, T1> createModel(Supplier<EntityManager> emg,
            Class<T1> entityClass, KeyConverter<K1> converter)
    {
        return createModel(emg, entityClass, converter, (in) -> {});
    }


    /**
     * Set up this particular instance of the data model
     * with entity manager, class and key converter
     *
     * @param <K1> Key Type
     * @param <T1> Value Type
     * @param emg
     * @param entityClass
     * @param converter
     * @param initializer
     * @return newly-created data model
     */
    public static<K1, T1> JPALazyDataModel<K1, T1> createModel(Supplier<EntityManager> emg,
            Class<T1> entityClass, KeyConverter<K1> converter, Initializer<K1, T1> initializer)
    {
        @SuppressWarnings("unchecked")
        JPALazyDataModel<K1, T1> model = Beans.getReference(JPALazyDataModel.class);
        model.emg = emg;
        model.facade = new JPAModelImpl<>(model, entityClass);
        model.converter = converter;
        initializer.init(model);
        return model;
    }

    /**
     * Utility method for replacing a predicate in the filter list
     *
     * @param filters filter list
     * @param element element to be replace
     * @param fp lambda to get the new Filter predicate
     */
    public void replaceFilter(Map<String, FilterData> filters, String element, FilterReplacer fp)
    {
        FilterData elt = filters.get(element);
        if (elt != null && StringUtils.isNotBlank(elt.getFieldValue()))
        {
            filters.replace(element, new FilterData(elt.getFieldValue(),
                    fp.get(elt.getPredicate(), elt.getFieldValue())));
        }
    }

    /**
     * transforms JPA entity field to format suitable for hints
     *
     * @param val
     * @return JPA field suitable for hints
     */
    public String getResultField(String val)
    {
        return String.format("%s.%s", RESULT, val);
    }


    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public KK getRowKey(TT entity)
    {
        return (KK)emg.get().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }


    @Override
    @Transactional
    public TT getRowData(String rowKey)
    {
        return emg.get().find(facade.getEntityClass(), converter.convert(rowKey));
    }


    @Override
    @Transactional
    public List<TT> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy)
    {
        setRowCount(facade.count(filterBy));
        return facade.findRows(first, pageSize, filterBy, sortBy);
    }
}
