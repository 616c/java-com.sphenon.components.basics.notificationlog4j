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

import org.apache.log4j.spi.LocationInfo;

public class SphLocationInfo extends LocationInfo {
    public SphLocationInfo(String file, String cl, String method, String ln) {
        super(null, null);
        this.fileName = file;
        this.className = cl;
        this.methodName = method;
        this.lineNumber = ln;
    }
  
    public SphLocationInfo(String fqnOfClass) {
        super(null, null);
        this.setFn(fqnOfClass);
        this.setCn(fqnOfClass);
        this.setLn(fqnOfClass);
        this.setMn(fqnOfClass);
    }
  
    public String getClassName () {
        return this.className;
    }
  
    public void setClassName (String className) {
        this.className = className;
    }
  
    public String getFileName () {
        return this.fileName;
    }
  
    public void setFileName (String fileName) {
        this.fileName = fileName;
    }
  
    public String getLineNumber () {
        return this.lineNumber;
    }
  
    public void setLineNumber (String linenNumber) {
        this.lineNumber = linenNumber;
    }
  
    public String getMethodName () {
        return this.methodName;
    }
  
    public void setMethodName (String methodName) {
        this.methodName = methodName;
    }
  
    public String getFullInfo () {
        if (fullInfo == null) {
            fullInfo = getClassName()+"."+getMethodName()+"("+getFileName()+":"+
                getLineNumber()+")";
        }
        super.fullInfo =fullInfo;
        return fullInfo;
    }
  
    private void setFn (String fqn) {
        if (fqn == null) {
            this.fileName = "";
        } 
        else {
            int iend = fqn.lastIndexOf(':');

            if (iend == -1) {
                this.fileName = "";
            } 
            else {
                int ibegin = fqn.lastIndexOf('(', iend - 1);
                this.fileName = fqn.substring(ibegin + 1, iend);
            }
        }
    }
  
    private void setCn (String fqn) {
        if (fqn == null) {
            this.className = "";
            return;
        }

        // Starting the search from '(' is safer because there is
        // potentially a dot between the parentheses.
        int iend = fqn.lastIndexOf('(');

        if (iend == -1) {
            this.className = "";
        } 
        else {
            iend = fqn.lastIndexOf('.', iend);
            int ibegin = 0;

            if (iend == -1) {
                this.className = LocationInfo.NA;
            } 
            else {
                this.className = fqn.substring(ibegin, iend);
            }
        }
    }
  
    private void setLn (String fqn) {
        if (fqn == null) {
            this.lineNumber = "";
        }
        else {
            int iend = fqn.lastIndexOf(')');
            int ibegin = fqn.lastIndexOf(':', iend - 1);

            if (ibegin == -1) {
                this.lineNumber = "";
            } 
            else {
                this.lineNumber = fqn.substring(ibegin + 1, iend);
            }
        }
    }
  
    private void setMn (String fqn) {
        if (fqn == null) {
            this.methodName = "";
        } 
        else {
            int iend = fqn.lastIndexOf('(');
            int ibegin = fqn.lastIndexOf('.', iend);

            if (ibegin == -1) {
                this.methodName = "";
            } 
            else {
                this.methodName = fqn.substring(ibegin + 1, iend);
            }
        }
    }
  
    String fileName = "";
    String className = "";
    String methodName = "";
    String lineNumber = "";
  
    static final long serialVersionUID = 8888;
}
