#!/bin/bash
javadoc -encoding UTF-8 -sourcepath ./src -cp ./src:./WaterFlowSim.jar -d doc/javadoc -version -author ./src/*.java
