#!/bin/bash

files=("04" "05" "20" "36" "83")
for file in "${files[@]}"; do
	echo $file
	java SetRangeSum < $file > "${file}.ma"
	correct=`md5sum "${file}.a2"`
	myans=`md5sum "${file}.ma"`
	echo $correct
	echo $myans
done