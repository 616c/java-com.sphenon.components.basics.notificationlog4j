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

import com.sphenon.basics.context.CallContext;
import com.sphenon.basics.context.Context;
import com.sphenon.basics.customary.CustomaryContext;
import com.sphenon.basics.notification.Notifier;

import java.io.StringWriter;
import java.io.PrintWriter;

public class Log {
    public static void trace (CallContext call_context, String msg) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        cc.sendTrace(call_context, msg);
    }
  
    public static void trace (CallContext call_context, String msg, Throwable t) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        String msg_ = msg + getStackTrace(t);
        cc.sendTrace(call_context, msg_);
    }
  
    public static void info (CallContext call_context, String msg) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        cc.sendInfo(call_context, msg);
    }
  
    public static void info (CallContext call_context, String msg, Throwable t) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        String msg_ = msg + getStackTrace(t);
        cc.sendInfo(call_context, msg_);
    }
  
    public static void warn (CallContext call_context, String msg) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        cc.sendWarning(call_context, msg);
    }
  
    public static void warn (CallContext call_context, String msg, Throwable t) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        String msg_ = msg + getStackTrace(t);
        cc.sendWarning(call_context, msg_);
    }
  
    public static void error (CallContext call_context, String msg) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        cc.sendError(call_context, msg);
    }
  
    public static void error (CallContext call_context, String msg, Throwable t) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        String msg_ = msg + getStackTrace(t);
        cc.sendError(call_context, msg_);
    }
  
    public static void debug (CallContext call_context, String msg) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        cc.sendNotice(call_context, msg);
    }
  
    public static void debug (CallContext call_context, String msg, Throwable t) {
        Context context = Context.create(call_context);
        CustomaryContext cc = CustomaryContext.create(context);
        String msg_ = msg + getStackTrace(t);
        cc.sendNotice(call_context, msg_);
    }
  
    public static boolean isDebug (CallContext context, long level) {
        if ((level & Notifier.DIAGNOSTICS) != 0 ||
            (level & Notifier.SELF_DIAGNOSTICS) != 0 ||
            (level & Notifier.VERBOSE) != 0 ||
            (level & Notifier.MORE_VERBOSE) != 0 ||
            (level & Notifier.CHECKPOINT) != 0 ||
            (level & Notifier.MOST_VERBOSE) != 0)
            return true;
        return false;
    }
  
    public static boolean isInfo (CallContext context, long level) {
        if ((level & Notifier.MONITORING) != 0 ||
            (level & Notifier.OBSERVATION) != 0)
            return true;
        return false;
    }
  
    public static boolean isWarn (CallContext context, long level) {
        if ((level & Notifier.DIAGNOSTICS) != 0 ||
            (level & Notifier.SELF_DIAGNOSTICS) != 0)
            return true;
        return false;
    }
  
    public static boolean isError (CallContext context, long level) {
        if ((level & Notifier.PRODUCTION) != 0)
            return true;
        return false;
    }
  
    public static boolean isTrace (CallContext context, long level) {
        if ((level & Notifier.VERBOSE) != 0 ||
            (level & Notifier.MORE_VERBOSE) != 0 ||
            (level & Notifier.MOST_VERBOSE) != 0)
            return true;
        return false;
    }
  
    private static String getStackTrace (Throwable t) {
        if (t == null) return "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String s = sw.toString() + "\n";
        pw.close();
        return s;
    }
}
