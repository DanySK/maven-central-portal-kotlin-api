# IntegrationApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet**](IntegrationApi.md#apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet) | **GET** /api/v1/publisher/deployment/{deploymentId}/download/{relativePath} |  |
| [**apiV1PublisherDeploymentsDownloadRelativePathGet**](IntegrationApi.md#apiV1PublisherDeploymentsDownloadRelativePathGet) | **GET** /api/v1/publisher/deployments/download/{relativePath} |  |


<a id="apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet"></a>
# **apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet**
> org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet(deploymentId, relativePath)



Integrate a deployment bundle with a build for manual testing. For more information, see the the following [documentation](https://central.sonatype.org/publish/publish-portal-api/#manually-testing-a-deployment-bundle). 

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = IntegrationApi()
val deploymentId : kotlin.String = deploymentId_example // kotlin.String | The deployment identifier, which was obtained by a call to `/api/v1/publisher/upload`.
val relativePath : kotlin.String = relativePath_example // kotlin.String | The full path to a specific file from a deployment bundle.
try {
    val result : org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray = apiInstance.apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet(deploymentId, relativePath)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling IntegrationApi#apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling IntegrationApi#apiV1PublisherDeploymentDeploymentIdDownloadRelativePathGet")
    e.printStackTrace()
}
```

### Parameters
| **deploymentId** | **kotlin.String**| The deployment identifier, which was obtained by a call to &#x60;/api/v1/publisher/upload&#x60;. | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **relativePath** | **kotlin.String**| The full path to a specific file from a deployment bundle. | |

### Return type

[**org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray**](org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray.md)

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/octet-stream

<a id="apiV1PublisherDeploymentsDownloadRelativePathGet"></a>
# **apiV1PublisherDeploymentsDownloadRelativePathGet**
> org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray apiV1PublisherDeploymentsDownloadRelativePathGet(relativePath)



Integrate deployment bundles with a build for manual testing. For more information, see the the following [documentation](https://central.sonatype.org/publish/publish-portal-api/#manually-testing-a-deployment-bundle). 

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = IntegrationApi()
val relativePath : kotlin.String = relativePath_example // kotlin.String | The full path to a specific file from a deployment bundle.
try {
    val result : org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray = apiInstance.apiV1PublisherDeploymentsDownloadRelativePathGet(relativePath)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling IntegrationApi#apiV1PublisherDeploymentsDownloadRelativePathGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling IntegrationApi#apiV1PublisherDeploymentsDownloadRelativePathGet")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **relativePath** | **kotlin.String**| The full path to a specific file from a deployment bundle. | |

### Return type

[**org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray**](org.danilopianini.centralpublisher.impl.infrastructure.OctetByteArray.md)

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/octet-stream

