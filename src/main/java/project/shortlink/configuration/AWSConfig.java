package project.shortlink.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "ab180test.shortlink.repository")
public class AWSConfig {

    @Value("${aws.accessKey}")
    private String awsAccessKey;
    @Value("${aws.secretKey}")
    private String awsSecretKey;
    @Value("${aws.region}")
    private String awsRegion;

    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(awsCredentials());
    }

    // DynamoDB Configuration
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().withCredentials(awsCredentialsProvider())
                .withRegion(awsRegion).build();
    }

}
