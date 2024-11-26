# PublishingApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**apiV1PublisherDeploymentDeploymentIdDelete**](PublishingApi.md#apiV1PublisherDeploymentDeploymentIdDelete) | **DELETE** /api/v1/publisher/deployment/{deploymentId} |  |
| [**apiV1PublisherDeploymentDeploymentIdPost**](PublishingApi.md#apiV1PublisherDeploymentDeploymentIdPost) | **POST** /api/v1/publisher/deployment/{deploymentId} |  |
| [**apiV1PublisherStatusPost**](PublishingApi.md#apiV1PublisherStatusPost) | **POST** /api/v1/publisher/status |  |
| [**apiV1PublisherUploadPost**](PublishingApi.md#apiV1PublisherUploadPost) | **POST** /api/v1/publisher/upload |  |


<a id="apiV1PublisherDeploymentDeploymentIdDelete"></a>
# **apiV1PublisherDeploymentDeploymentIdDelete**
> apiV1PublisherDeploymentDeploymentIdDelete(deploymentId)



Drop a deployment. Deployments can be dropped if they are in a &#x60;FAILED&#x60; or &#x60;VALIDATED&#x60; state. 

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = PublishingApi()
val deploymentId : kotlin.String = deploymentId_example // kotlin.String | The deployment identifier, which was obtained by a call to `/api/v1/publisher/upload`.
try {
    apiInstance.apiV1PublisherDeploymentDeploymentIdDelete(deploymentId)
} catch (e: ClientException) {
    println("4xx response calling PublishingApi#apiV1PublisherDeploymentDeploymentIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublishingApi#apiV1PublisherDeploymentDeploymentIdDelete")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deploymentId** | **kotlin.String**| The deployment identifier, which was obtained by a call to &#x60;/api/v1/publisher/upload&#x60;. | |

### Return type

null (empty response body)

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiV1PublisherDeploymentDeploymentIdPost"></a>
# **apiV1PublisherDeploymentDeploymentIdPost**
> apiV1PublisherDeploymentDeploymentIdPost(deploymentId)



Publish a deployment. Deployments can be published if they are in a &#x60;VALIDATED&#x60; state. 

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = PublishingApi()
val deploymentId : kotlin.String = deploymentId_example // kotlin.String | The deployment identifier, which was obtained by a call to `/api/v1/publisher/upload`.
try {
    apiInstance.apiV1PublisherDeploymentDeploymentIdPost(deploymentId)
} catch (e: ClientException) {
    println("4xx response calling PublishingApi#apiV1PublisherDeploymentDeploymentIdPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublishingApi#apiV1PublisherDeploymentDeploymentIdPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deploymentId** | **kotlin.String**| The deployment identifier, which was obtained by a call to &#x60;/api/v1/publisher/upload&#x60;. | |

### Return type

null (empty response body)

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="apiV1PublisherStatusPost"></a>
# **apiV1PublisherStatusPost**
> kotlin.String apiV1PublisherStatusPost(id)



Retrieve status of a deployment. Polling this endpoint can be useful for determining when a deployment changes state.

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = PublishingApi()
val id : kotlin.String = id_example // kotlin.String | The deployment identifier, which was obtained by a call to `/api/v1/publisher/upload`.
try {
    val result : kotlin.String = apiInstance.apiV1PublisherStatusPost(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublishingApi#apiV1PublisherStatusPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublishingApi#apiV1PublisherStatusPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| The deployment identifier, which was obtained by a call to &#x60;/api/v1/publisher/upload&#x60;. | |

### Return type

**kotlin.String**

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="apiV1PublisherUploadPost"></a>
# **apiV1PublisherUploadPost**
> kotlin.String apiV1PublisherUploadPost(name, publishingType, bundle)



Upload a deployment bundle intended to be published to Maven Central.

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = PublishingApi()
val name : kotlin.String = name_example // kotlin.String | Deployment/bundle name, optional (will use attached file name if not present).
val publishingType : kotlin.String = publishingType_example // kotlin.String | Whether to have the deployment stop in the `VALIDATED` state and require a user to log in and manually approve its progression, or to automatically go directly to `PUBLISHING` when validation has passed.
val bundle : io.ktor.client.request.forms.InputProvider = BINARY_DATA_HERE // io.ktor.client.request.forms.InputProvider | 
try {
    val result : kotlin.String = apiInstance.apiV1PublisherUploadPost(name, publishingType, bundle)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PublishingApi#apiV1PublisherUploadPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PublishingApi#apiV1PublisherUploadPost")
    e.printStackTrace()
}
```

### Parameters
| **name** | **kotlin.String**| Deployment/bundle name, optional (will use attached file name if not present). | [optional] |
| **publishingType** | **kotlin.String**| Whether to have the deployment stop in the &#x60;VALIDATED&#x60; state and require a user to log in and manually approve its progression, or to automatically go directly to &#x60;PUBLISHING&#x60; when validation has passed. | [optional] [enum: USER_MANAGED, AUTOMATIC] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **bundle** | **io.ktor.client.request.forms.InputProvider**|  | [optional] |

### Return type

**kotlin.String**

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: text/plain

