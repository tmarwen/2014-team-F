/*
 * Copyright 2013 eXo Platform SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@juzu.Application
@Servlet(value = "/")
@Portlet
@Scripts({
    @Script( id="jquery", value= "//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js",
        location = AssetLocation.URL),
    @Script( id="bootstrap", value = "//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js",
        location = AssetLocation.URL,
    depends = "jquery")
})
@Assets("*")
package org.exoplatform.addons.codefest.fteam;

import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Scripts;
import juzu.plugin.portlet.Portlet;
import juzu.plugin.servlet.Servlet;