#!/bin/bash

docker run --restart always -d -p 36379:6379 --name redis redis
