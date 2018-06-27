#!/bin/bash

echo -e "\n========================"
java -jar $*

#title=`grep "<title" examples/index.html  | sed -r "s/[ ]*<\/?[^>]*>//g"`
#
#echo "TITLE: $title"
#
#window_id=`xdotool search --name "$title"`
#
#echo "ID: $window_id"
#
#xdotool key --window $window_id 'CTRL+r'
