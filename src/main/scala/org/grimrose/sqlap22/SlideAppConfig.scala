package org.grimrose.sqlap22

import org.h2.server.web.WebServlet
import org.springframework.batch.core.configuration.annotation.{EnableBatchProcessing, JobBuilderFactory, StepBuilderFactory}
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.builder.SimpleStepBuilder
import org.springframework.batch.core.{Job, Step}
import org.springframework.batch.item.{ItemProcessor, ItemReader, ItemWriter}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation._

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan
class SlideAppConfig {

  @Profile(Array("dev"))
  @Bean
  def h2servletRegistration: ServletRegistrationBean = {
    new ServletRegistrationBean(new WebServlet, "/console/*")
  }

  @Profile(Array("dev"))
  @Bean
  def makeSlideHtml(jobs: JobBuilderFactory, step: Step): Job = {
    jobs
      .get("makeSlideHtml")
      .incrementer(new RunIdIncrementer)
      .flow(step)
      .end()
      .build()
  }

  @Profile(Array("dev"))
  @Bean
  def makeSlideStep(factory: StepBuilderFactory, reader: ItemReader[Slide], writer: ItemWriter[String], processor: ItemProcessor[Slide, String]): Step = {
    val builder: SimpleStepBuilder[Slide, String] = factory
      .get("makeSlideStep")
      .chunk(1)
    builder.reader(reader)
      .processor(processor)
      .writer(writer)
      .build()
  }

}
