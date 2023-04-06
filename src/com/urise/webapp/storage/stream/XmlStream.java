package com.urise.webapp.storage.stream;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStream implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStream() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, OrganizationSection.class,
                TextSection.class, ListTextSection.class, Period.class);
    }

    @Override
    public void doWrite(final Resume r, final OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(final InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
