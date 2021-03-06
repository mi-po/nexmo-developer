/*
 * Copyright (c) 2011-2018 Nexmo Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.nexmo.quickstart.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexmo.client.voice.ncco.ConnectNcco;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.RecordNcco;
import com.nexmo.client.voice.ncco.SplitRecording;
import spark.Route;

import static com.nexmo.quickstart.Util.envVar;
import static spark.Spark.*;

public class RecordCallSplitAudio {
    public static void main(String[] args) {
        /*
         * Route to answer and connect incoming calls with recording.
         */
        Route answerRoute = (req, res) -> {
            String recordingUrl = String.format("%s://%s/webhook/recordings", req.scheme(), req.host());

            RecordNcco record = new RecordNcco();
            record.setEventUrl(recordingUrl);
            record.setSplit(SplitRecording.CONVERSATION);

            String TO_NUMBER = envVar("TO_NUMBER");
            String NEXMO_NUMBER = envVar("NEXMO_NUMBER");
            ConnectNcco connect = new ConnectNcco(TO_NUMBER);
            connect.setFrom(NEXMO_NUMBER);

            Ncco[] nccos = new Ncco[]{record, connect};

            res.type("application/json");
            return new ObjectMapper().writer().writeValueAsString(nccos);
        };

        /*
         * Webhook Route which prints out the recording URL it is given to stdout.
         */
        Route recordingWebhookRoute = (req, res) -> {
            System.out.println(RecordingPayload.fromJson(req.bodyAsBytes()).getRecordingUrl());

            res.status(204);
            return "";
        };

        port(3000);
        get("/webhook/answer", answerRoute);
        post("/webhook/recordings", recordingWebhookRoute);
    }
}
