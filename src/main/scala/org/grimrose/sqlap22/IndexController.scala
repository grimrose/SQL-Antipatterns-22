package org.grimrose.sqlap22

import java.nio.charset.StandardCharsets

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.io.Source

@RestController
class IndexController @Autowired()(val slideConverter: SlideConverter) {

  @RequestMapping(Array("/"))
  def index() = {
    val title = "SQLアンチパターン 22章"
    val markdown = loadMarkdown.getOrElse("")
    slideConverter.bind(title, markdown).getBytes(StandardCharsets.UTF_8)
  }

  def loadMarkdown: Option[String] = {
    try {
      val source = Source.fromURL(getClass.getResource("/slide.md"), StandardCharsets.UTF_8.toString)
      try {
        Some(source.mkString)
      }
      finally {
        source.close()
      }
    } catch {
      case e: Exception => None
    }
  }

}
