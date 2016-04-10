#!/bin/bash

echo "Starting VES"
echo -e "\n"

cd /glassfish4/bin

servicestart=$(./asadmin start-domain)

echo $servicestart
echo -e "\n"

configresult=$(curl -H "Content-Type: application/json" -X PUT -d '{"storage":"/storage", "database":""}' http://localhost:8080/VES/api/cfg/direct)

echo $configresult
echo -e "\n"

echo "VES start conclused"




