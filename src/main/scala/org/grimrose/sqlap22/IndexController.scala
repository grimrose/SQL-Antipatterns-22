package org.grimrose.sqlap22

import java.nio.charset.StandardCharsets

import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.io.Source

@RestController
class IndexController {


  @RequestMapping(Array("/"))
  def index() = {
    val title = "SQLアンチパターン 22章"
    val markdown = loadMarkdown.getOrElse("")
    template(title, markdown).getBytes(StandardCharsets.UTF_8)
  }

  def template = (title: String, contents: String) => {
    s"""
      |<!DOCTYPE html>
      |<html>
      | <head>
      |  <meta charset="UTF-8">
      |  <title>$title</title>
      |  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/css/theme/night.css">
      |  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/css/reveal.min.css">
      | </head>
      | <body>
      |  <div class="reveal">
      |   <div class="slides">
      |    <section data-markdown data-separator="^\n---$$" data-vertical="^\n>>>$$">
      |     <script type="text/template">
$contents
      |     </script>
      |   </div>
      |  </div>
      |  <script src="//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/js/reveal.min.js"></script>
      |  <script src="//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/lib/js/head.min.js"></script>
      |  <script type="text/javascript">
      |   Reveal.initialize({
      |        controls: true,
      |        progress: true,
      |        slideNumber: true,
      |        history: true,
      |        keyboard: true,
      |        center: true,
      |        dependencies: [
      |            {
      |                src: '//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/plugin/markdown/marked.js',
      |                condition: function () {
      |                    return !!document.querySelector('[data-markdown]');
      |                }
      |            },
      |            {
      |                src: '//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/plugin/markdown/markdown.js',
      |                condition: function () {
      |                    return !!document.querySelector('[data-markdown]');
      |                }
      |            },
      |            {
      |                src: '//cdnjs.cloudflare.com/ajax/libs/reveal.js/2.6.2/plugin/highlight/highlight.js',
      |                async: true,
      |                callback: function () {
      |                    hljs.initHighlightingOnLoad();
      |                }
      |            }
      |        ]
      |   });
      |  </script>
      | </body>
      |</html>
    """.stripMargin
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
