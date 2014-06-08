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

package org.lorislab.barn.db.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.lorislab.barn.api.model.Application;
import org.lorislab.barn.api.model.Config;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The application.
 * 
 * @author Andrej Petras
 */
@Entity
@Table(name = "BARN_APPLICATION")
public class DBApplication extends Persistent implements Application {
    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1607783791524815752L;

    /**
     * The application name.
     */
    @Column(name = "C_NAME")
    private String name;
    
    /**
     * The application release name.
     */
    @Column(name = "C_RELEASE")
    private String release;
      
    /**
     * The application release date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_DATE")
    private Date date;
    
    /**
     * The set of configuration.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "application")
    private Set<DBConfig> configs = new HashSet<>();
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRelease() {
        return release;
    }

    @Override
    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public Set<? extends Config> getConfigs() {
        return configs;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
