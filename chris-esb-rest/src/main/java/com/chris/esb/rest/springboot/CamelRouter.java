/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chris.esb.rest.springboot;

import com.alibaba.fastjson.JSONObject;
import com.chris.esb.rest.dashi.doorctrl.model.DoorCtrlReserveParam;
import com.chris.esb.rest.dashi.doorctrl.model.RemoteOpenDoorParam;
import com.chris.esb.rest.springboot.utils.CommonResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;

/**
 * A simple Camel REST DSL route with Swagger API documentation.
 */
@Component
public class CamelRouter extends RouteBuilder {
    private Logger logger = Logger.getLogger(CamelRouter.class);

    @Autowired
    private Environment env;
    
    @Value("${camel.component.servlet.mapping.context-path}")
    private String contextPath;

    @Value("8080")
    private String port;

    @Override
    public void configure() throws Exception {
        logger.error("camel router init*******************");
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(this.env.getProperty("server.port", this.port))
            .contextPath(this.contextPath.substring(0, this.contextPath.length() - 2))
            // turn on swagger api-doc
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "User API")
            .apiProperty("api.version", "1.0.0");

        rest("/remoteOpenDoor").consumes("application/json")
                .produces("application/json").
                post().description("Remote Open Door").type(RemoteOpenDoorParam.class).outType(CommonResponse.class)
                .param().name("body").type(body).description("open door params").endParam()
                .responseMessage().code(200).message("success").endResponseMessage()
                .to("bean:doorCtrlMgrService?method=remoteOpenDoor");

        rest("/doorCtrlReserve").consumes("application/json")
                .produces("application/json").
                post().description("Door Controller Reserve").type(DoorCtrlReserveParam.class).outType(CommonResponse.class)
                .param().name("body").type(body).description("door controller reserve").endParam()
                .responseMessage().code(200).message("success").endResponseMessage()
                .to("bean:doorCtrlMgrService?method=doorCtrlReserve");

        rest("/doorCtrlReserve4Coson").consumes("application/json")
                .produces("application/json").
                post().description("Door Controller Reserve 4 Coson").type(JSONObject.class).outType(CommonResponse.class)
                .param().name("body").type(body).description("door controller reserve 4 Coson").endParam()
                .responseMessage().code(200).message("success").endResponseMessage()
                .to("bean:doorCtrlMgrService?method=doorCtrlReserve4Coson");

        logger.error("camel router end*******************");
    }

}
