/*
 * Copyright 2013 lorislab.org.
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
package org.lorislab.barn.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.lorislab.barn.api.model.Attribute;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The configuration model attribute.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "BARN_ATTRIBUTE")
public class DBAttribute extends Persistent implements Attribute {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8218977162091953419L;

    /**
     * The name.
     */
    @Column(name = "C_NAME")
    private String name;

    /**
     * The value.
     */
    @Column(name = "C_VALUE")
    private String value;

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the value.
     */
    public void setValue(String value) {
        this.value = value;
    }

}
