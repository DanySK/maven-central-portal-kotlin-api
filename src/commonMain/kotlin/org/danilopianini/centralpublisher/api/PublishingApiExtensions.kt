package org.danilopianini.centralpublisher.api

import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.InputProvider
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders.ContentDisposition
import org.danilopianini.centralpublisher.api.PublishingApi.PublishingTypeApiV1PublisherUploadPost.AUTOMATIC
import org.danilopianini.centralpublisher.api.PublishingApi.PublishingTypeApiV1PublisherUploadPost.USER_MANAGED
import org.danilopianini.centralpublisher.impl.infrastructure.HttpResponse

/**
 * Uploads a deployment bundle intended for publication to Maven Central.
 *
 * @param name The name of the deployment or bundle. If not specified, the name of the attached file is used.
 * @param releaseAfterUpload If `true`, the deployment will automatically proceed to the `PUBLISHING` state upon validation.
 *                           If `false`, it will remain in the `VALIDATED` state until manually approved. Default is `false`.
 * @param bundle The bundle file to upload.
 * @return A [String] response from the server.
 */
@Suppress("UNCHECKED_CAST")
suspend fun PublishingApi.apiV1PublisherUploadPost(
    name: String,
    releaseAfterUpload: Boolean = false,
    bundle: InputProvider,
): HttpResponse<String> = apiV1PublisherUploadPost(
    name,
    if (releaseAfterUpload) AUTOMATIC else USER_MANAGED,
    FormPart("bundle", bundle, Headers.build { append(ContentDisposition, "filename=\"$name\"") })
)
