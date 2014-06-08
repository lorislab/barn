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

package org.lorislab.barn.api.model;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Andrej Petras
 */
public interface Application {
    
    public String getName();
    
    public void setName(String name);
    
    public Date getDate();
    
    public void setDate(Date date);
    
    public String getRelease();
    
    public void setRelease(String release);
    
    public Set<? extends Config> getConfigs();
}
