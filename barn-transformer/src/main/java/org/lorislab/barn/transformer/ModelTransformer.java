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
package org.lorislab.barn.transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.lorislab.barn.api.model.Attribute;
import org.lorislab.barn.api.model.Config;
import org.lorislab.transformer.api.Transformer;

/**
 * The model transformer class.
 *
 * @author Andrej Petras
 */
public final class ModelTransformer {

    /**
     * The default constructor
     */
    private ModelTransformer() {
        // empty contrustor
    }

    /**
     * Gets the list of fields for the class.
     *
     * @param clazz the class.
     * @return the corresponding list of fields.
     */
    public static Set<String> getFieldNames(Class clazz) {
        return Transformer.getFieldNames(clazz);
    }

    /**
     * Creates object instance by class name.
     *
     * @param name the class name.
     * @return the corresponding object instance.
     */
    public static Object createInstance(String name) {
        return Transformer.createInstance(name);
    }
    
    /**
     * Creates the configuration object from the model.
     *
     * @param config the configuration model.
     * @return the corresponding configuration object.
     */
    public static Object createObject(Config config) {
        Object result = null;
        if (config.getAttributes() != null) {
            Map<String, String> tmp = new HashMap<>();
            for (Attribute attribute : config.getAttributes().values()) {
                tmp.put(attribute.getName(), attribute.getValue());
            }
            result = Transformer.transform(tmp, config.getType());
        }
        return result;
    }

    /**
     * Updates the model.
     *
     * @param <T> the data.
     * @param map the map of attributes.
     * @param data the data.
     * @return the corresponding change data.
     */
    public static <T> T updateModel(final Map<String, ? extends Attribute> map, final T data) {
        Map<String,String> tmp = Transformer.transform(data);
        if (tmp != null && map != null) {
            for (Entry<String,String> entry : tmp.entrySet()) {
                
                Attribute attr = map.get(entry.getKey());
                if (attr != null) {
                    attr.setValue(entry.getValue());
                }
            }
        }
        return data;
    }

}
