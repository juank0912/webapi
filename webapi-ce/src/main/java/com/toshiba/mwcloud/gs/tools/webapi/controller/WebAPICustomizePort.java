/*
 	Copyright (c) 2019 TOSHIBA Digital Solutions Corporation.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.toshiba.mwcloud.gs.tools.webapi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("griddb_webapiPath.properties")
public class WebAPICustomizePort implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	@Value("${adminHome}")
	private String adminHome;

	@Value("${propertyFilePath}")
	private String propertyFilePath;

	/**
	 * Customize port of API
	 */
	@Override
	public void customize(ConfigurableWebServerFactory factory) {

		Properties configProp = new Properties();
		try {
			File file = new File(adminHome, propertyFilePath);
			configProp.load(new FileInputStream(file));
		} catch (IOException exception) {
			System.out.println(
					"No file griddb_webapi.properties has been found with the configuration in file griddb_webapiPath.properties");
		}
		String port = configProp.getProperty("port");
		if (port != null) {
			factory.setPort(Integer.parseInt(port));
		}
	}

}
