package org.grimrose.sqlap22

import org.h2.server.web.WebServlet
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, Profile}

@Configuration
@EnableAutoConfiguration
@ComponentScan
class SlideAppConfig {

  @Profile(Array("dev"))
  @Bean
  def h2servletRegistration: ServletRegistrationBean = {
    new ServletRegistrationBean(new WebServlet, "/console/*")
  }

}
