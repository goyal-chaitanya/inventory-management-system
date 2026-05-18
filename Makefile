JAVAC = javac
JAVA = java
SRC_DIR = src
BUILD_DIR = out
MAIN_CLASS = App

.PHONY: all run demo clean

all:
	mkdir -p $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) $(SRC_DIR)/*.java

run: all
	$(JAVA) -cp $(BUILD_DIR) $(MAIN_CLASS)

demo: all
	printf "1\n3\nChaitanya\nP100\n3\nP102\n7\nDONE\n4\n5\n1\n6\n0\n" | $(JAVA) -cp $(BUILD_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BUILD_DIR)
