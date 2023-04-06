package com.urise.webapp.storage.stream;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStream implements StreamSerializer {
    @Override
    public void doWrite(final Resume r, final OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                AbstractSection section = entry.getValue();
                writeSection(dos, section);
            }
        }
    }


    private void writeSection(DataOutputStream dos, AbstractSection section) throws IOException {
        if (section instanceof TextSection) {
            writeNotNullElement(dos, (((TextSection) section).getText()));
        }

        if (section instanceof ListTextSection) {
            dos.writeInt(((ListTextSection) section).getList().size());

            for (String value : ((ListTextSection) section).getList()) {
                writeNotNullElement(dos, value);
            }
        }

        if (section instanceof OrganizationSection) {
            dos.writeInt(((OrganizationSection) section).getAllOrganizations().size());

            for (Organization organization : ((OrganizationSection) section).getAllOrganizations()) {
                writeNotNullElement(dos, organization.getName());
                writeNotNullElement(dos, organization.getWebsite());

                dos.writeInt(organization.getPeriods().size());

                for (Period period : organization.getPeriods()) {
                    writeNotNullElement(dos, String.valueOf(period.getStartDate()));
                    writeNotNullElement(dos, String.valueOf(period.getEndDate()));
                    writeNotNullElement(dos, period.getTitle());
                    writeNotNullElement(dos, period.getDescription());
                }
            }
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

            int size = dis.readInt();

            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            AbstractSection section;

            int numSections = dis.readInt();
            for (int i = 0; i < numSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                section = readSection(dis, sectionType);
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        if (sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE) {
            return new TextSection(readNotNullElement(dis));
        }
        if (sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATION) {
            List<String> list = new ArrayList<>();

            int num1 = dis.readInt();

            for (int j = 0; j < num1; j++) {
                list.add(readNotNullElement(dis));
            }
            return new ListTextSection(list);
        }
        if (sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION) {
            List<Organization> allOrganisations = new ArrayList<>();

            int num2 = dis.readInt();

            for (int j = 0; j < num2; j++) {
                String name = readNotNullElement(dis);
                String website = readNotNullElement(dis);

                int num3 = dis.readInt();

                List<Period> periods = new ArrayList<>();
                for (int k = 0; k < num3; k++) {
                    String startDateStr = readNotNullElement(dis);
                    String endDateStr = readNotNullElement(dis);
                    String title = readNotNullElement(dis);
                    String description = readNotNullElement(dis);
                    Period period = new Period(LocalDate.parse(Objects.requireNonNull(startDateStr)), LocalDate.parse(Objects.requireNonNull(endDateStr)), title);
                    period.setDescription(description);
                    periods.add(period);
                }
                Organization org = (website != null) ? new Organization(name, website, periods) : new Organization(name, periods);
                allOrganisations.add(org);
            }
            return new OrganizationSection(allOrganisations);
        }
        return null;
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
