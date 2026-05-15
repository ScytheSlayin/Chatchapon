all: build

build: 
	javac *.java;

server: build 
	java GameServer

client: build
	java NewMenu

clean: 
	rm -f *.class
