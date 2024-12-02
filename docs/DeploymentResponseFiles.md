
# DeploymentResponseFiles

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **deploymentId** | **kotlin.String** |  |  [optional] |
| **deploymentName** | **kotlin.String** |  |  [optional] |
| **deploymentState** | [**inline**](#DeploymentState) |  |  [optional] |
| **deploymentType** | [**inline**](#DeploymentType) |  |  [optional] |
| **createTimestamp** | **kotlin.Double** |  |  [optional] |
| **purls** | **kotlin.collections.List&lt;kotlin.String&gt;** |  |  [optional] |
| **deployedComponentVersions** | [**kotlin.collections.List&lt;DeployedComponentVersion&gt;**](DeployedComponentVersion.md) |  |  [optional] |


<a id="DeploymentState"></a>
## Enum: deploymentState
| Name | Value |
| ---- | ----- |
| deploymentState | PENDING, VALIDATING, VALIDATED, PUBLISHING, PUBLISHED, FAILED |


<a id="DeploymentType"></a>
## Enum: deploymentType
| Name | Value |
| ---- | ----- |
| deploymentType | BUNDLE, SINGLE |



