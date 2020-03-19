#!/bin/sh

THE_CLASSPATH=/bin
PROGRAM_NAME=main.java
cd src
#for i in `ls /Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/Contents/Home/jre/lib/*.jar`
for i in `ls ../lib/*.jar`
    do
    THE_CLASSPATH=${THE_CLASSPATH}:${i}
done

javac -classpath ".:${THE_CLASSPATH}" $PROGRAM_NAME

if [ $? -eq 0 ]
then
    echo "compile worked!"
fi

java Main
