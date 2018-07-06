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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

import com.sphenon.basics.context.CallContext;
import com.sphenon.basics.message.Message;
import com.sphenon.basics.message.MessageText;
import com.sphenon.basics.notification.Notifier;

public class LogLocationContext
{
    static public long getLevel (Class cl) {
        Log log = LogFactory.getLog(cl);
        long level = mapLevel( log );
        return level;
    }
  
    static private long mapLevel(Log log) {
        long level = 0;
        if( log.isDebugEnabled() )
            level |= Notifier.DIAGNOSTICS|Notifier.SELF_DIAGNOSTICS;
      
        if( log.isErrorEnabled()  )
            level |= Notifier.PRODUCTION|Notifier.CHECKPOINT;
    
        if( log.isInfoEnabled() )
            level |= Notifier.MONITORING|Notifier.OBSERVATION;
    
        if( log.isTraceEnabled() )
            level |= Notifier.DIAGNOSTICS|Notifier.SELF_DIAGNOSTICS|Notifier.VERBOSE|Notifier.MORE_VERBOSE|Notifier.MOST_VERBOSE|Notifier.MONITORING|Notifier.OBSERVATION|Notifier.PRODUCTION|Notifier.CHECKPOINT;
        
        if( log.isWarnEnabled() )
            level |= Notifier.DIAGNOSTICS;
      
        return level;
    }
}
