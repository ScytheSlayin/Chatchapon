all: build

build: 
	javac *.java;

server: build 
	java GameServer

client: build
	java NewMenu

run: client

clean: 
	rm -f *.class
