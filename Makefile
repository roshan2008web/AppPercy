NPM=node_modules/.bin
NPMDEPS=$(node_modules)

$(NPM): $(NPMDEPS)
	npm install

.PHONY: npm install clean serve test

install: $(NPM)

clean:
	rm -rf $$(cat .gitignore)

serve:
	mvn package
	java -cp target/example-percy-java-appium-1.0-SNAPSHOT.jar io.percy.examplepercyjavaappium.App

test-android: install
	$(NPM)/percy exec --  mvn compile exec:java -Dexec.mainClass="io.percy.examplepercyjavaappium.Android"

test-ios: install
	$(NPM)/percy exec --  mvn compile exec:java -Dexec.mainClass="io.percy.examplepercyjavaappium.Ios"
