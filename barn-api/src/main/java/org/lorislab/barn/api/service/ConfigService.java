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
 *
 * @author Andrej Petras
 * @param <C>
 * @param <A>
 */
public interface ConfigService<C extends Config, A extends Attribute> {
    
    public List<C> getAllConfig();
    
    public C saveConfig(C config);
             
    public C getConfigByType(String type);
    
    public C createConfig();
    
    public A createAttribute();
}
