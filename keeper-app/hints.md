# manual testing request
```bash
curl -i \
    -H "content-type: text/plain;charset=UTF-8" \
    -X POST \
    -d "some data" http://localhost:8080/keeper/item

```

# docker hints
```bash
# build and tag
docker build -t keeper-app:1.1 .
docker tag keeper-app:1.1 keeper-app:latest

# run
docker run -d -p 8080:8080 --name keeper-app --network=keeper_backing-services keeper-app:1.1

# stop and remove
docker stop keeper-app
docker rm keeper-app


# remove untagged
docker rmi $(docker images | grep "^<none>" | awk "{print $3}")
#or
docker rmi $(docker images | grep "^<none>" | tr -s " " | cut -d' ' -f3 | tr '\n' ' ')

# clean up
docker system prune

```


