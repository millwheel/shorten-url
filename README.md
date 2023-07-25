# shorten-url

This api is to create short url to replace original long url.

### example
If given url is "https://www.thisissamplehttpurl.com/this/is/fake/url".

The system creates short id such as "8pjjNX9V" which is consist of alphanumeric and returns it to client.

if client requests redirection with short id "8pjjNX9V", it goes to original long url "https://www.thisissamplehttpurl.com/this/is/fake/url" redirected by the server.
### method to create short id

There are two options of method to create short url.

1. Use SecureRandom of Java, encode it with Base62.
2. Use Timestamp and server ip address, encode it with Base62.

Encoding with base62 is common point, so we would check the base 62 encoding first.

## 0. Base 62 Encoding

Both creating short url service commonly use base62 encoding. 
Base 62 encoding is encoding method to convert given number to alphanumeric word.


0 -> 'A'

1 -> 'B'

44 -> 's'

61 -> '9'

if the number is larger than 61, divide the number by 62 and the remainder is transferred to alphanumeric word by base 62 encoding until the remainder get to be zero.
For example, given number is 3568429834, the base62 encoded alphanumeric word.
4LVE3d.

The reason to use base62 encoding instead of base62 encoding is to create alphanumeric word.
In case of base64 encoding, the number 62 is converted to '+' and 63 is converted to '/'. 
Those are not alphanumeric words. we would like to exclude these letters so our encoding method is base62.


## 1. Secure Random of Java

Before encode number, we should create source number used by base62 encoding.
First strategy to create number is using secure random of Java. 
Java produces random number by SecureRandom class.

(Java random uses system time as its seed, so it has some kind of pattern. This is not random function. Instead of it, we use secure random.)

secureRandom.nextInt() makes random number then we convert the number to alphanumeric word which is short url.

This method has a fatal weakness. 
That is 'we should check DB if it has the short url already.'
because there is a little possibility of redundancy of number when creating number by using secure random.
every time we create secure random number, every time we should check if it exists in db.
This forces DB checking before putting data to db. This is not good for performance.

## 2. Timestamp, server number and serial number

We need unique number that guarantees no redundancy of number in DB.
To create unique number, we use timestamp, server number and serial number just like snowflake technique.

### Timestamp
timestamp means epoch time(millisecond) since 1970 year.
In Java, System.currentTimeMills() produces milliseconds of time since 1970. 

### Server number
server number is just arbitrary number given by service provider.
That can be number 1 or 25 or 128 etc. Any number is possible. 
But we use the server number to create short id so the smaller the number, the better.
so picking number from zero is best option. 
By using server number, 
when we scale-out server and use multiple servers with load balancer,
Uniqueness of short url is guaranteed.

### Serial number
We need serial number to create unique number.
In spite of using time stamp and server number, 
There is a little possibility to make redundancy of number.
In a multi-thread circumstance, there is possibility for each different thread to use same timestamp.
we use serial number to distinguish each timestamp in every millisecond.

## method to reset serial number

We cannot use large number as serial number because we should create alphanumeric id that is used as short url.
The shorter the url, the better. To make the serial shorter, we should reset it every appropriate time.
There are two method to reset the serial number.

### First method: reset it with timer

Java provides timer. Spring produces scheduled annotation.
We can reset the serial number by using timer method to reset it every millisecond.
However, this has a fatal drawback. In multi thread environment, These two functions (create id, reset serial number) can operate at the same time.
If two method works concurrently, there is possibility to use same timestamp and serial number by multi thread in one server.

### Second method: reset it when it reaches specific number.
Because reset serial id to zero based on time is bad idea, we reset it when it comes to specific number.
If we put 'synchronized' in front of method that check if the number is specific number, it works safely in multi thread.
But it also has warning point. Specific point to reset number is important.
If it is too small, it has still possibility to make redundancy because reset cycle would be too fast.