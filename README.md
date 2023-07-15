# shorten-url

This api is to create short url to replace original long url.

There are two options of method to create short url.

1. Use SecureRandom of Java, encode it with Base62.
2. Use Timestamp and server ip address, encode it with Base62.