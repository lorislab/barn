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

/**
 * The configuration service.
 *
 * @author Andrej Petras
 */
public interface ConfigurationService {

    /**
     * Reloads the configuration.
     */
    public void reload();

    /**
     * Sets the configuration data.
     *
     * @param <T> the configuration type.
     * @param data the configuration data.
     * @return the corresponding configuration.
     */
    public <T> T setConfiguration(T data);

    /**
     * Gets the configuration data.
     *
     * @param <T> the configuration type.
     * @param clazz the configuration data.
     * @return the corresponding configuration.
     */
    public <T> T getConfiguration(Class<T> clazz);
}
