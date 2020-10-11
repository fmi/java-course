# Dos and Dont's


> Clean coding means that in the first place you write code for your later self and for your co-workers and not for the machine. - András Tóth


### How should I name my variables?

1. Use self-explanatory, context-specific names
1. Use searchable names
1. Use is/has/can/should at the beginning of a boolean variable
1. Don't use abbreviations
1. Don't add unnecessary nouns to the variable names

When choosing a name for your variable ask yourself a question - **Only by reading the name of the variable, can I understand its purpose in the context of the application?**

For example, I am developing a Chat Server application and need to store a list of users which have their accounts locked due to failed authentications. So, I may write something like this:

``` java
Set <User> set = new HashSet<>();
```

Then I use `set` all over my code. Can another person or my future self understand at first glance what was my intention when I wrote this code? I may have stored all the registered users in the chat, or the logged ones.

``` java
// don't
Set <User> set = new HashSet<>();
Set <User> s = new HashSet<>();
Set <User> users = new HashSet<>();
// do
Set <User> lockedUsers = new HashSet<>();
```
``` java
// don't
String to;
String from;
// do
String receiverUsername;
String senderUsername;
```
``` java
// don't
String t;
// do
String numberOfTasks;
```
``` java
// don't
String fName;
// do
String firstName;
```
``` java
// don't
String[] namesArray;
// do
String[] names;
```

### How should I name my methods?

1. Use verbs for method names
1. Use descriptive, context-specific names
1. Use names that reflect the purpose of the method
1. Use is/has/can/should at the beginning of a boolean method

### How should I write my methods?

> Functions should do one thing. They should do it well. They should do it only. — Robert C. Martin

1. Keep methods short (~ 40 lines)
2. Don't make long arguments list
3. Don't make excessive nesting
4. Don't repeat yourself (code duplication)

### How should I name my classes?

1. [How to avoid calling everything a “<WhatEver>Manager”?](https://stackoverflow.com/questions/1866794/naming-classes-how-to-avoid-calling-everything-a-whatevermanager)

### How should I handle exceptions?

1. Log exceptions in a file - https://www.loggly.com/blog/logging-exceptions-in-java/
1. Clean up resources in a finally block or use try-with-resources
1. Don't silently swallow exceptions
1. Don't print stack trace to the user. (*Don't print stack trace at all - use loggers*). Instead, write some user-friendly message and log the exception in a file.
1. If you cannot recover from an exception, don't catch it silently. Instead propagate it, or re-throw a more specific one.
1. Don't use an empty exception constructor. Instead, use `(String message)` or `(String message, Throwable cause)`. Always try to add as much details as you can in the exception message.
    ``` java
    // don't
    [...]
    throw new CustomException ();
    throw new CustomException ("Connection failed");
    // do
    [...]
    throw new CustomException ("Connection to server on " + host + ":" + port + " cannot be established", ioExc);
    ```
