.PHONY: friends plays services install clean python java

VIRTUALENV = $(shell which virtualenv)

ifeq ($(strip $(VIRTUALENV)),)
  VIRTUALENV = /usr/local/python/bin/virtualenv
endif

clean: cleanup
	rm -rf venv
	rm -rf listenup.egg-info

venv:
	$(VIRTUALENV) venv

install: clean venv
	. venv/bin/activate; pip install -r requirements.txt
	. venv/bin/activate; python setup.py develop

friends: venv
	. venv/bin/activate; python services/friends.py

plays: venv
	. venv/bin/activate; python services/plays.py

cleanup:
	ps aux | grep listenup | grep -v grep | grep -v PyCharm | awk '{print $$2}' | xargs kill

services: venv cleanup
	. venv/bin/activate; python services/friends.py &
	. venv/bin/activate; python services/plays.py &

python:
	. venv/bin/activate; python python/solution/api.py
java:
	mvn clean install -f ./java/pom.xml
	java -jar ./java/listenup-solution-server/target/listenup-solution-server-1.0.0-SNAPSHOT.jar
