package org.grimrose.sqlap22

import java.util

import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._
import scalax.file.Path

@Component
class SlideHtmlWriter extends ItemWriter[String] {

  override def write(items: util.List[_ <: String]): Unit = {
    val path = Path(".", "index.html")
    path.createFile(false, false)
    path.write(items.toSeq.head)
  }

}
