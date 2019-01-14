#!/usr/bin/enb bash

mvn clean package -Dmaven.test.skip=true -U

docker build -t hub.c.163.com/job4ww/springcloud-order .

docker push hub.c.163.com/job4ww/springcloud-order