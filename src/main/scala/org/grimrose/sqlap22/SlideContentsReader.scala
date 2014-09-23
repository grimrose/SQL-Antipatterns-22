package org.grimrose.sqlap22

import java.nio.charset.StandardCharsets

import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component

import scala.io.Source

@Component
class SlideContentsReader extends ItemReader[Slide] {

  override def read(): Slide = {
    val title = "SQLアンチパターン 22章"
    val contents = loadMarkdown.getOrElse("")
    new Slide(title, contents)
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
