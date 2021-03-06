PACKAGE = cs455/scaling/client/
PACKAGE2 = cs455/scaling/server/

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = ${PACKAGE2}*.java ${PACKAGE}*.java

default: classes

all: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) ${PACKAGE2}*.class ${PACKAGE}*.class
