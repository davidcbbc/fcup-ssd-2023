.PHONY: clean build run

default: build

clean:
	mvn clean

build:
	mvn compile

run:
	mvn exec:java

test:
	mvn test

package:
	mvn package
