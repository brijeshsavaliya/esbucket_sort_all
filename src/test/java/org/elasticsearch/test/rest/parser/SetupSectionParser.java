/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.test.rest.parser;

import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.rest.section.SetupSection;

import java.io.IOException;

/**
 * Parser for setup sections
 */
public class SetupSectionParser implements RestTestFragmentParser<SetupSection> {

    @Override
    public SetupSection parse(RestTestSuiteParseContext parseContext) throws IOException, RestTestParseException {

        XContentParser parser = parseContext.parser();

        SetupSection setupSection = new SetupSection();
        setupSection.setSkipSection(parseContext.parseSkipSection());

        while (parser.currentToken() != XContentParser.Token.END_ARRAY) {
            parseContext.advanceToFieldName();
            if (!"do".equals(parser.currentName())) {
                throw new RestTestParseException("section [" + parser.currentName() + "] not supported within setup section");
            }

            parser.nextToken();
            setupSection.addDoSection(parseContext.parseDoSection());
            parser.nextToken();
        }

        parser.nextToken();

        return setupSection;
    }
}
