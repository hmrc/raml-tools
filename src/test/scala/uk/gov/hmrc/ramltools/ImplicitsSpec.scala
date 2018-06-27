/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ramltools

import org.raml.v2.api.model.v10.resources.Resource
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ramltools.Implicits._
import uk.gov.hmrc.ramltools.loaders.ClasspathRamlLoader

class ImplicitsSpec extends WordSpec with Matchers {

   "Monkey patching of RAML" should {
     "just work" in {
       val raml = new ClasspathRamlLoader().load("good_raml/nested-good.raml").get

       val resources: Seq[Resource] = raml.flattenedResources()

       resources.size shouldBe 3

       resources.map(_.resourcePath()) should contain("/{empRef}")
       resources.map(_.resourcePath()) should contain("/{empRef}/{taxYear}")
       resources.map(_.resourcePath()) should contain("/{empRef}/{taxYear}/charges")
     }

    "work with more complex resources" in {
      val raml = new ClasspathRamlLoader().load("good_raml/complex-good.raml").get

      val resources = raml.flattenedResources().map(_.resourcePath())

      resources.size shouldBe 16

      resources shouldBe List("/{empRef}",
        "/{empRef}/{taxYear}",
        "/{empRef}/{taxYear}/charges",
        "/{empRef}/{other_thing}",
        "/{empRef}/{other_thing}/humph",
        "/docs",
        "/docs/to_read",
        "/docs/to_read/now",
        "/docs/to_read/now/favorites",
        "/docs/to_read/now/backburner",
        "/docs/to_read/later",
        "/docs/to_read/later/important",
        "/docs/to_read/later/meh",
        "/docs/archive",
        "/docs/archive/2014",
        "/docs/archive/2015")
    }
  }
}
