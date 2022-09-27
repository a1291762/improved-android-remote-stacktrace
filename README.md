Android Remote Stacktrace: Improved (Again)
===========================================

This project is fork of [Android Remote Stacktrace: Improved][1] which adds a few minor features:

 * No longer using the obsolete apache http client classes.
 * Build with moden tools.
 
What follows is the original documentation for Android Remote Stacktrace, with small modifications for the few API changes made by me.

## Client side usage

Check out the code and build trace.jar by running make.
Drop it into your project's libs dir.
If you use the default `HttpPostStackInfoSender`, you must enable internet access for your application:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

In the onCreate method of your activity or in your service, you must call either `public static boolean register(Context context, String url)` (for the default HTTP POST behavior) or `public static boolean register(Context context, final StackInfoSender stackInfoSender, final boolean debug)` found in the class ExceptionHandler. Do something like this:

```java
ExceptionHandler.register(this, "https://trace.nullwire.com");
```

Or, using your own handler:

```java
ExceptionHandler.register(this, new MyFancyExceptionSender(), isDebugEnabled);
```

If you wish to implement your own StackInfoSender, see the [javadoc documentation](http://pretz.github.com/improved-android-remote-stacktrace/javadoc/index.html?com/nullwire/trace/StackInfoSender.html) for the interface.

## Server side installation

If you would like to store your stack traces on your own server, you will have to register the exception handler like this:

```java
ExceptionHandler.register(this, "https://your.domain/path"); 
```

At `https://your.domain/path` the client side implementation will expect to find [this simple PHP script](https://github.com/a1291762/improved-android-remote-stacktrace/blob/master/server/_crash/collect.php), which will take some POST parameters: 'package_name', 'package_version', 'phone_model', 'android_version' and 'stacktrace'. The collected data is emailed (use your own address).

## Building the JAR

The JAR may be built by issuing the following command:

    make

This will produce a trace.jar file.

## License


The MIT License

Copyright (c) 2009 Mads Kristiansen, Nullwire ApS

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


[1]: https://github.com/Pretz/improved-android-remote-stacktrace
