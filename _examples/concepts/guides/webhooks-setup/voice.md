---
title: Voice API
menu_weight: 3
---

For Voice API requests, webhooks can be set at an application level, when creating a call, or in the actions in an NCCO.

### Application-level webhooks

Numbers you have purchased that are connected to Nexmo applications will use the `answer_url` to retrieve an NCCO, and the `event_url` to send call status information to you.

You can set these using the [Application API](/api/application), in the [Nexmo Dashboard](https://dashboard.nexmo.com) or using the [Nexmo CLI](https://github.com/nexmo/nexmo-cli) tool.

### Number-level webhooks

You can set a status webhook for each number you purchase. This will be used to send events to you regarding each number.

These can be set up in the Numbers section of the [Dashboard](https://dashboard.nexmo.com), via the [Nexmo CLI](https://github.com/nexmo/nexmo-cli) or via the [Update a Number](/api/developer/numbers#update-a-number) API call (specifically, the `voiceStatusCallback` property).

### On creating an outbound call

When [making a new outbound call](/voice/voice-api/building-blocks/make-an-outbound-call), you need to set the `answer_url` in the call to a URL containing an NCCO. Nexmo's servers will retrieve the NCCO from this endpoint and follow its instructions in handling the outbound call.

### Inside an NCCO

Inside an NCCO, the following action types take a webhook URL for use when that action is executed:

* [record.eventUrl](/voice/guides/ncco-reference#record) - set the webhook endpoint that receives information about the recording for a Call or Conversation
* [conversation.eventUrl](/voice/guides/ncco-reference#conversation) - set the URL to the webhook endpoint Nexmo calls asynchronously when a conversation changes state for this conversation action
* [connect.eventUrl](/voice/guides/ncco-reference#connect) - set the URL to the webhook endpoint Nexmo calls asynchronously when a conversation changes state for this connect action
* [input.eventUrl](/voice/guides/ncco-reference#input) - set the URL to the webhook endpoint Nexmo sends the digits pressed by the callee
* [stream.streamUrl](/voice/guides/ncco-reference#stream) - set an array of URLs pointing to the webhook endpoints hosting the audio file to stream to the Call or Conversation
