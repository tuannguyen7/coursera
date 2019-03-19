#!/bin/bash
for i in {10..54} ; do
	out="$i.ma"
	cout="$i.a"
	java check_brackets < $i > $out
	my="$(md5sum < $out)"
	correct="$(md5sum < $cout)"
	if [ "$my" != "$correct" ]; then
		echo "$my"
		echo "$correct"
	fi
done
