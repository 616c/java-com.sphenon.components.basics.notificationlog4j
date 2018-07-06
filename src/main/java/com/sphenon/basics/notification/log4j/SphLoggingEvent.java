package com.sphenon.basics.notification.log4j;

/****************************************************************************
  Copyright 2001-2018 Sphenon GmbH

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations
  under the License.
*****************************************************************************/

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class SphLoggingEvent extends LoggingEvent
{
    public SphLoggingEvent(String fqnOfLoggerClass, Logger logger, Level level, Object message, Throwable throwable) {
        super( fqnOfLoggerClass, logger, level, message, throwable );
    }
  
    public SphLoggingEvent(String fqnOfCategoryClass, Logger logger, long timeStamp, Level level, Object message, Throwable t ) {
        super( fqnOfCategoryClass, logger, timeStamp, level, message, t);
    }
  
    public LocationInfo getLocationInformation() {
        // we rely on the fact that fqnOfLoggerClass does not survive serialization
        return new SphLocationInfo( this.fqnOfCategoryClass );
    }

    static final long serialVersionUID = 8888;
}
