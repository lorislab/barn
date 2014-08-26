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

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.barn.api.model.Config;
import org.lorislab.barn.api.service.ConfigurationStoreService;
import org.lorislab.barn.db.model.DBAttribute;
import org.lorislab.barn.db.model.DBConfig;

/**
 * The DB configuration store service.
 *
 * @author Andrej Petras
 */
@Stateless
@Local(ConfigurationStoreService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DBConfigurationStoreService implements ConfigurationStoreService {

    /**
     * The configuration service.
     */
    @EJB
    private DBConfigService configService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List getAllConfig() throws Exception {
        return configService.getAllConfig();
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public Config saveConfig(Config config) throws Exception {
        Config result = null;
        if (config instanceof DBConfig) {
            result = configService.saveConfig((DBConfig) config);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public Config getConfigByType(String type, Set<String> attributes) throws Exception {
        DBConfig result = configService.getConfigByType(type);
        
        // create new configuration
        if (result == null) {
            result = new DBConfig();
            result.setType(type);        
        }
        
        // check the attributes
        if (attributes != null) {
            Map<String, DBAttribute> map = result.getAttributes();
            for (String name : attributes) {
                DBAttribute attr = map.get(name);
                if (attr == null) {
                    attr = new DBAttribute();
                    attr.setName(name);
                    map.put(name, attr);
                }
            }
        }
        
        return result;
    }

}
