test:
	mkdir -p out
	find . | grep java > sources.txt
	javac @sources.txt -sourcepath src -d out -cp ./libs/junit.jar:./libs/hamcrest-core.jar:./out
	java -cp ./libs/junit.jar:./libs/hamcrest-core.jar:./out org.junit.runner.JUnitCore LinkedListTest
