#!/bin/bash

PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG="`dirname "$PRG"`/$link"
  fi
done
DIRNAME=`dirname "$PRG"`
PROGNAME=`basename "$PRG"`

source $DIRNAME/upr.cfg

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi


# name
NAME="cli"

# configurration path:
UPR_CONFIG="$UPR_HOME/etc"

# ZIEOOK classpath
CLASSPATH="$UPR_HOME/lib/*"

# logger (should be one that logs to std out)
LOGS_OPTS="-DZIEOOK_HOME=$UPR_HOME -Dlogback.configurationFile=$UPR_HOME/etc/logback-$NAME.xml"
        
     # start zieook workflow engine:
eval "( \"$JAVA\" -classpath \"$CLASSPATH\"  $JAVA_OPTS $LOGS_OPTS nl.gridline.upr.client.UPR $@)"


