/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.v4creations.tmd.system.api;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;


public class RssJSONConverter extends JSONConverter {

    public RssJSONConverter(Gson gson) {
        super(gson);
    }

    public RssJSONConverter(Gson gson, String encoding) {
        super(gson, encoding);
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        JSONObject jsonObject = (JSONObject) super.fromBody(body, type);
        JSONArray result = null;
        try {
            result = jsonObject.getJSONObject("responseData").getJSONObject("feed").getJSONArray("entries");
            return getGson().fromJson(result.toString(), type);
        } catch (JSONException e) {
            throw new ConversionException(e);
        }
    }
}
