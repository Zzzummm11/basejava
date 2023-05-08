package com.urise.webapp.storage.stream;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStream implements StreamSerializer {
    @Override
    public void doWrite(final Resume r, final OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();

            writeWithExeption(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();

            writeWithExeption(sections.entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                AbstractSection section = entry.getValue();
                writeSection(dos, section, sectionType);
            });
        }
    }

    @FunctionalInterface
    private interface WriteCollection<K> {
        void write(K k) throws IOException;
    }

    private <K> void writeWithExeption(Collection<K> collection, DataOutputStream dos,
                                       WriteCollection<K> map) throws IOException {
        dos.writeInt(collection.size());
        for (K i : collection) {
            map.write(i);
        }
    }

    private void writeSection(DataOutputStream dos, AbstractSection section, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                writeNotNullElement(dos, (((TextSection) section).getText()));
                break;

            case ACHIEVEMENT:
            case QUALIFICATION:

                writeWithExeption(((ListTextSection) section).getList(), dos, value -> writeNotNullElement(dos, value));
                break;

            case EXPERIENCE:
            case EDUCATION:

                writeWithExeption(((OrganizationSection) section).getAllOrganizations(), dos, organization -> {
                    writeNotNullElement(dos, organization.getName());
                    writeNotNullElement(dos, organization.getWebsite());

                    writeWithExeption(organization.getPeriods(), dos, period -> {

                        writeNotNullElement(dos, String.valueOf(period.getStartDate()));
                        writeNotNullElement(dos, String.valueOf(period.getEndDate()));
                        writeNotNullElement(dos, period.getTitle());
                        writeNotNullElement(dos, period.getDescription());
                    });
                });
                break;

            default:
                break;
        }
    }

    private void writeNotNullElement(DataOutputStream dos, Object element) throws IOException {
        if (element != null) {
            dos.writeUTF(String.valueOf(element));
        } else {
            dos.writeUTF("");
        }
    }


    @Override
    public Resume doRead(final InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);

            readWithExeption(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithExeption(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface ReadCollection {
        void read() throws IOException;
    }

    private void readWithExeption(DataInputStream dis, ReadCollection map) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            map.read();
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(readNotNullElement(dis));

            case ACHIEVEMENT:
            case QUALIFICATION:
                List<String> list = new ArrayList<>();

                readWithExeption(dis, () -> list.add(readNotNullElement(dis)));
                return new ListTextSection(list);

            case EXPERIENCE:
            case EDUCATION:
                List<Organization> allOrganisations = new ArrayList<>();

                readWithExeption(dis, () -> {
                    String name = readNotNullElement(dis);
                    String website = readNotNullElement(dis);

                    List<Period> periods = new ArrayList<>();

                    readWithExeption(dis, () -> {
                        String startDateStr = readNotNullElement(dis);
                        String endDateStr = readNotNullElement(dis);
                        String title = readNotNullElement(dis);
                        String description = readNotNullElement(dis);
                        Period period = new Period(LocalDate.parse(Objects.requireNonNull(startDateStr)), LocalDate.parse(Objects.requireNonNull(endDateStr)), title);
                        period.setDescription(description);
                        periods.add(period);
                    });
                    if (website != null) {
                        allOrganisations.add(new Organization(name, website, periods));
                    } else {
                        allOrganisations.add(new Organization(name, periods));
                    }
                });
                return new OrganizationSection(allOrganisations);
            default:
                return null;
        }
    }

    private String readNotNullElement(DataInputStream dis) throws IOException {
        String ob = dis.readUTF();
        if (ob.isEmpty()) {
            return null;
        } else {
            return ob;
        }
    }


}

