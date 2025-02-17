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

package uk.gov.hmrc.hmrcemailrenderer.templates.oss

import org.scalatest.EitherValues
import uk.gov.hmrc.hmrcemailrenderer.domain.MessagePriority
import uk.gov.hmrc.hmrcemailrenderer.templates.ServiceIdentifier.OSS
import uk.gov.hmrc.hmrcemailrenderer.templates.{ CommonParamsForSpec, TemplateLoader, TemplateLocator }
import uk.gov.hmrc.play.test.UnitSpec

class OSSRegistrationConfirmationSpec extends UnitSpec with EitherValues with TemplateLoader with CommonParamsForSpec {

  "OSS Registration Confirmation" should {
    val templateLocator = new TemplateLocator {}
    val params = commonParameters ++ Map(
      "recipientName_line1" -> "Joe Bloggs",
      "businessName"        -> "Test Business",
      "startDate"           -> "12 June 2021",
      "reference"           -> "123456789"
    )
    val template = templateLocator
      .templateGroups("OSS")
      .find(_.templateId == "oss_registration_confirmation")
      .get

    "render correct meta information" in {
      template.templateId shouldBe "oss_registration_confirmation"
      template.service shouldBe OSS
      template.fromAddress(Map.empty) shouldBe "VAT One Stop Shop Team <noreply@tax.service.gov.uk>"
      template.subject(Map.empty) shouldBe "HMRC: your registration for the One Stop Shop Union scheme"
      template.priority shouldBe Some(MessagePriority.Background)
    }

    "render correct html content" in {
      val htmlContent = template.htmlTemplate(params).toString

      htmlContent should include(
        "You are registered to pay VAT to the EU on distance sales of goods from Northern Ireland")
      htmlContent should include("Dear Joe Bloggs")
      htmlContent should include("HMRC has received the registration from Test Business.")
      htmlContent should include("Your eligible sales starting from 12 June 2021 will be included in this scheme.")
      htmlContent should include("You should:")
      htmlContent should include("charge VAT at the rate of the EU country where the goods are sent to")
      htmlContent should include(
        "complete a single quarterly return using this scheme for eligible sales of goods to consumers in the EU")
      htmlContent should include(
        "make a quarterly payment to HMRC of the total VAT due on eligible sales of goods to consumers in the EU")
      htmlContent should include("What happens next")
      htmlContent should include("We will contact you if we need to check any information.")
      htmlContent should include(
        "We will send you details about how to submit returns and make payments before your first VAT return for this scheme is due.")
      htmlContent should include("If you need to contact us")
      htmlContent should include("Quote your UK VAT registration number: 123456789")
      htmlContent should include("From the VAT One Stop Shop team")
    }

    "render correct text content" in {
      val txtContent = template.plainTemplate(params).toString

      txtContent should include(
        "You are registered to pay VAT to the EU on distance sales of goods from Northern Ireland")
      txtContent should include("Dear Joe Bloggs")
      txtContent should include("HMRC has received the registration from Test Business.")
      txtContent should include("Your eligible sales starting from 12 June 2021 will be included in this scheme.")
      txtContent should include("You should:")
      txtContent should include("charge VAT at the rate of the EU country where the goods are sent to")
      txtContent should include(
        "complete a single quarterly return using this scheme for eligible sales of goods to consumers in the EU")
      txtContent should include(
        "make a quarterly payment to HMRC of the total VAT due on eligible sales of goods to consumers in the EU")
      txtContent should include("What happens next")
      txtContent should include("We will contact you if we need to check any information.")
      txtContent should include(
        "We will send you details about how to submit returns and make payments before your first VAT return for this scheme is due.")
      txtContent should include("If you need to contact us")
      txtContent should include("Quote your UK VAT registration number: 123456789")
      txtContent should include("From the VAT One Stop Shop team")
    }
  }
}
