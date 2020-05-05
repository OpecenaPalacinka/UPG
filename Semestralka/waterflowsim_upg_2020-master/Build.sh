#!/bin/bash
mkdir ./bin
javac -cp ./src:./WaterFlowSim.jar;.\jfreechart-1.5.0.jar -encoding UTF-8 -d ./bin src/*.java
