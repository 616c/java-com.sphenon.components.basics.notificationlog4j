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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

import com.sphenon.basics.context.CallContext;
import com.sphenon.basics.message.Message;
import com.sphenon.basics.message.MessageText;
import com.sphenon.basics.notification.Notifier;

public class NotifierLog4j extends Notifier
{
    public  static Notifier
        create( CallContext call_context )
    {
        return new NotifierLog4j( call_context );  
    }
  
    public static Notifier
        create( CallContext call_context, Properties props )
    {
        return new NotifierLog4j( call_context, props );
    }
  
    public NotifierLog4j( CallContext ctx  )
    {
    }
  
    public NotifierLog4j( CallContext ctx, Properties props  )
    {
        this.setProperties(ctx, props);
    }
  
    public void 
        send(CallContext call_context, Message msg, int reliability, long lifetime,  long level)
    {
        StackTraceElement ste = this.getFittingStackElement();
        SphLocationInfo li = null;
        String text = msg.getMessageText(call_context).getText(call_context);
        boolean output_done = false;
    
        if( ste == null )
            li = new SphLocationInfo("NotifierLog4j.java", "com.sphenon.basics.notification.log4j.NotifierLog4j", "send", "0" );
        else
            li = new SphLocationInfo( ste.getFileName(), ste.getClassName(), ste.getMethodName(), String.valueOf(ste.getLineNumber()) );
    

        if( com.sphenon.basics.notification.log4j.Log.isInfo( call_context, level ) && !output_done  )
            output_done = this.info(li, text);
    

        if( (com.sphenon.basics.notification.log4j.Log.isDebug( call_context, level ) || 
             com.sphenon.basics.notification.log4j.Log.isTrace( call_context, level )) && !output_done )
            output_done = this.debug( li, text );
    

        if( com.sphenon.basics.notification.log4j.Log.isWarn( call_context, level ) && !output_done )
            output_done = this.warn(li, text);
    

        if( com.sphenon.basics.notification.log4j.Log.isError( call_context, level ) && !output_done )
            output_done = this.error(li, text);
    }
  
    private Properties
        loadProperties( String name ) throws IOException
    {
        return null;
    }
  
    private StackTraceElement
        getFittingStackElement()
    {
        StackTraceElement[] st  = new Throwable().getStackTrace();
        StackTraceElement ste = null;
    
        for( int i = 0; i <st.length; i++ )
            {
                ste = st[i];
                if( lm.exists(ste.getClassName()) != null )
                    return ste;
            }
        return null;
    }
  
    private boolean
        debug( SphLocationInfo li, String msg )
    {
        Logger l = this.getClassLogger(li);
        if( l.isEnabledFor(Level.DEBUG) && Level.DEBUG.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.DEBUG, msg, null, li );
                return true;
            }
        return false;
    }
  
    private boolean
        info( SphLocationInfo li, String msg )
    {
        Logger l = this.getClassLogger(li);
        if( l.isEnabledFor(Level.INFO) && Level.INFO.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.INFO, msg, null, li );
                return true;
            }
        return false;
    }
  
    private boolean
        warn( SphLocationInfo li, String msg )
    {
        Logger l = this.getClassLogger(li);
        if( l.isEnabledFor(Level.WARN) && Level.WARN.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.WARN, msg, null, li );
                return true;
            }
        return false;
    }
  
    private boolean
        error( SphLocationInfo li, String msg )
    {
        Logger l = this.getClassLogger(li);
        if( l.isEnabledFor(Level.ERROR) && Level.ERROR.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.ERROR, msg, null, li );
                return true;
            }
        return false;
    }
  
    private void 
        forcedLog(Logger l, Level level, Object message, Throwable t, SphLocationInfo li ) 
    {
        String s = li.getFullInfo();
        LoggingEvent lev = new LoggingEvent(li.getFullInfo(), l, level, message, t);
        String ss = lev.getLocationInformation().getClassName();
        l.callAppenders(new SphLoggingEvent(li.getFullInfo(), l, level, message, t));
    }
  
    private Logger
        getClassLogger( SphLocationInfo li )
    {
        Logger l = this.lm.exists(li.getClassName());
        if( l == null )
            l = this.lm.getLogger(li.getClassName());
        return l;
    }
  
    private void
        setProperties( CallContext ctx, Properties props )
    {
        PropertyConfigurator pc = new PropertyConfigurator();
        pc.configure(props);
        this.properties = props;
        this.pc = pc;
    }
  
    public Properties
        getProperties( CallContext ctx )
    {
        return this.properties;
    }
  
    private void
        sendMessage2Log4j( CallContext ctx, SphLocationInfo li, String msg )
    {
        Logger l = this.getClassLogger(li);
        if( l.isEnabledFor(Level.INFO) && Level.INFO.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.INFO, msg, null, li );
                return;
            }
        if( l.isEnabledFor(Level.WARN) && Level.WARN.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.WARN, msg, null, li );
                return;
            }
        if( l.isEnabledFor(Level.ERROR) && Level.ERROR.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.ERROR, msg, null, li );
                return;
            }
        if( l.isEnabledFor(Level.DEBUG) && Level.DEBUG.isGreaterOrEqual(l.getEffectiveLevel()) )
            {
                this.forcedLog( l, Level.DEBUG, msg, null, li );
                return;
            }
    }

    PropertyConfigurator pc = null;
    LogManager lm = new LogManager();
    Properties properties = null;
  
    public static int MODE_FOREIGN = 1;
    public static int MODE_OWN = 2;
}
