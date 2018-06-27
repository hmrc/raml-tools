#!/bin/bash

this_dir=`dirname $0`

file=`ls ${this_dir}/target/scala-2.11/raml-tools-*.jar`

if [[ $? != 0 ]]
then
    pushd $this_dir
    sbt clean assembly
    file=`ls ${this_dir}/target/scala-2.11/raml-tools-*.jar`
    popd
fi

which entr

if [[ $? == 0 ]]
then
    find . -name "*" | entr ${this_dir}/run.sh $file $*
else
    ${this_dir}/run.sh $file $*
fi
