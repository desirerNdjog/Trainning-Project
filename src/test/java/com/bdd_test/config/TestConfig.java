package com.bdd_test.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: desirejuniorndjog.
 * @created: 16/05/2024 : 20:46
 * @project: trainning
 */

@Configuration
@ComponentScan(basePackages = "com.bdd_test")
@EnableAutoConfiguration
public class TestConfig {

}
