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
package org.lorislab.barn.api.service;

import java.util.List;
import java.util.Set;
import org.lorislab.barn.api.model.Config;

/**
 * The configuration store service.
 *
 * @author Andrej Petras
 */
public interface ConfigurationStoreService {

    /**
     * Gets all configuration for the application and version.
     *
     * @param application the application.
     * @param version the version.
     * @return the list of configuration.
     * @throws java.lang.Exception if the method fails.
     */
    public List<Config> getAllConfig(String application, String version) throws Exception;

    /**
     * Saves the configuration.
     *
     * @param config the configuration.
     * @return the saved configuration.
     * @throws java.lang.Exception if the method fails.
     */
    public Config saveConfig(Config config) throws Exception;

    /**
     * Gets the configuration by type or create new configuration.
     *
     * @param application the application.
     * @param release the version.
     * @param type the configuration class.
     * @param attributes the set of attributes.
     * @return the configuration.
     * @throws java.lang.Exception if the method fails.
     */
    public Config getConfigByType(String application, String release, String type, Set<String> attributes) throws Exception;

}
