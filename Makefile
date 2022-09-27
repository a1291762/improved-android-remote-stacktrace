trace.jar:
	./gradlew compileReleaseSources
	cd build/intermediates/javac/release/classes && jar -cf ../../../../../trace.jar `find . -name '*.class'`
.PHONY: trace.jar
