# Listen-up Take Home

Service for users from friends service and plays service

## How to run server
Open up terminal

Run plays service and friends
```
make install
make services
```
Run users service
```
make java
```

## Implementation Notes
Every time `/users` endpoint is requested, it makes a request to friends and plays services to get the most up to date list of users. A small optimization I had implemented was to cache the users in memory using a hash map so that if you had previously requested for a user either using `/users` or `/users/joe_example` then it will return cached result instead of re-fetching using the services.

If I had more time, I would have made optimizations so that it's not re-fetching from services when `/users` is requested. I might have looked into using a cache that expires after a fixed amount of time so that way it doesn't overload the other services, but it still gets new data after a time interval.
