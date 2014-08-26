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
package org.lorislab.barn.standalone.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.barn.api.model.Config;
import org.lorislab.barn.api.service.ConfigurationService;
import org.lorislab.barn.api.service.ConfigurationStoreService;
import org.lorislab.barn.transformer.ModelTransformer;

/**
 * The configuration service.
 *
 * @author Andrej Petras
 */
@Singleton
@Local(ConfigurationService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationStandaloneServiceBean implements ConfigurationService {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ConfigurationStandaloneServiceBean.class.getName());

    /**
     * The cache.
     */
    private Map<Class, Object> cache = new HashMap<>();

    /**
     * The configuration model service.
     */
    @EJB
    private ConfigurationStoreService service;

    /**
     * After start method.
     */
    @PostConstruct
    public void start() {
        reload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        try {
            cache = new HashMap<>();

            List<Config> configs = service.getAllConfig();
            if (configs != null) {
                for (Config config : configs) {
                    Object tmp = ModelTransformer.createObject(config);
                    cache.put(tmp.getClass(), tmp);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reloading the the configuration from store!", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T setConfiguration(T data) {
        T result = data;
        if (data != null) {
            Class clazz = data.getClass();
            
            try {
                Set<String> names = ModelTransformer.getFieldNames(clazz);
                Config config = service.getConfigByType(data.getClass().getName(), names);                                           
                ModelTransformer.updateModel(config.getAttributes(), data);
                service.saveConfig(config);
                
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error save the configuration model " + data.getClass().getName(), ex);
            }
        
            cache.put(clazz, result);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getConfiguration(Class<T> clazz) {
        T result = (T) cache.get(clazz);
        if (result == null) {
            result = (T) ModelTransformer.createInstance(clazz.getName());
            result = setConfiguration(result);
        }
        return result;
    }

}
