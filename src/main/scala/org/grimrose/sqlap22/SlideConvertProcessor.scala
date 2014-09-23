package org.grimrose.sqlap22

import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SlideConvertProcessor @Autowired()(val slideConverter: SlideConverter) extends ItemProcessor[Slide, String] {

  override def process(slide: Slide): String = {
    val title = slide.title
    val contents = slide.contents
    slideConverter.bind(title, contents)
  }

}
