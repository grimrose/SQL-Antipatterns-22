## SQLアンチパターン
## 第22章
## シー・ノー・エビル(臭いものに蓋)

---

### See no evil, hear no evil, speak no evil.

---

## 1. 目的

>>>

## 簡潔なコードを書く

>>>

* ### より短い時間
* ### テスト、文書化、ピアレビューの対象が減る
* ### バグの混入

---

## 2. アンチパターン

>>>

## 肝心な部分を見逃す

---

## その1: 診断せずに判断する

>>>

# 推測するな
# 計測せよ

>>>

## 推測するな
## 再現コード書け

---

## その2: 見逃しがちなコード

**TODO**

---

## 3. アンチパターンの見つけ方

>>>

### データベースにクエリを発行した後で、プログラムがクラッシュする

>>>

### SQLエラーの特定を手伝って欲しい。これがコードだ

>>>

### エラー処理でコードをゴチャゴチャさせたくないな

---

### (  ﾟдﾟ) ・・・ 

### (つд⊂)ｺﾞｼｺﾞｼ 
 
### (；ﾟдﾟ) ・・・ 

---

## 4. アンチパターンを用いてもよい場合

>>>

### 例外処理の責任があるのは呼び出しを行った側であると確信出来る場合

---

## 例外設計

---

<iframe src="//www.slideshare.net/slideshow/embed_code/13477925" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/t_wada/exception-design-by-contract" title="例外設計における大罪" target="_blank">例外設計における大罪</a> </strong> from <strong><a href="http://www.slideshare.net/t_wada" target="_blank">Takuto Wada</a></strong> </div>

>>>

### [契約プログラミング](http://ja.wikipedia.org/wiki/%E5%A5%91%E7%B4%84%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%9F%E3%83%B3%E3%82%B0)

---

## 5. 解決策

>>>

## エラーから優雅に回復する

---

## その1: リズムを維持する

>>>

## 戻り値と例外のチェック

---

## リソースの開始と開放

>>>

## Loan Pattern

>>>

## C++

* [RAII（Resource Acquisition Is Initialization)](http://ja.wikipedia.org/wiki/RAII)

>>>

## Python

* [with 文](http://docs.python.jp/3.4/reference/compound_stmts.html#the-with-statement)

>>>

## Go

* [Defer, Panic, and Recover](http://blog.golang.org/defer-panic-and-recover)

>>>

## C♯

* [using ステートメント (C# リファレンス)](http://msdn.microsoft.com/ja-jp/library/yh598w02.aspx)

>>>

## Java7

* [try-with-resources 文](http://docs.oracle.com/javase/jp/7/technotes/guides/language/try-with-resources.html)

>>>

## Groovy

* [Loan my Resource Pattern](http://docs.groovy-lang.org/docs/next/html/documentation/design-pattern-in-groovy.html#_loan_my_resource_pattern)

>>>

## Scala

* [ローンパターン](http://www.ne.jp/asahi/hishidama/home/tech/scala/sample/using.html#h_loan_pattern)

---

## その2: ステップをたどり直す

>>>

## 実際に構築されたSQLクエリを使用する

>>>

* ### ORM付属のLogger 
* ### [log4jdbc-log4j2](https://code.google.com/p/log4jdbc-log4j2/)
* ### データベースのログ
* ### fluentd
* ### etc...

---

## コードのトラブルシューティングは、それだけで十分に大変な作業です。
## 闇雲に進めても、作業を遅らせるだけです。

---

では、どうしたら良いか？

---

## SQLException

---

[「SQL 99 Complete, Really」が無償公開されました。](http://blog.kimuradb.com/?page=1&cid=1093)

---

[SQL-99 Complete, Really](https://mariadb.com/kb/en/sql-99-complete-really/)

---

## [SQLSTATE Codes](https://mariadb.com/kb/en/sql-99-complete-really/47-sqlcli-diagnostic-functions/sqlstate-codes/)

---

* ### 5文字
* ### 2文字の条件クラス + 3文字のサブクラス
* ### '0'から'9'の数字か'A'から'Z'までの大文字アルファベット
* ### 規格にない場合、ベンダー固有で設定されている

---

## [PostgreSQLエラーコード](https://www.postgresql.jp/document/9.3/html/errcodes-appendix.html)

---

## [Server Error Codes and Messages](http://dev.mysql.com/doc/refman/5.7/en/error-messages-server.html)

---

過去のイベントで、それらしいのが…

---

## [LOG.debug("nice catch!")](http://connpass.com/event/607/)

---

[エラー処理の抽象化](http://tanakh.jp/pub/exception-logging-study-2012-06-28/exception.html)

---

[エラー処理を書いてはいけない](http://tanakh.jp/pub/pfi-seminar-2011-12-08.html)

---

[2012/06/27 java-ja 『LOG.debug("nice catch!")』#java_ja #javaja](http://togetter.com/li/328035)

---

Scalaでは

>>>

* try-cache-finally
* Option[T]
* Either[+A, +B]
* Try[T]

>>>

[Scalaでの例外処理 - Either,Option,util.control.Exception](http://yuroyoro.hatenablog.com/entry/20100719/1279519961)

>>>

<iframe src="//www.slideshare.net/slideshow/embed_code/16023052" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/TakashiKawachi/scala-16023052" title="Scalaでの例外処理" target="_blank">Scalaでの例外処理</a> </strong> from <strong><a href="http://www.slideshare.net/TakashiKawachi" target="_blank">Takashi Kawachi</a></strong> </div>

---

Scalazでは

>>>

[EitherとValidation](http://slides.pab-tech.net/either-and-validation)

---

ログは？

>>>

[#java_ja で例外とロギングについて勉強会をやるというのでいってきた＆飛び込みLTやった＆運用の視点から見たアプリケーションのログについて](http://d.hatena.ne.jp/tagomoris/20120628/1340858779)

---

ログと例外は適切に設計して、扱いましょう

---

[back to TOP](#/0)
