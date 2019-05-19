# java-ask-proactive-api-kit
Alexa Skills Proactive API kit
This kit has boierplate code to send Proactive Events to Alexa Skills over AWS Lambda function.
* Inherit your Lambda function from the `SendProactiveEventRequestHandler`
* Set environment variables in a Lambda function configuration dashboard (names and values are described in `EnvironmentVariable` class). Default: `Dev`, `NorthEast`

Add dependency in the `build.gradle` file:
```
repositories {
    mavenCentral()
    //Include dependencies from GitHub
    maven { url 'https://jitpack.io' }
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
    compile group: 'com.github.satr', name:'java-ask-proactive-api-kit', version:'1.0'
}

//Include dependencies in jar-file - to deploy to AWS Lambda function
jar {
    from {
        configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
    }
}
```
