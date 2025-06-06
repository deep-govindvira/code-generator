import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Bean
    public AmazonDynamoDB getAmazonDynamoDB(
            @Value("${dynamodb.endpoint}") String endpoint,
            @Value("${dynamodb.region}") String region,
            @Value("${dynamodb.id}") String id,
            @Value("${dynamodb.password}") String password) {

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(id, password)))
                .build();
    }

    @Bean
    public DynamoDBMapper getAmazonDynamoDBMapper(final AmazonDynamoDB client) {
        return new DynamoDBMapper(client);
    }
}

