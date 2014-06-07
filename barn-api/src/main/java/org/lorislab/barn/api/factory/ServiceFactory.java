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
import org.lorislab.barn.api.service.AttributeAdapterService;
import org.lorislab.barn.api.service.ConfigService;

/**
 *
 * @author Andrej Petras
 */
public class ServiceFactory {
    
    private static AttributeAdapterService ATTR_ADAPTER_SERVICE;
    
    static {       
        ServiceLoader<AttributeAdapterService> loader2 = ServiceLoader.load(AttributeAdapterService.class);
        Iterator<AttributeAdapterService> iter2 = loader2.iterator();
        if (iter2.hasNext()) {
            ATTR_ADAPTER_SERVICE = iter2.next();
        }        
    }
    
    public static AttributeAdapterService getAttributeAdapterService() {
        return ATTR_ADAPTER_SERVICE;
    }
}
