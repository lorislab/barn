/*
 * Copyright 2013 lorislab.org.
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
package org.lorislab.barn.db.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.barn.api.service.ConfigService;
import org.lorislab.barn.db.model.DBAttribute;
import org.lorislab.barn.db.model.DBConfig;
import org.lorislab.barn.db.model.DBConfig_;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 * The configuration model service.
 *
 * @author Andrej Petras
 */
@Stateless
@Local(ConfigService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigServiceBean extends AbstractEntityServiceBean<DBConfig> implements ConfigService<DBConfig, DBAttribute> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 3429620108065122167L;

    /**
     * Gets all configuration models.
     *
     * @return the list of all configuration models.
     */
    @Override
    public List<DBConfig> getAllConfig() {
        return this.getAll();
    }

    /**
     * Saves the configuration model.
     *
     * @param config the configuration model.
     * @return the saved configuration model.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public DBConfig saveConfig(DBConfig config) {
        return this.save(config);
    }

    /**
     * Gets the configuration model by type.
     *
     * @param type the type.
     * @return the corresponding configuration model to the type.
     */
    @Override
    public DBConfig getConfigByType(String type) {
        DBConfig result = null;

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<DBConfig> cq = getBaseEAO().createCriteriaQuery();
        Root<DBConfig> root = cq.from(DBConfig.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(DBConfig_.type), type));

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        TypedQuery<DBConfig> query = getBaseEAO().createTypedQuery(cq);
        List<DBConfig> g = query.getResultList();
        if (g != null && !g.isEmpty()) {
            result = g.get(0);
        }

        return result;
    }

    @Override
    public DBConfig createConfig() {
        return new DBConfig();
    }

    @Override
    public DBAttribute createAttribute() {
        return new DBAttribute();
    }

}
