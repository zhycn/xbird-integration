/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xbird.starter.id.annotations;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.xbird.starter.id.autoconfigure.SegmentProperties;
import cn.xbird.starter.id.factory.SegmentFactory;
import cn.xbird.starter.id.repository.SegmentRepository;
import cn.xbird.starter.id.service.SegmentID;

/**
 * ID JPA Configuration
 * 
 * @author zhycn
 * @since 2.2.0 2018-06-08
 */
@Configuration
@EnableConfigurationProperties(SegmentProperties.class)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ, proxyTargetClass = true)
@EnableJpaRepositories({"cn.xbird.starter.id.repository"})
@EntityScan("cn.xbird.starter.id.domain")
@EnableJpaAuditing
public class SegmentConfigurationSelector implements InitializingBean {

  @Autowired
  private SegmentProperties properties;

  @Autowired
  private SegmentRepository repository;

  @Bean
  @ConditionalOnMissingBean
  public SegmentID createSegmentFactory() {
    boolean asynLoading = properties.getAsynLoading();
    return new SegmentFactory(repository, asynLoading);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    properties.getEndpoints().forEach((k, v) -> {
      createSegmentFactory().init(v.getBizTag(), v.getStartId(), v.getStep(),
          v.getDescription());
    });
  }

}
