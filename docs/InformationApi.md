# InformationApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**apiV1PublisherDeploymentsFilesPost**](InformationApi.md#apiV1PublisherDeploymentsFilesPost) | **POST** /api/v1/publisher/deployments/files | 
[**apiV1PublisherPublishedGet**](InformationApi.md#apiV1PublisherPublishedGet) | **GET** /api/v1/publisher/published | 


<a id="apiV1PublisherDeploymentsFilesPost"></a>
# **apiV1PublisherDeploymentsFilesPost**
> ApiV1PublisherDeploymentsFilesPost200Response apiV1PublisherDeploymentsFilesPost(apiV1PublisherDeploymentsFilesPostRequest)



Browse the content of the deployment. 

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = InformationApi()
val apiV1PublisherDeploymentsFilesPostRequest : ApiV1PublisherDeploymentsFilesPostRequest =  // ApiV1PublisherDeploymentsFilesPostRequest | Request body containing the necessary parameters.
try {
    val result : ApiV1PublisherDeploymentsFilesPost200Response = apiInstance.apiV1PublisherDeploymentsFilesPost(apiV1PublisherDeploymentsFilesPostRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InformationApi#apiV1PublisherDeploymentsFilesPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InformationApi#apiV1PublisherDeploymentsFilesPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiV1PublisherDeploymentsFilesPostRequest** | [**ApiV1PublisherDeploymentsFilesPostRequest**](ApiV1PublisherDeploymentsFilesPostRequest.md)| Request body containing the necessary parameters. |

### Return type

[**ApiV1PublisherDeploymentsFilesPost200Response**](ApiV1PublisherDeploymentsFilesPost200Response.md)

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="apiV1PublisherPublishedGet"></a>
# **apiV1PublisherPublishedGet**
> ApiV1PublisherPublishedGet200Response apiV1PublisherPublishedGet(namespace, name, version)



Check whether a component is published.

### Example
```kotlin
// Import classes:
//import org.danilopianini.centralpublisher.impl.infrastructure.*
//import org.danilopianini.centralpublisher.impl.models.*

val apiInstance = InformationApi()
val namespace : kotlin.String = namespace_example // kotlin.String | namespace of component
val name : kotlin.String = name_example // kotlin.String | name of component
val version : kotlin.String = version_example // kotlin.String | version of component
try {
    val result : ApiV1PublisherPublishedGet200Response = apiInstance.apiV1PublisherPublishedGet(namespace, name, version)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InformationApi#apiV1PublisherPublishedGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InformationApi#apiV1PublisherPublishedGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **namespace** | **kotlin.String**| namespace of component |
 **name** | **kotlin.String**| name of component |
 **version** | **kotlin.String**| version of component |

### Return type

[**ApiV1PublisherPublishedGet200Response**](ApiV1PublisherPublishedGet200Response.md)

### Authorization


Configure BasicAuth:
    ApiClient.username = ""
    ApiClient.password = ""
Configure BearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

