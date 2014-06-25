/*
 * Copyright 2014 Andrej Petras.
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
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.lorislab.barn.db.model.DBApplication;
import org.lorislab.barn.db.model.DBApplication_;
import org.lorislab.jel.ejb.exception.ServiceException;
import org.lorislab.jel.ejb.services.AbstractEntityServiceBean;

/**
 *
 * @author Andrej Petras
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DBApplicationService extends AbstractEntityServiceBean<DBApplication> {

    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Gets the application by application name and version.
     *
     * @param name the application name.
     * @param release the application release.
     * @return the application.
     */
    public DBApplication getApplication(String name, String release) {
        DBApplication result = null;

        CriteriaBuilder cb = getBaseEAO().getCriteriaBuilder();
        CriteriaQuery<DBApplication> cq = getBaseEAO().createCriteriaQuery();
        Root<DBApplication> root = cq.from(DBApplication.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.equal(root.get(DBApplication_.name), name));
            predicates.add(cb.equal(root.get(DBApplication_.release), release));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        TypedQuery<DBApplication> query = getBaseEAO().createTypedQuery(cq);
        List<DBApplication> tmp = query.getResultList();

        if (tmp != null && !tmp.isEmpty()) {
            result = tmp.get(0);
        }

        return result;
    }

    /**
     * Saves the application.
     *
     * @param application
     * @return
     * @throws ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DBApplication saveApplication(DBApplication application) throws ServiceException {
        return save(application);
    }
}
