package com.galaxy.upload.module.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AWSS3Config {
    //@Value("${galaxy.amazonProperties.accessKey}")
    private String accessKey = "AKIA2AUK3NDS7MN4OCY4";
    //@Value("${galaxy.amazonProperties.secretKey}")
    private String secretAccessKey = "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";
    @Value("${galaxy.amazonProperties.region}")
    private String region;
    @Value("${galaxy.amazonProperties.cloudfront_DomainName}")
    private String cloudfront_domainname;

    /*private String accessKey = "AKIA2AUK3NDSXMVXAQ6B";
    private String secretAccessKey = "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";
    private String region = "us-west-1";
    private String cloudfront_domainname = "d18bri8sapzuu4.cloudfront.net";*/

    @Bean
    public AmazonS3 getAmazonS3Client(){
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                //设置服务器所属地区
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}


