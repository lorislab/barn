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
import org.lorislab.barn.api.model.Attribute;
import org.lorislab.barn.api.model.Config;

/**
 * The configuration store service.
 * 
 * @author Andrej Petras
 */
public interface ConfigurationStoreService {

    public List<Config> getAllConfig(String application, String version) throws Exception;
    
    public Config saveConfig(Config config) throws Exception;
             
    public Config getConfigByType(String application, String version, String type) throws Exception;
    
    public Config createConfig(String application, String version, String type) throws Exception;
    
    public Attribute createAttribute() throws Exception;

}
