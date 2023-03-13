// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

import https from "https";
import url from "url";

export const SUCCESS = "SUCCESS";
export const FAILED = "FAILED";

export const send = function (event, context, responseStatus, responseData, physicalResourceId, noEcho) {
    return new Promise((resolve, reject) => {
        var responseBody = JSON.stringify({
            Status: responseStatus,
            Reason: "See the details in CloudWatch Log Stream: " + context.logStreamName,
            PhysicalResourceId: physicalResourceId || context.logStreamName,
            StackId: event.StackId,
            RequestId: event.RequestId,
            LogicalResourceId: event.LogicalResourceId,
            NoEcho: noEcho || false,
            Data: responseData
        });

        console.log("Response body:\n", responseBody);

        var parsedUrl = url.parse(event.ResponseURL);
        var options = {
            hostname: parsedUrl.hostname,
            port: 443,
            path: parsedUrl.path,
            method: "PUT",
            headers: {
                "content-type": "",
                "content-length": responseBody.length
            }
        };

        var request = https.request(options, function (response) {
            console.log("Status code: " + response.statusCode);
            console.log("Status message: " + response.statusMessage);
            resolve(response.statusCode);
        });

        request.on("error", function (error) {
            console.log("send(..) failed executing https.request(..): " + error);
            reject(error);
        });

        request.write(responseBody);
        request.end();

    });
};
