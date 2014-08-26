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

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.lorislab.barn.api.model.Config;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The configuration model.
 *
 * @author Andrej Petras
 */
@Entity
@Table(name = "BARN_CONFIG")
public class DBConfig extends Persistent implements Config {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 6269308858836846322L;

    /**
     * The class name.
     */
    @Column(name = "C_TYPE")
    private String type;

    /**
     * The map of configuration attributes.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "C_NAME")
    @JoinColumn(name = "C_CONFIG")
    private final Map<String, DBAttribute> attributes = new HashMap<>();

    /**
     * Gets the type name.
     *
     * @return the type name.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Sets the type name.
     *
     * @param type the type name.
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the map of configuration attributes.
     *
     * @return the map of configuration attributes.
     */
    @Override
    public Map<String, DBAttribute> getAttributes() {
        return attributes;
    }

}
