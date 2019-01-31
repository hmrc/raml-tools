/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ramltools.domain

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsArray, JsValue, Json}
import uk.gov.hmrc.ramltools.RAML
import uk.gov.hmrc.ramltools.loaders.ClasspathRamlLoader

import scala.io.Source
import scala.util.Try

class EndpointsSpec extends WordSpec with Matchers {

  private trait Setup {
    def json(path: String): JsValue = Json.parse(Source.fromURL(getClass.getResource(path)).mkString)
    def raml(path: String): Try[RAML] = new ClasspathRamlLoader().load(path)
  }

  "The Endpoints utility" should {

    "Extract simple GET endpoint - no context" in new Setup {

      val raml1_0 = raml("input/simple-hello.raml").get
      val expOutput = json("/expected/json-exp-1.json")

      val actual = Endpoints(raml1_0, None)
      Json.toJson(actual).as[JsArray] shouldBe expOutput
    }

    "Extract simple GET endpoint with context" in new Setup {

      val raml1_0 = raml("input/simple-hello.raml").get
      val expOutput = json("/expected/json-exp-2.json")

      val actual = Endpoints(raml1_0, Some("test"))
      Json.toJson(actual).as[JsArray] shouldBe expOutput
    }

    "Extract simple GET endpoint with query parameters" in new Setup {

      val raml1_0 = raml("input/simple-hello-params.raml").get
      val expOutput = json("/expected/json-exp-2a.json")

      val actual = Endpoints(raml1_0, Some("test"))
      Json.toJson(actual).as[JsArray] shouldBe expOutput
    }

    "Extract complex endpoints with context" in new Setup {

      val raml1_0 = raml("good_raml/complex2-good.raml").get
      val expOutput = json("/expected/json-exp-3.json")

      val actual = Endpoints(raml1_0, Some("test"))
      Json.toJson(actual).as[JsArray] shouldBe expOutput
    }

  }

}
