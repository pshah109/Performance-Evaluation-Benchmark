#!/bin/bash

echo "The Following Program Will compute CPU FLOPS and IOPS"

javac CpuOperations.java

java CpuOperations

echo "=========================================================="


echo "The Following Program Will Obtain 600 Samples for FLOPS and IOPS"

echo ""

echo ""

javac CpuOperationSamples.java

java CpuOperationSamples
