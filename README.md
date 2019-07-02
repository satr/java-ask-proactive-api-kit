# Java ASK Proactive API kit
Alexa Skills Proactive API kit
This kit has boierplate code to send Proactive Events to Alexa Skills over AWS Lambda function. Currently supports only `MediaContent` event.

"Get started with Amazon Alexa Skills Proactive Events API": [article](https://medium.com/swlh/get-started-with-amazon-alexa-skills-proactive-events-api-5b082bcb282c), [video](https://www.youtube.com/watch?v=6Ul_ry2hq2w)

Create a Java project with Gradle

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

* Inherit your Lambda function from the `SendProactiveEventRequestHandler`

```
import com.amazonaws.regions.Regions;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.aws.regions.InvalidRegionNameException;

public class SendMediaContentProactiveEventRequestHandler extends SendProactiveEventRequestHandler {
    public SendMediaContentProactiveEventRequestHandler() throws InvalidRegionNameException {
        super("AlexaSkillSecretName", Regions.US_EAST_1, new MediaContentProactiveEventProvider());
    }
}
```

Add Alexa Skill's Client ID and Client Secret secret (Alexa Skills dashboard/Build/Permissions) to the [AWS Secrets Manager](https://us-east-1.console.aws.amazon.com/secretsmanager/home?region=us-east-1#/listSecrets): `client_id` and `client_secret` key-value pairs. Pass the name of this secret as a parameter to the super-class.

Add the Lambda function running user to a new group with permits to read secrets

```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": "secretsmanager:GetSecretValue",
            "Resource": "arn:aws:secretsmanager:us-east-1:*:secret:*"
        }
    ]
}
```

Create en events provider class, implementing `ProactiveEventProvider` - it performs backend logic, preparing events and return them as a list to be sent> `List<ProactiveEvent> getEvents() {}`

Upload the built jar-file to the Lambda function, specify the correct function Handler. 

(Optionally) In AWS Lambda function configuration - set environment variables in a Lambda function configuration dashboard (names and values are described in `EnvironmentVariable` class). If not set - default values: `Dev`, `NorthEast`

Run regularly this Lambda function. E.g add a rule to the CloudWatch, reacted to the `Event Pattern` or `Scheduled`.
