package com.github.satr.aws.auth;
/**
 * The source of alexaSkillClientId and alexaSkillClientSecret
 *   <li>{@link AlexaSkillClientIdSecretSource#StringValues} Skill ID and Skill Secret string values</li>
 *   <li>{@link AlexaSkillClientIdSecretSource#EnvironmentVariables} the name of an environment variable, holding a name of Skill ID and Skill Secret string values</li>
 */
public enum AlexaSkillClientIdSecretSource {
    /**
     * The name of an environment variable, holding a name of Skill ID and Skill Secret string values
     */
    EnvironmentVariables,
    /**
     * Skill ID and Skill Secret string values
     */
    StringValues
}
