#!/bin/bash

for i in {10..22} ; do
	out="tests/$i.ma"
	cout="tests/$i.a"
	inp="tests/$i"
	java process_packages < $inp > $out
	my="$(md5sum < $out)"
	correct="$(md5sum < $cout)"
	if [ "$my" != "$correct" ]; then
		echo "$i"
		#echo "$correct"
	fi
done
