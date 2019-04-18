#!/bin/bash

files=`ls | grep "\.a"`
#echo $files
for file in $files; do
	tr -d '\15\32' < $file > "${file}2"
done