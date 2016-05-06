#!/bin/bash



echo "The following program will compute Sequential Disk Write Operations"

javac  DiskSeqWrite.java

java  DiskSeqWrite


echo "The following program will compute Random Write operations"

javac DiskRandWrite.java

java DiskRandWrite

echo "Please wait input file getting created for read Operations"

head -c 1G </dev/urandom >myfile.txt
head -c 50m </dev/urandom >myshortfile.txt

echo "The following program will compute Random read operations"

javac   DiskRandRead.java

java   DiskRandRead


echo "The following program will compute Sequential Disk Read Operations"

javac DiskSeqRead.java

java DiskSeqRead




