#!/bin/bash

# (almost) standard gridline start / stop

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

# translate to standard values:

# application name:
NAME="upr"
# application home:
APP_HOME="$UPR_HOME"
# server port:
APP_PORT="$UPR_PORT"
APP_HOST="$UPR_HOST"


# check values & start the service:

# check if APP_HOME is defined:
if [ -n "${APP_HOME+x}" ]; then
    echo "'$NAME' APP_HOME set to '$APP_HOME'"
else
    echo "APP_HOME not set, failed"
    exit -1
fi

# cd to the home directory of zieook:
cd $APP_HOME

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi

if [ ! -n $APP_PORT ]; then
	APP_PORT="20100"
	echo "PORT not set using default: 20100"
fi


# configurration path:
CONFIGURATION="$APP_HOME/etc"
# logback configuration:
LOGS_OPTS="-DAPP_HOME=$APP_HOME -Dlogback.configurationFile=$CONFIGURATION/logback-$NAME.xml"
# ZIEOOK classpath
CLASSPATH="$APP_HOME/lib/*"
# PID file location:
APP_PID="$APP_HOME/state/$NAME.pid"
# WEBAPP OPTS:
WEBAPP="-n $NAME -d "$APP_HOME/webapps/$NAME" -l $APP_HOST -p $APP_PORT"
# output
OUTPUT="$APP_HOME/log/$NAME.out"

if [ $# == 1 ];
then
	if [ "$1" = "stop" ];
	then
		if [ -e $APP_PID ];
		then
			kill `cat $APP_PID`
			rm -f $APP_PID
		else
			echo $APP_PID "could not be found."
		fi
	elif [ "$1" = "start" ]
	then
	    if [ -e $APP_PID ];
	    then
	        echo "$NAME is already running"
	    else
	        echo "========================================================================="
	        echo " starting: $NAME at $HOST:$PORT"
        	echo "  APP_HOME: $APP_HOME"
        	echo "  JAVA: $JAVA"
        	echo "  JAVA_OPTS: $JAVA_OPTS"
        	echo "  CLASSPATH: $CLASSPATH"
        	echo "========================================================================="
        	echo ""
                 
             # start zieook workflow engine:
		echo "start command: (\"$JAVA\" -classpath \"$CLASSPATH\" $JAVA_OPTS $LOGS_OPTS nl.gridline.jettyembedder.WebAppServer $WEBAPP"
        	eval "(\"$JAVA\" -classpath \"$CLASSPATH\"  $JAVA_OPTS $LOGS_OPTS \
		        nl.gridline.jettyembedder.WebAppServer $WEBAPP  >> $OUTPUT 2>&1) &"
        	if [ ! -z "$APP_PID" ]; then
    		    echo $! > $APP_PID
        	fi    
	    fi
	elif [ "$1" = "status" ] 
	then
	    if [ -e $APP_PID ];
	    then
	        echo "$NAME is running"
	    else
	        echo "$NAME is not running"
	    fi
	else
	    echo "Usage: $0 [start|stop|status]" 
	fi
else
	echo "Usage: $0 [start|stop|status]"
fi
