/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ramltools.loaders

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import uk.gov.hmrc.ramltools.domain.{RamlNotFoundException, RamlParseException, RamlUnsupportedVersionException}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll

import scala.util.Failure

class RamlLoaderSpec extends AnyWordSpec with Matchers with BeforeAndAfterAll {

  private val stubPort = sys.env.getOrElse("WIREMOCK", "22745").toInt
  
  private val stubHost = "localhost"
  private val wireMockUrl = s"http://$stubHost:$stubPort"
  private val wireMockServer = new WireMockServer(wireMockConfig().port(stubPort))

  override def beforeAll(): Unit = {
    wireMockServer.start()
    WireMock.configureFor(stubHost, stubPort)
  }

  override def afterAll(): Unit = {
    if (wireMockServer.isRunning) {
      wireMockServer.stop()
    }
  }

  "failure to find the file" should {
    "result in a not found exception" in {
      new UrlRamlLoader().load("http://zzz-imnotreal-zzz/") match {
        case Failure(e: RamlNotFoundException) => e.getMessage shouldBe "Raml does not exist at: http://zzz-imnotreal-zzz/"
        case _ => throw new IllegalStateException("should not reach here")
      }
    }
  }

  "Not RAML" should {
    "result in a parse exception" in {
      stubFor(get(urlPathEqualTo("/application"))
        .willReturn(
          aResponse()
            .withStatus(202)
            .withBody("<!doctype html>" +
              "<html>" +
              "<head><title>Welcome</title></head>" +
              "<body>Hello</body>" +
              "</html>")))

      new UrlRamlLoader().load(s"$wireMockUrl/application") match {
        case Failure(e: RamlParseException) =>
          e.getMessage should include("Invalid header declaration <!doctype html><html")
        case _ =>
          throw new IllegalStateException("should not reach here")
      }
    }
  }

  "Bad RAML" should {
    "result in a parse exception" in {
      new ClasspathRamlLoader().load("bad_yaml.raml") match {
        case Failure(e: RamlParseException) => e.getMessage shouldBe
          """Underlying error while parsing YAML syntax: 'while parsing a block mapping
            | in 'reader', line 3, column 1:
            |    title: Employers PAYE Service
            |    ^
            |expected <block end>, but found BlockEntry
            | in 'reader', line 6, column 1:
            |    - bad mix
            |    ^
            |' --  [line=6, col=1]""".stripMargin
        case _ => throw new IllegalStateException("should not reach here")
      }
    }
  }

  "Unsupported RAML spec version" should {
    "result in an unsupported exception" in {
      new ClasspathRamlLoader().load("jukebox_0.8.raml") match {
        case Failure(e: RamlUnsupportedVersionException) => e.getMessage shouldBe "Only RAML1.0 is supported"
        case _ => throw new IllegalStateException("should not reach here")
      }
    }
  }

  "UrlRewriter" should {

    trait Setup {
      val underTest = new UrlRewriter {
        val rewrites = Map(
          "https://developer\\.service\\.hmrc\\.gov\\.uk" -> "http://api-documentation-frontend.example.co.uk",
          "http://api-documentation-raml-frontend\\.service" -> "http://api-documentation-frontend.example.co.uk")
      }
    }

    "modify public dev hub URL to internal one for doc frontend" in new Setup {
      val url = "https://developer.service.hmrc.gov.uk/api-documentation/assets/common/docs/errors.md"
      val internalUrl = "http://api-documentation-frontend.example.co.uk/api-documentation/assets/common/docs/errors.md"

      underTest.rewriteUrl(url) shouldBe internalUrl
    }

    "modify raml doc frontend URL to one for doc frontend" in new Setup {
      val url = "http://api-documentation-raml-frontend.service/api-documentation/assets/common/docs/errors.md"
      val internalUrl = "http://api-documentation-frontend.example.co.uk/api-documentation/assets/common/docs/errors.md"

      underTest.rewriteUrl(url) shouldBe internalUrl
    }

    "not modify other URLs" in new Setup {
      val url = "http://www.bbc.co.uk/news"

      underTest.rewriteUrl(url) shouldBe url
    }
  }

}
