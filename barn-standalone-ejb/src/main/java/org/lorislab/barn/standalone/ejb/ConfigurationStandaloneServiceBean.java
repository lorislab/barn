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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.barn.api.factory.ServiceFactory;
import org.lorislab.barn.api.model.Attribute;
import org.lorislab.barn.api.model.Config;
import org.lorislab.barn.api.service.ApplicationService;
import org.lorislab.barn.api.service.ConfigurationStoreService;
import org.lorislab.barn.api.service.ConfigurationService;
import org.lorislab.barn.api.util.ModelUtil;

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
     * The application.
     */
    private String application;

    /**
     * The version.
     */
    private String version;

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

            ApplicationService appService = ServiceFactory.getApplicationService();
            if (appService != null) {
                application = appService.getApplication();
                version = appService.getVersion();
            }
            List<Config> configs = service.getAllConfig(application, version);
            if (configs != null) {
                for (Config config : configs) {
                    Object tmp = ModelUtil.createObject(config);
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
        T result = null;
        if (data != null) {
            Class clazz = data.getClass();
            result = (T) saveConfiguration(data);
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
            result = (T) ModelUtil.createInstance(clazz.getName());
            result = setConfiguration(result);
        }
        return result;
    }

    /**
     * Saves the configuration object.
     *
     * @param <T> the type.
     * @param data the object.
     * @return the saved configuration object.
     */
    private <T> T saveConfiguration(T data) {
        try {
            Class clazz = data.getClass();
            Config config = service.getConfigByType(application, version, clazz.getName());
            if (config == null) {
                config = service.createConfig(application, version, clazz.getName());
            }

            Map attributes = config.getAttributes();

            Field[] fields = clazz.getDeclaredFields();
            if (fields != null) {

                for (Field field : fields) {

                    Attribute attr = (Attribute) attributes.get(field.getName());
                    if (attr == null) {
                        attr = service.createAttribute();
                        attr = ModelUtil.createAttribute(attr, data, field);
                        attributes.put(field.getName(), attr);
                    } else {
                        ModelUtil.updateAttribute(data, attr, field);
                    }
                }
            }

            service.saveConfig(config);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error save the configuration model " + data.getClass().getName(), ex);
        }
        return data;
    }
}
