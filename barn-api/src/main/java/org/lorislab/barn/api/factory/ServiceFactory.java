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

package org.lorislab.barn.api.factory;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.lorislab.barn.api.service.ApplicationService;

/**
 *
 * @author Andrej Petras
 */
public final class ServiceFactory {
    
    private static ApplicationService APP_SERVICE;
    
    static {                   
        ServiceLoader<ApplicationService> loader = ServiceLoader.load(ApplicationService.class);
        Iterator<ApplicationService> iter = loader.iterator();
        if (iter.hasNext()) {
            APP_SERVICE = iter.next();
        }         
    }
    
    private ServiceFactory() {
        // empty constructor
    }
    
    public static ApplicationService getApplicationService() {
        return APP_SERVICE;
    }   
}
