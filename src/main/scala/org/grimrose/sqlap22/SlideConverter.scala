package org.grimrose.sqlap22

import org.springframework.stereotype.Component

@Component
class SlideConverter {

  def bind(title: String, contents: String): String = {
    template(title, contents)
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

}
