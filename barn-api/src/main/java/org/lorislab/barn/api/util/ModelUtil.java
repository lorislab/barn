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
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
     * Gets the list of fields for the class.
     *
     * @param clazz the class.
     * @return the corresponding list of fields.
     */
    public static Set<String> getFieldNames(Class clazz) {
        Set<String> result = new HashSet<>();
        if (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        result.add(field.getName());
                    }
                }
            }
        }
        return result;
    }

    /**
     * Gets the list of fields for the class.
     *
     * @param clazz the class.
     * @return the corresponding list of fields.
     */
    public static Set<Field> getFields(Class clazz) {
        Set<Field> result = new HashSet<>();
        if (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        result.add(field);
                    }
                }
            }
        }
        return result;
    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
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
    private static <T> void setAttributeToObject(T object, Attribute attribute) {
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
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
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
    private static void updateAttribute(Object object, Attribute attribute, Field field) {
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
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
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

        Set<Field> fields = getFields(data.getClass());
        if (fields != null && map != null) {
            for (Field field : fields) {
                Attribute attr = map.get(field.getName());
                if (attr != null) {
                    updateAttribute(data, attr, field);
                }
            }
        }
        return data;
    }

}
