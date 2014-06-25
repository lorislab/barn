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

package org.lorislab.barn.api.util;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.barn.api.factory.ServiceFactory;
import org.lorislab.barn.api.model.Attribute;
import org.lorislab.barn.api.model.Config;

/**
 * The model utility class.
 * 
 * @author Andrej Petras
 */
public class ModelUtil {
    
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ModelUtil.class.getName());
    
    /**
     * Creates the configuration object from the model.
     *
     * @param config the configuration model.
     * @return the corresponding configuration object.
     */
    public static Object createObject(Config config) {
        Object result = createInstance(config.getType());
        if (result != null) {
            if (config.getAttributes() != null) {
                for (Attribute attribute : config.getAttributes().values()) {
                    setAttributeToObject(result, attribute);
                }
            }
        }
        return result;
    }
    
    /**
     * Creates object instance by class name.
     *
     * @param name the class name.
     * @return the corresponding object instance.
     */
    public static Object createInstance(String name) {
        Object result = null;
        try {
            Class clazz = Class.forName(name);
            result = clazz.newInstance();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Sets the attribute to the configuration object.
     *
     * @param <T> the configuration object type.
     * @param object the configuration object.
     * @param attribute the attribute.
     */
    public static <T> void setAttributeToObject(T object, Attribute attribute) {
        try {
            String tmp = attribute.getValue();

            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(attribute.getName());
            
            Object value = ServiceFactory.getAttributeAdapterService().readValue(tmp, field.getType());

            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                field.set(object, value);
            } finally {
                field.setAccessible(accessible);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }    

    /**
     * Updates the attribute in the configuration model by configuration object
     * field.
     *
     * @param object the object.
     * @param attribute the attribute.
     * @param field the field.
     */
    public static void updateAttribute(Object object, Attribute attribute, Field field) {
        if (attribute != null) {
            try {
                boolean accessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    String tmp = ServiceFactory.getAttributeAdapterService().writeValue(value, field.getType());
                    attribute.setValue(tmp);
                } finally {
                    field.setAccessible(accessible);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Creates the attribute for the configuration object field.
     *
     * @param object the object.
     * @param field the field.
     * @param attribute the attribute.
     * @return the corresponding attribute.
     */
    public static Attribute createAttribute(final Attribute attribute, final Object object, Field field) {        
        try {
            attribute.setName(field.getName());
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                String tmp = ServiceFactory.getAttributeAdapterService().writeValue(value, field.getType());
                attribute.setValue(tmp);
            } finally {
                field.setAccessible(accessible);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return attribute;
    }
    

    

    
    
    
}
