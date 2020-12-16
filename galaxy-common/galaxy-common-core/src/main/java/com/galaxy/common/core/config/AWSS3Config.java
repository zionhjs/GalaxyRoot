package com.galaxy.common.core.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSS3Config {
    /*@Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretAccessKey;
    @Value("${amazonProperties.region}")
    private String region;
    @Value("${amazonProperties.cloudfront_DomainName}")
    private String cloudfront_domainname;*/

    private String accessKey = "AKIA2AUK3NDSXMVXAQ6B";
    private String secretAccessKey = "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";
    private String region = "us-west-1";
    private String cloudfront_domainname = "d18bri8sapzuu4.cloudfront.net";

    @Bean
    public AmazonS3 getAmazonS3Client(){
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}


