package org.danilopianini.centralpublisher

import kotlinx.serialization.json.Json
import org.danilopianini.centralpublisher.impl.models.DeploymentResponseFiles
import kotlin.test.Test
import kotlin.test.assertTrue

class TestDeserialization {

    @Test
    fun `response with error field should deserialize correctly`() {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString: String = """
            {
              "deploymentId" : "47349a14-a9df-4b04-ac40-7b8d66dcfde3",
              "deploymentName" : "alchemist-maven-central-portal-42.2.3-dev0f+da309b869.zip",
              "deploymentState" : "PENDING",
              "purls" : [ ],
              "errors" : {
                "common" : [ "Deployment components info not found" ]
              }
            }
        """.trimIndent()
        val decoded = json.decodeFromString<DeploymentResponseFiles>(jsonString)
        assertTrue(decoded.errors?.common.orEmpty().isNotEmpty())
    }
}
